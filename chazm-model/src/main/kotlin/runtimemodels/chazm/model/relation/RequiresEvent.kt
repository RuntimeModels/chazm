package runtimemodels.chazm.model.relation

import runtimemodels.chazm.api.entity.Capability
import runtimemodels.chazm.api.entity.CapabilityId
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.entity.RoleId
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.api.relation.Requires
import runtimemodels.chazm.model.event.AbstractEvent
import runtimemodels.chazm.model.event.EventType
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
    category: EventType,
    requires: Requires
) : AbstractEvent(category) {
    /**
     * Returns a [UniqueId] that represents a [Role].
     *
     * @return a [UniqueId].
     */
    val roleId: RoleId = requires.role.id
    /**
     * Returns a [UniqueId] that represents a [Capability].
     *
     * @return a [UniqueId].
     */
    val capabilityId: CapabilityId = requires.capability.id

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
