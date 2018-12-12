package runtimemodels.chazm.model.relation

import runtimemodels.chazm.api.entity.Pmf
import runtimemodels.chazm.api.entity.PmfId
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.entity.RoleId
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.api.relation.Uses
import runtimemodels.chazm.model.event.AbstractEvent
import runtimemodels.chazm.model.event.EventType
import runtimemodels.chazm.model.message.M
import java.util.*
import javax.inject.Inject

/**
 * The [UsesEvent] class indicates that there is an update about an [Uses] relation.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
open class UsesEvent @Inject internal constructor(
    category: EventType,
    uses: Uses
) : AbstractEvent(category) {
    /**
     * Returns a [UniqueId] that represents a [Role].
     *
     * @return a [UniqueId].
     */
    val roleId: RoleId = uses.role.id
    /**
     * Returns a [UniqueId] that represents a [Pmf].
     *
     * @return a [UniqueId].
     */
    val pmfId: PmfId = uses.pmf.id

    override fun equals(other: Any?): Boolean {
        if (other is UsesEvent) {
            return super.equals(other) && roleId == other.roleId && pmfId == other.pmfId
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(category, roleId, pmfId)

    override fun toString(): String = M.EVENT_WITH_2_IDS[super.toString(), roleId, pmfId]

    companion object {
        private const val serialVersionUID = 5013118334434822084L
    }

}
