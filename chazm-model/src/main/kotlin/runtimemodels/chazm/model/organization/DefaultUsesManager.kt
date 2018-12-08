package runtimemodels.chazm.model.organization

import runtimemodels.chazm.api.id.PmfId
import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.organization.UsesManager
import runtimemodels.chazm.api.relation.Uses
import javax.inject.Inject

internal data class DefaultUsesManager @Inject constructor(
    private val map: MutableMap<RoleId, MutableMap<PmfId, Uses>>,
    private val byMap: MutableMap<PmfId, MutableMap<RoleId, Uses>>
) : UsesManager {

    override fun add(uses: Uses) {
        if (map[uses.role.id]?.containsKey(uses.pmf.id) == true) {
            return
        }
        map.computeIfAbsent(uses.role.id) { mutableMapOf() }[uses.pmf.id] = uses
        byMap.computeIfAbsent(uses.pmf.id) { mutableMapOf() }[uses.role.id] = uses
    }

    override operator fun get(roleId: RoleId, pmfId: PmfId) = map[roleId]?.get(pmfId)

    override operator fun get(id: RoleId): Map<PmfId, Uses> = map[id] ?: emptyMap()

    override operator fun get(id: PmfId): Map<RoleId, Uses> = byMap[id] ?: emptyMap()

    override fun remove(roleId: RoleId, pmfId: PmfId): Uses? {
        return map[roleId]?.remove(pmfId)?.also {
            byMap[pmfId]?.apply {
                remove(roleId)?.let { uses ->
                    if (it == uses) {
                        return it
                    }
                    throw IllegalStateException("the '$it' and '$uses' are different instances!")
                } ?: throw IllegalStateException("the 'byMap[$pmfId][$roleId]' is missing!")
            } ?: throw IllegalStateException("the 'byMap[$pmfId]' is missing!")
        }
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
