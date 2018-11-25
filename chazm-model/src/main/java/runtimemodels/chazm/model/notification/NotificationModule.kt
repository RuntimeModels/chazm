package runtimemodels.chazm.model.notification

import com.google.inject.AbstractModule

/**
 * The [NotificationModule] class provides a Guice binding module for events (such as pub/sub event notifications).
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
class NotificationModule : AbstractModule() {

    override fun configure() {
        bind(Mediator::class.java).to(DefaultMediator::class.java)
        bind(Publisher::class.java).to(DefaultPublisher::class.java)
        bind(Subscriber::class.java).to(DefaultSubscriber::class.java)
    }

}
