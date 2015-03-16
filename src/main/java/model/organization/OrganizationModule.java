package model.organization;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * The {@linkplain OrganizationModule} class provides a Guice binding module for entities.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class OrganizationModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new FactoryModuleBuilder().implement(Organization.class, OrganizationImpl.class).build(OrganizationFactory.class));
	}

}
