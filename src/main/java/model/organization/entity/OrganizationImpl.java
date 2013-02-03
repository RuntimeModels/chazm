/*
 * Organization.java
 *
 * Created on Sep 22, 2004
 *
 * See License.txt file the license agreement.
 */
package model.organization.entity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.ParserConfigurationException;

import model.organization.identifier.UniqueIdentifier;
import model.organization.parser.SpecificationGoalProvider;
import model.organization.parser.UniqueIdentifierProvider;
import model.organization.parser.xml.XMLParser;
import model.organization.registry.ChangeManager;
import model.organization.registry.EventRegistry;
import model.organization.relation.Assignment;
import model.organization.relation.Task;

import org.xml.sax.SAXException;

/**
 * The <code>Organization</code> class implements the {@link Organization}
 * interface.
 * 
 * @author Scott Harmon
 * @author Christopher Zhong
 * @version $Revision: 1.13.2.11 $, $Date: 2011/09/19 14:26:36 $
 * @see Agent
 * @see Capability
 * @see InstanceGoal
 * @see Policy
 * @see Role
 * @see SpecificationGoal
 * @since 1.0
 */
public class OrganizationImpl implements Organization {

	/**
	 * The set of <code>SpecificationGoal</code> in this
	 * <code>Organization</code>.
	 */
	private final Map<UniqueIdentifier, SpecificationGoal> specificationGoals = new ConcurrentHashMap<>();

	/**
	 * The set of <code>Role</code> in this <code>Organization</code>.
	 */
	private final Map<UniqueIdentifier, Role> roles = new ConcurrentHashMap<>();

	/**
	 * The set of <code>Agent</code> in this <code>Organization</code>.
	 */
	private final Map<UniqueIdentifier, Agent<?>> agents = new ConcurrentHashMap<>();

	/**
	 * The set of <code>Capability</code> in this <code>Organization</code>.
	 */
	private final Map<UniqueIdentifier, Capability> capabilities = new ConcurrentHashMap<>();

	/**
	 * The set of <code>Policy</code> in this <code>Organization</code>.
	 */
	private final Map<UniqueIdentifier, Policy> policies = new ConcurrentHashMap<>();

	/**
	 * The set of <code>InstanceGoal</code> in this <code>Organization</code>.
	 */
	private final Map<UniqueIdentifier, InstanceGoal<?>> instanceGoals = new ConcurrentHashMap<>();

	/**
	 * The set of <code>InstanceGoal</code> in this <code>Organization</code>
	 * that is indexed by the <code>SpecificationGoal</code>.
	 */
	private final Map<UniqueIdentifier, Map<UniqueIdentifier, InstanceGoal<?>>> instanceGoalsBySpecificationGoal = new ConcurrentHashMap<>();

	/**
	 * The set of <code>Assignment</code> in this <code>Organization</code>.
	 */
	private final Map<UniqueIdentifier, Assignment> assignments = new ConcurrentHashMap<>();

	/**
	 * The set of <code>Assignment</code> in this <code>Organization</code> that
	 * is indexed by the <code>Agent</code>.
	 */
	private final Map<UniqueIdentifier, Map<UniqueIdentifier, Assignment>> assignmentsByAgent = new ConcurrentHashMap<>();

	/**
	 * The set of <code>Attribute</code> in this <code>Organization</code>.
	 */
	private final Map<UniqueIdentifier, Attribute> attributes = new ConcurrentHashMap<>();

	/**
	 * The set of <code>Task</code> in this <code>Organization</code>.
	 */
	private final Map<UniqueIdentifier, Task> tasks = new ConcurrentHashMap<>();

	/**
	 * The set of <code>PerformanceFunction</code> in this
	 * <code>Organization</code>.
	 */
	private final Map<UniqueIdentifier, PerformanceFunction> performanceFunctions = new ConcurrentHashMap<>();

	/**
	 * The set of <code>Characteristic</code> in this <code>Organization</code>.
	 */
	private final Map<UniqueIdentifier, Characteristic> characteristics = new ConcurrentHashMap<>();

	/**
	 * Constructs a new instance of <code>OrganizationImpl</code>.
	 */
	public OrganizationImpl() {
	}

	/**
	 * Constructs a new instance of <code>OrganizationImpl</code>.
	 * 
	 * @param file
	 *            the <code>File</code> from which to populate the
	 *            <code>Organization</code>.
	 * @param specificationGoalProvider
	 *            the <code>SpecificationGoalProvider</code> from which to
	 *            retrieve <code>SpecificationGoal</code>s.
	 * @param uniqueIdentifierProvider
	 *            the <code>UniqueIdentifierProvider</code> which provides
	 *            <code>UniqueIdentifier</code>.
	 */
	@Deprecated
	public OrganizationImpl(final File file,
			final SpecificationGoalProvider specificationGoalProvider,
			final UniqueIdentifierProvider uniqueIdentifierProvider) {
		this(file.toPath(), specificationGoalProvider, uniqueIdentifierProvider);
	}

	/**
	 * Constructs a new instance of <code>OrganizationImpl</code>.
	 * 
	 * @param path
	 *            the <code>Path</code> from which to populate the
	 *            <code>Organization</code>.
	 * @param specificationGoalProvider
	 *            the <code>SpecificationGoalProvider</code> from which to
	 *            retrieve <code>SpecificationGoal</code>s.
	 * @param uniqueIdentifierProvider
	 *            the <code>UniqueIdentifierProvider</code> which provides
	 *            <code>UniqueIdentifier</code>.
	 */
	public OrganizationImpl(final Path path,
			final SpecificationGoalProvider specificationGoalProvider,
			final UniqueIdentifierProvider uniqueIdentifierProvider) {
		populate(path, specificationGoalProvider, uniqueIdentifierProvider);
	}

	@Override
	@Deprecated
	public Organization populate(final File file,
			final SpecificationGoalProvider specificationGoalProvider,
			final UniqueIdentifierProvider uniqueIdentifierProvider) {
		return populate(file.toPath(), specificationGoalProvider,
				uniqueIdentifierProvider);
	}

