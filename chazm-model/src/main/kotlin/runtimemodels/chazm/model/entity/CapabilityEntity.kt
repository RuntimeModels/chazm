package runtimemodels.chazm.model.entity

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Capability
import runtimemodels.chazm.api.id.CapabilityId
import javax.inject.Inject

internal data class CapabilityEntity @Inject constructor(
    @Assisted override val id: CapabilityId
) : Capability
