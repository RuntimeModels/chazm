package runtimemodels.chazm.model.organization

import runtimemodels.chazm.api.id.CharacteristicId
import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.organization.ContainsManager
import runtimemodels.chazm.api.relation.Contains
import runtimemodels.chazm.model.exceptions.ContainsExistsException
import runtimemodels.chazm.model.exceptions.ContainsNotExistsException
import javax.inject.Inject

internal data class DefaultContainsManager @Inject constructor(
    private val map: MutableMap<RoleId, MutableMap<CharacteristicId, Contains>>,
    private val byMap: MutableMap<CharacteristicId, MutableMap<RoleId, Contains>>
) : ContainsManager, Map<RoleId, Map<CharacteristicId, Contains>> by map {

    override fun add(contains: Contains) {
        if (map.containsKey(contains.role.id) && map[contains.role.id]!!.containsKey(contains.characteristic.id)) {
            throw ContainsExistsException(contains.role.id, contains.characteristic.id)
        }
        map.computeIfAbsent(contains.role.id) { mutableMapOf() }[contains.characteristic.id] = contains
        byMap.computeIfAbsent(contains.characteristic.id) { mutableMapOf() }[contains.role.id] = contains
    }

    override operator fun get(key: RoleId): Map<CharacteristicId, Contains> = map.getOrDefault(key, mutableMapOf())

    override operator fun get(id: CharacteristicId): Map<RoleId, Contains> = byMap.getOrDefault(id, mutableMapOf())

    override fun remove(roleId: RoleId, characteristicId: CharacteristicId): Contains {
        if (map.containsKey(roleId) && map[roleId]!!.containsKey(characteristicId)) {
            val contains = map[roleId]!!.remove(characteristicId)!!
            if (byMap.containsKey(characteristicId)) {
                val m = byMap[characteristicId]!!
                if (m.containsKey(roleId)) {
                    val c = m.remove(roleId)!!
                    if (contains === c) {
                        return contains
                    }
                    throw IllegalStateException("the '$contains' and '$c' are different instances!")
                }
                throw IllegalStateException("the 'byMap[$characteristicId][$roleId]' is missing!")
            }
            throw IllegalStateException("the 'byMap[$characteristicId]' is missing!")
        }
        throw ContainsNotExistsException(roleId, characteristicId)
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
