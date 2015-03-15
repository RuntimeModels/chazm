package org.models.organization.entity.manager;

import java.util.Collection;
import java.util.Set;

import org.models.organization.entity.SpecificationGoal;
import org.models.organization.id.UniqueId;

/**
 * The {@linkplain SpecificationGoalManager} interface defines the necessary APIs for managing {@linkplain SpecificationGoal}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface SpecificationGoalManager {
	/**
	 * Adds a {@linkplain SpecificationGoal} to this {@linkplain SpecificationGoalManager}.
	 *
	 * @param goal
	 *            the {@linkplain SpecificationGoal} to add.
	 */
	void addSpecificationGoal(SpecificationGoal goal);

	/**
	 * Adds a set of {@linkplain SpecificationGoal}s to this {@linkplain SpecificationGoalManager}.
	 *
	 * @param goals
	 *            the set of {@linkplain SpecificationGoal}s to add.
	 */
	void addSpecificationGoals(Collection<SpecificationGoal> goals);

	/**
	 * Returns a {@linkplain SpecificationGoal} from this {@linkplain SpecificationGoalManager}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents a {@linkplain SpecificationGoal} to retrieve.
	 * @return the {@linkplain SpecificationGoal} if it exists, <code>null</code> otherwise.
	 */
	SpecificationGoal getSpecificationGoal(UniqueId<SpecificationGoal> id);

	/**
	 * Returns a set of {@linkplain SpecificationGoal}s from this {@linkplain SpecificationGoalManager}.
	 *
	 * @return a set of {@linkplain SpecificationGoal}s.
	 */
	Set<SpecificationGoal> getSpecificationGoals();

	/**
	 * Removes a {@linkplain SpecificationGoal} from this {@linkplain SpecificationGoalManager}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain SpecificationGoal} to remove.
	 */
	void removeSpecificationGoal(UniqueId<SpecificationGoal> id);

	/**
	 * Removes a set of {@linkplain SpecificationGoal}s from this {@linkplain SpecificationGoalManager}.
	 *
	 * @param ids
	 *            a set of {@linkplain UniqueId}s that represents the {@linkplain SpecificationGoal}s to remove.
	 */
	void removeSpecificationGoals(Collection<UniqueId<SpecificationGoal>> ids);

	/**
	 * Removes all {@linkplain SpecificationGoal}s from this {@linkplain SpecificationGoalManager}.
	 */
	void removeAllSpecificationGoals();
}