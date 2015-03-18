package model.organization;

import static model.organization.validation.Checks.checkExists;
import static model.organization.validation.Checks.checkNotExists;
import static model.organization.validation.Checks.checkNotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import model.organization.entity.Agent;
import model.organization.entity.Attribute;
import model.organization.entity.Capability;
import model.organization.entity.Characteristic;
import model.organization.entity.InstanceGoal;
import model.organization.entity.Pmf;
import model.organization.entity.Policy;
import model.organization.entity.Role;
import model.organization.entity.SpecificationGoal;
import model.organization.event.Publisher;
import model.organization.function.Effectiveness;
import model.organization.function.Goodness;
import model.organization.id.UniqueId;
import model.organization.relation.Achieves;
import model.organization.relation.Assignment;
import model.organization.relation.Contains;
import model.organization.relation.Has;
import model.organization.relation.Moderates;
import model.organization.relation.Needs;
import model.organization.relation.Possesses;
import model.organization.relation.RelationFactory;
import model.organization.relation.Requires;
import model.organization.relation.Uses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class OrganizationImpl implements Organization {

	private static class Entities {

		private final Map<UniqueId<SpecificationGoal>, SpecificationGoal> specificationGoals = new ConcurrentHashMap<>();
		private final Map<UniqueId<Role>, Role> roles = new ConcurrentHashMap<>();
		private final Map<UniqueId<Agent>, Agent> agents = new ConcurrentHashMap<>();
		private final Map<UniqueId<Capability>, Capability> capabilities = new ConcurrentHashMap<>();
		private final Map<UniqueId<Policy>, Policy> policies = new ConcurrentHashMap<>();
		private final Map<UniqueId<InstanceGoal>, InstanceGoal> instanceGoals = new ConcurrentHashMap<>();
		private final Map<UniqueId<SpecificationGoal>, Map<UniqueId<InstanceGoal>, InstanceGoal>> instanceGoalsBySpecificationGoal = new ConcurrentHashMap<>();
		private final Map<UniqueId<Attribute>, Attribute> attributes = new ConcurrentHashMap<>();
		private final Map<UniqueId<Pmf>, Pmf> pmfs = new ConcurrentHashMap<>();
		private final Map<UniqueId<Characteristic>, Characteristic> characteristics = new ConcurrentHashMap<>();

	}

	private static class Relations {

		private final Map<UniqueId<Assignment>, Assignment> assignments = new ConcurrentHashMap<>();
		private final Map<UniqueId<Agent>, Map<UniqueId<Assignment>, Assignment>> assignmentsByAgent = new ConcurrentHashMap<>();
		private final Map<UniqueId<Role>, Map<UniqueId<SpecificationGoal>, Achieves>> achieves = new ConcurrentHashMap<>();
		private final Map<UniqueId<SpecificationGoal>, Map<UniqueId<Role>, Achieves>> achievedBy = new ConcurrentHashMap<>();
		private final Map<UniqueId<Role>, Map<UniqueId<Capability>, Requires>> requires = new ConcurrentHashMap<>();
		private final Map<UniqueId<Capability>, Map<UniqueId<Role>, Requires>> requiredBy = new ConcurrentHashMap<>();
		private final Map<UniqueId<Agent>, Map<UniqueId<Capability>, Possesses>> possesses = new ConcurrentHashMap<>();
		private final Map<UniqueId<Capability>, Map<UniqueId<Agent>, Possesses>> possessedBy = new ConcurrentHashMap<>();
		private final Map<UniqueId<Role>, Map<UniqueId<Attribute>, Needs>> needs = new ConcurrentHashMap<>();
		private final Map<UniqueId<Attribute>, Map<UniqueId<Role>, Needs>> neededBy = new ConcurrentHashMap<>();
		private final Map<UniqueId<Agent>, Map<UniqueId<Attribute>, Has>> has = new ConcurrentHashMap<>();
		private final Map<UniqueId<Attribute>, Map<UniqueId<Agent>, Has>> hadBy = new ConcurrentHashMap<>();
		private final Map<UniqueId<Role>, Map<UniqueId<Pmf>, Uses>> uses = new ConcurrentHashMap<>();
		private final Map<UniqueId<Pmf>, Map<UniqueId<Role>, Uses>> usedBy = new ConcurrentHashMap<>();
		private final Map<UniqueId<Pmf>, Moderates> moderates = new ConcurrentHashMap<>();
		private final Map<UniqueId<Attribute>, Map<UniqueId<Pmf>, Moderates>> moderatedBy = new ConcurrentHashMap<>();
		private final Map<UniqueId<Role>, Map<UniqueId<Characteristic>, Contains>> contains = new ConcurrentHashMap<>();
		private final Map<UniqueId<Characteristic>, Map<UniqueId<Role>, Contains>> containedBy = new ConcurrentHashMap<>();

	}

	private static class Functions {

		private final Map<UniqueId<Role>, Goodness> goodnesses = new ConcurrentHashMap<>();

	}

	private static final Logger logger = LoggerFactory.getLogger(Organization.class);
	private final Entities entities = new Entities();
	private final Relations relations = new Relations();
	private final Functions functions = new Functions();
	private final RelationFactory relationFactory;
	private final Goodness goodness;
	private final Effectiveness effectiveness;
	private final Publisher publisher = new Publisher() {}; // TODO switch to inject as parameter in constructor

	@Inject
	OrganizationImpl(@NotNull final RelationFactory relationFactory, @NotNull final Goodness goodness, @NotNull final Effectiveness effectiveness) {
		this.relationFactory = relationFactory;
		this.goodness = goodness;
		this.effectiveness = effectiveness;
	}

	@Override
	public void addSpecificationGoal(final SpecificationGoal goal) {
		checkNotExists(goal, "goal", entities.specificationGoals::containsKey);
		/* add the specification goal, instanceGoalsBySpecificationGoal map, achievedBy map */
		entities.specificationGoals.put(goal.getId(), goal);
		entities.instanceGoalsBySpecificationGoal.put(goal.getId(), new ConcurrentHashMap<>());
		relations.achievedBy.put(goal.getId(), new ConcurrentHashMap<>());
		publisher.publishAdd(SpecificationGoal.class, goal.getId());
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
			publisher.publishRemove(SpecificationGoal.class, id);
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
		functions.goodnesses.put(role.getId(), goodness);
		publisher.publishAdd(Role.class, role.getId());
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
			functions.goodnesses.remove(id);
			publisher.publishRemove(Role.class, id);
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
		publisher.publishAdd(Agent.class, agent.getId());
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
			publisher.publishRemove(Agent.class, id);
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
		publisher.publishAdd(Capability.class, capability.getId());
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
			publisher.publishRemove(Capability.class, id);
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
		publisher.publishAdd(Policy.class, policy.getId());
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
			publisher.publishRemove(Policy.class, id);
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
		checkExists(goal.getGoal().getId(), null, this::getSpecificationGoal);
		/* add the instance goal, instanceGoalsBySpecificationGoal map */
		entities.instanceGoals.put(goal.getId(), goal);
		Map<UniqueId<InstanceGoal>, InstanceGoal> map = entities.instanceGoalsBySpecificationGoal.get(goal.getGoal().getId());
		if (map == null) {
			logger.warn("instanceGoalsBySpecificationGoal is missing ({} -> instance goal) entry", goal.getGoal().getId());
			map = new ConcurrentHashMap<>();
			entities.instanceGoalsBySpecificationGoal.put(goal.getGoal().getId(), map);
		}
		map.put(goal.getId(), goal);
		publisher.publishAdd(InstanceGoal.class, goal.getId());
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
			if (entities.instanceGoalsBySpecificationGoal.containsKey(goal.getGoal().getId())) {
				final Map<UniqueId<InstanceGoal>, InstanceGoal> map = entities.instanceGoalsBySpecificationGoal.get(goal.getGoal().getId());
				if (map.containsKey(goal.getId())) {
					map.remove(goal.getId());
				} else {
					logger.warn("instanceGoalsBySpecificationGoal is missing ({} -> {}) entry", goal.getGoal().getId(), goal.getId());
				}
			} else {
				logger.warn("instanceGoalsBySpecificationGoal is missing ({} -> InstanceGoal) entry", goal.getGoal().getId());
				entities.instanceGoalsBySpecificationGoal.put(goal.getGoal().getId(), new ConcurrentHashMap<>());
			}
			publisher.publishRemove(InstanceGoal.class, id);
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
		publisher.publishAdd(Attribute.class, attribute.getId());
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
			publisher.publishRemove(Attribute.class, id);
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
		publisher.publishAdd(Pmf.class, pmf.getId());
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
			publisher.publishRemove(Pmf.class, id);
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
		publisher.publishAdd(Characteristic.class, characteristic.getId());
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
			publisher.publishRemove(Characteristic.class, id);
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
		publisher.publishAdd(Assignment.class, assignment.getAgent().getId(), assignment.getRole().getId(), assignment.getGoal().getId());
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
			publisher.publishRemove(Assignment.class, assignment.getAgent().getId(), assignment.getRole().getId(), assignment.getGoal().getId());
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
		final Achieves achieves = relationFactory.buildAchieves(role, goal);
		map.put(goalId, achieves);
		addBy(achieves, relations.achievedBy, "achievedBy", goalId, roleId);
		publisher.publishAdd(Achieves.class, roleId, goalId);
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
			publisher.publishRemove(Achieves.class, roleId, goalId);
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
		final Requires requires = relationFactory.buildRequires(role, capability);
		map.put(capabilityId, requires);
		addBy(requires, relations.requiredBy, "requiredBy", capabilityId, roleId);
		publisher.publishAdd(Requires.class, roleId, capabilityId);
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
			publisher.publishRemove(Requires.class, roleId, capabilityId);
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
		final Possesses possesses = relationFactory.buildPossesses(agent, capability, score);
		map.put(capabilityId, possesses);
		addBy(possesses, relations.possessedBy, "possessedBy", capabilityId, agentId);
		publisher.publishAdd(Possesses.class, agentId, capabilityId); // FIXME add score
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
			publisher.publishChange(Possesses.class, agentId, capabilityId); // FIXME add score
		}
	}

	@Override
	public void removePossesses(final @NotNull UniqueId<Agent> agentId, final @NotNull UniqueId<Capability> capabilityId) {
		checkNotNull(agentId, "agentId");
		checkNotNull(capabilityId, "capabilityId");
		if (relations.possesses.containsKey(agentId) && relations.possesses.get(agentId).containsKey(capabilityId)) {
			relations.possesses.get(agentId).remove(capabilityId);
			removeBy(capabilityId, agentId, relations.possessedBy, "possessedBy");
			publisher.publishRemove(Possesses.class, agentId, capabilityId);
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
		final Needs needs = relationFactory.buildNeeds(role, attribute);
		map.put(attributeId, needs);
		addBy(needs, relations.neededBy, "neededBy", attributeId, roleId);
		publisher.publishAdd(Needs.class, roleId, attributeId);
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
			publisher.publishRemove(Needs.class, roleId, attributeId);
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
		final Has has = relationFactory.buildHas(agent, attribute, value);
		map.put(attributeId, has);
		addBy(has, relations.hadBy, "hadBy", attributeId, agentId);
		publisher.publishAdd(Has.class, agentId, attributeId); // FIXME add value
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
			publisher.publishChange(Has.class, agentId, attributeId); // FIXME add value
		}
	}

	@Override
	public void removeHas(final UniqueId<Agent> agentId, final UniqueId<Attribute> attributeId) {
		checkNotNull(agentId, "agentId");
		checkNotNull(attributeId, "attributeId");
		if (relations.has.containsKey(agentId) && relations.has.get(agentId).containsKey(attributeId)) {
			relations.has.get(agentId).remove(attributeId);
			removeBy(attributeId, agentId, relations.hadBy, "hadBy");
			publisher.publishRemove(Has.class, agentId, attributeId);
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
		final Uses uses = relationFactory.buildUses(role, pmf);
		map.put(pmfId, uses);
		addBy(uses, relations.usedBy, "usedBy", pmfId, roleId);
		publisher.publishAdd(Uses.class, roleId, pmfId);
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
			publisher.publishRemove(Uses.class, roleId, pmfId);
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
		final Moderates moderates = relationFactory.buildModerates(pmf, attribute);
		relations.moderates.put(pmfId, moderates);
		addBy(moderates, relations.moderatedBy, "moderatedBy", attributeId, pmfId);
		publisher.publishAdd(Moderates.class, pmfId, attributeId);
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
			publisher.publishRemove(Moderates.class, pmfId, attributeId);
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
		final Contains contains = relationFactory.buildContains(role, characteristic, value);
		map.put(characteristicId, contains);
		addBy(contains, relations.containedBy, "containedBy", characteristicId, roleId);
		publisher.publishAdd(Contains.class, roleId, characteristicId); // FIXME add value
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
			publisher.publishChange(Contains.class, roleId, characteristicId); // FIXME add value
		}
	}

	@Override
	public void removeContains(final UniqueId<Role> roleId, final UniqueId<Characteristic> characteristicId) {
		checkNotNull(roleId, "roleId");
		checkNotNull(characteristicId, "characteristicId");
		if (relations.contains.containsKey(roleId) && relations.contains.get(roleId).containsKey(characteristicId)) {
			relations.contains.get(roleId).remove(characteristicId);
			removeBy(characteristicId, roleId, relations.containedBy, "containedBy");
			publisher.publishRemove(Contains.class, roleId, characteristicId);
		}
	}

	@Override
	public void removeAllContains() {
		removeAll(relations.contains, this::removeContains, f -> f.getRole().getId(), f -> f.getCharacteristic().getId());
	}

	@Override
	public double effectiveness(final Collection<Assignment> assignments) {
		return effectiveness.compute(this, assignments);
	}

	@Override
	public Goodness getGoodness(final UniqueId<Role> id) {
		checkNotNull(id, "id");
		return functions.goodnesses.get(id);
	}

	@Override
	public void setGoodness(final UniqueId<Role> id, final Goodness goodness) {
		checkExists(id, "id", this::getRole);
		functions.goodnesses.put(id, goodness);
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
