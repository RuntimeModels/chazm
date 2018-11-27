package runtimemodels.chazm.model.id

import runtimemodels.chazm.api.entity.Capability
import runtimemodels.chazm.api.id.CapabilityId

internal data class DefaultCapabilityId(
    private val id: String
) : AbstractId<Capability>(Capability::class.java), CapabilityId
