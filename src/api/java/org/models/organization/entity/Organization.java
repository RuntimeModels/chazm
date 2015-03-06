/*
 * Organization.java
 *
 * Created on Mar 24, 2009
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Set;

import org.models.organization.identifier.UniqueIdentifier;
import org.models.organization.parser.SpecificationGoalProvider;
import org.models.organization.parser.UniqueIdentifierProvider;
import org.models.organization.relation.Assignment;
import org.models.organization.relation.Task;

/**
 * The <code>Org1</code> interface defines the basic functionality of an organization.
 *
 * @author Christopher Zhong
 * @version $Revision: 1.9.2.7 $, $Date: 2011/09/19 14:26:37 $
 * @since 4.0
 */
public interface Organization {

	/**
	 * Populates the <code>Organization</code>.
	 *
	 * @param file
	 *            the <code>File</code> from which to populate the <code>Organization</code>.
	 * @param specificationGoalProvider
	 *            the <code>SpecificationGoalProvider</code> from which to retrieve <code>SpecificationGoal</code>s.
	 * @param uniqueIdentifierProvider
	 *            the <code>UniqueIdentifierProvider</code> which provides <code>UniqueIdentifier</code>.
	 * @return the populated <code>Organization</code>.
	 */
	@Deprecated
	Organization populate(File file, SpecificationGoalProvider specificationGoalProvider, UniqueIdentifierProvider uniqueIdentifierProvider);

	/**
	 * Populates the <code>Organization</code>.
	 *
	 * @param path
	 *            the <code>Path</code> from which to populate the <code>Organization</code>.
	 * @param specificationGoalProvider
	 *            the <code>SpecificationGoalProvider</code> from which to retrieve <code>SpecificationGoal</code>s.
	 * @param uniqueIdentifierProvider
	 *            the <code>UniqueIdentifierProvider</code> which provides <code>UniqueIdentifier</code>.
	 * @return the populated <code>Organization</code>.
	 */
	Organization populate(Path path, SpecificationGoalProvider specificationGoalProvider, UniqueIdentifierProvider uniqueIdentifierProvider);

	/**
	 * Adds the given <code>SpecificationGoal</code> to the set of <code>SpecificationGoal</code>.
	 *
	 * @param specificationGoal
	 *            the <code>SpecificationGoal</code> to be added.
	 */
	void addSpecificationGoal(SpecificationGoal specificationGoal);

	/**
	 * Adds the given set of <code>SpecificationGoal</code> to the set of <code>SpecificationGoal</code>.
	 *
	 * @param specificationGoals
	 *            the set of <code>SpecificationGoal</code> to be added.
	 */
	void addSpecificationGoals(Collection<SpecificationGoal> specificationGoals);

	/**
	 * Adds the given set of <code>SpecificationGoal</code> to the set of <code>SpecificationGoal</code>.
	 *
	 * @param specificationGoals
	 *            the set of <code>SpecificationGoal</code> to be added.
	 */
	void addSpecificationGoals(SpecificationGoal... specificationGoals);

	/**
	 * Returns the <code>SpecificationGoal</code> by the given <code>UniqueIdentifier</code>.
	 *
	 * @param specificationGoalIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>SpecificationGoal</code> to retrieve.
	 * @return the <code>SpecificationGoal</code> if it exists, <code>null</code> otherwise.
	 */
	SpecificationGoal getSpecificationGoal(UniqueIdentifier specificationGoalIdentifier);

	/**
	 * Returns the set of <code>SpecificationGoal</code>.
	 *
	 * @return the set of <code>SpecificationGoal</code>.
	 */
	Set<SpecificationGoal> getSpecificationGoals();

	/**
	 * Removes the <code>SpecificationGoal</code> by the given <code>UniqueIdentifier</code> from the set of <code>SpecificationGoal</code>.
	 *
	 * @param goalIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>SpecificationGoal</code> to remove.
	 */
	void removeSpecificationGoal(UniqueIdentifier goalIdentifier);

	/**
	 * Removes the given set of <code>UniqueIdentifier</code> that identifies <code>SpecificationGoal</code> from the set of <code>SpecificationGoal</code>.
	 *
	 * @param goalIdentifiers
	 *            the set of <code>UniqueIdentifier</code> that identifies <code>SpecificationGoal</code> to be removed.
	 */
	void removeSpecificationGoals(Collection<UniqueIdentifier> goalIdentifiers);

