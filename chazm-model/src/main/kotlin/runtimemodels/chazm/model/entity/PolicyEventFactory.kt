package runtimemodels.chazm.model.entity

import runtimemodels.chazm.api.entity.Policy
import runtimemodels.chazm.model.event.EventType

/**
 * The [PolicyEventFactory] interface defines the API for constructing [PolicyEvent]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface PolicyEventFactory {
    /**
     * Constructs an [PolicyEvent].
     *
     * @param category the [EventType].
     * @param policy   the [Policy].
     * @return a [PolicyEvent].
     */
    fun build(category: EventType, policy: Policy): PolicyEvent
}
