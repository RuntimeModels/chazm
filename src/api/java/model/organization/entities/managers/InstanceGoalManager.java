package model.organization.entities.managers;

import java.util.Collection;
import java.util.Set;

import model.organization.entities.InstanceGoal;
import model.organization.id.UniqueId;

/**
 * The {@linkplain InstanceGoalManager} interface defines the necessary APIs for managing {@linkplain InstanceGoal}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface InstanceGoalManager {
	/**
	 * Adds an {@linkplain InstanceGoal} to this {@linkplain InstanceGoalManager}.
	 *
	 * @param goal
	 *            the {@linkplain InstanceGoal} to add.
	 */
	void addInstanceGoal(InstanceGoal goal);

	/**
	 * Adds a set of {@linkplain InstanceGoal}s to this {@linkplain InstanceGoalManager}.
	 *
	 * @param goals
	 *            the set of {@linkplain InstanceGoal} to add.
	 */
	void addInstanceGoals(Collection<InstanceGoal> goals);

	/**
	 * Returns an {@linkplain InstanceGoal} from this {@linkplain InstanceGoalManager}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain InstanceGoal} to retrieve.
	 * @return the {@linkplain InstanceGoal} if it exists, <code>null</code> otherwise.
	 */
	InstanceGoal getInstanceGoal(UniqueId<InstanceGoal> id);

	/**
	 * Returns a set of {@linkplain InstanceGoal} from this {@linkplain InstanceGoalManager}.
	 *
	 * @return the set of {@linkplain InstanceGoal}.
	 */
	Set<InstanceGoal> getInstanceGoals();

	/**
	 * Removes an {@linkplain InstanceGoal} from this {@linkplain InstanceGoalManager}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain InstanceGoal} to remove.
	 */
	void removeInstanceGoal(UniqueId<InstanceGoal> id);

	/**
	 * Removes a set of {@linkplain InstanceGoal}s from this {@linkplain InstanceGoalManager}.
	 *
	 * @param ids
	 *            the set of {@linkplain UniqueId} that represents the {@linkplain InstanceGoal}s to remove.
	 */
	void removeInstanceGoals(Collection<UniqueId<InstanceGoal>> ids);

	/**
	 * Removes all {@linkplain InstanceGoal}s from this {@linkplain InstanceGoalManager}.
	 */
	void removeAllInstanceGoals();
}