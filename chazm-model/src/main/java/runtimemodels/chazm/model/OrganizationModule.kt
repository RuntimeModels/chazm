package runtimemodels.chazm.model

import com.google.inject.AbstractModule
import runtimemodels.chazm.api.Organization
import runtimemodels.chazm.model.aop.validation.ValidationModule
import runtimemodels.chazm.model.event.EventModule
import runtimemodels.chazm.model.function.FunctionModule
import runtimemodels.chazm.model.notification.NotificationModule
import runtimemodels.chazm.model.relation.RelationModule

/**
 * The [OrganizationModule] class provides a Guice binding module for entities.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
class OrganizationModule : AbstractModule() {

    override fun configure() {
        bind(Organization::class.java).to(DefaultOrganization::class.java)
        install(RelationModule())
        install(FunctionModule())
        install(EventModule())
        install(NotificationModule())
        install(ValidationModule())
    }

}
