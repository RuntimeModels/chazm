package model.organization.event;

/**
 * The {@linkplain Event} interface defines an event that carries a payload. A {@linkplain Subscriber} must annotate one or more methods with
 * {@linkplain Subscribe}. The example below show a {@linkplain Subscriber} that subscribes to two different types of event.
 *
 * <pre>
 * &#064;Subscribe
 * public void onEvent(Event&lt;Agent&gt; event);
 * 
 * &#064;Subscribe
 * public void someOtherEvent(Event&lt;Role&gt; event);
 * </pre>
 *
 * @author Christopher Zhong
 * @see Subscribe
 * @see Subscriber
 * @param <T>
 *            the type of the object the {@linkplain Event} is carrying.
 * @since 7.0.0
 */
public interface Event<T> {

	/**
	 * Returns the payload this {@linkplain Event} is carrying.
	 *
	 * @return the payload.
	 */
	T get();

}
