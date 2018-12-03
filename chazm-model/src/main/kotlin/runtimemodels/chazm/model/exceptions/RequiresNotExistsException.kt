package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.CapabilityId
import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.relation.Requires
import runtimemodels.chazm.model.message.E

class RequiresNotExistsException(
    roleId: RoleId,
    capabilityId: CapabilityId
) : IllegalArgumentException(E.RELATION_DOES_NOT_EXISTS[Requires::class, roleId, capabilityId])
