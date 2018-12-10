package runtimemodels.chazm.api.relation

import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.entity.Pmf
import runtimemodels.chazm.api.id.AttributeId
import runtimemodels.chazm.api.id.PmfId

/**
 * The [ModeratesManager] interface defines the APIs for managing a set of [Moderates] relations.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface ModeratesManager {
    /**
     * Adds a [Moderates] relation between a [Pmf] and an [Attribute] to this [ModeratesManager].
     *
     * @param moderates       the [Moderates] to add.
     */
    fun add(moderates: Moderates)

    /**
     * Returns the [Moderates] relation between an [Pmf] and an [Attribute] from this [HasManager].
     *
     * @param pmfId           the [PmfId] that represents the [Pmf].
     * @param attributeId the [AttributeId] that represents the [Attribute].
     * @return the [Moderates] relation if it exists, `null` otherwise.
     */
    operator fun get(pmfId: PmfId, attributeId: AttributeId): Moderates?

    /**
     * Returns a [Map] of [Attribute]s that are contained by a [Pmf] from this [ModeratesManager].
     *
     * @param id the [PmfId] that represents the [Pmf].
     * @return a [Map] of [Attribute]s.
     */
    operator fun get(id: PmfId): Map<AttributeId, Moderates>

    /**
     * Returns a [Map] of [Pmf]s that contains a [Attribute] from this [ModeratesManager].
     *
     * @param id the [AttributeId] that represents the [Attribute].
     * @return a [Map] of [Pmf]s.
     */
    operator fun get(id: AttributeId): Map<PmfId, Moderates>

    /**
     * Removes a moderates relation between a [Pmf] and ad [Attribute] in this [ModeratesManager].
     *
     * @param pmfId       the [PmfId] that represents the [Pmf].
     * @param attributeId the [AttributeId] that represents the [Attribute].
     * @return the [Moderates] that was removed, `null` otherwise.
     */
    fun remove(pmfId: PmfId, attributeId: AttributeId): Moderates?

    /**
     * Removes all [Moderates] relations associated to a [Pmf] from this [ModeratesManager].
     *
     * @param id the [PmfId] that represents the [Pmf].
     */
    fun remove(id: PmfId)

    /**
     * Removes all [Moderates] relations associated to a [Attribute] from this [ModeratesManager].
     *
     * @param id the [AttributeId] that represents the [Attribute].
     */
    fun remove(id: AttributeId)

}
