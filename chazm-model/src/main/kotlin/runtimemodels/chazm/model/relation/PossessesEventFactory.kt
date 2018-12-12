package runtimemodels.chazm.model.relation

import runtimemodels.chazm.api.relation.Possesses
import runtimemodels.chazm.model.event.EventType

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
