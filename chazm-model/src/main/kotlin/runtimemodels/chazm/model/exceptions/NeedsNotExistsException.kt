package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.AttributeId
import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.relation.Needs
import runtimemodels.chazm.model.message.E

class NeedsNotExistsException(
    roleId: RoleId,
    attributeId: AttributeId
) : IllegalArgumentException(E.RELATION_DOES_NOT_EXISTS[Needs::class, roleId, attributeId])
