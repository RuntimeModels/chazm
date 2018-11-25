package runtimemodels.chazm.model.event

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.entity.SpecificationGoal
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.api.relation.Achieves
import runtimemodels.chazm.model.message.M
import java.util.*
import javax.inject.Inject

/**
 * The [AchievesEvent] class indicates that there is an update about an [Achieves] relation.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
open class AchievesEvent @Inject internal constructor(
    @Assisted category: EventType,
    @Assisted achieves: Achieves
) : AbstractEvent(category) {
    /**
     * Returns a [UniqueId] that represents a [Role].
     *
     * @return a [UniqueId].
     */
    val roleId: UniqueId<Role> = achieves.role.id
    /**
     * Returns a [UniqueId] that represents a [SpecificationGoal].
     *
     * @return a [UniqueId].
     */
    val goalId: UniqueId<SpecificationGoal> = achieves.goal.id

    override fun equals(other: Any?): Boolean {
        if (other is AchievesEvent) {
            return super.equals(other) && roleId == other.roleId && goalId == other.goalId
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(category, roleId, goalId)

    override fun toString(): String = M.EVENT_WITH_2_IDS[super.toString(), roleId, goalId]

    companion object {
        private const val serialVersionUID = -6277684926586766989L
    }

}