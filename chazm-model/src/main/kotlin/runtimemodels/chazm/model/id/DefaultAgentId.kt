package runtimemodels.chazm.model.id

import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.id.AgentId
import javax.inject.Inject

internal data class DefaultAgentId @Inject constructor(
    private val id: String
) : AbstractId<Agent>(Agent::class), AgentId
