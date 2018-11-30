package runtimemodels.chazm.model.organizations

import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.id.AgentId
import runtimemodels.chazm.api.organization.AgentManager
import runtimemodels.chazm.model.exceptions.AgentExistsException
import runtimemodels.chazm.model.exceptions.AgentNotExistsException
import javax.inject.Inject

internal data class DefaultAgentManager @Inject constructor(
    private val map: MutableMap<AgentId, Agent>
) : AgentManager, Map<AgentId, Agent> by map {
    override fun add(agent: Agent) {
        if (map.containsKey(agent.id)) {
            throw AgentExistsException(agent.id)
        }
        map[agent.id] = agent
    }

    override fun remove(id: AgentId): Agent {
        if (map.containsKey(id)) {
            return map.remove(id)!!
        }
        throw AgentNotExistsException(id)
    }
}
