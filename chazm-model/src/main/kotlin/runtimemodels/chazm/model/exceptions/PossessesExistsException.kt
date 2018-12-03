package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.AgentId
import runtimemodels.chazm.api.id.CapabilityId
import runtimemodels.chazm.api.relation.Possesses
import runtimemodels.chazm.model.message.E

class PossessesExistsException(
    agentId: AgentId,
    capabilityId: CapabilityId
) : IllegalArgumentException(E.RELATION_ALREADY_EXISTS[Possesses::class, agentId, capabilityId])
