package model.organization.event;

import javax.inject.Inject;
import javax.inject.Singleton;

import model.organization.id.UniqueId;

@Singleton
class DefaultPublisher implements Publisher {

	private final Mediator mediator;

	@Inject
	public DefaultPublisher(final Mediator mediator) {
		this.mediator = mediator;
	}

	@Override
	public <T> void post(final Type type, final Class<T> clazz, final UniqueId<? extends T> id) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> void post(final Type type, final Class<T> clazz, final UniqueId<?>... ids) {
		// TODO Auto-generated method stub

	}

	public <T> void post(final Event<T> event) {
		mediator.notify();
	}

}
