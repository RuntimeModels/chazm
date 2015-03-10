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
import org.models.organization.relation.Assignment;
import org.models.organization.relation.Contains;
import org.models.organization.relation.Has;
import org.models.organization.relation.Moderates;
import org.models.organization.relation.NeedsRelation;
import org.models.organization.relation.Task;
import org.models.organization.relation.UsesRelation;

/**
 * The {@linkplain Organization} interface defines an organization.
 *
 * @author Christopher Zhong
 * @since 4.0
 */
public interface Organization {

	/**
	 * Adds the given {@linkplain SpecificationGoal} to the set of {@linkplain SpecificationGoal}.
	 *
	 * @param specificationGoal
	 *            the {@linkplain SpecificationGoal} to be added.
	 */
	void addSpecificationGoal(SpecificationGoal specificationGoal);

	/**
	 * Adds the given set of {@linkplain SpecificationGoal} to the set of {@linkplain SpecificationGoal}.
	 *
	 * @param specificationGoals
	 *            the set of {@linkplain SpecificationGoal} to be added.
	 */
	void addSpecificationGoals(Collection<SpecificationGoal> specificationGoals);

	/**
	 * Adds the given set of {@linkplain SpecificationGoal} to the set of {@linkplain SpecificationGoal}.
	 *
	 * @param specificationGoals
	 *            the set of {@linkplain SpecificationGoal} to be added.
	 */
	void addSpecificationGoals(SpecificationGoal... specificationGoals);

	/**
	 * Returns the {@linkplain SpecificationGoal} by the given {@linkplain UniqueId}.
	 *
	 * @param specificationGoalIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain SpecificationGoal} to retrieve.
	 * @return the {@linkplain SpecificationGoal} if it exists, {@linkplain null} otherwise.
	 */
	SpecificationGoal getSpecificationGoal(UniqueId specificationGoalIdentifier);

	/**
	 * Returns the set of {@linkplain SpecificationGoal}.
	 *
	 * @return the set of {@linkplain SpecificationGoal}.
	 */
	Set<SpecificationGoal> getSpecificationGoals();

	/**
	 * Removes the {@linkplain SpecificationGoal} by the given {@linkplain UniqueId} from the set of {@linkplain SpecificationGoal}.
	 *
	 * @param goalIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain SpecificationGoal} to remove.
	 */
	void removeSpecificationGoal(UniqueId goalIdentifier);

	/**
	 * Removes the given set of {@linkplain UniqueId} that identifies {@linkplain SpecificationGoal} from the set of {@linkplain SpecificationGoal}.
	 *
	 * @param goalIdentifiers
	 *            the set of {@linkplain UniqueId} that identifies {@linkplain SpecificationGoal} to be removed.
	 */
	void removeSpecificationGoals(Collection<UniqueId> goalIdentifiers);

	/**
	 * Removes the given set of {@linkplain UniqueId} that identifies {@linkplain SpecificationGoal} from the set of {@linkplain SpecificationGoal}.
	 *
	 * @param goalIdentifiers
	 *            the set of {@linkplain UniqueId} that identifies {@linkplain SpecificationGoal} to be removed.
	 */
	void removeSpecificationGoals(UniqueId... goalIdentifiers);

	/**
	 * Clears the set of {@linkplain SpecificationGoal}.
	 */
	void removeAllSpecificationGoals();

	/**
	 * Adds the given {@linkplain Role} to the set of {@linkplain Role}.
	 *
	 * @param role
	 *            the {@linkplain Role} to be added.
	 */
	void addRole(Role role);

	/**
	 * Adds the given set of {@linkplain Role} to the set of {@linkplain Role}.
	 *
	 * @param roles
	 *            the set of {@linkplain Role} to be added.
	 */
	void addRoles(Collection<Role> roles);

	/**
	 * Adds the given set of {@linkplain Role} to the set of {@linkplain Role}.
	 *
	 * @param roles
	 *            the set of {@linkplain Role} to be added.
	 */
	void addRoles(Role... roles);

	/**
	 * Returns the {@linkplain Role} by the given {@linkplain UniqueId}.
	 *
	 * @param roleIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Role} to retrieve.
	 * @return the {@linkplain Role} if it exists, {@linkplain null} otherwise.
	 */
	Role getRole(UniqueId roleIdentifier);

