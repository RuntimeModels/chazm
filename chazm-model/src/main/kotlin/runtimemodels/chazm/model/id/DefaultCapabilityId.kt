package runtimemodels.chazm.model.id

import runtimemodels.chazm.api.entity.Capability
import runtimemodels.chazm.api.id.CapabilityId
import javax.inject.Inject

internal data class DefaultCapabilityId @Inject constructor(
    private val id: String
) : AbstractId<Capability>(Capability::class), CapabilityId
