/*
 * Organization.java
 *
 * Created on Mar 24, 2009
 *
 * See License.txt file the license agreement.
 */
package org.models.organization;

import java.util.Collection;
import java.util.Set;

import org.models.organization.entity.Agent;
import org.models.organization.entity.Attribute;
import org.models.organization.entity.Capability;
import org.models.organization.entity.Characteristic;
import org.models.organization.entity.InstanceGoal;
import org.models.organization.entity.PerformanceFunction;
import org.models.organization.entity.Policy;
import org.models.organization.entity.Role;
import org.models.organization.entity.SpecificationGoal;
import org.models.organization.identifier.UniqueId;
import org.models.organization.manager.AgentManager;
import org.models.organization.manager.AttributeManager;
import org.models.organization.manager.CapabilityManager;
import org.models.organization.manager.CharacteristicManager;
import org.models.organization.manager.InstanceGoalManager;
import org.models.organization.manager.PerformanceFunctionManager;
import org.models.organization.manager.PolicyManager;
import org.models.organization.manager.RoleManager;
import org.models.organization.manager.SpecificationGoalManager;
import org.models.organization.manager.TaskManager;
import org.models.organization.relation.Assignment;
import org.models.organization.relation.Contains;
import org.models.organization.relation.Has;
import org.models.organization.relation.Moderates;
import org.models.organization.relation.Needs;
import org.models.organization.relation.Uses;

/**
 * The {@linkplain Organization} interface defines an organization, which consists of entities and relations between the entities.
 *
 * @author Christopher Zhong
 * @see Agent
 * @see Capability
 * @see InstanceGoal
 * @see Policy
 * @see Role
 * @see SpecificationGoal
 * @since 4.0
 */
