package io.github.chriszhong.model.organization;

import io.github.chriszhong.model.organization.entity.Role;
import io.github.chriszhong.model.organization.entity.manager.AgentManager;
import io.github.chriszhong.model.organization.entity.manager.AttributeManager;
import io.github.chriszhong.model.organization.entity.manager.CapabilityManager;
import io.github.chriszhong.model.organization.entity.manager.CharacteristicManager;
import io.github.chriszhong.model.organization.entity.manager.InstanceGoalManager;
import io.github.chriszhong.model.organization.entity.manager.PmfManager;
import io.github.chriszhong.model.organization.entity.manager.PolicyManager;
import io.github.chriszhong.model.organization.entity.manager.RoleManager;
import io.github.chriszhong.model.organization.entity.manager.SpecificationGoalManager;
import io.github.chriszhong.model.organization.function.Goodness;
import io.github.chriszhong.model.organization.id.UniqueId;
import io.github.chriszhong.model.organization.relation.Assignment;
import io.github.chriszhong.model.organization.relation.manager.AchievesManager;
import io.github.chriszhong.model.organization.relation.manager.AssignmentManager;
import io.github.chriszhong.model.organization.relation.manager.ContainsManager;
import io.github.chriszhong.model.organization.relation.manager.HasManager;
import io.github.chriszhong.model.organization.relation.manager.ModeratesManager;
import io.github.chriszhong.model.organization.relation.manager.NeedsManager;
import io.github.chriszhong.model.organization.relation.manager.PossessesManager;
import io.github.chriszhong.model.organization.relation.manager.RequiresManager;
import io.github.chriszhong.model.organization.relation.manager.UsesManager;

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