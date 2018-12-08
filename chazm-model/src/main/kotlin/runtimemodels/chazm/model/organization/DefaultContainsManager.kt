package runtimemodels.chazm.model.organization

import runtimemodels.chazm.api.id.CharacteristicId
import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.organization.ContainsManager
import runtimemodels.chazm.api.relation.Contains
import javax.inject.Inject

internal data class DefaultContainsManager @Inject constructor(
    private val map: MutableMap<RoleId, MutableMap<CharacteristicId, Contains>>,
    private val byMap: MutableMap<CharacteristicId, MutableMap<RoleId, Contains>>
) : ContainsManager {

    override fun add(contains: Contains) {
        if (map[contains.role.id]?.containsKey(contains.characteristic.id) == true) {
            return
        }
        map.computeIfAbsent(contains.role.id) { mutableMapOf() }[contains.characteristic.id] = contains
        byMap.computeIfAbsent(contains.characteristic.id) { mutableMapOf() }[contains.role.id] = contains
    }

    override operator fun get(roleId: RoleId, characteristicId: CharacteristicId): Contains? = map[roleId]?.get(characteristicId)

    override operator fun get(id: RoleId): Map<CharacteristicId, Contains> = map[id] ?: emptyMap()

    override operator fun get(id: CharacteristicId): Map<RoleId, Contains> = byMap[id] ?: emptyMap()

    override fun remove(roleId: RoleId, characteristicId: CharacteristicId): Contains? {
        return map[roleId]?.remove(characteristicId)?.also {
            byMap[characteristicId]?.apply {
                remove(roleId)?.also { contains ->
                    if (it === contains) {
                        return it
                    }
                    throw IllegalStateException("the '$it' and '$contains' are different instances!")
                } ?: throw IllegalStateException("the 'byMap[$characteristicId][$roleId]' is missing!")
            } ?: throw IllegalStateException("the 'byMap[$characteristicId]' is missing!")
        }
    }

    override fun remove(id: RoleId) {
        map.remove(id)
        byMap.forEach { it.value.remove(id) }
    }

    override fun remove(id: CharacteristicId) {
        map.forEach { it.value.remove(id) }
        byMap.remove(id)
    }
}
