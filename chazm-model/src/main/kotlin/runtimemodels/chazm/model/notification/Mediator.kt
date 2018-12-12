package runtimemodels.chazm.model.notification

/**
 * The [Mediator] interface defines the APIs for being a mediator between publishers and subscribers.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface Mediator { // TODO to be replaced with RxJava

    /**
     * Pushes an event to interested [Subscriber]s.
     *
     * @param <T>   the type of the event.
     * @param event the event.
    </T> */
    fun <T : Any> post(event: T)

    /**
     * Registers a [Subscriber] with this [Mediator].
     *
     * @param subscriber the [Subscriber].
     */
    fun register(subscriber: Subscriber)

    /**
     * Unregisters a [Subscriber] from this [Mediator].
     *
     * @param subscriber the [Subscriber].
     */
    fun unregister(subscriber: Subscriber)

}
