package runtimemodels.chazm.model.entity

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Policy
import runtimemodels.chazm.api.id.PolicyId
import javax.inject.Inject

internal data class PolicyEntity @Inject constructor(
    @Assisted override val id: PolicyId
) : Policy
