package model.organization;

import model.organization.event.EventModule;
import model.organization.function.FunctionModule;
import model.organization.relation.RelationModule;
import notification.NotificationModule;

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
	}

}
