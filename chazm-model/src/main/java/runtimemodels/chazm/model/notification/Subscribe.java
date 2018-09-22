package runtimemodels.chazm.model.notification;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@linkplain Subscribe} annotation allow {@linkplain Subscriber}s to indicate one or more methods that subscribe to some particular events.
 * <p>
 * Any methods that have the {@linkplain Subscribe} annotation must have exactly one parameter. The example below show a {@linkplain Subscriber} that subscribes
 * to two different types of event.
 * <p>
 * <pre>
 * &#064;Subscribe
 * public void onEvent(String event);
 * </pre>
 * <p>
 * <pre>
 * &#064;Subscribe
 * public void someOtherEvent(Integer event);
 * </pre>
 *
 * @author Christopher Zhong
 * @see Subscriber
 * @since 7.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Subscribe {}
