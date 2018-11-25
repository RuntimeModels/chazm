package runtimemodels.chazm.model.entity

import runtimemodels.chazm.api.entity.InstanceGoal
import runtimemodels.chazm.api.entity.SpecificationGoal
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
     * @param id        the [UniqueId] that represents the [InstanceGoal].
     * @param goal      the [SpecificationGoal] of the [InstanceGoal].
     * @param parameter the [InstanceGoal.Parameter] of the [InstanceGoal].
     * @return an [InstanceGoal].
     */
    fun buildInstanceGoal(id: UniqueId<InstanceGoal>, goal: SpecificationGoal, parameter: InstanceGoal.Parameter): InstanceGoal

}
