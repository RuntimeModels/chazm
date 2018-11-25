package runtimemodels.chazm.model.notification

import runtimemodels.chazm.model.message.L
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.locks.StampedLock
import javax.inject.Singleton
import kotlin.collections.Map.Entry

@Singleton
internal open class DefaultMediator : Mediator {
    private val eventSubscribers: MutableMap<Class<*>, MutableMap<Subscriber, Method>> = ConcurrentHashMap()
    private val subscriberEvents: MutableMap<Subscriber, MutableMap<Class<*>, Method>> = ConcurrentHashMap()

    private val lock = StampedLock()

    val events: Set<Class<*>>
        get() {
            val stamp = lock.readLock()
            try {
                return eventSubscribers.keys
            } finally {
                lock.unlockRead(stamp)
            }
        }

    val subscribers: Set<Subscriber>
        get() {
            val stamp = lock.readLock()
            try {
                return subscriberEvents.keys
            } finally {
                lock.unlockRead(stamp)
            }
        }

    fun getSubscriberEvents(subscriber: Subscriber): Map<Class<*>, Method> {
        val stamp = lock.readLock()
        try {
            return subscriberEvents[subscriber]!!.toMap()
        } finally {
            lock.unlockRead(stamp)
        }
    }

    fun register(vararg subscribers: Subscriber) {
        subscribers.forEach(::register)
    }

    fun unregister(vararg subscribers: Subscriber) {
        subscribers.forEach(::unregister)
    }

    override fun <T : Any> post(event: T) {
        /* prevents multiple post from occurring simultaneously because it is possible for a subscriber to be notified of two or more events at the same time */
        val set = get(event.javaClass)
        synchronized(this) {
            /* notifying all subscribers for this event can be performed in parallel because a subscriber can only subscribe at most once to the same event */
            set.forEach { invoke(it.value, it.key, event) }
        }
    }

    override fun register(subscriber: Subscriber) {
        subscriber.javaClass.declaredMethods
            .filter { it.isAnnotationPresent(Subscribe::class.java) && it.parameterCount == 1 }
            .forEach { add(it, subscriber) }
    }

    override fun unregister(subscriber: Subscriber) {
        val stamp = lock.writeLock()
        try {
            if (subscriberEvents.containsKey(subscriber)) {
                val map = subscriberEvents.remove(subscriber)!!
                map.keys.forEach {
                    val m = eventSubscribers[it]!!
                    m.remove(subscriber)
                    if (m.isEmpty()) {
                        eventSubscribers.remove(it)
                    }
                }
            }
        } finally {
            lock.unlockWrite(stamp)
        }
    }

    private operator fun get(type: Class<*>): Set<Entry<Subscriber, Method>> {
        val stamp = lock.readLock()
        try {
            if (eventSubscribers.containsKey(type)) {
                return eventSubscribers[type]!!.entries
            }
        } finally {
            lock.unlockRead(stamp)
        }
        return setOf()
    }

    private operator fun <T : Any> invoke(method: Method, subscriber: Subscriber, event: T) {
        try {
            method.invoke(subscriber, event)
        } catch (e: IllegalAccessException) {
            log.warn(L.UNABLE_TO_INVOKE.get(), subscriber.javaClass.name, method.name, event)
            val stamp = lock.writeLock()
            try {
                val m1 = eventSubscribers[event.javaClass]!!
                m1.remove(subscriber)
                if (m1.isEmpty()) {
                    eventSubscribers.remove(event.javaClass)
                }
                val m2 = subscriberEvents[subscriber]!!
                m2.remove(event.javaClass)
                if (m2.isEmpty()) {
                    subscriberEvents.remove(subscriber)
                }
            } finally {
                lock.unlockWrite(stamp)
            }
        } catch (e: IllegalArgumentException) {
            log.warn(L.UNABLE_TO_INVOKE.get(), subscriber.javaClass.name, method.name, event)
            val stamp = lock.writeLock()
            try {
                val m1 = eventSubscribers[event.javaClass]!!
                m1.remove(subscriber)
                if (m1.isEmpty()) {
                    eventSubscribers.remove(event.javaClass)
                }
                val m2 = subscriberEvents[subscriber]!!
                m2.remove(event.javaClass)
                if (m2.isEmpty()) {
                    subscriberEvents.remove(subscriber)
                }
            } finally {
                lock.unlockWrite(stamp)
            }
        } catch (e: InvocationTargetException) {
            log.warn(L.UNABLE_TO_INVOKE.get(), subscriber.javaClass.name, method.name, event)
            val stamp = lock.writeLock()
            try {
                val m1 = eventSubscribers[event.javaClass]!!
                m1.remove(subscriber)
                if (m1.isEmpty()) {
                    eventSubscribers.remove(event.javaClass)
                }
                val m2 = subscriberEvents[subscriber]!!
                m2.remove(event.javaClass)
                if (m2.isEmpty()) {
                    subscriberEvents.remove(subscriber)
                }
            } finally {
                lock.unlockWrite(stamp)
            }
        }

    }

    private fun add(method: Method, subscriber: Subscriber) {
        val type = method.parameterTypes[0]
        val stamp = lock.writeLock()
        try {
            val map = eventSubscribers.computeIfAbsent(type) { ConcurrentHashMap() }
            if (map.containsKey(subscriber)) {
                log.warn(L.SUBSCRIBER_ALREADY_REGISTERED.get(), subscriber, type)
            } else {
                log.info(L.SUBSCRIBER_REGISTERED.get(), subscriber, type, method)
                map[subscriber] = method
                subscriberEvents.computeIfAbsent(subscriber) { ConcurrentHashMap() }[type] = method
            }
        } finally {
            lock.unlockWrite(stamp)
        }
    }

    companion object {
        private val log = org.slf4j.LoggerFactory.getLogger(DefaultMediator::class.java)
    }

}
