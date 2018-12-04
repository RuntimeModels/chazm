package runtimemodels.chazm.model.id

import runtimemodels.chazm.api.entity.Characteristic
import runtimemodels.chazm.api.id.CharacteristicId
import javax.inject.Inject

internal data class DefaultCharacteristicId @Inject constructor(
    private val id: String
) : AbstractId<Characteristic>(Characteristic::class), CharacteristicId
