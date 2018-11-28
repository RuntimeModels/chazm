package runtimemodels.chazm.api.relation.manager;

import runtimemodels.chazm.api.entity.Role;
import runtimemodels.chazm.api.entity.SpecificationGoal;
import runtimemodels.chazm.api.id.RoleId;
import runtimemodels.chazm.api.id.SpecificationGoalId;
import runtimemodels.chazm.api.id.UniqueId;

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
     * @param roleId the {@linkplain UniqueId} identifying the {@linkplain Role} for the achieves relation.
     * @param goalId the {@linkplain UniqueId} identifying the {@linkplain SpecificationGoal} for the achieves relation.
     */
    void addAchieves(RoleId roleId, SpecificationGoalId goalId);

    /**
     * Returns a set of {@linkplain SpecificationGoal}s that is achieved by a {@linkplain Role} from this {@linkplain AchievesManager}.
     *
     * @param id the {@linkplain UniqueId} that represents the {@linkplain Role}.
     * @return a set of {@linkplain SpecificationGoal}s.
     */
    Set<SpecificationGoal> getAchieves(RoleId id);

    /**
     * Returns a set of {@linkplain Role}s that achieves a {@linkplain SpecificationGoal} from this {@linkplain AchievesManager}.
     *
     * @param id the {@linkplain UniqueId} that represents the {@linkplain SpecificationGoal}.
     * @return a set of {@linkplain Role}s.
     */
    Set<Role> getAchievedBy(SpecificationGoalId id);

    /**
     * Removes an achieves relation between a {@linkplain Role} and a {@linkplain SpecificationGoal} from this {@linkplain AchievesManager}.
     *
     * @param roleId the {@linkplain UniqueId} that represents the {@linkplain Role}.
     * @param goalId the {@linkplain UniqueId} that represents the {@linkplain SpecificationGoal}.
     */
    void removeAchieves(RoleId roleId, SpecificationGoalId goalId);

    /**
     * Removes all achieves relations from this {@linkplain AchievesManager}.
     */
    void removeAllAchieves();
}
