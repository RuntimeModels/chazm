package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.PolicyId
import runtimemodels.chazm.model.message.E

class PolicyNotExistsException(
    val id: PolicyId
) : IllegalArgumentException(E.ENTITY_DOES_NOT_EXISTS[id.type.simpleName, id])
