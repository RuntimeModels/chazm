package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.model.message.E

class RoleNotExistsException(
    val id: RoleId
) : IllegalArgumentException(E.ENTITY_DOES_NOT_EXISTS[id.type, id])
