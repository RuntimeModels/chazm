package runtimemodels.chazm.model.entity

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.SpecificationGoal
import runtimemodels.chazm.api.id.SpecificationGoalId
import javax.inject.Inject

internal open class SpecificationGoalEntity @Inject constructor(
    @Assisted override val id: SpecificationGoalId
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
