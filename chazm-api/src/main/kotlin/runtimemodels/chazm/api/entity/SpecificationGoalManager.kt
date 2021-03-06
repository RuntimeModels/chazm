package runtimemodels.chazm.api.entity

/**
 * The [SpecificationGoalManager] interface defines APIs that can be used for managing a set [SpecificationGoal]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface SpecificationGoalManager : Map<SpecificationGoalId, SpecificationGoal> {
    /**
     * Adds a [SpecificationGoal] to this [SpecificationGoalManager].
     *
     * @param goal the [SpecificationGoal] to add.
     */
    fun add(goal: SpecificationGoal)

    /**
     * Removes a [SpecificationGoal] from this [SpecificationGoalManager].
     *
     * @param id the [SpecificationGoalId] that represents the [SpecificationGoal] to remove.
     * @return the [SpecificationGoal] that was removed, `null` otherwise.
     */
    fun remove(id: SpecificationGoalId): SpecificationGoal?
}