	/**
	 * Returns the set of {@linkplain Role}.
	 *
	 * @return the set of {@linkplain Role}.
	 */
	Set<Role> getRoles();

	/**
	 * Removes the {@linkplain Role} by the given {@linkplain UniqueId} from the set of {@linkplain Role}.
	 *
	 * @param roleIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Role} to remove.
	 */
	void removeRole(UniqueId roleIdentifier);

	/**
	 * Removes the given set of {@linkplain UniqueId} that identifies the {@linkplain Role} from the set of {@linkplain Role}.
	 *
	 * @param roleIdentifiers
	 *            the set of {@linkplain UniqueId} that identifies the {@linkplain Role} to be removed.
	 */
	void removeRoles(Collection<UniqueId> roleIdentifiers);

	/**
	 * Removes the given set of {@linkplain UniqueId} that identifies the {@linkplain Role} from the set of {@linkplain Role}.
	 *
	 * @param roleIdentifiers
	 *            the set of {@linkplain UniqueId} that identifies the {@linkplain Role} to be removed.
	 */
	void removeRoles(UniqueId... roleIdentifiers);

	/**
	 * Clears the set of {@linkplain Role}.
	 */
	void removeAllRoles();

	/**
	 * Adds the given {@linkplain Agent} to the set of {@linkplain Agent}.
	 *
	 * @param agent
	 *            the {@linkplain Agent} to be added.
	 */
	void addAgent(Agent agent);

	/**
	 * Adds the given set of {@linkplain Agent} to the set of {@linkplain Agent} .
	 *
	 * @param agents
	 *            the set of {@linkplain Agent} to be added.
	 */
	void addAgents(Collection<Agent> agents);

	/**
	 * Adds the given set of {@linkplain Agent} to the set of {@linkplain Agent} .
	 *
	 * @param agents
	 *            the set of {@linkplain Agent} to be added.
	 */
	void addAgents(Agent... agents);

	/**
	 * Returns the {@linkplain Agent} by the given {@linkplain UniqueId} .
	 *
	 * @param agentIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Agent} to retrieve.
	 * @return the {@linkplain Agent} if it exists, {@linkplain null} otherwise.
	 */
	Agent getAgent(UniqueId agentIdentifier);

	/**
	 * Returns the set of {@linkplain Agent}.
	 *
	 * @return the set of {@linkplain Agent}.
	 */
	Set<Agent> getAgents();

	/**
	 * Removes the {@linkplain Agent} by the given {@linkplain UniqueId} from the set of {@linkplain Agent}.
	 *
	 * @param agentIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Agent} to remove.
	 */
	void removeAgent(UniqueId agentIdentifier);

	/**
	 * Removes the given set of {@linkplain UniqueId} that identifies the {@linkplain Agent} from the set of {@linkplain Agent}.
	 *
	 * @param agentIdentifiers
	 *            the set of {@linkplain UniqueId} that identifies the {@linkplain Agent} to remove.
	 */
	void removeAgents(Collection<UniqueId> agentIdentifiers);

	/**
	 * Removes the given set of {@linkplain UniqueId} that identifies the {@linkplain Agent} from the set of {@linkplain Agent}.
	 *
	 * @param agentIdentifiers
	 *            the set of {@linkplain UniqueId} that identifies the {@linkplain Agent} to remove.
	 */
	void removeAgents(UniqueId... agentIdentifiers);

	/**
	 * Clears the set of {@linkplain Agent}.
	 */
	void removeAllAgents();

	/**
	 * Adds the given {@linkplain Capability} to the set of {@linkplain Capability}.
	 *
	 * @param capability
	 *            the {@linkplain Capability} to be added.
	 */
	void addCapability(Capability capability);

	/**
	 * Adds the given set of {@linkplain Capability} to the set of {@linkplain Capability}.
	 *
	 * @param capabilities
	 *            the set of {@linkplain Capability} to be added.
	 */
	void addCapabilities(Collection<Capability> capabilities);

	/**
	 * Adds the given set of {@linkplain Capability} to the set of {@linkplain Capability}.
	 *
	 * @param capabilities
	 *            the set of {@linkplain Capability} to be added.
	 */
	void addCapabilities(Capability... capabilities);

