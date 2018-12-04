package runtimemodels.chazm.model.factory

import runtimemodels.chazm.api.entity.Capability
import runtimemodels.chazm.api.id.CapabilityId
import runtimemodels.chazm.api.id.UniqueId

/**
 * The [CapabilityFactory] interface defines the APIs for constructing [Capability]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface CapabilityFactory {

    /**
     * Constructs a [Capability].
     *
     * @param id the [UniqueId] that represents the [Capability].
     * @return a [Capability].
     */
    fun buildCapability(id: CapabilityId): Capability

}
