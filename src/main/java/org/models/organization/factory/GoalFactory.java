package org.models.organization.factory;

import org.models.organization.entity.InstanceGoal;
import org.models.organization.entity.SpecificationGoal;
import org.models.organization.id.UniqueId;

/**
 * The {@linkplain GoalFactory} interface defines the APIs for obtaining {@linkplain SpecificationGoal}s and {@linkplain InstanceGoal}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface GoalFactory {
	/**
	 * Returns a {@linkplain SpecificationGoal}.
	 *
	 * @param idFactory
	 * @param id
	 *            the id of the {@linkplain SpecificationGoal}.
	 * @return a {@linkplain SpecificationGoal} or <code>null</code> if there are issues.
	 */
	SpecificationGoal buildGoal(UniqueIdFactory idFactory, Object id);

	/**
	 * Returns an {@linkplain InstanceGoal}.
	 *
	 * @param goal
	 *            the {@linkplain SpecificationGoal} of the {@linkplain InstanceGoal}.
	 * @param id
	 *            the instance id of the {@linkplain InstanceGoal}.
	 * @param parameter
	 *            the parameter of the {@linkplain InstanceGoal}.
	 * @return an {@linkplain InstanceGoal} or <code>null</code> if there are issues.
	 */
	InstanceGoal getInstanceGoal(SpecificationGoal goal, UniqueId<InstanceGoal> id, InstanceGoal.P parameter);
}