	/**
	 * Returns the {@linkplain Capability} by the given {@linkplain UniqueId}.
	 *
	 * @param capabilityIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Capability} to retrieve.
	 * @return the {@linkplain Capability} if it exists, {@linkplain null} otherwise.
	 */
	Capability getCapability(UniqueId capabilityIdentifier);

	/**
	 * Returns the set of {@linkplain Capability}.
	 *
	 * @return the set of {@linkplain Capability}.
	 */
	Set<Capability> getCapabilities();

	/**
	 * Removes the {@linkplain Capability} by the given {@linkplain UniqueId} from the set of {@linkplain Capability}.
	 *
	 * @param capabilityIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Capability} to remove.
	 */
	void removeCapability(UniqueId capabilityIdentifier);

	/**
	 * Removes the given set of {@linkplain UniqueId} that identifies the {@linkplain Capability} from the set of {@linkplain Capability}.
	 *
	 * @param capabilityIdentifiers
	 *            the set of {@linkplain UniqueId} that identifies the {@linkplain Capability} to remove.
	 */
	void removeCapabilities(Collection<UniqueId> capabilityIdentifiers);

	/**
	 * Removes the given set of {@linkplain UniqueId} that identifies the {@linkplain Capability} from the set of {@linkplain Capability}.
	 *
	 * @param capabilityIdentifiers
	 *            the set of {@linkplain UniqueId} that identifies the {@linkplain Capability} to remove.
	 */
	void removeCapabilities(UniqueId... capabilityIdentifiers);

	/**
	 * Clears the set of {@linkplain Capability}.
	 */
	void removeAllCapabilities();

	/**
	 * Adds the given {@linkplain Policy} to the set of {@linkplain Policy}.
	 *
	 * @param policy
	 *            the {@linkplain Policy} to be added.
	 */
	void addPolicy(Policy policy);

	/**
	 * Adds the given set of {@linkplain Policy} to the set of {@linkplain Policy}.
	 *
	 * @param policies
	 *            the set of {@linkplain Policy} to be added.
	 */
	void addPolicies(Collection<Policy> policies);

	/**
	 * Adds the given set of {@linkplain Policy} to the set of {@linkplain Policy}.
	 *
	 * @param policies
	 *            the set of {@linkplain Policy} to be added.
	 */
	void addPolicies(Policy... policies);

	/**
	 * Returns the {@linkplain Policy} by the given {@linkplain UniqueId}.
	 *
	 * @param policyIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Policy} to retrieve.
	 * @return the {@linkplain Policy} if it exists, {@linkplain null} otherwise.
	 */
	Policy getPolicy(UniqueId policyIdentifier);

	/**
	 * Returns the set of {@linkplain Policy}.
	 *
	 * @return the set of {@linkplain Policy}.
	 */
	Set<Policy> getPolicies();

	/**
	 * Removes the given {@linkplain UniqueId} that identifies the {@linkplain Policy} from the set of {@linkplain Policy}.
	 *
	 * @param policyIdentifier
	 *            the {@linkplain UniqueId} that identifies the {@linkplain Policy} to be removed.
	 */
	void removePolicy(UniqueId policyIdentifier);

	/**
	 * Removes the given set of {@linkplain UniqueId} that identifies the {@linkplain Policy} from the set of {@linkplain Policy}.
	 *
	 * @param policyIdentifiers
	 *            the set of {@linkplain UniqueId} that identifies the {@linkplain Policy} to be removed.
	 */
	void removePolicies(Collection<UniqueId> policyIdentifiers);

	/**
	 * Removes the given set of {@linkplain UniqueId} that identifies the {@linkplain Policy} from the set of {@linkplain Policy}.
	 *
	 * @param policyIdentifiers
	 *            the set of {@linkplain UniqueId} that identifies the {@linkplain Policy} to be removed.
	 */
	void removePolicies(UniqueId... policyIdentifiers);

	/**
	 * Clears the set of {@linkplain Policy}.
	 */
	void removeAllPolicies();

	/**
	 * Adds the given {@linkplain InstanceGoal} to the set of {@linkplain InstanceGoal}.
	 *
	 * @param instanceGoal
	 *            the {@linkplain InstanceGoal} to be added.
	 */
	void addInstanceGoal(InstanceGoal<?> instanceGoal);

