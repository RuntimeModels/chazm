package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.CapabilityId
import runtimemodels.chazm.model.message.E

class CapabilityNotExistsException(
    val id: CapabilityId
) : IllegalArgumentException(E.ENTITY_DOES_NOT_EXISTS[id.type.simpleName, id])
