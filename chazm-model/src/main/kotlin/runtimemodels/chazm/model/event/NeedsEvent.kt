package runtimemodels.chazm.model.event

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.entity.AttributeId
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.entity.RoleId
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.api.relation.Needs
import runtimemodels.chazm.model.message.M
import java.util.*
import javax.inject.Inject

/**
 * The [NeedsEvent] class indicates that there is an update about a [Needs] relation.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
open class NeedsEvent @Inject internal constructor(
    @Assisted category: EventType,
    @Assisted needs: Needs
) : AbstractEvent(category) {
    /**
     * Returns a [UniqueId] that represents a [Role].
     *
     * @return a [UniqueId].
     */
    val roleId: RoleId = needs.role.id
    /**
     * Returns a [UniqueId] that represents a [Attribute].
     *
     * @return a [UniqueId].
     */
    val attributeId: AttributeId = needs.attribute.id

    override fun equals(other: Any?): Boolean {
        if (other is NeedsEvent) {
            return super.equals(other) && roleId == other.roleId && attributeId == other.attributeId
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(category, roleId, attributeId)

    override fun toString(): String = M.EVENT_WITH_2_IDS[super.toString(), roleId, attributeId]

    companion object {
        private const val serialVersionUID = -2368504360543452399L
    }

}
