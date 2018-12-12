package runtimemodels.chazm.model.entity

import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.entity.AgentId
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.model.event.AbstractEvent
import runtimemodels.chazm.model.event.EventType
import runtimemodels.chazm.model.message.M
import java.util.*
import javax.inject.Inject

/**
 * The [AgentEvent] class indicates that there is an update about an [Agent] entity.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
open class AgentEvent @Inject internal constructor(
    category: EventType,
    agent: Agent
) : AbstractEvent(category) {
    /**
     * Returns a [UniqueId] that represents a [Agent].
     *
     * @return a [UniqueId].
     */
    val id: AgentId = agent.id

    override fun equals(other: Any?): Boolean {
        if (other is AgentEvent) {
            return super.equals(other) && id == other.id
        }
        return false
    }

    override fun hashCode(): Int = Objects.hash(category, id)

    override fun toString(): String = M.EVENT_WITH_1_ID[super.toString(), id]

    companion object {
        private const val serialVersionUID = -1802353103746617813L
    }

}
