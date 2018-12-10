package runtimemodels.chazm.model.entity

import runtimemodels.chazm.api.entity.InstanceGoal
import runtimemodels.chazm.api.entity.SpecificationGoal
import runtimemodels.chazm.api.id.InstanceGoalId
import runtimemodels.chazm.api.id.UniqueId

/**
 * The [InstanceGoalFactory] interface defines the APIs for constructing [InstanceGoal]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface InstanceGoalFactory {
    /**
     * Constructs an [InstanceGoal].
     *
     * @param id        the [UniqueId] that represents this [InstanceGoal].
     * @param goal      the [SpecificationGoal] of this [InstanceGoal].
     * @param parameter a [Map] of parameters for this [InstanceGoal].
     * @return an [InstanceGoal].
     */
    fun build(id: InstanceGoalId, goal: SpecificationGoal, parameter: Map<Any, Any>): InstanceGoal
}
