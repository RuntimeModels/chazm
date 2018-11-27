package runtimemodels.chazm.model.relation

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.entity.InstanceGoal
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.relation.Assignment
import runtimemodels.chazm.model.id.AssignmentId
import runtimemodels.chazm.model.message.M
import javax.inject.Inject

internal open class AssignmentRelation @Inject constructor(
    @param:Assisted final override val agent: Agent,
    @param:Assisted final override val role: Role,
    @param:Assisted final override val goal: InstanceGoal
) : Assignment {
    override val id: AssignmentId = AssignmentId(agent.id, role.id, goal.id)

    override fun equals(other: Any?): Boolean {
        if (other is Assignment) {
            return id == other.id
        }
        return false
    }

    override fun hashCode(): Int = id.hashCode()

    override fun toString(): String = M.ASSIGNMENT[agent.id, role.id, goal.id]
}
