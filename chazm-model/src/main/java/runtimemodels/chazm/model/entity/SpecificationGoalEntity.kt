package runtimemodels.chazm.model.entity

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.SpecificationGoal
import runtimemodels.chazm.api.id.UniqueId

import javax.inject.Inject

internal open class SpecificationGoalEntity @Inject constructor(
    @Assisted id: UniqueId<SpecificationGoal>
) : AbstractEntity<SpecificationGoal>(id), SpecificationGoal {

    override fun equals(other: Any?): Boolean {
        if (other is SpecificationGoal) {
            return super.equals(other)
        }
        return false
    }

    override fun hashCode(): Int = super.hashCode()

    override fun toString(): String = super.toString()

}
