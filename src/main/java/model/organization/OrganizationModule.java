package model.organization;

import model.organization.function.FunctionModule;
import model.organization.relation.RelationModule;

import com.google.inject.AbstractModule;

import event.EventModule;

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
	}

}
