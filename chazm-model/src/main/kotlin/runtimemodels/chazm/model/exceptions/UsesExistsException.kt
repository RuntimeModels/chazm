package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.PmfId
import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.relation.Uses
import runtimemodels.chazm.model.message.E

class UsesExistsException(
    roleId: RoleId,
    pmfId: PmfId
) : IllegalArgumentException(E.RELATION_ALREADY_EXISTS[Uses::class, roleId, pmfId])
