package runtimemodels.chazm.model.event

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Capability
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.api.relation.Requires
import runtimemodels.chazm.model.message.M
import java.util.*
import javax.inject.Inject

/**
 * The [RequiresEvent] class indicates that there is an update about a [Requires] relation.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
open class RequiresEvent @Inject internal constructor(
    @Assisted category: EventType,
    @Assisted requires: Requires
) : AbstractEvent(category) {
    /**
     * Returns a [UniqueId] that represents a [Role].
     *
     * @return a [UniqueId].
     */
    val roleId: UniqueId<Role> = requires.role.id
    /**
     * Returns a [UniqueId] that represents a [Capability].
     *
     * @return a [UniqueId].
     */
    val capabilityId: UniqueId<Capability> = requires.capability.id

    override fun equals(other: Any?): Boolean {
        if (other is RequiresEvent) {
            return super.equals(other) && roleId == other.roleId && capabilityId == other.capabilityId
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(category, roleId, capabilityId)

    override fun toString(): String = M.EVENT_WITH_2_IDS[super.toString(), roleId, capabilityId]

    companion object {
        private const val serialVersionUID = -8416757195713372958L
    }

}
