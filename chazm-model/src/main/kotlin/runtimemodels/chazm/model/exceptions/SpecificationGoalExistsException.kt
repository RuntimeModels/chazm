package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.SpecificationGoalId
import runtimemodels.chazm.model.message.E

class SpecificationGoalExistsException(
    val id: SpecificationGoalId
) : IllegalArgumentException(E.ENTITY_ALREADY_EXISTS[id.type, id])
