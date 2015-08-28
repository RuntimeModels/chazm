package io.github.runtimemodels.chazm.relation;

/**
 * The {@linkplain RelationFactory} interface defines the APIs for constructing relations.
 *
 * @author Christopher Zhong
 * @see AchievesFactory
 * @see AssignmentFactory
 * @see ContainsFactory
 * @see HasFactory
 * @see ModeratesFactory
 * @see NeedsFactory
 * @see PossessesFactory
 * @see RequiresFactory
 * @see UsesFactory
 * @since 7.0.0
 */
public interface RelationFactory extends AchievesFactory, AssignmentFactory, ContainsFactory, HasFactory, ModeratesFactory, NeedsFactory, PossessesFactory,
		RequiresFactory, UsesFactory {}