	/**
	 * Adds the given set of {@linkplain InstanceGoal} to the set of {@linkplain InstanceGoal}.
	 *
	 * @param instanceGoals
	 *            the set of {@linkplain InstanceGoal} to be added.
	 */
	void addInstanceGoals(Collection<InstanceGoal<?>> instanceGoals);

	/**
	 * Adds the given set of {@linkplain InstanceGoal} to the set of {@linkplain InstanceGoal}.
	 *
	 * @param instanceGoals
	 *            the set of {@linkplain InstanceGoal} to be added.
	 */
	void addInstanceGoals(InstanceGoal<?>... instanceGoals);

	/**
	 * Returns the {@linkplain InstanceGoal} by the given {@linkplain UniqueId} that identifies the the {@linkplain InstanceGoal}.
	 *
	 * @param goalIdentifier
	 *            the {@linkplain UniqueId} that identifies the {@linkplain InstanceGoal} to retrieve.
	 * @return the {@linkplain InstanceGoal} if it exists, {@linkplain null} otherwise.
	 */
	InstanceGoal<?> getInstanceGoal(UniqueId goalIdentifier);

	/**
	 * Returns the set of {@linkplain InstanceGoal}.
	 *
	 * @return the set of {@linkplain InstanceGoal}.
	 */
	Set<InstanceGoal<?>> getInstanceGoals();

	/**
	 * Removes the given {@linkplain UniqueId} that identifies the {@linkplain InstanceGoal} from the set of {@linkplain InstanceGoal}.
	 *
	 * @param goalIdentifier
	 *            the {@linkplain UniqueId} that identifies the {@linkplain InstanceGoal} to be removed.
	 */
	void removeInstanceGoal(UniqueId goalIdentifier);

	/**
	 * Removes the given set of {@linkplain UniqueId} that identifies the {@linkplain InstanceGoal} from the set of {@linkplain InstanceGoal}.
	 *
	 * @param goalIdentifiers
	 *            the set of {@linkplain UniqueId} that identifies the {@linkplain InstanceGoal} to be removed.
	 */
	void removeInstanceGoals(Collection<UniqueId> goalIdentifiers);

	/**
	 * Removes the given set of {@linkplain UniqueId} that identifies the {@linkplain InstanceGoal} from the set of {@linkplain InstanceGoal}.
	 *
	 * @param goalIdentifiers
	 *            the set of {@linkplain UniqueId} that identifies the {@linkplain InstanceGoal} to be removed.
	 */
	void removeInstanceGoals(UniqueId... goalIdentifiers);

	/**
	 * Clears the set of {@linkplain InstanceGoal}.
	 */
	void removeAllInstanceGoals();

	/**
	 * Adds the given {@linkplain Attribute} to the set of {@linkplain Attribute}.
	 *
	 * @param attribute
	 *            the {@linkplain Attribute} to be added.
	 */
	void addAttribute(Attribute attribute);

	/**
	 * Adds the given set of {@linkplain Attribute} to the set of {@linkplain Attribute}.
	 *
	 * @param attributes
	 *            the set of {@linkplain Attribute} to be added.
	 */
	void addAttributes(Collection<Attribute> attributes);

	/**
	 * Adds the given set of {@linkplain Attribute} to the set of {@linkplain Attribute}.
	 *
	 * @param attributes
	 *            the set of {@linkplain Attribute} to be added.
	 */
	void addAttributes(Attribute... attributes);

	/**
	 * Returns the {@linkplain Attribute} by the given {@linkplain UniqueId} that identifies the {@linkplain Attribute} .
	 *
	 * @param attributeIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Attribute} to retrieve.
	 * @return the {@linkplain Attribute} if it exists, {@linkplain null} otherwise.
	 */
	Attribute getAttribute(UniqueId attributeIdentifier);

	/**
	 * Returns the set of {@linkplain Attribute}.
	 *
	 * @return the set of {@linkplain Attribute}.
	 */
	Set<Attribute> getAttributes();

	/**
	 * Removes the {@linkplain Attribute} by the given {@linkplain UniqueId} that identifies the {@linkplain Attribute} .
	 *
	 * @param attributeIdentifier
	 *            the {@linkplain UniqueId} that identifies the {@linkplain Attribute} to be removed.
	 */
	void removeAttribute(UniqueId attributeIdentifier);

