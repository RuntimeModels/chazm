package runtimemodels.chazm.model.id

import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.id.AgentId

data class DefaultAgentId(
    private val id: String
) : AbstractId<Agent>(Agent::class.java), AgentId
