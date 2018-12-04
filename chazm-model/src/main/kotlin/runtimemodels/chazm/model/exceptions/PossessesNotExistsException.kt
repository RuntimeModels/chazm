package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.AgentId
import runtimemodels.chazm.api.id.CapabilityId
import runtimemodels.chazm.api.relation.Possesses
import runtimemodels.chazm.model.message.E

class PossessesNotExistsException(
    agentId: AgentId,
    capabilityId: CapabilityId
) : IllegalArgumentException(E.RELATION_DOES_NOT_EXISTS[Possesses::class, agentId, capabilityId])
