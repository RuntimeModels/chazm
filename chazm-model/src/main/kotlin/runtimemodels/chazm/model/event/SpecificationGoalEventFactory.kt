package runtimemodels.chazm.model.event

import runtimemodels.chazm.api.entity.SpecificationGoal

/**
 * The [SpecificationGoalEventFactory] interface defines the API for constructing [SpecificationGoalEvent]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface SpecificationGoalEventFactory {

    /**
     * Constructs an [SpecificationGoalEvent].
     *
     * @param category the [EventType].
     * @param goal     the [SpecificationGoal].
     * @return a [SpecificationGoalEvent].
     */
    fun build(category: EventType, goal: SpecificationGoal): SpecificationGoalEvent

}
