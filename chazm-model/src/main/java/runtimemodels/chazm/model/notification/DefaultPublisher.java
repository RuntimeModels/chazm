package runtimemodels.chazm.model.notification;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.constraints.NotNull;

@Singleton
class DefaultPublisher implements Publisher {

    private final Mediator mediator;

    @Inject
    DefaultPublisher(@NotNull final Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public <T> void post(@NotNull final T event) {
        mediator.post(event);
    }

}
