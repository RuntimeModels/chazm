package runtimemodels.chazm.model.organization

import runtimemodels.chazm.api.id.CapabilityId
import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.organization.RequiresManager
import runtimemodels.chazm.api.relation.Requires
import javax.inject.Inject

internal data class DefaultRequiresManager @Inject constructor(
    private val map: MutableMap<RoleId, MutableMap<CapabilityId, Requires>>,
    private val byMap: MutableMap<CapabilityId, MutableMap<RoleId, Requires>>
) : RequiresManager {

    override fun add(requires: Requires) {
        if (map[requires.role.id]?.containsKey(requires.capability.id) == true) {
            return
        }
        map.computeIfAbsent(requires.role.id) { mutableMapOf() }[requires.capability.id] = requires
        byMap.computeIfAbsent(requires.capability.id) { mutableMapOf() }[requires.role.id] = requires
    }

    override operator fun get(roleId: RoleId, capabilityId: CapabilityId) = map[roleId]?.get(capabilityId)

    override operator fun get(id: RoleId): Map<CapabilityId, Requires> = map[id] ?: emptyMap()

    override operator fun get(id: CapabilityId): Map<RoleId, Requires> = byMap[id] ?: emptyMap()

    override fun remove(roleId: RoleId, capabilityId: CapabilityId): Requires? {
        return map[roleId]?.remove(capabilityId)?.also {
            byMap[capabilityId]?.apply {
                remove(roleId)?.let { requires ->
                    if (it == requires) {
                        return it
                    }
                    throw IllegalStateException("the '$it' and '$requires' are different instances!")
                } ?: throw IllegalStateException("the 'byMap[$capabilityId][$roleId]' is missing!")
            } ?: throw IllegalStateException("the 'byMap[$capabilityId]' is missing!")
        }
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
