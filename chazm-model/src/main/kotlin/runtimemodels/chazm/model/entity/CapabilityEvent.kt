package runtimemodels.chazm.model.entity

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Capability
import runtimemodels.chazm.api.entity.CapabilityId
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.model.event.AbstractEvent
import runtimemodels.chazm.model.event.EventType
import runtimemodels.chazm.model.message.M
import java.util.*
import javax.inject.Inject

/**
 * The [CapabilityEvent] class indicates that there is an update about a [Capability] entity.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
open class CapabilityEvent @Inject internal constructor(
    @Assisted category: EventType,
    @Assisted capability: Capability
) : AbstractEvent(category) {
    /**
     * Returns a [UniqueId] that represents a [Capability].
     *
     * @return a [UniqueId].
     */
    val id: CapabilityId = capability.id

    override fun equals(other: Any?): Boolean {
        if (other is CapabilityEvent) {
            return super.equals(other) && id == other.id
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(category, id)

    override fun toString(): String = M.EVENT_WITH_1_ID[super.toString(), id]

    companion object {
        private const val serialVersionUID = -8208865145150920878L
    }

}
