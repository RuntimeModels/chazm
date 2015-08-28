package io.github.runtimemodels.chazm.event;

import io.github.runtimemodels.chazm.entity.SpecificationGoal;

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
	 * @param category
	 *            the {@linkplain EventCategory}.
	 * @param goal
	 *            the {@linkplain SpecificationGoal}.
	 *
	 * @return a {@linkplain SpecificationGoalEvent}.
	 */
	SpecificationGoalEvent build(EventCategory category, SpecificationGoal goal);

}