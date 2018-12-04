package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.CapabilityId
import runtimemodels.chazm.model.message.E

class CapabilityExistsException(
    val id: CapabilityId
) : IllegalArgumentException(E.ENTITY_ALREADY_EXISTS[id.type, id])
