package model.organization.event;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * The {@linkplain EventModule} class provides a Guice binding module for events (such as pub/sub event notifications).
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class EventModule extends AbstractModule {

	@Override
	protected void configure() {
		// .implement(AchievesEvent.class, AchievesEvent.class).implement(AgentEvent.class, AgentEvent.class)
		// .implement(AssignmentEvent.class, AssignmentEvent.class).implement(AttributeEvent.class, AttributeEvent.class)
		// .implement(CapabilityEvent.class, CapabilityEvent.class).implement(CharacteristicEvent.class, CharacteristicEvent.class)
		// .implement(ContainsEvent.class, ContainsEvent.class).implement(HasEvent.class, HasEvent.class)
		// .implement(InstanceGoalEvent.class, InstanceGoalEvent.class).implement(ModeratesEvent.class, ModeratesEvent.class)
		// .implement(NeedsEvent.class, NeedsEvent.class).implement(PmfEvent.class, PmfEvent.class).implement(PolicyEvent.class, PolicyEvent.class)
		// .implement(PossessesEvent.class, PossessesEvent.class).implement(RequiresEvent.class, RequiresEvent.class)
		// .implement(RoleEvent.class, RoleEvent.class).implement(SpecificationGoalEvent.class, SpecificationGoalEvent.class)
		// .implement(UsesEvent.class, UsesEvent.class)
		final FactoryModuleBuilder factoryModuleBuilder = new FactoryModuleBuilder();
		install(factoryModuleBuilder.build(EventFactory.class));
	}

}
