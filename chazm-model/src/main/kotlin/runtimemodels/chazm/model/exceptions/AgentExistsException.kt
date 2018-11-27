package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.AgentId
import runtimemodels.chazm.model.message.E

class AgentExistsException(
    val id: AgentId
) : IllegalArgumentException(E.ENTITY_ALREADY_EXISTS[id.type.simpleName, id])
