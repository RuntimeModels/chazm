package io.github.runtimemodels.chazm.event;

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
		final FactoryModuleBuilder factoryModuleBuilder = new FactoryModuleBuilder();
		install(factoryModuleBuilder.build(AchievesEventFactory.class));
		install(factoryModuleBuilder.build(AgentEventFactory.class));
		install(factoryModuleBuilder.build(AssignmentEventFactory.class));
		install(factoryModuleBuilder.build(AttributeEventFactory.class));
		install(factoryModuleBuilder.build(CapabilityEventFactory.class));
		install(factoryModuleBuilder.build(CharacteristicEventFactory.class));
		install(factoryModuleBuilder.build(ContainsEventFactory.class));
		install(factoryModuleBuilder.build(HasEventFactory.class));
		install(factoryModuleBuilder.build(InstanceGoalEventFactory.class));
		install(factoryModuleBuilder.build(ModeratesEventFactory.class));
		install(factoryModuleBuilder.build(NeedsEventFactory.class));
		install(factoryModuleBuilder.build(PmfEventFactory.class));
		install(factoryModuleBuilder.build(PolicyEventFactory.class));
		install(factoryModuleBuilder.build(PossessesEventFactory.class));
		install(factoryModuleBuilder.build(RequiresEventFactory.class));
		install(factoryModuleBuilder.build(RoleEventFactory.class));
		install(factoryModuleBuilder.build(SpecificationGoalEventFactory.class));
		install(factoryModuleBuilder.build(UsesEventFactory.class));
		install(factoryModuleBuilder.build(EventFactory.class));
	}

}
