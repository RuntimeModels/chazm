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
import org.models.organization.registry.ChangeManager;
import org.models.organization.registry.EventRegistry;
import org.models.organization.relation.Achieves;
import org.models.organization.relation.AchievesRelation;
import org.models.organization.relation.Assignment;
import org.models.organization.relation.Task;
import org.models.organization.relation.TaskRelation;

/**
 * The {@linkplain OrganizationImpl} class is an implementation of the {@link Organization}.
 *
 * @author Scott Harmon
 * @author Christopher Zhong
 * @since 1.0
 */
public class OrganizationImpl implements Organization {
	/**
	 * The {@linkplain Entities} class is an data class that encapsulates the entities within an {@linkplain Organization}.
	 *
	 * @author Christopher Zhong
	 * @since 7.0.0
	 */
	private static final class Entities {
		/**
		 * The set of {@linkplain SpecificationGoal} in this {@linkplain Organization}.
		 */
		private final Map<UniqueId, SpecificationGoal> specificationGoals = new ConcurrentHashMap<>();

		/**
		 * The set of {@linkplain Role} in this {@linkplain Organization}.
		 */
		private final Map<UniqueId, Role> roles = new ConcurrentHashMap<>();

		/**
		 * The set of {@linkplain Agent} in this {@linkplain Organization}.
		 */
		private final Map<UniqueId, Agent> agents = new ConcurrentHashMap<>();

		/**
		 * The set of {@linkplain Capability} in this {@linkplain Organization}.
		 */
		private final Map<UniqueId, Capability> capabilities = new ConcurrentHashMap<>();

		/**
		 * The set of {@linkplain Policy} in this {@linkplain Organization}.
		 */
		private final Map<UniqueId, Policy> policies = new ConcurrentHashMap<>();

		/**
		 * The set of {@linkplain InstanceGoal} in this {@linkplain Organization}.
		 */
		private final Map<UniqueId, InstanceGoal<?>> instanceGoals = new ConcurrentHashMap<>();

		/**
		 * The set of {@linkplain InstanceGoal} in this {@linkplain Organization} that is indexed by the {@linkplain SpecificationGoal}.
		 */
		private final Map<UniqueId, Map<UniqueId, InstanceGoal<?>>> instanceGoalsBySpecificationGoal = new ConcurrentHashMap<>();

		/**
		 * The set of {@linkplain Attribute} in this {@linkplain Organization}.
		 */
		private final Map<UniqueId, Attribute> attributes = new ConcurrentHashMap<>();

		/**
		 * The set of {@linkplain TaskRelation} in this {@linkplain Organization}.
		 */
		private final Map<UniqueId, Task> taskRelations = new ConcurrentHashMap<>();

		/**
		 * The set of {@linkplain PerformanceFunction} in this {@linkplain Organization}.
		 */
		private final Map<UniqueId, PerformanceFunction> performanceFunctions = new ConcurrentHashMap<>();

		/**
		 * The set of {@linkplain Characteristic} in this {@linkplain Organization}.
		 */
		private final Map<UniqueId, Characteristic> characteristics = new ConcurrentHashMap<>();
	}

	/**
	 * The {@linkplain Relations} is a data class that encapsulates the relations that are within an {@linkplain Organization}.
	 *
	 * @author Christopher Zhong
	 * @since 7.0.0
	 */
	private static final class Relations {
		/**
		 * The set of {@linkplain Assignment} in this {@linkplain Organization}.
		 */
		private final Map<UniqueId, Assignment> assignments = new ConcurrentHashMap<>();

		/**
		 * The set of {@linkplain Assignment} in this {@linkplain Organization} that is indexed by the {@linkplain Agent}.
		 */
		private final Map<UniqueId, Map<UniqueId, Assignment>> assignmentsByAgent = new ConcurrentHashMap<>();

		/**
		 * Contains a set of {@linkplain Role}s and the {@linkplain SpecificationGoal}s that are achieved by each {@linkplain Role}.
		 */
		private final Map<UniqueId, Map<UniqueId, Achieves>> achieves = new ConcurrentHashMap<>();

