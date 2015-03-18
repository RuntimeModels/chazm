package event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.StampedLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DefaultMediator implements Mediator {

	private static final Logger logger = LoggerFactory.getLogger(DefaultMediator.class);
	private final Map<Class<?>, Map<Subscriber, Method>> subscribers = new ConcurrentHashMap<>();
	private final StampedLock lock = new StampedLock();

	@Override
	public <T> void post(final Event<T> event) {
		get(event.get().getClass()).parallelStream().forEach(c -> invoke(c.getValue(), c.getKey(), event.get()));
	}

	private Set<Entry<Subscriber, Method>> get(final Class<?> type) {
		final long stamp = lock.readLock();
		try {
			if (subscribers.containsKey(type)) {
				return subscribers.get(type).entrySet();
			}
		} finally {
			lock.unlockRead(stamp);
		}
		return new HashSet<>();
	}

	@Override
	public void register(final Subscriber subscriber) {
		Arrays.stream(subscriber.getClass().getDeclaredMethods()).parallel().filter(p -> p.isAnnotationPresent(Subscribe.class) && p.getParameterCount() == 1)
				.forEach(c -> add(c, subscriber));
	}

	private void add(final Method method, final Subscriber subscriber) {
		final Class<?> type = method.getParameterTypes()[0];
		final long stamp = lock.writeLock();
		try {
			final Map<Subscriber, Method> map = subscribers.computeIfAbsent(type, f -> new ConcurrentHashMap<>());
			if (map.containsKey(subscriber)) {
				logger.warn("Subscriber ({}) is already registered to ({})", subscriber, type);
			} else {
				logger.info("Registering ({}) for ({}) with ({})", subscriber, type, method);
				map.put(subscriber, method);
			}
		} finally {
			lock.unlockWrite(stamp);
		}
	}

	private <T> void invoke(final Method method, final Subscriber subscriber, final T payload) {
		try {
			method.invoke(subscriber, payload);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			logger.warn("Unable to invoke {}.{}({})", subscriber.getClass().getName(), method.getName(), payload);
			final long stamp = lock.writeLock();
			try {
				final Map<Subscriber, Method> map = subscribers.get(payload.getClass());
				map.remove(subscriber);
				if (map.isEmpty()) {
					subscribers.remove(payload.getClass());
				}
			} finally {
				lock.unlockWrite(stamp);
			}
		}
	}

}