	/**
	 * Removes the given set of <code>UniqueIdentifier</code> that identifies <code>SpecificationGoal</code> from the set of <code>SpecificationGoal</code>.
	 *
	 * @param goalIdentifiers
	 *            the set of <code>UniqueIdentifier</code> that identifies <code>SpecificationGoal</code> to be removed.
	 */
	void removeSpecificationGoals(UniqueIdentifier... goalIdentifiers);

	/**
	 * Clears the set of <code>SpecificationGoal</code>.
	 */
	void removeAllSpecificationGoals();

	/**
	 * Adds the given <code>Role</code> to the set of <code>Role</code>.
	 *
	 * @param role
	 *            the <code>Role</code> to be added.
	 */
	void addRole(Role role);

	/**
	 * Adds the given set of <code>Role</code> to the set of <code>Role</code>.
	 *
	 * @param roles
	 *            the set of <code>Role</code> to be added.
	 */
	void addRoles(Collection<Role> roles);

	/**
	 * Adds the given set of <code>Role</code> to the set of <code>Role</code>.
	 *
	 * @param roles
	 *            the set of <code>Role</code> to be added.
	 */
	void addRoles(Role... roles);

	/**
	 * Returns the <code>Role</code> by the given <code>UniqueIdentifier</code>.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Role</code> to retrieve.
	 * @return the <code>Role</code> if it exists, <code>null</code> otherwise.
	 */
	Role getRole(UniqueIdentifier roleIdentifier);

	/**
	 * Returns the set of <code>Role</code>.
	 *
	 * @return the set of <code>Role</code>.
	 */
	Set<Role> getRoles();

	/**
	 * Removes the <code>Role</code> by the given <code>UniqueIdentifier</code> from the set of <code>Role</code>.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Role</code> to remove.
	 */
	void removeRole(UniqueIdentifier roleIdentifier);

	/**
	 * Removes the given set of <code>UniqueIdentifier</code> that identifies the <code>Role</code> from the set of <code>Role</code>.
	 *
	 * @param roleIdentifiers
	 *            the set of <code>UniqueIdentifier</code> that identifies the <code>Role</code> to be removed.
	 */
	void removeRoles(Collection<UniqueIdentifier> roleIdentifiers);

	/**
	 * Removes the given set of <code>UniqueIdentifier</code> that identifies the <code>Role</code> from the set of <code>Role</code>.
	 *
	 * @param roleIdentifiers
	 *            the set of <code>UniqueIdentifier</code> that identifies the <code>Role</code> to be removed.
	 */
	void removeRoles(UniqueIdentifier... roleIdentifiers);

	/**
	 * Clears the set of <code>Role</code>.
	 */
	void removeAllRoles();

	/**
	 * Adds the given <code>Agent</code> to the set of <code>Agent</code>.
	 *
	 * @param agent
	 *            the <code>Agent</code> to be added.
	 */
	void addAgent(Agent agent);

	/**
	 * Adds the given set of <code>Agent</code> to the set of <code>Agent</code> .
	 *
	 * @param agents
	 *            the set of <code>Agent</code> to be added.
	 */
	void addAgents(Collection<Agent> agents);

	/**
	 * Adds the given set of <code>Agent</code> to the set of <code>Agent</code> .
	 *
	 * @param agents
	 *            the set of <code>Agent</code> to be added.
	 */
	void addAgents(Agent... agents);

	/**
	 * Returns the <code>Agent</code> by the given <code>UniqueIdentifier</code> .
	 *
	 * @param agentIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Agent</code> to retrieve.
	 * @return the <code>Agent</code> if it exists, <code>null</code> otherwise.
	 */
	Agent getAgent(UniqueIdentifier agentIdentifier);

	/**
	 * Returns the set of <code>Agent</code>.
	 *
	 * @return the set of <code>Agent</code>.
	 */
	Set<Agent> getAgents();

	/**
	 * Removes the <code>Agent</code> by the given <code>UniqueIdentifier</code> from the set of <code>Agent</code>.
	 *
	 * @param agentIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Agent</code> to remove.
	 */
	void removeAgent(UniqueIdentifier agentIdentifier);

	/**
	 * Removes the given set of <code>UniqueIdentifier</code> that identifies the <code>Agent</code> from the set of <code>Agent</code>.
	 *
	 * @param agentIdentifiers
	 *            the set of <code>UniqueIdentifier</code> that identifies the <code>Agent</code> to remove.
	 */
	void removeAgents(Collection<UniqueIdentifier> agentIdentifiers);

