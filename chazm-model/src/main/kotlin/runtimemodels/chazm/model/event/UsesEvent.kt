package runtimemodels.chazm.model.event

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Pmf
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.api.relation.Uses
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
    @Assisted category: EventType,
    @Assisted uses: Uses
) : AbstractEvent(category) {
    /**
     * Returns a [UniqueId] that represents a [Role].
     *
     * @return a [UniqueId].
     */
    val roleId: UniqueId<Role> = uses.role.id
    /**
     * Returns a [UniqueId] that represents a [Pmf].
     *
     * @return a [UniqueId].
     */
    val pmfId: UniqueId<Pmf> = uses.pmf.id

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
