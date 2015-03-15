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
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.models.organization.entity.Agent;
import org.models.organization.entity.Attribute;
import org.models.organization.entity.Capability;
import org.models.organization.entity.Characteristic;
import org.models.organization.entity.InstanceGoal;
import org.models.organization.entity.Pmf;
import org.models.organization.entity.Policy;
import org.models.organization.entity.Role;
import org.models.organization.entity.SpecificationGoal;
import org.models.organization.event.Publisher;
import org.models.organization.id.Identifiable;
import org.models.organization.id.UniqueId;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@linkplain OrganizationImpl} class is an implementation of the {@link Organization}.
 *
 * @author Scott Harmon
 * @author Christopher Zhong
 * @since 1.0
 */
public class OrganizationImpl implements Organization, Publisher {
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
		private final Map<UniqueId<InstanceGoal>, InstanceGoal> instanceGoals = new ConcurrentHashMap<>();
		/**
		 * The set of {@linkplain InstanceGoal} in this {@linkplain Organization} that is indexed by the {@linkplain SpecificationGoal}.
		 */
		private final Map<UniqueId<SpecificationGoal>, Map<UniqueId<InstanceGoal>, InstanceGoal>> instanceGoalsBySpecificationGoal = new ConcurrentHashMap<>();
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

	private static final Logger logger = LoggerFactory.getLogger(Organization.class);
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
		checkNotExists(goal, "goal", entities.specificationGoals::containsKey);
		/* add the specification goal, instanceGoalsBySpecificationGoal map, achievedBy map */
		entities.specificationGoals.put(goal.getId(), goal);
		entities.instanceGoalsBySpecificationGoal.put(goal.getId(), new ConcurrentHashMap<>());
		relations.achievedBy.put(goal.getId(), new ConcurrentHashMap<>());
		publishAdd(SpecificationGoal.class, goal.getId());
	}

	@Override
	public void addSpecificationGoals(final Collection<SpecificationGoal> goals) {
		checkNotNull(goals, "goals");
		goals.parallelStream().forEach(this::addSpecificationGoal);
	}

	@Override
	public SpecificationGoal getSpecificationGoal(final UniqueId<SpecificationGoal> id) {
		checkNotNull(id, "id");
		return entities.specificationGoals.get(id);
	}

	@Override
	public Set<SpecificationGoal> getSpecificationGoals() {
		return entities.specificationGoals.values().parallelStream().collect(Collectors.toCollection(HashSet::new));
	}

	@Override
	public void removeSpecificationGoal(final UniqueId<SpecificationGoal> id) {
		checkNotNull(id, "id");
		if (entities.specificationGoals.containsKey(id)) {
			/* remove the specification goal, all associated instance goals, all associated achieves relations */
			entities.specificationGoals.remove(id);
			remove(id, entities.instanceGoalsBySpecificationGoal, "instanceGoalsBySpecificationGoal", c -> removeInstanceGoal(c), InstanceGoal.class);
			remove(id, relations.achievedBy, "achievedBy", c -> removeAchieves(c, id), Role.class);
			publishRemove(SpecificationGoal.class, id);
		}
	}

	@Override
	public void removeSpecificationGoals(final Collection<UniqueId<SpecificationGoal>> ids) {
		checkNotNull(ids, "ids");
		ids.parallelStream().forEach(this::removeSpecificationGoal);
	}

	@Override
	public void removeAllSpecificationGoals() {
		removeSpecificationGoals(entities.specificationGoals.keySet());
	}

	@Override
	public void addRole(final Role role) {
		checkNotExists(role, "role", entities.roles::containsKey);
		/* add the role, achieves map, requires map, needs map, uses, contains map */
		entities.roles.put(role.getId(), role);
		relations.achieves.put(role.getId(), new ConcurrentHashMap<>());
		relations.requires.put(role.getId(), new ConcurrentHashMap<>());
		relations.needs.put(role.getId(), new ConcurrentHashMap<>());
		relations.uses.put(role.getId(), new ConcurrentHashMap<>());
		relations.contains.put(role.getId(), new ConcurrentHashMap<>());
		publishAdd(Role.class, role.getId());
	}

	@Override
	public void addRoles(final Collection<Role> roles) {
		checkNotNull(roles, "roles");
		roles.parallelStream().forEach(this::addRole);
	}

	@Override
	public Role getRole(final UniqueId<Role> id) {
		checkNotNull(id, "id");
		return entities.roles.get(id);
	}

	@Override
	public Set<Role> getRoles() {
		return entities.roles.values().parallelStream().collect(Collectors.toCollection(HashSet::new));
	}

	@Override
	public void removeRole(final UniqueId<Role> id) {
		checkNotNull(id, "id");
		if (entities.roles.containsKey(id)) {
			/*
			 * remove role, all associated achieves relations, all associated requires relations, all associated needs relations, all associated uses relations,
			 * all associated contains relations
			 */
			entities.roles.remove(id);
			remove(id, relations.achieves, "achieves", c -> removeAchieves(id, c), SpecificationGoal.class);
			remove(id, relations.requires, "requires", c -> removeRequires(id, c), Capability.class);
			remove(id, relations.needs, "needs", c -> removeNeeds(id, c), Attribute.class);
			remove(id, relations.uses, "uses", c -> removeUses(id, c), Pmf.class);
			remove(id, relations.contains, "contains", c -> removeContains(id, c), Characteristic.class);
			publishRemove(Role.class, id);
		}
	}

	@Override
	public void removeRoles(final Collection<UniqueId<Role>> ids) {
		checkNotNull(ids, "ids");
		ids.parallelStream().forEach(this::removeRole);
	}

	@Override
	public void removeAllRoles() {
		removeRoles(entities.roles.keySet());
	}

	@Override
	public void addAgent(final Agent agent) {
		checkNotExists(agent, "agent", entities.agents::containsKey);
		/* add the agent, assignmentsByAgent map, possesses map, has map */
		entities.agents.put(agent.getId(), agent);
		relations.assignmentsByAgent.put(agent.getId(), new ConcurrentHashMap<>());
		relations.possesses.put(agent.getId(), new ConcurrentHashMap<>());
		relations.has.put(agent.getId(), new ConcurrentHashMap<>());
		publishAdd(Agent.class, agent.getId());
	}

	@Override
	public void addAgents(final Collection<Agent> agents) {
		checkNotNull(agents, "agents");
		agents.parallelStream().forEach(this::addAgent);
	}

	@Override
	public Agent getAgent(final UniqueId<Agent> id) {
		checkNotNull(id, "id");
		return entities.agents.get(id);
	}

	@Override
	public Set<Agent> getAgents() {
		return entities.agents.values().parallelStream().collect(Collectors.toCollection(HashSet::new));
	}

	@Override
	public void removeAgent(final UniqueId<Agent> id) {
		checkNotNull(id, "id");
		if (entities.agents.containsKey(id)) {
			/* remove the agent, all associated assignments, all associated possesses relations, all associated has relations */
			entities.agents.remove(id);
			remove(id, relations.assignmentsByAgent, "assignmentsByAgent", c -> removeAssignment(c), Assignment.class);
			remove(id, relations.possesses, "possesses", c -> removePossesses(id, c), Capability.class);
			remove(id, relations.has, "possesses", c -> removeHas(id, c), Attribute.class);
			publishRemove(Agent.class, id);
		}
	}

	@Override
	public void removeAgents(final Collection<UniqueId<Agent>> ids) {
		checkNotNull(ids, "ids");
		ids.parallelStream().forEach(this::removeAgent);
	}

	@Override
	public void removeAllAgents() {
		removeAgents(entities.agents.keySet());
	}

	@Override
	public void addCapability(final Capability capability) {
		checkNotExists(capability, "capability", entities.capabilities::containsKey);
		/* add the capability, requiredBy map, possessedBy map */
		entities.capabilities.put(capability.getId(), capability);
		relations.requiredBy.put(capability.getId(), new ConcurrentHashMap<>());
		relations.possessedBy.put(capability.getId(), new ConcurrentHashMap<>());
		publishAdd(Capability.class, capability.getId());
	}

	@Override
	public void addCapabilities(final Collection<Capability> capabilities) {
		checkNotNull(capabilities, "capabilities");
		capabilities.parallelStream().forEach(this::addCapability);
	}

	@Override
	public Capability getCapability(final UniqueId<Capability> id) {
		checkNotNull(id, "id");
		return entities.capabilities.get(id);
	}

	@Override
	public Set<Capability> getCapabilities() {
		return entities.capabilities.values().parallelStream().collect(Collectors.toCollection(HashSet::new));
	}

	@Override
	public void removeCapability(final UniqueId<Capability> id) {
		checkNotNull(id, "id");
		if (entities.capabilities.containsKey(id)) {
			/* remove the capability, all associated requires relations, all associated possesses relations */
			entities.capabilities.remove(id);
			remove(id, relations.requiredBy, "requiredBy", c -> removeRequires(c, id), Role.class);
			remove(id, relations.possessedBy, "possessedBy", c -> removePossesses(c, id), Agent.class);
			publishRemove(Capability.class, id);
		}
	}

	@Override
	public void removeCapabilities(final Collection<UniqueId<Capability>> ids) {
		checkNotNull(ids, "ids");
		ids.parallelStream().forEach(this::removeCapability);
	}

	@Override
	public void removeAllCapabilities() {
		removeCapabilities(entities.capabilities.keySet());
	}

	@Override
	public void addPolicy(final Policy policy) {
		checkNotExists(policy, "policy", entities.policies::containsKey);
		/* add the policy */
		entities.policies.put(policy.getId(), policy);
		publishAdd(Policy.class, policy.getId());
	}

	@Override
	public void addPolicies(final Collection<Policy> policies) {
		checkNotNull(policies, "policies");
		policies.parallelStream().forEach(this::addPolicy);
	}

	@Override
	public Policy getPolicy(final UniqueId<Policy> id) {
		checkNotNull(id, "id");
		return entities.policies.get(id);
	}

	@Override
	public Set<Policy> getPolicies() {
		return entities.policies.values().parallelStream().collect(Collectors.toCollection(HashSet::new));
	}

	@Override
	public void removePolicy(final UniqueId<Policy> id) {
		checkNotNull(id, "id");
		if (entities.policies.containsKey(id)) {
			/* remove the policy */
			entities.policies.remove(id);
			publishRemove(Policy.class, id);
		}
	}

	@Override
	public void removePolicies(final Collection<UniqueId<Policy>> ids) {
		checkNotNull(ids, "ids");
		ids.parallelStream().forEach(this::removePolicy);
	}

	@Override
	public void removeAllPolicies() {
		removePolicies(entities.policies.keySet());
	}

	@Override
	public void addInstanceGoal(final InstanceGoal goal) {
		checkNotExists(goal, "goal", entities.instanceGoals::containsKey);
		checkExists(goal.getSpecificationGoal().getId(), null, this::getSpecificationGoal);
		/* add the instance goal, instanceGoalsBySpecificationGoal map */
		entities.instanceGoals.put(goal.getId(), goal);
		Map<UniqueId<InstanceGoal>, InstanceGoal> map = entities.instanceGoalsBySpecificationGoal.get(goal.getSpecificationGoal().getId());
		if (map == null) {
			logger.warn("instanceGoalsBySpecificationGoal is missing ({} -> instance goal) entry", goal.getSpecificationGoal().getId());
			map = new ConcurrentHashMap<>();
			entities.instanceGoalsBySpecificationGoal.put(goal.getSpecificationGoal().getId(), map);
		}
		map.put(goal.getInstanceId(), goal);
		publishAdd(InstanceGoal.class, goal.getId());
	}

	@Override
	public void addInstanceGoals(final Collection<InstanceGoal> goals) {
		checkNotNull(goals, "goals");
		goals.parallelStream().forEach(this::addInstanceGoal);
	}

	@Override
	public InstanceGoal getInstanceGoal(final UniqueId<InstanceGoal> id) {
		checkNotNull(id, "id");
		return entities.instanceGoals.get(id);
	}

	@Override
	public Set<InstanceGoal> getInstanceGoals() {
		return entities.instanceGoals.values().parallelStream().collect(Collectors.toCollection(HashSet::new));
	}

	@Override
	public void removeInstanceGoal(final UniqueId<InstanceGoal> id) {
		checkNotNull(id, "id");
		if (entities.instanceGoals.containsKey(id)) {
			/* remove the instance goal, instanceGoalsBySpecificationGoal map */
			final InstanceGoal goal = entities.instanceGoals.remove(id);
			if (entities.instanceGoalsBySpecificationGoal.containsKey(goal.getSpecificationGoal().getId())) {
				final Map<UniqueId<InstanceGoal>, InstanceGoal> map = entities.instanceGoalsBySpecificationGoal.get(goal.getSpecificationGoal().getId());
				if (map.containsKey(goal.getInstanceId())) {
					map.remove(goal.getInstanceId());
				} else {
					logger.warn("instanceGoalsBySpecificationGoal is missing ({} -> {}) entry", goal.getSpecificationGoal().getId(), goal.getInstanceId());
				}
			} else {
				logger.warn("instanceGoalsBySpecificationGoal is missing ({} -> InstanceGoal) entry", goal.getSpecificationGoal().getId());
				entities.instanceGoalsBySpecificationGoal.put(goal.getSpecificationGoal().getId(), new ConcurrentHashMap<>());
			}
			publishRemove(InstanceGoal.class, id);
		}
	}

	@Override
	public void removeInstanceGoals(final Collection<UniqueId<InstanceGoal>> ids) {
		checkNotNull(ids, "ids");
		ids.parallelStream().forEach(this::removeInstanceGoal);
	}

	@Override
	public void removeAllInstanceGoals() {
		removeInstanceGoals(entities.instanceGoals.keySet());
	}

	@Override
	public void addAttribute(final Attribute attribute) {
		checkNotExists(attribute, "attribute", entities.attributes::containsKey);
		/* add the attribute, neededBy map, hadBy map, moderatedBy map */
		entities.attributes.put(attribute.getId(), attribute);
		relations.neededBy.put(attribute.getId(), new ConcurrentHashMap<>());
		relations.hadBy.put(attribute.getId(), new ConcurrentHashMap<>());
		relations.moderatedBy.put(attribute.getId(), new ConcurrentHashMap<>());
		publishAdd(Attribute.class, attribute.getId());
	}

	@Override
	public void addAttributes(final Collection<Attribute> attributes) {
		checkNotNull(attributes, "attributes");
		attributes.parallelStream().forEach(this::addAttribute);
	}

	@Override
	public Attribute getAttribute(final UniqueId<Attribute> id) {
		checkNotNull(id, "id");
		return entities.attributes.get(id);
	}

	@Override
	public Set<Attribute> getAttributes() {
		return entities.attributes.values().parallelStream().collect(Collectors.toCollection(HashSet::new));
	}

	@Override
	public void removeAttribute(final UniqueId<Attribute> id) {
		checkNotNull(id, "id");
		if (entities.attributes.containsKey(id)) {
			/* remove the attribute, all associated needs relations, all associated has relations, all associated moderates relations */
			entities.attributes.remove(id);
			remove(id, relations.neededBy, "neededBy", c -> removeNeeds(c, id), Role.class);
			remove(id, relations.hadBy, "hadBy", c -> removeHas(c, id), Agent.class);
			remove(id, relations.moderatedBy, "moderatedBy", c -> removeModerates(c, id), Pmf.class);
			publishRemove(Attribute.class, id);
		}
	}

	@Override
	public void removeAttributes(final Collection<UniqueId<Attribute>> ids) {
		checkNotNull(ids, "ids");
		ids.parallelStream().forEach(this::removeAttribute);
	}

	@Override
	public void removeAllAttributes() {
		removeAttributes(entities.attributes.keySet());
	}

	@Override
	public void addPmf(final Pmf pmf) {
		checkNotExists(pmf, "pmf", entities.pmfs::containsKey);
		/* add the pmf */
		entities.pmfs.put(pmf.getId(), pmf);
		publishAdd(Pmf.class, pmf.getId());
	}

	@Override
	public void addPmfs(final Collection<Pmf> pmfs) {
		checkNotNull(pmfs, "pmfs");
		pmfs.parallelStream().forEach(this::addPmf);
	}

	@Override
	public Pmf getPmf(final UniqueId<Pmf> id) {
		checkNotNull(id, "id");
		return entities.pmfs.get(id);
	}

	@Override
	public Set<Pmf> getPmfs() {
		return entities.pmfs.values().parallelStream().collect(Collectors.toCollection(HashSet::new));
	}

	@Override
	public void removePmf(final UniqueId<Pmf> id) {
		checkNotNull(id, "id");
		if (entities.pmfs.containsKey(id)) {
			/* remove the pmf, all associated moderates relations */
			entities.pmfs.remove(id);
			if (relations.moderates.containsKey(id)) {
				removeModerates(id, relations.moderates.get(id).getAttribute().getId());
			}
			publishRemove(Pmf.class, id);
		}
	}

	@Override
	public void removePmfs(final Collection<UniqueId<Pmf>> ids) {
		checkNotNull(ids, "ids");
		ids.parallelStream().forEach(this::removePmf);
	}

	@Override
	public void removeAllPmfs() {
		removePmfs(entities.pmfs.keySet());
	}

	@Override
	public void addCharacteristic(final Characteristic characteristic) {
		checkNotExists(characteristic, "characteristic", entities.characteristics::containsKey);
		/* add the characteristic, containedBy map */
		entities.characteristics.put(characteristic.getId(), characteristic);
		relations.containedBy.put(characteristic.getId(), new ConcurrentHashMap<>());
		publishAdd(Characteristic.class, characteristic.getId());
	}

	@Override
	public void addCharacteristics(final Collection<Characteristic> characteristics) {
		checkNotNull(characteristics, "characteristic");
		characteristics.parallelStream().forEach(this::addCharacteristic);
	}

	@Override
	public Characteristic getCharacteristic(final UniqueId<Characteristic> id) {
		checkNotNull(id, "id");
		return entities.characteristics.get(id);
	}

	@Override
	public Set<Characteristic> getCharacteristics() {
		return entities.characteristics.values().parallelStream().collect(Collectors.toCollection(HashSet::new));
	}

	@Override
	public void removeCharacteristic(final UniqueId<Characteristic> id) {
		checkNotNull(id, "id");
		if (entities.characteristics.containsKey(id)) {
			/* remove characteristics, all associated contains relations */
			entities.characteristics.remove(id);
			remove(id, relations.containedBy, "containedBy", c -> removeContains(c, id), Role.class);
			publishRemove(Characteristic.class, id);
		}
	}

	@Override
	public void removeCharacteristics(final Collection<UniqueId<Characteristic>> ids) {
		checkNotNull(ids, "ids");
		ids.parallelStream().forEach(this::removeCharacteristic);
	}

	@Override
	public void removeAllCharacteristic() {
		removeCharacteristics(entities.characteristics.keySet());
	}

	@Override
	public void addAssignment(final Assignment assignment) {
		checkNotExists(assignment, "assignment", relations.assignments::containsKey);
		checkExists(assignment.getAgent().getId(), null, this::getAgent);
		checkExists(assignment.getRole().getId(), null, this::getRole);
		checkExists(assignment.getGoal().getId(), null, this::getInstanceGoal);
		/* add the assignment */
		relations.assignments.put(assignment.getId(), assignment);
		relations.assignmentsByAgent.get(assignment.getAgent().getId()).put(assignment.getId(), assignment);
		publishAdd(Assignment.class, assignment.getAgent().getId(), assignment.getRole().getId(), assignment.getGoal().getId());
	}

	@Override
	public void addAssignments(final Collection<Assignment> assignments) {
		checkNotNull(assignments, "assignments");
		assignments.parallelStream().forEach(this::addAssignment);
	}

	@Override
	public Assignment getAssignment(final UniqueId<Assignment> id) {
		checkNotNull(id, "id");
		return relations.assignments.get(id);
	}

	@Override
	public Set<Assignment> getAssignments() {
		return relations.assignments.values().parallelStream().collect(Collectors.toCollection(HashSet::new));
	}

	@Override
	public Set<Assignment> getAssignmentsByAgent(final UniqueId<Agent> id) {
		checkNotNull(id, "id");
		if (relations.assignmentsByAgent.containsKey(id)) {
			return relations.assignmentsByAgent.get(id).values().parallelStream().collect(Collectors.toCollection(HashSet::new));
		}
		return new HashSet<>();
	}

	@Override
	public void removeAssignment(final UniqueId<Assignment> id) {
		checkNotNull(id, "id");
		if (relations.assignments.containsKey(id)) {
			final Assignment assignment = relations.assignments.remove(id);
			if (relations.assignmentsByAgent.containsKey(assignment.getAgent().getId())) {
				if (relations.assignmentsByAgent.get(assignment.getAgent().getId()).remove(id) == null) {
					logger.warn("assignmentsByAgent is missing ({} -> {}) entry", assignment.getAgent().getId(), id);
				}
			} else {
				logger.warn("assignmentsByAgent is missing ({} -> assignment) entry", assignment.getAgent().getId());
				relations.assignmentsByAgent.put(assignment.getAgent().getId(), new ConcurrentHashMap<>());
			}
			publishRemove(Assignment.class, assignment.getAgent().getId(), assignment.getRole().getId(), assignment.getGoal().getId());
		}
	}

	@Override
	public void removeAssignments(final Collection<UniqueId<Assignment>> ids) {
		checkNotNull(ids, "ids");
		ids.parallelStream().forEach(this::removeAssignment);
	}

	@Override
	public void removeAllAssignments() {
		removeAssignments(relations.assignments.keySet());
	}

	private void addTask(final Task task) {
		relations.tasks.put(task.getId(), task);
		publishAdd(Task.class, task.getRole().getId(), task.getGoal().getId());
	}

	@Override
	public Task getTask(final UniqueId<Task> id) {
		checkNotNull(id, "id");
		return relations.tasks.get(id);
	}

	@Override
	public Set<Task> getTasks() {
		return relations.tasks.values().parallelStream().collect(Collectors.toCollection(HashSet::new));
	}

	private void removeTask(final UniqueId<Task> id) {
		if (relations.tasks.containsKey(id)) {
			relations.tasks.remove(id);
			publishRemove(Task.class, id);
		} else {
			logger.warn("tasks is missing {}", id);
		}
	}

	@Override
	public void addAchieves(final UniqueId<Role> roleId, final UniqueId<SpecificationGoal> goalId) {
		final Role role = checkExists(roleId, "roleId", this::getRole);
		final SpecificationGoal goal = checkExists(goalId, "goalId", this::getSpecificationGoal);
		Map<UniqueId<SpecificationGoal>, Achieves> map = relations.achieves.get(roleId);
		if (map == null) {
			logger.warn("achieves is missing ({} -> goal) entry", roleId);
			map = new ConcurrentHashMap<>();
			relations.achieves.put(roleId, map);
		} else if (map.containsKey(goalId)) {
			/* relation already exists do nothing */
			return;
		}
		final Achieves achieves = new AchievesRelation(role, goal);
		map.put(goalId, achieves);
		addAchievedBy(achieves);
		addTask(new TaskRelation(role, goal));
		publishAdd(Achieves.class, roleId, goalId);
	}

	private void addAchievedBy(final Achieves achieves) {
		Map<UniqueId<Role>, Achieves> map = relations.achievedBy.get(achieves.getGoal().getId());
		if (map == null) {
			logger.warn("achievedBy is missing ({} -> role) entry", achieves.getGoal().getId());
			map = new ConcurrentHashMap<>();
			relations.achievedBy.put(achieves.getGoal().getId(), map);
		}
		map.put(achieves.getRole().getId(), achieves);
	}

	@Override
	public Set<SpecificationGoal> getAchieves(final UniqueId<Role> id) {
		checkNotNull(id, "id");
		if (relations.achieves.containsKey(id)) {
			return relations.achieves.get(id).values().parallelStream().map(Achieves::getGoal).collect(Collectors.toCollection(HashSet::new));
		}
		return new HashSet<>();
	}

	@Override
	public Set<Role> getAchievedBy(final UniqueId<SpecificationGoal> id) {
		checkNotNull(id, "id");
		if (relations.achievedBy.containsKey(id)) {
			return relations.achievedBy.get(id).values().parallelStream().map(Achieves::getRole).collect(Collectors.toCollection(HashSet::new));
		}
		return new HashSet<>();
	}

	@Override
	public void removeAchieves(final UniqueId<Role> roleId, final UniqueId<SpecificationGoal> goalId) {
		checkNotNull(roleId, "roleId");
		checkNotNull(goalId, "goalId");
		if (relations.achieves.containsKey(roleId) && relations.achieves.get(roleId).containsKey(goalId)) {
			relations.achieves.get(roleId).remove(goalId);
			removeAchievedBy(goalId, roleId);
			removeTask(new TaskRelation.Id(roleId, goalId));
			publishRemove(Achieves.class, roleId, goalId);
		}
	}

	private void removeAchievedBy(final UniqueId<SpecificationGoal> goalId, final UniqueId<Role> roleId) {
		if (relations.achievedBy.containsKey(goalId)) {
			if (relations.achievedBy.get(goalId).containsKey(roleId)) {
				relations.achievedBy.get(goalId).remove(roleId);
			} else {
				logger.warn("achievedBy map is missing ({} -> {}) entry", goalId, roleId);
			}
		} else {
			logger.warn("achievedBy map is missing ({} -> role) entry", goalId);
			relations.achievedBy.put(goalId, new ConcurrentHashMap<>());
		}
	}

	@Override
	public void removeAllAchieves() {
		final Set<Achieves> collect = relations.achieves.values().parallelStream().flatMap(m -> m.values().parallelStream())
				.collect(Collectors.toCollection(HashSet::new));
		collect.parallelStream().forEach(c -> removeAchieves(c.getRole().getId(), c.getGoal().getId()));
	}

	@Override
	public void addRequires(final UniqueId<Role> roleId, final UniqueId<Capability> capabilityId) {
		final Role role = checkExists(roleId, "roleId", this::getRole);
		final Capability capability = checkExists(capabilityId, "capabilityId", this::getCapability);
		Map<UniqueId<Capability>, Requires> map = relations.requires.get(roleId);
		if (map == null) {
			logger.warn("requires is missing ({} -> capability) entry", roleId);
			map = new ConcurrentHashMap<>();
			relations.requires.put(roleId, map);
		} else if (map.containsKey(capabilityId)) {
			/* relation already exists do nothing */
			return;
		}
		final Requires requires = new RequiresRelation(role, capability);
		map.put(capabilityId, requires);
		addRequiredBy(requires);
		publishAdd(Requires.class, roleId, capabilityId);
	}

	private void addRequiredBy(final Requires requires) {
		Map<UniqueId<Role>, Requires> map = relations.requiredBy.get(requires.getCapability().getId());
		if (map == null) {
			logger.warn("requiredBy is missing ({} -> role) entry", requires.getCapability().getId());
			map = new ConcurrentHashMap<>();
			relations.requiredBy.put(requires.getCapability().getId(), map);
		}
		map.put(requires.getRole().getId(), requires);
	}

	@Override
	public Set<Capability> getRequires(final UniqueId<Role> id) {
		checkNotNull(id, "id");
		if (relations.requires.containsKey(id)) {
			return relations.requires.get(id).values().parallelStream().map(Requires::getCapability).collect(Collectors.toCollection(HashSet::new));
		}
		return new HashSet<>();
	}

	@Override
	public Set<Role> getRequiredBy(final UniqueId<Capability> id) {
		checkNotNull(id, "id");
		if (relations.requiredBy.containsKey(id)) {
			return relations.requiredBy.get(id).values().parallelStream().map(Requires::getRole).collect(Collectors.toCollection(HashSet::new));
		}
		return new HashSet<>();
	}

	@Override
	public void removeRequires(final UniqueId<Role> roleId, final UniqueId<Capability> capabilityId) {
		checkNotNull(roleId, "roleId");
		checkNotNull(capabilityId, "capabilityId");
		if (relations.requires.containsKey(roleId) && relations.requires.get(roleId).containsKey(capabilityId)) {
			relations.requires.get(roleId).remove(capabilityId);
			removeRequiredBy(capabilityId, roleId);
			publishRemove(Requires.class, roleId, capabilityId);
		}
	}

	private void removeRequiredBy(final UniqueId<Capability> capabilityId, final UniqueId<Role> roleId) {
		if (relations.requiredBy.containsKey(capabilityId)) {
			if (relations.requiredBy.get(capabilityId).containsKey(roleId)) {
				relations.requiredBy.get(capabilityId).remove(roleId);
			} else {
				logger.warn("requiredBy is missing ({} -> {}) entry", capabilityId, roleId);
			}
		} else {
			logger.warn("requiredBy is missing ({} -> role) entry");
			relations.requiredBy.put(capabilityId, new ConcurrentHashMap<>());
		}
	}

	@Override
	public void removeAllRequires() {
		final Set<Requires> collect = relations.requires.values().parallelStream().flatMap(m -> m.values().parallelStream())
				.collect(Collectors.toCollection(HashSet::new));
		collect.parallelStream().forEach(c -> removeRequires(c.getRole().getId(), c.getCapability().getId()));
	}

	@Override
	public void addPossesses(final UniqueId<Agent> agentId, final UniqueId<Capability> capabilityId, final double score) {
		final Agent agent = checkExists(agentId, "agentId", this::getAgent);
		final Capability capability = checkExists(capabilityId, "capabilityId", this::getCapability);
		final Map<UniqueId<Capability>, Possesses> map = relations.possesses.computeIfAbsent(agentId, m -> new ConcurrentHashMap<>());
		if (map.containsKey(capabilityId)) {
			/* if relation already exists do nothing */
			return;
		}
		final Possesses possesses = new PossessesRelation(agent, capability, score);
		map.put(capabilityId, possesses);
		addPossessedBy(possesses);
		publishAdd(Possesses.class, agentId, capabilityId); // FIXME add score
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

		publishChange(Possesses.class, agentId, capabilityId); // FIXME add score
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

		publishRemove(Possesses.class, agentId, capabilityId);
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

		publishAdd(Needs.class, roleId, attributeId);
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

		publishRemove(Needs.class, roleId, attributeId);
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

		publishAdd(Has.class, agentId, attributeId); // FIXME add value
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

		publishChange(Has.class, agentId, attributeId); // FIXME add value
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

		publishRemove(Has.class, agentId, attributeId);
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

		publishAdd(Uses.class, roleId, pmfId);
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

		publishRemove(Uses.class, roleId, pmfId);
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

		publishAdd(Moderates.class, pmfId, attributeId);
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

		publishRemove(Moderates.class, pmfId, attributeId);
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

		publishAdd(Contains.class, roleId, characteristicId); // FIXME add value
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

		publishChange(Contains.class, roleId, characteristicId); // FIXME add value
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

		publishRemove(Contains.class, roleId, characteristicId);
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

	private <T> void checkNotNull(final T t, final String name) {
		if (t == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, name));
		}
	}

	private <T extends Identifiable<T>> void checkNotExists(final T t, final String name, final Predicate<UniqueId<T>> p) {
		checkNotNull(t, name);
		if (p.test(t.getId())) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_ENTITY_EXISTS, t.getClass().getSimpleName(), t));
		}
	}

	private <T, U extends UniqueId<T>> T checkExists(final U id, final String name, final Function<U, T> f) {
		checkNotNull(id, name);
		final T t = f.apply(id);
		if (t == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_ENTITY_DOES_NOT_EXISTS, id.getType().getSimpleName(), id));
		}
		return t;
	}

	private <T, U, V> void remove(final UniqueId<T> id, final Map<UniqueId<T>, Map<UniqueId<U>, V>> map, final String name,
			final Consumer<UniqueId<U>> consumer, final Class<U> clazz) {
		if (map.containsKey(id)) {
			final Set<UniqueId<U>> ids = map.get(id).keySet();
			ids.parallelStream().forEach(c -> consumer.accept(c));
			map.remove(id);
		} else {
			logger.warn("{} is missing ({} -> {}) entry", name, id, clazz.getSimpleName());
		}
	}

	private <T> T getOrThrow(final UniqueId<T> id, final Class<T> clazz, final Function<UniqueId<T>, T> f) {
		final T t = f.apply(id);
		if (t == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_ENTITY_DOES_NOT_EXISTS, clazz.getSimpleName(), id));
		}
		return t;
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
