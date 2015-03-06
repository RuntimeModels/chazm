/*
 * Organization.java
 *
 * Created on Mar 24, 2009
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity;

import java.util.Collection;
import java.util.Set;

import org.models.organization.identifier.UniqueIdentifier;
import org.models.organization.relation.Assignment;
import org.models.organization.relation.ContainsRelation;
import org.models.organization.relation.HasRelation;
import org.models.organization.relation.ModeratesRelation;
import org.models.organization.relation.NeedsRelation;
import org.models.organization.relation.Task;
import org.models.organization.relation.UsesRelation;

/**
 * The {@linkplain Organization} interface defines an organization.
 *
 * @author Christopher Zhong
 * @version $Revision: 1.9.2.7 $, $Date: 2011/09/19 14:26:37 $
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
	 * Returns the {@linkplain SpecificationGoal} by the given {@linkplain UniqueIdentifier}.
	 *
	 * @param specificationGoalIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain SpecificationGoal} to retrieve.
	 * @return the {@linkplain SpecificationGoal} if it exists, {@linkplain null} otherwise.
	 */
	SpecificationGoal getSpecificationGoal(UniqueIdentifier specificationGoalIdentifier);

	/**
	 * Returns the set of {@linkplain SpecificationGoal}.
	 *
	 * @return the set of {@linkplain SpecificationGoal}.
	 */
	Set<SpecificationGoal> getSpecificationGoals();

	/**
	 * Removes the {@linkplain SpecificationGoal} by the given {@linkplain UniqueIdentifier} from the set of {@linkplain SpecificationGoal}.
	 *
	 * @param goalIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain SpecificationGoal} to remove.
	 */
	void removeSpecificationGoal(UniqueIdentifier goalIdentifier);

	/**
	 * Removes the given set of {@linkplain UniqueIdentifier} that identifies {@linkplain SpecificationGoal} from the set of {@linkplain SpecificationGoal}.
	 *
	 * @param goalIdentifiers
	 *            the set of {@linkplain UniqueIdentifier} that identifies {@linkplain SpecificationGoal} to be removed.
	 */
	void removeSpecificationGoals(Collection<UniqueIdentifier> goalIdentifiers);

	/**
	 * Removes the given set of {@linkplain UniqueIdentifier} that identifies {@linkplain SpecificationGoal} from the set of {@linkplain SpecificationGoal}.
	 *
	 * @param goalIdentifiers
	 *            the set of {@linkplain UniqueIdentifier} that identifies {@linkplain SpecificationGoal} to be removed.
	 */
	void removeSpecificationGoals(UniqueIdentifier... goalIdentifiers);

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
	 * Returns the {@linkplain Role} by the given {@linkplain UniqueIdentifier}.
	 *
	 * @param roleIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Role} to retrieve.
	 * @return the {@linkplain Role} if it exists, {@linkplain null} otherwise.
	 */
	Role getRole(UniqueIdentifier roleIdentifier);

	/**
	 * Returns the set of {@linkplain Role}.
	 *
	 * @return the set of {@linkplain Role}.
	 */
	Set<Role> getRoles();

	/**
	 * Removes the {@linkplain Role} by the given {@linkplain UniqueIdentifier} from the set of {@linkplain Role}.
	 *
	 * @param roleIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Role} to remove.
	 */
	void removeRole(UniqueIdentifier roleIdentifier);

	/**
	 * Removes the given set of {@linkplain UniqueIdentifier} that identifies the {@linkplain Role} from the set of {@linkplain Role}.
	 *
	 * @param roleIdentifiers
	 *            the set of {@linkplain UniqueIdentifier} that identifies the {@linkplain Role} to be removed.
	 */
	void removeRoles(Collection<UniqueIdentifier> roleIdentifiers);

	/**
	 * Removes the given set of {@linkplain UniqueIdentifier} that identifies the {@linkplain Role} from the set of {@linkplain Role}.
	 *
	 * @param roleIdentifiers
	 *            the set of {@linkplain UniqueIdentifier} that identifies the {@linkplain Role} to be removed.
	 */
	void removeRoles(UniqueIdentifier... roleIdentifiers);

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
	 * Returns the {@linkplain Agent} by the given {@linkplain UniqueIdentifier} .
	 *
	 * @param agentIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Agent} to retrieve.
	 * @return the {@linkplain Agent} if it exists, {@linkplain null} otherwise.
	 */
	Agent getAgent(UniqueIdentifier agentIdentifier);

	/**
	 * Returns the set of {@linkplain Agent}.
	 *
	 * @return the set of {@linkplain Agent}.
	 */
	Set<Agent> getAgents();

	/**
	 * Removes the {@linkplain Agent} by the given {@linkplain UniqueIdentifier} from the set of {@linkplain Agent}.
	 *
	 * @param agentIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Agent} to remove.
	 */
	void removeAgent(UniqueIdentifier agentIdentifier);

	/**
	 * Removes the given set of {@linkplain UniqueIdentifier} that identifies the {@linkplain Agent} from the set of {@linkplain Agent}.
	 *
	 * @param agentIdentifiers
	 *            the set of {@linkplain UniqueIdentifier} that identifies the {@linkplain Agent} to remove.
	 */
	void removeAgents(Collection<UniqueIdentifier> agentIdentifiers);

	/**
	 * Removes the given set of {@linkplain UniqueIdentifier} that identifies the {@linkplain Agent} from the set of {@linkplain Agent}.
	 *
	 * @param agentIdentifiers
	 *            the set of {@linkplain UniqueIdentifier} that identifies the {@linkplain Agent} to remove.
	 */
	void removeAgents(UniqueIdentifier... agentIdentifiers);

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
	 * Returns the {@linkplain Capability} by the given {@linkplain UniqueIdentifier}.
	 *
	 * @param capabilityIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Capability} to retrieve.
	 * @return the {@linkplain Capability} if it exists, {@linkplain null} otherwise.
	 */
	Capability getCapability(UniqueIdentifier capabilityIdentifier);

	/**
	 * Returns the set of {@linkplain Capability}.
	 *
	 * @return the set of {@linkplain Capability}.
	 */
	Set<Capability> getCapabilities();

	/**
	 * Removes the {@linkplain Capability} by the given {@linkplain UniqueIdentifier} from the set of {@linkplain Capability}.
	 *
	 * @param capabilityIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Capability} to remove.
	 */
	void removeCapability(UniqueIdentifier capabilityIdentifier);

	/**
	 * Removes the given set of {@linkplain UniqueIdentifier} that identifies the {@linkplain Capability} from the set of {@linkplain Capability}.
	 *
	 * @param capabilityIdentifiers
	 *            the set of {@linkplain UniqueIdentifier} that identifies the {@linkplain Capability} to remove.
	 */
	void removeCapabilities(Collection<UniqueIdentifier> capabilityIdentifiers);

	/**
	 * Removes the given set of {@linkplain UniqueIdentifier} that identifies the {@linkplain Capability} from the set of {@linkplain Capability}.
	 *
	 * @param capabilityIdentifiers
	 *            the set of {@linkplain UniqueIdentifier} that identifies the {@linkplain Capability} to remove.
	 */
	void removeCapabilities(UniqueIdentifier... capabilityIdentifiers);

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
	 * Returns the {@linkplain Policy} by the given {@linkplain UniqueIdentifier}.
	 *
	 * @param policyIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Policy} to retrieve.
	 * @return the {@linkplain Policy} if it exists, {@linkplain null} otherwise.
	 */
	Policy getPolicy(UniqueIdentifier policyIdentifier);

	/**
	 * Returns the set of {@linkplain Policy}.
	 *
	 * @return the set of {@linkplain Policy}.
	 */
	Set<Policy> getPolicies();

	/**
	 * Removes the given {@linkplain UniqueIdentifier} that identifies the {@linkplain Policy} from the set of {@linkplain Policy}.
	 *
	 * @param policyIdentifier
	 *            the {@linkplain UniqueIdentifier} that identifies the {@linkplain Policy} to be removed.
	 */
	void removePolicy(UniqueIdentifier policyIdentifier);

	/**
	 * Removes the given set of {@linkplain UniqueIdentifier} that identifies the {@linkplain Policy} from the set of {@linkplain Policy}.
	 *
	 * @param policyIdentifiers
	 *            the set of {@linkplain UniqueIdentifier} that identifies the {@linkplain Policy} to be removed.
	 */
	void removePolicies(Collection<UniqueIdentifier> policyIdentifiers);

	/**
	 * Removes the given set of {@linkplain UniqueIdentifier} that identifies the {@linkplain Policy} from the set of {@linkplain Policy}.
	 *
	 * @param policyIdentifiers
	 *            the set of {@linkplain UniqueIdentifier} that identifies the {@linkplain Policy} to be removed.
	 */
	void removePolicies(UniqueIdentifier... policyIdentifiers);

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
	 * Returns the {@linkplain InstanceGoal} by the given {@linkplain UniqueIdentifier} that identifies the the {@linkplain InstanceGoal}.
	 *
	 * @param goalIdentifier
	 *            the {@linkplain UniqueIdentifier} that identifies the {@linkplain InstanceGoal} to retrieve.
	 * @return the {@linkplain InstanceGoal} if it exists, {@linkplain null} otherwise.
	 */
	InstanceGoal<?> getInstanceGoal(UniqueIdentifier goalIdentifier);

	/**
	 * Returns the set of {@linkplain InstanceGoal}.
	 *
	 * @return the set of {@linkplain InstanceGoal}.
	 */
	Set<InstanceGoal<?>> getInstanceGoals();

	/**
	 * Removes the given {@linkplain UniqueIdentifier} that identifies the {@linkplain InstanceGoal} from the set of {@linkplain InstanceGoal}.
	 *
	 * @param goalIdentifier
	 *            the {@linkplain UniqueIdentifier} that identifies the {@linkplain InstanceGoal} to be removed.
	 */
	void removeInstanceGoal(UniqueIdentifier goalIdentifier);

	/**
	 * Removes the given set of {@linkplain UniqueIdentifier} that identifies the {@linkplain InstanceGoal} from the set of {@linkplain InstanceGoal}.
	 *
	 * @param goalIdentifiers
	 *            the set of {@linkplain UniqueIdentifier} that identifies the {@linkplain InstanceGoal} to be removed.
	 */
	void removeInstanceGoals(Collection<UniqueIdentifier> goalIdentifiers);

	/**
	 * Removes the given set of {@linkplain UniqueIdentifier} that identifies the {@linkplain InstanceGoal} from the set of {@linkplain InstanceGoal}.
	 *
	 * @param goalIdentifiers
	 *            the set of {@linkplain UniqueIdentifier} that identifies the {@linkplain InstanceGoal} to be removed.
	 */
	void removeInstanceGoals(UniqueIdentifier... goalIdentifiers);

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
	 * Returns the {@linkplain Attribute} by the given {@linkplain UniqueIdentifier} that identifies the {@linkplain Attribute} .
	 *
	 * @param attributeIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Attribute} to retrieve.
	 * @return the {@linkplain Attribute} if it exists, {@linkplain null} otherwise.
	 */
	Attribute getAttribute(UniqueIdentifier attributeIdentifier);

	/**
	 * Returns the set of {@linkplain Attribute}.
	 *
	 * @return the set of {@linkplain Attribute}.
	 */
	Set<Attribute> getAttributes();

	/**
	 * Removes the {@linkplain Attribute} by the given {@linkplain UniqueIdentifier} that identifies the {@linkplain Attribute} .
	 *
	 * @param attributeIdentifier
	 *            the {@linkplain UniqueIdentifier} that identifies the {@linkplain Attribute} to be removed.
	 */
	void removeAttribute(UniqueIdentifier attributeIdentifier);

	/**
	 * Removes the given set of {@linkplain UniqueIdentifier} that identifies {@linkplain Attribute} from the set of {@linkplain Attribute}.
	 *
	 * @param attributeIdentifiers
	 *            the set of {@linkplain UniqueIdentifier} that identifies {@linkplain Attribute} to be removed.
	 */
	void removeAttributes(Collection<UniqueIdentifier> attributeIdentifiers);

	/**
	 * Removes the given set of {@linkplain UniqueIdentifier} that identifies {@linkplain Attribute} from the set of {@linkplain Attribute}.
	 *
	 * @param attributeIdentifiers
	 *            the set of {@linkplain UniqueIdentifier} that identifies {@linkplain Attribute} to be removed.
	 */
	void removeAttributes(UniqueIdentifier... attributeIdentifiers);

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
	 * Returns the {@linkplain Task} by the given {@linkplain UniqueIdentifier} that identifies the {@linkplain Task}.
	 *
	 * @param taskIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Task} to retrieve.
	 * @return the {@linkplain Task} if it exists, {@linkplain null} otherwise.
	 */
	Task getTask(UniqueIdentifier taskIdentifier);

	/**
	 * Returns the set of {@linkplain Task}.
	 *
	 * @return the set of {@linkplain Task}.
	 */
	Set<Task> getTasks();

	/**
	 * Removes the {@linkplain Task} by the given {@linkplain UniqueIdentifier} that identifies the {@linkplain Task}.
	 *
	 * @param taskIdentifier
	 *            the {@linkplain UniqueIdentifier} that identifies the {@linkplain Task} to be removed.
	 */
	void removeTask(UniqueIdentifier taskIdentifier);

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
	 * Returns the {@linkplain PerformanceFunction} by the given {@linkplain UniqueIdentifier} that identifies the {@linkplain PerformanceFunction}.
	 *
	 * @param performanceFunctionIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain PerformanceFunction} to retrieve.
	 * @return the {@linkplain PerformanceFunction} if it exists, {@linkplain null} otherwise.
	 */
	PerformanceFunction getPerformanceFunction(UniqueIdentifier performanceFunctionIdentifier);

	/**
	 * Returns the set of {@linkplain PerformanceFunction}.
	 *
	 * @return the set of {@linkplain PerformanceFunction}.
	 */
	Set<PerformanceFunction> getPerformanceFunctions();

	/**
	 * Removes the {@linkplain PerformanceFunction} by the given {@linkplain UniqueIdentifier} that identifies the {@linkplain PerformanceFunction}.
	 *
	 * @param performanceFunctionIdentifier
	 *            the {@linkplain UniqueIdentifier} that identifies the {@linkplain PerformanceFunction} to be removed.
	 */
	void removePerformanceFunction(UniqueIdentifier performanceFunctionIdentifier);

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
	 * Returns the {@linkplain Characteristic} represented by the given {@linkplain UniqueIdentifier}.
	 *
	 * @param characteristicIdentifier
	 *            the {@linkplain UniqueIdentifier} of the {@linkplain Characteristic} to return.
	 * @return the {@linkplain Characteristic} represented by the given {@linkplain UniqueIdentifier}, {@linkplain null} otherwise.
	 */
	Characteristic getCharacteristic(UniqueIdentifier characteristicIdentifier);

	/**
	 * Returns the set of {@linkplain Characteristic}.
	 *
	 * @return the set of {@linkplain Characteristic}.
	 */
	Set<Characteristic> getCharacteristics();

	/**
	 * Removes the {@linkplain Characteristic} (from the given {@linkplain UniqueIdentifier}) from the set of {@linkplain Characteristic}.
	 *
	 * @param characteristicIdentifier
	 *            the {@linkplain UniqueIdentifier} of the {@linkplain Characteristic} to remove.
	 */
	void removeCharacteristic(UniqueIdentifier characteristicIdentifier);

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
	 * Returns the {@linkplain Assignment} by the given {@linkplain UniqueIdentifier} that identifies the {@linkplain Assignment} .
	 *
	 * @param assignmentIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Assignment} to retrieve.
	 * @return the {@linkplain Assignment} if it exists, {@linkplain null} otherwise.
	 */
	Assignment getAssignment(UniqueIdentifier assignmentIdentifier);

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
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Agent} to retrieve the set of {@linkplain Assignment}.
	 * @return the set of {@linkplain Assignment} assigned to the given {@linkplain Agent}.
	 */
	Set<Assignment> getAssignmentsOfAgent(UniqueIdentifier agentIdentifier);

	/**
	 * Removes the {@linkplain Assignment} by the given {@linkplain UniqueIdentifier} that identifies the {@linkplain Assignment} .
	 *
	 * @param assignmentIdentifier
	 *            the {@linkplain UniqueIdentifier} that identifies the {@linkplain Assignment} to be removed.
	 */
	void removeAssignment(UniqueIdentifier assignmentIdentifier);

	/**
	 * Removes the given set of {@linkplain UniqueIdentifier} that identifies {@linkplain Assignment} from the set of {@linkplain Assignment}.
	 *
	 * @param assignmentIdentifiers
	 *            the set of {@linkplain UniqueIdentifier} that identifies {@linkplain Assignment} to be removed.
	 */
	void removeAssignments(Collection<UniqueIdentifier> assignmentIdentifiers);

	/**
	 * Removes the given set of {@linkplain UniqueIdentifier} that identifies {@linkplain Assignment} from the set of {@linkplain Assignment}.
	 *
	 * @param assignmentIdentifiers
	 *            the set of {@linkplain UniqueIdentifier} that identifies {@linkplain Assignment} to be removed.
	 */
	void removeAssignments(UniqueIdentifier... assignmentIdentifiers);

	/**
	 * Clears the set of {@linkplain Assignment}.
	 */
	void removeAllAssignments();

	/**
	 * Creates the achieves relation with the given {@linkplain Role}, {@linkplain SpecificationGoal}, and score.
	 *
	 * @param roleIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Role} for the achieves relation.
	 * @param goalIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain SpecificationGoal} for the achieves relation.
	 */
	void addAchievesRelation(UniqueIdentifier roleIdentifier, UniqueIdentifier goalIdentifier);

	/**
	 * Removes the achieves relation with the given {@linkplain Role} and {@linkplain SpecificationGoal}.
	 *
	 * @param roleIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Role} for the achieves relation.
	 * @param goalIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain SpecificationGoal} for the achieves relation.
	 */
	void removeAchievesRelation(UniqueIdentifier roleIdentifier, UniqueIdentifier goalIdentifier);

	/**
	 * Creates the requires relation with the given {@linkplain Role} and {@linkplain Capability}.
	 *
	 * @param roleIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Role} for the requires relation.
	 * @param capabilityIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Capability} for the requires relation.
	 */
	void addRequiresRelation(UniqueIdentifier roleIdentifier, UniqueIdentifier capabilityIdentifier);

	/**
	 * Removes the requires relation with the given {@linkplain Role} and {@linkplain Capability}.
	 *
	 * @param roleIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Role} for the requires relation.
	 * @param capabilityIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Capability} for the requires relation.
	 */
	void removeRequiresRelation(UniqueIdentifier roleIdentifier, UniqueIdentifier capabilityIdentifier);

	/**
	 * Creates the possesses relation with the given {@linkplain Agent}, {@linkplain Capability}, and score.
	 *
	 * @param agentIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Agent} for the possesses relation.
	 * @param capabilityIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Capability} for the possesses relation.
	 * @param score
	 *            the score of the possesses relation.
	 */
	void addPossessesRelation(UniqueIdentifier agentIdentifier, UniqueIdentifier capabilityIdentifier, double score);

	/**
	 * Removes the possesses relation with the given {@linkplain Agent} and {@linkplain Capability}.
	 *
	 * @param agentIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Agent} for the possesses relation.
	 * @param capabilityIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Capability} for the possesses relation.
	 */
	void removePossessesRelation(UniqueIdentifier agentIdentifier, UniqueIdentifier capabilityIdentifier);

	/**
	 * Updates the possesses relation with the given {@linkplain Agent}, {@linkplain Capability}, and score.
	 *
	 * @param agentIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Agent} for the possesses relation.
	 * @param capabilityIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Capability} for the possesses relation.
	 * @param score
	 *            the score of the possesses relation.
	 */
	void updatePossessesRelation(UniqueIdentifier agentIdentifier, UniqueIdentifier capabilityIdentifier, double score);

	/**
	 * Creates the {@linkplain NeedsRelation} with the given {@linkplain Role} and {@linkplain Attribute}.
	 *
	 * @param roleIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Role} for the {@linkplain NeedsRelation}.
	 * @param attributeIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Attribute} for the {@linkplain NeedsRelation} .
	 */
	void addNeedsRelation(UniqueIdentifier roleIdentifier, UniqueIdentifier attributeIdentifier);

	/**
	 * Removes the {@linkplain NeedsRelation} with the given {@linkplain Role} and {@linkplain Attribute}.
	 *
	 * @param roleIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Role} for the {@linkplain NeedsRelation}.
	 * @param attributeIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Attribute} for the {@linkplain NeedsRelation} .
	 */
	void removeNeedsRelation(UniqueIdentifier roleIdentifier, UniqueIdentifier attributeIdentifier);

	/**
	 * Creates the {@linkplain HasRelation} with the given {@linkplain Role}, {@linkplain Attribute}, and a {@linkplain double} representing the score.
	 *
	 * @param agentIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Agent} for the {@linkplain HasRelation}.
	 * @param attributeIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Attribute} for the {@linkplain HasRelation}.
	 * @param value
	 *            the {@linkplain double} representing the value for the {@linkplain HasRelation}.
	 */
	void addHasRelation(UniqueIdentifier agentIdentifier, UniqueIdentifier attributeIdentifier, double value);

	/**
	 * Removes the {@linkplain HasRelation} with the given {@linkplain Agent} and {@linkplain Attribute}.
	 *
	 * @param agentIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Agent} for the {@linkplain HasRelation}.
	 * @param attributeIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Attribute} for the {@linkplain HasRelation}.
	 */
	void removeHasRelation(UniqueIdentifier agentIdentifier, UniqueIdentifier attributeIdentifier);

	/**
	 * Updates the {@linkplain HasRelation} with the given {@linkplain Agent}, {@linkplain Attribute}, and {@linkplain double} representing the score.
	 *
	 * @param agentIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Agent} for the {@linkplain HasRelation}.
	 * @param attributeIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Attribute} for the {@linkplain HasRelation}.
	 * @param score
	 *            the {@linkplain double} representing the score for the {@linkplain HasRelation}.
	 */
	void updateHasRelation(UniqueIdentifier agentIdentifier, UniqueIdentifier attributeIdentifier, double score);

	/**
	 * Creates the {@linkplain UsesRelation} with the given {@linkplain Role} and {@linkplain PerformanceFunction}.
	 *
	 * @param roleIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Role} for the {@linkplain UsesRelation}.
	 * @param functionIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain PerformanceFunction} for the {@linkplain UsesRelation}.
	 */
	void addUsesRelation(UniqueIdentifier roleIdentifier, UniqueIdentifier functionIdentifier);

	/**
	 * Removes the {@linkplain UsesRelation} with the given {@linkplain Role} and {@linkplain PerformanceFunction}.
	 *
	 * @param roleIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Role} for the {@linkplain UsesRelation}.
	 * @param functionIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain PerformanceFunction} for the {@linkplain UsesRelation}.
	 */
	void removeUsesRelation(UniqueIdentifier roleIdentifier, UniqueIdentifier functionIdentifier);

	/**
	 * Creates the {@linkplain ModeratesRelation} with the given {@linkplain PerformanceFunction} and {@linkplain Attribute}.
	 *
	 * @param performanceFunctionIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain PerformanceFunction} for the {@linkplain ModeratesRelation}.
	 * @param attributeIdentifier
	 *            the {@linkplain UniqueIdentifier} identifying the {@linkplain Attribute} for the {@linkplain ModeratesRelation}.
	 */
	void setModeratesRelation(UniqueIdentifier performanceFunctionIdentifier, UniqueIdentifier attributeIdentifier);

	/**
	 * Creates the {@linkplain ContainsRelation} with the {@linkplain Role} (from the given {@linkplain UniqueIdentifier}), {@linkplain Characteristic} (from
	 * the given {@linkplain UniqueIdentifier} ), and {@linkplain double} value.
	 *
	 * @param roleIdentifier
	 *            the {@linkplain UniqueIdentifier} of the {@linkplain Role} for the {@linkplain ContainsRelation}.
	 * @param characteristicIdentifier
	 *            the {@linkplain UniqueIdentifier} of the {@linkplain Characteristic} for the {@linkplain ContainsRelation}.
	 * @param value
	 *            the {@linkplain double} value for the {@linkplain ContainsRelation}.
	 */
	void addContainsRelation(UniqueIdentifier roleIdentifier, UniqueIdentifier characteristicIdentifier, double value);

	/**
	 * Removes the {@linkplain ContainsRelation} of the {@linkplain Role} (from the given {@linkplain UniqueIdentifier}) and {@linkplain Characteristic} (from
	 * the given {@linkplain UniqueIdentifier}).
	 *
	 * @param roleIdentifier
	 *            the {@linkplain UniqueIdentifier} of the {@linkplain Role} for the {@linkplain ContainsRelation}.
	 * @param characteristicIdentifier
	 *            the {@linkplain UniqueIdentifier} of the {@linkplain Characteristic} for the {@linkplain ContainsRelation}.
	 */
	void removeContainsRelation(UniqueIdentifier roleIdentifier, UniqueIdentifier characteristicIdentifier);

	/**
	 * Updates the {@linkplain ContainsRelation} of the {@linkplain Role} (from the given {@linkplain UniqueIdentifier}) and {@linkplain Characteristic} (from
	 * the given {@linkplain UniqueIdentifier}) with the given new {@linkplain double} value.
	 *
	 * @param roleIdentifier
	 *            the {@linkplain UniqueIdentifier} of the {@linkplain Role} for the {@linkplain ContainsRelation}.
	 * @param characteristicIdentifier
	 *            the {@linkplain UniqueIdentifier} of the {@linkplain Characteristic} for the {@linkplain ContainsRelation}.
	 * @param value
	 *            the new {@linkplain double} value for the {@linkplain ContainsRelation}.
	 */
	void updateContainsRelation(UniqueIdentifier roleIdentifier, UniqueIdentifier characteristicIdentifier, double value);

}