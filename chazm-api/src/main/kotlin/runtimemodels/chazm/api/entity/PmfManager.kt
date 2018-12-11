package runtimemodels.chazm.api.entity

/**
 * The [PmfManager] interface defines APIs that can be used for managing a set [Pmf]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface PmfManager : Map<PmfId, Pmf> {
    /**
     * Adds a [Pmf] to this [PmfManager].
     *
     * @param pmf the [Pmf] to add.
     */
    fun add(pmf: Pmf)

    /**
     * Removes a [Pmf] from this [PmfManager].
     *
     * @param id the [PmfId] that represents the [Pmf] to remove.
     * @return the [Pmf] that was removed, `null` otherwise.
     */
    fun remove(id: PmfId): Pmf?
}
