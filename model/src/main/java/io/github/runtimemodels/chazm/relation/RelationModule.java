package io.github.runtimemodels.chazm.relation;

import io.github.runtimemodels.chazm.entity.EntityModule;
import io.github.runtimemodels.chazm.relation.Achieves;
import io.github.runtimemodels.chazm.relation.Assignment;
import io.github.runtimemodels.chazm.relation.Contains;
import io.github.runtimemodels.chazm.relation.Has;
import io.github.runtimemodels.chazm.relation.Moderates;
import io.github.runtimemodels.chazm.relation.Needs;
import io.github.runtimemodels.chazm.relation.Possesses;
import io.github.runtimemodels.chazm.relation.Requires;
import io.github.runtimemodels.chazm.relation.Uses;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * The {@linkplain RelationModule} class provides a Guice binding module for relations.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class RelationModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new EntityModule());
		final FactoryModuleBuilder factoryModuleBuilder = new FactoryModuleBuilder().implement(Achieves.class, AchievesRelation.class)
				.implement(Assignment.class, AssignmentRelation.class).implement(Contains.class, ContainsRelation.class)
				.implement(Has.class, HasRelation.class).implement(Moderates.class, ModeratesRelation.class).implement(Needs.class, NeedsRelation.class)
				.implement(Possesses.class, PossessesRelation.class).implement(Requires.class, RequiresRelation.class)
				.implement(Uses.class, UsesRelation.class);
		install(factoryModuleBuilder.build(AchievesFactory.class));
		install(factoryModuleBuilder.build(AssignmentFactory.class));
		install(factoryModuleBuilder.build(ContainsFactory.class));
		install(factoryModuleBuilder.build(HasFactory.class));
		install(factoryModuleBuilder.build(ModeratesFactory.class));
		install(factoryModuleBuilder.build(NeedsFactory.class));
		install(factoryModuleBuilder.build(PossessesFactory.class));
		install(factoryModuleBuilder.build(RequiresFactory.class));
		install(factoryModuleBuilder.build(UsesFactory.class));
		install(factoryModuleBuilder.build(RelationFactory.class));
	}

}
