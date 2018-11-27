package runtimemodels.chazm.api.relation

import runtimemodels.chazm.api.entity.Pmf
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.organization.Organization

/**
 * The [Uses] interface defines a uses relation, where a [Role] uses a [Pmf], of an [Organization].
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface Uses {
    /**
     * The [Role] of this [Uses].
     */
    val role: Role

    /**
     * The [Pmf] of this [Uses].
     */
    val pmf: Pmf
}