	/**
	 * Removes the given set of <code>UniqueIdentifier</code> that identifies the <code>Agent</code> from the set of <code>Agent</code>.
	 *
	 * @param agentIdentifiers
	 *            the set of <code>UniqueIdentifier</code> that identifies the <code>Agent</code> to remove.
	 */
	void removeAgents(UniqueIdentifier... agentIdentifiers);

	/**
	 * Clears the set of <code>Agent</code>.
	 */
	void removeAllAgents();

	/**
	 * Adds the given <code>Capability</code> to the set of <code>Capability</code>.
	 *
	 * @param capability
	 *            the <code>Capability</code> to be added.
	 */
	void addCapability(Capability capability);

	/**
	 * Adds the given set of <code>Capability</code> to the set of <code>Capability</code>.
	 *
	 * @param capabilities
	 *            the set of <code>Capability</code> to be added.
	 */
	void addCapabilities(Collection<Capability> capabilities);

	/**
	 * Adds the given set of <code>Capability</code> to the set of <code>Capability</code>.
	 *
	 * @param capabilities
	 *            the set of <code>Capability</code> to be added.
	 */
	void addCapabilities(Capability... capabilities);

	/**
	 * Returns the <code>Capability</code> by the given <code>UniqueIdentifier</code>.
	 *
	 * @param capabilityIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Capability</code> to retrieve.
	 * @return the <code>Capability</code> if it exists, <code>null</code> otherwise.
	 */
	Capability getCapability(UniqueIdentifier capabilityIdentifier);

	/**
	 * Returns the set of <code>Capability</code>.
	 *
	 * @return the set of <code>Capability</code>.
	 */
	Set<Capability> getCapabilities();

	/**
	 * Removes the <code>Capability</code> by the given <code>UniqueIdentifier</code> from the set of <code>Capability</code>.
	 *
	 * @param capabilityIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Capability</code> to remove.
	 */
	void removeCapability(UniqueIdentifier capabilityIdentifier);

	/**
	 * Removes the given set of <code>UniqueIdentifier</code> that identifies the <code>Capability</code> from the set of <code>Capability</code>.
	 *
	 * @param capabilityIdentifiers
	 *            the set of <code>UniqueIdentifier</code> that identifies the <code>Capability</code> to remove.
	 */
	void removeCapabilities(Collection<UniqueIdentifier> capabilityIdentifiers);

	/**
	 * Removes the given set of <code>UniqueIdentifier</code> that identifies the <code>Capability</code> from the set of <code>Capability</code>.
	 *
	 * @param capabilityIdentifiers
	 *            the set of <code>UniqueIdentifier</code> that identifies the <code>Capability</code> to remove.
	 */
	void removeCapabilities(UniqueIdentifier... capabilityIdentifiers);

	/**
	 * Clears the set of <code>Capability</code>.
	 */
	void removeAllCapabilities();

	/**
	 * Adds the given <code>Policy</code> to the set of <code>Policy</code>.
	 *
	 * @param policy
	 *            the <code>Policy</code> to be added.
	 */
	void addPolicy(Policy policy);

	/**
	 * Adds the given set of <code>Policy</code> to the set of <code>Policy</code>.
	 *
	 * @param policies
	 *            the set of <code>Policy</code> to be added.
	 */
	void addPolicies(Collection<Policy> policies);

	/**
	 * Adds the given set of <code>Policy</code> to the set of <code>Policy</code>.
	 *
	 * @param policies
	 *            the set of <code>Policy</code> to be added.
	 */
	void addPolicies(Policy... policies);

	/**
	 * Returns the <code>Policy</code> by the given <code>UniqueIdentifier</code>.
	 *
	 * @param policyIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Policy</code> to retrieve.
	 * @return the <code>Policy</code> if it exists, <code>null</code> otherwise.
	 */
	Policy getPolicy(UniqueIdentifier policyIdentifier);

	/**
	 * Returns the set of <code>Policy</code>.
	 *
	 * @return the set of <code>Policy</code>.
	 */
	Set<Policy> getPolicies();

	/**
	 * Removes the given <code>UniqueIdentifier</code> that identifies the <code>Policy</code> from the set of <code>Policy</code>.
	 *
	 * @param policyIdentifier
	 *            the <code>UniqueIdentifier</code> that identifies the <code>Policy</code> to be removed.
	 */
	void removePolicy(UniqueIdentifier policyIdentifier);

	/**
	 * Removes the given set of <code>UniqueIdentifier</code> that identifies the <code>Policy</code> from the set of <code>Policy</code>.
	 *
	 * @param policyIdentifiers
	 *            the set of <code>UniqueIdentifier</code> that identifies the <code>Policy</code> to be removed.
	 */
	void removePolicies(Collection<UniqueIdentifier> policyIdentifiers);

