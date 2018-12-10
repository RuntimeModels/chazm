package runtimemodels.chazm.model.entity

import runtimemodels.chazm.api.entity.Policy
import runtimemodels.chazm.api.entity.PolicyId
import runtimemodels.chazm.api.id.UniqueId

/**
 * The [PolicyFactory] interface defines the APIs for constructing [Policy]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
interface PolicyFactory {
    /**
     * Constructs a [Policy].
     *
     * @param id the [UniqueId] that represents the [Policy].
     * @return a [Policy].
     */
    fun build(id: PolicyId): Policy
}
