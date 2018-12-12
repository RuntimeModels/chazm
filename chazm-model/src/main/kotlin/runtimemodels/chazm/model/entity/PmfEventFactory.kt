package runtimemodels.chazm.model.entity

import runtimemodels.chazm.api.entity.Pmf
import runtimemodels.chazm.model.event.EventType

/**
 * The [PmfEventFactory] interface defines the API for constructing [PmfEvent]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface PmfEventFactory {
    /**
     * Constructs an [PmfEvent].
     *
     * @param category the [EventType].
     * @param pmf      the [Pmf].
     * @return a [PmfEvent].
     */
    fun build(category: EventType, pmf: Pmf): PmfEvent
}
