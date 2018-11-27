package runtimemodels.chazm.api.relation

import runtimemodels.chazm.api.Organization
import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.entity.Attribute

/**
 * The [Has] interface defines a has relation, where an [Agent] has an [Attribute], of an [Organization].
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface Has {
    /**
     * The [Agent] of this [Has].
     */
    val agent: Agent

    /**
     * The [Attribute] of this [Has].
     */
    val attribute: Attribute

    /**
     * The value of this [Has].
     */
    var value: Double
}
