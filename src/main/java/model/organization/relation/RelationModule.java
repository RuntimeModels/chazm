package model.organization.relation;

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
		install(new FactoryModuleBuilder().implement(Achieves.class, AchievesRelation.class).implement(Assignment.class, AssignmentRelation.class)
				.implement(Contains.class, ContainsRelation.class).implement(Has.class, HasRelation.class).implement(Moderates.class, ModeratesRelation.class)
				.implement(Needs.class, NeedsRelation.class).implement(Possesses.class, PossessesRelation.class)
				.implement(Requires.class, RequiresRelation.class).implement(Uses.class, UsesRelation.class).build(RelationFactory.class));
	}

}
