package runtimemodels.chazm.model.guice

import com.google.inject.AbstractModule
import com.google.inject.assistedinject.FactoryModuleBuilder
import runtimemodels.chazm.api.entity.*
import runtimemodels.chazm.model.entity.*
import runtimemodels.chazm.model.entity.impl.*

/**
 * The [EntityModule] class provides a Guice binding module for entities.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
class EntityModule : AbstractModule() {

    override fun configure() {
        val factoryModuleBuilder = FactoryModuleBuilder()
            .implement(Agent::class.java, DefaultAgent::class.java)
            .implement(Attribute::class.java, DefaultAttribute::class.java)
            .implement(Capability::class.java, DefaultCapability::class.java)
            .implement(Characteristic::class.java, DefaultCharacteristic::class.java)
            .implement(InstanceGoal::class.java, DefaultInstanceGoal::class.java)
            .implement(Pmf::class.java, DefaultPmf::class.java)
            .implement(Policy::class.java, DefaultPolicy::class.java)
            .implement(Role::class.java, DefaultRole::class.java)
            .implement(SpecificationGoal::class.java, DefaultSpecificationGoal::class.java)
        install(factoryModuleBuilder.build(AgentFactory::class.java))
        install(factoryModuleBuilder.build(AttributeFactory::class.java))
        install(factoryModuleBuilder.build(CapabilityFactory::class.java))
        install(factoryModuleBuilder.build(CharacteristicFactory::class.java))
        install(factoryModuleBuilder.build(InstanceGoalFactory::class.java))
        install(factoryModuleBuilder.build(PmfFactory::class.java))
        install(factoryModuleBuilder.build(PolicyFactory::class.java))
        install(factoryModuleBuilder.build(RoleFactory::class.java))
        install(factoryModuleBuilder.build(SpecificationGoalFactory::class.java))
        install(factoryModuleBuilder.build(EntityFactory::class.java))
    }

}
