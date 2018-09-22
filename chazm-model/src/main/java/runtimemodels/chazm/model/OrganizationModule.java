package runtimemodels.chazm.model;

import com.google.inject.AbstractModule;
import runtimemodels.chazm.aop.validation.ValidationModule;
import runtimemodels.chazm.api.Organization;
import runtimemodels.chazm.model.event.EventModule;
import runtimemodels.chazm.model.function.FunctionModule;
import runtimemodels.chazm.model.relation.RelationModule;
import runtimemodels.chazm.notification.NotificationModule;

/**
 * The {@linkplain OrganizationModule} class provides a Guice binding module for entities.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class OrganizationModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Organization.class).to(DefaultOrganization.class);
        install(new RelationModule());
        install(new FunctionModule());
        install(new EventModule());
        install(new NotificationModule());
        install(new ValidationModule());
    }

}
