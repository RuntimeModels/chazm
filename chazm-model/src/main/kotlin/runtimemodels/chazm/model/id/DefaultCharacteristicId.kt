package runtimemodels.chazm.model.id

import runtimemodels.chazm.api.entity.Characteristic
import runtimemodels.chazm.api.id.CharacteristicId

internal data class DefaultCharacteristicId(
    private val id: String
) : AbstractId<Characteristic>(Characteristic::class.java), CharacteristicId
