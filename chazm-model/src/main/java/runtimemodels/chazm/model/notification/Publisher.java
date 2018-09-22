package runtimemodels.chazm.model.notification;

/**
 * The {@linkplain Publisher} interface defines the APIs a publish/subscribe pattern (a.k.a. observer pattern). Publishers push out updates that get sent to
 * subscribers.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface Publisher {

    /**
     * Post an event.
     *
     * @param event the event.
     */
    <T> void post(final T event);

}