	/**
	 * Removes the given set of <code>UniqueIdentifier</code> that identifies the <code>Policy</code> from the set of <code>Policy</code>.
	 *
	 * @param policyIdentifiers
	 *            the set of <code>UniqueIdentifier</code> that identifies the <code>Policy</code> to be removed.
	 */
	void removePolicies(UniqueIdentifier... policyIdentifiers);

	/**
	 * Clears the set of <code>Policy</code>.
	 */
	void removeAllPolicies();

	/**
	 * Adds the given <code>InstanceGoal</code> to the set of <code>InstanceGoal</code>.
	 *
	 * @param instanceGoal
	 *            the <code>InstanceGoal</code> to be added.
	 */
	void addInstanceGoal(InstanceGoal<?> instanceGoal);

	/**
	 * Adds the given set of <code>InstanceGoal</code> to the set of <code>InstanceGoal</code>.
	 *
	 * @param instanceGoals
	 *            the set of <code>InstanceGoal</code> to be added.
	 */
	void addInstanceGoals(Collection<InstanceGoal<?>> instanceGoals);

	/**
	 * Adds the given set of <code>InstanceGoal</code> to the set of <code>InstanceGoal</code>.
	 *
	 * @param instanceGoals
	 *            the set of <code>InstanceGoal</code> to be added.
	 */
	void addInstanceGoals(InstanceGoal<?>... instanceGoals);

	/**
	 * Returns the <code>InstanceGoal</code> by the given <code>UniqueIdentifier</code> that identifies the the <code>InstanceGoal</code>.
	 *
	 * @param goalIdentifier
	 *            the <code>UniqueIdentifier</code> that identifies the <code>InstanceGoal</code> to retrieve.
	 * @return the <code>InstanceGoal</code> if it exists, <code>null</code> otherwise.
	 */
	InstanceGoal<?> getInstanceGoal(UniqueIdentifier goalIdentifier);

	/**
	 * Returns the set of <code>InstanceGoal</code>.
	 *
	 * @return the set of <code>InstanceGoal</code>.
	 */
	Set<InstanceGoal<?>> getInstanceGoals();

	/**
	 * Removes the given <code>UniqueIdentifier</code> that identifies the <code>InstanceGoal</code> from the set of <code>InstanceGoal</code>.
	 *
	 * @param goalIdentifier
	 *            the <code>UniqueIdentifier</code> that identifies the <code>InstanceGoal</code> to be removed.
	 */
	void removeInstanceGoal(UniqueIdentifier goalIdentifier);

	/**
	 * Removes the given set of <code>UniqueIdentifier</code> that identifies the <code>InstanceGoal</code> from the set of <code>InstanceGoal</code>.
	 *
	 * @param goalIdentifiers
	 *            the set of <code>UniqueIdentifier</code> that identifies the <code>InstanceGoal</code> to be removed.
	 */
	void removeInstanceGoals(Collection<UniqueIdentifier> goalIdentifiers);

	/**
	 * Removes the given set of <code>UniqueIdentifier</code> that identifies the <code>InstanceGoal</code> from the set of <code>InstanceGoal</code>.
	 *
	 * @param goalIdentifiers
	 *            the set of <code>UniqueIdentifier</code> that identifies the <code>InstanceGoal</code> to be removed.
	 */
	void removeInstanceGoals(UniqueIdentifier... goalIdentifiers);

	/**
	 * Clears the set of <code>InstanceGoal</code>.
	 */
	void removeAllInstanceGoals();

	/**
	 * Adds the given <code>Assignment</code> to the set of <code>Assignment</code>.
	 *
	 * @param assignment
	 *            the <code>Assignment</code> to be added.
	 */
	void addAssignment(Assignment assignment);

	/**
	 * Adds the given set of <code>Assignment</code> to the set of <code>Assignment</code>.
	 *
	 * @param assignments
	 *            the set of <code>Assignment</code> to be added.
	 */
	void addAssignments(Collection<Assignment> assignments);

	/**
	 * Adds the given set of <code>Assignment</code> to the set of <code>Assignment</code>.
	 *
	 * @param assignments
	 *            the set of <code>Assignment</code> to be added.
	 */
	void addAssignments(Assignment... assignments);

	/**
	 * Returns the <code>Assignment</code> by the given <code>UniqueIdentifier</code> that identifies the <code>Assignment</code> .
	 *
	 * @param assignmentIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Assignment</code> to retrieve.
	 * @return the <code>Assignment</code> if it exists, <code>null</code> otherwise.
	 */
	Assignment getAssignment(UniqueIdentifier assignmentIdentifier);

