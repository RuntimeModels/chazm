package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.CharacteristicId
import runtimemodels.chazm.model.message.E

class CharacteristicExistsException(
    val id: CharacteristicId
) : IllegalArgumentException(E.ENTITY_ALREADY_EXISTS[id.type.simpleName, id])
