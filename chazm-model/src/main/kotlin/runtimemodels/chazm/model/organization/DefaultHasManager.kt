package runtimemodels.chazm.model.organization

import runtimemodels.chazm.api.id.AgentId
import runtimemodels.chazm.api.id.AttributeId
import runtimemodels.chazm.api.organization.HasManager
import runtimemodels.chazm.api.relation.Has
import javax.inject.Inject

internal data class DefaultHasManager @Inject constructor(
    private val map: MutableMap<AgentId, MutableMap<AttributeId, Has>>,
    private val byMap: MutableMap<AttributeId, MutableMap<AgentId, Has>>
) : HasManager {

    override fun add(has: Has) {
        if (map[has.agent.id]?.containsKey(has.attribute.id) == true) {
            return
        }
        map.computeIfAbsent(has.agent.id) { mutableMapOf() }[has.attribute.id] = has
        byMap.computeIfAbsent(has.attribute.id) { mutableMapOf() }[has.agent.id] = has
    }

    override operator fun get(agentId: AgentId, attributeId: AttributeId) = map[agentId]?.get(attributeId)

    override operator fun get(id: AgentId): Map<AttributeId, Has> = map[id] ?: emptyMap()

    override operator fun get(id: AttributeId): Map<AgentId, Has> = byMap[id] ?: emptyMap()

    override fun remove(agentId: AgentId, attributeId: AttributeId): Has? {
        return map[agentId]?.remove(attributeId)?.also {
            byMap[attributeId]?.apply {
                remove(agentId)?.let { has ->
                    if (it === has) {
                        return it
                    }
                    throw IllegalStateException("the '$it' and '$has' are different instances!")
                } ?: throw IllegalStateException("the 'byMap[$attributeId][$agentId]' is missing!")
            } ?: throw IllegalStateException("the 'byMap[$attributeId]' is missing!")
        }
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