	/**
	 * Returns the set of <code>Assignment</code>.
	 *
	 * @return the set of <code>Assignment</code>.
	 */
	Set<Assignment> getAssignments();

	/**
	 * Returns the set of <code>Assignment</code> assigned to the given <code>Agent</code>.
	 *
	 * @param agentIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Agent</code> to retrieve the set of <code>Assignment</code>.
	 * @return the set of <code>Assignment</code> assigned to the given <code>Agent</code>.
	 */
	Set<Assignment> getAssignmentsOfAgent(UniqueIdentifier agentIdentifier);

	/**
	 * Removes the <code>Assignment</code> by the given <code>UniqueIdentifier</code> that identifies the <code>Assignment</code> .
	 *
	 * @param assignmentIdentifier
	 *            the <code>UniqueIdentifier</code> that identifies the <code>Assignment</code> to be removed.
	 */
	void removeAssignment(UniqueIdentifier assignmentIdentifier);

	/**
	 * Removes the given set of <code>UniqueIdentifier</code> that identifies <code>Assignment</code> from the set of <code>Assignment</code>.
	 *
	 * @param assignmentIdentifiers
	 *            the set of <code>UniqueIdentifier</code> that identifies <code>Assignment</code> to be removed.
	 */
	void removeAssignments(Collection<UniqueIdentifier> assignmentIdentifiers);

	/**
	 * Removes the given set of <code>UniqueIdentifier</code> that identifies <code>Assignment</code> from the set of <code>Assignment</code>.
	 *
	 * @param assignmentIdentifiers
	 *            the set of <code>UniqueIdentifier</code> that identifies <code>Assignment</code> to be removed.
	 */
	void removeAssignments(UniqueIdentifier... assignmentIdentifiers);

	/**
	 * Clears the set of <code>Assignment</code>.
	 */
	void removeAllAssignments();

	/**
	 * Adds the given <code>Attribute</code> to the set of <code>Attribute</code>.
	 *
	 * @param attribute
	 *            the <code>Attribute</code> to be added.
	 */
	void addAttribute(Attribute attribute);

	/**
	 * Adds the given set of <code>Attribute</code> to the set of <code>Attribute</code>.
	 *
	 * @param attributes
	 *            the set of <code>Attribute</code> to be added.
	 */
	void addAttributes(Collection<Attribute> attributes);

	/**
	 * Adds the given set of <code>Attribute</code> to the set of <code>Attribute</code>.
	 *
	 * @param attributes
	 *            the set of <code>Attribute</code> to be added.
	 */
	void addAttributes(Attribute... attributes);

	/**
	 * Returns the <code>Attribute</code> by the given <code>UniqueIdentifier</code> that identifies the <code>Attribute</code> .
	 *
	 * @param attributeIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Attribute</code> to retrieve.
	 * @return the <code>Attribute</code> if it exists, <code>null</code> otherwise.
	 */
	Attribute getAttribute(UniqueIdentifier attributeIdentifier);

	/**
	 * Returns the set of <code>Attribute</code>.
	 *
	 * @return the set of <code>Attribute</code>.
	 */
	Set<Attribute> getAttributes();

	/**
	 * Removes the <code>Attribute</code> by the given <code>UniqueIdentifier</code> that identifies the <code>Attribute</code> .
	 *
	 * @param attributeIdentifier
	 *            the <code>UniqueIdentifier</code> that identifies the <code>Attribute</code> to be removed.
	 */
	void removeAttribute(UniqueIdentifier attributeIdentifier);

	/**
	 * Removes the given set of <code>UniqueIdentifier</code> that identifies <code>Attribute</code> from the set of <code>Attribute</code>.
	 *
	 * @param attributeIdentifiers
	 *            the set of <code>UniqueIdentifier</code> that identifies <code>Attribute</code> to be removed.
	 */
	void removeAttributes(Collection<UniqueIdentifier> attributeIdentifiers);

	/**
	 * Removes the given set of <code>UniqueIdentifier</code> that identifies <code>Attribute</code> from the set of <code>Attribute</code>.
	 *
	 * @param attributeIdentifiers
	 *            the set of <code>UniqueIdentifier</code> that identifies <code>Attribute</code> to be removed.
	 */
	void removeAttributes(UniqueIdentifier... attributeIdentifiers);

	/**
	 * Clears the set of <code>Attribute</code>.
	 */
	void removeAllAttributes();

