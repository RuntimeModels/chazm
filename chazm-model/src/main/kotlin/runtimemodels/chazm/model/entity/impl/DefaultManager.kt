package runtimemodels.chazm.model.entity.impl

import runtimemodels.chazm.api.entity.*
import javax.inject.Inject

internal sealed class DefaultManager

internal data class DefaultAgentManager @Inject constructor(
    private val map: MutableMap<AgentId, Agent>
) : AgentManager, DefaultManager(), Map<AgentId, Agent> by map {
    override fun add(agent: Agent) {
        map.putIfAbsent(agent.id, agent)
    }

    override fun remove(id: AgentId): Agent? {
        return map.remove(id)
    }
}

internal data class DefaultAttributeManager @Inject constructor(
    private val map: MutableMap<AttributeId, Attribute>
) : AttributeManager, DefaultManager(), Map<AttributeId, Attribute> by map {
    override fun add(attribute: Attribute) {
        map.putIfAbsent(attribute.id, attribute)
    }

    override fun remove(id: AttributeId): Attribute? {
        return map.remove(id)
    }
}

internal data class DefaultCapabilityManager @Inject constructor(
    private val map: MutableMap<CapabilityId, Capability>
) : CapabilityManager, DefaultManager(), Map<CapabilityId, Capability> by map {
    override fun add(capability: Capability) {
        map.putIfAbsent(capability.id, capability)
    }

    override fun remove(id: CapabilityId): Capability? {
        return map.remove(id)
    }
}

internal data class DefaultCharacteristicManager @Inject constructor(
    private val map: MutableMap<CharacteristicId, Characteristic>
) : CharacteristicManager, DefaultManager(), Map<CharacteristicId, Characteristic> by map {
    override fun add(characteristic: Characteristic) {
        map.putIfAbsent(characteristic.id, characteristic)
    }

    override fun remove(id: CharacteristicId): Characteristic? {
        return map.remove(id)
    }
}

internal data class DefaultInstanceGoalManager @Inject constructor(
    private val map: MutableMap<InstanceGoalId, InstanceGoal>,
    private val byMap: MutableMap<SpecificationGoalId, MutableMap<InstanceGoalId, InstanceGoal>>
) : InstanceGoalManager, DefaultManager(), Map<InstanceGoalId, InstanceGoal> by map {
    override fun add(goal: InstanceGoal) {
        if (map.putIfAbsent(goal.id, goal) == null) {
            byMap.computeIfAbsent(goal.goal.id) { mutableMapOf() }[goal.id] = goal
        }
    }

    override fun remove(id: InstanceGoalId): InstanceGoal? {
        return map.remove(id)?.also {
            byMap[it.goal.id]?.apply {
                remove(id)?.apply {
                    if (it == this) {
                        return it
                    }
                    throw IllegalStateException("the goal '$this' obtained from 'byMap'[${this.goal.id}][$id] is different from '$it'")
                }
                    ?: throw IllegalStateException("the key '$id' is missing from the map return by 'byMap[${it.goal.id}]'")
            } ?: throw IllegalStateException("the key '${it.goal.id}' is missing from map 'byMap'")
        }
    }

    override fun remove(id: SpecificationGoalId) {
        byMap.remove(id)?.forEach { map.remove(it.key) }
    }
}

internal data class DefaultPmfManager @Inject constructor(
    private val map: MutableMap<PmfId, Pmf>
) : PmfManager, DefaultManager(), Map<PmfId, Pmf> by map {
    override fun add(pmf: Pmf) {
        map.putIfAbsent(pmf.id, pmf)
    }

    override fun remove(id: PmfId): Pmf? {
        return map.remove(id)
    }
}

internal data class DefaultPolicyManager @Inject constructor(
    private val map: MutableMap<PolicyId, Policy>
) : PolicyManager, DefaultManager(), Map<PolicyId, Policy> by map {
    override fun add(policy: Policy) {
        map.putIfAbsent(policy.id, policy)
    }

    override fun remove(id: PolicyId): Policy? {
        return map.remove(id)
    }
}

internal data class DefaultRoleManager @Inject constructor(
    private val map: MutableMap<RoleId, Role>
) : RoleManager, DefaultManager(), Map<RoleId, Role> by map {
    override fun add(role: Role) {
        map.putIfAbsent(role.id, role)
    }

    override fun remove(id: RoleId): Role? {
        return map.remove(id)
    }
}

internal data class DefaultSpecificationGoalManager @Inject constructor(
    private val map: MutableMap<SpecificationGoalId, SpecificationGoal>
) : SpecificationGoalManager, DefaultManager(), Map<SpecificationGoalId, SpecificationGoal> by map {
    override fun add(goal: SpecificationGoal) {
        map.putIfAbsent(goal.id, goal)
    }

    override fun remove(id: SpecificationGoalId): SpecificationGoal? {
        return map.remove(id)
    }
}
