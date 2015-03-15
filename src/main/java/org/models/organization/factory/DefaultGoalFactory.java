package org.models.organization.factory;

import org.models.organization.entity.InstanceGoal;
import org.models.organization.entity.InstanceGoalEntity;
import org.models.organization.entity.SpecificationGoal;
import org.models.organization.entity.SpecificationGoalEntity;
import org.models.organization.id.UniqueId;

/**
 * The {@linkplain DefaultGoalFactory} class is an implementation of {@linkplain GoalFactory}.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class DefaultGoalFactory implements GoalFactory {
	@Override
	public SpecificationGoal buildGoal(final UniqueIdFactory idFactory, final Object id) {
		return new SpecificationGoalEntity(idFactory.buildId(SpecificationGoal.class, id.toString()));
	}

	@Override
	public InstanceGoal getInstanceGoal(final SpecificationGoal goal, final UniqueId<InstanceGoal> id, final InstanceGoal.P parameter) {
		return new InstanceGoalEntity(goal, id, parameter);
	}
}
