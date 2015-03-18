package model.organization;

import model.organization.function.FunctionModule;
import model.organization.relation.RelationModule;

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
		install(new RelationModule());
		install(new FunctionModule());
		bind(Organization.class).to(OrganizationImpl.class);
	}

}
