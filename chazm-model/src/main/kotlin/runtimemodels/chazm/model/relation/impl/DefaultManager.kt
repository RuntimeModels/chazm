package runtimemodels.chazm.model.relation.impl

import runtimemodels.chazm.api.id.*
import runtimemodels.chazm.api.relation.*
import javax.inject.Inject

sealed class DefaultManager

internal data class DefaultAchievesManager @Inject constructor(
    private val map: MutableMap<RoleId, MutableMap<SpecificationGoalId, Achieves>>,
    private val byMap: MutableMap<SpecificationGoalId, MutableMap<RoleId, Achieves>>
) : AchievesManager, DefaultManager() {

    override fun add(achieves: Achieves) {
        if (map[achieves.role.id]?.containsKey(achieves.goal.id) == true) {
            return
        }
        map.computeIfAbsent(achieves.role.id) { mutableMapOf() }[achieves.goal.id] = achieves
        byMap.computeIfAbsent(achieves.goal.id) { mutableMapOf() }[achieves.role.id] = achieves
    }

    override operator fun get(roleId: RoleId, goalId: SpecificationGoalId): Achieves? = map[roleId]?.get(goalId)

    override operator fun get(id: RoleId): Map<SpecificationGoalId, Achieves> = map[id] ?: emptyMap()

    override operator fun get(id: SpecificationGoalId): Map<RoleId, Achieves> = byMap[id] ?: emptyMap()

    override fun remove(roleId: RoleId, goalId: SpecificationGoalId): Achieves? {
        return map[roleId]?.remove(goalId)?.also {
            byMap[goalId]?.apply {
                remove(roleId)?.also { achieves ->
                    if (it === achieves) {
                        return it
                    }
                    throw IllegalStateException("the '$it' and '$achieves' are different instances!")
                } ?: throw IllegalStateException("the 'byMap[$goalId][$roleId]' is missing!")
            } ?: throw IllegalStateException("the 'byMap[$goalId]' is missing!")
        }
    }

    override fun remove(id: RoleId) {
        map.remove(id)
        byMap.forEach { it.value.remove(id) }
    }

    override fun remove(id: SpecificationGoalId) {
        map.forEach { it.value.remove(id) }
        byMap.remove(id)
    }
}

