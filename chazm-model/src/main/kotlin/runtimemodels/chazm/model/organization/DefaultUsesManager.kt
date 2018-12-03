package runtimemodels.chazm.model.organization

import runtimemodels.chazm.api.id.PmfId
import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.organization.UsesManager
import runtimemodels.chazm.api.relation.Uses
import runtimemodels.chazm.model.exceptions.UsesExistsException
import runtimemodels.chazm.model.exceptions.UsesNotExistsException
import javax.inject.Inject

internal data class DefaultUsesManager @Inject constructor(
    private val map: MutableMap<RoleId, MutableMap<PmfId, Uses>>,
    private val byMap: MutableMap<PmfId, MutableMap<RoleId, Uses>>
) : UsesManager, Map<RoleId, Map<PmfId, Uses>> by map {

    override fun add(uses: Uses) {
        if (map.containsKey(uses.role.id) && map[uses.role.id]!!.containsKey(uses.pmf.id)) {
            throw UsesExistsException(uses.role.id, uses.pmf.id)
        }
        map.computeIfAbsent(uses.role.id) { mutableMapOf() }[uses.pmf.id] = uses
        byMap.computeIfAbsent(uses.pmf.id) { mutableMapOf() }[uses.role.id] = uses
    }

    override operator fun get(roleId: RoleId, pmfId: PmfId) = map[roleId]?.get(pmfId)

    override operator fun get(key: RoleId): Map<PmfId, Uses> = map.getOrDefault(key, mutableMapOf())

    override operator fun get(id: PmfId): Map<RoleId, Uses> = byMap.getOrDefault(id, mutableMapOf())

    override fun remove(roleId: RoleId, pmfId: PmfId): Uses {
        if (map.containsKey(roleId) && map[roleId]!!.containsKey(pmfId)) {
            val contains = map[roleId]!!.remove(pmfId)!!
            if (byMap.containsKey(pmfId)) {
                val m = byMap[pmfId]!!
                if (m.containsKey(roleId)) {
                    val c = m.remove(roleId)!!
                    if (contains === c) {
                        return contains
                    }
                    throw IllegalStateException("the '$contains' and '$c' are different instances!")
                }
                throw IllegalStateException("the 'byMap[$pmfId][$roleId]' is missing!")
            }
            throw IllegalStateException("the 'byMap[$pmfId]' is missing!")
        }
        throw UsesNotExistsException(roleId, pmfId)
    }

    override fun remove(id: RoleId) {
        map.remove(id)
        byMap.forEach { it.value.remove(id) }
    }

    override fun remove(id: PmfId) {
        map.forEach { it.value.remove(id) }
        byMap.remove(id)
    }
}
