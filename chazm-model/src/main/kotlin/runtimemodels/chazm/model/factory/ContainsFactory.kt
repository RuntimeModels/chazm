package runtimemodels.chazm.model.factory

import runtimemodels.chazm.api.entity.Characteristic
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.relation.Contains

/**
 * The [ContainsFactory] interface defines the API for constructing [Contains] relations.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface ContainsFactory {

    /**
     * Constructs a [Contains].
     *
     * @param role           the [Role] of the [Contains].
     * @param characteristic the [Characteristic] of the [Contains].
     * @param value          the `double` value of the [Contains].
     * @return a [Contains].
     */
    fun buildContains(role: Role, characteristic: Characteristic, value: Double): Contains

}
