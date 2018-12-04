package runtimemodels.chazm.api.entity

import runtimemodels.chazm.api.id.Identifiable
import runtimemodels.chazm.api.id.PmfId
import runtimemodels.chazm.api.organization.Organization

/**
 * The [Pmf] interface defines the performance moderator function entity of an [Organization].
 *
 * @author Christopher Zhong
 * @since 6.0
 */
interface Pmf : Identifiable<Pmf, PmfId>
