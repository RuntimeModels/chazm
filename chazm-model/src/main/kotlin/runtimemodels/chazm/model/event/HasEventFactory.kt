package runtimemodels.chazm.model.event

import runtimemodels.chazm.api.relation.Has

/**
 * The [HasEventFactory] interface defines the API for constructing [HasEvent]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface HasEventFactory {

    /**
     * Constructs an [HasEvent].
     *
     * @param category the [EventType].
     * @param has      the [Has].
     * @return a [HasEvent].
     */
    fun build(category: EventType, has: Has): HasEvent

}
