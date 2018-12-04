package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.SpecificationGoalId
import runtimemodels.chazm.model.message.E

class SpecifcationGoalNotExistsException(
    val id: SpecificationGoalId
) : IllegalArgumentException(E.ENTITY_DOES_NOT_EXISTS[id.type, id])
