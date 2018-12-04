package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.model.message.E

class EntityExistsException(
    val id: UniqueId<*>
) : IllegalArgumentException(E.ENTITY_ALREADY_EXISTS[id.type, id])
