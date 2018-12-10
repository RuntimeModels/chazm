package runtimemodels.chazm.model.entity

import runtimemodels.chazm.api.entity.Pmf
import runtimemodels.chazm.api.entity.PmfId
import runtimemodels.chazm.api.id.UniqueId

/**
 * The [PmfFactory] interface defines the APIs for constructing [Pmf]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface PmfFactory {

    /**
     * Constructs a [Pmf].
     *
     * @param id the [UniqueId] that represents the [Pmf].
     * @return a [Pmf].
     */
    fun build(id: PmfId): Pmf

}
