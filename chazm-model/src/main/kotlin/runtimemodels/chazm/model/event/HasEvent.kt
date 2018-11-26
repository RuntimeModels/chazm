package runtimemodels.chazm.model.event

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.api.relation.Has
import runtimemodels.chazm.model.message.M
import java.util.*
import javax.inject.Inject

/**
 * The [HasEvent] class indicates that there is an update about a [Has] relation.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
open class HasEvent @Inject internal constructor(
    @Assisted category: EventType,
    @Assisted has: Has
) : AbstractEvent(category) {
    /**
     * Returns a [UniqueId] that represents an [Agent].
     *
     * @return a [UniqueId].
     */
    val agentId: UniqueId<Agent> = has.agent.id
    /**
     * Returns a [UniqueId] that represents an [Attribute].
     *
     * @return a [UniqueId].
     */
    val attributeId: UniqueId<Attribute> = has.attribute.id
    /**
     * Returns a `double` value.
     *
     * @return a `double` value
     */
    val value: Double = has.value

    override fun equals(other: Any?): Boolean {
        if (other is HasEvent) {
            return super.equals(other) && agentId == other.agentId && attributeId == other.attributeId
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(category, agentId, attributeId)

    override fun toString(): String = M.EVENT_WITH_2_IDS_AND_VALUE[super.toString(), agentId, attributeId, value]

    companion object {
        private const val serialVersionUID = 4516793115062475658L
    }

}
