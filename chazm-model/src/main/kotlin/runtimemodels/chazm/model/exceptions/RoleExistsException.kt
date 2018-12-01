package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.model.message.E

class RoleExistsException(
    val id: RoleId
) : IllegalArgumentException(E.ENTITY_ALREADY_EXISTS[id.type.simpleName, id])
