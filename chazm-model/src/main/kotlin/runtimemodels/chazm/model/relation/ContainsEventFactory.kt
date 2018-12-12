package runtimemodels.chazm.model.relation

import runtimemodels.chazm.api.relation.Contains
import runtimemodels.chazm.model.event.EventType

/**
 * The [ContainsEventFactory] interface defines the API for constructing [ContainsEvent]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface ContainsEventFactory {
    /**
     * Constructs an [ContainsEvent].
     *
     * @param category the [EventType].
     * @param contains the [Contains].
     * @return a [ContainsEvent].
     */
    fun build(category: EventType, contains: Contains): ContainsEvent
}
