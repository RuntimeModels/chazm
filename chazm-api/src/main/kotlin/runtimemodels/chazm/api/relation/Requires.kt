package runtimemodels.chazm.api.relation

import runtimemodels.chazm.api.Organization
import runtimemodels.chazm.api.entity.Capability
import runtimemodels.chazm.api.entity.Role

/**
 * The [Requires] interface defines a requires relation, where a [Role] requires a [Capability], of an
 * [Organization].
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface Requires {
    /**
     * The [Role] of this [Requires].
     */
    val role: Role

    /**
     * The [Capability] of this [Requires].
     */
    val capability: Capability
}
