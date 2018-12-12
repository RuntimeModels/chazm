package runtimemodels.chazm.model.relation

import runtimemodels.chazm.api.entity.Capability
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.relation.Requires

/**
 * The [RequiresFactory] interface defines the API for constructing [Requires] relations.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface RequiresFactory {
    /**
     * Constructs a [Requires].
     *
     * @param role       the [Role] of the [Requires].
     * @param capability the [Capability] of the [Requires].
     * @return a [Requires].
     */
    fun build(role: Role, capability: Capability): Requires
}
