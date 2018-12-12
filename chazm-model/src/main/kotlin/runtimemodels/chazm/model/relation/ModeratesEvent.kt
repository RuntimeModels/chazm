package runtimemodels.chazm.model.relation

import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.entity.AttributeId
import runtimemodels.chazm.api.entity.Pmf
import runtimemodels.chazm.api.entity.PmfId
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.api.relation.Moderates
import runtimemodels.chazm.model.event.AbstractEvent
import runtimemodels.chazm.model.event.EventType
import runtimemodels.chazm.model.message.M
import java.util.*
import javax.inject.Inject

/**
 * The [ModeratesEvent] class indicates that there is an update about a [Moderates] relation.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
open class ModeratesEvent @Inject internal constructor(
    category: EventType,
    moderates: Moderates
) : AbstractEvent(category) {
    /**
     * Returns a [UniqueId] that represents a [Pmf].
     *
     * @return a [UniqueId].
     */
    val pmfId: PmfId = moderates.pmf.id
    /**
     * Returns a [UniqueId] that represents an [Attribute].
     *
     * @return a [UniqueId].
     */
    val attributeId: AttributeId = moderates.attribute.id

    override fun equals(other: Any?): Boolean {
        if (other is ModeratesEvent) {
            return super.equals(other) && pmfId == other.pmfId && attributeId == other.attributeId
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(category, pmfId, attributeId)

    override fun toString(): String = M.EVENT_WITH_2_IDS[super.toString(), pmfId, attributeId]

    companion object {
        private const val serialVersionUID = 273935856408749575L
    }

}
