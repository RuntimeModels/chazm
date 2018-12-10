package runtimemodels.chazm.model.entity

import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.entity.AttributeId
import runtimemodels.chazm.api.id.UniqueId

/**
 * The [AttributeFactory] interface defines the APIs for constructing [Attribute]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface AttributeFactory {

    /**
     * Constructs an [Attribute].
     *
     * @param id   the [UniqueId] that represents the [Attribute].
     * @param type the [Attribute.Type] of the [Attribute].
     * @return an [Attribute].
     */
    fun build(id: AttributeId, type: Attribute.Type): Attribute

}
