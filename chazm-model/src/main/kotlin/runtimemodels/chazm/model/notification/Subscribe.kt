package runtimemodels.chazm.model.notification

/**
 * The [Subscribe] annotation allow [Subscriber]s to indicate one or more methods that subscribe to some particular events.
 * <br></br>
 * Any methods that have the [Subscribe] annotation must have exactly one parameter. The example below show a [Subscriber] that subscribes
 * to two different types of event.
 * <br></br>
 * <pre>
 * &#064;Subscribe
 * public void onEvent(String event);
</pre> *
 * <br></br>
 * <pre>
 * &#064;Subscribe
 * public void someOtherEvent(Integer event);
</pre> *
 *
 * @author Christopher Zhong
 * @see Subscriber
 *
 * @since 7.0.0
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class Subscribe