	/**
	 * Adds the given <code>Task</code> to the set of <code>Task</code>.
	 *
	 * @param task
	 *            the <code>Task</code> to be added.
	 */
	void addTask(Task task);

	/**
	 * Adds the given set of <code>Task</code> to the set of <code>Task</code>.
	 *
	 * @param tasks
	 *            the set of <code>Task</code> to be added.
	 */
	void addTasks(Collection<Task> tasks);

	/**
	 * Adds the given set of <code>Task</code> to the set of <code>Task</code>.
	 *
	 * @param tasks
	 *            the set of <code>Task</code> to be added.
	 */
	void addTasks(Task... tasks);

	/**
	 * Returns the <code>Task</code> by the given <code>UniqueIdentifier</code> that identifies the <code>Task</code>.
	 *
	 * @param taskIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Task</code> to retrieve.
	 * @return the <code>Task</code> if it exists, <code>null</code> otherwise.
	 */
	Task getTask(UniqueIdentifier taskIdentifier);

	/**
	 * Returns the set of <code>Task</code>.
	 *
	 * @return the set of <code>Task</code>.
	 */
	Set<Task> getTasks();

	/**
	 * Removes the <code>Task</code> by the given <code>UniqueIdentifier</code> that identifies the <code>Task</code>.
	 *
	 * @param taskIdentifier
	 *            the <code>UniqueIdentifier</code> that identifies the <code>Task</code> to be removed.
	 */
	void removeTask(UniqueIdentifier taskIdentifier);

	/**
	 * Clears the set of <code>Task</code>.
	 */
	void removeAllTasks();

	/**
	 * Adds the given <code>PerformanceFunction</code> to the set of <code>PerformanceFunction</code>.
	 *
	 * @param performanceFunction
	 *            the <code>PerformanceFunction</code> to be added.
	 */
	void addPerformanceFunction(PerformanceFunction performanceFunction);

	/**
	 * Adds the given set of <code>PerformanceFunction</code> to the set of <code>PerformanceFunction</code>.
	 *
	 * @param performanceFunctions
	 *            the set of <code>PerformanceFunction</code> to be added.
	 */
	void addPerformanceFunctions(Collection<PerformanceFunction> performanceFunctions);

	/**
	 * Adds the given set of <code>PerformanceFunction</code> to the set of <code>PerformanceFunction</code>.
	 *
	 * @param performanceFunctions
	 *            the set of <code>PerformanceFunction</code> to be added.
	 */
	void addPerformanceFunctions(PerformanceFunction... performanceFunctions);

	/**
	 * Returns the <code>PerformanceFunction</code> by the given <code>UniqueIdentifier</code> that identifies the <code>PerformanceFunction</code>.
	 *
	 * @param performanceFunctionIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>PerformanceFunction</code> to retrieve.
	 * @return the <code>PerformanceFunction</code> if it exists, <code>null</code> otherwise.
	 */
	PerformanceFunction getPerformanceFunction(UniqueIdentifier performanceFunctionIdentifier);

	/**
	 * Returns the set of <code>PerformanceFunction</code>.
	 *
	 * @return the set of <code>PerformanceFunction</code>.
	 */
	Set<PerformanceFunction> getPerformanceFunctions();

	/**
	 * Removes the <code>PerformanceFunction</code> by the given <code>UniqueIdentifier</code> that identifies the <code>PerformanceFunction</code>.
	 *
	 * @param performanceFunctionIdentifier
	 *            the <code>UniqueIdentifier</code> that identifies the <code>PerformanceFunction</code> to be removed.
	 */
	void removePerformanceFunction(UniqueIdentifier performanceFunctionIdentifier);

	/**
	 * Clears the set of <code>PerformanceFunction</code>.
	 */
	void removeAllPerformanceFunctions();

	/**
	 * Adds the given <code>Characteristic</code> to the set of <code>Characteristic</code>.
	 *
	 * @param characteristic
	 *            the <code>Characteristic</code> to add.
	 */
	void addCharacteristic(Characteristic characteristic);

	/**
	 * Adds the given set of <code>Characteristic</code> to the set of <code>Characteristic</code>.
	 *
	 * @param characteristics
	 *            the set of <code>Characteristic</code> to add.
	 */
	void addCharacteristics(Collection<Characteristic> characteristics);

	/**
	 * Adds the given set of <code>Characteristic</code> to the set of <code>Characteristic</code>.
	 *
	 * @param characteristics
	 *            the set of <code>Characteristic</code> to add.
	 */
	void addCharacteristics(Characteristic... characteristics);

