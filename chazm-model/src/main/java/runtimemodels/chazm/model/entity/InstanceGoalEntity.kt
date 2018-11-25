package runtimemodels.chazm.model.entity

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.InstanceGoal
import runtimemodels.chazm.api.entity.SpecificationGoal
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.model.message.M
import java.util.*
import javax.inject.Inject

internal open class InstanceGoalEntity @Inject constructor(
    @Assisted id: UniqueId<InstanceGoal>,
    @param:Assisted private val goal: SpecificationGoal,
    @param:Assisted private val parameter: InstanceGoal.Parameter
) : AbstractEntity<InstanceGoal>(id), InstanceGoal {
    override fun equals(other: Any?): Boolean {
        if (other is InstanceGoal) {
            return super.equals(other) && goal == other.goal
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(super.hashCode(), goal)

    override fun toString(): String = M.ENTITY_2.get(super.toString(), goal, parameter)!!

    override fun getGoal(): SpecificationGoal = goal

    override fun getParameter(): InstanceGoal.Parameter = parameter
}
