package runtimemodels.chazm.model.entity

import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.entity.AttributeId
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.model.event.AbstractEvent
import runtimemodels.chazm.model.event.EventType
import runtimemodels.chazm.model.message.M
import java.util.*
import javax.inject.Inject

/**
 * The [AttributeEvent] class indicates that there is an update about an [Attribute] entity.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
open class AttributeEvent @Inject internal constructor(
    category: EventType,
    attribute: Attribute
) : AbstractEvent(category) {
    /**
     * Returns a [UniqueId] that represents a [Attribute].
     *
     * @return a [UniqueId].
     */
    val id: AttributeId = attribute.id

    override fun equals(other: Any?): Boolean {
        if (other is AttributeEvent) {
            return super.equals(other) && id == other.id
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(category, id)

    override fun toString(): String = M.EVENT_WITH_1_ID[super.toString(), id]

    companion object {
        private const val serialVersionUID = -2314619272565535715L
    }

}
