package model.organization.entity;

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
		install(new FactoryModuleBuilder().implement(Agent.class, AgentEntity.class).implement(Attribute.class, AttributeEntity.class)
				.implement(Capability.class, CapabilityEntity.class).implement(Characteristic.class, CharacteristicEntity.class)
				.implement(InstanceGoal.class, InstanceGoalEntity.class).implement(Pmf.class, PmfEntity.class).implement(Policy.class, PolicyEntity.class)
				.implement(Role.class, RoleEntity.class).implement(SpecificationGoal.class, SpecificationGoalEntity.class).build(EntityFactory.class));
	}

}