internal data class DefaultAssignmentManager @Inject constructor(
    private val map: MutableMap<AgentId, MutableMap<RoleId, MutableMap<InstanceGoalId, Assignment>>>,
    private val byRoleMap: MutableMap<RoleId, MutableMap<AgentId, MutableMap<InstanceGoalId, Assignment>>>,
    private val byGoalMap: MutableMap<InstanceGoalId, MutableMap<AgentId, MutableMap<RoleId, Assignment>>>
) : AssignmentManager, DefaultManager() {

    override val assignments: Set<Assignment>
        get() = map.flatMap { (_, roleMap) ->
            roleMap.flatMap { (_, goalMap) ->
                goalMap.map { it.value }
            }
        }.toSet()

    override val agentIds: Set<AgentId>
        get() = map.keys

    override val roleIds: Set<RoleId>
        get() = byRoleMap.keys

    override val goalIds: Set<InstanceGoalId>
        get() = byGoalMap.keys

    override fun add(assignment: Assignment) {
        if (map[assignment.agent.id]?.get(assignment.role.id)?.containsKey(assignment.goal.id) == true) {
            return
        }
        map.computeIfAbsent(assignment.agent.id) { mutableMapOf() }.computeIfAbsent(assignment.role.id) { mutableMapOf() }[assignment.goal.id] = assignment
        byRoleMap.computeIfAbsent(assignment.role.id) { mutableMapOf() }.computeIfAbsent(assignment.agent.id) { mutableMapOf() }[assignment.goal.id] = assignment
        byGoalMap.computeIfAbsent(assignment.goal.id) { mutableMapOf() }.computeIfAbsent(assignment.agent.id) { mutableMapOf() }[assignment.role.id] = assignment
    }

    override operator fun get(agentId: AgentId, roleId: RoleId, goalId: InstanceGoalId) = map[agentId]?.get(roleId)?.get(goalId)

    override operator fun get(id: AgentId) = map[id] ?: emptyMap<RoleId, Map<InstanceGoalId, Assignment>>()

    override operator fun get(id: RoleId) = byRoleMap[id] ?: emptyMap<AgentId, Map<InstanceGoalId, Assignment>>()

    override operator fun get(id: InstanceGoalId) = byGoalMap[id] ?: emptyMap<AgentId, Map<RoleId, Assignment>>()

    override fun remove(agentId: AgentId, roleId: RoleId, goalId: InstanceGoalId): Assignment? {
        return map[agentId]?.get(roleId)?.remove(goalId)?.also {
            removeByRoleMap(roleId, agentId, goalId, it)
            removeByGoalMap(goalId, agentId, roleId, it)
            return it
        }
    }

    private fun removeByRoleMap(roleId: RoleId, agentId: AgentId, goalId: InstanceGoalId, it: Assignment) {
        byRoleMap[roleId]?.run {
            get(agentId)?.run {
                remove(goalId)?.let { assignment ->
                    if (it === assignment) {
                        return
                    }
                    throw IllegalStateException("the '$it' and '$assignment' are different instances!")
                } ?: throw IllegalStateException("the 'byRoleMap[$roleId][$agentId][$goalId]' is missing!")
            } ?: throw IllegalStateException("the 'byRoleMap[$roleId][$agentId]' is missing!")
        } ?: throw IllegalStateException("the 'byRoleMap[$roleId]' is missing!")
    }

    private fun removeByGoalMap(goalId: InstanceGoalId, agentId: AgentId, roleId: RoleId, it: Assignment) {
        byGoalMap[goalId]?.run {
            get(agentId)?.run {
                remove(roleId)?.let { assignment ->
                    if (it === assignment) {
                        return
                    }
                    throw IllegalStateException("the '$it' and '$assignment' are different instances!")
                } ?: throw IllegalStateException("the 'byGoalMap[$goalId][$agentId][$roleId]' is missing!")
            } ?: throw IllegalStateException("the 'byGoalMap[$goalId][$agentId]' is missing!")
        } ?: throw IllegalStateException("the 'byGoalMap[$goalId]' is missing!")
    }

    override fun remove(id: AgentId) {
        map.remove(id)
        byRoleMap.forEach { it.value.remove(id) }
        byGoalMap.forEach { it.value.remove(id) }
    }

    override fun remove(id: RoleId) {
        map.forEach { it.value.remove(id) }
        byRoleMap.remove(id)
        byGoalMap.forEach { it.value.forEach { it.value.remove(id) } }
    }

    override fun remove(id: InstanceGoalId) {
        map.forEach { it.value.forEach { it.value.remove(id) } }
        byRoleMap.forEach { it.value.forEach { it.value.remove(id) } }
        byGoalMap.remove(id)
    }
}

