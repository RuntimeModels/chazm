package runtimemodels.chazm.model.entity

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.id.AgentId
import javax.inject.Inject

internal data class AgentEntity @Inject constructor(
    @Assisted override val id: AgentId
) : Agent {
    internal data class DefaultContactInfo(
        private val map: MutableMap<Any, Any> = mutableMapOf()
    ) : Agent.ContactInfo, Map<Any, Any> by map {
        override fun add(key: Any, value: Any) {
            map[key] = value
        }

        override fun remove(key: Any) {
            map.remove(key)
        }
    }

    override val contactInfo: Agent.ContactInfo = DefaultContactInfo()
}
