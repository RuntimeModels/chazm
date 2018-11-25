package runtimemodels.chazm.model

import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.entity.InstanceGoal
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.relation.Assignment

typealias Assignments = MutableSet<Assignment>
typealias AgentAssignments = MutableMap<Agent, Assignments>
typealias RoleAssignments = MutableMap<Role, Assignments>
typealias GoalAssignments = MutableMap<InstanceGoal, Assignments>

@Suppress("unused")
class AssignmentSet {
    private val assignments: Assignments = mutableSetOf()
    private val assignmentsByAgent: AgentAssignments = mutableMapOf()
    private val assignmentsByRole: RoleAssignments = mutableMapOf()
    private val assignmentsByGoal: GoalAssignments = mutableMapOf()

    @Synchronized
    fun addAssignment(assignment: Assignment) {
        assignments.add(assignment)
        assignmentsByAgent.computeIfAbsent(assignment.agent) { mutableSetOf() }.add(assignment)
        assignmentsByRole.computeIfAbsent(assignment.role) { mutableSetOf() }.add(assignment)
        assignmentsByGoal.computeIfAbsent(assignment.goal) { mutableSetOf() }.add(assignment)
    }

    @Synchronized
    fun addAssignments(assignments: Set<Assignment>) = assignments.forEach(::addAssignment)


    @Synchronized
    fun removeAssignment(assignment: Assignment) {
        assignments.remove(assignment)
        assignmentsByAgent[assignment.agent]?.remove(assignment)
        assignmentsByRole[assignment.role]?.remove(assignment)
        assignmentsByGoal[assignment.goal]?.remove(assignment)
    }

    @Synchronized
    fun removeAssignments(assignments: Set<Assignment>) = assignments.forEach(::removeAssignment)

    @Synchronized
    fun getAssignments(): Set<Assignment> = assignments.toSet()

    @Synchronized
    fun getAssignmentsByAgent(agent: Agent): Set<Assignment> = assignmentsByAgent.getOrDefault(agent, mutableSetOf()).toSet()

    @Synchronized
    fun getAssignmentsByRole(role: Role): Set<Assignment> = assignmentsByRole.getOrDefault(role, mutableSetOf()).toSet()

    @Synchronized
    fun getAssignmentsByGoal(goal: InstanceGoal): Set<Assignment> = assignmentsByGoal.getOrDefault(goal, mutableSetOf()).toSet()

}