	@Override
	public Organization populate(final Path path,
			final SpecificationGoalProvider specificationGoalProvider,
			final UniqueIdentifierProvider uniqueIdentifierProvider) {
		try {
			XMLParser.parse(path, specificationGoalProvider,
					uniqueIdentifierProvider, this);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		return this;
	}

	@Override
	public final void addSpecificationGoal(
			final SpecificationGoal specificationGoal) {
		if (specificationGoal == null) {
			throw new IllegalArgumentException(
					"Parameter (specificationGoal) cannot be null");
		}
		if (specificationGoals.containsKey(specificationGoal.getIdentifier())) {
			throw new IllegalArgumentException(
					String.format("Specification goal (%s) already exists",
							specificationGoal));
		}
		specificationGoals.put(specificationGoal.getIdentifier(),
				specificationGoal);
		instanceGoalsBySpecificationGoal.put(specificationGoal.getIdentifier(),
				new ConcurrentHashMap<UniqueIdentifier, InstanceGoal<?>>());

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifySpecificationGoalAdded(specificationGoal
					.getIdentifier());
		}
	}

	@Override
	public final void addSpecificationGoals(
			final Collection<SpecificationGoal> specificationGoals) {
		if (specificationGoals == null) {
			throw new IllegalArgumentException(String.format(
					"Specification goals (%s) cannot be null",
					specificationGoals));
		}
		for (final SpecificationGoal specificationGoal : specificationGoals) {
			addSpecificationGoal(specificationGoal);
		}
	}

	@Override
	public final void addSpecificationGoals(
			final SpecificationGoal... specificationGoals) {
		if (specificationGoals == null) {
			throw new IllegalArgumentException(String.format(
					"Specification goals (%s) cannot be null",
					(Object[]) specificationGoals));
		}
		for (final SpecificationGoal specificationGoal : specificationGoals) {
			addSpecificationGoal(specificationGoal);
		}
	}

	@Override
	public final SpecificationGoal getSpecificationGoal(
			final UniqueIdentifier goalIdentifier) {
		if (goalIdentifier == null) {
			throw new IllegalArgumentException(
					"Parameter (goalIdentifier) cannot be null");
		}
		return specificationGoals.get(goalIdentifier);
	}

	@Override
	public final Set<SpecificationGoal> getSpecificationGoals() {
		return new HashSet<>(specificationGoals.values());
	}

