package model.organization.factory;

import model.organization.NullCheck;
import model.organization.entities.InstanceGoal;
import model.organization.entities.InstanceGoalEntity;
import model.organization.entities.SpecificationGoal;
import model.organization.entities.SpecificationGoalEntity;
import model.organization.id.UniqueId;
import model.organization.id.UniqueIdFactory;

/**
 * The {@linkplain DefaultGoalFactory} class is an implementation of {@linkplain GoalFactory}.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class DefaultGoalFactory implements GoalFactory, NullCheck {
	@Override
	public <T> SpecificationGoal buildGoal(final UniqueIdFactory idFactory, final T id) {
		checkNotNull(idFactory, "idFactory");
		checkNotNull(id, "id");
		return new SpecificationGoalEntity(idFactory.buildId(SpecificationGoal.class, id.toString()));
	}

	@Override
	public InstanceGoal getInstanceGoal(final SpecificationGoal goal, final UniqueId<InstanceGoal> id, final InstanceGoal.P parameter) {
		checkNotNull(goal, "goal");
		checkNotNull(id, "id");
		return new InstanceGoalEntity(goal, id, parameter);
	}
}
