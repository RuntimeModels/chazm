package runtimemodels.chazm.model.organization

import runtimemodels.chazm.api.id.CapabilityId
import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.organization.RequiresManager
import runtimemodels.chazm.api.relation.Requires
import runtimemodels.chazm.model.exceptions.RequiresExistsException
import runtimemodels.chazm.model.exceptions.RequiresNotExistsException
import javax.inject.Inject

internal data class DefaultRequiresManager @Inject constructor(
    private val map: MutableMap<RoleId, MutableMap<CapabilityId, Requires>>,
    private val byMap: MutableMap<CapabilityId, MutableMap<RoleId, Requires>>
) : RequiresManager, Map<RoleId, Map<CapabilityId, Requires>> by map {

    override fun add(requires: Requires) {
        if (map.containsKey(requires.role.id) && map[requires.role.id]!!.containsKey(requires.capability.id)) {
            throw RequiresExistsException(requires.role.id, requires.capability.id)
        }
        map.computeIfAbsent(requires.role.id) { mutableMapOf() }[requires.capability.id] = requires
        byMap.computeIfAbsent(requires.capability.id) { mutableMapOf() }[requires.role.id] = requires
    }

    override operator fun get(roleId: RoleId, capabilityId: CapabilityId) = map[roleId]?.get(capabilityId)

    override operator fun get(key: RoleId): Map<CapabilityId, Requires> = map.getOrDefault(key, mutableMapOf())

    override operator fun get(id: CapabilityId): Map<RoleId, Requires> = byMap.getOrDefault(id, mutableMapOf())

    override fun remove(roleId: RoleId, capabilityId: CapabilityId): Requires {
        if (map.containsKey(roleId) && map[roleId]!!.containsKey(capabilityId)) {
            val contains = map[roleId]!!.remove(capabilityId)!!
            if (byMap.containsKey(capabilityId)) {
                val m = byMap[capabilityId]!!
                if (m.containsKey(roleId)) {
                    val c = m.remove(roleId)!!
                    if (contains === c) {
                        return contains
                    }
                    throw IllegalStateException("the '$contains' and '$c' are different instances!")
                }
                throw IllegalStateException("the 'byMap[$capabilityId][$roleId]' is missing!")
            }
            throw IllegalStateException("the 'byMap[$capabilityId]' is missing!")
        }
        throw RequiresNotExistsException(roleId, capabilityId)
    }

    override fun remove(id: RoleId) {
        map.remove(id)
        byMap.forEach { it.value.remove(id) }
    }

    override fun remove(id: CapabilityId) {
        map.forEach { it.value.remove(id) }
        byMap.remove(id)
    }
}