	@Override
	public final void removeSpecificationGoal(
			final UniqueIdentifier goalIdentifier) {
		if (goalIdentifier == null) {
			throw new IllegalArgumentException(
					"Parameter (goalIdentifier) cannot be null");
		}
		final SpecificationGoal specificationGoal = specificationGoals
				.remove(goalIdentifier);
		instanceGoalsBySpecificationGoal.remove(goalIdentifier);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifySpecificationGoalRemoved(specificationGoal
					.getIdentifier());
		}
	}

	@Override
	public final void removeSpecificationGoals(
			final Collection<UniqueIdentifier> goalIdentifiers) {
		if (goalIdentifiers == null) {
			throw new IllegalArgumentException(
					"Parameter (goalIdentifiers) cannot be null");
		}
		for (final UniqueIdentifier specificationGoalIdentifier : goalIdentifiers) {
			removeSpecificationGoal(specificationGoalIdentifier);
		}
	}

	@Override
	public final void removeSpecificationGoals(
			final UniqueIdentifier... goalIdentifiers) {
		if (goalIdentifiers == null) {
			throw new IllegalArgumentException(
					"Parameter (goalIdentifiers) cannot be null");
		}
		for (final UniqueIdentifier specificationGoalIdentifier : goalIdentifiers) {
			removeSpecificationGoal(specificationGoalIdentifier);
		}
	}

	@Override
	public final void removeAllSpecificationGoals() {
		specificationGoals.clear();
		instanceGoalsBySpecificationGoal.clear();
	}

	@Override
	public final void addRole(final Role role) {
		if (role == null) {
			throw new IllegalArgumentException(
					"Parameter (role) cannot be null");
		}
		if (roles.containsKey(role.getIdentifier())) {
			throw new IllegalArgumentException(String.format(
					"Role (%s) already exists", role));
		}
		roles.put(role.getIdentifier(), role);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyRoleAdded(role.getIdentifier());
		}
	}

	@Override
	public final void addRoles(final Collection<Role> roles) {
		if (roles == null) {
			throw new IllegalArgumentException(
					"Parameter (roles) cannot be null");
		}
		for (final Role role : roles) {
			addRole(role);
		}
	}

	@Override
	public final void addRoles(final Role... roles) {
		if (roles == null) {
			throw new IllegalArgumentException(
					"Parameter (roles) cannot be null");
		}
		for (final Role role : roles) {
			addRole(role);
		}
	}

	@Override
	public final Role getRole(final UniqueIdentifier roleIdentifier) {
		if (roleIdentifier == null) {
			throw new IllegalArgumentException(
					"Parameter (roleIdentifier) cannot be null");
		}
		return roles.get(roleIdentifier);
	}

	@Override
	public final Set<Role> getRoles() {
		return new HashSet<>(roles.values());
	}

	@Override
	public final void removeRole(final UniqueIdentifier roleIdentifier) {
		if (roleIdentifier == null) {
			throw new IllegalArgumentException(
					"Parameter (roleIdentifier) cannot be null");
		}
		final Role role = roles.remove(roleIdentifier);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyRoleRemoved(role.getIdentifier());
		}
	}

	@Override
	public final void removeRoles(
			final Collection<UniqueIdentifier> roleIdentifiers) {
		if (roleIdentifiers == null) {
			throw new IllegalArgumentException(
					"Parameter (roleIdentifiers) cannot be null");
		}
		for (final UniqueIdentifier roleIdentifier : roleIdentifiers) {
			removeRole(roleIdentifier);
		}
	}

	@Override
	public final void removeRoles(final UniqueIdentifier... roleIdentifiers) {
		if (roleIdentifiers == null) {
			throw new IllegalArgumentException(
					"Parameter (roleIdentifiers) cannot be null");
		}
		for (final UniqueIdentifier roleIdentifier : roleIdentifiers) {
			removeRole(roleIdentifier);
		}
	}

	@Override
	public final void removeAllRoles() {
		roles.clear();
	}

	@Override
	public final void addAgent(final Agent<?> agent) {
		if (agent == null) {
			throw new IllegalArgumentException(
					"Parameter (agent) cannot be null");
		}
		if (agents.containsKey(agent.getIdentifier())) {
			throw new IllegalArgumentException(String.format(
					"Agent (%s) already exists", agent));
		}
		agents.put(agent.getIdentifier(), agent);
		final Map<UniqueIdentifier, Assignment> map = new ConcurrentHashMap<>();
		assignmentsByAgent.put(agent.getIdentifier(), map);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyAgentAdded(agent.getIdentifier());
		}
	}

	@Override
	public final void addAgents(final Collection<Agent<?>> agents) {
		if (agents == null) {
			throw new IllegalArgumentException(
					"Parameter (agents) cannot be null");
		}
		for (final Agent<?> agent : agents) {
			addAgent(agent);
		}
	}

	@Override
	public final void addAgents(final Agent<?>... agents) {
		if (agents == null) {
			throw new IllegalArgumentException(
					"Parameter (agents) cannot be null");
		}
		for (final Agent<?> agent : agents) {
			addAgent(agent);
		}
	}

	@Override
	public final Agent<?> getAgent(final UniqueIdentifier agentIdentifier) {
		if (agentIdentifier == null) {
			throw new IllegalArgumentException(
					"Parameter (agentIdentifier) cannot be null");
		}
		return agents.get(agentIdentifier);
	}

	@Override
	public final Set<Agent<?>> getAgents() {
		return new HashSet<>(agents.values());
	}

	@Override
	public final void removeAgent(final UniqueIdentifier agentIdentifier) {
		if (agentIdentifier == null) {
			throw new IllegalArgumentException(
					"Parameter (agentIdentifier) cannot be null");
		}
		final Agent<?> agent = agents.remove(agentIdentifier);
		assignmentsByAgent.remove(agentIdentifier);

		if (agent != null) {
			final ChangeManager changeManager = EventRegistry.get();
			if (changeManager != null) {
				changeManager.notifyAgentRemoved(agent.getIdentifier());
			}
		}
	}

	@Override
	public final void removeAgents(
			final Collection<UniqueIdentifier> agentIdentifiers) {
		if (agentIdentifiers == null) {
			throw new IllegalArgumentException(
					"Parameter (agentIdentifiers) cannot be null");
		}
		for (final UniqueIdentifier agentIdentifier : agentIdentifiers) {
			removeAgent(agentIdentifier);
		}
	}

	@Override
	public final void removeAgents(final UniqueIdentifier... agentIdentifiers) {
		if (agentIdentifiers == null) {
			throw new IllegalArgumentException(
					"Parameter (agentIdentifiers) cannot be null");
		}
		for (final UniqueIdentifier agentIdentifier : agentIdentifiers) {
			removeAgent(agentIdentifier);
		}
	}

	@Override
	public final void removeAllAgents() {
		agents.clear();
		assignmentsByAgent.clear();
	}

	@Override
	public final void addCapability(final Capability capability) {
		if (capability == null) {
			throw new IllegalArgumentException(
					"Parameter (capability) cannot be null");
		}
		if (capabilities.containsKey(capability.getIdentifier())) {
			throw new IllegalArgumentException(String.format(
					"Capability (%s) already exists", capability));
		}
		capabilities.put(capability.getIdentifier(), capability);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyCapabilityAdded(capability.getIdentifier());
		}
	}

	@Override
	public final void addCapabilities(final Collection<Capability> capabilities) {
		if (capabilities == null) {
			throw new IllegalArgumentException(
					"Parameter (capabilities) cannot be null");
		}
		for (final Capability capability : capabilities) {
			addCapability(capability);
		}
	}

	@Override
	public final void addCapabilities(final Capability... capabilities) {
		if (capabilities == null) {
			throw new IllegalArgumentException(
					"Parameter (capabilities) cannot be null");
		}
		for (final Capability capability : capabilities) {
			addCapability(capability);
		}
	}

	@Override
	public final Capability getCapability(
			final UniqueIdentifier capabilityIdentifier) {
		if (capabilityIdentifier == null) {
			throw new IllegalArgumentException(
					"Parameter (capabilityIdentifier) cannot be null");
		}
		return capabilities.get(capabilityIdentifier);
	}

	@Override
	public final Set<Capability> getCapabilities() {
		return new HashSet<>(capabilities.values());
	}

	@Override
	public final void removeCapability(
			final UniqueIdentifier capabilityIdentifier) {
		if (capabilityIdentifier == null) {
			throw new IllegalArgumentException(
					"Parameter (capabilityIdentifier) cannot be null");
		}
		final Capability capability = capabilities.remove(capabilityIdentifier);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyCapabilityRemoved(capability.getIdentifier());
		}
	}

	@Override
	public final void removeCapabilities(
			final Collection<UniqueIdentifier> capabilityIdentifiers) {
		if (capabilityIdentifiers == null) {
			throw new IllegalArgumentException(
					"Parameter (capabilityIdentifiers) cannot be null");
		}
		for (final UniqueIdentifier capabilityIdentifier : capabilityIdentifiers) {
			removeCapability(capabilityIdentifier);
		}
	}

	@Override
	public final void removeCapabilities(
			final UniqueIdentifier... capabilityIdentifiers) {
		if (capabilityIdentifiers == null) {
			throw new IllegalArgumentException(
					"Parameter (capabilityIdentifiers) cannot be null");
		}
		for (final UniqueIdentifier capabilityIdentifier : capabilityIdentifiers) {
			removeCapability(capabilityIdentifier);
		}
	}

	@Override
	public final void removeAllCapabilities() {
		capabilities.clear();
	}

	@Override
	public final void addPolicy(final Policy policy) {
		if (policy == null) {
			throw new IllegalArgumentException(
					"Parameter (policy) cannot be null");
		}
		if (policies.containsKey(policy.getIdentifier())) {
			throw new IllegalArgumentException(String.format(
					"Policy (%s) already exists", policy));
		}
		policies.put(policy.getIdentifier(), policy);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyPolicyAdded(policy.getIdentifier());
		}
	}

	@Override
	public final void addPolicies(final Collection<Policy> policies) {
		if (policies == null) {
			throw new IllegalArgumentException(
					"Parameter (policies) cannot be null");
		}
		for (final Policy policy : policies) {
			addPolicy(policy);
		}
	}

	@Override
	public final void addPolicies(final Policy... policies) {
		if (policies == null) {
			throw new IllegalArgumentException(
					"Parameter (policies) cannot be null");
		}
		for (final Policy policy : policies) {
			addPolicy(policy);
		}
	}

	@Override
	public final Policy getPolicy(final UniqueIdentifier policyIdentifier) {
		if (policyIdentifier == null) {
			throw new IllegalArgumentException(
					"Parameter (policyIdentifier) cannot be null");
		}
		return policies.get(policyIdentifier);
	}

	@Override
	public final Set<Policy> getPolicies() {
		return new HashSet<>(policies.values());
	}

	@Override
	public final void removePolicy(final UniqueIdentifier policyIdentifier) {
		if (policyIdentifier == null) {
			throw new IllegalArgumentException(
					"Parameter (policyIdentifier) cannot be null");
		}
		final Policy policy = policies.remove(policyIdentifier);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyPolicyRemoved(policy.getIdentifier());
		}
	}

	@Override
	public final void removePolicies(
			final Collection<UniqueIdentifier> policyIdentifiers) {
		if (policyIdentifiers == null) {
			throw new IllegalArgumentException(
					"Parameter (policyIdentifiers) cannot be null");
		}
		for (final UniqueIdentifier policyIdentifer : policyIdentifiers) {
			removePolicy(policyIdentifer);
		}
	}

	@Override
	public final void removePolicies(
			final UniqueIdentifier... policyIdentifiers) {
		if (policyIdentifiers == null) {
			throw new IllegalArgumentException(
					"Parameter (policyIdentifiers) cannot be null");
		}
		for (final UniqueIdentifier policyIdentifer : policyIdentifiers) {
			removePolicy(policyIdentifer);
		}
	}

	@Override
	public final void removeAllPolicies() {
		policies.clear();
	}

	@Override
	public final void addInstanceGoal(final InstanceGoal<?> instanceGoal) {
		if (instanceGoal == null) {
			throw new IllegalArgumentException(
					"Parameter (instanceGoal) cannot be null");
		}
		if (instanceGoals.containsKey(instanceGoal.getIdentifier())) {
			throw new IllegalArgumentException(String.format(
					"Instance goal (%s) already exists",
					instanceGoal.getIdentifier()));
		}
		instanceGoals.put(instanceGoal.getIdentifier(), instanceGoal);
		instanceGoalsBySpecificationGoal.get(
				instanceGoal.getSpecificationIdentifier()).put(
				instanceGoal.getInstanceIdentifier(), instanceGoal);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyInstanceGoalAdded(instanceGoal.getIdentifier(),
					instanceGoal.getParameter());
		}
	}

	@Override
	public final void addInstanceGoals(
			final Collection<InstanceGoal<?>> instanceGoals) {
		if (instanceGoals == null) {
			throw new IllegalArgumentException(
					"Parameter (instanceGoals) cannot be null");
		}
		for (final InstanceGoal<?> instanceGoal : instanceGoals) {
			addInstanceGoal(instanceGoal);
		}
	}

	@Override
	public final void addInstanceGoals(final InstanceGoal<?>... instanceGoals) {
		if (instanceGoals == null) {
			throw new IllegalArgumentException(
					"Parameter (instanceGoals) cannot be null");
		}
		for (final InstanceGoal<?> instanceGoal : instanceGoals) {
			addInstanceGoal(instanceGoal);
		}
	}

	@Override
	public final InstanceGoal<?> getInstanceGoal(
			final UniqueIdentifier goalIdentifier) {
		if (goalIdentifier == null) {
			throw new IllegalArgumentException(
					"Parameter (goalIdentifier) cannot be null");
		}
		return instanceGoals.get(goalIdentifier);
	}

	@Override
	public final Set<InstanceGoal<?>> getInstanceGoals() {
		return new HashSet<>(instanceGoals.values());
	}

	@Override
	public final void removeInstanceGoal(final UniqueIdentifier goalIdentifier) {
		if (goalIdentifier == null) {
			throw new IllegalArgumentException(
					"Parameter (goalIdentifier) cannot be null");
		}
		final InstanceGoal<?> instanceGoal = instanceGoals
				.remove(goalIdentifier);
		instanceGoalsBySpecificationGoal.get(
				instanceGoal.getSpecificationIdentifier()).remove(
				instanceGoal.getInstanceIdentifier());

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyInstanceGoalRemoved(instanceGoal
					.getIdentifier());
		}
	}

	@Override
	public final void removeInstanceGoals(
			final Collection<UniqueIdentifier> goalIdentifiers) {
		if (goalIdentifiers == null) {
			throw new IllegalArgumentException(
					"Parameter (goalIdentifiers) cannot be null");
		}
		for (final UniqueIdentifier instanceGoalIdentifier : goalIdentifiers) {
			removeInstanceGoal(instanceGoalIdentifier);
		}
	}

	@Override
	public final void removeInstanceGoals(
			final UniqueIdentifier... goalIdentifiers) {
		if (goalIdentifiers == null) {
			throw new IllegalArgumentException(
					"Parameter (goalIdentifiers) cannot be null");
		}
		for (final UniqueIdentifier instanceGoalIdentifier : goalIdentifiers) {
			removeInstanceGoal(instanceGoalIdentifier);
		}
	}

	@Override
	public final void removeAllInstanceGoals() {
		instanceGoals.clear();
		for (final Map<UniqueIdentifier, InstanceGoal<?>> map : instanceGoalsBySpecificationGoal
				.values()) {
			map.clear();
		}
	}

	@Override
	public final void addAssignment(final Assignment assignment) {
		if (assignment == null) {
			throw new IllegalArgumentException(
					"Parameter (assignment) cannot be null");
		}
		if (assignments.containsKey(assignment.getIdentifier())) {
			throw new IllegalArgumentException(String.format(
					"Assignment (%s) already exists", assignment));
		}
		assignments.put(assignment.getIdentifier(), assignment);
		assignmentsByAgent.get(assignment.getAgent().getIdentifier()).put(
				assignment.getIdentifier(), assignment);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyAssignmentAdded(assignment.getAgent()
					.getIdentifier(), assignment.getRole().getIdentifier(),
					assignment.getInstanceGoal().getIdentifier());
		}
	}

	@Override
	public final void addAssignments(final Collection<Assignment> assignments) {
		if (assignments == null) {
			throw new IllegalArgumentException(
					"Parameter (assignments) cannot be null");
		}
		for (final Assignment assignment : assignments) {
			addAssignment(assignment);
		}
	}

	@Override
	public final void addAssignments(final Assignment... assignments) {
		if (assignments == null) {
			throw new IllegalArgumentException(
					"Parameter (assignments) cannot be null");
		}
		for (final Assignment assignment : assignments) {
			addAssignment(assignment);
		}
	}

	@Override
	public final Assignment getAssignment(
			final UniqueIdentifier assignmentIdentifier) {
		if (assignmentIdentifier == null) {
			throw new IllegalArgumentException(
					"Parameter (assignmentIdentifier) cannot be null");
		}
		return assignments.get(assignmentIdentifier);
	}

	@Override
	public final Set<Assignment> getAssignments() {
		return new HashSet<>(assignments.values());
	}

	@Override
	public final Set<Assignment> getAssignmentsOfAgent(
			final UniqueIdentifier agentIdentifier) {
		if (agentIdentifier == null) {
			throw new IllegalArgumentException(
					"Parameter (agentIdentifier) cannot be null");
		}
		final Set<Assignment> results = new HashSet<>();
		final Map<UniqueIdentifier, Assignment> map = assignmentsByAgent
				.get(agentIdentifier);
		if (map != null) {
			results.addAll(map.values());
		}
		return results;
	}

	@Override
	public final void removeAssignment(
			final UniqueIdentifier assignmentIdentifier) {
		if (assignmentIdentifier == null) {
			throw new IllegalArgumentException(
					"Parameter (assignmentIdentifier) cannot be null");
		}
		final Assignment assignment = assignments.remove(assignmentIdentifier);
		assignmentsByAgent.get(assignment.getAgent().getIdentifier()).remove(
				assignmentIdentifier);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyAssignmentRemoved(assignment.getAgent()
					.getIdentifier(), assignment.getRole().getIdentifier(),
					assignment.getInstanceGoal().getIdentifier());
		}
	}

	@Override
	public final void removeAssignments(
			final Collection<UniqueIdentifier> assignmentIdentifiers) {
		if (assignmentIdentifiers == null) {
			throw new IllegalArgumentException(
					"Parameter (assignmentIdentifiers) cannot be null");
		}
		for (final UniqueIdentifier assignmentIdentifier : assignmentIdentifiers) {
			removeAssignment(assignmentIdentifier);
		}
	}

	@Override
	public final void removeAssignments(
			final UniqueIdentifier... assignmentIdentifiers) {
		if (assignmentIdentifiers == null) {
			throw new IllegalArgumentException(
					"Parameter (assignmentIdentifiers) cannot be null");
		}
		for (final UniqueIdentifier assignmentIdentifier : assignmentIdentifiers) {
			removeAssignment(assignmentIdentifier);
		}
	}

	@Override
	public final void removeAllAssignments() {
		assignments.clear();
		for (final Map<UniqueIdentifier, Assignment> map : assignmentsByAgent
				.values()) {
			map.clear();
		}
	}

	@Override
	public final void addAttribute(final Attribute attribute) {
		if (attribute == null) {
			throw new IllegalArgumentException(
					"Parameter (attribute) cannot be null");
		}
		if (attributes.containsKey(attribute.getIdentifier())) {
			throw new IllegalArgumentException(String.format(
					"Attribute (%s) already exists", attribute));
		}
		attributes.put(attribute.getIdentifier(), attribute);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyAttributeAdded(attribute.getIdentifier());
		}
	}

	@Override
	public final void addAttributes(final Collection<Attribute> attributes) {
		if (attributes == null) {
			throw new IllegalArgumentException(
					"Parameter (attributes) cannot be null");
		}
		for (final Attribute attribute : attributes) {
			addAttribute(attribute);
		}
	}

	@Override
	public final void addAttributes(final Attribute... attributes) {
		if (attributes == null) {
			throw new IllegalArgumentException(
					"Parameter (attributes) cannot be null");
		}
		for (final Attribute attribute : attributes) {
			addAttribute(attribute);
		}
	}

	@Override
	public final Attribute getAttribute(
			final UniqueIdentifier attributeIdentifier) {
		if (attributeIdentifier == null) {
			throw new IllegalArgumentException(
					"Parameter (attributeIdentifier) cannot be null");
		}
		return attributes.get(attributeIdentifier);
	}

	@Override
	public final Set<Attribute> getAttributes() {
		return new HashSet<>(attributes.values());
	}

	@Override
	public final void removeAttribute(final UniqueIdentifier attributeIdentifier) {
		if (attributeIdentifier == null) {
			throw new IllegalArgumentException(
					"Parameter (attributeIdentifier) cannot be null");
		}
		final Attribute attribute = attributes.remove(attributeIdentifier);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyAttributeRemoved(attribute.getIdentifier());
		}
	}

	@Override
	public final void removeAttributes(
			final Collection<UniqueIdentifier> attributeIdentifiers) {
		if (attributeIdentifiers == null) {
			throw new IllegalArgumentException(
					"Parameter (attributeIdentifiers) cannot be null");
		}
		for (final UniqueIdentifier attributeIdentifier : attributeIdentifiers) {
			removeAttribute(attributeIdentifier);
		}
	}

	@Override
	public final void removeAttributes(
			final UniqueIdentifier... attributeIdentifiers) {
		if (attributeIdentifiers == null) {
			throw new IllegalArgumentException(
					"Parameter (attributeIdentifiers) cannot be null");
		}
		for (final UniqueIdentifier attributeIdentifier : attributeIdentifiers) {
			removeAttribute(attributeIdentifier);
		}
	}

	@Override
	public final void removeAllAttributes() {
		attributes.clear();
	}

	@Override
	public final void addTask(final Task task) {
		if (task == null) {
			throw new IllegalArgumentException(
					"Parameter (task) cannot be null");
		}
		if (tasks.containsKey(task.getIdentifier())) {
			throw new IllegalArgumentException(String.format(
					"Task (%s) already exists", task));
		}
		tasks.put(task.getIdentifier(), task);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyTaskAdded(task.getIdentifier());
		}
	}

	@Override
	public final void addTasks(final Collection<Task> tasks) {
		if (tasks == null) {
			throw new IllegalArgumentException(
					"Parameter (tasks) cannot be null");
		}
		for (final Task task : tasks) {
			addTask(task);
		}
	}

	@Override
	public final void addTasks(final Task... tasks) {
		if (tasks == null) {
			throw new IllegalArgumentException(
					"Parameter (tasks) cannot be null");
		}
		for (final Task task : tasks) {
			addTask(task);
		}
	}

	@Override
	public Task getTask(final UniqueIdentifier taskIdentifier) {
		if (taskIdentifier == null) {
			throw new IllegalArgumentException(
					"Parameter (taskIdentifier) cannot be null");
		}
		return tasks.get(taskIdentifier);
	}

	@Override
	public final Set<Task> getTasks() {
		return new HashSet<>(tasks.values());
	}

	@Override
	public final void removeTask(final UniqueIdentifier taskIdentifier) {
		if (taskIdentifier == null) {
			throw new IllegalArgumentException(
					"Parameter (taskIdentifier) cannot be null");
		}
		final Task task = tasks.remove(taskIdentifier);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyTaskRemoved(task.getIdentifier());
		}
	}

	@Override
	public final void removeAllTasks() {
		tasks.clear();
	}

	@Override
	public final void addPerformanceFunction(
			final PerformanceFunction performanceFunction) {
		if (performanceFunction == null) {
			throw new IllegalArgumentException(
					"Parameter (performanceFunction) cannot be null");
		}
		if (performanceFunctions.containsKey(performanceFunction
				.getIdentifier())) {
			throw new IllegalArgumentException(String.format(
					"Performance function (%s) already exists",
					performanceFunction));
		}
		performanceFunctions.put(performanceFunction.getIdentifier(),
				performanceFunction);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyPerformanceFunctionAdded(performanceFunction
					.getIdentifier());
		}
	}

	@Override
	public final void addPerformanceFunctions(
			final Collection<PerformanceFunction> performanceFunctions) {
		if (performanceFunctions == null) {
			throw new IllegalArgumentException(
					"Parameter (performanceFunctions) cannot be null");
		}
		for (final PerformanceFunction performanceFunction : performanceFunctions) {
			addPerformanceFunction(performanceFunction);
		}
	}

	@Override
	public final void addPerformanceFunctions(
			final PerformanceFunction... performanceFunctions) {
		if (performanceFunctions == null) {
			throw new IllegalArgumentException(
					"Parameter (performanceFunctions) cannot be null");
		}
		for (final PerformanceFunction performanceFunction : performanceFunctions) {
			addPerformanceFunction(performanceFunction);
		}
	}

	@Override
	public PerformanceFunction getPerformanceFunction(
			final UniqueIdentifier performanceFunctionIdentifier) {
		if (performanceFunctionIdentifier == null) {
			throw new IllegalArgumentException(
					"Parameter (performanceFunctionIdentifier) cannot be null");
		}
		return performanceFunctions.get(performanceFunctionIdentifier);
	}

	@Override
	public final Set<PerformanceFunction> getPerformanceFunctions() {
		return new HashSet<>(performanceFunctions.values());
	}

	@Override
	public final void removePerformanceFunction(
			final UniqueIdentifier performanceFunctionIdentifier) {
		if (performanceFunctionIdentifier == null) {
			throw new IllegalArgumentException(
					"Parameter (performanceFunctionIdentifier) cannot be null");
		}
		final PerformanceFunction performanceFunction = performanceFunctions
				.remove(performanceFunctionIdentifier);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyPerformanceFunctionRemoved(performanceFunction
					.getIdentifier());
		}
	}

	@Override
	public final void removeAllPerformanceFunctions() {
		performanceFunctions.clear();
	}

	@Override
	public final void addCharacteristic(final Characteristic characteristic) {
		if (characteristic == null) {
			throw new IllegalArgumentException(
					"Parameter (characteristic) cannot be null");
		}
		if (characteristics.containsKey(characteristic.getIdentifier())) {
			throw new IllegalArgumentException(String.format(
					"Characteristic (%s) already exists", characteristic));
		}
		characteristics.put(characteristic.getIdentifier(), characteristic);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyCharacteristicAdded(characteristic
					.getIdentifier());
		}
	}

	@Override
	public final void addCharacteristics(
			final Collection<Characteristic> characteristics) {
		if (characteristics == null) {
			throw new IllegalArgumentException(
					"Parameter (characteristics) cannot be null");
		}
		for (final Characteristic characteristic : characteristics) {
			addCharacteristic(characteristic);
		}
	}

	@Override
	public final void addCharacteristics(
			final Characteristic... characteristics) {
		if (characteristics == null) {
			throw new IllegalArgumentException(
					"Parameter (characteristics) cannot be null");
		}
		for (final Characteristic characteristic : characteristics) {
			addCharacteristic(characteristic);
		}
	}

	@Override
	public final Characteristic getCharacteristic(
			final UniqueIdentifier characteristicIdentifier) {
		if (characteristicIdentifier == null) {
			throw new IllegalArgumentException(
					"Parameter (characteristicIdentifier) cannot be null");
		}
		return characteristics.get(characteristicIdentifier);
	}

	@Override
	public final Set<Characteristic> getCharacteristics() {
		return new HashSet<>(characteristics.values());
	}

	@Override
	public final void removeCharacteristic(
			final UniqueIdentifier characteristicIdentifier) {
		if (characteristicIdentifier == null) {
			throw new IllegalArgumentException(
					"Parameter (characteristicIdentifier) cannot be null");
		}
		if (characteristics.containsKey(characteristicIdentifier)) {
			final Characteristic characteristic = characteristics
					.remove(characteristicIdentifier);

			final ChangeManager changeManager = EventRegistry.get();
			if (changeManager != null) {
				changeManager.notifyCharacteristicRemoved(characteristic
						.getIdentifier());
			}
		}
	}

	@Override
	public final void removeAllCharacteristic() {
		characteristics.clear();
	}

	@Override
	public final void specifyAchievesRelation(
			final UniqueIdentifier roleIdentifier,
			final UniqueIdentifier goalIdentifier) {
		final Role role = getRole(roleIdentifier);
		final SpecificationGoal goal = getSpecificationGoal(goalIdentifier);
		if (role == null || goal == null) {
			throw new IllegalArgumentException(String.format(
					"Both role (%s=%s) and goal (%s=%s) must exists",
					roleIdentifier, role, goalIdentifier, goal));
		}
		role.addAchieves(goal);
	}

	@Override
	public final void removeAchievesRelation(
			final UniqueIdentifier roleIdentifier,
			final UniqueIdentifier goalIdentifier) {
		final Role role = getRole(roleIdentifier);
		final SpecificationGoal goal = getSpecificationGoal(goalIdentifier);
		if (role == null || goal == null) {
			throw new IllegalArgumentException(String.format(
					"Both role (%s=%s) and goal (%s=%s) must exists",
					roleIdentifier, role, goalIdentifier, goal));
		}
		role.removeAchieves(goal.getIdentifier());
	}

	@Override
	public final void specifyRequiresRelation(
			final UniqueIdentifier roleIdentifier,
			final UniqueIdentifier capabilityIdentifier) {
		final Role role = getRole(roleIdentifier);
		final Capability capability = getCapability(capabilityIdentifier);
		if (role == null || capability == null) {
			throw new IllegalArgumentException(String.format(
					"Both role (%s=%s) and capability (%s=%s) must exists",
					roleIdentifier, role, capabilityIdentifier, capability));
		}
		role.addRequires(capability);
	}

	@Override
	public final void removeRequiresRelation(
			final UniqueIdentifier roleIdentifier,
			final UniqueIdentifier capabilityIdentifier) {
		final Role role = getRole(roleIdentifier);
		final Capability capability = getCapability(capabilityIdentifier);
		if (role == null || capability == null) {
			throw new IllegalArgumentException(String.format(
					"Both role (%s=%s) and capability (%s=%s) must exists",
					roleIdentifier, role, capabilityIdentifier, capability));
		}
		role.removeRequires(capability.getIdentifier());
	}

	@Override
	public final void specifyPossessesRelation(
			final UniqueIdentifier agentIdentifier,
			final UniqueIdentifier capabilityIdentifier, final double score) {
		final Agent<?> agent = getAgent(agentIdentifier);
		final Capability capability = getCapability(capabilityIdentifier);
		if (agent == null || capability == null) {
			throw new IllegalArgumentException(String.format(
					"Both agent (%s=%s) and capability (%s=%s) must exists",
					agentIdentifier, agent, capabilityIdentifier, capability));
		}
		agent.addPossesses(capability, score);
	}

	@Override
	public final void removePossessesRelation(
			final UniqueIdentifier agentIdentifier,
			final UniqueIdentifier capabilityIdentifier) {
		final Agent<?> agent = getAgent(agentIdentifier);
		final Capability capability = getCapability(capabilityIdentifier);
		if (agent == null || capability == null) {
			throw new IllegalArgumentException(String.format(
					"Both agent (%s=%s) and capability (%s=%s) must exists",
					agentIdentifier, agent, capabilityIdentifier, capability));
		}
		agent.removePossesses(capabilityIdentifier);
	}

	@Override
	public final void updatePossessesRelation(
			final UniqueIdentifier agentIdentifier,
			final UniqueIdentifier capabilityIdentifier, final double score) {
		final Agent<?> agent = getAgent(agentIdentifier);
		final Capability capability = getCapability(capabilityIdentifier);
		if (agent == null || capability == null) {
			throw new IllegalArgumentException(String.format(
					"Both agent (%s=%s) and capability (%s=%s) must exists",
					agentIdentifier, agent, capabilityIdentifier, capability));
		}
		agent.setPossessesScore(capabilityIdentifier, score);
	}

	@Override
	public final void specifyNeedsRelation(
			final UniqueIdentifier roleIdentifier,
			final UniqueIdentifier attributeIdentifier) {
		final Role role = getRole(roleIdentifier);
		final Attribute attribute = getAttribute(attributeIdentifier);
		if (role == null || attribute == null) {
			throw new IllegalArgumentException(String.format(
					"Both role (%s=%s) and attribute (%s=%s) must exists",
					roleIdentifier, role, attributeIdentifier, attribute));
		}
		role.addNeeds(attribute);
	}

	@Override
	public final void removeNeedsRelation(
			final UniqueIdentifier roleIdentifier,
			final UniqueIdentifier attributeIdentifier) {
		final Role role = getRole(roleIdentifier);
		final Attribute attribute = getAttribute(attributeIdentifier);
		if (role == null || attribute == null) {
			throw new IllegalArgumentException(String.format(
					"Both role (%s=%s) and attribute (%s=%s) must exists",
					roleIdentifier, role, attributeIdentifier, attribute));
		}
		role.removeNeeds(attributeIdentifier);
	}

	@Override
	public final void specifyHasRelation(
			final UniqueIdentifier agentIdentifier,
			final UniqueIdentifier attributeIdentifier, final double value) {
		final Agent<?> agent = getAgent(agentIdentifier);
		final Attribute attribute = getAttribute(attributeIdentifier);
		if (agent == null || attribute == null) {
			throw new IllegalArgumentException(String.format(
					"Both agent (%s=%s) and attribute (%s=%s) must exists",
					agentIdentifier, agent, attributeIdentifier, attribute));
		}
		agent.addHas(attribute, value);
	}

	@Override
	public final void removeHasRelation(final UniqueIdentifier agentIdentifier,
			final UniqueIdentifier attributeIdentifier) {
		final Agent<?> agent = getAgent(agentIdentifier);
		final Attribute attribute = getAttribute(attributeIdentifier);
		if (agent == null || attribute == null) {
			throw new IllegalArgumentException(String.format(
					"Both agent (%s=%s) and attribute (%s=%s) must exists",
					agentIdentifier, agent, attributeIdentifier, attribute));
		}
		agent.removeHas(attributeIdentifier);
	}

	@Override
	public final void updateHasRelation(final UniqueIdentifier agentIdentifier,
			final UniqueIdentifier attributeIdentifier, final double score) {
		final Agent<?> agent = getAgent(agentIdentifier);
		final Attribute attribute = getAttribute(attributeIdentifier);
		if (agent == null || attribute == null) {
			throw new IllegalArgumentException(String.format(
					"Both agent (%s=%s) and attribute (%s=%s) must exists",
					agentIdentifier, agent, attributeIdentifier, attribute));
		}
		agent.setHasValue(attributeIdentifier, score);
	}

	@Override
	public final void specifyUsesRelation(
			final UniqueIdentifier roleIdentifier,
			final UniqueIdentifier functionIdentifier) {
		final Role role = getRole(roleIdentifier);
		final PerformanceFunction performanceFunction = getPerformanceFunction(functionIdentifier);
		if (role == null || performanceFunction == null) {
			throw new IllegalArgumentException(
					String.format(
							"Both role (%s=%s) and performance function (%s=%s) must exists",
							roleIdentifier, role, functionIdentifier,
							performanceFunction));
		}
		role.addUses(performanceFunction);
	}

	@Override
	public final void removeUsesRelation(final UniqueIdentifier roleIdentifier,
			final UniqueIdentifier functionIdentifier) {
		final Role role = getRole(roleIdentifier);
		final PerformanceFunction performanceFunction = getPerformanceFunction(functionIdentifier);
		if (role == null || performanceFunction == null) {
			throw new IllegalArgumentException(
					String.format(
							"Both role (%s=%s) and performance function (%s=%s) must exists",
							roleIdentifier, role, functionIdentifier,
							performanceFunction));
		}
		role.removeUses(functionIdentifier);
	}

	@Override
	public final void specifyModeratesRelation(
			final UniqueIdentifier performanceFunctionIdentifier,
			final UniqueIdentifier attributeIdentifier) {
		final PerformanceFunction performanceFunction = getPerformanceFunction(performanceFunctionIdentifier);
		final Attribute attribute = getAttribute(attributeIdentifier);
		if (performanceFunction == null || attribute == null) {
			throw new IllegalArgumentException(
					String.format(
							"Both performance function (%s=%s) and attribute (%s=%s) must exists",
							performanceFunctionIdentifier, performanceFunction,
							attributeIdentifier, attribute));
		}
		performanceFunction.setModerates(attribute);
	}

	@Override
	public final void specifyContainsRelation(
			final UniqueIdentifier roleIdentifier,
			final UniqueIdentifier characteristicIdentifier, final double value) {
		final Role role = getRole(roleIdentifier);
		final Characteristic characteristic = getCharacteristic(characteristicIdentifier);
		if (role == null || characteristic == null) {
			throw new IllegalArgumentException(String.format(
					"Both role (%s=%s) and characteristic (%s=%s) must exists",
					roleIdentifier, role, characteristicIdentifier,
					characteristic));
		}
		role.addContains(characteristic, value);
	}

	@Override
	public final void removeContainsRelation(
			final UniqueIdentifier roleIdentifier,
			final UniqueIdentifier characteristicIdentifier) {
		final Role role = getRole(roleIdentifier);
		final Characteristic characteristic = getCharacteristic(characteristicIdentifier);
		if (role == null || characteristic == null) {
			throw new IllegalArgumentException(String.format(
					"Both role (%s=%s) and characteristic (%s=%s) must exists",
					roleIdentifier, role, characteristicIdentifier,
					characteristic));
		}
		role.removeContains(characteristicIdentifier);
	}

	@Override
	public final void updateContainsRelation(
			final UniqueIdentifier roleIdentifier,
			final UniqueIdentifier characteristicIdentifier, final double value) {
		final Role role = getRole(roleIdentifier);
		final Characteristic characteristic = getCharacteristic(characteristicIdentifier);
		if (role == null || characteristic == null) {
			throw new IllegalArgumentException(String.format(
					"Both role (%s=%s) and characteristic (%s=%s) must exists",
					roleIdentifier, role, characteristicIdentifier,
					characteristic));
		}
		role.setContainsValue(characteristicIdentifier, value);
	}

	/**
	 * Determines whether this <code>Organization</code> is valid.
	 * <p>
	 * Validity checks include checks for the structural integrity of the
	 * current organization.
	 * 
	 * @param organization
	 *            the <code>Organization</code> to check.
	 * @return <code>true</code> if the <code>Organization</code> is valid,
	 *         <code>false</code> otherwise.
	 */
	public static final boolean isOrganizationValid(
			final Organization organization) {
		boolean result = true;
		/*
		 * every goal can be achieved by at least one role from the organization
		 */
		for (final SpecificationGoal goal : organization
				.getSpecificationGoals()) {
			boolean isAchievable = false;
			for (final Role role : goal.getAchievedBySet()) {
				isAchievable |= organization.getRole(role.getIdentifier()) != null;
				if (isAchievable) { /* short circuit */
					/*
					 * can stop checking because there is at least one role that
					 * can achieve the goal
					 */
					break;
				}
			}
			result &= isAchievable;
			if (!result) { /* short circuit */
				/*
				 * can stop checking because there is at least one goal that
				 * cannot be achieved by any role in the organization
				 */
				break;
			}
		}
		/*
		 * the set of capabilities is the union of all capabilities required by
		 * roles or possessed by agents in the organization
		 */
		if (result) { /* short circult */
			/*
			 * there is no reason to continue checking if the previous results
			 * are false
			 */
			for (final Role role : organization.getRoles()) {
				/*
				 * every role requires at least one capability
				 */
				result &= role.getRequiresSet().size() > 0;
				if (!result) { /* short circuit */
					/*
					 * can stop checking because there is a role that does not
					 * require at least one capability
					 */
					break;
				}
				for (final Capability capability : role.getRequiresSet()) {
					result &= organization.getCapability(capability
							.getIdentifier()) != null;
					if (!result) { /* short circuit */
						/*
						 * can stop checking because there is at least one
						 * capability required by a role that is not in the
						 * organization
						 */
						break;
					}
				}
				if (!result) { /* short circuit */
					/*
					 * can stop checking because there is at least one
					 * capability required by a role that is not in the
					 * organization
					 */
					break;
				}
			}
			if (result) { /* short circuit */
				/*
				 * there is no reason to continue checking if the previous
				 * results are false
				 */
				for (final Agent<?> agent : organization.getAgents()) {
					for (final Capability capability : agent.getPossessesSet()) {
						result &= organization.getCapability(capability
								.getIdentifier()) != null;
						if (!result) { /* short circuit */
							/*
							 * can stop checking because there is at least one
							 * capability possessed by an agent that is not in
							 * the organization
							 */
							break;
						}
					}
					if (!result) { /* short circuit */
						/*
						 * can stop checking because there is at least one
						 * capability possessed by an agent that is not in the
						 * organization
						 */
						break;
					}
				}
			}
		}
		return result;
	}

}
