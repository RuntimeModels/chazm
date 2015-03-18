package model.organization;

import java.util.Collection;
import java.util.function.Predicate;

import model.organization.entity.Role;
import model.organization.entity.manager.AgentManager;
import model.organization.entity.manager.AttributeManager;
import model.organization.entity.manager.CapabilityManager;
import model.organization.entity.manager.CharacteristicManager;
import model.organization.entity.manager.InstanceGoalManager;
import model.organization.entity.manager.PmfManager;
import model.organization.entity.manager.PolicyManager;
import model.organization.entity.manager.RoleManager;
import model.organization.entity.manager.SpecificationGoalManager;
import model.organization.function.Goodness;
import model.organization.id.UniqueId;
import model.organization.relation.Assignment;
import model.organization.relation.manager.AchievesManager;
import model.organization.relation.manager.AssignmentManager;
import model.organization.relation.manager.ContainsManager;
import model.organization.relation.manager.HasManager;
import model.organization.relation.manager.ModeratesManager;
import model.organization.relation.manager.NeedsManager;
import model.organization.relation.manager.PossessesManager;
import model.organization.relation.manager.RequiresManager;
import model.organization.relation.manager.UsesManager;

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