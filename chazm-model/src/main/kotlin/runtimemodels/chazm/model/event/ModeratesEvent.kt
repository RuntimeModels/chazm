package runtimemodels.chazm.model.event

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.entity.Pmf
import runtimemodels.chazm.api.id.AttributeId
import runtimemodels.chazm.api.id.PmfId
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.api.relation.Moderates
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
    @Assisted category: EventType,
    @Assisted moderates: Moderates
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