package runtimemodels.chazm.model.event

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.InstanceGoal
import runtimemodels.chazm.api.entity.SpecificationGoal
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.model.message.M
import java.util.*
import javax.inject.Inject

/**
 * The [InstanceGoalEvent] class indicates that there is an update about an [InstanceGoal] entity.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
open class InstanceGoalEvent @Inject internal constructor(
    @Assisted category: EventType,
    @Assisted goal: InstanceGoal
) : AbstractEvent(category) {
    /**
     * Returns a [UniqueId] that represents a [InstanceGoal].
     *
     * @return a [UniqueId].
     */
    val id: UniqueId<InstanceGoal> = goal.id
    /**
     * Returns a [UniqueId] that represents a [SpecificationGoal].
     *
     * @return a [UniqueId].
     */
    val specificationGoalId: UniqueId<SpecificationGoal> = goal.goal.id
    /**
     * Returns a [InstanceGoal.Parameter].
     *
     * @return a [InstanceGoal.Parameter].
     */
    val parameter: InstanceGoal.Parameter = goal.parameter

    override fun equals(other: Any?): Boolean {
        if (other is InstanceGoalEvent) {
            return super.equals(other) && id == other.id && specificationGoalId == other.specificationGoalId
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(category, id, specificationGoalId)

    override fun toString(): String = M.EVENT_WITH_2_IDS_AND_VALUE[super.toString(), id, specificationGoalId, parameter]

    companion object {
        private const val serialVersionUID = -7178745959528744216L
    }

}
