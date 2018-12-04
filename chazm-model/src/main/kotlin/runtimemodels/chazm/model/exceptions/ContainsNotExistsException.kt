package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.CharacteristicId
import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.relation.Achieves
import runtimemodels.chazm.model.message.E

class ContainsNotExistsException(
    roleId: RoleId,
    characteristicId: CharacteristicId
) : IllegalArgumentException(E.RELATION_DOES_NOT_EXISTS[Achieves::class, roleId, characteristicId])
