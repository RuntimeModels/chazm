package event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@linkplain Subscribe} annotation allow {@linkplain Subscriber}s to indicate one or more methods that subscribe to some particular {@linkplain Event}s.
 * <p>
 * Any methods that have the {@linkplain Subscribe} annotation must have exactly one parameter of the {@linkplain Event} type. The example below show a
 * {@linkplain Subscriber} that subscribes to two different types of event.
 *
 * <pre>
 * &#064;Subscribe
 * public void onEvent(Event&lt;Agent&gt; event);
 * </pre>
 *
 * <pre>
 * &#064;Subscribe
 * public void someOtherEvent(Event&lt;Role&gt; event);
 * </pre>
 *
 * @author Christopher Zhong
 * @see Event
 * @see Subscriber
 * @since 7.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Subscribe {}
