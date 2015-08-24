package io.github.chriszhong.model.organization.event;

import io.github.chriszhong.model.organization.entity.InstanceGoal;

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
	 * @param category
	 *            the {@linkplain EventCategory}.
	 * @param goal
	 *            the {@linkplain InstanceGoal}.
	 *
	 * @return a {@linkplain InstanceGoalEvent}.
	 */
	InstanceGoalEvent build(EventCategory category, InstanceGoal goal);

}