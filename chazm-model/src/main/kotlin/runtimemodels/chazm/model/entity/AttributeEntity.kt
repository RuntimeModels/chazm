package runtimemodels.chazm.model.entity

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.id.AttributeId
import javax.inject.Inject

internal data class AttributeEntity @Inject constructor(
    @Assisted override val id: AttributeId,
    @param:Assisted override val type: Attribute.Type
) : Attribute
