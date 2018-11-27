package runtimemodels.chazm.model.id

import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.id.AttributeId

internal data class DefaultAttributeId(
    private val id: String
) : AbstractId<Attribute>(Attribute::class.java), AttributeId
