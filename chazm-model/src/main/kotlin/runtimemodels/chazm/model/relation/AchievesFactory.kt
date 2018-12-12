package runtimemodels.chazm.model.relation

import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.entity.SpecificationGoal
import runtimemodels.chazm.api.relation.Achieves

/**
 * The [AchievesFactory] interface defines the API for constructing [Achieves] relations.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface AchievesFactory {
    /**
     * Constructs an [Achieves].
     *
     * @param role the [Role] of the [Achieves].
     * @param goal the [Achieves] of the [Achieves].
     * @return an [Achieves].
     */
    fun build(role: Role, goal: SpecificationGoal): Achieves
}