internal data class DefaultContainsManager @Inject constructor(
    private val map: MutableMap<RoleId, MutableMap<CharacteristicId, Contains>>,
    private val byMap: MutableMap<CharacteristicId, MutableMap<RoleId, Contains>>
) : ContainsManager, DefaultManager() {

    override fun add(contains: Contains) {
        if (map[contains.role.id]?.containsKey(contains.characteristic.id) == true) {
            return
        }
        map.computeIfAbsent(contains.role.id) { mutableMapOf() }[contains.characteristic.id] = contains
        byMap.computeIfAbsent(contains.characteristic.id) { mutableMapOf() }[contains.role.id] = contains
    }

    override operator fun get(roleId: RoleId, characteristicId: CharacteristicId): Contains? = map[roleId]?.get(characteristicId)

    override operator fun get(id: RoleId): Map<CharacteristicId, Contains> = map[id] ?: emptyMap()

    override operator fun get(id: CharacteristicId): Map<RoleId, Contains> = byMap[id] ?: emptyMap()

    override fun remove(roleId: RoleId, characteristicId: CharacteristicId): Contains? {
        return map[roleId]?.remove(characteristicId)?.also {
            byMap[characteristicId]?.apply {
                remove(roleId)?.also { contains ->
                    if (it === contains) {
                        return it
                    }
                    throw IllegalStateException("the '$it' and '$contains' are different instances!")
                } ?: throw IllegalStateException("the 'byMap[$characteristicId][$roleId]' is missing!")
            } ?: throw IllegalStateException("the 'byMap[$characteristicId]' is missing!")
        }
    }

    override fun remove(id: RoleId) {
        map.remove(id)
        byMap.forEach { it.value.remove(id) }
    }

    override fun remove(id: CharacteristicId) {
        map.forEach { it.value.remove(id) }
        byMap.remove(id)
    }
}

internal data class DefaultHasManager @Inject constructor(
    private val map: MutableMap<AgentId, MutableMap<AttributeId, Has>>,
    private val byMap: MutableMap<AttributeId, MutableMap<AgentId, Has>>
) : HasManager, DefaultManager() {

    override fun add(has: Has) {
        if (map[has.agent.id]?.containsKey(has.attribute.id) == true) {
            return
        }
        map.computeIfAbsent(has.agent.id) { mutableMapOf() }[has.attribute.id] = has
        byMap.computeIfAbsent(has.attribute.id) { mutableMapOf() }[has.agent.id] = has
    }

    override operator fun get(agentId: AgentId, attributeId: AttributeId) = map[agentId]?.get(attributeId)

    override operator fun get(id: AgentId): Map<AttributeId, Has> = map[id] ?: emptyMap()

    override operator fun get(id: AttributeId): Map<AgentId, Has> = byMap[id] ?: emptyMap()

    override fun remove(agentId: AgentId, attributeId: AttributeId): Has? {
        return map[agentId]?.remove(attributeId)?.also {
            byMap[attributeId]?.apply {
                remove(agentId)?.let { has ->
                    if (it === has) {
                        return it
                    }
                    throw IllegalStateException("the '$it' and '$has' are different instances!")
                } ?: throw IllegalStateException("the 'byMap[$attributeId][$agentId]' is missing!")
            } ?: throw IllegalStateException("the 'byMap[$attributeId]' is missing!")
        }
    }

    override fun remove(id: AgentId) {
        map.remove(id)
        byMap.forEach { it.value.remove(id) }
    }

    override fun remove(id: AttributeId) {
        map.forEach { it.value.remove(id) }
        byMap.remove(id)
    }
}

internal data class DefaultModeratesManager @Inject constructor(
    private val map: MutableMap<PmfId, MutableMap<AttributeId, Moderates>>,
    private val byMap: MutableMap<AttributeId, MutableMap<PmfId, Moderates>>
) : ModeratesManager, DefaultManager() {

    override fun add(moderates: Moderates) {
        if (map[moderates.pmf.id]?.containsKey(moderates.attribute.id) == true) {
            return
        }
        map.computeIfAbsent(moderates.pmf.id) { mutableMapOf() }[moderates.attribute.id] = moderates
        byMap.computeIfAbsent(moderates.attribute.id) { mutableMapOf() }[moderates.pmf.id] = moderates
    }

    override operator fun get(pmfId: PmfId, attributeId: AttributeId) = map[pmfId]?.get(attributeId)

    override operator fun get(id: PmfId): Map<AttributeId, Moderates> = map[id] ?: emptyMap()

    override operator fun get(id: AttributeId): Map<PmfId, Moderates> = byMap[id] ?: emptyMap()

    override fun remove(pmfId: PmfId, attributeId: AttributeId): Moderates? {
        return map[pmfId]?.remove(attributeId)?.also {
            byMap[attributeId]?.apply {
                remove(pmfId)?.let { moderates ->
                    if (it === moderates) {
                        return it
                    }
                    throw IllegalStateException("the '$it' and '$moderates' are different instances!")
                } ?: throw IllegalStateException("the 'byMap[$attributeId][$pmfId]' is missing!")
            } ?: throw IllegalStateException("the 'byMap[$attributeId]' is missing!")
        }
    }

    override fun remove(id: PmfId) {
        map.remove(id)
        byMap.forEach { it.value.remove(id) }
    }

    override fun remove(id: AttributeId) {
        map.forEach { it.value.remove(id) }
        byMap.remove(id)
    }
}

