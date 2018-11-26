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
    @Assisted parameters: Map<Any, Any>
) : AbstractEntity<InstanceGoal>(id), InstanceGoal {
    private val parameters: MutableMap<Any, Any> = mutableMapOf()

    init {
        this.parameters.putAll(parameters)
    }

    override fun equals(other: Any?): Boolean {
        if (other is InstanceGoal) {
            return super.equals(other) && goal == other.goal
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(super.hashCode(), goal)

    override fun toString(): String = M.ENTITY_2[super.toString(), goal, parameters]

    override fun getGoal(): SpecificationGoal = goal

    override fun getParameters(): Map<Any, Any> = parameters.toMap()

    override fun addParameter(key: Any, value: Any) {
        parameters[key] = value
    }

    override fun removeParameter(key: Any) {
        parameters.remove(key)
    }
}
