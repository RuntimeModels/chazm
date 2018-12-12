package runtimemodels.chazm.model.notification

/**
 * The [Publisher] interface defines the APIs a publish/subscribe pattern (a.k.a. observer pattern). Publishers push out updates that get sent to
 * subscribers.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface Publisher { // TODO to be replaced with RxJava

    /**
     * Post an event.
     *
     * @param <T>   the type of event.
     * @param event the event.
    </T> */
    fun <T : Any> post(event: T)

}
