package runtimemodels.chazm.model.guice

import com.google.inject.AbstractModule
import com.google.inject.assistedinject.FactoryModuleBuilder
import runtimemodels.chazm.model.event.*

/**
 * The [EventModule] class provides a Guice binding module for events (such as pub/sub event notifications).
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
class EventModule : AbstractModule() {

    override fun configure() {
        val factoryModuleBuilder = FactoryModuleBuilder()
        install(factoryModuleBuilder.build(AchievesEventFactory::class.java))
        install(factoryModuleBuilder.build(AgentEventFactory::class.java))
        install(factoryModuleBuilder.build(AssignmentEventFactory::class.java))
        install(factoryModuleBuilder.build(AttributeEventFactory::class.java))
        install(factoryModuleBuilder.build(CapabilityEventFactory::class.java))
        install(factoryModuleBuilder.build(CharacteristicEventFactory::class.java))
        install(factoryModuleBuilder.build(ContainsEventFactory::class.java))
        install(factoryModuleBuilder.build(HasEventFactory::class.java))
        install(factoryModuleBuilder.build(InstanceGoalEventFactory::class.java))
        install(factoryModuleBuilder.build(ModeratesEventFactory::class.java))
        install(factoryModuleBuilder.build(NeedsEventFactory::class.java))
        install(factoryModuleBuilder.build(PmfEventFactory::class.java))
        install(factoryModuleBuilder.build(PolicyEventFactory::class.java))
        install(factoryModuleBuilder.build(PossessesEventFactory::class.java))
        install(factoryModuleBuilder.build(RequiresEventFactory::class.java))
        install(factoryModuleBuilder.build(RoleEventFactory::class.java))
        install(factoryModuleBuilder.build(SpecificationGoalEventFactory::class.java))
        install(factoryModuleBuilder.build(UsesEventFactory::class.java))
        install(factoryModuleBuilder.build(EventFactory::class.java))
    }

}