	/**
	 * Removes the given set of {@linkplain UniqueId} that identifies {@linkplain Attribute} from the set of {@linkplain Attribute}.
	 *
	 * @param attributeIdentifiers
	 *            the set of {@linkplain UniqueId} that identifies {@linkplain Attribute} to be removed.
	 */
	void removeAttributes(Collection<UniqueId> attributeIdentifiers);

	/**
	 * Removes the given set of {@linkplain UniqueId} that identifies {@linkplain Attribute} from the set of {@linkplain Attribute}.
	 *
	 * @param attributeIdentifiers
	 *            the set of {@linkplain UniqueId} that identifies {@linkplain Attribute} to be removed.
	 */
	void removeAttributes(UniqueId... attributeIdentifiers);

	/**
	 * Clears the set of {@linkplain Attribute}.
	 */
	void removeAllAttributes();

	/**
	 * Adds the given {@linkplain Task} to the set of {@linkplain Task}.
	 *
	 * @param task
	 *            the {@linkplain Task} to be added.
	 */
	void addTask(Task task);

	/**
	 * Adds the given set of {@linkplain Task} to the set of {@linkplain Task}.
	 *
	 * @param tasks
	 *            the set of {@linkplain Task} to be added.
	 */
	void addTasks(Collection<Task> tasks);

	/**
	 * Adds the given set of {@linkplain Task} to the set of {@linkplain Task}.
	 *
	 * @param tasks
	 *            the set of {@linkplain Task} to be added.
	 */
	void addTasks(Task... tasks);

	/**
	 * Returns the {@linkplain Task} by the given {@linkplain UniqueId} that identifies the {@linkplain Task}.
	 *
	 * @param taskIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Task} to retrieve.
	 * @return the {@linkplain Task} if it exists, {@linkplain null} otherwise.
	 */
	Task getTask(UniqueId taskIdentifier);

	/**
	 * Returns the set of {@linkplain Task}.
	 *
	 * @return the set of {@linkplain Task}.
	 */
	Set<Task> getTasks();

	/**
	 * Removes the {@linkplain Task} by the given {@linkplain UniqueId} that identifies the {@linkplain Task}.
	 *
	 * @param taskIdentifier
	 *            the {@linkplain UniqueId} that identifies the {@linkplain Task} to be removed.
	 */
	void removeTask(UniqueId taskIdentifier);

	/**
	 * Clears the set of {@linkplain Task}.
	 */
	void removeAllTasks();

	/**
	 * Adds the given {@linkplain PerformanceFunction} to the set of {@linkplain PerformanceFunction}.
	 *
	 * @param performanceFunction
	 *            the {@linkplain PerformanceFunction} to be added.
	 */
	void addPerformanceFunction(PerformanceFunction performanceFunction);

	/**
	 * Adds the given set of {@linkplain PerformanceFunction} to the set of {@linkplain PerformanceFunction}.
	 *
	 * @param performanceFunctions
	 *            the set of {@linkplain PerformanceFunction} to be added.
	 */
	void addPerformanceFunctions(Collection<PerformanceFunction> performanceFunctions);

	/**
	 * Adds the given set of {@linkplain PerformanceFunction} to the set of {@linkplain PerformanceFunction}.
	 *
	 * @param performanceFunctions
	 *            the set of {@linkplain PerformanceFunction} to be added.
	 */
	void addPerformanceFunctions(PerformanceFunction... performanceFunctions);

	/**
	 * Returns the {@linkplain PerformanceFunction} by the given {@linkplain UniqueId} that identifies the {@linkplain PerformanceFunction}.
	 *
	 * @param performanceFunctionIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain PerformanceFunction} to retrieve.
	 * @return the {@linkplain PerformanceFunction} if it exists, {@linkplain null} otherwise.
	 */
	PerformanceFunction getPerformanceFunction(UniqueId performanceFunctionIdentifier);

	/**
	 * Returns the set of {@linkplain PerformanceFunction}.
	 *
	 * @return the set of {@linkplain PerformanceFunction}.
	 */
	Set<PerformanceFunction> getPerformanceFunctions();

