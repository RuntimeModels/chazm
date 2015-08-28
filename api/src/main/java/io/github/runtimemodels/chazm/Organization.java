package io.github.runtimemodels.chazm;

import io.github.runtimemodels.chazm.entity.Role;
import io.github.runtimemodels.chazm.entity.manager.AgentManager;
import io.github.runtimemodels.chazm.entity.manager.AttributeManager;
import io.github.runtimemodels.chazm.entity.manager.CapabilityManager;
import io.github.runtimemodels.chazm.entity.manager.CharacteristicManager;
import io.github.runtimemodels.chazm.entity.manager.InstanceGoalManager;
import io.github.runtimemodels.chazm.entity.manager.PmfManager;
import io.github.runtimemodels.chazm.entity.manager.PolicyManager;
import io.github.runtimemodels.chazm.entity.manager.RoleManager;
import io.github.runtimemodels.chazm.entity.manager.SpecificationGoalManager;
import io.github.runtimemodels.chazm.function.Goodness;
import io.github.runtimemodels.chazm.id.UniqueId;
import io.github.runtimemodels.chazm.relation.Assignment;
import io.github.runtimemodels.chazm.relation.manager.AchievesManager;
import io.github.runtimemodels.chazm.relation.manager.AssignmentManager;
import io.github.runtimemodels.chazm.relation.manager.ContainsManager;
import io.github.runtimemodels.chazm.relation.manager.HasManager;
import io.github.runtimemodels.chazm.relation.manager.ModeratesManager;
import io.github.runtimemodels.chazm.relation.manager.NeedsManager;
import io.github.runtimemodels.chazm.relation.manager.PossessesManager;
import io.github.runtimemodels.chazm.relation.manager.RequiresManager;
import io.github.runtimemodels.chazm.relation.manager.UsesManager;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * The {@linkplain Organization} interface defines an organization, which consists of entities and relations between the entities.
 *
 * @author Christopher Zhong
 * @since 4.0
 */
public interface Organization extends SpecificationGoalManager, RoleManager, AgentManager, CapabilityManager, PolicyManager, InstanceGoalManager,
		AttributeManager, PmfManager, CharacteristicManager, AssignmentManager, AchievesManager, RequiresManager, PossessesManager, NeedsManager, HasManager,
		UsesManager, ModeratesManager, ContainsManager {

	/**
	 * Checks if this {@linkplain Organization} is valid. Validity rules differ from one organization to another, so there is no general algorithm to determine
	 * the validity of an {@linkplain Organization}.
	 *
	 * @param p
	 *            the {@linkplain Predicate} that tests the validity of this {@linkplain Organization}.
	 * @return <code>true</code> if the {@linkplain Organization} is valid, <code>false</code> otherwise.
	 */
	default boolean isValid(final Predicate<Organization> p) {
		return p.test(this);
	}

	/**
	 * Returns a <code>double</code>-valued score of this {@linkplain Organization}'s effectiveness.
	 *
	 * @param assignments
	 *            a set of {@linkplain Assignment}s.
	 * @return a <code>double</code>-valued score.
	 */
	double effectiveness(Collection<Assignment> assignments);

	/**
	 * Returns the {@linkplain Goodness} function associated with a {@linkplain Role} from this {@linkplain Organization}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents a {@linkplain Role}.
	 * @return a {@linkplain Goodness} function.
	 */
	Goodness getGoodness(UniqueId<Role> id);

	/**
	 * Sets a new {@linkplain Goodness} function for a {@linkplain Role} in this {@linkplain Organization}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents a {@linkplain Role}.
	 * @param goodness
	 *            the {@linkplain Goodness} function.
	 */
	void setGoodness(UniqueId<Role> id, Goodness goodness);

}