	/**
	 * Returns the <code>Characteristic</code> represented by the given <code>UniqueIdentifier</code>.
	 *
	 * @param characteristicIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Characteristic</code> to return.
	 * @return the <code>Characteristic</code> represented by the given <code>UniqueIdentifier</code>, <code>null</code> otherwise.
	 */
	Characteristic getCharacteristic(UniqueIdentifier characteristicIdentifier);

	/**
	 * Returns the set of <code>Characteristic</code>.
	 *
	 * @return the set of <code>Characteristic</code>.
	 */
	Set<Characteristic> getCharacteristics();

	/**
	 * Removes the <code>Characteristic</code> (from the given <code>UniqueIdentifier</code>) from the set of <code>Characteristic</code>.
	 *
	 * @param characteristicIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Characteristic</code> to remove.
	 */
	void removeCharacteristic(UniqueIdentifier characteristicIdentifier);

	/**
	 * Clears the set of <code>Characteristic</code>.
	 */
	void removeAllCharacteristic();

	/**
	 * Creates the achieves relation with the given <code>Role</code>, <code>SpecificationGoal</code>, and score.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Role</code> for the achieves relation.
	 * @param goalIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>SpecificationGoal</code> for the achieves relation.
	 */
	void specifyAchievesRelation(UniqueIdentifier roleIdentifier, UniqueIdentifier goalIdentifier);

	/**
	 * Removes the achieves relation with the given <code>Role</code> and <code>SpecificationGoal</code>.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Role</code> for the achieves relation.
	 * @param goalIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>SpecificationGoal</code> for the achieves relation.
	 */
	void removeAchievesRelation(UniqueIdentifier roleIdentifier, UniqueIdentifier goalIdentifier);

	/**
	 * Creates the requires relation with the given <code>Role</code> and <code>Capability</code>.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Role</code> for the requires relation.
	 * @param capabilityIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Capability</code> for the requires relation.
	 */
	void specifyRequiresRelation(UniqueIdentifier roleIdentifier, UniqueIdentifier capabilityIdentifier);

	/**
	 * Removes the requires relation with the given <code>Role</code> and <code>Capability</code>.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Role</code> for the requires relation.
	 * @param capabilityIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Capability</code> for the requires relation.
	 */
	void removeRequiresRelation(UniqueIdentifier roleIdentifier, UniqueIdentifier capabilityIdentifier);

	/**
	 * Creates the possesses relation with the given <code>Agent</code>, <code>Capability</code>, and score.
	 *
	 * @param agentIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Agent</code> for the possesses relation.
	 * @param capabilityIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Capability</code> for the possesses relation.
	 * @param score
	 *            the score of the possesses relation.
	 */
	void specifyPossessesRelation(UniqueIdentifier agentIdentifier, UniqueIdentifier capabilityIdentifier, double score);

	/**
	 * Removes the possesses relation with the given <code>Agent</code> and <code>Capability</code>.
	 *
	 * @param agentIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Agent</code> for the possesses relation.
	 * @param capabilityIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Capability</code> for the possesses relation.
	 */
	void removePossessesRelation(UniqueIdentifier agentIdentifier, UniqueIdentifier capabilityIdentifier);

	/**
	 * Updates the possesses relation with the given <code>Agent</code>, <code>Capability</code>, and score.
	 *
	 * @param agentIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Agent</code> for the possesses relation.
	 * @param capabilityIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Capability</code> for the possesses relation.
	 * @param score
	 *            the score of the possesses relation.
	 */
	void updatePossessesRelation(UniqueIdentifier agentIdentifier, UniqueIdentifier capabilityIdentifier, double score);

	/**
	 * Creates the <code>NeedsRelation</code> with the given <code>Role</code> and <code>Attribute</code>.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Role</code> for the <code>NeedsRelation</code>.
	 * @param attributeIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Attribute</code> for the <code>NeedsRelation</code> .
	 */
	void specifyNeedsRelation(UniqueIdentifier roleIdentifier, UniqueIdentifier attributeIdentifier);

	/**
	 * Removes the <code>NeedsRelation</code> with the given <code>Role</code> and <code>Attribute</code>.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Role</code> for the <code>NeedsRelation</code>.
	 * @param attributeIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Attribute</code> for the <code>NeedsRelation</code> .
	 */
	void removeNeedsRelation(UniqueIdentifier roleIdentifier, UniqueIdentifier attributeIdentifier);

