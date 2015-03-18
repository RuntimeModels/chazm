package event;

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

	public <T> void post(final Event<T> event) {
		mediator.post(event);
	}

	@Override
	public <T> void post(Type type, Class<T> clazz, UniqueId<? extends T> id) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> void post(Type type, Class<T> clazz, UniqueId<?>... ids) {
		// TODO Auto-generated method stub

	}

}
