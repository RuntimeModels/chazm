package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.PmfId
import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.relation.Uses
import runtimemodels.chazm.model.message.E

class UsesNotExistsException(
    roleId: RoleId,
    pmfId: PmfId
) : IllegalArgumentException(E.RELATION_DOES_NOT_EXISTS[Uses::class, roleId, pmfId])
