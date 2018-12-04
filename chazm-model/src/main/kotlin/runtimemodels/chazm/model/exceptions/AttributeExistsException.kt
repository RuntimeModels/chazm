package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.AttributeId
import runtimemodels.chazm.model.message.E

class AttributeExistsException(
    val id: AttributeId
) : IllegalArgumentException(E.ENTITY_ALREADY_EXISTS[id.type, id])
