package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.CapabilityId
import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.relation.Requires
import runtimemodels.chazm.model.message.E

class RequiresExistsException(
    roleId: RoleId,
    capabilityId: CapabilityId
) : IllegalArgumentException(E.RELATION_ALREADY_EXISTS[Requires::class, roleId, capabilityId])
