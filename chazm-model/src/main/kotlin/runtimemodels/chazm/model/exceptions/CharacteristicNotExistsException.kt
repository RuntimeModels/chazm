package runtimemodels.chazm.model.exceptions

import runtimemodels.chazm.api.id.CharacteristicId
import runtimemodels.chazm.model.message.E

class CharacteristicNotExistsException(
    val id: CharacteristicId
) : IllegalArgumentException(E.ENTITY_DOES_NOT_EXISTS[id.type, id])
