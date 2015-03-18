package event;

/**
 * The {@linkplain Mediator} interface defines the APIs for being a mediator between publishers and subscribers.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface Mediator {

	/**
	 * Pushes an event to interested {@linkplain Subscriber}s.
	 *
	 * @param event
	 *            the event.
	 */
	<T> void post(T event);

	/**
	 * Registers a {@linkplain Subscriber} with this {@linkplain Mediator}.
	 *
	 * @param subscriber
	 *            the {@linkplain Subscriber}.
	 */
	void register(Subscriber subscriber);

	/**
	 * Unregisters a {@linkplain Subscriber} from this {@linkplain Mediator}.
	 *
	 * @param subscriber
	 *            the {@linkplain Subscriber}.
	 */
	void unregister(Subscriber subscriber);

}
