package runtimemodels.chazm.api.relation

import runtimemodels.chazm.api.Organization
import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.entity.Pmf

/**
 * The [Moderates] interface defines a moderates relation, where a [Pmf] moderates an [Attribute], of an
 * [Organization].
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface Moderates {
    /**
     * The [Pmf] of this [Moderates].
     */
    val pmf: Pmf

    /**
     * The [Attribute] of this [Moderates].
     */
    val attribute: Attribute
}
