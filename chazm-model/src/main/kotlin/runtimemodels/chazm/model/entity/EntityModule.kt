package runtimemodels.chazm.model.entity

import com.google.inject.AbstractModule
import com.google.inject.assistedinject.FactoryModuleBuilder
import runtimemodels.chazm.api.entity.*
import runtimemodels.chazm.model.id.IdModule

/**
 * The [EntityModule] class provides a Guice binding module for entities.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
class EntityModule : AbstractModule() {

    override fun configure() {
        install(IdModule())
        val factoryModuleBuilder = FactoryModuleBuilder()
            .implement(Agent::class.java, AgentEntity::class.java)
            .implement(Attribute::class.java, AttributeEntity::class.java)
            .implement(Capability::class.java, CapabilityEntity::class.java)
            .implement(Characteristic::class.java, CharacteristicEntity::class.java)
            .implement(InstanceGoal::class.java, InstanceGoalEntity::class.java)
            .implement(Pmf::class.java, PmfEntity::class.java)
            .implement(Policy::class.java, PolicyEntity::class.java).implement(Role::class.java, RoleEntity::class.java)
            .implement(SpecificationGoal::class.java, SpecificationGoalEntity::class.java)
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
