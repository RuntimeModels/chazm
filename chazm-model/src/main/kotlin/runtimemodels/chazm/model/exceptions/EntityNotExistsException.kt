package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.model.message.E

class EntityNotExistsException(
    val id: UniqueId<*>
) : IllegalArgumentException(E.ENTITY_DOES_NOT_EXISTS[id.type, id])
