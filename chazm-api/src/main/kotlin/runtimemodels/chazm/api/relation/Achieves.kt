package runtimemodels.chazm.api.relation

import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.entity.SpecificationGoal
import runtimemodels.chazm.api.organization.Organization

/**
 * The [Achieves] interface defines an achieves relation, which means that a [Role] achieves
 * a [SpecificationGoal], of an [Organization].
 *
 * @author Christopher Zhong
 * @see Role
 * @see SpecificationGoal
 * @since 7.0.0
 */
interface Achieves {
    /**
     * The [Role] of this [Achieves].
     */
    val role: Role

    /**
     * The [SpecificationGoal] of this [Achieves].
     */
    val goal: SpecificationGoal
}
