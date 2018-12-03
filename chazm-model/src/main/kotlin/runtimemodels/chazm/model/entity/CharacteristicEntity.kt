package runtimemodels.chazm.model.entity

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Characteristic
import runtimemodels.chazm.api.id.CharacteristicId
import javax.inject.Inject

internal data class CharacteristicEntity @Inject constructor(
    @Assisted override val id: CharacteristicId
) : Characteristic
