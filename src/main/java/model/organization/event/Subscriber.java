package model.organization.event;

/**
 * The {@linkplain Subscriber} interface defines the APIs for a publish/subscribe pattern (a.k.a. observer pattern). Subscribers receive updates that have been
 * pushed out by publishers. The example below show a {@linkplain Subscriber} that subscribes to two different types of event.
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
 * @see Event
 * @see Subscribe
 * @since 7.0.0
 */
public interface Subscriber {}
