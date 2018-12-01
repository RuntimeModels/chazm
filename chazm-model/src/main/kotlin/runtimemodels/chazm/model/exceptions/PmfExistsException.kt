package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.PmfId
import runtimemodels.chazm.model.message.E

class PmfExistsException(
    val id: PmfId
) : IllegalArgumentException(E.ENTITY_ALREADY_EXISTS[id.type.simpleName, id])
