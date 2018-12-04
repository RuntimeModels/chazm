package runtimemodels.chazm.model.relation

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.entity.SpecificationGoal
import runtimemodels.chazm.api.relation.Achieves
import runtimemodels.chazm.model.message.M
import java.util.*
import javax.inject.Inject

internal open class AchievesRelation @Inject constructor(
    @param:Assisted override val role: Role,
    @param:Assisted override val goal: SpecificationGoal
) : Achieves {
    override fun equals(other: Any?): Boolean {
        if (other is Achieves) {
            return role == other.role && goal == other.goal
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(role, goal)

    override fun toString(): String = M.RELATION[role.id, goal.id]
}
