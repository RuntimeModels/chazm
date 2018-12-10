package runtimemodels.chazm.model.entity.impl

import runtimemodels.chazm.api.entity.*
import runtimemodels.chazm.model.exceptions.EntityExistsException
import runtimemodels.chazm.model.exceptions.EntityNotExistsException
import javax.inject.Inject

internal sealed class DefaultManager

internal data class DefaultAgentManager @Inject constructor(
    private val map: MutableMap<AgentId, Agent>
) : AgentManager, DefaultManager(), Map<AgentId, Agent> by map {
    override fun add(agent: Agent) {
        if (map.containsKey(agent.id)) {
            throw EntityExistsException(agent.id)
        }
        map[agent.id] = agent
    }

    override fun remove(id: AgentId): Agent {
        if (map.containsKey(id)) {
            return map.remove(id)!!
        }
        throw EntityNotExistsException(id)
    }
}

internal data class DefaultAttributeManager @Inject constructor(
    private val map: MutableMap<AttributeId, Attribute>
) : AttributeManager, DefaultManager(), Map<AttributeId, Attribute> by map {
    override fun add(attribute: Attribute) {
        if (map.containsKey(attribute.id)) {
            throw EntityExistsException(attribute.id)
        }
        map[attribute.id] = attribute
    }

    override fun remove(id: AttributeId): Attribute {
        if (map.containsKey(id)) {
            return map.remove(id)!!
        }
        throw EntityNotExistsException(id)
    }

}

internal data class DefaultCapabilityManager @Inject constructor(
    private val map: MutableMap<CapabilityId, Capability>
) : CapabilityManager, DefaultManager(), Map<CapabilityId, Capability> by map {
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

internal data class DefaultCharacteristicManager @Inject constructor(
    private val map: MutableMap<CharacteristicId, Characteristic>
) : CharacteristicManager, DefaultManager(), Map<CharacteristicId, Characteristic> by map {
    override fun add(characteristic: Characteristic) {
        if (map.containsKey(characteristic.id)) {
            throw EntityExistsException(characteristic.id)
        }
        map[characteristic.id] = characteristic
    }

    override fun remove(id: CharacteristicId): Characteristic {
        if (map.containsKey(id)) {
            return map.remove(id)!!
        }
        throw EntityNotExistsException(id)
    }
}

internal data class DefaultInstanceGoalManager @Inject constructor(
    private val map: MutableMap<InstanceGoalId, InstanceGoal>,
    private val byMap: MutableMap<SpecificationGoalId, MutableMap<InstanceGoalId, InstanceGoal>>
) : InstanceGoalManager, DefaultManager(), Map<InstanceGoalId, InstanceGoal> by map {
    override fun add(goal: InstanceGoal) {
        if (map.containsKey(goal.id)) {
            throw EntityExistsException(goal.id)
        }
        map[goal.id] = goal
        byMap.computeIfAbsent(goal.goal.id) { mutableMapOf() }[goal.id] = goal
    }

    override fun remove(id: InstanceGoalId): InstanceGoal {
        if (map.containsKey(id)) {
            val goal = map.remove(id)!!
            if (byMap.containsKey(goal.goal.id)) {
                val m = byMap[goal.goal.id]!!
                if (m.containsKey(id)) {
                    val g = m.remove(id)!!
                    if (goal === g) {
                        return goal
                    } else {
                        throw IllegalStateException("the goal '$g' obtained from 'byMap'[${g.goal.id}][$id] is different from '$goal'")
                    }
                } else {
                    throw IllegalStateException("the key '$id' is missing from the map return by 'byMap[${goal.goal.id}]'")
                }
            } else {
                throw IllegalStateException("the key '${goal.goal.id}' is missing from map 'byMap'")
            }
        }
        throw EntityNotExistsException(id)
    }

    override fun remove(id: SpecificationGoalId) {
        val mBy = byMap.remove(id)
        mBy?.forEach { map.remove(it.key) }
    }
}

internal data class DefaultPmfManager @Inject constructor(
    private val map: MutableMap<PmfId, Pmf>
) : PmfManager, DefaultManager(), Map<PmfId, Pmf> by map {
    override fun add(pmf: Pmf) {
        if (map.containsKey(pmf.id)) {
            throw EntityExistsException(pmf.id)
        }
        map[pmf.id] = pmf
    }

    override fun remove(id: PmfId): Pmf {
        if (map.containsKey(id)) {
            return map.remove(id)!!
        }
        throw EntityNotExistsException(id)
    }
}

internal data class DefaultPolicyManager @Inject constructor(
    private val map: MutableMap<PolicyId, Policy>
) : PolicyManager, DefaultManager(), Map<PolicyId, Policy> by map {
    override fun add(policy: Policy) {
        if (map.containsKey(policy.id)) {
            throw EntityExistsException(policy.id)
        }
        map[policy.id] = policy
    }

    override fun remove(id: PolicyId): Policy {
        if (map.containsKey(id)) {
            return map.remove(id)!!
        }
        throw EntityNotExistsException(id)
    }
}

internal data class DefaultRoleManager @Inject constructor(
    private val map: MutableMap<RoleId, Role>
) : RoleManager, DefaultManager(), Map<RoleId, Role> by map {
    override fun add(role: Role) {
        if (map.containsKey(role.id)) {
            throw EntityExistsException(role.id)
        }
        map[role.id] = role
    }

    override fun remove(id: RoleId): Role {
        if (map.containsKey(id)) {
            return map.remove(id)!!
        }
        throw EntityNotExistsException(id)
    }
}

internal data class DefaultSpecificationGoalManager @Inject constructor(
    private val map: MutableMap<SpecificationGoalId, SpecificationGoal>
) : SpecificationGoalManager, DefaultManager(), Map<SpecificationGoalId, SpecificationGoal> by map {
    override fun add(goal: SpecificationGoal) {
        if (map.containsKey(goal.id)) {
            throw EntityExistsException(goal.id)
        }
        map[goal.id] = goal
    }

    override fun remove(id: SpecificationGoalId): SpecificationGoal {
        if (map.containsKey(id)) {
            return map.remove(id)!!
        }
        throw EntityNotExistsException(id)
    }
}
