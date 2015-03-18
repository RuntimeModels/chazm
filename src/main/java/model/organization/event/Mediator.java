package model.organization.event;

/**
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface Mediator {

	/**
	 * Pushes an {@linkplain Event} to interested {@linkplain Subscriber}s.
	 *
	 * @param event
	 *            the {@linkplain Event}.
	 */
	<T> void post(Event<T> event);

	/**
	 * Registers a {@linkplain Subscriber} with this {@linkplain Mediator}.
	 *
	 * @param subscriber
	 *            the {@linkplain Subscriber}.
	 */
	void register(Subscriber subscriber);

}
