package runtimemodels.chazm.api.relation

import runtimemodels.chazm.api.entity.*

/**
 * The [AssignmentManager] interface defines the APIs for managing a set of [Assignment] relations.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface AssignmentManager {
    /**
     * The [Set] of [Assignment]s.
     */
    val assignments: Set<Assignment>

    /**
     * The [Set] of [AgentId]s.
     */
    val agentIds: Set<AgentId>

    /**
     * The [Set] of [RoleId]s.
     */
    val roleIds: Set<RoleId>

    /**
     *The [Set] of [InstanceGoalId]s.
     */
    val goalIds: Set<InstanceGoalId>

    /**
     * Adds an [Assignment] relation among a [Agent], a [Role], and an [InstanceGoal] to this [AssignmentManager].
     *
     * @param assignment the [Assignment] to add.
     */
    fun add(assignment: Assignment)

    /**
     * Returns an [Assignment] relation among a [Agent], a [Role], and an [InstanceGoal] from this [AssignmentManager].
     *
     * @param agentId the [AgentId] that represents the [Agent].
     * @param roleId the [RoleId] that represents the [Role].
     * @param goalId the [InstanceGoalId] that represents the [InstanceGoal].
     * @return the [Assignment] relation if it exists, `null` otherwise.
     */
    operator fun get(agentId: AgentId, roleId: RoleId, goalId: InstanceGoalId): Assignment?

    /**
     * Returns a [Map] of [Role]s and [InstanceGoal]s of an [Agent] from this [AssignmentManager].
     *
     * @param id an [AgentId] that represents the [Agent].
     * @return a [Map] of [Role]s and [InstanceGoal]s.
     */
    operator fun get(id: AgentId): Map<RoleId, Map<InstanceGoalId, Assignment>>

    /**
     * Returns a [Map] of [Agent]s and [InstanceGoal]s of a [Role] from this [AssignmentManager].
     *
     * @param id the [RoleId] that represents the [Role].
     * @return a [Map] of [Agent]s and [InstanceGoal]s.
     */
    operator fun get(id: RoleId): Map<AgentId, Map<InstanceGoalId, Assignment>>

    /**
     * Returns a [Map] of [Agent]s and [Role]s of an [InstanceGoal] from this [AssignmentManager].
     *
     * @param id the [InstanceGoalId] that represents the [InstanceGoal].
     * @return a [Map] of [Agent]s and [Role]s.
     */
    operator fun get(id: InstanceGoalId): Map<AgentId, Map<RoleId, Assignment>>

    /**
     * Removes an [Assignment] relation among a [Agent], a [Role], and an [InstanceGoal] from this [AssignmentManager].
     *
     * @param agentId the [AgentId] that represents the [Agent].
     * @param roleId the [RoleId] that represents the [Role].
     * @param goalId the [InstanceGoalId] that represents the [InstanceGoal].
     * @return the [Assignment] that was removed, `null` otherwise.
     */
    fun remove(agentId: AgentId, roleId: RoleId, goalId: InstanceGoalId): Assignment?

    /**
     * Removes all [Assignment] relations that are associated to an [Agent] from this [AssignmentManager].
     *
     * @param id the [AgentId] that represents the [Agent].
     */
    fun remove(id: AgentId)

    /**
     * Removes all [Assignment] relations that are associated to a [Role] from this [AssignmentManager].
     *
     * @param id the [RoleId] that represents the [Role].
     */
    fun remove(id: RoleId)

    /**
     * Removes all [Assignment] relations that are associated to an [InstanceGoal] from this [AssignmentManager].
     *
     * @param id the [InstanceGoalId] that represents the [InstanceGoal].
     */
    fun remove(id: InstanceGoalId)
}
