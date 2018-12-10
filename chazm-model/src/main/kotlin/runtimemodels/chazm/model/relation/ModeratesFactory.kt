package runtimemodels.chazm.model.relation

import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.entity.Pmf
import runtimemodels.chazm.api.relation.Moderates

/**
 * The [ModeratesFactory] interface defines the API for constructing [Moderates] relations.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface ModeratesFactory {
    /**
     * Constructs a [Moderates].
     *
     * @param pmf       the [Pmf] of the [Moderates].
     * @param attribute the [Attribute] of the [Moderates].
     * @return a [Moderates].
     */
    fun build(pmf: Pmf, attribute: Attribute): Moderates
}
