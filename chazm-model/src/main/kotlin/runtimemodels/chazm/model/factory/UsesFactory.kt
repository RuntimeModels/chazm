package runtimemodels.chazm.model.factory

import runtimemodels.chazm.api.entity.Pmf
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.relation.Uses

/**
 * The [UsesFactory] interface defines the API for constructing [Uses] relations.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface UsesFactory {
    /**
     * Constructs an [Uses].
     *
     * @param role the [Role] of the [Uses].
     * @param pmf  the [Pmf] of the [Uses].
     * @return an [Uses].
     */
    fun build(role: Role, pmf: Pmf): Uses
}
