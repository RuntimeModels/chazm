package runtimemodels.chazm.api.relation.manager

import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.entity.Pmf
import runtimemodels.chazm.api.id.AttributeId
import runtimemodels.chazm.api.id.PmfId
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.api.relation.Moderates

/**
 * The [ModeratesManager] interface defines the APIs for managing [Moderates].
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface ModeratesManager {
    /**
     * Creates a moderates relation between a [Pmf] and an [Attribute] in this [ModeratesManager].
     *
     * @param pmfId       the [UniqueId] that represents the [Pmf].
     * @param attributeId the [UniqueId] that represents the [Attribute].
     */
    fun addModerates(pmfId: PmfId, attributeId: AttributeId)

    /**
     * Returns an [Attribute], if it exists, that is moderated by a [Pmf] in this [ModeratesManager].
     *
     * @param id the [UniqueId] that represents the [Pmf]
     * @return an [Attribute] if it exists, `null` otherwise.
     */
    fun getModerates(id: PmfId): Attribute?

    /**
     * Returns a set of [Pmf]s that moderates an [Attribute] in this [ModeratesManager].
     *
     * @param id the [UniqueId] that represents the [Attribute].
     * @return a set of [Pmf]s.
     */
    fun getModeratedBy(id: AttributeId): Set<Pmf>

    /**
     * Removes a moderates relation between a [Pmf] and ad [Attribute] in this [ModeratesManager].
     *
     * @param pmfId       the [UniqueId] that represents the [Pmf].
     * @param attributeId the [UniqueId] that represents the [Attribute].
     */
    fun removeModerates(pmfId: PmfId, attributeId: AttributeId)

    /**
     * Removes all moderates relations from this [ModeratesManager].
     */
    fun removeAllModerates()
}
