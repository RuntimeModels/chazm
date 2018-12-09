package runtimemodels.chazm.model.guice

import com.google.inject.AbstractModule
import com.google.inject.assistedinject.FactoryModuleBuilder
import runtimemodels.chazm.api.relation.*
import runtimemodels.chazm.model.factory.RelationFactory
import runtimemodels.chazm.model.factory.relation.*
import runtimemodels.chazm.model.relation.*

/**
 * The [RelationModule] class provides a Guice binding module for relations.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
class RelationModule : AbstractModule() {

    override fun configure() {
        install(EntityModule())
        val factoryModuleBuilder = FactoryModuleBuilder()
            .implement(Achieves::class.java, AchievesRelation::class.java)
            .implement(Assignment::class.java, AssignmentRelation::class.java)
            .implement(Contains::class.java, ContainsRelation::class.java)
            .implement(Has::class.java, HasRelation::class.java)
            .implement(Moderates::class.java, ModeratesRelation::class.java)
            .implement(Needs::class.java, NeedsRelation::class.java)
            .implement(Possesses::class.java, PossessesRelation::class.java)
            .implement(Requires::class.java, RequiresRelation::class.java)
            .implement(Uses::class.java, UsesRelation::class.java)
        install(factoryModuleBuilder.build(AchievesFactory::class.java))
        install(factoryModuleBuilder.build(AssignmentFactory::class.java))
        install(factoryModuleBuilder.build(ContainsFactory::class.java))
        install(factoryModuleBuilder.build(HasFactory::class.java))
        install(factoryModuleBuilder.build(ModeratesFactory::class.java))
        install(factoryModuleBuilder.build(NeedsFactory::class.java))
        install(factoryModuleBuilder.build(PossessesFactory::class.java))
        install(factoryModuleBuilder.build(RequiresFactory::class.java))
        install(factoryModuleBuilder.build(UsesFactory::class.java))
        install(factoryModuleBuilder.build(RelationFactory::class.java))
    }

}