		/**
		 * Contains a set of {@linkplain SpecificationGoal}s and the {@linkplain Role}s that achieve each {@linkplain SpecificationGoal}.
		 */
		private final Map<UniqueId, Map<UniqueId, Achieves>> achievedBy = new ConcurrentHashMap<>();
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
	 * Constructs a new instance of an {@linkplain Organization}.
	 */
	public OrganizationImpl() {
	}

	@Override
	public final void addSpecificationGoal(final SpecificationGoal goal) {
		if (goal == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL,"specificationGoal"));
		}
		if (entities.specificationGoals.containsKey(goal.getId())) {
			throw new IllegalArgumentException(String.format("Specification goal (%s) already exists", goal));
		}
		entities.specificationGoals.put(goal.getId(), goal);
		entities.instanceGoalsBySpecificationGoal.put(goal.getId(), new ConcurrentHashMap<UniqueId, InstanceGoal<?>>());

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifySpecificationGoalAdded(goal.getId());
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
	public final SpecificationGoal getSpecificationGoal(final UniqueId goalIdentifier) {
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
	public final void removeSpecificationGoal(final UniqueId goalIdentifier) {
		if (goalIdentifier == null) {
			throw new IllegalArgumentException("Parameter (goalIdentifier) cannot be null");
		}
		final SpecificationGoal specificationGoal = entities.specificationGoals.remove(goalIdentifier);
		entities.instanceGoalsBySpecificationGoal.remove(goalIdentifier);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifySpecificationGoalRemoved(specificationGoal.getId());
		}
	}

	@Override
	public final void removeSpecificationGoals(final Collection<UniqueId> goalIdentifiers) {
		if (goalIdentifiers == null) {
			throw new IllegalArgumentException("Parameter (goalIdentifiers) cannot be null");
		}
		for (final UniqueId specificationGoalIdentifier : goalIdentifiers) {
			removeSpecificationGoal(specificationGoalIdentifier);
		}
	}

