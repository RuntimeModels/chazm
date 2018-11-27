package runtimemodels.chazm.api.relation

import runtimemodels.chazm.api.Organization
import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.entity.Capability

/**
 * The [Possesses] interface defines a possesses relation, where an [Agent] possesses a [Capability], of an
 * [Organization].
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface Possesses {

    /**
     * The [Agent] of this [Possesses].
     */
    val agent: Agent

    /**
     * The [Capability] of this [Possesses].
     */
    val capability: Capability

    /**
     * The score of this [Possesses].
     */
    var score: Double
}
