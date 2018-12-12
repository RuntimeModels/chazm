package runtimemodels.chazm.model.entity

import runtimemodels.chazm.api.entity.SpecificationGoal
import runtimemodels.chazm.api.entity.SpecificationGoalId
import runtimemodels.chazm.api.id.UniqueId

/**
 * The [SpecificationGoalFactory] interface defines the APIs for constructing [SpecificationGoal]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface SpecificationGoalFactory {
    /**
     * Constructs a [SpecificationGoal].
     *
     * @param id the [UniqueId] that represents the [SpecificationGoal].
     * @return a [SpecificationGoal].
     */
    fun build(id: SpecificationGoalId): SpecificationGoal
}