	/**
	 * Creates the <code>HasRelation</code> with the given <code>Role</code>, <code>Attribute</code>, and a <code>double</code> representing the score.
	 *
	 * @param agentIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Agent</code> for the <code>HasRelation</code>.
	 * @param attributeIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Attribute</code> for the <code>HasRelation</code>.
	 * @param value
	 *            the <code>double</code> representing the value for the <code>HasRelation</code>.
	 */
	void specifyHasRelation(UniqueIdentifier agentIdentifier, UniqueIdentifier attributeIdentifier, double value);

	/**
	 * Removes the <code>HasRelation</code> with the given <code>Agent</code> and <code>Attribute</code>.
	 *
	 * @param agentIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Agent</code> for the <code>HasRelation</code>.
	 * @param attributeIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Attribute</code> for the <code>HasRelation</code>.
	 */
	void removeHasRelation(UniqueIdentifier agentIdentifier, UniqueIdentifier attributeIdentifier);

	/**
	 * Updates the <code>HasRelation</code> with the given <code>Agent</code>, <code>Attribute</code>, and <code>double</code> representing the score.
	 *
	 * @param agentIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Agent</code> for the <code>HasRelation</code>.
	 * @param attributeIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Attribute</code> for the <code>HasRelation</code>.
	 * @param score
	 *            the <code>double</code> representing the score for the <code>HasRelation</code>.
	 */
	void updateHasRelation(UniqueIdentifier agentIdentifier, UniqueIdentifier attributeIdentifier, double score);

	/**
	 * Creates the <code>LinkedRelation</code> with the given <code>Role</code> and <code>PerformanceFunction</code>.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Role</code> for the <code>LinkedRelation</code>.
	 * @param functionIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>PerformanceFunction</code> for the <code>LinkedRelation</code>.
	 */
	void specifyUsesRelation(UniqueIdentifier roleIdentifier, UniqueIdentifier functionIdentifier);

	/**
	 * Removes the <code>LinkedRelation</code> with the given <code>Role</code> and <code>PerformanceFunction</code>.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Role</code> for the <code>LinkedRelation</code>.
	 * @param functionIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>PerformanceFunction</code> for the <code>LinkedRelation</code>.
	 */
	void removeUsesRelation(UniqueIdentifier roleIdentifier, UniqueIdentifier functionIdentifier);

	/**
	 * Creates the <code>ModeratesRelation</code> with the given <code>PerformanceFunction</code> and <code>Attribute</code>.
	 *
	 * @param performanceFunctionIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>PerformanceFunction</code> for the <code>ModeratesRelation</code>.
	 * @param attributeIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Attribute</code> for the <code>ModeratesRelation</code>.
	 */
	void specifyModeratesRelation(UniqueIdentifier performanceFunctionIdentifier, UniqueIdentifier attributeIdentifier);

	/**
	 * Creates the <code>ContainsRelation</code> with the <code>Role</code> (from the given <code>UniqueIdentifier</code>), <code>Characteristic</code> (from
	 * the given <code>UniqueIdentifier</code> ), and <code>double</code> value.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Role</code> for the <code>ContainsRelation</code>.
	 * @param characteristicIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Characteristic</code> for the <code>ContainsRelation</code>.
	 * @param value
	 *            the <code>double</code> value for the <code>ContainsRelation</code>.
	 */
	void specifyContainsRelation(UniqueIdentifier roleIdentifier, UniqueIdentifier characteristicIdentifier, double value);

	/**
	 * Removes the <code>ContainsRelation</code> of the <code>Role</code> (from the given <code>UniqueIdentifier</code>) and <code>Characteristic</code> (from
	 * the given <code>UniqueIdentifier</code>).
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Role</code> for the <code>ContainsRelation</code>.
	 * @param characteristicIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Characteristic</code> for the <code>ContainsRelation</code>.
	 */
	void removeContainsRelation(UniqueIdentifier roleIdentifier, UniqueIdentifier characteristicIdentifier);

	/**
	 * Updates the <code>ContainsRelation</code> of the <code>Role</code> (from the given <code>UniqueIdentifier</code>) and <code>Characteristic</code> (from
	 * the given <code>UniqueIdentifier</code>) with the given new <code>double</code> value.
	 *
	 * @param roleIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Role</code> for the <code>ContainsRelation</code>.
	 * @param characteristicIdentifier
	 *            the <code>UniqueIdentifier</code> of the <code>Characteristic</code> for the <code>ContainsRelation</code>.
	 * @param value
	 *            the new <code>double</code> value for the <code>ContainsRelation</code>.
	 */
	void updateContainsRelation(UniqueIdentifier roleIdentifier, UniqueIdentifier characteristicIdentifier, double value);

}