	/**
	 * Removes the {@linkplain PerformanceFunction} by the given {@linkplain UniqueId} that identifies the {@linkplain PerformanceFunction}.
	 *
	 * @param performanceFunctionIdentifier
	 *            the {@linkplain UniqueId} that identifies the {@linkplain PerformanceFunction} to be removed.
	 */
	void removePerformanceFunction(UniqueId performanceFunctionIdentifier);

	/**
	 * Clears the set of {@linkplain PerformanceFunction}.
	 */
	void removeAllPerformanceFunctions();

	/**
	 * Adds the given {@linkplain Characteristic} to the set of {@linkplain Characteristic}.
	 *
	 * @param characteristic
	 *            the {@linkplain Characteristic} to add.
	 */
	void addCharacteristic(Characteristic characteristic);

	/**
	 * Adds the given set of {@linkplain Characteristic} to the set of {@linkplain Characteristic}.
	 *
	 * @param characteristics
	 *            the set of {@linkplain Characteristic} to add.
	 */
	void addCharacteristics(Collection<Characteristic> characteristics);

	/**
	 * Adds the given set of {@linkplain Characteristic} to the set of {@linkplain Characteristic}.
	 *
	 * @param characteristics
	 *            the set of {@linkplain Characteristic} to add.
	 */
	void addCharacteristics(Characteristic... characteristics);

	/**
	 * Returns the {@linkplain Characteristic} represented by the given {@linkplain UniqueId}.
	 *
	 * @param characteristicIdentifier
	 *            the {@linkplain UniqueId} of the {@linkplain Characteristic} to return.
	 * @return the {@linkplain Characteristic} represented by the given {@linkplain UniqueId}, {@linkplain null} otherwise.
	 */
	Characteristic getCharacteristic(UniqueId characteristicIdentifier);

	/**
	 * Returns the set of {@linkplain Characteristic}.
	 *
	 * @return the set of {@linkplain Characteristic}.
	 */
	Set<Characteristic> getCharacteristics();

	/**
	 * Removes the {@linkplain Characteristic} (from the given {@linkplain UniqueId}) from the set of {@linkplain Characteristic}.
	 *
	 * @param characteristicIdentifier
	 *            the {@linkplain UniqueId} of the {@linkplain Characteristic} to remove.
	 */
	void removeCharacteristic(UniqueId characteristicIdentifier);

