package runtimemodels.chazm.model.notification

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal open class DefaultPublisher @Inject constructor(private val mediator: Mediator) : Publisher {

    override fun <T : Any> post(event: T) {
        mediator.post(event)
    }

}
