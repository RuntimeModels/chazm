package runtimemodels.chazm.model.event

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.model.message.M
import java.util.*
import javax.inject.Inject

/**
 * The [RoleEvent] class indicates that there is an update about a [Role] entity.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
open class RoleEvent @Inject internal constructor(
    @Assisted category: EventType,
    @Assisted role: Role
) : AbstractEvent(category) {
    /**
     * Returns a [UniqueId] that represents a [Role].
     *
     * @return a [UniqueId].
     */
    val id: RoleId = role.id

    override fun equals(other: Any?): Boolean {
        if (other is RoleEvent) {
            return super.equals(other) && id == other.id
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(category, id)

    override fun toString(): String = M.EVENT_WITH_1_ID[super.toString(), id]

    companion object {
        private const val serialVersionUID = -5258695088891301883L
    }

}
