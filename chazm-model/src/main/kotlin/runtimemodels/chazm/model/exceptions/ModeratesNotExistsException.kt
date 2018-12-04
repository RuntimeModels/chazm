package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.AttributeId
import runtimemodels.chazm.api.id.PmfId
import runtimemodels.chazm.api.relation.Moderates
import runtimemodels.chazm.model.message.E

class ModeratesNotExistsException(
    pmfId: PmfId,
    attributeId: AttributeId
) : IllegalArgumentException(E.RELATION_DOES_NOT_EXISTS[Moderates::class, pmfId, attributeId])
