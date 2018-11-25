package runtimemodels.chazm.model.event

import runtimemodels.chazm.api.entity.InstanceGoal

/**
 * The [InstanceGoalEventFactory] interface defines the API for constructing [InstanceGoalEvent]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface InstanceGoalEventFactory {

    /**
     * Constructs an [InstanceGoalEvent].
     *
     * @param category the [EventType].
     * @param goal     the [InstanceGoal].
     * @return a [InstanceGoalEvent].
     */
    fun build(category: EventType, goal: InstanceGoal): InstanceGoalEvent

}
