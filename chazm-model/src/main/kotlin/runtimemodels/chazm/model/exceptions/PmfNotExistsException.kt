package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.PmfId
import runtimemodels.chazm.model.message.E

class PmfNotExistsException(
    val id: PmfId
) : IllegalArgumentException(E.ENTITY_DOES_NOT_EXISTS[id.type, id])
