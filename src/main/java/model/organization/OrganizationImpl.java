/*
 * Organization.java
 *
 * Created on Sep 22, 2004
 *
 * See License.txt file the license agreement.
 */
package model.organization;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import model.organization.entities.Agent;
import model.organization.entities.Attribute;
import model.organization.entities.Capability;
import model.organization.entities.Characteristic;
import model.organization.entities.InstanceGoal;
import model.organization.entities.Pmf;
import model.organization.entities.Policy;
import model.organization.entities.Role;
import model.organization.entities.SpecificationGoal;
import model.organization.event.Publisher;
import model.organization.id.UniqueId;
import model.organization.relations.Achieves;
import model.organization.relations.AchievesRelation;
import model.organization.relations.Assignment;
import model.organization.relations.Contains;
import model.organization.relations.ContainsRelation;
import model.organization.relations.Has;
import model.organization.relations.HasRelation;
import model.organization.relations.Moderates;
import model.organization.relations.ModeratesRelation;
import model.organization.relations.Needs;
import model.organization.relations.NeedsRelation;
import model.organization.relations.Possesses;
import model.organization.relations.PossessesRelation;
import model.organization.relations.Requires;
import model.organization.relations.RequiresRelation;
import model.organization.relations.Task;
import model.organization.relations.TaskRelation;
import model.organization.relations.Uses;
import model.organization.relations.UsesRelation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@linkplain OrganizationImpl} class is an implementation of the {@link Organization}.
 *
 * @author Scott Harmon
 * @author Christopher Zhong
 * @since 1.0
 */