	/**
	 * Clears the set of {@linkplain Characteristic}.
	 */
	void removeAllCharacteristic();

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
	 * @param assignmentIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Assignment} to retrieve.
	 * @return the {@linkplain Assignment} if it exists, {@linkplain null} otherwise.
	 */
	Assignment getAssignment(UniqueId assignmentIdentifier);

	/**
	 * Returns the set of {@linkplain Assignment}.
	 *
	 * @return the set of {@linkplain Assignment}.
	 */
	Set<Assignment> getAssignments();

	/**
	 * Returns the set of {@linkplain Assignment} assigned to the given {@linkplain Agent}.
	 *
	 * @param agentIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Agent} to retrieve the set of {@linkplain Assignment}.
	 * @return the set of {@linkplain Assignment} assigned to the given {@linkplain Agent}.
	 */
	Set<Assignment> getAssignmentsOfAgent(UniqueId agentIdentifier);

	/**
	 * Removes the {@linkplain Assignment} by the given {@linkplain UniqueId} that identifies the {@linkplain Assignment} .
	 *
	 * @param assignmentIdentifier
	 *            the {@linkplain UniqueId} that identifies the {@linkplain Assignment} to be removed.
	 */
	void removeAssignment(UniqueId assignmentIdentifier);

	/**
	 * Removes the given set of {@linkplain UniqueId} that identifies {@linkplain Assignment} from the set of {@linkplain Assignment}.
	 *
	 * @param assignmentIdentifiers
	 *            the set of {@linkplain UniqueId} that identifies {@linkplain Assignment} to be removed.
	 */
	void removeAssignments(Collection<UniqueId> assignmentIdentifiers);

	/**
	 * Removes the given set of {@linkplain UniqueId} that identifies {@linkplain Assignment} from the set of {@linkplain Assignment}.
	 *
	 * @param assignmentIdentifiers
	 *            the set of {@linkplain UniqueId} that identifies {@linkplain Assignment} to be removed.
	 */
	void removeAssignments(UniqueId... assignmentIdentifiers);

	/**
	 * Clears the set of {@linkplain Assignment}.
	 */
	void removeAllAssignments();

	/**
	 * Creates the achieves relation with the given {@linkplain Role}, {@linkplain SpecificationGoal}, and score.
	 *
	 * @param roleIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Role} for the achieves relation.
	 * @param goalIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain SpecificationGoal} for the achieves relation.
	 */
	void addAchievesRelation(UniqueId roleIdentifier, UniqueId goalIdentifier);

	/**
	 * Removes the achieves relation with the given {@linkplain Role} and {@linkplain SpecificationGoal}.
	 *
	 * @param roleIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Role} for the achieves relation.
	 * @param goalIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain SpecificationGoal} for the achieves relation.
	 */
	void removeAchievesRelation(UniqueId roleIdentifier, UniqueId goalIdentifier);

	/**
	 * Creates the requires relation with the given {@linkplain Role} and {@linkplain Capability}.
	 *
	 * @param roleIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Role} for the requires relation.
	 * @param capabilityIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Capability} for the requires relation.
	 */
	void addRequiresRelation(UniqueId roleIdentifier, UniqueId capabilityIdentifier);

	/**
	 * Removes the requires relation with the given {@linkplain Role} and {@linkplain Capability}.
	 *
	 * @param roleIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Role} for the requires relation.
	 * @param capabilityIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Capability} for the requires relation.
	 */
	void removeRequiresRelation(UniqueId roleIdentifier, UniqueId capabilityIdentifier);

	/**
	 * Creates the possesses relation with the given {@linkplain Agent}, {@linkplain Capability}, and score.
	 *
	 * @param agentIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Agent} for the possesses relation.
	 * @param capabilityIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Capability} for the possesses relation.
	 * @param score
	 *            the score of the possesses relation.
	 */
	void addPossessesRelation(UniqueId agentIdentifier, UniqueId capabilityIdentifier, double score);

	/**
	 * Removes the possesses relation with the given {@linkplain Agent} and {@linkplain Capability}.
	 *
	 * @param agentIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Agent} for the possesses relation.
	 * @param capabilityIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Capability} for the possesses relation.
	 */
	void removePossessesRelation(UniqueId agentIdentifier, UniqueId capabilityIdentifier);

	/**
	 * Updates the possesses relation with the given {@linkplain Agent}, {@linkplain Capability}, and score.
	 *
	 * @param agentIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Agent} for the possesses relation.
	 * @param capabilityIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Capability} for the possesses relation.
	 * @param score
	 *            the score of the possesses relation.
	 */
	void updatePossessesRelation(UniqueId agentIdentifier, UniqueId capabilityIdentifier, double score);

	/**
	 * Creates the {@linkplain NeedsRelation} with the given {@linkplain Role} and {@linkplain Attribute}.
	 *
	 * @param roleIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Role} for the {@linkplain NeedsRelation}.
	 * @param attributeIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Attribute} for the {@linkplain NeedsRelation} .
	 */
	void addNeedsRelation(UniqueId roleIdentifier, UniqueId attributeIdentifier);

	/**
	 * Removes the {@linkplain NeedsRelation} with the given {@linkplain Role} and {@linkplain Attribute}.
	 *
	 * @param roleIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Role} for the {@linkplain NeedsRelation}.
	 * @param attributeIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Attribute} for the {@linkplain NeedsRelation} .
	 */
	void removeNeedsRelation(UniqueId roleIdentifier, UniqueId attributeIdentifier);

	/**
	 * Creates the {@linkplain Has} with the given {@linkplain Role}, {@linkplain Attribute}, and a {@linkplain double} representing the score.
	 *
	 * @param agentIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Agent} for the {@linkplain Has}.
	 * @param attributeIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Attribute} for the {@linkplain Has}.
	 * @param value
	 *            the {@linkplain double} representing the value for the {@linkplain Has}.
	 */
	void addHasRelation(UniqueId agentIdentifier, UniqueId attributeIdentifier, double value);

	/**
	 * Removes the {@linkplain Has} with the given {@linkplain Agent} and {@linkplain Attribute}.
	 *
	 * @param agentIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Agent} for the {@linkplain Has}.
	 * @param attributeIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Attribute} for the {@linkplain Has}.
	 */
	void removeHasRelation(UniqueId agentIdentifier, UniqueId attributeIdentifier);

	/**
	 * Updates the {@linkplain Has} with the given {@linkplain Agent}, {@linkplain Attribute}, and {@linkplain double} representing the score.
	 *
	 * @param agentIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Agent} for the {@linkplain Has}.
	 * @param attributeIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Attribute} for the {@linkplain Has}.
	 * @param score
	 *            the {@linkplain double} representing the score for the {@linkplain Has}.
	 */
	void updateHasRelation(UniqueId agentIdentifier, UniqueId attributeIdentifier, double score);

	/**
	 * Creates the {@linkplain UsesRelation} with the given {@linkplain Role} and {@linkplain PerformanceFunction}.
	 *
	 * @param roleIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Role} for the {@linkplain UsesRelation}.
	 * @param functionIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain PerformanceFunction} for the {@linkplain UsesRelation}.
	 */
	void addUsesRelation(UniqueId roleIdentifier, UniqueId functionIdentifier);

	/**
	 * Removes the {@linkplain UsesRelation} with the given {@linkplain Role} and {@linkplain PerformanceFunction}.
	 *
	 * @param roleIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Role} for the {@linkplain UsesRelation}.
	 * @param functionIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain PerformanceFunction} for the {@linkplain UsesRelation}.
	 */
	void removeUsesRelation(UniqueId roleIdentifier, UniqueId functionIdentifier);

	/**
	 * Creates the {@linkplain Moderates} with the given {@linkplain PerformanceFunction} and {@linkplain Attribute}.
	 *
	 * @param performanceFunctionIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain PerformanceFunction} for the {@linkplain Moderates}.
	 * @param attributeIdentifier
	 *            the {@linkplain UniqueId} identifying the {@linkplain Attribute} for the {@linkplain Moderates}.
	 */
	void setModeratesRelation(UniqueId performanceFunctionIdentifier, UniqueId attributeIdentifier);

	/**
	 * Creates a {@linkplain Contains} with the {@linkplain Role} (from the given {@linkplain UniqueId}), {@linkplain Characteristic} (from the given
	 * {@linkplain UniqueId} ), and {@linkplain double} value.
	 *
	 * @param roleIdentifier
	 *            the {@linkplain UniqueId} of the {@linkplain Role} for the {@linkplain Contains}.
	 * @param characteristicIdentifier
	 *            the {@linkplain UniqueId} of the {@linkplain Characteristic} for the {@linkplain Contains}.
	 * @param value
	 *            the {@linkplain double} value for the {@linkplain Contains}.
	 */
	void addContainsRelation(UniqueId roleIdentifier, UniqueId characteristicIdentifier, double value);

	/**
	 * Removes the {@linkplain Contains} of the {@linkplain Role} (from the given {@linkplain UniqueId}) and {@linkplain Characteristic} (from the given
	 * {@linkplain UniqueId}).
	 *
	 * @param roleIdentifier
	 *            the {@linkplain UniqueId} of the {@linkplain Role} for the {@linkplain Contains}.
	 * @param characteristicIdentifier
	 *            the {@linkplain UniqueId} of the {@linkplain Characteristic} for the {@linkplain Contains}.
	 */
	void removeContainsRelation(UniqueId roleIdentifier, UniqueId characteristicIdentifier);

	/**
	 * Updates the {@linkplain Contains} of the {@linkplain Role} (from the given {@linkplain UniqueId}) and {@linkplain Characteristic} (from the given
	 * {@linkplain UniqueId}) with the given new {@linkplain double} value.
	 *
	 * @param roleIdentifier
	 *            the {@linkplain UniqueId} of the {@linkplain Role} for the {@linkplain Contains}.
	 * @param characteristicIdentifier
	 *            the {@linkplain UniqueId} of the {@linkplain Characteristic} for the {@linkplain Contains}.
	 * @param value
	 *            the new {@linkplain double} value for the {@linkplain Contains}.
	 */
	void updateContainsRelation(UniqueId roleIdentifier, UniqueId characteristicIdentifier, double value);

}