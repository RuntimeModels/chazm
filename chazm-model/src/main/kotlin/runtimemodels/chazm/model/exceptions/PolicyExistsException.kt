package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.PolicyId
import runtimemodels.chazm.model.message.E

class PolicyExistsException(
    val id: PolicyId
) : IllegalArgumentException(E.ENTITY_ALREADY_EXISTS[id.type, id])
