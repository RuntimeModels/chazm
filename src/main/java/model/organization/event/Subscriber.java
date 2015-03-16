package model.organization.event;

/**
 * The {@linkplain Subscriber} interface defines the APIs for a publish/subscribe pattern (a.k.a. observer pattern). Subscribers receive updates that have been
 * pushed out by publishers.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface Subscriber {
	/**
	 *
	 */
	void notifySubscriber();
}
