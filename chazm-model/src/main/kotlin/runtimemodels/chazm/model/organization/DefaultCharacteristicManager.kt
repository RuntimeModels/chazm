package runtimemodels.chazm.model.organization

import runtimemodels.chazm.api.entity.Characteristic
import runtimemodels.chazm.api.id.CharacteristicId
import runtimemodels.chazm.api.organization.CharacteristicManager
import runtimemodels.chazm.model.exceptions.EntityExistsException
import runtimemodels.chazm.model.exceptions.EntityNotExistsException
import javax.inject.Inject

internal data class DefaultCharacteristicManager @Inject constructor(
    private val map: MutableMap<CharacteristicId, Characteristic>
) : CharacteristicManager, Map<CharacteristicId, Characteristic> by map {
    override fun add(characteristic: Characteristic) {
        if (map.containsKey(characteristic.id)) {
            throw EntityExistsException(characteristic.id)
        }
        map[characteristic.id] = characteristic
    }

    override fun remove(id: CharacteristicId): Characteristic {
        if (map.containsKey(id)) {
            return map.remove(id)!!
        }
        throw EntityNotExistsException(id)
    }
}
