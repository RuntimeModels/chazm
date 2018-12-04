package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.InstanceGoalId
import runtimemodels.chazm.model.message.E

class InstanceGoalNotExistsException(
    val id: InstanceGoalId
) : IllegalArgumentException(E.ENTITY_DOES_NOT_EXISTS[id.type, id])
