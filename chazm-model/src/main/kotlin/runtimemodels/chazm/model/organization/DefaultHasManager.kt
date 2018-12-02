package runtimemodels.chazm.model.organization

import runtimemodels.chazm.api.id.AgentId
import runtimemodels.chazm.api.id.AttributeId
import runtimemodels.chazm.api.organization.HasManager
import runtimemodels.chazm.api.relation.Has
import runtimemodels.chazm.model.exceptions.HasExistsException
import runtimemodels.chazm.model.exceptions.HasNotExistsException
import javax.inject.Inject

internal data class DefaultHasManager @Inject constructor(
    private val map: MutableMap<AgentId, MutableMap<AttributeId, Has>>,
    private val byMap: MutableMap<AttributeId, MutableMap<AgentId, Has>>
) : HasManager, Map<AgentId, Map<AttributeId, Has>> by map {

    override fun add(has: Has) {
        if (map.containsKey(has.agent.id) && map[has.agent.id]!!.containsKey(has.attribute.id)) {
            throw HasExistsException(has.agent.id, has.attribute.id)
        }
        map.computeIfAbsent(has.agent.id) { mutableMapOf() }[has.attribute.id] = has
        byMap.computeIfAbsent(has.attribute.id) { mutableMapOf() }[has.agent.id] = has
    }

    override operator fun get(agentId: AgentId, attributeId: AttributeId) = map[agentId]?.get(attributeId)

    override operator fun get(key: AgentId): Map<AttributeId, Has> = map.getOrDefault(key, mutableMapOf())

    override operator fun get(id: AttributeId): Map<AgentId, Has> = byMap.getOrDefault(id, mutableMapOf())

    override fun remove(agentId: AgentId, attributeId: AttributeId): Has {
        if (map.containsKey(agentId) && map[agentId]!!.containsKey(attributeId)) {
            val contains = map[agentId]!!.remove(attributeId)!!
            if (byMap.containsKey(attributeId)) {
                val m = byMap[attributeId]!!
                if (m.containsKey(agentId)) {
                    val c = m.remove(agentId)!!
                    if (contains === c) {
                        return contains
                    }
                    throw IllegalStateException("the '$contains' and '$c' are different instances!")
                }
                throw IllegalStateException("the 'byMap[$attributeId][$agentId]' is missing!")
            }
            throw IllegalStateException("the 'byMap[$attributeId]' is missing!")
        }
        throw HasNotExistsException(agentId, attributeId)
    }

    override fun remove(id: AgentId) {
        map.remove(id)
        byMap.forEach { it.value.remove(id) }
    }

    override fun remove(id: AttributeId) {
        map.forEach { it.value.remove(id) }
        byMap.remove(id)
    }
}
