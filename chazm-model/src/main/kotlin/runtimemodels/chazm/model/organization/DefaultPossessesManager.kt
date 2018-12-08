package runtimemodels.chazm.model.organization

import runtimemodels.chazm.api.id.AgentId
import runtimemodels.chazm.api.id.CapabilityId
import runtimemodels.chazm.api.organization.PossessesManager
import runtimemodels.chazm.api.relation.Possesses
import javax.inject.Inject

internal data class DefaultPossessesManager @Inject constructor(
    private val map: MutableMap<AgentId, MutableMap<CapabilityId, Possesses>>,
    private val byMap: MutableMap<CapabilityId, MutableMap<AgentId, Possesses>>
) : PossessesManager {

    override fun add(possesses: Possesses) {
        if (map[possesses.agent.id]?.containsKey(possesses.capability.id) == true) {
            return
        }
        map.computeIfAbsent(possesses.agent.id) { mutableMapOf() }[possesses.capability.id] = possesses
        byMap.computeIfAbsent(possesses.capability.id) { mutableMapOf() }[possesses.agent.id] = possesses
    }

    override operator fun get(agentId: AgentId, capabilityId: CapabilityId) = map[agentId]?.get(capabilityId)

    override operator fun get(id: AgentId): Map<CapabilityId, Possesses> = map[id] ?: emptyMap()

    override operator fun get(id: CapabilityId): Map<AgentId, Possesses> = byMap[id] ?: emptyMap()

    override fun remove(agentId: AgentId, capabilityId: CapabilityId): Possesses? {
        return map[agentId]?.remove(capabilityId)?.also {
            byMap[capabilityId]?.apply {
                remove(agentId)?.let { possesses ->
                    if (it == possesses) {
                        return it
                    }
                    throw IllegalStateException("the '$it' and '$possesses' are different instances!")
                } ?: throw IllegalStateException("the 'byMap[$capabilityId][$agentId]' is missing!")
            } ?: throw IllegalStateException("the 'byMap[$capabilityId]' is missing!")
        }
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
