package io.github.runtimemodels.chazm;

import io.github.runtimemodels.aop.validation.ValidationModule;
import io.github.runtimemodels.chazm.Organization;
import io.github.runtimemodels.chazm.event.EventModule;
import io.github.runtimemodels.chazm.function.FunctionModule;
import io.github.runtimemodels.chazm.relation.RelationModule;
import io.github.runtimemodels.notification.NotificationModule;

import com.google.inject.AbstractModule;

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
