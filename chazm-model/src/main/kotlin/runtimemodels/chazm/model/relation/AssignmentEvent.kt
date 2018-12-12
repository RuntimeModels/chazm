package runtimemodels.chazm.model.relation

import runtimemodels.chazm.api.entity.*
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.api.relation.Assignment
import runtimemodels.chazm.model.event.AbstractEvent
import runtimemodels.chazm.model.event.EventType
import runtimemodels.chazm.model.message.M
import java.util.*
import javax.inject.Inject

/**
 * The [AssignmentEvent] class indicates that there is an update about an [Assignment].
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
open class AssignmentEvent @Inject internal constructor(
    category: EventType,
    assignment: Assignment
) : AbstractEvent(category) {
    /**
     * Returns a [UniqueId] that represents an [Agent].
     *
     * @return a [UniqueId].
     */
    val agentId: AgentId = assignment.agent.id
    /**
     * Returns a [UniqueId] that represents an [Role].
     *
     * @return a [UniqueId].
     */
    val roleId: RoleId = assignment.role.id
    /**
     * Returns a [UniqueId] that represents an [InstanceGoal].
     *
     * @return a [UniqueId].
     */
    val goalId: InstanceGoalId = assignment.goal.id

    override fun equals(other: Any?): Boolean {
        if (other is AssignmentEvent) {
            return (super.equals(other) && agentId == other.agentId && roleId == other.roleId
                && goalId == other.goalId)
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(category, agentId, roleId, goalId)

    override fun toString(): String = M.EVENT_WITH_3_IDS[super.toString(), agentId, roleId, goalId]

    companion object {
        private const val serialVersionUID = -606847454040505425L
    }

}
