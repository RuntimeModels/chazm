package runtimemodels.chazm.model.entity

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.SpecificationGoal
import runtimemodels.chazm.api.entity.SpecificationGoalId
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.model.event.AbstractEvent
import runtimemodels.chazm.model.event.EventType
import runtimemodels.chazm.model.message.M
import java.util.*
import javax.inject.Inject

/**
 * The [SpecificationGoalEvent] class indicates that there is an update about an [SpecificationGoal].
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
open class SpecificationGoalEvent @Inject internal constructor(
    @Assisted category: EventType,
    @Assisted goal: SpecificationGoal
) : AbstractEvent(category) {
    /**
     * Returns a [UniqueId] that represents a [SpecificationGoal].
     *
     * @return a [UniqueId].
     */
    val id: SpecificationGoalId = goal.id

    override fun equals(other: Any?): Boolean {
        if (other is SpecificationGoalEvent) {
            return super.equals(other) && id == other.id
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(category, id)

    override fun toString(): String = M.EVENT_WITH_1_ID[super.toString(), id]

    companion object {
        private const val serialVersionUID = 602095857291387756L
    }

}
