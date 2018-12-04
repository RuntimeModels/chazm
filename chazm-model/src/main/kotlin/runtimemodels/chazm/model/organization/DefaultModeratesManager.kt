package runtimemodels.chazm.model.organization

import runtimemodels.chazm.api.id.AttributeId
import runtimemodels.chazm.api.id.PmfId
import runtimemodels.chazm.api.organization.ModeratesManager
import runtimemodels.chazm.api.relation.Moderates
import runtimemodels.chazm.model.exceptions.ModeratesExistsException
import runtimemodels.chazm.model.exceptions.ModeratesNotExistsException
import javax.inject.Inject

internal data class DefaultModeratesManager @Inject constructor(
    private val map: MutableMap<PmfId, MutableMap<AttributeId, Moderates>>,
    private val byMap: MutableMap<AttributeId, MutableMap<PmfId, Moderates>>
) : ModeratesManager, Map<PmfId, Map<AttributeId, Moderates>> by map {

    override fun add(moderates: Moderates) {
        if (map.containsKey(moderates.pmf.id) && map[moderates.pmf.id]!!.containsKey(moderates.attribute.id)) {
            throw ModeratesExistsException(moderates.pmf.id, moderates.attribute.id)
        }
        map.computeIfAbsent(moderates.pmf.id) { mutableMapOf() }[moderates.attribute.id] = moderates
        byMap.computeIfAbsent(moderates.attribute.id) { mutableMapOf() }[moderates.pmf.id] = moderates
    }

    override operator fun get(pmfId: PmfId, attributeId: AttributeId) = map[pmfId]?.get(attributeId)

    override operator fun get(key: PmfId): Map<AttributeId, Moderates> = map.getOrDefault(key, mutableMapOf())

    override operator fun get(id: AttributeId): Map<PmfId, Moderates> = byMap.getOrDefault(id, mutableMapOf())

    override fun remove(pmfId: PmfId, attributeId: AttributeId): Moderates {
        if (map.containsKey(pmfId) && map[pmfId]!!.containsKey(attributeId)) {
            val contains = map[pmfId]!!.remove(attributeId)!!
            if (byMap.containsKey(attributeId)) {
                val m = byMap[attributeId]!!
                if (m.containsKey(pmfId)) {
                    val c = m.remove(pmfId)!!
                    if (contains === c) {
                        return contains
                    }
                    throw IllegalStateException("the '$contains' and '$c' are different instances!")
                }
                throw IllegalStateException("the 'byMap[$attributeId][$pmfId]' is missing!")
            }
            throw IllegalStateException("the 'byMap[$attributeId]' is missing!")
        }
        throw ModeratesNotExistsException(pmfId, attributeId)
    }

    override fun remove(id: PmfId) {
        map.remove(id)
        byMap.forEach { it.value.remove(id) }
    }

    override fun remove(id: AttributeId) {
        map.forEach { it.value.remove(id) }
        byMap.remove(id)
    }
}
