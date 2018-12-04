package runtimemodels.chazm.model.organization

import runtimemodels.chazm.api.entity.Pmf
import runtimemodels.chazm.api.id.PmfId
import runtimemodels.chazm.api.organization.PmfManager
import runtimemodels.chazm.model.exceptions.EntityExistsException
import runtimemodels.chazm.model.exceptions.EntityNotExistsException
import javax.inject.Inject

internal data class DefaultPmfManager @Inject constructor(
    private val map: MutableMap<PmfId, Pmf>
) : PmfManager, Map<PmfId, Pmf> by map {
    override fun add(pmf: Pmf) {
        if (map.containsKey(pmf.id)) {
            throw EntityExistsException(pmf.id)
        }
        map[pmf.id] = pmf
    }

    override fun remove(id: PmfId): Pmf {
        if (map.containsKey(id)) {
            return map.remove(id)!!
        }
        throw EntityNotExistsException(id)
    }
}
