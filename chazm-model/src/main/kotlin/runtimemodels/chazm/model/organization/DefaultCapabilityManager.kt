package runtimemodels.chazm.model.organization

import runtimemodels.chazm.api.entity.Capability
import runtimemodels.chazm.api.id.CapabilityId
import runtimemodels.chazm.api.organization.CapabilityManager
import runtimemodels.chazm.model.exceptions.EntityExistsException
import runtimemodels.chazm.model.exceptions.EntityNotExistsException
import javax.inject.Inject

internal data class DefaultCapabilityManager @Inject constructor(
    private val map: MutableMap<CapabilityId, Capability>
) : CapabilityManager, Map<CapabilityId, Capability> by map {
    override fun add(capability: Capability) {
        if (map.containsKey(capability.id)) {
            throw EntityExistsException(capability.id)
        }
        map[capability.id] = capability
    }

    override fun remove(id: CapabilityId): Capability {
        if (map.containsKey(id)) {
            return map.remove(id)!!
        }
        throw EntityNotExistsException(id)
    }
}
