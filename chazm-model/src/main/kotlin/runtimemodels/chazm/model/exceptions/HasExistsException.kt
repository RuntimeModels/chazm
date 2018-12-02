package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.AgentId
import runtimemodels.chazm.api.id.AttributeId
import runtimemodels.chazm.api.relation.Has
import runtimemodels.chazm.model.message.E

class HasExistsException(
    agentId: AgentId,
    attributeId: AttributeId
) : IllegalArgumentException(E.RELATION_ALREADY_EXISTS[Has::class, agentId, attributeId])
