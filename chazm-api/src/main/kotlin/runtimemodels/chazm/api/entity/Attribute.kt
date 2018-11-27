package runtimemodels.chazm.api.entity

import runtimemodels.chazm.api.id.AttributeId
import runtimemodels.chazm.api.id.Identifiable
import runtimemodels.chazm.api.organization.Organization

/**
 * The [Attribute] interface defines an attribute entity of an [Organization].
 *
 * @author Christopher Zhong
 * @since 5.0
 */
interface Attribute : Identifiable<Attribute> {
    /**
     * The [AttributeId] of this [Attribute].
     */
    override val id: AttributeId
    /**
     * Returns the [Type] of this [Attribute].
     *
     * @return the [Type] of this [Attribute].
     */
    val type: Type

    /**
     * The [Type] enumerates the various types of an [Attribute].
     *
     * @author Christopher Zhong
     * @since 5.0
     */
    enum class Type {
        /**
         * Indicates a quantity-type [Attribute] (whose range is from `0.0` to `+infinity`), and
         * that higher values are better.
         */
        POSITIVE_QUANTITY,
        /**
         * Indicates a quantity-type [Attribute] (whose range is from `0.0` to `+infinity`), and
         * that lower values are better.
         */
        NEGATIVE_QUANTITY,
        /**
         * Indicates a quantity-type [Attribute] (whose range is from `0.0` to `1.0`), and
         * that higher values are better.
         */
        POSITIVE_QUALITY,
        /**
         * Indicates a quantity-type [Attribute] (whose range is from `0.0` to `1.0`), and that
         * lower values are better.
         */
        NEGATIVE_QUALITY,
        /**
         * Indicates an unbounded-type [Attribute] (whose range is from `-infinity` to `+infinity`), and
         * that higher values are better.
         */
        POSITIVE_UNBOUNDED,
        /**
         * Indicates an unbounded-type [Attribute] (whose range is from `-infinity` to `+infinity`), and
         * that lower values are better.
         */
        NEGATIVE_UNBOUNDED
    }
}
