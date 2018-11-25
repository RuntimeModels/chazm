package runtimemodels.chazm.model.event

import runtimemodels.chazm.api.relation.Uses

/**
 * The [UsesEventFactory] interface defines the API for constructing [UsesEvent]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface UsesEventFactory {

    /**
     * Constructs an [UsesEvent].
     *
     * @param category the [EventType].
     * @param uses     the [Uses].
     * @return a [UsesEvent].
     */
    fun build(category: EventType, uses: Uses): UsesEvent

}