public interface Organization extends SpecificationGoalManager, RoleManager, AgentManager, CapabilityManager, PolicyManager, InstanceGoalManager,
		AttributeManager, TaskManager, PerformanceFunctionManager, CharacteristicManager {

	/**
	 * Adds the given {@linkplain Assignment} to the set of {@linkplain Assignment}.
	 *
	 * @param assignment
	 *            the {@linkplain Assignment} to be added.
	 */
	void addAssignment(Assignment assignment);

	/**
	 * Adds the given set of {@linkplain Assignment} to the set of {@linkplain Assignment}.
	 *
	 * @param assignments
	 *            the set of {@linkplain Assignment} to be added.
	 */
	void addAssignments(Collection<Assignment> assignments);

	/**
	 * Adds the given set of {@linkplain Assignment} to the set of {@linkplain Assignment}.
	 *
	 * @param assignments
	 *            the set of {@linkplain Assignment} to be added.
	 */
	void addAssignments(Assignment... assignments);

	/**
	 * Returns the {@linkplain Assignment} by the given {@linkplain UniqueId} that identifies the {@linkplain Assignment} .
	 *
	 * @param assignmentId
	 *            the {@linkplain UniqueId} identifying the {@linkplain Assignment} to retrieve.
	 * @return the {@linkplain Assignment} if it exists, <code>null</code> otherwise.
	 */
	Assignment getAssignment(UniqueId assignmentId);

	/**
	 * Returns the set of {@linkplain Assignment}.
	 *
	 * @return the set of {@linkplain Assignment}.
	 */
	Set<Assignment> getAssignments();

	/**
	 * Returns the set of {@linkplain Assignment} assigned to the given {@linkplain Agent}.
	 *
	 * @param agentId
	 *            the {@linkplain UniqueId} identifying the {@linkplain Agent} to retrieve the set of {@linkplain Assignment}.
	 * @return the set of {@linkplain Assignment} assigned to the given {@linkplain Agent}.
	 */
	Set<Assignment> getAssignmentsOfAgent(UniqueId agentId);

	/**
	 * Removes the {@linkplain Assignment} by the given {@linkplain UniqueId} that identifies the {@linkplain Assignment} .
	 *
	 * @param assignmentId
	 *            the {@linkplain UniqueId} that identifies the {@linkplain Assignment} to be removed.
	 */
	void removeAssignment(UniqueId assignmentId);

	/**
	 * Removes the given set of {@linkplain UniqueId} that identifies {@linkplain Assignment} from the set of {@linkplain Assignment}.
	 *
	 * @param assignmentIds
	 *            the set of {@linkplain UniqueId} that identifies {@linkplain Assignment} to be removed.
	 */
	void removeAssignments(Collection<UniqueId> assignmentIds);

	/**
	 * Removes the given set of {@linkplain UniqueId} that identifies {@linkplain Assignment} from the set of {@linkplain Assignment}.
	 *
	 * @param assignmentIds
	 *            the set of {@linkplain UniqueId} that identifies {@linkplain Assignment} to be removed.
	 */
	void removeAssignments(UniqueId... assignmentIds);

	/**
	 * Clears the set of {@linkplain Assignment}.
	 */
	void removeAllAssignments();

	/**
	 * Creates the achieves relation with the given {@linkplain Role}, {@linkplain SpecificationGoal}, and score.
	 *
	 * @param roleId
	 *            the {@linkplain UniqueId} identifying the {@linkplain Role} for the achieves relation.
	 * @param goalId
	 *            the {@linkplain UniqueId} identifying the {@linkplain SpecificationGoal} for the achieves relation.
	 */
	void addAchievesRelation(UniqueId roleId, UniqueId goalId);

	/**
	 * Removes the achieves relation with the given {@linkplain Role} and {@linkplain SpecificationGoal}.
	 *
	 * @param roleId
	 *            the {@linkplain UniqueId} identifying the {@linkplain Role} for the achieves relation.
	 * @param goalId
	 *            the {@linkplain UniqueId} identifying the {@linkplain SpecificationGoal} for the achieves relation.
	 */
	void removeAchievesRelation(UniqueId roleId, UniqueId goalId);

	/**
	 * Creates the requires relation with the given {@linkplain Role} and {@linkplain Capability}.
	 *
	 * @param roleId
	 *            the {@linkplain UniqueId} identifying the {@linkplain Role} for the requires relation.
	 * @param capabilityId
	 *            the {@linkplain UniqueId} identifying the {@linkplain Capability} for the requires relation.
	 */
	void addRequiresRelation(UniqueId roleId, UniqueId capabilityId);

	/**
	 * Removes the requires relation with the given {@linkplain Role} and {@linkplain Capability}.
	 *
	 * @param roleId
	 *            the {@linkplain UniqueId} identifying the {@linkplain Role} for the requires relation.
	 * @param capabilityId
	 *            the {@linkplain UniqueId} identifying the {@linkplain Capability} for the requires relation.
	 */
	void removeRequiresRelation(UniqueId roleId, UniqueId capabilityId);

	/**
	 * Creates the possesses relation with the given {@linkplain Agent}, {@linkplain Capability}, and score.
	 *
	 * @param agentId
	 *            the {@linkplain UniqueId} identifying the {@linkplain Agent} for the possesses relation.
	 * @param capabilityId
	 *            the {@linkplain UniqueId} identifying the {@linkplain Capability} for the possesses relation.
	 * @param score
	 *            the score of the possesses relation.
	 */
	void addPossessesRelation(UniqueId agentId, UniqueId capabilityId, double score);

	/**
	 * Removes the possesses relation with the given {@linkplain Agent} and {@linkplain Capability}.
	 *
	 * @param agentId
	 *            the {@linkplain UniqueId} identifying the {@linkplain Agent} for the possesses relation.
	 * @param capabilityId
	 *            the {@linkplain UniqueId} identifying the {@linkplain Capability} for the possesses relation.
	 */
	void removePossessesRelation(UniqueId agentId, UniqueId capabilityId);

	/**
	 * Updates the possesses relation with the given {@linkplain Agent}, {@linkplain Capability}, and score.
	 *
	 * @param agentId
	 *            the {@linkplain UniqueId} identifying the {@linkplain Agent} for the possesses relation.
	 * @param capabilityId
	 *            the {@linkplain UniqueId} identifying the {@linkplain Capability} for the possesses relation.
	 * @param score
	 *            the score of the possesses relation.
	 */
	void updatePossessesRelation(UniqueId agentId, UniqueId capabilityId, double score);

	/**
	 * Creates the {@linkplain Needs} with the given {@linkplain Role} and {@linkplain Attribute}.
	 *
	 * @param roleId
	 *            the {@linkplain UniqueId} identifying the {@linkplain Role} for the {@linkplain Needs}.
	 * @param attributeId
	 *            the {@linkplain UniqueId} identifying the {@linkplain Attribute} for the {@linkplain Needs} .
	 */
	void addNeedsRelation(UniqueId roleId, UniqueId attributeId);

	/**
	 * Removes the {@linkplain Needs} with the given {@linkplain Role} and {@linkplain Attribute}.
	 *
	 * @param roleId
	 *            the {@linkplain UniqueId} identifying the {@linkplain Role} for the {@linkplain Needs}.
	 * @param attributeId
	 *            the {@linkplain UniqueId} identifying the {@linkplain Attribute} for the {@linkplain Needs} .
	 */
	void removeNeedsRelation(UniqueId roleId, UniqueId attributeId);

	/**
	 * Creates the {@linkplain Has} with the given {@linkplain Role}, {@linkplain Attribute}, and a {@linkplain double} representing the score.
	 *
	 * @param agentId
	 *            the {@linkplain UniqueId} identifying the {@linkplain Agent} for the {@linkplain Has}.
	 * @param attributeId
	 *            the {@linkplain UniqueId} identifying the {@linkplain Attribute} for the {@linkplain Has}.
	 * @param value
	 *            the {@linkplain double} representing the value for the {@linkplain Has}.
	 */
	void addHasRelation(UniqueId agentId, UniqueId attributeId, double value);

	/**
	 * Removes the {@linkplain Has} with the given {@linkplain Agent} and {@linkplain Attribute}.
	 *
	 * @param agentId
	 *            the {@linkplain UniqueId} identifying the {@linkplain Agent} for the {@linkplain Has}.
	 * @param attributeId
	 *            the {@linkplain UniqueId} identifying the {@linkplain Attribute} for the {@linkplain Has}.
	 */
	void removeHasRelation(UniqueId agentId, UniqueId attributeId);

	/**
	 * Updates the {@linkplain Has} with the given {@linkplain Agent}, {@linkplain Attribute}, and {@linkplain double} representing the score.
	 *
	 * @param agentId
	 *            the {@linkplain UniqueId} identifying the {@linkplain Agent} for the {@linkplain Has}.
	 * @param attributeId
	 *            the {@linkplain UniqueId} identifying the {@linkplain Attribute} for the {@linkplain Has}.
	 * @param score
	 *            the {@linkplain double} representing the score for the {@linkplain Has}.
	 */
	void updateHasRelation(UniqueId agentId, UniqueId attributeId, double score);

	/**
	 * Creates the {@linkplain Uses} with the given {@linkplain Role} and {@linkplain PerformanceFunction}.
	 *
	 * @param roleId
	 *            the {@linkplain UniqueId} identifying the {@linkplain Role} for the {@linkplain Uses}.
	 * @param functionId
	 *            the {@linkplain UniqueId} identifying the {@linkplain PerformanceFunction} for the {@linkplain Uses}.
	 */
	void addUsesRelation(UniqueId roleId, UniqueId functionId);

	/**
	 * Removes the {@linkplain Uses} with the given {@linkplain Role} and {@linkplain PerformanceFunction}.
	 *
	 * @param roleId
	 *            the {@linkplain UniqueId} identifying the {@linkplain Role} for the {@linkplain Uses}.
	 * @param functionId
	 *            the {@linkplain UniqueId} identifying the {@linkplain PerformanceFunction} for the {@linkplain Uses}.
	 */
	void removeUsesRelation(UniqueId roleId, UniqueId functionId);

	/**
	 * Creates the {@linkplain Moderates} with the given {@linkplain PerformanceFunction} and {@linkplain Attribute}.
	 *
	 * @param performanceFunctionId
	 *            the {@linkplain UniqueId} identifying the {@linkplain PerformanceFunction} for the {@linkplain Moderates}.
	 * @param attributeId
	 *            the {@linkplain UniqueId} identifying the {@linkplain Attribute} for the {@linkplain Moderates}.
	 */
	void setModeratesRelation(UniqueId performanceFunctionId, UniqueId attributeId);

	/**
	 * Creates a {@linkplain Contains} with the {@linkplain Role} (from the given {@linkplain UniqueId}), {@linkplain Characteristic} (from the given
	 * {@linkplain UniqueId} ), and {@linkplain double} value.
	 *
	 * @param roleId
	 *            the {@linkplain UniqueId} of the {@linkplain Role} for the {@linkplain Contains}.
	 * @param characteristicId
	 *            the {@linkplain UniqueId} of the {@linkplain Characteristic} for the {@linkplain Contains}.
	 * @param value
	 *            the {@linkplain double} value for the {@linkplain Contains}.
	 */
	void addContainsRelation(UniqueId roleId, UniqueId characteristicId, double value);

	/**
	 * Removes the {@linkplain Contains} of the {@linkplain Role} (from the given {@linkplain UniqueId}) and {@linkplain Characteristic} (from the given
	 * {@linkplain UniqueId}).
	 *
	 * @param roleId
	 *            the {@linkplain UniqueId} of the {@linkplain Role} for the {@linkplain Contains}.
	 * @param characteristicId
	 *            the {@linkplain UniqueId} of the {@linkplain Characteristic} for the {@linkplain Contains}.
	 */
	void removeContainsRelation(UniqueId roleId, UniqueId characteristicId);

	/**
	 * Updates the {@linkplain Contains} of the {@linkplain Role} (from the given {@linkplain UniqueId}) and {@linkplain Characteristic} (from the given
	 * {@linkplain UniqueId}) with the given new {@linkplain double} value.
	 *
	 * @param roleId
	 *            the {@linkplain UniqueId} of the {@linkplain Role} for the {@linkplain Contains}.
	 * @param characteristicId
	 *            the {@linkplain UniqueId} of the {@linkplain Characteristic} for the {@linkplain Contains}.
	 * @param value
	 *            the new {@linkplain double} value for the {@linkplain Contains}.
	 */
	void updateContainsRelation(UniqueId roleId, UniqueId characteristicId, double value);

}