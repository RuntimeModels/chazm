package runtimemodels.chazm.api.entity

import runtimemodels.chazm.api.function.Goodness
import runtimemodels.chazm.api.id.Identifiable
import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.organization.Organization

/**
 * The [Role] interface defines a role entity of an [Organization].
 *
 * @author Christopher Zhong
 * @see Goodness
 *
 * @since 3.4
 */
interface Role : Identifiable<Role, RoleId>
