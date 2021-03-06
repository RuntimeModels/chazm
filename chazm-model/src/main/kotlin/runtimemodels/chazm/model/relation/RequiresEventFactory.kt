package runtimemodels.chazm.model.relation

import runtimemodels.chazm.api.relation.Requires
import runtimemodels.chazm.model.event.EventType

/**
 * The [RequiresEventFactory] interface defines the API for constructing [RequiresEvent]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface RequiresEventFactory {
    /**
     * Constructs an [RequiresEvent].
     *
     * @param category the [EventType].
     * @param requires the [Requires].
     * @return a [RequiresEvent].
     */
    fun build(category: EventType, requires: Requires): RequiresEvent
}
