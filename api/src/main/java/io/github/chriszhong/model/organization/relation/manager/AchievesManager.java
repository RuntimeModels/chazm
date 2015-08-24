package io.github.chriszhong.model.organization.relation.manager;

import io.github.chriszhong.model.organization.entity.Role;
import io.github.chriszhong.model.organization.entity.SpecificationGoal;
import io.github.chriszhong.model.organization.id.UniqueId;

import java.util.Set;

/**
 * The {@linkplain AchievesManager} interface defines the APIs for managing achieves relations.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface AchievesManager {
	/**
	 * Creates an achieves relation between a {@linkplain Role} and a {@linkplain SpecificationGoal} in this {@linkplain AchievesManager}.
	 *
	 * @param roleId
	 *            the {@linkplain UniqueId} identifying the {@linkplain Role} for the achieves relation.
	 * @param goalId
	 *            the {@linkplain UniqueId} identifying the {@linkplain SpecificationGoal} for the achieves relation.
	 */
	void addAchieves(UniqueId<Role> roleId, UniqueId<SpecificationGoal> goalId);

	/**
	 * Returns a set of {@linkplain SpecificationGoal}s that is achieved by a {@linkplain Role} from this {@linkplain AchievesManager}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Role}.
	 *
	 * @return a set of {@linkplain SpecificationGoal}s.
	 */
	Set<SpecificationGoal> getAchieves(UniqueId<Role> id);

	/**
	 * Returns a set of {@linkplain Role}s that achieves a {@linkplain SpecificationGoal} from this {@linkplain AchievesManager}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain SpecificationGoal}.
	 *
	 * @return a set of {@linkplain Role}s.
	 */
	Set<Role> getAchievedBy(UniqueId<SpecificationGoal> id);

	/**
	 * Removes an achieves relation between a {@linkplain Role} and a {@linkplain SpecificationGoal} from this {@linkplain AchievesManager}.
	 *
	 * @param roleId
	 *            the {@linkplain UniqueId} that represents the {@linkplain Role}.
	 * @param goalId
	 *            the {@linkplain UniqueId} that represents the {@linkplain SpecificationGoal}.
	 */
	void removeAchieves(UniqueId<Role> roleId, UniqueId<SpecificationGoal> goalId);

	/**
	 * Removes all achieves relations from this {@linkplain AchievesManager}.
	 */
	void removeAllAchieves();
}