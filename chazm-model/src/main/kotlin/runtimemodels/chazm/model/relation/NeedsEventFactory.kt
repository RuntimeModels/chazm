package runtimemodels.chazm.model.relation

import runtimemodels.chazm.api.relation.Needs
import runtimemodels.chazm.model.event.EventType

/**
 * The [NeedsEventFactory] interface defines the API for constructing [NeedsEvent]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface NeedsEventFactory {
    /**
     * Constructs an [NeedsEvent].
     *
     * @param category the [EventType].
     * @param needs    the [Needs].
     * @return a [NeedsEvent].
     */
    fun build(category: EventType, needs: Needs): NeedsEvent
}
