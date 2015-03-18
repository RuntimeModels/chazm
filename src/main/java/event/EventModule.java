package event;

import com.google.inject.AbstractModule;

/**
 * The {@linkplain EventModule} class provides a Guice binding module for events (such as pub/sub event notifications).
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class EventModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Mediator.class).to(DefaultMediator.class);
		bind(Publisher.class).to(DefaultPublisher.class);
		bind(Subscriber.class).to(DefaultSubscriber.class);
	}

}
