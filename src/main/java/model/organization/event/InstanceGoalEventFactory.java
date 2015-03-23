package model.organization.event;

import model.organization.entity.InstanceGoal;

/**
 * The {@linkplain InstanceGoalEventFactory} interface defines the API for constructing {@linkplain InstanceGoalEvent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface InstanceGoalEventFactory {

	/**
	 * Constructs an {@linkplain InstanceGoalEvent}.
	 *
	 * @param goal
	 *            the {@linkplain InstanceGoal}.
	 * @param category
	 *            the {@linkplain EventCategory}.
	 * @return a {@linkplain InstanceGoalEvent}.
	 */
	InstanceGoalEvent build(InstanceGoal goal, EventCategory category);

}