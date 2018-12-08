package runtimemodels.chazm.model.organization

import runtimemodels.chazm.api.id.AttributeId
import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.organization.NeedsManager
import runtimemodels.chazm.api.relation.Needs
import javax.inject.Inject

internal data class DefaultNeedsManager @Inject constructor(
    private val map: MutableMap<RoleId, MutableMap<AttributeId, Needs>>,
    private val byMap: MutableMap<AttributeId, MutableMap<RoleId, Needs>>
) : NeedsManager {

    override fun add(needs: Needs) {
        if (map[needs.role.id]?.containsKey(needs.attribute.id) == true) {
            return
        }
        map.computeIfAbsent(needs.role.id) { mutableMapOf() }[needs.attribute.id] = needs
        byMap.computeIfAbsent(needs.attribute.id) { mutableMapOf() }[needs.role.id] = needs
    }

    override operator fun get(roleId: RoleId, attributeId: AttributeId) = map[roleId]?.get(attributeId)

    override operator fun get(id: RoleId): Map<AttributeId, Needs> = map[id] ?: emptyMap()

    override operator fun get(id: AttributeId): Map<RoleId, Needs> = byMap[id] ?: emptyMap()

    override fun remove(roleId: RoleId, attributeId: AttributeId): Needs? {
        return map[roleId]?.remove(attributeId)?.also {
            byMap[attributeId]?.apply {
                remove(roleId)?.let { needs ->
                    if (it == needs) {
                        return it
                    }
                    throw IllegalStateException("the '$it' and '$needs' are different instances!")
                } ?: throw IllegalStateException("the 'byMap[$attributeId][$roleId]' is missing!")
            } ?: throw IllegalStateException("the 'byMap[$attributeId]' is missing!")
        }
    }

    override fun remove(id: RoleId) {
        map.remove(id)
        byMap.forEach { it.value.remove(id) }
    }

    override fun remove(id: AttributeId) {
        map.forEach { it.value.remove(id) }
        byMap.remove(id)
    }
}
