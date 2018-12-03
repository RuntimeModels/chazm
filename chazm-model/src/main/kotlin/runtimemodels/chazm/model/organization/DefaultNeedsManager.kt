package runtimemodels.chazm.model.organization

import runtimemodels.chazm.api.id.AttributeId
import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.organization.NeedsManager
import runtimemodels.chazm.api.relation.Needs
import runtimemodels.chazm.model.exceptions.NeedsExistsException
import runtimemodels.chazm.model.exceptions.NeedsNotExistsException
import javax.inject.Inject

internal data class DefaultNeedsManager @Inject constructor(
    private val map: MutableMap<RoleId, MutableMap<AttributeId, Needs>>,
    private val byMap: MutableMap<AttributeId, MutableMap<RoleId, Needs>>
) : NeedsManager, Map<RoleId, Map<AttributeId, Needs>> by map {

    override fun add(needs: Needs) {
        if (map.containsKey(needs.role.id) && map[needs.role.id]!!.containsKey(needs.attribute.id)) {
            throw NeedsExistsException(needs.role.id, needs.attribute.id)
        }
        map.computeIfAbsent(needs.role.id) { mutableMapOf() }[needs.attribute.id] = needs
        byMap.computeIfAbsent(needs.attribute.id) { mutableMapOf() }[needs.role.id] = needs
    }

    override operator fun get(roleId: RoleId, attributeId: AttributeId) = map[roleId]?.get(attributeId)

    override operator fun get(key: RoleId): Map<AttributeId, Needs> = map.getOrDefault(key, mutableMapOf())

    override operator fun get(id: AttributeId): Map<RoleId, Needs> = byMap.getOrDefault(id, mutableMapOf())

    override fun remove(roleId: RoleId, attributeId: AttributeId): Needs {
        if (map.containsKey(roleId) && map[roleId]!!.containsKey(attributeId)) {
            val contains = map[roleId]!!.remove(attributeId)!!
            if (byMap.containsKey(attributeId)) {
                val m = byMap[attributeId]!!
                if (m.containsKey(roleId)) {
                    val c = m.remove(roleId)!!
                    if (contains === c) {
                        return contains
                    }
                    throw IllegalStateException("the '$contains' and '$c' are different instances!")
                }
                throw IllegalStateException("the 'byMap[$attributeId][$roleId]' is missing!")
            }
            throw IllegalStateException("the 'byMap[$attributeId]' is missing!")
        }
        throw NeedsNotExistsException(roleId, attributeId)
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
