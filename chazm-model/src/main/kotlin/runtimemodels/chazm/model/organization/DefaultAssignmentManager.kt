package runtimemodels.chazm.model.organization

import runtimemodels.chazm.api.id.AgentId
import runtimemodels.chazm.api.id.InstanceGoalId
import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.organization.AssignmentManager
import runtimemodels.chazm.api.relation.Assignment
import javax.inject.Inject

internal data class DefaultAssignmentManager @Inject constructor(
    private val map: MutableMap<AgentId, MutableMap<RoleId, MutableMap<InstanceGoalId, Assignment>>>,
    private val byRoleMap: MutableMap<RoleId, MutableMap<AgentId, MutableMap<InstanceGoalId, Assignment>>>,
    private val byGoalMap: MutableMap<InstanceGoalId, MutableMap<AgentId, MutableMap<RoleId, Assignment>>>
) : AssignmentManager {

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
