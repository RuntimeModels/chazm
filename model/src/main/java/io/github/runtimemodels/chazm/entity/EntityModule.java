package io.github.runtimemodels.chazm.entity;

import io.github.runtimemodels.chazm.entity.Agent;
import io.github.runtimemodels.chazm.entity.Attribute;
import io.github.runtimemodels.chazm.entity.Capability;
import io.github.runtimemodels.chazm.entity.Characteristic;
import io.github.runtimemodels.chazm.entity.InstanceGoal;
import io.github.runtimemodels.chazm.entity.Pmf;
import io.github.runtimemodels.chazm.entity.Policy;
import io.github.runtimemodels.chazm.entity.Role;
import io.github.runtimemodels.chazm.entity.SpecificationGoal;
import io.github.runtimemodels.chazm.id.IdModule;

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
		final FactoryModuleBuilder factoryModuleBuilder = new FactoryModuleBuilder().implement(Agent.class, AgentEntity.class)
				.implement(Attribute.class, AttributeEntity.class).implement(Capability.class, CapabilityEntity.class)
				.implement(Characteristic.class, CharacteristicEntity.class).implement(InstanceGoal.class, InstanceGoalEntity.class)
				.implement(Pmf.class, PmfEntity.class).implement(Policy.class, PolicyEntity.class).implement(Role.class, RoleEntity.class)
				.implement(SpecificationGoal.class, SpecificationGoalEntity.class);
		install(factoryModuleBuilder.build(AgentFactory.class));
		install(factoryModuleBuilder.build(AttributeFactory.class));
		install(factoryModuleBuilder.build(CapabilityFactory.class));
		install(factoryModuleBuilder.build(CharacteristicFactory.class));
		install(factoryModuleBuilder.build(InstanceGoalFactory.class));
		install(factoryModuleBuilder.build(PmfFactory.class));
		install(factoryModuleBuilder.build(PolicyFactory.class));
		install(factoryModuleBuilder.build(RoleFactory.class));
		install(factoryModuleBuilder.build(SpecificationGoalFactory.class));
		install(factoryModuleBuilder.build(EntityFactory.class));
	}

}
