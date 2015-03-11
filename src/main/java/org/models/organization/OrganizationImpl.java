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
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.models.organization.entity.Agent;
import org.models.organization.entity.Attribute;
import org.models.organization.entity.Capability;
import org.models.organization.entity.Characteristic;
import org.models.organization.entity.InstanceGoal;
import org.models.organization.entity.Pmf;
import org.models.organization.entity.Policy;
import org.models.organization.entity.Role;
import org.models.organization.entity.SpecificationGoal;
import org.models.organization.id.UniqueId;
import org.models.organization.registry.ChangeManager;
import org.models.organization.registry.EventRegistry;
import org.models.organization.relation.Achieves;
import org.models.organization.relation.AchievesRelation;
import org.models.organization.relation.Assignment;
import org.models.organization.relation.Contains;
import org.models.organization.relation.ContainsRelation;
import org.models.organization.relation.Has;
import org.models.organization.relation.HasRelation;
import org.models.organization.relation.Moderates;
import org.models.organization.relation.ModeratesRelation;
import org.models.organization.relation.Needs;
import org.models.organization.relation.NeedsRelation;
import org.models.organization.relation.Possesses;
import org.models.organization.relation.PossessesRelation;
import org.models.organization.relation.Requires;
import org.models.organization.relation.RequiresRelation;
import org.models.organization.relation.Task;
import org.models.organization.relation.TaskRelation;
import org.models.organization.relation.Uses;
import org.models.organization.relation.UsesRelation;

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
	private static class Entities {
		/**
		 * The set of {@linkplain SpecificationGoal} in this {@linkplain Organization}.
		 */
		private final Map<UniqueId<SpecificationGoal>, SpecificationGoal> specificationGoals = new ConcurrentHashMap<>();
		/**
		 * The set of {@linkplain Role} in this {@linkplain Organization}.
		 */
		private final Map<UniqueId<Role>, Role> roles = new ConcurrentHashMap<>();
		/**
		 * The set of {@linkplain Agent} in this {@linkplain Organization}.
		 */
		private final Map<UniqueId<Agent>, Agent> agents = new ConcurrentHashMap<>();
		/**
		 * The set of {@linkplain Capability} in this {@linkplain Organization}.
		 */
		private final Map<UniqueId<Capability>, Capability> capabilities = new ConcurrentHashMap<>();
		/**
		 * The set of {@linkplain Policy} in this {@linkplain Organization}.
		 */
		private final Map<UniqueId<Policy>, Policy> policies = new ConcurrentHashMap<>();
		/**
		 * The set of {@linkplain InstanceGoal} in this {@linkplain Organization}.
		 */
		private final Map<UniqueId<InstanceGoal<?>>, InstanceGoal<?>> instanceGoals = new ConcurrentHashMap<>();
		/**
		 * The set of {@linkplain InstanceGoal} in this {@linkplain Organization} that is indexed by the {@linkplain SpecificationGoal}.
		 */
		private final Map<UniqueId<SpecificationGoal>, Map<UniqueId<InstanceGoal<?>>, InstanceGoal<?>>> instanceGoalsBySpecificationGoal = new ConcurrentHashMap<>();
		/**
		 * The set of {@linkplain Attribute} in this {@linkplain Organization}.
		 */
		private final Map<UniqueId<Attribute>, Attribute> attributes = new ConcurrentHashMap<>();
		/**
		 * The set of {@linkplain Pmf} in this {@linkplain Organization}.
		 */
		private final Map<UniqueId<Pmf>, Pmf> pmfs = new ConcurrentHashMap<>();
		/**
		 * The set of {@linkplain Characteristic} in this {@linkplain Organization}.
		 */
		private final Map<UniqueId<Characteristic>, Characteristic> characteristics = new ConcurrentHashMap<>();
	}

	/**
	 * The {@linkplain Relations} is a data class that encapsulates the relations that are within an {@linkplain Organization}.
	 *
	 * @author Christopher Zhong
	 * @since 7.0.0
	 */
	private static class Relations {
		/**
		 * A set of {@linkplain Assignment}s.
		 */
		private final Map<UniqueId<Assignment>, Assignment> assignments = new ConcurrentHashMap<>();
		/**
		 * An {@linkplain Agent}'s set of {@linkplain Assignment}.
		 */
		private final Map<UniqueId<Agent>, Map<UniqueId<Assignment>, Assignment>> assignmentsByAgent = new ConcurrentHashMap<>();
		/**
		 * A set of {@linkplain Task}s.
		 */
		private final Map<UniqueId<Task>, Task> tasks = new ConcurrentHashMap<>();
		/**
		 * A {@linkplain Role} that achieves a set of {@linkplain SpecificationGoal}s.
		 */
		private final Map<UniqueId<Role>, Map<UniqueId<SpecificationGoal>, Achieves>> achieves = new ConcurrentHashMap<>();
		/**
		 * A {@linkplain SpecificationGoal} that is achieved by a set of {@linkplain Role}s.
		 */
		private final Map<UniqueId<SpecificationGoal>, Map<UniqueId<Role>, Achieves>> achievedBy = new ConcurrentHashMap<>();
		/**
		 * A {@linkplain Role} that requires a set of {@linkplain Capability}s.
		 */
		private final Map<UniqueId<Role>, Map<UniqueId<Capability>, Requires>> requires = new ConcurrentHashMap<>();
		/**
		 * A {@linkplain Capability} that is required by a set of {@linkplain Role}s.
		 */
		private final Map<UniqueId<Capability>, Map<UniqueId<Role>, Requires>> requiredBy = new ConcurrentHashMap<>();
		/**
		 * An {@linkplain Agent} that possesses a set of {@linkplain Capability}s.
		 */
		private final Map<UniqueId<Agent>, Map<UniqueId<Capability>, Possesses>> possesses = new ConcurrentHashMap<>();
		/**
		 * A {@linkplain Capability} that is possessed by a set of {@linkplain Agent}s.
		 */
		private final Map<UniqueId<Capability>, Map<UniqueId<Agent>, Possesses>> possessedBy = new ConcurrentHashMap<>();
		/**
		 * A {@linkplain Role} that needs a set of {@linkplain Attribute}s.
		 */
		private final Map<UniqueId<Role>, Map<UniqueId<Attribute>, Needs>> needs = new ConcurrentHashMap<>();
		/**
		 * An {@linkplain Attribute} that is needed by a set of {@linkplain Role}s.
		 */
		private final Map<UniqueId<Attribute>, Map<UniqueId<Role>, Needs>> neededBy = new ConcurrentHashMap<>();
		/**
		 * An {@linkplain Agent}s that has a set of {@linkplain Attribute}s.
		 */
		private final Map<UniqueId<Agent>, Map<UniqueId<Attribute>, Has>> has = new ConcurrentHashMap<>();
		/**
		 * An {@linkplain Attribute} that is had by a set of {@linkplain Agent}s.
		 */
		private final Map<UniqueId<Attribute>, Map<UniqueId<Agent>, Has>> hadBy = new ConcurrentHashMap<>();
		/**
		 * A {@linkplain Role} that uses a set of {@linkplain Pmf}s.
		 */
		private final Map<UniqueId<Role>, Map<UniqueId<Pmf>, Uses>> uses = new ConcurrentHashMap<>();
		/**
		 * A {@linkplain Pmf} that is used by a set of {@linkplain Role}s.
		 */
		private final Map<UniqueId<Pmf>, Map<UniqueId<Role>, Uses>> usedBy = new ConcurrentHashMap<>();
		/**
		 * A {@linkplain Pmf} that moderates an {@linkplain Attribute}.
		 */
		private final Map<UniqueId<Pmf>, Moderates> moderates = new ConcurrentHashMap<>();
		/**
		 * An {@linkplain Attribute} that is moderated by a set of {@linkplain Pmf}s.
		 */
		private final Map<UniqueId<Attribute>, Map<UniqueId<Pmf>, Moderates>> moderatedBy = new ConcurrentHashMap<>();
		/**
		 * A {@linkplain Role} that contains a set of {@linkplain Characteristic}s.
		 */
		private final Map<UniqueId<Role>, Map<UniqueId<Characteristic>, Contains>> contains = new ConcurrentHashMap<>();
		/**
		 * A {@linkplain Characteristic} that is contained by a set of {@linkplain Role}s.
		 */
		private final Map<UniqueId<Characteristic>, Map<UniqueId<Role>, Contains>> containedBy = new ConcurrentHashMap<>();
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
	public void addSpecificationGoal(final SpecificationGoal goal) {
		if (goal == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "goal"));
		}
		if (entities.specificationGoals.containsKey(goal.getId())) {
			throw new IllegalArgumentException(String.format("Specification goal (%s) already exists", goal));
		}
		entities.specificationGoals.put(goal.getId(), goal);
		entities.instanceGoalsBySpecificationGoal.put(goal.getId(), new ConcurrentHashMap<UniqueId<InstanceGoal<?>>, InstanceGoal<?>>());

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifySpecificationGoalAdded(goal.getId());
		}
	}

	@Override
	public void addSpecificationGoals(final Collection<SpecificationGoal> goals) {
		if (goals == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "goals"));
		}
		for (final SpecificationGoal goal : goals) {
			addSpecificationGoal(goal);
		}
	}

	@Override
	public void addSpecificationGoals(final SpecificationGoal... goals) {
		if (goals == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "goals"));
		}
		for (final SpecificationGoal specificationGoal : goals) {
			addSpecificationGoal(specificationGoal);
		}
	}

	@Override
	public SpecificationGoal getSpecificationGoal(final UniqueId<SpecificationGoal> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		return entities.specificationGoals.get(id);
	}

	@Override
	public Set<SpecificationGoal> getSpecificationGoals() {
		return new HashSet<>(entities.specificationGoals.values());
	}

	@Override
	public void removeSpecificationGoal(final UniqueId<SpecificationGoal> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		final SpecificationGoal goal = entities.specificationGoals.remove(id);
		entities.instanceGoalsBySpecificationGoal.remove(id);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifySpecificationGoalRemoved(goal.getId());
		}
	}

	@Override
	public void removeSpecificationGoals(final Collection<UniqueId<SpecificationGoal>> ids) {
		if (ids == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "ids"));
		}
		for (final UniqueId<SpecificationGoal> id : ids) {
			removeSpecificationGoal(id);
		}
	}

	@Override
	public void removeAllSpecificationGoals() {
		entities.specificationGoals.clear();
		entities.instanceGoalsBySpecificationGoal.clear();
	}

	@Override
	public void addRole(final Role role) {
		if (role == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "role"));
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
	public void addRoles(final Collection<Role> roles) {
		if (roles == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "roles"));
		}
		for (final Role role : roles) {
			addRole(role);
		}
	}

	@Override
	public void addRoles(final Role... roles) {
		if (roles == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "roles"));
		}
		for (final Role role : roles) {
			addRole(role);
		}
	}

	@Override
	public Role getRole(final UniqueId<Role> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		return entities.roles.get(id);
	}

	@Override
	public Set<Role> getRoles() {
		return new HashSet<>(entities.roles.values());
	}

	@Override
	public void removeRole(final UniqueId<Role> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		final Role role = entities.roles.remove(id);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyRoleRemoved(role.getId());
		}
	}

	@Override
	public void removeRoles(final Collection<UniqueId<Role>> ids) {
		if (ids == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "ids"));
		}
		for (final UniqueId<Role> id : ids) {
			removeRole(id);
		}
	}

	@Override
	public void removeAllRoles() {
		entities.roles.clear();
	}

	@Override
	public void addAgent(final Agent agent) {
		if (agent == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "agent"));
		}
		if (entities.agents.containsKey(agent.getId())) {
			throw new IllegalArgumentException(String.format("Agent (%s) already exists", agent));
		}
		entities.agents.put(agent.getId(), agent);
		final Map<UniqueId<Assignment>, Assignment> map = new ConcurrentHashMap<>();
		relations.assignmentsByAgent.put(agent.getId(), map);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyAgentAdded(agent.getId());
		}
	}

	@Override
	public void addAgents(final Collection<Agent> agents) {
		if (agents == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "agents"));
		}
		for (final Agent agent : agents) {
			addAgent(agent);
		}
	}

	@Override
	public void addAgents(final Agent... agents) {
		if (agents == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "agents"));
		}
		for (final Agent agent : agents) {
			addAgent(agent);
		}
	}

	@Override
	public Agent getAgent(final UniqueId<Agent> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		return entities.agents.get(id);
	}

	@Override
	public Set<Agent> getAgents() {
		return new HashSet<>(entities.agents.values());
	}

	@Override
	public void removeAgent(final UniqueId<Agent> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		final Agent agent = entities.agents.remove(id);
		relations.assignmentsByAgent.remove(id);

		if (agent != null) {
			final ChangeManager changeManager = EventRegistry.get();
			if (changeManager != null) {
				changeManager.notifyAgentRemoved(agent.getId());
			}
		}
	}

	@Override
	public void removeAgents(final Collection<UniqueId<Agent>> ids) {
		if (ids == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "ids"));
		}
		for (final UniqueId<Agent> id : ids) {
			removeAgent(id);
		}
	}

	@Override
	public void removeAllAgents() {
		entities.agents.clear();
		relations.assignmentsByAgent.clear();
	}

	@Override
	public void addCapability(final Capability capability) {
		if (capability == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "capability"));
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
	public void addCapabilities(final Collection<Capability> capabilities) {
		if (capabilities == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "capabilities"));
		}
		for (final Capability capability : capabilities) {
			addCapability(capability);
		}
	}

	@Override
	public void addCapabilities(final Capability... capabilities) {
		if (capabilities == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "capabilities"));
		}
		for (final Capability capability : capabilities) {
			addCapability(capability);
		}
	}

	@Override
	public Capability getCapability(final UniqueId<Capability> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		return entities.capabilities.get(id);
	}

	@Override
	public Set<Capability> getCapabilities() {
		return new HashSet<>(entities.capabilities.values());
	}

	@Override
	public void removeCapability(final UniqueId<Capability> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		final Capability capability = entities.capabilities.remove(id);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyCapabilityRemoved(capability.getId());
		}
	}

	@Override
	public void removeCapabilities(final Collection<UniqueId<Capability>> ids) {
		if (ids == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "ids"));
		}
		for (final UniqueId<Capability> id : ids) {
			removeCapability(id);
		}
	}

	@Override
	public void removeAllCapabilities() {
		entities.capabilities.clear();
	}

	@Override
	public void addPolicy(final Policy policy) {
		if (policy == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "policy"));
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
	public void addPolicies(final Collection<Policy> policies) {
		if (policies == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "policies"));
		}
		for (final Policy policy : policies) {
			addPolicy(policy);
		}
	}

	@Override
	public void addPolicies(final Policy... policies) {
		if (policies == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "policies"));
		}
		for (final Policy policy : policies) {
			addPolicy(policy);
		}
	}

	@Override
	public Policy getPolicy(final UniqueId<Policy> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		return entities.policies.get(id);
	}

	@Override
	public Set<Policy> getPolicies() {
		return new HashSet<>(entities.policies.values());
	}

	@Override
	public void removePolicy(final UniqueId<Policy> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		final Policy policy = entities.policies.remove(id);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyPolicyRemoved(policy.getId());
		}
	}

	@Override
	public void removePolicies(final Collection<UniqueId<Policy>> ids) {
		if (ids == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "ids"));
		}
		for (final UniqueId<Policy> id : ids) {
			removePolicy(id);
		}
	}

	@Override
	public void removeAllPolicies() {
		entities.policies.clear();
	}

	@Override
	public void addInstanceGoal(final InstanceGoal<?> goal) {
		if (goal == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "goal"));
		}
		if (entities.instanceGoals.containsKey(goal.getId())) {
			throw new IllegalArgumentException(String.format("Instance goal (%s) already exists", goal.getId()));
		}
		entities.instanceGoals.put(goal.getId(), goal);
		entities.instanceGoalsBySpecificationGoal.get(goal.getSpecificationGoal().getId()).put(goal.getInstanceId(), goal);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyInstanceGoalAdded(goal.getId(), goal.getParameter());
		}
	}

	@Override
	public void addInstanceGoals(final Collection<InstanceGoal<?>> goals) {
		if (goals == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "goals"));
		}
		for (final InstanceGoal<?> goal : goals) {
			addInstanceGoal(goal);
		}
	}

	@Override
	public void addInstanceGoals(final InstanceGoal<?>... goals) {
		if (goals == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "goals"));
		}
		for (final InstanceGoal<?> goal : goals) {
			addInstanceGoal(goal);
		}
	}

	@Override
	public InstanceGoal<?> getInstanceGoal(final UniqueId<InstanceGoal<?>> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		return entities.instanceGoals.get(id);
	}

	@Override
	public Set<InstanceGoal<?>> getInstanceGoals() {
		return new HashSet<>(entities.instanceGoals.values());
	}

	@Override
	public void removeInstanceGoal(final UniqueId<InstanceGoal<?>> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		final InstanceGoal<?> goal = entities.instanceGoals.remove(id);
		entities.instanceGoalsBySpecificationGoal.get(goal.getSpecificationGoal().getId()).remove(goal.getInstanceId());

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyInstanceGoalRemoved(goal.getId());
		}
	}

	@Override
	public void removeInstanceGoals(final Collection<UniqueId<InstanceGoal<?>>> ids) {
		if (ids == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "ids"));
		}
		for (final UniqueId<InstanceGoal<?>> id : ids) {
			removeInstanceGoal(id);
		}
	}

	@Override
	public void removeAllInstanceGoals() {
		entities.instanceGoals.clear();
		for (final Map<UniqueId<InstanceGoal<?>>, InstanceGoal<?>> map : entities.instanceGoalsBySpecificationGoal.values()) {
			map.clear();
		}
	}

	@Override
	public void addAttribute(final Attribute attribute) {
		if (attribute == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "attribute"));
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
	public void addAttributes(final Collection<Attribute> attributes) {
		if (attributes == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "attributes"));
		}
		for (final Attribute attribute : attributes) {
			addAttribute(attribute);
		}
	}

	@Override
	public void addAttributes(final Attribute... attributes) {
		if (attributes == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "attributes"));
		}
		for (final Attribute attribute : attributes) {
			addAttribute(attribute);
		}
	}

	@Override
	public Attribute getAttribute(final UniqueId<Attribute> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		return entities.attributes.get(id);
	}

	@Override
	public Set<Attribute> getAttributes() {
		return new HashSet<>(entities.attributes.values());
	}

	@Override
	public void removeAttribute(final UniqueId<Attribute> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		final Attribute attribute = entities.attributes.remove(id);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyAttributeRemoved(attribute.getId());
		}
	}

	@Override
	public void removeAttributes(final Collection<UniqueId<Attribute>> ids) {
		if (ids == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "ids"));
		}
		for (final UniqueId<Attribute> id : ids) {
			removeAttribute(id);
		}
	}

	@Override
	public void removeAllAttributes() {
		entities.attributes.clear();
	}

	@Override
	public void addPmf(final Pmf pmf) {
		if (pmf == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "pmf"));
		}
		if (entities.pmfs.containsKey(pmf.getId())) {
			throw new IllegalArgumentException(String.format("Performance function (%s) already exists", pmf));
		}
		entities.pmfs.put(pmf.getId(), pmf);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyPerformanceFunctionAdded(pmf.getId());
		}
	}

	@Override
	public void addPmfs(final Collection<Pmf> pmfs) {
		if (pmfs == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "pmfs"));
		}
		for (final Pmf pmf : pmfs) {
			addPmf(pmf);
		}
	}

	@Override
	public void addPmfs(final Pmf... pmfs) {
		if (pmfs == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "pmfs"));
		}
		for (final Pmf pmf : pmfs) {
			addPmf(pmf);
		}
	}

	@Override
	public Pmf getPmf(final UniqueId<Pmf> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		return entities.pmfs.get(id);
	}

	@Override
	public Set<Pmf> getPmfs() {
		return new HashSet<>(entities.pmfs.values());
	}

	@Override
	public void removePmf(final UniqueId<Pmf> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		final Pmf pmf = entities.pmfs.remove(id);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyPerformanceFunctionRemoved(pmf.getId());
		}
	}

	@Override
	public void removePmfs(final Collection<UniqueId<Pmf>> ids) {
		if (ids == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "ids"));
		}
		for (final UniqueId<Pmf> id : ids) {
			removePmf(id);
		}
	}

	@Override
	public void removeAllPmfs() {
		entities.pmfs.clear();
	}

	@Override
	public void addCharacteristic(final Characteristic characteristic) {
		if (characteristic == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "characteristic"));
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
	public void addCharacteristics(final Collection<Characteristic> characteristics) {
		if (characteristics == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "characteristics"));
		}
		for (final Characteristic characteristic : characteristics) {
			addCharacteristic(characteristic);
		}
	}

	@Override
	public void addCharacteristics(final Characteristic... characteristics) {
		if (characteristics == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "characteristics"));
		}
		for (final Characteristic characteristic : characteristics) {
			addCharacteristic(characteristic);
		}
	}

	@Override
	public Characteristic getCharacteristic(final UniqueId<Characteristic> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		return entities.characteristics.get(id);
	}

	@Override
	public Set<Characteristic> getCharacteristics() {
		return new HashSet<>(entities.characteristics.values());
	}

	@Override
	public void removeCharacteristic(final UniqueId<Characteristic> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		if (entities.characteristics.containsKey(id)) {
			final Characteristic characteristic = entities.characteristics.remove(id);

			final ChangeManager changeManager = EventRegistry.get();
			if (changeManager != null) {
				changeManager.notifyCharacteristicRemoved(characteristic.getId());
			}
		}
	}

	@Override
	public void removeCharacteristics(final Collection<UniqueId<Characteristic>> ids) {
		if (ids == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "ids"));
		}
		for (final UniqueId<Characteristic> id : ids) {
			removeCharacteristic(id);
		}
	}

	@Override
	public void removeAllCharacteristic() {
		entities.characteristics.clear();
	}

	@Override
	public void addAssignment(final Assignment assignment) {
		if (assignment == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "assignment"));
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
	public void addAssignments(final Collection<Assignment> assignments) {
		if (assignments == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "assignments"));
		}
		for (final Assignment assignment : assignments) {
			addAssignment(assignment);
		}
	}

	@Override
	public void addAssignments(final Assignment... assignments) {
		if (assignments == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "assignments"));
		}
		for (final Assignment assignment : assignments) {
			addAssignment(assignment);
		}
	}

	@Override
	public Assignment getAssignment(final UniqueId<Assignment> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		return relations.assignments.get(id);
	}

	@Override
	public Set<Assignment> getAssignments() {
		return new HashSet<>(relations.assignments.values());
	}

	@Override
	public Set<Assignment> getAgentAssignments(final UniqueId<Agent> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		final Set<Assignment> results = new HashSet<>();
		final Map<UniqueId<Assignment>, Assignment> map = relations.assignmentsByAgent.get(id);
		if (map != null) {
			results.addAll(map.values());
		}
		return results;
	}

	@Override
	public void removeAssignment(final UniqueId<Assignment> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		final Assignment assignment = relations.assignments.remove(id);
		relations.assignmentsByAgent.get(assignment.getAgent().getId()).remove(id);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyAssignmentRemoved(assignment.getAgent().getId(), assignment.getRole().getId(), assignment.getGoal().getId());
		}
	}

	@Override
	public void removeAssignments(final Collection<UniqueId<Assignment>> ids) {
		if (ids == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "ids"));
		}
		for (final UniqueId<Assignment> id : ids) {
			removeAssignment(id);
		}
	}

	@Override
	public void removeAllAssignments() {
		relations.assignments.clear();
		for (final Map<UniqueId<Assignment>, Assignment> map : relations.assignmentsByAgent.values()) {
			map.clear();
		}
	}

	private void addTask(final Task task) {
		if (task == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "task"));
		}
		if (relations.tasks.containsKey(task.getId())) {
			/* task already exists so do nothing , but this should not be happening */
			return;
		}
		relations.tasks.put(task.getId(), task);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyTaskAdded(task.getId());
		}
	}

	@Override
	public Task getTask(final UniqueId<Task> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		return relations.tasks.get(id);
	}

	@Override
	public Set<Task> getTasks() {
		return new HashSet<>(relations.tasks.values());
	}

	private void removeTask(final UniqueId<Task> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		final Task task = relations.tasks.remove(id);
		if (task == null) {
			/* task did not exists so do nothing, but this should not be happening */
			return;
		}

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyTaskRemoved(id);
		}
	}

	@Override
	public void addAchieves(final UniqueId<Role> roleId, final UniqueId<SpecificationGoal> goalId) {
		final Role role = getOrThrow(roleId, Role.class, i -> getRole(i));
		final SpecificationGoal goal = getOrThrow(goalId, SpecificationGoal.class, i -> getSpecificationGoal(i));
		final Map<UniqueId<SpecificationGoal>, Achieves> map = relations.achieves.computeIfAbsent(roleId, m -> new ConcurrentHashMap<>());
		if (map.containsKey(goalId)) {
			/* if the relation already exists do nothing */
			return;
		}
		final Achieves achieves = new AchievesRelation(role, goal);
		map.put(goalId, achieves);
		addAchievedBy(achieves);
		addTask(new TaskRelation(role, goal));

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyAchievesAdded(roleId, goalId);
		}

	}

	private void addAchievedBy(final Achieves achieves) {
		final Map<UniqueId<Role>, Achieves> map = relations.achievedBy.computeIfAbsent(achieves.getGoal().getId(), m -> new ConcurrentHashMap<>());
		map.put(achieves.getRole().getId(), achieves);
	}

	@Override
	public Set<SpecificationGoal> getAchieves(final UniqueId<Role> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		final Set<SpecificationGoal> result = new HashSet<>();
		final Map<UniqueId<SpecificationGoal>, Achieves> map = relations.achieves.get(id);
		if (map == null) {
			/* there are no achieves relation for the role */
			return result;
		}
		for (final Achieves achieves : map.values()) {
			result.add(achieves.getGoal());
		}
		return result;
	}

	@Override
	public Set<Role> getAchievedBy(final UniqueId<SpecificationGoal> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		final Set<Role> result = new HashSet<>();
		final Map<UniqueId<Role>, Achieves> map = relations.achievedBy.get(id);
		if (map == null) {
			/* there are no achieves relation for the goal */
			return result;
		}
		for (final Achieves achieves : map.values()) {
			result.add(achieves.getRole());
		}
		return result;
	}

	@Override
	public void removeAchieves(final UniqueId<Role> roleId, final UniqueId<SpecificationGoal> goalId) {
		final Map<UniqueId<SpecificationGoal>, Achieves> map = relations.achieves.get(roleId);
		if (map == null) {
			/* there are no achieves relations for the role */
			return;
		}
		if (map.remove(goalId) == null) {
			/* there is no achieves relation between the role and goal */
			return;
		}
		removeAchievedBy(goalId, roleId);
		removeTask(new TaskRelation.Id(roleId, goalId));

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyAchievesRemoved(roleId, goalId);
		}
	}

	private void removeAchievedBy(final UniqueId<SpecificationGoal> goalId, final UniqueId<Role> roleId) {
		final Map<UniqueId<Role>, Achieves> map = relations.achievedBy.get(goalId);
		if (map == null) {
			/* the relation does not exists, but this should not happen */
			return;
		}
		map.remove(roleId);
	}

	@Override
	public void removeAllAchieves() {
		relations.achieves.clear();
		relations.achievedBy.clear();
	}

	@Override
	public void addRequires(final UniqueId<Role> roleId, final UniqueId<Capability> capabilityId) {
		final Role role = getOrThrow(roleId, Role.class, i -> getRole(i));
		final Capability capability = getOrThrow(capabilityId, Capability.class, i -> getCapability(i));
		final Map<UniqueId<Capability>, Requires> map = relations.requires.computeIfAbsent(roleId, m -> new ConcurrentHashMap<>());
		if (map.containsKey(capabilityId)) {
			/* if the relation already exists do nothing */
			return;
		}
		final Requires requires = new RequiresRelation(role, capability);
		map.put(capabilityId, requires);
		addRequiredBy(requires);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyRequiresAdded(roleId, capabilityId);
		}
	}

	private void addRequiredBy(final Requires requires) {
		final Map<UniqueId<Role>, Requires> map = relations.requiredBy.computeIfAbsent(requires.getCapability().getId(), m -> new ConcurrentHashMap<>());
		map.put(requires.getRole().getId(), requires);
	}

	@Override
	public Set<Capability> getRequires(final UniqueId<Role> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		final Set<Capability> result = new HashSet<>();
		final Map<UniqueId<Capability>, Requires> map = relations.requires.get(id);
		if (map == null) {
			/* there are not requires relation for the role */
			return result;
		}
		for (final Requires requires : map.values()) {
			result.add(requires.getCapability());
		}
		return result;
	}

	@Override
	public Set<Role> getRequiredBy(final UniqueId<Capability> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		final Set<Role> result = new HashSet<>();
		final Map<UniqueId<Role>, Requires> map = relations.requiredBy.get(id);
		if (map == null) {
			/* there are no achieves relation for the goal */
			return result;
		}
		for (final Requires requires : map.values()) {
			result.add(requires.getRole());
		}
		return result;
	}

	@Override
	public void removeRequires(final UniqueId<Role> roleId, final UniqueId<Capability> capabilityId) {
		final Map<UniqueId<Capability>, Requires> map = relations.requires.get(roleId);
		if (map == null) {
			/* there are no requires relation for the role */
			return;
		}
		if (map.remove(capabilityId) == null) {
			/* there is no requires relation between the role and capability */
			return;
		}
		removeRequiredBy(capabilityId, roleId);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyRequiresRemoves(roleId, capabilityId);
		}
	}

	private void removeRequiredBy(final UniqueId<Capability> capabilityId, final UniqueId<Role> roleId) {
		final Map<UniqueId<Role>, Requires> map = relations.requiredBy.get(capabilityId);
		if (map == null) {
			/* the relation does not exists, but this should not happen */
			return;
		}
		map.remove(roleId);
	}

	@Override
	public void removeAllRequires() {
		relations.requires.clear();
		relations.requiredBy.clear();
	}

	@Override
	public void addPossesses(final UniqueId<Agent> agentId, final UniqueId<Capability> capabilityId, final double score) {
		final Agent agent = getOrThrow(agentId, Agent.class, i -> getAgent(i));
		final Capability capability = getOrThrow(capabilityId, Capability.class, i -> getCapability(i));
		final Map<UniqueId<Capability>, Possesses> map = relations.possesses.computeIfAbsent(agentId, m -> new ConcurrentHashMap<>());
		if (map.containsKey(capabilityId)) {
			/* if relation already exists do nothing */
			return;
		}
		final Possesses possesses = new PossessesRelation(agent, capability, score);
		map.put(capabilityId, possesses);
		addPossessedBy(possesses);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyPossessesAdded(agentId, capabilityId, score);
		}
	}

	private void addPossessedBy(final Possesses possesses) {
		final Map<UniqueId<Agent>, Possesses> map = relations.possessedBy.computeIfAbsent(possesses.getCapability().getId(), m -> new ConcurrentHashMap<>());
		map.put(possesses.getAgent().getId(), possesses);
	}

	@Override
	public Set<Capability> getPossesses(final UniqueId<Agent> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		final Set<Capability> result = new HashSet<>();
		final Map<UniqueId<Capability>, Possesses> map = relations.possesses.get(id);
		if (map == null) {
			return result;
		}
		for (final Possesses possesses : map.values()) {
			result.add(possesses.getCapability());
		}
		return result;
	}

	@Override
	public Set<Agent> getPossessedBy(final UniqueId<Capability> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		final Set<Agent> result = new HashSet<>();
		final Map<UniqueId<Agent>, Possesses> map = relations.possessedBy.get(id);
		if (map == null) {
			/* there are no achieves relation for the goal */
			return result;
		}
		for (final Possesses possesses : map.values()) {
			result.add(possesses.getAgent());
		}
		return result;
	}

	@Override
	public double getPossessesScore(final UniqueId<Agent> agentId, final UniqueId<Capability> capabilityId) {
		final Map<UniqueId<Capability>, Possesses> map = relations.possesses.get(agentId);
		if (map == null) {
			return 0.0;
		}
		final Possesses possesses = map.get(capabilityId);
		if (possesses == null) {
			return 0.0;
		}
		return possesses.getScore();
	}

	@Override
	public void setPossessesScore(final UniqueId<Agent> agentId, final UniqueId<Capability> capabilityId, final double score) {
		final Map<UniqueId<Capability>, Possesses> map = relations.possesses.get(agentId);
		if (map == null) {
			/* there are no possesses relation for this agent */
			return;
		}
		final Possesses possesses = map.get(capabilityId);
		if (possesses == null) {
			/* there is no possesses relation between the agent and capability */
			return;
		}
		possesses.setScore(score);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyPossessesChanged(agentId, capabilityId, score);
		}
	}

	@Override
	public void removePossesses(final UniqueId<Agent> agentId, final UniqueId<Capability> capabilityId) {
		final Map<UniqueId<Capability>, Possesses> map = relations.possesses.get(agentId);
		if (map == null) {
			/* there are no possesses relation for this agent */
			return;
		}
		if (map.remove(capabilityId) == null) {
			/* there is no possesses relation between the agent and capability */
			return;
		}
		removePossessedBy(capabilityId, agentId);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyPossessesRemoved(agentId, capabilityId);
		}
	}

	private void removePossessedBy(final UniqueId<Capability> capabilityId, final UniqueId<Agent> agentId) {
		final Map<UniqueId<Agent>, Possesses> map = relations.possessedBy.get(capabilityId);
		if (map == null) {
			/* the relation does not exists, but this should not happen */
			return;
		}
		map.remove(agentId);
	}

	@Override
	public void removeAllPossesses() {
		relations.possesses.clear();
		relations.possessedBy.clear();
	}

	@Override
	public void addNeeds(final UniqueId<Role> roleId, final UniqueId<Attribute> attributeId) {
		final Role role = getOrThrow(roleId, Role.class, i -> getRole(i));
		final Attribute attribute = getOrThrow(attributeId, Attribute.class, i -> getAttribute(i));
		final Map<UniqueId<Attribute>, Needs> map = relations.needs.computeIfAbsent(roleId, m -> new ConcurrentHashMap<>());
		if (map.containsKey(attributeId)) {
			/* if relation already exists do nothing */
			return;
		}
		final Needs needs = new NeedsRelation(role, attribute);
		map.put(attributeId, needs);
		addNeededBy(needs);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyInfluencesAdded(roleId, attributeId);
		}
	}

	private void addNeededBy(final Needs needs) {
		final Map<UniqueId<Role>, Needs> map = relations.neededBy.computeIfAbsent(needs.getAttribute().getId(), m -> new ConcurrentHashMap<>());
		map.put(needs.getRole().getId(), needs);
	}

	@Override
	public Set<Attribute> getNeeds(final UniqueId<Role> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		final Set<Attribute> result = new HashSet<>();
		final Map<UniqueId<Attribute>, Needs> map = relations.needs.get(id);
		if (map == null) {
			return result;
		}
		for (final Needs needs : map.values()) {
			result.add(needs.getAttribute());
		}
		return result;
	}

	@Override
	public Set<Role> getNeededBy(final UniqueId<Attribute> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		final Set<Role> result = new HashSet<>();
		final Map<UniqueId<Role>, Needs> map = relations.neededBy.get(id);
		if (map == null) {
			return result;
		}
		for (final Needs needs : map.values()) {
			result.add(needs.getRole());
		}
		return result;
	}

	@Override
	public void removeNeeds(final UniqueId<Role> roleId, final UniqueId<Attribute> attributeId) {
		final Map<UniqueId<Attribute>, Needs> map = relations.needs.get(roleId);
		if (map == null) {
			return;
		}
		if (map.remove(attributeId) == null) {
			return;
		}
		removeNeededBy(attributeId, roleId);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyInfluencesRemoved(roleId, attributeId);
		}
	}

	private void removeNeededBy(final UniqueId<Attribute> attributeId, final UniqueId<Role> roleId) {
		final Map<UniqueId<Role>, Needs> map = relations.neededBy.get(attributeId);
		if (map == null) {
			return;
		}
		map.remove(roleId);
	}

	@Override
	public void removeAllNeeds() {
		relations.needs.clear();
		relations.neededBy.clear();
	}

	@Override
	public void addHas(final UniqueId<Agent> agentId, final UniqueId<Attribute> attributeId, final double value) {
		final Agent agent = getOrThrow(agentId, Agent.class, i -> getAgent(i));
		final Attribute attribute = getOrThrow(attributeId, Attribute.class, i -> getAttribute(i));
		final Map<UniqueId<Attribute>, Has> map = relations.has.computeIfAbsent(agentId, m -> new ConcurrentHashMap<>());
		if (map.containsKey(attributeId)) {
			return;
		}
		final Has has = new HasRelation(agent, attribute, value);
		map.put(attributeId, has);
		addHadBy(has);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyHasAdded(agentId, attributeId, value);
		}
	}

	private void addHadBy(final Has has) {
		final Map<UniqueId<Agent>, Has> map = relations.hadBy.computeIfAbsent(has.getAttribute().getId(), m -> new ConcurrentHashMap<>());
		map.put(has.getAgent().getId(), has);
	}

	@Override
	public Set<Attribute> getHas(final UniqueId<Agent> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		final Set<Attribute> result = new HashSet<>();
		final Map<UniqueId<Attribute>, Has> map = relations.has.get(id);
		if (map == null) {
			return result;
		}
		for (final Has has : map.values()) {
			result.add(has.getAttribute());
		}
		return result;
	}

	@Override
	public Set<Agent> getHadBy(final UniqueId<Attribute> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		final Set<Agent> result = new HashSet<>();
		final Map<UniqueId<Agent>, Has> map = relations.hadBy.get(id);
		if (map == null) {
			return result;
		}
		for (final Has has : map.values()) {
			result.add(has.getAgent());
		}
		return result;
	}

	@Override
	public Double getHasValue(final UniqueId<Agent> agentId, final UniqueId<Attribute> attributeId) {
		final Map<UniqueId<Attribute>, Has> map = relations.has.get(agentId);
		if (map == null) {
			return null;
		}
		final Has has = map.get(attributeId);
		if (has == null) {
			return null;
		}
		return has.getValue();
	}

	@Override
	public void setHasValue(final UniqueId<Agent> agentId, final UniqueId<Attribute> attributeId, final double value) {
		final Map<UniqueId<Attribute>, Has> map = relations.has.get(agentId);
		if (map == null) {
			return;
		}
		final Has has = map.get(attributeId);
		if (has == null) {
			return;
		}
		has.setValue(value);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyHasChanged(agentId, attributeId, value);
		}
	}

	@Override
	public void removeHas(final UniqueId<Agent> agentId, final UniqueId<Attribute> attributeId) {
		final Map<UniqueId<Attribute>, Has> map = relations.has.get(agentId);
		if (map == null) {
			return;
		}
		if (map.remove(attributeId) == null) {
			return;
		}
		removeHadBy(attributeId, agentId);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyHasRemoved(agentId, attributeId);
		}
	}

	private void removeHadBy(final UniqueId<Attribute> attributeId, final UniqueId<Agent> agentId) {
		final Map<UniqueId<Agent>, Has> map = relations.hadBy.get(attributeId);
		if (map == null) {
			return;
		}
		map.remove(agentId);
	}

	@Override
	public void removeAllHas() {
		relations.has.clear();
		relations.hadBy.clear();
	}

	@Override
	public void addUses(final UniqueId<Role> roleId, final UniqueId<Pmf> pmfId) {
		final Role role = getOrThrow(roleId, Role.class, i -> getRole(i));
		final Pmf pmf = getOrThrow(pmfId, Pmf.class, i -> getPmf(i));
		final Map<UniqueId<Pmf>, Uses> map = relations.uses.computeIfAbsent(roleId, m -> new ConcurrentHashMap<>());
		if (map.containsKey(pmfId)) {
			return;
		}
		final Uses uses = new UsesRelation(role, pmf);
		map.put(pmfId, uses);
		addUsedBy(uses);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyLinkedAdded(roleId, pmfId);
		}
	}

	private void addUsedBy(final Uses uses) {
		final Map<UniqueId<Role>, Uses> map = relations.usedBy.computeIfAbsent(uses.getPmf().getId(), m -> new ConcurrentHashMap<>());
		map.put(uses.getRole().getId(), uses);

	}

	@Override
	public Set<Pmf> getUses(final UniqueId<Role> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		final Set<Pmf> result = new HashSet<>();
		final Map<UniqueId<Pmf>, Uses> map = relations.uses.get(id);
		if (map == null) {
			return result;
		}
		for (final Uses uses : map.values()) {
			result.add(uses.getPmf());
		}
		return result;
	}

	@Override
	public Set<Role> getUsedBy(final UniqueId<Pmf> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		final Set<Role> result = new HashSet<>();
		final Map<UniqueId<Role>, Uses> map = relations.usedBy.get(id);
		if (map == null) {
			return result;
		}
		for (final Uses uses : map.values()) {
			result.add(uses.getRole());
		}
		return result;
	}

	@Override
	public void removeUses(final UniqueId<Role> roleId, final UniqueId<Pmf> pmfId) {
		final Map<UniqueId<Pmf>, Uses> map = relations.uses.get(roleId);
		if (map == null) {
			return;
		}
		if (map.remove(pmfId) == null) {
			return;
		}
		removeUsedBy(pmfId, roleId);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyLinkedRemoved(roleId, pmfId);
		}
	}

	private void removeUsedBy(final UniqueId<Pmf> pmfId, final UniqueId<Role> roleId) {
		final Map<UniqueId<Role>, Uses> map = relations.usedBy.get(pmfId);
		if (map == null) {
			return;
		}
		map.remove(roleId);
	}

	@Override
	public void removeAllUses() {
		relations.uses.clear();
		relations.usedBy.clear();
	}

	@Override
	public void addModerates(final UniqueId<Pmf> pmfId, final UniqueId<Attribute> attributeId) {
		final Pmf pmf = getOrThrow(pmfId, Pmf.class, i -> getPmf(i));
		final Attribute attribute = getOrThrow(attributeId, Attribute.class, i -> getAttribute(i));
		if (relations.moderates.containsKey(pmfId)) {
			return;
		}
		final Moderates moderates = new ModeratesRelation(pmf, attribute);
		relations.moderates.put(pmfId, moderates);
		addModeratedBy(moderates);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyModeratesAdded(pmfId, attributeId);
		}
	}

	private void addModeratedBy(final Moderates moderates) {
		final Map<UniqueId<Pmf>, Moderates> map = relations.moderatedBy.computeIfAbsent(moderates.getAttribute().getId(), m -> new ConcurrentHashMap<>());
		map.put(moderates.getPmf().getId(), moderates);
	}

	@Override
	public Attribute getModerates(final UniqueId<Pmf> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		final Moderates moderates = relations.moderates.get(id);
		if (moderates == null) {
			return null;
		}
		return moderates.getAttribute();
	}

	@Override
	public Set<Pmf> getModeratedBy(final UniqueId<Attribute> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		final Set<Pmf> result = new HashSet<>();
		final Map<UniqueId<Pmf>, Moderates> map = relations.moderatedBy.get(id);
		if (map == null) {
			return result;
		}
		for (final Moderates moderates : map.values()) {
			result.add(moderates.getPmf());
		}
		return result;
	}

	@Override
	public void removeModerates(final UniqueId<Pmf> pmfId, final UniqueId<Attribute> attributeId) {
		final Moderates moderates = relations.moderates.get(pmfId);
		if (moderates == null) {
			return;
		}
		removedModeratedBy(attributeId, pmfId);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			// changeManager.notifyModeratesRemoved(pmfId, attributeId);
		}
	}

	private void removedModeratedBy(final UniqueId<Attribute> attributeId, final UniqueId<Pmf> pmfId) {
		final Map<UniqueId<Pmf>, Moderates> map = relations.moderatedBy.get(attributeId);
		if (map == null) {
			return;
		}
		map.remove(pmfId);
	}

	@Override
	public void removeAllModerates() {
		relations.moderates.clear();
		relations.moderatedBy.clear();
	}

	@Override
	public void addContains(final UniqueId<Role> roleId, final UniqueId<Characteristic> characteristicId, final double value) {
		final Role role = getOrThrow(roleId, Role.class, i -> getRole(i));
		final Characteristic characteristic = getOrThrow(characteristicId, Characteristic.class, i -> getCharacteristic(i));
		final Map<UniqueId<Characteristic>, Contains> map = relations.contains.computeIfAbsent(roleId, m -> new ConcurrentHashMap<>());
		if (map.containsKey(characteristicId)) {
			return;
		}
		final Contains contains = new ContainsRelation(role, characteristic, value);
		map.put(characteristicId, contains);
		addContainedBy(contains);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyContainsAdded(roleId, characteristicId, value);
		}
	}

	private void addContainedBy(final Contains contains) {
		final Map<UniqueId<Role>, Contains> map = relations.containedBy.computeIfAbsent(contains.getCharacteristic().getId(), m -> new ConcurrentHashMap<>());
		map.put(contains.getRole().getId(), contains);
	}

	@Override
	public Set<Characteristic> getContains(final UniqueId<Role> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		final Set<Characteristic> result = new HashSet<>();
		final Map<UniqueId<Characteristic>, Contains> map = relations.contains.get(id);
		if (map == null) {
			return result;
		}
		for (final Contains contains : map.values()) {
			result.add(contains.getCharacteristic());
		}
		return result;
	}

	@Override
	public Set<Role> getContainedBy(final UniqueId<Characteristic> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		final Set<Role> result = new HashSet<>();
		final Map<UniqueId<Role>, Contains> map = relations.containedBy.get(id);
		if (map == null) {
			return result;
		}
		for (final Contains contains : map.values()) {
			result.add(contains.getRole());
		}
		return result;
	}

	@Override
	public Double getContainsValue(final UniqueId<Role> roleId, final UniqueId<Characteristic> characteristicId) {
		final Map<UniqueId<Characteristic>, Contains> map = relations.contains.get(roleId);
		if (map == null) {
			return null;
		}
		final Contains contains = map.get(characteristicId);
		if (contains == null) {
			return null;
		}
		return contains.getValue();
	}

	@Override
	public void setContainsValue(final UniqueId<Role> roleId, final UniqueId<Characteristic> characteristicId, final double value) {
		final Map<UniqueId<Characteristic>, Contains> map = relations.contains.get(roleId);
		if (map == null) {
			return;
		}
		final Contains contains = map.get(characteristicId);
		if (contains == null) {
			return;
		}
		contains.setValue(value);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyContainsChanged(roleId, characteristicId, value);
		}
	}

	@Override
	public void removeContains(final UniqueId<Role> roleId, final UniqueId<Characteristic> characteristicId) {
		final Map<UniqueId<Characteristic>, Contains> map = relations.contains.get(roleId);
		if (map == null) {
			return;
		}
		if (map.remove(characteristicId) == null) {
			return;
		}
		removeContainedBy(characteristicId, roleId);

		final ChangeManager changeManager = EventRegistry.get();
		if (changeManager != null) {
			changeManager.notifyContainsRemoved(roleId, characteristicId);
		}
	}

	private void removeContainedBy(final UniqueId<Characteristic> characteristicId, final UniqueId<Role> roleId) {
		final Map<UniqueId<Role>, Contains> map = relations.containedBy.get(characteristicId);
		if (map == null) {
			return;
		}
		map.remove(roleId);
	}

	@Override
	public void removeAllContains() {
		relations.contains.clear();
		relations.containedBy.clear();
	}

	private <T> T getOrThrow(final UniqueId<T> i, final Class<T> c, final Function<UniqueId<T>, T> f) {
		final T t = f.apply(i);
		if (t == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_ENTITY_DOES_NOT_EXISTS, c.getSimpleName(), i));
		}
		return t;
	}

	<T1, T2, T3> void removeR(final UniqueId<T1> id1, final UniqueId<T2> id2, final Map<UniqueId<T1>, Map<UniqueId<T2>, T3>> m1,
			final Map<UniqueId<T2>, Map<UniqueId<T1>, T3>> m2, final BiConsumer<UniqueId<T1>, UniqueId<T2>> c) {
		final Map<UniqueId<T2>, T3> map1 = m1.get(id1);
		if (map1 == null) {
			return;
		}
		final T3 relation = map1.remove(id2);
		if (relation == null) {
			return;
		}
		final Map<UniqueId<T1>, T3> map2 = m2.get(id2);
		if (map2 == null) {
			return;
		}
		map2.remove(id1);
		c.accept(id1, id2);
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
	public static boolean isOrganizationValid(final Organization organization) {
		boolean result = true;
		/*
		 * every goal can be achieved by at least one role from the organization
		 */
		for (final SpecificationGoal goal : organization.getSpecificationGoals()) {
			boolean isAchievable = false;
			for (final Role role : organization.getAchievedBy(goal.getId())) {
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
				result &= organization.getRequires(role.getId()).size() > 0;
				if (!result) { /* short circuit */
					/*
					 * can stop checking because there is a role that does not require at least one capability
					 */
					break;
				}
				for (final Capability capability : organization.getRequires(role.getId())) {
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
					for (final Capability capability : organization.getPossesses(agent.getId())) {
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
