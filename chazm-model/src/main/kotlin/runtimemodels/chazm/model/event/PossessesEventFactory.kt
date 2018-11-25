package runtimemodels.chazm.model.event

import runtimemodels.chazm.api.relation.Possesses

/**
 * The [PossessesEventFactory] interface defines the API for constructing [PossessesEvent]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface PossessesEventFactory {

    /**
     * Constructs an [PossessesEvent].
     *
     * @param category  the [EventType].
     * @param possesses the [Possesses].
     * @return a [PossessesEvent].
     */
    fun build(category: EventType, possesses: Possesses): PossessesEvent

}
