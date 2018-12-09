package runtimemodels.chazm.model.factory.relation

import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.relation.Has

/**
 * The [HasFactory] interface defines the API for constructing [Has] relations.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface HasFactory {
    /**
     * Constructs a [Has].
     *
     * @param agent     the [Agent] of the [Has].
     * @param attribute the [Attribute] of the [Has].
     * @param value     the `double` value of the [Has].
     * @return a [Has].
     */
    fun build(agent: Agent, attribute: Attribute, value: Double): Has
}
