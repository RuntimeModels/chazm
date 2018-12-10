package runtimemodels.chazm.model.relation

import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.entity.Capability
import runtimemodels.chazm.api.relation.Possesses

/**
 * The [PossessesFactory] interface defines the API for constructing [Possesses] relations.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface PossessesFactory {
    /**
     * Constructs a [Possesses].
     *
     * @param agent      the [Agent] of the [Possesses].
     * @param capability the [Capability] of the [Possesses].
     * @param score      the `double` score of the [Possesses].
     * @return a [Possesses].
     */
    fun build(agent: Agent, capability: Capability, score: Double): Possesses
}
