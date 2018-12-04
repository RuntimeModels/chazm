package runtimemodels.chazm.model.id

import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.id.AttributeId
import javax.inject.Inject

internal data class DefaultAttributeId @Inject constructor(
    private val id: String
) : AbstractId<Attribute>(Attribute::class), AttributeId
