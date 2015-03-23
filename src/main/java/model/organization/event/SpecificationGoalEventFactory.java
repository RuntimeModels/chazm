package model.organization.event;

import model.organization.entity.SpecificationGoal;

/**
 * The {@linkplain SpecificationGoalEventFactory} interface defines the API for constructing {@linkplain SpecificationGoalEvent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface SpecificationGoalEventFactory {

	/**
	 * Constructs an {@linkplain SpecificationGoalEvent}.
	 *
	 * @param goal
	 *            the {@linkplain SpecificationGoal}.
	 * @param category
	 *            the {@linkplain EventCategory}.
	 * @return a {@linkplain SpecificationGoalEvent}.
	 */
	SpecificationGoalEvent build(SpecificationGoal goal, EventCategory category);

}