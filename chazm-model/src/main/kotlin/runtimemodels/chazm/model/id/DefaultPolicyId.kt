package runtimemodels.chazm.model.id

import runtimemodels.chazm.api.entity.Policy
import runtimemodels.chazm.api.id.PolicyId
import javax.inject.Inject

internal data class DefaultPolicyId @Inject constructor(
    private val id: String
) : AbstractId<Policy>(Policy::class), PolicyId