public class OrganizationImpl implements Organization, Publisher, NullCheck {
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
		final Map<UniqueId<SpecificationGoal>, Achieves> map = relations.achieves.computeIfAbsent(roleId, m -> {
			logger.warn("achieves is missing ({} -> goal) entry", roleId);
			return new ConcurrentHashMap<>();
		});
		if (map.containsKey(goalId)) {
			/* relation already exists do nothing */
			return;
		}
		final Achieves achieves = new AchievesRelation(role, goal);
		map.put(goalId, achieves);
		addBy(achieves, relations.achievedBy, "achievedBy", goalId, roleId);
		addTask(new TaskRelation(role, goal));
		publishAdd(Achieves.class, roleId, goalId);
	}

	@Override
	public Set<SpecificationGoal> getAchieves(final UniqueId<Role> id) {
		checkNotNull(id, "id");
		return get(id, relations.achieves, Achieves::getGoal);
	}

	@Override
	public Set<Role> getAchievedBy(final UniqueId<SpecificationGoal> id) {
		checkNotNull(id, "id");
		return get(id, relations.achievedBy, Achieves::getRole);
	}

	@Override
	public void removeAchieves(final UniqueId<Role> roleId, final UniqueId<SpecificationGoal> goalId) {
		checkNotNull(roleId, "roleId");
		checkNotNull(goalId, "goalId");
		if (relations.achieves.containsKey(roleId) && relations.achieves.get(roleId).containsKey(goalId)) {
			relations.achieves.get(roleId).remove(goalId);
			removeBy(goalId, roleId, relations.achievedBy, "achievedBy");
			removeTask(new TaskRelation.Id(roleId, goalId));
			publishRemove(Achieves.class, roleId, goalId);
		}
	}

	@Override
	public void removeAllAchieves() {
		removeAll(relations.achieves, this::removeAchieves, f -> f.getRole().getId(), f -> f.getGoal().getId());
	}

	@Override
	public void addRequires(final UniqueId<Role> roleId, final UniqueId<Capability> capabilityId) {
		final Role role = checkExists(roleId, "roleId", this::getRole);
		final Capability capability = checkExists(capabilityId, "capabilityId", this::getCapability);
		final Map<UniqueId<Capability>, Requires> map = relations.requires.computeIfAbsent(roleId, m -> {
			logger.warn("requires is missing ({} -> capability) entry", roleId);
			return new ConcurrentHashMap<>();
		});
		if (map.containsKey(capabilityId)) {
			/* relation already exists do nothing */
			return;
		}
		final Requires requires = new RequiresRelation(role, capability);
		map.put(capabilityId, requires);
		addBy(requires, relations.requiredBy, "requiredBy", capabilityId, roleId);
		publishAdd(Requires.class, roleId, capabilityId);
	}

	@Override
	public Set<Capability> getRequires(final UniqueId<Role> id) {
		checkNotNull(id, "id");
		return get(id, relations.requires, Requires::getCapability);
	}

	@Override
	public Set<Role> getRequiredBy(final UniqueId<Capability> id) {
		checkNotNull(id, "id");
		return get(id, relations.requiredBy, Requires::getRole);
	}

	@Override
	public void removeRequires(final UniqueId<Role> roleId, final UniqueId<Capability> capabilityId) {
		checkNotNull(roleId, "roleId");
		checkNotNull(capabilityId, "capabilityId");
		if (relations.requires.containsKey(roleId) && relations.requires.get(roleId).containsKey(capabilityId)) {
			relations.requires.get(roleId).remove(capabilityId);
			removeBy(capabilityId, roleId, relations.requiredBy, "requiredBy");
			publishRemove(Requires.class, roleId, capabilityId);
		}
	}

	@Override
	public void removeAllRequires() {
		removeAll(relations.requires, this::removeRequires, f -> f.getRole().getId(), f -> f.getCapability().getId());
	}

	@Override
	public void addPossesses(final UniqueId<Agent> agentId, final UniqueId<Capability> capabilityId, final double score) {
		final Agent agent = checkExists(agentId, "agentId", this::getAgent);
		final Capability capability = checkExists(capabilityId, "capabilityId", this::getCapability);
		final Map<UniqueId<Capability>, Possesses> map = relations.possesses.computeIfAbsent(agentId, m -> {
			logger.warn("possesses is missing ({} -> capability) entry", agentId);
			return new ConcurrentHashMap<>();
		});
		if (map.containsKey(capabilityId)) {
			/* relation already exists do nothing */
			return;
		}
		final Possesses possesses = new PossessesRelation(agent, capability, score);
		map.put(capabilityId, possesses);
		addBy(possesses, relations.possessedBy, "possessedBy", capabilityId, agentId);
		publishAdd(Possesses.class, agentId, capabilityId); // FIXME add score
	}

	@Override
	public Set<Capability> getPossesses(final UniqueId<Agent> id) {
		checkNotNull(id, "id");
		return get(id, relations.possesses, Possesses::getCapability);
	}

	@Override
	public Set<Agent> getPossessedBy(final UniqueId<Capability> id) {
		checkNotNull(id, "id");
		return get(id, relations.possessedBy, Possesses::getAgent);
	}

	@Override
	public double getPossessesScore(final UniqueId<Agent> agentId, final UniqueId<Capability> capabilityId) {
		checkNotNull(agentId, "agentId");
		checkNotNull(capabilityId, "capabilityId");
		if (relations.possesses.containsKey(agentId) && relations.possesses.get(agentId).containsKey(capabilityId)) {
			return relations.possesses.get(agentId).get(capabilityId).getScore();
		}
		return Possesses.MIN_SCORE;
	}

	@Override
	public void setPossessesScore(final UniqueId<Agent> agentId, final UniqueId<Capability> capabilityId, final double score) {
		checkNotNull(agentId, "agentId");
		checkNotNull(capabilityId, "capabilityId");
		if (relations.possesses.containsKey(agentId) && relations.possesses.get(agentId).containsKey(capabilityId)) {
			relations.possesses.get(agentId).get(capabilityId).setScore(score);
			publishChange(Possesses.class, agentId, capabilityId); // FIXME add score
		}
	}

	@Override
	public void removePossesses(final @NotNull UniqueId<Agent> agentId, final @NotNull UniqueId<Capability> capabilityId) {
		checkNotNull(agentId, "agentId");
		checkNotNull(capabilityId, "capabilityId");
		if (relations.possesses.containsKey(agentId) && relations.possesses.get(agentId).containsKey(capabilityId)) {
			relations.possesses.get(agentId).remove(capabilityId);
			removeBy(capabilityId, agentId, relations.possessedBy, "possessedBy");
			publishRemove(Possesses.class, agentId, capabilityId);
		}
	}

	@Override
	public void removeAllPossesses() {
		removeAll(relations.possesses, this::removePossesses, f -> f.getAgent().getId(), f -> f.getCapability().getId());
	}

	@Override
	public void addNeeds(final UniqueId<Role> roleId, final UniqueId<Attribute> attributeId) {
		final Role role = checkExists(roleId, "roleId", this::getRole);
		final Attribute attribute = checkExists(attributeId, "attributeId", this::getAttribute);
		final Map<UniqueId<Attribute>, Needs> map = relations.needs.computeIfAbsent(roleId, m -> {
			logger.warn("needs is missing ({} -> attribute) entry", roleId);
			return new ConcurrentHashMap<>();
		});
		if (map.containsKey(attributeId)) {
			/* relation already exists do nothing */
			return;
		}
		final Needs needs = new NeedsRelation(role, attribute);
		map.put(attributeId, needs);
		addBy(needs, relations.neededBy, "neededBy", attributeId, roleId);
		publishAdd(Needs.class, roleId, attributeId);
	}

	@Override
	public Set<Attribute> getNeeds(final UniqueId<Role> id) {
		checkNotNull(id, "id");
		return get(id, relations.needs, Needs::getAttribute);
	}

	@Override
	public Set<Role> getNeededBy(final UniqueId<Attribute> id) {
		checkNotNull(id, "id");
		return get(id, relations.neededBy, Needs::getRole);
	}

	@Override
	public void removeNeeds(final UniqueId<Role> roleId, final UniqueId<Attribute> attributeId) {
		checkNotNull(roleId, "roleId");
		checkNotNull(attributeId, "attributeId");
		if (relations.needs.containsKey(roleId) && relations.needs.get(roleId).containsKey(attributeId)) {
			relations.needs.get(roleId).remove(attributeId);
			removeBy(attributeId, roleId, relations.neededBy, "neededBy");
			publishRemove(Needs.class, roleId, attributeId);
		}
	}

	@Override
	public void removeAllNeeds() {
		removeAll(relations.needs, this::removeNeeds, f -> f.getRole().getId(), f -> f.getAttribute().getId());
	}

	@Override
	public void addHas(final UniqueId<Agent> agentId, final UniqueId<Attribute> attributeId, final double value) {
		final Agent agent = checkExists(agentId, "agentId", this::getAgent);
		final Attribute attribute = checkExists(attributeId, "attributeId", this::getAttribute);
		final Map<UniqueId<Attribute>, Has> map = relations.has.computeIfAbsent(agentId, m -> {
			logger.warn("has is missing ({} -> attribute) entry", agentId);
			return new ConcurrentHashMap<>();
		});
		if (map.containsKey(attributeId)) {
			/* relation already exists do nothing */
			return;
		}
		final Has has = new HasRelation(agent, attribute, value);
		map.put(attributeId, has);
		addBy(has, relations.hadBy, "hadBy", attributeId, agentId);
		publishAdd(Has.class, agentId, attributeId); // FIXME add value
	}

	@Override
	public Set<Attribute> getHas(final UniqueId<Agent> id) {
		checkNotNull(id, "id");
		return get(id, relations.has, Has::getAttribute);
	}

	@Override
	public Set<Agent> getHadBy(final UniqueId<Attribute> id) {
		checkNotNull(id, "id");
		return get(id, relations.hadBy, Has::getAgent);
	}

	@Override
	public Double getHasValue(final UniqueId<Agent> agentId, final UniqueId<Attribute> attributeId) {
		checkNotNull(agentId, "agentId");
		checkNotNull(attributeId, "attributeId");
		if (relations.has.containsKey(agentId) && relations.has.get(agentId).containsKey(attributeId)) {
			return relations.has.get(agentId).get(attributeId).getValue();
		}
		return null;
	}

	@Override
	public void setHasValue(final UniqueId<Agent> agentId, final UniqueId<Attribute> attributeId, final double value) {
		checkNotNull(agentId, "agentId");
		checkNotNull(attributeId, "attributeId");
		if (relations.has.containsKey(agentId) && relations.has.get(agentId).containsKey(attributeId)) {
			relations.has.get(agentId).get(attributeId).setValue(value);
			publishChange(Has.class, agentId, attributeId); // FIXME add value
		}
	}

	@Override
	public void removeHas(final UniqueId<Agent> agentId, final UniqueId<Attribute> attributeId) {
		checkNotNull(agentId, "agentId");
		checkNotNull(attributeId, "attributeId");
		if (relations.has.containsKey(agentId) && relations.has.get(agentId).containsKey(attributeId)) {
			relations.has.get(agentId).remove(attributeId);
			removeBy(attributeId, agentId, relations.hadBy, "hadBy");
			publishRemove(Has.class, agentId, attributeId);
		}
	}

	@Override
	public void removeAllHas() {
		removeAll(relations.has, this::removeHas, f -> f.getAgent().getId(), f -> f.getAttribute().getId());
	}

	@Override
	public void addUses(final UniqueId<Role> roleId, final UniqueId<Pmf> pmfId) {
		final Role role = checkExists(roleId, "roleId", this::getRole);
		final Pmf pmf = checkExists(pmfId, "pmfId", this::getPmf);
		final Map<UniqueId<Pmf>, Uses> map = relations.uses.computeIfAbsent(roleId, m -> {
			logger.warn("uses is missing ({} -> pmf) entry", roleId);
			return new ConcurrentHashMap<>();
		});
		if (map.containsKey(pmfId)) {
			/* relation already exists do nothing */
			return;
		}
		final Uses uses = new UsesRelation(role, pmf);
		map.put(pmfId, uses);
		addBy(uses, relations.usedBy, "usedBy", pmfId, roleId);
		publishAdd(Uses.class, roleId, pmfId);
	}

	@Override
	public Set<Pmf> getUses(final UniqueId<Role> id) {
		checkNotNull(id, "id");
		return get(id, relations.uses, Uses::getPmf);
	}

	@Override
	public Set<Role> getUsedBy(final UniqueId<Pmf> id) {
		checkNotNull(id, "id");
		return get(id, relations.usedBy, Uses::getRole);
	}

	@Override
	public void removeUses(final UniqueId<Role> roleId, final UniqueId<Pmf> pmfId) {
		checkNotNull(roleId, "roleId");
		checkNotNull(pmfId, "pmfId");
		if (relations.uses.containsKey(roleId) && relations.uses.get(roleId).containsKey(pmfId)) {
			relations.uses.get(roleId).remove(pmfId);
			removeBy(pmfId, roleId, relations.usedBy, "usedBy");
			publishRemove(Uses.class, roleId, pmfId);
		}
	}

	@Override
	public void removeAllUses() {
		removeAll(relations.uses, this::removeUses, f -> f.getRole().getId(), f -> f.getPmf().getId());
	}

	@Override
	public void addModerates(final UniqueId<Pmf> pmfId, final UniqueId<Attribute> attributeId) {
		final Pmf pmf = checkExists(pmfId, "pmfId", this::getPmf);
		final Attribute attribute = checkExists(attributeId, "attributeId", this::getAttribute);
		if (relations.moderates.containsKey(pmfId)) {
			return;
		}
		final Moderates moderates = new ModeratesRelation(pmf, attribute);
		relations.moderates.put(pmfId, moderates);
		addBy(moderates, relations.moderatedBy, "moderatedBy", attributeId, pmfId);
		publishAdd(Moderates.class, pmfId, attributeId);
	}

	@Override
	public Attribute getModerates(final UniqueId<Pmf> id) {
		checkNotNull(id, "id");
		if (relations.moderates.containsKey(id)) {
			return relations.moderates.get(id).getAttribute();
		}
		return null;
	}

	@Override
	public Set<Pmf> getModeratedBy(final UniqueId<Attribute> id) {
		checkNotNull(id, "id");
		return get(id, relations.moderatedBy, Moderates::getPmf);
	}

	@Override
	public void removeModerates(final UniqueId<Pmf> pmfId, final UniqueId<Attribute> attributeId) {
		checkNotNull(pmfId, "pmfId");
		checkNotNull(attributeId, "attributeId");
		if (relations.moderates.containsKey(pmfId)) {
			relations.moderates.remove(pmfId);
			removeBy(attributeId, pmfId, relations.moderatedBy, "moderatedBy");
			publishRemove(Moderates.class, pmfId, attributeId);
		}
	}

	@Override
	public void removeAllModerates() {
		relations.moderates.values().parallelStream().collect(Collectors.toCollection(HashSet::new)).parallelStream()
				.forEach(c -> removeModerates(c.getPmf().getId(), c.getAttribute().getId()));
	}

	@Override
	public void addContains(final UniqueId<Role> roleId, final UniqueId<Characteristic> characteristicId, final double value) {
		final Role role = checkExists(roleId, "roleId", this::getRole);
		final Characteristic characteristic = checkExists(characteristicId, "characteristicId", this::getCharacteristic);
		final Map<UniqueId<Characteristic>, Contains> map = relations.contains.computeIfAbsent(roleId, m -> {
			logger.warn("contains is missing ({} -> characteristic) entry", roleId);
			return new ConcurrentHashMap<>();
		});
		if (map.containsKey(characteristicId)) {
			/* relation already exists do nothing */
			return;
		}
		final Contains contains = new ContainsRelation(role, characteristic, value);
		map.put(characteristicId, contains);
		addBy(contains, relations.containedBy, "containedBy", characteristicId, roleId);
		publishAdd(Contains.class, roleId, characteristicId); // FIXME add value
	}

	@Override
	public Set<Characteristic> getContains(final UniqueId<Role> id) {
		checkNotNull(id, "id");
		return get(id, relations.contains, Contains::getCharacteristic);
	}

	@Override
	public Set<Role> getContainedBy(final UniqueId<Characteristic> id) {
		checkNotNull(id, "id");
		return get(id, relations.containedBy, Contains::getRole);
	}

	@Override
	public Double getContainsValue(final UniqueId<Role> roleId, final UniqueId<Characteristic> characteristicId) {
		checkNotNull(roleId, "roleId");
		checkNotNull(characteristicId, "characteristicId");
		if (relations.contains.containsKey(roleId) && relations.contains.get(roleId).containsKey(characteristicId)) {
			return relations.contains.get(roleId).get(characteristicId).getValue();
		}
		return null;
	}

	@Override
	public void setContainsValue(final UniqueId<Role> roleId, final UniqueId<Characteristic> characteristicId, final double value) {
		checkNotNull(roleId, "roleId");
		checkNotNull(characteristicId, "characteristicId");
		if (relations.contains.containsKey(roleId) && relations.contains.get(roleId).containsKey(characteristicId)) {
			relations.contains.get(roleId).get(characteristicId).setValue(value);
			publishChange(Contains.class, roleId, characteristicId); // FIXME add value
		}
	}

	@Override
	public void removeContains(final UniqueId<Role> roleId, final UniqueId<Characteristic> characteristicId) {
		checkNotNull(roleId, "roleId");
		checkNotNull(characteristicId, "characteristicId");
		if (relations.contains.containsKey(roleId) && relations.contains.get(roleId).containsKey(characteristicId)) {
			relations.contains.get(roleId).remove(characteristicId);
			removeBy(characteristicId, roleId, relations.containedBy, "containedBy");
			publishRemove(Contains.class, roleId, characteristicId);
		}
	}

	@Override
	public void removeAllContains() {
		removeAll(relations.contains, this::removeContains, f -> f.getRole().getId(), f -> f.getCharacteristic().getId());
	}

	private <T, U, V> void addBy(final T value, final Map<UniqueId<U>, Map<UniqueId<V>, T>> map, final String mapName, final UniqueId<U> id1,
			final UniqueId<V> id2) {
		map.computeIfAbsent(id1, m -> {
			logger.warn("{} is missing ({} -> {}) entry", mapName, id1, id2.getType().getSimpleName());
			return new ConcurrentHashMap<>();
		});
		map.get(id1).put(id2, value);
	}

	private <T, U, V> Set<T> get(final UniqueId<U> id, final Map<UniqueId<U>, Map<UniqueId<T>, V>> map, final Function<V, T> f) {
		if (map.containsKey(id)) {
			return map.get(id).values().parallelStream().map(f).collect(Collectors.toCollection(HashSet::new));
		}
		return new HashSet<>();
	}

	private <T, U, V> void remove(final UniqueId<T> id, final Map<UniqueId<T>, Map<UniqueId<U>, V>> map, final String mapName,
			final Consumer<UniqueId<U>> consumer, final Class<U> clazz) {
		if (map.containsKey(id)) {
			final Set<UniqueId<U>> ids = map.get(id).keySet();
			ids.parallelStream().forEach(c -> consumer.accept(c));
			map.remove(id);
		} else {
			logger.warn("{} is missing ({} -> {}) entry", mapName, id, clazz.getSimpleName());
		}
	}

	private <T, U, V> void removeBy(final UniqueId<T> id1, final UniqueId<U> id2, final Map<UniqueId<T>, Map<UniqueId<U>, V>> map, final String mapName) {
		if (map.containsKey(id1)) {
			if (map.get(id1).containsKey(id2)) {
				map.get(id1).remove(id2);
			} else {
				logger.warn("{} is missing ({} -> {}) entry", mapName, id1, id2);
			}
		} else {
			logger.warn("{} is missing ({} -> {}) entry", mapName, id1, id2.getType().getSimpleName());
			map.put(id1, new ConcurrentHashMap<>());
		}
	}

	private <T, U, V, W extends UniqueId<T>, X extends UniqueId<U>> void removeAll(final Map<W, Map<X, V>> map, final BiConsumer<W, X> consumer,
			final Function<V, W> function1, final Function<V, X> function2) {
		map.values().parallelStream().flatMap(m -> m.values().parallelStream()).collect(Collectors.toCollection(HashSet::new)).parallelStream()
				.forEach(c -> consumer.accept(function1.apply(c), function2.apply(c)));

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
