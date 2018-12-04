package runtimemodels.chazm.model.organization

import runtimemodels.chazm.api.id.AgentId
import runtimemodels.chazm.api.id.CapabilityId
import runtimemodels.chazm.api.organization.PossessesManager
import runtimemodels.chazm.api.relation.Possesses
import runtimemodels.chazm.model.exceptions.PossessesExistsException
import runtimemodels.chazm.model.exceptions.PossessesNotExistsException
import javax.inject.Inject

internal data class DefaultPossessesManager @Inject constructor(
    private val map: MutableMap<AgentId, MutableMap<CapabilityId, Possesses>>,
    private val byMap: MutableMap<CapabilityId, MutableMap<AgentId, Possesses>>
) : PossessesManager, Map<AgentId, Map<CapabilityId, Possesses>> by map {

    override fun add(possesses: Possesses) {
        if (map.containsKey(possesses.agent.id) && map[possesses.agent.id]!!.containsKey(possesses.capability.id)) {
            throw PossessesExistsException(possesses.agent.id, possesses.capability.id)
        }
        map.computeIfAbsent(possesses.agent.id) { mutableMapOf() }[possesses.capability.id] = possesses
        byMap.computeIfAbsent(possesses.capability.id) { mutableMapOf() }[possesses.agent.id] = possesses
    }

    override operator fun get(agentId: AgentId, capabilityId: CapabilityId) = map[agentId]?.get(capabilityId)

    override operator fun get(key: AgentId): Map<CapabilityId, Possesses> = map.getOrDefault(key, mutableMapOf())

    override operator fun get(id: CapabilityId): Map<AgentId, Possesses> = byMap.getOrDefault(id, mutableMapOf())

    override fun remove(agentId: AgentId, capabilityId: CapabilityId): Possesses {
        if (map.containsKey(agentId) && map[agentId]!!.containsKey(capabilityId)) {
            val contains = map[agentId]!!.remove(capabilityId)!!
            if (byMap.containsKey(capabilityId)) {
                val m = byMap[capabilityId]!!
                if (m.containsKey(agentId)) {
                    val c = m.remove(agentId)!!
                    if (contains === c) {
                        return contains
                    }
                    throw IllegalStateException("the '$contains' and '$c' are different instances!")
                }
                throw IllegalStateException("the 'byMap[$capabilityId][$agentId]' is missing!")
            }
            throw IllegalStateException("the 'byMap[$capabilityId]' is missing!")
        }
        throw PossessesNotExistsException(agentId, capabilityId)
    }

    override fun remove(id: AgentId) {
        map.remove(id)
        byMap.forEach { it.value.remove(id) }
    }

    override fun remove(id: CapabilityId) {
        map.forEach { it.value.remove(id) }
        byMap.remove(id)
    }
}
