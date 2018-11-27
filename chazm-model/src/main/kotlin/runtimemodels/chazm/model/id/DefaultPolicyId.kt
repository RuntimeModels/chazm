package runtimemodels.chazm.model.id

import runtimemodels.chazm.api.entity.Policy
import runtimemodels.chazm.api.id.PolicyId

internal data class DefaultPolicyId(
    private val id: String
) : AbstractId<Policy>(Policy::class.java), PolicyId