	@Override
	public final void removeSpecificationGoals(final UniqueId... goalIdentifiers) {
		if (goalIdentifiers == null) {
			throw new IllegalArgumentException("Parameter (goalIdentifiers) cannot be null");
		}
		for (final UniqueId specificationGoalIdentifier : goalIdentifiers) {
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
		if (entities.roles.containsKey(role.getId())) {
			throw new IllegalArgumentException(String.format("Role (%s) already exists", role));
		}
		entities.roles.put(role.getId(), role);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyRoleAdded(role.getId());
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
	public final Role getRole(final UniqueId roleIdentifier) {
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
	public final void removeRole(final UniqueId roleIdentifier) {
		if (roleIdentifier == null) {
			throw new IllegalArgumentException("Parameter (roleIdentifier) cannot be null");
		}
		final Role role = entities.roles.remove(roleIdentifier);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyRoleRemoved(role.getId());
		}
	}

	@Override
	public final void removeRoles(final Collection<UniqueId> roleIdentifiers) {
		if (roleIdentifiers == null) {
			throw new IllegalArgumentException("Parameter (roleIdentifiers) cannot be null");
		}
		for (final UniqueId roleIdentifier : roleIdentifiers) {
			removeRole(roleIdentifier);
		}
	}

	@Override
	public final void removeRoles(final UniqueId... roleIdentifiers) {
		if (roleIdentifiers == null) {
			throw new IllegalArgumentException("Parameter (roleIdentifiers) cannot be null");
		}
		for (final UniqueId roleIdentifier : roleIdentifiers) {
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
		if (entities.agents.containsKey(agent.getId())) {
			throw new IllegalArgumentException(String.format("Agent (%s) already exists", agent));
		}
		entities.agents.put(agent.getId(), agent);
		final Map<UniqueId, Assignment> map = new ConcurrentHashMap<>();
		relations.assignmentsByAgent.put(agent.getId(), map);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyAgentAdded(agent.getId());
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
	public final Agent getAgent(final UniqueId agentIdentifier) {
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
	public final void removeAgent(final UniqueId agentIdentifier) {
		if (agentIdentifier == null) {
			throw new IllegalArgumentException("Parameter (agentIdentifier) cannot be null");
		}
		final Agent agent = entities.agents.remove(agentIdentifier);
		relations.assignmentsByAgent.remove(agentIdentifier);

		if (agent != null) {
			final ChangeManager changeManager = EventRegistry.get();
			if (changeManager != null) {
				changeManager.notifyAgentRemoved(agent.getId());
			}
		}
	}

	@Override
	public final void removeAgents(final Collection<UniqueId> agentIdentifiers) {
		if (agentIdentifiers == null) {
			throw new IllegalArgumentException("Parameter (agentIdentifiers) cannot be null");
		}
		for (final UniqueId agentIdentifier : agentIdentifiers) {
			removeAgent(agentIdentifier);
		}
	}

	@Override
	public final void removeAgents(final UniqueId... agentIdentifiers) {
		if (agentIdentifiers == null) {
			throw new IllegalArgumentException("Parameter (agentIdentifiers) cannot be null");
		}
		for (final UniqueId agentIdentifier : agentIdentifiers) {
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
		if (entities.capabilities.containsKey(capability.getId())) {
			throw new IllegalArgumentException(String.format("Capability (%s) already exists", capability));
		}
		entities.capabilities.put(capability.getId(), capability);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyCapabilityAdded(capability.getId());
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
	public final Capability getCapability(final UniqueId capabilityIdentifier) {
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
	public final void removeCapability(final UniqueId capabilityIdentifier) {
		if (capabilityIdentifier == null) {
			throw new IllegalArgumentException("Parameter (capabilityIdentifier) cannot be null");
		}
		final Capability capability = entities.capabilities.remove(capabilityIdentifier);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyCapabilityRemoved(capability.getId());
		}
	}

	@Override
	public final void removeCapabilities(final Collection<UniqueId> capabilityIdentifiers) {
		if (capabilityIdentifiers == null) {
			throw new IllegalArgumentException("Parameter (capabilityIdentifiers) cannot be null");
		}
		for (final UniqueId capabilityIdentifier : capabilityIdentifiers) {
			removeCapability(capabilityIdentifier);
		}
	}

	@Override
	public final void removeCapabilities(final UniqueId... capabilityIdentifiers) {
		if (capabilityIdentifiers == null) {
			throw new IllegalArgumentException("Parameter (capabilityIdentifiers) cannot be null");
		}
		for (final UniqueId capabilityIdentifier : capabilityIdentifiers) {
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
		if (entities.policies.containsKey(policy.getId())) {
			throw new IllegalArgumentException(String.format("Policy (%s) already exists", policy));
		}
		entities.policies.put(policy.getId(), policy);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyPolicyAdded(policy.getId());
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
	public final Policy getPolicy(final UniqueId policyIdentifier) {
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
	public final void removePolicy(final UniqueId policyIdentifier) {
		if (policyIdentifier == null) {
			throw new IllegalArgumentException("Parameter (policyIdentifier) cannot be null");
		}
		final Policy policy = entities.policies.remove(policyIdentifier);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyPolicyRemoved(policy.getId());
		}
	}

	@Override
	public final void removePolicies(final Collection<UniqueId> policyIdentifiers) {
		if (policyIdentifiers == null) {
			throw new IllegalArgumentException("Parameter (policyIdentifiers) cannot be null");
		}
		for (final UniqueId policyIdentifer : policyIdentifiers) {
			removePolicy(policyIdentifer);
		}
	}

	@Override
	public final void removePolicies(final UniqueId... policyIdentifiers) {
		if (policyIdentifiers == null) {
			throw new IllegalArgumentException("Parameter (policyIdentifiers) cannot be null");
		}
		for (final UniqueId policyIdentifer : policyIdentifiers) {
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
		if (entities.instanceGoals.containsKey(instanceGoal.getId())) {
			throw new IllegalArgumentException(String.format("Instance goal (%s) already exists", instanceGoal.getId()));
		}
		entities.instanceGoals.put(instanceGoal.getId(), instanceGoal);
		entities.instanceGoalsBySpecificationGoal.get(instanceGoal.getSpecificationGoal().getId()).put(instanceGoal.getInstanceId(), instanceGoal);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyInstanceGoalAdded(instanceGoal.getId(), instanceGoal.getParameter());
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
	public final InstanceGoal<?> getInstanceGoal(final UniqueId goalIdentifier) {
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
	public final void removeInstanceGoal(final UniqueId goalIdentifier) {
		if (goalIdentifier == null) {
			throw new IllegalArgumentException("Parameter (goalIdentifier) cannot be null");
		}
		final InstanceGoal<?> instanceGoal = entities.instanceGoals.remove(goalIdentifier);
		entities.instanceGoalsBySpecificationGoal.get(instanceGoal.getSpecificationGoal().getId()).remove(instanceGoal.getInstanceId());

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyInstanceGoalRemoved(instanceGoal.getId());
		}
	}

	@Override
	public final void removeInstanceGoals(final Collection<UniqueId> goalIdentifiers) {
		if (goalIdentifiers == null) {
			throw new IllegalArgumentException("Parameter (goalIdentifiers) cannot be null");
		}
		for (final UniqueId instanceGoalIdentifier : goalIdentifiers) {
			removeInstanceGoal(instanceGoalIdentifier);
		}
	}

	@Override
	public final void removeInstanceGoals(final UniqueId... goalIdentifiers) {
		if (goalIdentifiers == null) {
			throw new IllegalArgumentException("Parameter (goalIdentifiers) cannot be null");
		}
		for (final UniqueId instanceGoalIdentifier : goalIdentifiers) {
			removeInstanceGoal(instanceGoalIdentifier);
		}
	}

	@Override
	public final void removeAllInstanceGoals() {
		entities.instanceGoals.clear();
		for (final Map<UniqueId, InstanceGoal<?>> map : entities.instanceGoalsBySpecificationGoal.values()) {
			map.clear();
		}
	}

	@Override
	public final void addAttribute(final Attribute attribute) {
		if (attribute == null) {
			throw new IllegalArgumentException("Parameter (attribute) cannot be null");
		}
		if (entities.attributes.containsKey(attribute.getId())) {
			throw new IllegalArgumentException(String.format("Attribute (%s) already exists", attribute));
		}
		entities.attributes.put(attribute.getId(), attribute);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyAttributeAdded(attribute.getId());
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
	public final Attribute getAttribute(final UniqueId attributeIdentifier) {
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
	public final void removeAttribute(final UniqueId attributeIdentifier) {
		if (attributeIdentifier == null) {
			throw new IllegalArgumentException("Parameter (attributeIdentifier) cannot be null");
		}
		final Attribute attribute = entities.attributes.remove(attributeIdentifier);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyAttributeRemoved(attribute.getId());
		}
	}

	@Override
	public final void removeAttributes(final Collection<UniqueId> attributeIdentifiers) {
		if (attributeIdentifiers == null) {
			throw new IllegalArgumentException("Parameter (attributeIdentifiers) cannot be null");
		}
		for (final UniqueId attributeIdentifier : attributeIdentifiers) {
			removeAttribute(attributeIdentifier);
		}
	}

	@Override
	public final void removeAttributes(final UniqueId... attributeIdentifiers) {
		if (attributeIdentifiers == null) {
			throw new IllegalArgumentException("Parameter (attributeIdentifiers) cannot be null");
		}
		for (final UniqueId attributeIdentifier : attributeIdentifiers) {
			removeAttribute(attributeIdentifier);
		}
	}

	@Override
	public final void removeAllAttributes() {
		entities.attributes.clear();
	}

	@Override
	public final void addTask(final Task taskRelation) {
		if (taskRelation == null) {
			throw new IllegalArgumentException("Parameter (task) cannot be null");
		}
		if (entities.taskRelations.containsKey(taskRelation.getId())) {
			throw new IllegalArgumentException(String.format("Task (%s) already exists", taskRelation));
		}
		entities.taskRelations.put(taskRelation.getId(), taskRelation);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyTaskAdded(taskRelation.getId());
		}
	}

	@Override
	public final void addTasks(final Collection<Task> taskRelations) {
		if (taskRelations == null) {
			throw new IllegalArgumentException("Parameter (tasks) cannot be null");
		}
		for (final Task taskRelation : taskRelations) {
			addTask(taskRelation);
		}
	}

	@Override
	public final void addTasks(final Task... tasks) {
		if (tasks == null) {
			throw new IllegalArgumentException("Parameter (tasks) cannot be null");
		}
		for (final Task taskRelation : tasks) {
			addTask(taskRelation);
		}
	}

	@Override
	public Task getTask(final UniqueId taskIdentifier) {
		if (taskIdentifier == null) {
			throw new IllegalArgumentException("Parameter (taskIdentifier) cannot be null");
		}
		return entities.taskRelations.get(taskIdentifier);
	}

	@Override
	public final Set<Task> getTasks() {
		return new HashSet<>(entities.taskRelations.values());
	}

	@Override
	public final void removeTask(final UniqueId taskIdentifier) {
		if (taskIdentifier == null) {
			throw new IllegalArgumentException("Parameter (taskIdentifier) cannot be null");
		}
		final Task taskRelation = entities.taskRelations.remove(taskIdentifier);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyTaskRemoved(taskRelation.getId());
		}
	}

	@Override
	public final void removeAllTasks() {
		entities.taskRelations.clear();
	}

	@Override
	public final void addPerformanceFunction(final PerformanceFunction performanceFunction) {
		if (performanceFunction == null) {
			throw new IllegalArgumentException("Parameter (performanceFunction) cannot be null");
		}
		if (entities.performanceFunctions.containsKey(performanceFunction.getId())) {
			throw new IllegalArgumentException(String.format("Performance function (%s) already exists", performanceFunction));
		}
		entities.performanceFunctions.put(performanceFunction.getId(), performanceFunction);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyPerformanceFunctionAdded(performanceFunction.getId());
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
	public PerformanceFunction getPerformanceFunction(final UniqueId performanceFunctionIdentifier) {
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
	public final void removePerformanceFunction(final UniqueId performanceFunctionIdentifier) {
		if (performanceFunctionIdentifier == null) {
			throw new IllegalArgumentException("Parameter (performanceFunctionIdentifier) cannot be null");
		}
		final PerformanceFunction performanceFunction = entities.performanceFunctions.remove(performanceFunctionIdentifier);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyPerformanceFunctionRemoved(performanceFunction.getId());
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
		if (entities.characteristics.containsKey(characteristic.getId())) {
			throw new IllegalArgumentException(String.format("Characteristic (%s) already exists", characteristic));
		}
		entities.characteristics.put(characteristic.getId(), characteristic);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyCharacteristicAdded(characteristic.getId());
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
	public final Characteristic getCharacteristic(final UniqueId characteristicIdentifier) {
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
	public final void removeCharacteristic(final UniqueId characteristicIdentifier) {
		if (characteristicIdentifier == null) {
			throw new IllegalArgumentException("Parameter (characteristicIdentifier) cannot be null");
		}
		if (entities.characteristics.containsKey(characteristicIdentifier)) {
			final Characteristic characteristic = entities.characteristics.remove(characteristicIdentifier);

			final ChangeManager changeManager = EventRegistry.get();
			if (changeManager != null) {
				changeManager.notifyCharacteristicRemoved(characteristic.getId());
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
		if (relations.assignments.containsKey(assignment.getId())) {
			throw new IllegalArgumentException(String.format("Assignment (%s) already exists", assignment));
		}
		relations.assignments.put(assignment.getId(), assignment);
		relations.assignmentsByAgent.get(assignment.getAgent().getId()).put(assignment.getId(), assignment);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyAssignmentAdded(assignment.getAgent().getId(), assignment.getRole().getId(), assignment.getGoal().getId());
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
	public final Assignment getAssignment(final UniqueId assignmentIdentifier) {
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
	public final Set<Assignment> getAssignmentsOfAgent(final UniqueId agentIdentifier) {
		if (agentIdentifier == null) {
			throw new IllegalArgumentException("Parameter (agentIdentifier) cannot be null");
		}
		final Set<Assignment> results = new HashSet<>();
		final Map<UniqueId, Assignment> map = relations.assignmentsByAgent.get(agentIdentifier);
		if (map != null) {
			results.addAll(map.values());
		}
		return results;
	}

	@Override
	public final void removeAssignment(final UniqueId assignmentIdentifier) {
		if (assignmentIdentifier == null) {
			throw new IllegalArgumentException("Parameter (assignmentIdentifier) cannot be null");
		}
		final Assignment assignmentRelation = relations.assignments.remove(assignmentIdentifier);
		relations.assignmentsByAgent.get(assignmentRelation.getAgent().getId()).remove(assignmentIdentifier);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyAssignmentRemoved(assignmentRelation.getAgent().getId(), assignmentRelation.getRole().getId(), assignmentRelation.getGoal()
					.getId());
		}
	}

	@Override
	public final void removeAssignments(final Collection<UniqueId> assignmentIdentifiers) {
		if (assignmentIdentifiers == null) {
			throw new IllegalArgumentException("Parameter (assignmentIdentifiers) cannot be null");
		}
		for (final UniqueId assignmentIdentifier : assignmentIdentifiers) {
			removeAssignment(assignmentIdentifier);
		}
	}

	@Override
	public final void removeAssignments(final UniqueId... assignmentIdentifiers) {
		if (assignmentIdentifiers == null) {
			throw new IllegalArgumentException("Parameter (assignmentIdentifiers) cannot be null");
		}
		for (final UniqueId assignmentIdentifier : assignmentIdentifiers) {
			removeAssignment(assignmentIdentifier);
		}
	}

	@Override
	public final void removeAllAssignments() {
		relations.assignments.clear();
		for (final Map<UniqueId, Assignment> map : relations.assignmentsByAgent.values()) {
			map.clear();
		}
	}

	@Override
	public final void addAchievesRelation(final UniqueId roleId, final UniqueId specGoalId) {
		final Role role = getRole(roleId);
		if (role == null) {
			throw new IllegalArgumentException(String.format("Role (%s) does not exists", roleId));
		}
		final SpecificationGoal specGoal = getSpecificationGoal(specGoalId);
		if (specGoal == null) {
			throw new IllegalArgumentException(String.format("Specification goal (%s) does not exists", specGoalId));
		}
		final Map<UniqueId, Achieves> achieves = relations.achieves.computeIfAbsent(roleId, v -> new ConcurrentHashMap<>());
		/* if the relation already exists do nothing */
		if (achieves.containsKey(specGoalId)) {
			return;
		}
		final Achieves achievesRelation = new AchievesRelation(role, specGoal);
		achieves.put(specGoalId, achievesRelation);
		final Map<UniqueId, Achieves> achievedBy = relations.achievedBy.computeIfAbsent(specGoalId, v -> new ConcurrentHashMap<>());
		achievedBy.put(roleId, achievesRelation);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyAchievesAdded(roleId, specGoalId);
		}

	}

	@Override
	public final void removeAchievesRelation(final UniqueId roleIdentifier, final UniqueId goalIdentifier) {
		final Role role = getRole(roleIdentifier);
		final SpecificationGoal goal = getSpecificationGoal(goalIdentifier);
		if (role == null || goal == null) {
			throw new IllegalArgumentException(String.format("Both role (%s=%s) and goal (%s=%s) must exists", roleIdentifier, role, goalIdentifier, goal));
		}
		role.removeAchieves(goal.getId());
	}

	@Override
	public final void addRequiresRelation(final UniqueId roleIdentifier, final UniqueId capabilityIdentifier) {
		final Role role = getRole(roleIdentifier);
		final Capability capability = getCapability(capabilityIdentifier);
		if (role == null || capability == null) {
			throw new IllegalArgumentException(String.format("Both role (%s=%s) and capability (%s=%s) must exists", roleIdentifier, role,
					capabilityIdentifier, capability));
		}
		role.addRequires(capability);
	}

	@Override
	public final void removeRequiresRelation(final UniqueId roleIdentifier, final UniqueId capabilityIdentifier) {
		final Role role = getRole(roleIdentifier);
		final Capability capability = getCapability(capabilityIdentifier);
		if (role == null || capability == null) {
			throw new IllegalArgumentException(String.format("Both role (%s=%s) and capability (%s=%s) must exists", roleIdentifier, role,
					capabilityIdentifier, capability));
		}
		role.removeRequires(capability.getId());
	}

	@Override
	public final void addPossessesRelation(final UniqueId agentIdentifier, final UniqueId capabilityIdentifier, final double score) {
		final Agent agent = getAgent(agentIdentifier);
		final Capability capability = getCapability(capabilityIdentifier);
		if (agent == null || capability == null) {
			throw new IllegalArgumentException(String.format("Both agent (%s=%s) and capability (%s=%s) must exists", agentIdentifier, agent,
					capabilityIdentifier, capability));
		}
		agent.addPossesses(capability, score);
	}

	@Override
	public final void removePossessesRelation(final UniqueId agentIdentifier, final UniqueId capabilityIdentifier) {
		final Agent agent = getAgent(agentIdentifier);
		final Capability capability = getCapability(capabilityIdentifier);
		if (agent == null || capability == null) {
			throw new IllegalArgumentException(String.format("Both agent (%s=%s) and capability (%s=%s) must exists", agentIdentifier, agent,
					capabilityIdentifier, capability));
		}
		agent.removePossesses(capabilityIdentifier);
	}

	@Override
	public final void updatePossessesRelation(final UniqueId agentIdentifier, final UniqueId capabilityIdentifier, final double score) {
		final Agent agent = getAgent(agentIdentifier);
		final Capability capability = getCapability(capabilityIdentifier);
		if (agent == null || capability == null) {
			throw new IllegalArgumentException(String.format("Both agent (%s=%s) and capability (%s=%s) must exists", agentIdentifier, agent,
					capabilityIdentifier, capability));
		}
		agent.setPossessesScore(capabilityIdentifier, score);
	}

	@Override
	public final void addNeedsRelation(final UniqueId roleIdentifier, final UniqueId attributeIdentifier) {
		final Role role = getRole(roleIdentifier);
		final Attribute attribute = getAttribute(attributeIdentifier);
		if (role == null || attribute == null) {
			throw new IllegalArgumentException(String.format("Both role (%s=%s) and attribute (%s=%s) must exists", roleIdentifier, role, attributeIdentifier,
					attribute));
		}
		role.addNeeds(attribute);
	}

	@Override
	public final void removeNeedsRelation(final UniqueId roleIdentifier, final UniqueId attributeIdentifier) {
		final Role role = getRole(roleIdentifier);
		final Attribute attribute = getAttribute(attributeIdentifier);
		if (role == null || attribute == null) {
			throw new IllegalArgumentException(String.format("Both role (%s=%s) and attribute (%s=%s) must exists", roleIdentifier, role, attributeIdentifier,
					attribute));
		}
		role.removeNeeds(attributeIdentifier);
	}

	@Override
	public final void addHasRelation(final UniqueId agentIdentifier, final UniqueId attributeIdentifier, final double value) {
		final Agent agent = getAgent(agentIdentifier);
		final Attribute attribute = getAttribute(attributeIdentifier);
		if (agent == null || attribute == null) {
			throw new IllegalArgumentException(String.format("Both agent (%s=%s) and attribute (%s=%s) must exists", agentIdentifier, agent,
					attributeIdentifier, attribute));
		}
		agent.addHas(attribute, value);
	}

	@Override
	public final void removeHasRelation(final UniqueId agentIdentifier, final UniqueId attributeIdentifier) {
		final Agent agent = getAgent(agentIdentifier);
		final Attribute attribute = getAttribute(attributeIdentifier);
		if (agent == null || attribute == null) {
			throw new IllegalArgumentException(String.format("Both agent (%s=%s) and attribute (%s=%s) must exists", agentIdentifier, agent,
					attributeIdentifier, attribute));
		}
		agent.removeHas(attributeIdentifier);
	}

	@Override
	public final void updateHasRelation(final UniqueId agentIdentifier, final UniqueId attributeIdentifier, final double score) {
		final Agent agent = getAgent(agentIdentifier);
		final Attribute attribute = getAttribute(attributeIdentifier);
		if (agent == null || attribute == null) {
			throw new IllegalArgumentException(String.format("Both agent (%s=%s) and attribute (%s=%s) must exists", agentIdentifier, agent,
					attributeIdentifier, attribute));
		}
		agent.setHasValue(attributeIdentifier, score);
	}

	@Override
	public final void addUsesRelation(final UniqueId roleIdentifier, final UniqueId functionIdentifier) {
		final Role role = getRole(roleIdentifier);
		final PerformanceFunction performanceFunction = getPerformanceFunction(functionIdentifier);
		if (role == null || performanceFunction == null) {
			throw new IllegalArgumentException(String.format("Both role (%s=%s) and performance function (%s=%s) must exists", roleIdentifier, role,
					functionIdentifier, performanceFunction));
		}
		role.addUses(performanceFunction);
	}

	@Override
	public final void removeUsesRelation(final UniqueId roleIdentifier, final UniqueId functionIdentifier) {
		final Role role = getRole(roleIdentifier);
		final PerformanceFunction performanceFunction = getPerformanceFunction(functionIdentifier);
		if (role == null || performanceFunction == null) {
			throw new IllegalArgumentException(String.format("Both role (%s=%s) and performance function (%s=%s) must exists", roleIdentifier, role,
					functionIdentifier, performanceFunction));
		}
		role.removeUses(functionIdentifier);
	}

	@Override
	public final void setModeratesRelation(final UniqueId performanceFunctionIdentifier, final UniqueId attributeIdentifier) {
		final PerformanceFunction performanceFunction = getPerformanceFunction(performanceFunctionIdentifier);
		final Attribute attribute = getAttribute(attributeIdentifier);
		if (performanceFunction == null || attribute == null) {
			throw new IllegalArgumentException(String.format("Both performance function (%s=%s) and attribute (%s=%s) must exists",
					performanceFunctionIdentifier, performanceFunction, attributeIdentifier, attribute));
		}
		performanceFunction.setModerates(attribute);

	}

	@Override
	public final void addContainsRelation(final UniqueId roleIdentifier, final UniqueId characteristicIdentifier, final double value) {
		final Role role = getRole(roleIdentifier);
		final Characteristic characteristic = getCharacteristic(characteristicIdentifier);
		if (role == null || characteristic == null) {
			throw new IllegalArgumentException(String.format("Both role (%s=%s) and characteristic (%s=%s) must exists", roleIdentifier, role,
					characteristicIdentifier, characteristic));
		}
		role.addContains(characteristic, value);
	}

	@Override
	public final void removeContainsRelation(final UniqueId roleIdentifier, final UniqueId characteristicIdentifier) {
		final Role role = getRole(roleIdentifier);
		final Characteristic characteristic = getCharacteristic(characteristicIdentifier);
		if (role == null || characteristic == null) {
			throw new IllegalArgumentException(String.format("Both role (%s=%s) and characteristic (%s=%s) must exists", roleIdentifier, role,
					characteristicIdentifier, characteristic));
		}
		role.removeContains(characteristicIdentifier);
	}

	@Override
	public final void updateContainsRelation(final UniqueId roleIdentifier, final UniqueId characteristicIdentifier, final double value) {
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
				isAchievable |= organization.getRole(role.getId()) != null;
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
					result &= organization.getCapability(capability.getId()) != null;
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
						result &= organization.getCapability(capability.getId()) != null;
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
