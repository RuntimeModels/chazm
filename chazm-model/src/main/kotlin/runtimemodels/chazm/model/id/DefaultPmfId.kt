package runtimemodels.chazm.model.id

import runtimemodels.chazm.api.entity.Pmf
import runtimemodels.chazm.api.id.PmfId

internal data class DefaultPmfId(
    private val id: String
) : AbstractId<Pmf>(Pmf::class.java), PmfId
