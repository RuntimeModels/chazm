package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.InstanceGoalId
import runtimemodels.chazm.model.message.E

class InstanceGoalExistsException(
    val id: InstanceGoalId
) : IllegalArgumentException(E.ENTITY_ALREADY_EXISTS[id.type.simpleName, id])