internal data class DefaultNeedsManager @Inject constructor(
    private val map: MutableMap<RoleId, MutableMap<AttributeId, Needs>>,
    private val byMap: MutableMap<AttributeId, MutableMap<RoleId, Needs>>
) : NeedsManager, DefaultManager() {

    override fun add(needs: Needs) {
        if (map[needs.role.id]?.containsKey(needs.attribute.id) == true) {
            return
        }
        map.computeIfAbsent(needs.role.id) { mutableMapOf() }[needs.attribute.id] = needs
        byMap.computeIfAbsent(needs.attribute.id) { mutableMapOf() }[needs.role.id] = needs
    }

    override operator fun get(roleId: RoleId, attributeId: AttributeId) = map[roleId]?.get(attributeId)

    override operator fun get(id: RoleId): Map<AttributeId, Needs> = map[id] ?: emptyMap()

    override operator fun get(id: AttributeId): Map<RoleId, Needs> = byMap[id] ?: emptyMap()

    override fun remove(roleId: RoleId, attributeId: AttributeId): Needs? {
        return map[roleId]?.remove(attributeId)?.also {
            byMap[attributeId]?.apply {
                remove(roleId)?.let { needs ->
                    if (it === needs) {
                        return it
                    }
                    throw IllegalStateException("the '$it' and '$needs' are different instances!")
                } ?: throw IllegalStateException("the 'byMap[$attributeId][$roleId]' is missing!")
            } ?: throw IllegalStateException("the 'byMap[$attributeId]' is missing!")
        }
    }

    override fun remove(id: RoleId) {
        map.remove(id)
        byMap.forEach { it.value.remove(id) }
    }

    override fun remove(id: AttributeId) {
        map.forEach { it.value.remove(id) }
        byMap.remove(id)
    }
}

internal data class DefaultPossessesManager @Inject constructor(
    private val map: MutableMap<AgentId, MutableMap<CapabilityId, Possesses>>,
    private val byMap: MutableMap<CapabilityId, MutableMap<AgentId, Possesses>>
) : PossessesManager, DefaultManager() {

    override fun add(possesses: Possesses) {
        if (map[possesses.agent.id]?.containsKey(possesses.capability.id) == true) {
            return
        }
        map.computeIfAbsent(possesses.agent.id) { mutableMapOf() }[possesses.capability.id] = possesses
        byMap.computeIfAbsent(possesses.capability.id) { mutableMapOf() }[possesses.agent.id] = possesses
    }

    override operator fun get(agentId: AgentId, capabilityId: CapabilityId) = map[agentId]?.get(capabilityId)

    override operator fun get(id: AgentId): Map<CapabilityId, Possesses> = map[id] ?: emptyMap()

    override operator fun get(id: CapabilityId): Map<AgentId, Possesses> = byMap[id] ?: emptyMap()

    override fun remove(agentId: AgentId, capabilityId: CapabilityId): Possesses? {
        return map[agentId]?.remove(capabilityId)?.also {
            byMap[capabilityId]?.apply {
                remove(agentId)?.let { possesses ->
                    if (it === possesses) {
                        return it
                    }
                    throw IllegalStateException("the '$it' and '$possesses' are different instances!")
                } ?: throw IllegalStateException("the 'byMap[$capabilityId][$agentId]' is missing!")
            } ?: throw IllegalStateException("the 'byMap[$capabilityId]' is missing!")
        }
    }

    override fun remove(id: AgentId) {
        map.remove(id)
        byMap.forEach { it.value.remove(id) }
    }

    override fun remove(id: CapabilityId) {
        map.forEach { it.value.remove(id) }
        byMap.remove(id)
    }
}

