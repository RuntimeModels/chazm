package runtimemodels.chazm.api.entity

/**
 * The [PolicyManager] interface defines APIs that can be used for managing a set [Policy]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface PolicyManager : Map<PolicyId, Policy> {
    /**
     * Adds a [Policy] to this [PolicyManager].
     *
     * @param policy the [Policy] to add.
     */
    fun add(policy: Policy)

    /**
     * Removes a [Policy] from this [PolicyManager].
     *
     * @param id the [PolicyId] that represents the [Policy] to remove.
     * @return the [Policy] that was removed, `null` otherwise.
     */
    fun remove(id: PolicyId): Policy?
}
