package model.organization.entity;

import model.organization.id.IdModule;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * The {@linkplain EntityModule} class provides a Guice binding module for entities.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class EntityModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new IdModule());
		final FactoryModuleBuilder factoryModuleBuilder = new FactoryModuleBuilder();
		install(factoryModuleBuilder.implement(Agent.class, AgentEntity.class).build(AgentFactory.class));
		install(factoryModuleBuilder.implement(Attribute.class, AttributeEntity.class).build(AttributeFactory.class));
		install(factoryModuleBuilder.implement(Capability.class, CapabilityEntity.class).build(CapabilityFactory.class));
		install(factoryModuleBuilder.implement(Characteristic.class, CharacteristicEntity.class).build(CharacteristicFactory.class));
		install(factoryModuleBuilder.implement(InstanceGoal.class, InstanceGoalEntity.class).build(InstanceGoalFactory.class));
		install(factoryModuleBuilder.implement(Pmf.class, PmfEntity.class).build(PmfFactory.class));
		install(factoryModuleBuilder.implement(Policy.class, PolicyEntity.class).build(PolicyFactory.class));
		install(factoryModuleBuilder.implement(Role.class, RoleEntity.class).build(RoleFactory.class));
		install(factoryModuleBuilder.implement(SpecificationGoal.class, SpecificationGoalEntity.class).build(SpecificationGoalFactory.class));
		install(factoryModuleBuilder.build(EntityFactory.class));
	}

}
