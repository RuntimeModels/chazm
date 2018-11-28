package runtimemodels.chazm.model.id

import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.entity.InstanceGoal
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.id.AgentId
import runtimemodels.chazm.api.id.InstanceGoalId
import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.api.relation.Assignment
import runtimemodels.chazm.model.message.M
import java.util.*

/**
 * The [AssignmentId] extends the [AbstractId] by using three [UniqueId]s: the [UniqueId] of an [Agent], the
 * [UniqueId] of a [Role], and the [UniqueId] of an [InstanceGoal].
 *
 * @constructor Constructs a new instance of [Assignment].
 * @property agentId the [UniqueId] that represents an [Agent].
 * @property roleId  the [UniqueId] that represents a [Role].
 * @property goalId  the [UniqueId] that represents an [InstanceGoal].
 *
 * @author Christopher Zhong
 * @see UniqueId
 * @since 4.0
 */
class AssignmentId(
    private val agentId: AgentId,
    private val roleId: RoleId,
    private val goalId: InstanceGoalId
) : AbstractId<Assignment>(Assignment::class.java) {

    override fun equals(other: Any?): Boolean {
        if (other is AssignmentId) {
            return agentId == other.agentId && roleId == other.roleId && goalId == other.goalId
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(super.hashCode(), agentId, roleId, goalId)

    override fun toString(): String = M.ASSIGNMENT_ID[javaClass.simpleName, agentId, roleId, goalId]

    companion object {
        /**
         * Serial version ID.
         */
        private const val serialVersionUID = 7696865567559985410L
    }
}
