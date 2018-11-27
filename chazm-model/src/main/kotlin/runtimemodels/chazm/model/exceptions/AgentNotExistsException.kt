package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.AgentId
import runtimemodels.chazm.model.message.E

class AgentNotExistsException(
    val id: AgentId
) : IllegalArgumentException(E.ENTITY_DOES_NOT_EXISTS[id.type.simpleName, id])
