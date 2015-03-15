package org.models.organization.event;

import org.models.organization.id.UniqueId;

/**
 * The {@linkplain Publisher} interface defines the APIs a publish/subscribe pattern (a.k.a. observer pattern). Publishers push out updates that get sent to
 * subscribers.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface Publisher {
	/**
	 * The {@linkplain Type} enumerates the types of update.
	 *
	 * @author Christopher Zhong
	 * @since 7.0.0
	 */
	enum Type {
		/**
		 * Something was added.
		 */
		ADDED,
		/**
		 * Something was removed.
		 */
		REMOVED,
		/**
		 * Something was changed.
		 */
		CHANGED;
	}

	/**
	 * Publishes an update.
	 *
	 * @param type
	 *            the {@linkplain Type} of update.
	 * @param clazz
	 *            the {@linkplain Class} of the instance.
	 * @param id
	 *            the {@linkplain UniqueId} that represent an instance of the class.
	 */
	default <T> void publish(final Type type, final Class<T> clazz, final UniqueId<? extends T> id) {
		final Mediator mediator = MediatorFactory.getMediator();
		// mediator.publish(this, type, clazz, id);
	}

	/**
	 * Publishes an update.
	 *
	 * @param type
	 *            the {@linkplain Type} of update.
	 * @param clazz
	 *            the {@linkplain Class} of the instance.
	 * @param ids
	 */
	default <T> void publish(final Type type, final Class<T> clazz, final UniqueId<?>... ids) {
		final Mediator mediator = MediatorFactory.getMediator();
		// mediator.publish(this, type, clazz, ids);
	}

	/**
	 * @param clazz
	 * @param id
	 */
	default <T> void publishAdd(final Class<T> clazz, final UniqueId<? extends T> id) {
		publish(Type.ADDED, clazz, id);
	}

	/**
	 * @param clazz
	 * @param id
	 */
	default <T> void publishRemove(final Class<T> clazz, final UniqueId<? extends T> id) {
		publish(Type.REMOVED, clazz, id);
	}

	/**
	 * @param clazz
	 * @param ids
	 */
	default <T> void publishAdd(final Class<T> clazz, final UniqueId<?>... ids) {
		publish(Type.ADDED, clazz, ids);
	}

	/**
	 * @param clazz
	 * @param ids
	 */
	default <T> void publishRemove(final Class<T> clazz, final UniqueId<?>... ids) {
		publish(Type.REMOVED, clazz, ids);
	}

	/**
	 * @param clazz
	 * @param ids
	 */
	default <T> void publishChange(final Class<T> clazz, final UniqueId<?>... ids) {
		publish(Type.CHANGED, clazz, ids);
	}
}