internal data class DefaultRequiresManager @Inject constructor(
    private val map: MutableMap<RoleId, MutableMap<CapabilityId, Requires>>,
    private val byMap: MutableMap<CapabilityId, MutableMap<RoleId, Requires>>
) : RequiresManager, DefaultManager() {

    override fun add(requires: Requires) {
        if (map[requires.role.id]?.containsKey(requires.capability.id) == true) {
            return
        }
        map.computeIfAbsent(requires.role.id) { mutableMapOf() }[requires.capability.id] = requires
        byMap.computeIfAbsent(requires.capability.id) { mutableMapOf() }[requires.role.id] = requires
    }

    override operator fun get(roleId: RoleId, capabilityId: CapabilityId) = map[roleId]?.get(capabilityId)

    override operator fun get(id: RoleId): Map<CapabilityId, Requires> = map[id] ?: emptyMap()

    override operator fun get(id: CapabilityId): Map<RoleId, Requires> = byMap[id] ?: emptyMap()

    override fun remove(roleId: RoleId, capabilityId: CapabilityId): Requires? {
        return map[roleId]?.remove(capabilityId)?.also {
            byMap[capabilityId]?.apply {
                remove(roleId)?.let { requires ->
                    if (it === requires) {
                        return it
                    }
                    throw IllegalStateException("the '$it' and '$requires' are different instances!")
                } ?: throw IllegalStateException("the 'byMap[$capabilityId][$roleId]' is missing!")
            } ?: throw IllegalStateException("the 'byMap[$capabilityId]' is missing!")
        }
    }

    override fun remove(id: RoleId) {
        map.remove(id)
        byMap.forEach { it.value.remove(id) }
    }

    override fun remove(id: CapabilityId) {
        map.forEach { it.value.remove(id) }
        byMap.remove(id)
    }
}

internal data class DefaultUsesManager @Inject constructor(
    private val map: MutableMap<RoleId, MutableMap<PmfId, Uses>>,
    private val byMap: MutableMap<PmfId, MutableMap<RoleId, Uses>>
) : UsesManager, DefaultManager() {

    override fun add(uses: Uses) {
        if (map[uses.role.id]?.containsKey(uses.pmf.id) == true) {
            return
        }
        map.computeIfAbsent(uses.role.id) { mutableMapOf() }[uses.pmf.id] = uses
        byMap.computeIfAbsent(uses.pmf.id) { mutableMapOf() }[uses.role.id] = uses
    }

    override operator fun get(roleId: RoleId, pmfId: PmfId) = map[roleId]?.get(pmfId)

    override operator fun get(id: RoleId): Map<PmfId, Uses> = map[id] ?: emptyMap()

    override operator fun get(id: PmfId): Map<RoleId, Uses> = byMap[id] ?: emptyMap()

    override fun remove(roleId: RoleId, pmfId: PmfId): Uses? {
        return map[roleId]?.remove(pmfId)?.also {
            byMap[pmfId]?.apply {
                remove(roleId)?.let { uses ->
                    if (it === uses) {
                        return it
                    }
                    throw IllegalStateException("the '$it' and '$uses' are different instances!")
                } ?: throw IllegalStateException("the 'byMap[$pmfId][$roleId]' is missing!")
            } ?: throw IllegalStateException("the 'byMap[$pmfId]' is missing!")
        }
    }

    override fun remove(id: RoleId) {
        map.remove(id)
        byMap.forEach { it.value.remove(id) }
    }

    override fun remove(id: PmfId) {
        map.forEach { it.value.remove(id) }
        byMap.remove(id)
    }
}
