/*
 * Organization.java
 *
 * Created on Mar 24, 2009
 *
 * See License.txt file the license agreement.
 */
package model.organization;

import java.util.function.Predicate;

import model.organization.entities.managers.AgentManager;
import model.organization.entities.managers.AttributeManager;
import model.organization.entities.managers.CapabilityManager;
import model.organization.entities.managers.CharacteristicManager;
import model.organization.entities.managers.InstanceGoalManager;
import model.organization.entities.managers.PmfManager;
import model.organization.entities.managers.PolicyManager;
import model.organization.entities.managers.RoleManager;
import model.organization.entities.managers.SpecificationGoalManager;
import model.organization.relations.managers.AchievesManager;
import model.organization.relations.managers.AssignmentManager;
import model.organization.relations.managers.ContainsManager;
import model.organization.relations.managers.HasManager;
import model.organization.relations.managers.ModeratesManager;
import model.organization.relations.managers.NeedsManager;
import model.organization.relations.managers.PossessesManager;
import model.organization.relations.managers.RequiresManager;
import model.organization.relations.managers.TaskManager;
import model.organization.relations.managers.UsesManager;

/**
 * The {@linkplain Organization} interface defines an organization, which consists of entities and relations between the entities.
 *
 * @author Christopher Zhong
 * @since 4.0
 */
public interface Organization extends SpecificationGoalManager, RoleManager, AgentManager, CapabilityManager, PolicyManager, InstanceGoalManager,
		AttributeManager, TaskManager, PmfManager, CharacteristicManager, AssignmentManager, AchievesManager, RequiresManager, PossessesManager, NeedsManager,
		HasManager, UsesManager, ModeratesManager, ContainsManager {
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
}