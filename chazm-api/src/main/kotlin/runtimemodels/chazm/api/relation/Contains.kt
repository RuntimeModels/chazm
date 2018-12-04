package runtimemodels.chazm.api.relation

import runtimemodels.chazm.api.entity.Characteristic
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.organization.Organization

/**
 * The [Contains] interface defines a contains relation, where a [Role] contains a [Characteristic], of an
 * [Organization].
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface Contains {
    /**
     * The [Role] of this [Contains].
     */
    val role: Role

    /**
     * The [Characteristic] of this [Contains].
     */
    val characteristic: Characteristic

    /**
     * The `double` value of this [Contains].
     */
    var value: Double
}
