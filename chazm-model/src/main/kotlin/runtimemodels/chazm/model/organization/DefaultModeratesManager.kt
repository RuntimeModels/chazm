package runtimemodels.chazm.model.organization

import runtimemodels.chazm.api.id.AttributeId
import runtimemodels.chazm.api.id.PmfId
import runtimemodels.chazm.api.organization.ModeratesManager
import runtimemodels.chazm.api.relation.Moderates
import javax.inject.Inject

internal data class DefaultModeratesManager @Inject constructor(
    private val map: MutableMap<PmfId, MutableMap<AttributeId, Moderates>>,
    private val byMap: MutableMap<AttributeId, MutableMap<PmfId, Moderates>>
) : ModeratesManager {

    override fun add(moderates: Moderates) {
        if (map[moderates.pmf.id]?.containsKey(moderates.attribute.id) == true) {
            return
        }
        map.computeIfAbsent(moderates.pmf.id) { mutableMapOf() }[moderates.attribute.id] = moderates
        byMap.computeIfAbsent(moderates.attribute.id) { mutableMapOf() }[moderates.pmf.id] = moderates
    }

    override operator fun get(pmfId: PmfId, attributeId: AttributeId) = map[pmfId]?.get(attributeId)

    override operator fun get(id: PmfId): Map<AttributeId, Moderates> = map[id] ?: emptyMap()

    override operator fun get(id: AttributeId): Map<PmfId, Moderates> = byMap[id] ?: emptyMap()

    override fun remove(pmfId: PmfId, attributeId: AttributeId): Moderates? {
        return map[pmfId]?.remove(attributeId)?.also {
            byMap[attributeId]?.apply {
                remove(pmfId)?.let { moderates ->
                    if (it === moderates) {
                        return it
                    }
                    throw IllegalStateException("the '$it' and '$moderates' are different instances!")
                } ?: throw IllegalStateException("the 'byMap[$attributeId][$pmfId]' is missing!")
            } ?: throw IllegalStateException("the 'byMap[$attributeId]' is missing!")
        }
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
