package runtimemodels.chazm.api.entity

import runtimemodels.chazm.api.id.CapabilityId
import runtimemodels.chazm.api.id.Identifiable
import runtimemodels.chazm.api.organization.Organization

/**
 * The [Capability] interface defines a capability entity of an [Organization].
 *
 * @author Christopher Zhong
 * @since 3.4
 */
interface Capability : Identifiable<Capability> {
    /**
     * The [CapabilityId] of this [Capability].
     */
    override val id: CapabilityId
}
