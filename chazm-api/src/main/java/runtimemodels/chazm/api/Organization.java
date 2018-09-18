package runtimemodels.chazm.api;

import runtimemodels.chazm.api.entity.Role;
import runtimemodels.chazm.api.entity.manager.*;
import runtimemodels.chazm.api.function.Goodness;
import runtimemodels.chazm.api.id.UniqueId;
import runtimemodels.chazm.api.relation.Assignment;
import runtimemodels.chazm.api.relation.manager.*;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * The {@linkplain Organization} interface defines an organization, which consists of entities and relations between the entities.
 *
 * @author Christopher Zhong
 * @since 4.0
 */
public interface Organization extends AgentManager, AttributeManager, CapabilityManager, CharacteristicManager, InstanceGoalManager, PmfManager, PolicyManager, RoleManager, SpecificationGoalManager, AchievesManager, AssignmentManager, ContainsManager, HasManager, ModeratesManager, NeedsManager, PossessesManager, RequiresManager, UsesManager {

    /**
     * Checks if this {@linkplain Organization} is valid. Validity rules differ from one organization to another, so there is no general algorithm to determine
     * the validity of an {@linkplain Organization}.
     *
     * @param p the {@linkplain Predicate} that tests the validity of this {@linkplain Organization}.
     * @return <code>true</code> if the {@linkplain Organization} is valid, <code>false</code> otherwise.
     */
    default boolean isValid(final Predicate<Organization> p) {
        return p.test(this);
    }

    /**
     * Returns a <code>double</code>-valued score of this {@linkplain Organization}'s effectiveness.
     *
     * @param assignments a set of {@linkplain Assignment}s.
     * @return a <code>double</code>-valued score.
     */
    double effectiveness(Collection<Assignment> assignments);

    /**
     * Returns the {@linkplain Goodness} function associated with a {@linkplain Role} from this {@linkplain Organization}.
     *
     * @param id the {@linkplain UniqueId} that represents a {@linkplain Role}.
     * @return a {@linkplain Goodness} function.
     */
    Goodness getGoodness(UniqueId<Role> id);

    /**
     * Sets a new {@linkplain Goodness} function for a {@linkplain Role} in this {@linkplain Organization}.
     *
     * @param id       the {@linkplain UniqueId} that represents a {@linkplain Role}.
     * @param goodness the {@linkplain Goodness} function.
     */
    void setGoodness(UniqueId<Role> id, Goodness goodness);

}
