/*
 * Organization.java
 *
 * Created on Sep 22, 2004
 *
 * See License.txt file the license agreement.
 */
package org.models.organization;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.models.organization.Organization;
import org.models.organization.entity.Agent;
import org.models.organization.entity.Attribute;
import org.models.organization.entity.Capability;
import org.models.organization.entity.Characteristic;
import org.models.organization.entity.InstanceGoal;
import org.models.organization.entity.PerformanceFunction;
import org.models.organization.entity.Policy;
import org.models.organization.entity.Role;
import org.models.organization.entity.SpecificationGoal;
import org.models.organization.identifier.UniqueIdentifier;
import org.models.organization.registry.ChangeManager;
import org.models.organization.registry.EventRegistry;
import org.models.organization.relation.Assignment;
import org.models.organization.relation.Task;

/**
 * The {@linkplain OrganizationImpl} class implements the {@link Organization} interface.
 *
 * @author Scott Harmon
 * @author Christopher Zhong
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
	 * The {@linkplain Entities} class is an data class for the entities within an {@linkplain Organization}.
	 * 
	 * @author Christopher Zhong
	 * @since 7.0.0
	 */
	private static final class Entities {
		/**
		 * The set of {@linkplain SpecificationGoal} in this {@linkplain Organization}.
		 */
		private final Map<UniqueIdentifier, SpecificationGoal> specificationGoals = new ConcurrentHashMap<>();

		/**
		 * The set of {@linkplain Role} in this {@linkplain Organization}.
		 */
		private final Map<UniqueIdentifier, Role> roles = new ConcurrentHashMap<>();

		/**
		 * The set of {@linkplain Agent} in this {@linkplain Organization}.
		 */
		private final Map<UniqueIdentifier, Agent> agents = new ConcurrentHashMap<>();

		/**
		 * The set of {@linkplain Capability} in this {@linkplain Organization}.
		 */
		private final Map<UniqueIdentifier, Capability> capabilities = new ConcurrentHashMap<>();

		/**
		 * The set of {@linkplain Policy} in this {@linkplain Organization}.
		 */
		private final Map<UniqueIdentifier, Policy> policies = new ConcurrentHashMap<>();

		/**
		 * The set of {@linkplain InstanceGoal} in this {@linkplain Organization}.
		 */
		private final Map<UniqueIdentifier, InstanceGoal<?>> instanceGoals = new ConcurrentHashMap<>();

		/**
		 * The set of {@linkplain InstanceGoal} in this {@linkplain Organization} that is indexed by the {@linkplain SpecificationGoal}.
		 */
		private final Map<UniqueIdentifier, Map<UniqueIdentifier, InstanceGoal<?>>> instanceGoalsBySpecificationGoal = new ConcurrentHashMap<>();

		/**
		 * The set of {@linkplain Attribute} in this {@linkplain Organization}.
		 */
		private final Map<UniqueIdentifier, Attribute> attributes = new ConcurrentHashMap<>();

		/**
		 * The set of {@linkplain Task} in this {@linkplain Organization}.
		 */
		private final Map<UniqueIdentifier, Task> tasks = new ConcurrentHashMap<>();

		/**
		 * The set of {@linkplain PerformanceFunction} in this {@linkplain Organization}.
		 */
		private final Map<UniqueIdentifier, PerformanceFunction> performanceFunctions = new ConcurrentHashMap<>();

		/**
		 * The set of {@linkplain Characteristic} in this {@linkplain Organization}.
		 */
		private final Map<UniqueIdentifier, Characteristic> characteristics = new ConcurrentHashMap<>();
	}

	/**
	 * The {@linkplain Relations} is a data class for the relations that are within an {@linkplain Organization}.
	 * 
	 * @author Christopher Zhong
	 * @since 7.0.0
	 */
	private static final class Relations {
		/**
		 * The set of {@linkplain Assignment} in this {@linkplain Organization}.
		 */
		private final Map<UniqueIdentifier, Assignment> assignments = new ConcurrentHashMap<>();

		/**
		 * The set of {@linkplain Assignment} in this {@linkplain Organization} that is indexed by the {@linkplain Agent}.
		 */
		private final Map<UniqueIdentifier, Map<UniqueIdentifier, Assignment>> assignmentsByAgent = new ConcurrentHashMap<>();
	}

	/**
	 * The entities within this {@linkplain Organization}.
	 */
	private final Entities entities = new Entities();

	/**
	 * The relations within this {@linkplain Organization}.
	 */
	private final Relations relations = new Relations();

	/**
	 * Constructs an empty instance of an {@linkplain Organization}.
	 */
	public OrganizationImpl() {
	}

	@Override
	public final void addSpecificationGoal(final SpecificationGoal specificationGoal) {
		if (specificationGoal == null) {
			throw new IllegalArgumentException("Parameter (specificationGoal) cannot be null");
		}
		if (entities.specificationGoals.containsKey(specificationGoal.getIdentifier())) {
			throw new IllegalArgumentException(String.format("Specification goal (%s) already exists", specificationGoal));
		}
		entities.specificationGoals.put(specificationGoal.getIdentifier(), specificationGoal);
		entities.instanceGoalsBySpecificationGoal.put(specificationGoal.getIdentifier(), new ConcurrentHashMap<UniqueIdentifier, InstanceGoal<?>>());

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifySpecificationGoalAdded(specificationGoal.getIdentifier());
		}
	}

	@Override
	public final void addSpecificationGoals(final Collection<SpecificationGoal> specificationGoals) {
		if (specificationGoals == null) {
			throw new IllegalArgumentException(String.format("Specification goals (%s) cannot be null", specificationGoals));
		}
		for (final SpecificationGoal specificationGoal : specificationGoals) {
			addSpecificationGoal(specificationGoal);
		}
	}

	@Override
	public final void addSpecificationGoals(final SpecificationGoal... specificationGoals) {
		if (specificationGoals == null) {
			throw new IllegalArgumentException(String.format("Specification goals (%s) cannot be null", (Object[]) specificationGoals));
		}
		for (final SpecificationGoal specificationGoal : specificationGoals) {
			addSpecificationGoal(specificationGoal);
		}
	}

	@Override
	public final SpecificationGoal getSpecificationGoal(final UniqueIdentifier goalIdentifier) {
		if (goalIdentifier == null) {
			throw new IllegalArgumentException("Parameter (goalIdentifier) cannot be null");
		}
		return entities.specificationGoals.get(goalIdentifier);
	}

	@Override
	public final Set<SpecificationGoal> getSpecificationGoals() {
		return new HashSet<>(entities.specificationGoals.values());
	}

	@Override
	public final void removeSpecificationGoal(final UniqueIdentifier goalIdentifier) {
		if (goalIdentifier == null) {
			throw new IllegalArgumentException("Parameter (goalIdentifier) cannot be null");
		}
		final SpecificationGoal specificationGoal = entities.specificationGoals.remove(goalIdentifier);
		entities.instanceGoalsBySpecificationGoal.remove(goalIdentifier);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifySpecificationGoalRemoved(specificationGoal.getIdentifier());
		}
	}

	@Override
	public final void removeSpecificationGoals(final Collection<UniqueIdentifier> goalIdentifiers) {
		if (goalIdentifiers == null) {
			throw new IllegalArgumentException("Parameter (goalIdentifiers) cannot be null");
		}
		for (final UniqueIdentifier specificationGoalIdentifier : goalIdentifiers) {
			removeSpecificationGoal(specificationGoalIdentifier);
		}
	}

	@Override
	public final void removeSpecificationGoals(final UniqueIdentifier... goalIdentifiers) {
		if (goalIdentifiers == null) {
			throw new IllegalArgumentException("Parameter (goalIdentifiers) cannot be null");
		}
		for (final UniqueIdentifier specificationGoalIdentifier : goalIdentifiers) {
			removeSpecificationGoal(specificationGoalIdentifier);
		}
	}

	@Override
	public final void removeAllSpecificationGoals() {
		entities.specificationGoals.clear();
		entities.instanceGoalsBySpecificationGoal.clear();
	}

	@Override
	public final void addRole(final Role role) {
		if (role == null) {
			throw new IllegalArgumentException("Parameter (role) cannot be null");
		}
		if (entities.roles.containsKey(role.getIdentifier())) {
			throw new IllegalArgumentException(String.format("Role (%s) already exists", role));
		}
		entities.roles.put(role.getIdentifier(), role);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyRoleAdded(role.getIdentifier());
		}
	}

	@Override
	public final void addRoles(final Collection<Role> roles) {
		if (roles == null) {
			throw new IllegalArgumentException("Parameter (roles) cannot be null");
		}
		for (final Role role : roles) {
			addRole(role);
		}
	}

	@Override
	public final void addRoles(final Role... roles) {
		if (roles == null) {
			throw new IllegalArgumentException("Parameter (roles) cannot be null");
		}
		for (final Role role : roles) {
			addRole(role);
		}
	}

	@Override
	public final Role getRole(final UniqueIdentifier roleIdentifier) {
		if (roleIdentifier == null) {
			throw new IllegalArgumentException("Parameter (roleIdentifier) cannot be null");
		}
		return entities.roles.get(roleIdentifier);
	}

	@Override
	public final Set<Role> getRoles() {
		return new HashSet<>(entities.roles.values());
	}

	@Override
	public final void removeRole(final UniqueIdentifier roleIdentifier) {
		if (roleIdentifier == null) {
			throw new IllegalArgumentException("Parameter (roleIdentifier) cannot be null");
		}
		final Role role = entities.roles.remove(roleIdentifier);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyRoleRemoved(role.getIdentifier());
		}
	}

	@Override
	public final void removeRoles(final Collection<UniqueIdentifier> roleIdentifiers) {
		if (roleIdentifiers == null) {
			throw new IllegalArgumentException("Parameter (roleIdentifiers) cannot be null");
		}
		for (final UniqueIdentifier roleIdentifier : roleIdentifiers) {
			removeRole(roleIdentifier);
		}
	}

	@Override
	public final void removeRoles(final UniqueIdentifier... roleIdentifiers) {
		if (roleIdentifiers == null) {
			throw new IllegalArgumentException("Parameter (roleIdentifiers) cannot be null");
		}
		for (final UniqueIdentifier roleIdentifier : roleIdentifiers) {
			removeRole(roleIdentifier);
		}
	}

	@Override
	public final void removeAllRoles() {
		entities.roles.clear();
	}

	@Override
	public final void addAgent(final Agent agent) {
		if (agent == null) {
			throw new IllegalArgumentException("Parameter (agent) cannot be null");
		}
		if (entities.agents.containsKey(agent.getIdentifier())) {
			throw new IllegalArgumentException(String.format("Agent (%s) already exists", agent));
		}
		entities.agents.put(agent.getIdentifier(), agent);
		final Map<UniqueIdentifier, Assignment> map = new ConcurrentHashMap<>();
		relations.assignmentsByAgent.put(agent.getIdentifier(), map);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyAgentAdded(agent.getIdentifier());
		}
	}

	@Override
	public final void addAgents(final Collection<Agent> agents) {
		if (agents == null) {
			throw new IllegalArgumentException("Parameter (agents) cannot be null");
		}
		for (final Agent agent : agents) {
			addAgent(agent);
		}
	}

	@Override
	public final void addAgents(final Agent... agents) {
		if (agents == null) {
			throw new IllegalArgumentException("Parameter (agents) cannot be null");
		}
		for (final Agent agent : agents) {
			addAgent(agent);
		}
	}

	@Override
	public final Agent getAgent(final UniqueIdentifier agentIdentifier) {
		if (agentIdentifier == null) {
			throw new IllegalArgumentException("Parameter (agentIdentifier) cannot be null");
		}
		return entities.agents.get(agentIdentifier);
	}

	@Override
	public final Set<Agent> getAgents() {
		return new HashSet<>(entities.agents.values());
	}

	@Override
	public final void removeAgent(final UniqueIdentifier agentIdentifier) {
		if (agentIdentifier == null) {
			throw new IllegalArgumentException("Parameter (agentIdentifier) cannot be null");
		}
		final Agent agent = entities.agents.remove(agentIdentifier);
		relations.assignmentsByAgent.remove(agentIdentifier);

		if (agent != null) {
			final ChangeManager changeManager = EventRegistry.get();
			if (changeManager != null) {
				changeManager.notifyAgentRemoved(agent.getIdentifier());
			}
		}
	}

	@Override
	public final void removeAgents(final Collection<UniqueIdentifier> agentIdentifiers) {
		if (agentIdentifiers == null) {
			throw new IllegalArgumentException("Parameter (agentIdentifiers) cannot be null");
		}
		for (final UniqueIdentifier agentIdentifier : agentIdentifiers) {
			removeAgent(agentIdentifier);
		}
	}

	@Override
	public final void removeAgents(final UniqueIdentifier... agentIdentifiers) {
		if (agentIdentifiers == null) {
			throw new IllegalArgumentException("Parameter (agentIdentifiers) cannot be null");
		}
		for (final UniqueIdentifier agentIdentifier : agentIdentifiers) {
			removeAgent(agentIdentifier);
		}
	}

	@Override
	public final void removeAllAgents() {
		entities.agents.clear();
		relations.assignmentsByAgent.clear();
	}

	@Override
	public final void addCapability(final Capability capability) {
		if (capability == null) {
			throw new IllegalArgumentException("Parameter (capability) cannot be null");
		}
		if (entities.capabilities.containsKey(capability.getIdentifier())) {
			throw new IllegalArgumentException(String.format("Capability (%s) already exists", capability));
		}
		entities.capabilities.put(capability.getIdentifier(), capability);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyCapabilityAdded(capability.getIdentifier());
		}
	}

	@Override
	public final void addCapabilities(final Collection<Capability> capabilities) {
		if (capabilities == null) {
			throw new IllegalArgumentException("Parameter (capabilities) cannot be null");
		}
		for (final Capability capability : capabilities) {
			addCapability(capability);
		}
	}

	@Override
	public final void addCapabilities(final Capability... capabilities) {
		if (capabilities == null) {
			throw new IllegalArgumentException("Parameter (capabilities) cannot be null");
		}
		for (final Capability capability : capabilities) {
			addCapability(capability);
		}
	}

	@Override
	public final Capability getCapability(final UniqueIdentifier capabilityIdentifier) {
		if (capabilityIdentifier == null) {
			throw new IllegalArgumentException("Parameter (capabilityIdentifier) cannot be null");
		}
		return entities.capabilities.get(capabilityIdentifier);
	}

	@Override
	public final Set<Capability> getCapabilities() {
		return new HashSet<>(entities.capabilities.values());
	}

	@Override
	public final void removeCapability(final UniqueIdentifier capabilityIdentifier) {
		if (capabilityIdentifier == null) {
			throw new IllegalArgumentException("Parameter (capabilityIdentifier) cannot be null");
		}
		final Capability capability = entities.capabilities.remove(capabilityIdentifier);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyCapabilityRemoved(capability.getIdentifier());
		}
	}

	@Override
	public final void removeCapabilities(final Collection<UniqueIdentifier> capabilityIdentifiers) {
		if (capabilityIdentifiers == null) {
			throw new IllegalArgumentException("Parameter (capabilityIdentifiers) cannot be null");
		}
		for (final UniqueIdentifier capabilityIdentifier : capabilityIdentifiers) {
			removeCapability(capabilityIdentifier);
		}
	}

	@Override
	public final void removeCapabilities(final UniqueIdentifier... capabilityIdentifiers) {
		if (capabilityIdentifiers == null) {
			throw new IllegalArgumentException("Parameter (capabilityIdentifiers) cannot be null");
		}
		for (final UniqueIdentifier capabilityIdentifier : capabilityIdentifiers) {
			removeCapability(capabilityIdentifier);
		}
	}

	@Override
	public final void removeAllCapabilities() {
		entities.capabilities.clear();
	}

	@Override
	public final void addPolicy(final Policy policy) {
		if (policy == null) {
			throw new IllegalArgumentException("Parameter (policy) cannot be null");
		}
		if (entities.policies.containsKey(policy.getIdentifier())) {
			throw new IllegalArgumentException(String.format("Policy (%s) already exists", policy));
		}
		entities.policies.put(policy.getIdentifier(), policy);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyPolicyAdded(policy.getIdentifier());
		}
	}

	@Override
	public final void addPolicies(final Collection<Policy> policies) {
		if (policies == null) {
			throw new IllegalArgumentException("Parameter (policies) cannot be null");
		}
		for (final Policy policy : policies) {
			addPolicy(policy);
		}
	}

	@Override
	public final void addPolicies(final Policy... policies) {
		if (policies == null) {
			throw new IllegalArgumentException("Parameter (policies) cannot be null");
		}
		for (final Policy policy : policies) {
			addPolicy(policy);
		}
	}

	@Override
	public final Policy getPolicy(final UniqueIdentifier policyIdentifier) {
		if (policyIdentifier == null) {
			throw new IllegalArgumentException("Parameter (policyIdentifier) cannot be null");
		}
		return entities.policies.get(policyIdentifier);
	}

	@Override
	public final Set<Policy> getPolicies() {
		return new HashSet<>(entities.policies.values());
	}

	@Override
	public final void removePolicy(final UniqueIdentifier policyIdentifier) {
		if (policyIdentifier == null) {
			throw new IllegalArgumentException("Parameter (policyIdentifier) cannot be null");
		}
		final Policy policy = entities.policies.remove(policyIdentifier);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyPolicyRemoved(policy.getIdentifier());
		}
	}

	@Override
	public final void removePolicies(final Collection<UniqueIdentifier> policyIdentifiers) {
		if (policyIdentifiers == null) {
			throw new IllegalArgumentException("Parameter (policyIdentifiers) cannot be null");
		}
		for (final UniqueIdentifier policyIdentifer : policyIdentifiers) {
			removePolicy(policyIdentifer);
		}
	}

	@Override
	public final void removePolicies(final UniqueIdentifier... policyIdentifiers) {
		if (policyIdentifiers == null) {
			throw new IllegalArgumentException("Parameter (policyIdentifiers) cannot be null");
		}
		for (final UniqueIdentifier policyIdentifer : policyIdentifiers) {
			removePolicy(policyIdentifer);
		}
	}

	@Override
	public final void removeAllPolicies() {
		entities.policies.clear();
	}

	@Override
	public final void addInstanceGoal(final InstanceGoal<?> instanceGoal) {
		if (instanceGoal == null) {
			throw new IllegalArgumentException("Parameter (instanceGoal) cannot be null");
		}
		if (entities.instanceGoals.containsKey(instanceGoal.getIdentifier())) {
			throw new IllegalArgumentException(String.format("Instance goal (%s) already exists", instanceGoal.getIdentifier()));
		}
		entities.instanceGoals.put(instanceGoal.getIdentifier(), instanceGoal);
		entities.instanceGoalsBySpecificationGoal.get(instanceGoal.getSpecificationIdentifier()).put(instanceGoal.getInstanceIdentifier(), instanceGoal);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyInstanceGoalAdded(instanceGoal.getIdentifier(), instanceGoal.getParameter());
		}
	}

	@Override
	public final void addInstanceGoals(final Collection<InstanceGoal<?>> instanceGoals) {
		if (instanceGoals == null) {
			throw new IllegalArgumentException("Parameter (instanceGoals) cannot be null");
		}
		for (final InstanceGoal<?> instanceGoal : instanceGoals) {
			addInstanceGoal(instanceGoal);
		}
	}

	@Override
	public final void addInstanceGoals(final InstanceGoal<?>... instanceGoals) {
		if (instanceGoals == null) {
			throw new IllegalArgumentException("Parameter (instanceGoals) cannot be null");
		}
		for (final InstanceGoal<?> instanceGoal : instanceGoals) {
			addInstanceGoal(instanceGoal);
		}
	}

	@Override
	public final InstanceGoal<?> getInstanceGoal(final UniqueIdentifier goalIdentifier) {
		if (goalIdentifier == null) {
			throw new IllegalArgumentException("Parameter (goalIdentifier) cannot be null");
		}
		return entities.instanceGoals.get(goalIdentifier);
	}

	@Override
	public final Set<InstanceGoal<?>> getInstanceGoals() {
		return new HashSet<>(entities.instanceGoals.values());
	}

	@Override
	public final void removeInstanceGoal(final UniqueIdentifier goalIdentifier) {
		if (goalIdentifier == null) {
			throw new IllegalArgumentException("Parameter (goalIdentifier) cannot be null");
		}
		final InstanceGoal<?> instanceGoal = entities.instanceGoals.remove(goalIdentifier);
		entities.instanceGoalsBySpecificationGoal.get(instanceGoal.getSpecificationIdentifier()).remove(instanceGoal.getInstanceIdentifier());

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyInstanceGoalRemoved(instanceGoal.getIdentifier());
		}
	}

	@Override
	public final void removeInstanceGoals(final Collection<UniqueIdentifier> goalIdentifiers) {
		if (goalIdentifiers == null) {
			throw new IllegalArgumentException("Parameter (goalIdentifiers) cannot be null");
		}
		for (final UniqueIdentifier instanceGoalIdentifier : goalIdentifiers) {
			removeInstanceGoal(instanceGoalIdentifier);
		}
	}

	@Override
	public final void removeInstanceGoals(final UniqueIdentifier... goalIdentifiers) {
		if (goalIdentifiers == null) {
			throw new IllegalArgumentException("Parameter (goalIdentifiers) cannot be null");
		}
		for (final UniqueIdentifier instanceGoalIdentifier : goalIdentifiers) {
			removeInstanceGoal(instanceGoalIdentifier);
		}
	}

	@Override
	public final void removeAllInstanceGoals() {
		entities.instanceGoals.clear();
		for (final Map<UniqueIdentifier, InstanceGoal<?>> map : entities.instanceGoalsBySpecificationGoal.values()) {
			map.clear();
		}
	}

	@Override
	public final void addAttribute(final Attribute attribute) {
		if (attribute == null) {
			throw new IllegalArgumentException("Parameter (attribute) cannot be null");
		}
		if (entities.attributes.containsKey(attribute.getIdentifier())) {
			throw new IllegalArgumentException(String.format("Attribute (%s) already exists", attribute));
		}
		entities.attributes.put(attribute.getIdentifier(), attribute);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyAttributeAdded(attribute.getIdentifier());
		}
	}

	@Override
	public final void addAttributes(final Collection<Attribute> attributes) {
		if (attributes == null) {
			throw new IllegalArgumentException("Parameter (attributes) cannot be null");
		}
		for (final Attribute attribute : attributes) {
			addAttribute(attribute);
		}
	}

	@Override
	public final void addAttributes(final Attribute... attributes) {
		if (attributes == null) {
			throw new IllegalArgumentException("Parameter (attributes) cannot be null");
		}
		for (final Attribute attribute : attributes) {
			addAttribute(attribute);
		}
	}

	@Override
	public final Attribute getAttribute(final UniqueIdentifier attributeIdentifier) {
		if (attributeIdentifier == null) {
			throw new IllegalArgumentException("Parameter (attributeIdentifier) cannot be null");
		}
		return entities.attributes.get(attributeIdentifier);
	}

	@Override
	public final Set<Attribute> getAttributes() {
		return new HashSet<>(entities.attributes.values());
	}

	@Override
	public final void removeAttribute(final UniqueIdentifier attributeIdentifier) {
		if (attributeIdentifier == null) {
			throw new IllegalArgumentException("Parameter (attributeIdentifier) cannot be null");
		}
		final Attribute attribute = entities.attributes.remove(attributeIdentifier);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyAttributeRemoved(attribute.getIdentifier());
		}
	}

	@Override
	public final void removeAttributes(final Collection<UniqueIdentifier> attributeIdentifiers) {
		if (attributeIdentifiers == null) {
			throw new IllegalArgumentException("Parameter (attributeIdentifiers) cannot be null");
		}
		for (final UniqueIdentifier attributeIdentifier : attributeIdentifiers) {
			removeAttribute(attributeIdentifier);
		}
	}

	@Override
	public final void removeAttributes(final UniqueIdentifier... attributeIdentifiers) {
		if (attributeIdentifiers == null) {
			throw new IllegalArgumentException("Parameter (attributeIdentifiers) cannot be null");
		}
		for (final UniqueIdentifier attributeIdentifier : attributeIdentifiers) {
			removeAttribute(attributeIdentifier);
		}
	}

	@Override
	public final void removeAllAttributes() {
		entities.attributes.clear();
	}

	@Override
	public final void addTask(final Task task) {
		if (task == null) {
			throw new IllegalArgumentException("Parameter (task) cannot be null");
		}
		if (entities.tasks.containsKey(task.getIdentifier())) {
			throw new IllegalArgumentException(String.format("Task (%s) already exists", task));
		}
		entities.tasks.put(task.getIdentifier(), task);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyTaskAdded(task.getIdentifier());
		}
	}

	@Override
	public final void addTasks(final Collection<Task> tasks) {
		if (tasks == null) {
			throw new IllegalArgumentException("Parameter (tasks) cannot be null");
		}
		for (final Task task : tasks) {
			addTask(task);
		}
	}

	@Override
	public final void addTasks(final Task... tasks) {
		if (tasks == null) {
			throw new IllegalArgumentException("Parameter (tasks) cannot be null");
		}
		for (final Task task : tasks) {
			addTask(task);
		}
	}

	@Override
	public Task getTask(final UniqueIdentifier taskIdentifier) {
		if (taskIdentifier == null) {
			throw new IllegalArgumentException("Parameter (taskIdentifier) cannot be null");
		}
		return entities.tasks.get(taskIdentifier);
	}

	@Override
	public final Set<Task> getTasks() {
		return new HashSet<>(entities.tasks.values());
	}

	@Override
	public final void removeTask(final UniqueIdentifier taskIdentifier) {
		if (taskIdentifier == null) {
			throw new IllegalArgumentException("Parameter (taskIdentifier) cannot be null");
		}
		final Task task = entities.tasks.remove(taskIdentifier);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyTaskRemoved(task.getIdentifier());
		}
	}

	@Override
	public final void removeAllTasks() {
		entities.tasks.clear();
	}

	@Override
	public final void addPerformanceFunction(final PerformanceFunction performanceFunction) {
		if (performanceFunction == null) {
			throw new IllegalArgumentException("Parameter (performanceFunction) cannot be null");
		}
		if (entities.performanceFunctions.containsKey(performanceFunction.getIdentifier())) {
			throw new IllegalArgumentException(String.format("Performance function (%s) already exists", performanceFunction));
		}
		entities.performanceFunctions.put(performanceFunction.getIdentifier(), performanceFunction);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyPerformanceFunctionAdded(performanceFunction.getIdentifier());
		}
	}

	@Override
	public final void addPerformanceFunctions(final Collection<PerformanceFunction> performanceFunctions) {
		if (performanceFunctions == null) {
			throw new IllegalArgumentException("Parameter (performanceFunctions) cannot be null");
		}
		for (final PerformanceFunction performanceFunction : performanceFunctions) {
			addPerformanceFunction(performanceFunction);
		}
	}

	@Override
	public final void addPerformanceFunctions(final PerformanceFunction... performanceFunctions) {
		if (performanceFunctions == null) {
			throw new IllegalArgumentException("Parameter (performanceFunctions) cannot be null");
		}
		for (final PerformanceFunction performanceFunction : performanceFunctions) {
			addPerformanceFunction(performanceFunction);
		}
	}

	@Override
	public PerformanceFunction getPerformanceFunction(final UniqueIdentifier performanceFunctionIdentifier) {
		if (performanceFunctionIdentifier == null) {
			throw new IllegalArgumentException("Parameter (performanceFunctionIdentifier) cannot be null");
		}
		return entities.performanceFunctions.get(performanceFunctionIdentifier);
	}

	@Override
	public final Set<PerformanceFunction> getPerformanceFunctions() {
		return new HashSet<>(entities.performanceFunctions.values());
	}

	@Override
	public final void removePerformanceFunction(final UniqueIdentifier performanceFunctionIdentifier) {
		if (performanceFunctionIdentifier == null) {
			throw new IllegalArgumentException("Parameter (performanceFunctionIdentifier) cannot be null");
		}
		final PerformanceFunction performanceFunction = entities.performanceFunctions.remove(performanceFunctionIdentifier);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyPerformanceFunctionRemoved(performanceFunction.getIdentifier());
		}
	}

	@Override
	public final void removeAllPerformanceFunctions() {
		entities.performanceFunctions.clear();
	}

	@Override
	public final void addCharacteristic(final Characteristic characteristic) {
		if (characteristic == null) {
			throw new IllegalArgumentException("Parameter (characteristic) cannot be null");
		}
		if (entities.characteristics.containsKey(characteristic.getIdentifier())) {
			throw new IllegalArgumentException(String.format("Characteristic (%s) already exists", characteristic));
		}
		entities.characteristics.put(characteristic.getIdentifier(), characteristic);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyCharacteristicAdded(characteristic.getIdentifier());
		}
	}

	@Override
	public final void addCharacteristics(final Collection<Characteristic> characteristics) {
		if (characteristics == null) {
			throw new IllegalArgumentException("Parameter (characteristics) cannot be null");
		}
		for (final Characteristic characteristic : characteristics) {
			addCharacteristic(characteristic);
		}
	}

	@Override
	public final void addCharacteristics(final Characteristic... characteristics) {
		if (characteristics == null) {
			throw new IllegalArgumentException("Parameter (characteristics) cannot be null");
		}
		for (final Characteristic characteristic : characteristics) {
			addCharacteristic(characteristic);
		}
	}

	@Override
	public final Characteristic getCharacteristic(final UniqueIdentifier characteristicIdentifier) {
		if (characteristicIdentifier == null) {
			throw new IllegalArgumentException("Parameter (characteristicIdentifier) cannot be null");
		}
		return entities.characteristics.get(characteristicIdentifier);
	}

	@Override
	public final Set<Characteristic> getCharacteristics() {
		return new HashSet<>(entities.characteristics.values());
	}

	@Override
	public final void removeCharacteristic(final UniqueIdentifier characteristicIdentifier) {
		if (characteristicIdentifier == null) {
			throw new IllegalArgumentException("Parameter (characteristicIdentifier) cannot be null");
		}
		if (entities.characteristics.containsKey(characteristicIdentifier)) {
			final Characteristic characteristic = entities.characteristics.remove(characteristicIdentifier);

			final ChangeManager changeManager = EventRegistry.get();
			if (changeManager != null) {
				changeManager.notifyCharacteristicRemoved(characteristic.getIdentifier());
			}
		}
	}

	@Override
	public final void removeAllCharacteristic() {
		entities.characteristics.clear();
	}

	@Override
	public final void addAssignment(final Assignment assignment) {
		if (assignment == null) {
			throw new IllegalArgumentException("Parameter (assignment) cannot be null");
		}
		if (relations.assignments.containsKey(assignment.getIdentifier())) {
			throw new IllegalArgumentException(String.format("Assignment (%s) already exists", assignment));
		}
		relations.assignments.put(assignment.getIdentifier(), assignment);
		relations.assignmentsByAgent.get(assignment.getAgent().getIdentifier()).put(assignment.getIdentifier(), assignment);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyAssignmentAdded(assignment.getAgent().getIdentifier(), assignment.getRole().getIdentifier(), assignment.getInstanceGoal()
					.getIdentifier());
		}
	}

	@Override
	public final void addAssignments(final Collection<Assignment> assignments) {
		if (assignments == null) {
			throw new IllegalArgumentException("Parameter (assignments) cannot be null");
		}
		for (final Assignment assignment : assignments) {
			addAssignment(assignment);
		}
	}

	@Override
	public final void addAssignments(final Assignment... assignments) {
		if (assignments == null) {
			throw new IllegalArgumentException("Parameter (assignments) cannot be null");
		}
		for (final Assignment assignment : assignments) {
			addAssignment(assignment);
		}
	}

	@Override
	public final Assignment getAssignment(final UniqueIdentifier assignmentIdentifier) {
		if (assignmentIdentifier == null) {
			throw new IllegalArgumentException("Parameter (assignmentIdentifier) cannot be null");
		}
		return relations.assignments.get(assignmentIdentifier);
	}

	@Override
	public final Set<Assignment> getAssignments() {
		return new HashSet<>(relations.assignments.values());
	}

	@Override
	public final Set<Assignment> getAssignmentsOfAgent(final UniqueIdentifier agentIdentifier) {
		if (agentIdentifier == null) {
			throw new IllegalArgumentException("Parameter (agentIdentifier) cannot be null");
		}
		final Set<Assignment> results = new HashSet<>();
		final Map<UniqueIdentifier, Assignment> map = relations.assignmentsByAgent.get(agentIdentifier);
		if (map != null) {
			results.addAll(map.values());
		}
		return results;
	}

	@Override
	public final void removeAssignment(final UniqueIdentifier assignmentIdentifier) {
		if (assignmentIdentifier == null) {
			throw new IllegalArgumentException("Parameter (assignmentIdentifier) cannot be null");
		}
		final Assignment assignment = relations.assignments.remove(assignmentIdentifier);
		relations.assignmentsByAgent.get(assignment.getAgent().getIdentifier()).remove(assignmentIdentifier);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyAssignmentRemoved(assignment.getAgent().getIdentifier(), assignment.getRole().getIdentifier(), assignment.getInstanceGoal()
					.getIdentifier());
		}
	}

	@Override
	public final void removeAssignments(final Collection<UniqueIdentifier> assignmentIdentifiers) {
		if (assignmentIdentifiers == null) {
			throw new IllegalArgumentException("Parameter (assignmentIdentifiers) cannot be null");
		}
		for (final UniqueIdentifier assignmentIdentifier : assignmentIdentifiers) {
			removeAssignment(assignmentIdentifier);
		}
	}

	@Override
	public final void removeAssignments(final UniqueIdentifier... assignmentIdentifiers) {
		if (assignmentIdentifiers == null) {
			throw new IllegalArgumentException("Parameter (assignmentIdentifiers) cannot be null");
		}
		for (final UniqueIdentifier assignmentIdentifier : assignmentIdentifiers) {
			removeAssignment(assignmentIdentifier);
		}
	}

	@Override
	public final void removeAllAssignments() {
		relations.assignments.clear();
		for (final Map<UniqueIdentifier, Assignment> map : relations.assignmentsByAgent.values()) {
			map.clear();
		}
	}

	@Override
	public final void addAchievesRelation(final UniqueIdentifier roleIdentifier, final UniqueIdentifier goalIdentifier) {
		final Role role = getRole(roleIdentifier);
		final SpecificationGoal goal = getSpecificationGoal(goalIdentifier);
		if (role == null || goal == null) {
			throw new IllegalArgumentException(String.format("Both role (%s=%s) and goal (%s=%s) must exists", roleIdentifier, role, goalIdentifier, goal));
		}
		role.addAchieves(goal);
	}

	@Override
	public final void removeAchievesRelation(final UniqueIdentifier roleIdentifier, final UniqueIdentifier goalIdentifier) {
		final Role role = getRole(roleIdentifier);
		final SpecificationGoal goal = getSpecificationGoal(goalIdentifier);
		if (role == null || goal == null) {
			throw new IllegalArgumentException(String.format("Both role (%s=%s) and goal (%s=%s) must exists", roleIdentifier, role, goalIdentifier, goal));
		}
		role.removeAchieves(goal.getIdentifier());
	}

	@Override
	public final void addRequiresRelation(final UniqueIdentifier roleIdentifier, final UniqueIdentifier capabilityIdentifier) {
		final Role role = getRole(roleIdentifier);
		final Capability capability = getCapability(capabilityIdentifier);
		if (role == null || capability == null) {
			throw new IllegalArgumentException(String.format("Both role (%s=%s) and capability (%s=%s) must exists", roleIdentifier, role,
					capabilityIdentifier, capability));
		}
		role.addRequires(capability);
	}

	@Override
	public final void removeRequiresRelation(final UniqueIdentifier roleIdentifier, final UniqueIdentifier capabilityIdentifier) {
		final Role role = getRole(roleIdentifier);
		final Capability capability = getCapability(capabilityIdentifier);
		if (role == null || capability == null) {
			throw new IllegalArgumentException(String.format("Both role (%s=%s) and capability (%s=%s) must exists", roleIdentifier, role,
					capabilityIdentifier, capability));
		}
		role.removeRequires(capability.getIdentifier());
	}

	@Override
	public final void addPossessesRelation(final UniqueIdentifier agentIdentifier, final UniqueIdentifier capabilityIdentifier, final double score) {
		final Agent agent = getAgent(agentIdentifier);
		final Capability capability = getCapability(capabilityIdentifier);
		if (agent == null || capability == null) {
			throw new IllegalArgumentException(String.format("Both agent (%s=%s) and capability (%s=%s) must exists", agentIdentifier, agent,
					capabilityIdentifier, capability));
		}
		agent.addPossesses(capability, score);
	}

	@Override
	public final void removePossessesRelation(final UniqueIdentifier agentIdentifier, final UniqueIdentifier capabilityIdentifier) {
		final Agent agent = getAgent(agentIdentifier);
		final Capability capability = getCapability(capabilityIdentifier);
		if (agent == null || capability == null) {
			throw new IllegalArgumentException(String.format("Both agent (%s=%s) and capability (%s=%s) must exists", agentIdentifier, agent,
					capabilityIdentifier, capability));
		}
		agent.removePossesses(capabilityIdentifier);
	}

	@Override
	public final void updatePossessesRelation(final UniqueIdentifier agentIdentifier, final UniqueIdentifier capabilityIdentifier, final double score) {
		final Agent agent = getAgent(agentIdentifier);
		final Capability capability = getCapability(capabilityIdentifier);
		if (agent == null || capability == null) {
			throw new IllegalArgumentException(String.format("Both agent (%s=%s) and capability (%s=%s) must exists", agentIdentifier, agent,
					capabilityIdentifier, capability));
		}
		agent.setPossessesScore(capabilityIdentifier, score);
	}

	@Override
	public final void addNeedsRelation(final UniqueIdentifier roleIdentifier, final UniqueIdentifier attributeIdentifier) {
		final Role role = getRole(roleIdentifier);
		final Attribute attribute = getAttribute(attributeIdentifier);
		if (role == null || attribute == null) {
			throw new IllegalArgumentException(String.format("Both role (%s=%s) and attribute (%s=%s) must exists", roleIdentifier, role, attributeIdentifier,
					attribute));
		}
		role.addNeeds(attribute);
	}

	@Override
	public final void removeNeedsRelation(final UniqueIdentifier roleIdentifier, final UniqueIdentifier attributeIdentifier) {
		final Role role = getRole(roleIdentifier);
		final Attribute attribute = getAttribute(attributeIdentifier);
		if (role == null || attribute == null) {
			throw new IllegalArgumentException(String.format("Both role (%s=%s) and attribute (%s=%s) must exists", roleIdentifier, role, attributeIdentifier,
					attribute));
		}
		role.removeNeeds(attributeIdentifier);
	}

	@Override
	public final void addHasRelation(final UniqueIdentifier agentIdentifier, final UniqueIdentifier attributeIdentifier, final double value) {
		final Agent agent = getAgent(agentIdentifier);
		final Attribute attribute = getAttribute(attributeIdentifier);
		if (agent == null || attribute == null) {
			throw new IllegalArgumentException(String.format("Both agent (%s=%s) and attribute (%s=%s) must exists", agentIdentifier, agent,
					attributeIdentifier, attribute));
		}
		agent.addHas(attribute, value);
	}

	@Override
	public final void removeHasRelation(final UniqueIdentifier agentIdentifier, final UniqueIdentifier attributeIdentifier) {
		final Agent agent = getAgent(agentIdentifier);
		final Attribute attribute = getAttribute(attributeIdentifier);
		if (agent == null || attribute == null) {
			throw new IllegalArgumentException(String.format("Both agent (%s=%s) and attribute (%s=%s) must exists", agentIdentifier, agent,
					attributeIdentifier, attribute));
		}
		agent.removeHas(attributeIdentifier);
	}

	@Override
	public final void updateHasRelation(final UniqueIdentifier agentIdentifier, final UniqueIdentifier attributeIdentifier, final double score) {
		final Agent agent = getAgent(agentIdentifier);
		final Attribute attribute = getAttribute(attributeIdentifier);
		if (agent == null || attribute == null) {
			throw new IllegalArgumentException(String.format("Both agent (%s=%s) and attribute (%s=%s) must exists", agentIdentifier, agent,
					attributeIdentifier, attribute));
		}
		agent.setHasValue(attributeIdentifier, score);
	}

	@Override
	public final void addUsesRelation(final UniqueIdentifier roleIdentifier, final UniqueIdentifier functionIdentifier) {
		final Role role = getRole(roleIdentifier);
		final PerformanceFunction performanceFunction = getPerformanceFunction(functionIdentifier);
		if (role == null || performanceFunction == null) {
			throw new IllegalArgumentException(String.format("Both role (%s=%s) and performance function (%s=%s) must exists", roleIdentifier, role,
					functionIdentifier, performanceFunction));
		}
		role.addUses(performanceFunction);
	}

	@Override
	public final void removeUsesRelation(final UniqueIdentifier roleIdentifier, final UniqueIdentifier functionIdentifier) {
		final Role role = getRole(roleIdentifier);
		final PerformanceFunction performanceFunction = getPerformanceFunction(functionIdentifier);
		if (role == null || performanceFunction == null) {
			throw new IllegalArgumentException(String.format("Both role (%s=%s) and performance function (%s=%s) must exists", roleIdentifier, role,
					functionIdentifier, performanceFunction));
		}
		role.removeUses(functionIdentifier);
	}

	@Override
	public final void setModeratesRelation(final UniqueIdentifier performanceFunctionIdentifier, final UniqueIdentifier attributeIdentifier) {
		final PerformanceFunction performanceFunction = getPerformanceFunction(performanceFunctionIdentifier);
		final Attribute attribute = getAttribute(attributeIdentifier);
		if (performanceFunction == null || attribute == null) {
			throw new IllegalArgumentException(String.format("Both performance function (%s=%s) and attribute (%s=%s) must exists",
					performanceFunctionIdentifier, performanceFunction, attributeIdentifier, attribute));
		}
		performanceFunction.setModerates(attribute);

	}

	@Override
	public final void addContainsRelation(final UniqueIdentifier roleIdentifier, final UniqueIdentifier characteristicIdentifier, final double value) {
		final Role role = getRole(roleIdentifier);
		final Characteristic characteristic = getCharacteristic(characteristicIdentifier);
		if (role == null || characteristic == null) {
			throw new IllegalArgumentException(String.format("Both role (%s=%s) and characteristic (%s=%s) must exists", roleIdentifier, role,
					characteristicIdentifier, characteristic));
		}
		role.addContains(characteristic, value);
	}

	@Override
	public final void removeContainsRelation(final UniqueIdentifier roleIdentifier, final UniqueIdentifier characteristicIdentifier) {
		final Role role = getRole(roleIdentifier);
		final Characteristic characteristic = getCharacteristic(characteristicIdentifier);
		if (role == null || characteristic == null) {
			throw new IllegalArgumentException(String.format("Both role (%s=%s) and characteristic (%s=%s) must exists", roleIdentifier, role,
					characteristicIdentifier, characteristic));
		}
		role.removeContains(characteristicIdentifier);
	}

	@Override
	public final void updateContainsRelation(final UniqueIdentifier roleIdentifier, final UniqueIdentifier characteristicIdentifier, final double value) {
		final Role role = getRole(roleIdentifier);
		final Characteristic characteristic = getCharacteristic(characteristicIdentifier);
		if (role == null || characteristic == null) {
			throw new IllegalArgumentException(String.format("Both role (%s=%s) and characteristic (%s=%s) must exists", roleIdentifier, role,
					characteristicIdentifier, characteristic));
		}
		role.setContainsValue(characteristicIdentifier, value);
	}

	/**
	 * Determines whether this {@linkplain Organization} is valid.
	 * <p>
	 * Validity checks include checks for the structural integrity of the current organization.
	 *
	 * @param organization
	 *            the {@linkplain Organization} to check.
	 * @return <code>true</code> if the {@linkplain Organization} is valid, <code>false</code> otherwise.
	 */
	public static final boolean isOrganizationValid(final Organization organization) {
		boolean result = true;
		/*
		 * every goal can be achieved by at least one role from the organization
		 */
		for (final SpecificationGoal goal : organization.getSpecificationGoals()) {
			boolean isAchievable = false;
			for (final Role role : goal.getAchievedBySet()) {
				isAchievable |= organization.getRole(role.getIdentifier()) != null;
				if (isAchievable) { /* short circuit */
					/*
					 * can stop checking because there is at least one role that can achieve the goal
					 */
					break;
				}
			}
			result &= isAchievable;
			if (!result) { /* short circuit */
				/*
				 * can stop checking because there is at least one goal that cannot be achieved by any role in the organization
				 */
				break;
			}
		}
		/*
		 * the set of capabilities is the union of all capabilities required by roles or possessed by agents in the organization
		 */
		if (result) { /* short circult */
			/*
			 * there is no reason to continue checking if the previous results are false
			 */
			for (final Role role : organization.getRoles()) {
				/*
				 * every role requires at least one capability
				 */
				result &= role.getRequiresSet().size() > 0;
				if (!result) { /* short circuit */
					/*
					 * can stop checking because there is a role that does not require at least one capability
					 */
					break;
				}
				for (final Capability capability : role.getRequiresSet()) {
					result &= organization.getCapability(capability.getIdentifier()) != null;
					if (!result) { /* short circuit */
						/*
						 * can stop checking because there is at least one capability required by a role that is not in the organization
						 */
						break;
					}
				}
				if (!result) { /* short circuit */
					/*
					 * can stop checking because there is at least one capability required by a role that is not in the organization
					 */
					break;
				}
			}
			if (result) { /* short circuit */
				/*
				 * there is no reason to continue checking if the previous results are false
				 */
				for (final Agent agent : organization.getAgents()) {
					for (final Capability capability : agent.getPossessesSet()) {
						result &= organization.getCapability(capability.getIdentifier()) != null;
						if (!result) { /* short circuit */
							/*
							 * can stop checking because there is at least one capability possessed by an agent that is not in the organization
							 */
							break;
						}
					}
					if (!result) { /* short circuit */
						/*
						 * can stop checking because there is at least one capability possessed by an agent that is not in the organization
						 */
						break;
					}
				}
			}
		}
		return result;
	}

}
