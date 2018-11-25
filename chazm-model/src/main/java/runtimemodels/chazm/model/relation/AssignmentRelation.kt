package runtimemodels.chazm.model.relation

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.entity.InstanceGoal
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.api.relation.Assignment
import runtimemodels.chazm.model.id.AbstractId
import runtimemodels.chazm.model.message.M
import java.util.*
import javax.inject.Inject

internal open class AssignmentRelation @Inject constructor(
    @param:Assisted private val agent: Agent,
    @param:Assisted private val role: Role,
    @param:Assisted private val goal: InstanceGoal
) : Assignment {
    private val id: UniqueId<Assignment> = AssignmentId(agent.id, role.id, goal.id)

    override fun getId(): UniqueId<Assignment> = id

    override fun getAgent(): Agent = agent

    override fun getRole(): Role = role

    override fun getGoal(): InstanceGoal = goal

    override fun equals(other: Any?): Boolean {
        if (other is Assignment) {
            return id == other.id
        }
        return false
    }

    override fun hashCode(): Int = id.hashCode()

    override fun toString(): String = M.ASSIGNMENT[agent.id, role.id, goal.id]

}

/**
 * The [AssignmentId] extends the [AbstractId] by using three [UniqueId]s: the [UniqueId] of an [Agent], the
 * [UniqueId] of a [Role], and the [UniqueId] of an [InstanceGoal].
 *
 * @author Christopher Zhong
 * @see UniqueId
 *
 * @since 4.0
 */
class AssignmentId
/**
 * Constructs a new instance of [Assignment].
 *
 * @param agentId the [UniqueId] that represents an [Agent].
 * @param roleId  the [UniqueId] that represents a [Role].
 * @param goalId  the [UniqueId] that represents an [InstanceGoal].
 */
(
    /**
     * The [UniqueId] that represents an [Agent].
     */
    /**
     * Returns the [UniqueId] that represents an [Agent].
     *
     * @return the [UniqueId] that represents an [Agent].
     */
    private val agentId: UniqueId<Agent>,
    /**
     * The [UniqueId] that represents a [Role].
     */
    /**
     * Returns the [UniqueId] that represents a [Role].
     *
     * @return the [UniqueId] that represents a [Role].
     */
    private val roleId: UniqueId<Role>,
    /**
     * The [UniqueId] that represents an [InstanceGoal].
     */
    /**
     * Returns the [UniqueId] that represents an [InstanceGoal].
     *
     * @return the [UniqueId] that represents an [InstanceGoal].
     */
    private val goalId: UniqueId<InstanceGoal>) : AbstractId<Assignment>(Assignment::class.java) {

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
