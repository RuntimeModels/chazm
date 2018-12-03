package runtimemodels.chazm.api.entity

import runtimemodels.chazm.api.id.Identifiable
import runtimemodels.chazm.api.id.PolicyId
import runtimemodels.chazm.api.organization.Organization

/**
 * The [Policy] interface defines a policy entity of an [Organization].
 *
 * @author Christopher Zhong
 * @since 3.4
 */
interface Policy : Identifiable<Policy, PolicyId>
