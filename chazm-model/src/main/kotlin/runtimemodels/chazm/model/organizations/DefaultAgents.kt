package runtimemodels.chazm.model.organizations

import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.id.AgentId
import runtimemodels.chazm.api.organization.Agents
import runtimemodels.chazm.model.exceptions.AgentExistsException
import runtimemodels.chazm.model.exceptions.AgentNotExistsException
import javax.inject.Inject

internal class DefaultAgents @Inject constructor(
    override val map: MutableMap<AgentId, Agent>
) : Agents, Map<AgentId, Agent> by map {
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
