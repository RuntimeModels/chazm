package runtimemodels.chazm.model.id

import runtimemodels.chazm.api.entity.Pmf
import runtimemodels.chazm.api.id.PmfId
import javax.inject.Inject

internal data class DefaultPmfId @Inject constructor(
    private val id: String
) : AbstractId<Pmf>(Pmf::class), PmfId
