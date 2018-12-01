package runtimemodels.chazm.model.organization

import runtimemodels.chazm.api.entity.Policy
import runtimemodels.chazm.api.id.PolicyId
import runtimemodels.chazm.api.organization.PolicyManager
import runtimemodels.chazm.model.exceptions.PolicyExistsException
import runtimemodels.chazm.model.exceptions.PolicyNotExistsException
import javax.inject.Inject

internal data class DefaultPolicyManager @Inject constructor(
    private val map: MutableMap<PolicyId, Policy>
) : PolicyManager, Map<PolicyId, Policy> by map {
    override fun add(policy: Policy) {
        if (map.containsKey(policy.id)) {
            throw PolicyExistsException(policy.id)
        }
        map[policy.id] = policy
    }

    override fun remove(id: PolicyId): Policy {
        if (map.containsKey(id)) {
            return map.remove(id)!!
        }
        throw PolicyNotExistsException(id)
    }
}
