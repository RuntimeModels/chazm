package runtimemodels.chazm.api.relation

import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.organization.Organization

/**
 * The [Needs] interface defines a needs relation, where a [Role] needs an [Attribute], of an [Organization].
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface Needs {
    /**
     * The [Role] of this [Needs].
     */
    val role: Role

    /**
     * The [Attribute] of this [Needs].
     */
    val attribute: Attribute
}
