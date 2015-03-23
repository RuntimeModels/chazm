package notification;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class DefaultPublisher implements Publisher {

	private final Mediator mediator;

	@Inject
	public DefaultPublisher(final Mediator mediator) {
		this.mediator = mediator;
	}

	@Override
	public <T> void post(final T event) {
		mediator.post(event);
	}

}
