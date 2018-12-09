package runtimemodels.chazm.model.factory.relation

import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.relation.Needs

/**
 * The [NeedsFactory] interface defines the API for constructing [Needs] relations.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface NeedsFactory {
    /**
     * Constructs a [Needs].
     *
     * @param role      the [Role] of the [Needs].
     * @param attribute the [Attribute] of the [Needs].
     * @return a [Needs].
     */
    fun build(role: Role, attribute: Attribute): Needs
}
