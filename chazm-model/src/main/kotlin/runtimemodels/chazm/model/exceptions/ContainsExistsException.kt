package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.CharacteristicId
import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.relation.Contains
import runtimemodels.chazm.model.message.E

class ContainsExistsException(
    roleId: RoleId,
    characteristicId: CharacteristicId
) : IllegalArgumentException(E.RELATION_ALREADY_EXISTS[Contains::class, roleId, characteristicId])
