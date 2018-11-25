package runtimemodels.chazm.model.notification

/**
 * The [Subscriber] interface defines the APIs for a publish/subscribe pattern (a.k.a. observer pattern). Subscribers receive updates that have been
 * pushed out by publishers. The example below show a [Subscriber] that subscribes to two different types of event.
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
 * @see Subscribe
 *
 * @since 7.0.0
 */
interface Subscriber
