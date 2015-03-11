/*
 * Organization.java
 *
 * Created on Mar 24, 2009
 *
 * See License.txt file the license agreement.
 */
package org.models.organization;

import java.util.function.Predicate;

import org.models.organization.entity.manager.AgentManager;
import org.models.organization.entity.manager.AttributeManager;
import org.models.organization.entity.manager.CapabilityManager;
import org.models.organization.entity.manager.CharacteristicManager;
import org.models.organization.entity.manager.InstanceGoalManager;
import org.models.organization.entity.manager.PmfManager;
import org.models.organization.entity.manager.PolicyManager;
import org.models.organization.entity.manager.RoleManager;
import org.models.organization.entity.manager.SpecificationGoalManager;
import org.models.organization.relation.manager.AchievesManager;
import org.models.organization.relation.manager.AssignmentManager;
import org.models.organization.relation.manager.ContainsManager;
import org.models.organization.relation.manager.HasManager;
import org.models.organization.relation.manager.ModeratesManager;
import org.models.organization.relation.manager.NeedsManager;
import org.models.organization.relation.manager.PossessesManager;
import org.models.organization.relation.manager.RequiresManager;
import org.models.organization.relation.manager.TaskManager;
import org.models.organization.relation.manager.UsesManager;

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