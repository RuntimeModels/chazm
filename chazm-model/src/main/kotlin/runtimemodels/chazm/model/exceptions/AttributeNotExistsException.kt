package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.AttributeId
import runtimemodels.chazm.model.message.E

class AttributeNotExistsException(
    val id: AttributeId
) : IllegalArgumentException(E.ENTITY_DOES_NOT_EXISTS[id.type, id])
