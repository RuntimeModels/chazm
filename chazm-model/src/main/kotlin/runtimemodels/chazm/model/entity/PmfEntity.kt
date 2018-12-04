package runtimemodels.chazm.model.entity

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Pmf
import runtimemodels.chazm.api.id.PmfId
import javax.inject.Inject

internal data class PmfEntity @Inject constructor(
    @Assisted override val id: PmfId
) : Pmf
