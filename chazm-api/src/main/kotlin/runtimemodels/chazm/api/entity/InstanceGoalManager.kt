package runtimemodels.chazm.api.entity

import runtimemodels.chazm.api.id.InstanceGoalId
import runtimemodels.chazm.api.id.SpecificationGoalId

/**
 * The [InstanceGoalManager] interface defines APIs that can be used for managing a set [InstanceGoal]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface InstanceGoalManager : Map<InstanceGoalId, InstanceGoal> {
    /**
     * Adds an [InstanceGoal] to this [InstanceGoalManager].
     *
     * @param goal the [InstanceGoal] to add.
     */
    fun add(goal: InstanceGoal)

    /**
     * Removes an [InstanceGoal] from this [InstanceGoalManager].
     *
     * @param id the [InstanceGoalId] that represents the [InstanceGoal] to remove.
     * @return the [InstanceGoal] that was removed.
     */
    fun remove(id: InstanceGoalId): InstanceGoal

    /**
     * Removes all [InstanceGoal]s associated to a [SpecificationGoal] from this [InstanceGoalManager].
     *
     * @param id the [SpecificationGoalId] that represents the [SpecificationGoal]
     */
    fun remove(id: SpecificationGoalId)
}
