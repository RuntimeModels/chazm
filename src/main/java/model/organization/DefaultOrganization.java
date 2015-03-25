package model.organization;

import static model.organization.event.EventCategory.ADDED;
import static model.organization.event.EventCategory.CHANGED;
import static model.organization.event.EventCategory.REMOVED;
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
import model.organization.event.EventFactory;
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
import notification.Publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DefaultOrganization implements Organization {

	private static final Logger logger = LoggerFactory.getLogger(Organization.class);
	private final Entities entities = new Entities();
	private final Relations relations = new Relations();
	private final Functions functions = new Functions();
	private final RelationFactory relationFactory;
	private final EventFactory eventFactory;
	private final Goodness goodness;
	private final Effectiveness effectiveness;
	private final Publisher publisher;

	@Inject
	DefaultOrganization(@NotNull final RelationFactory relationFactory, @NotNull final EventFactory eventFactory, @NotNull final Goodness goodness,
			@NotNull final Effectiveness effectiveness, @NotNull final Publisher publisher) {
		this.relationFactory = relationFactory;
		this.eventFactory = eventFactory;
		this.goodness = goodness;
		this.effectiveness = effectiveness;
		this.publisher = publisher;
	}

	@Override
	public void addAgent(@NotNull final Agent agent) {
		checkNotExists(agent, "agent", entities.agents::containsKey);
		/* add the agent, assignmentsByAgent map, possesses map, has map */
		entities.agents.put(agent.getId(), agent);
		relations.assignmentsByAgent.put(agent.getId(), new ConcurrentHashMap<>());
		relations.possesses.put(agent.getId(), new ConcurrentHashMap<>());
		relations.has.put(agent.getId(), new ConcurrentHashMap<>());
		publisher.post(eventFactory.build(ADDED, agent));
	}

	@Override
	public void addAgents(@NotNull final Collection<Agent> agents) {
		checkNotNull(agents, "agents");
		agents.parallelStream().forEach(this::addAgent);
	}

	@Override
	public Agent getAgent(@NotNull final UniqueId<Agent> id) {
		checkNotNull(id, "id");
		return entities.agents.get(id);
	}

	@Override
	public Set<Agent> getAgents() {
		return entities.agents.values().parallelStream().collect(Collectors.toCollection(HashSet::new));
	}

	@Override
	public void removeAgent(@NotNull final UniqueId<Agent> id) {
		checkNotNull(id, "id");
		if (entities.agents.containsKey(id)) {
			/* remove the agent, all associated assignments, all associated possesses relations, all associated has relations */
			final Agent agent = entities.agents.remove(id);
			remove(id, relations.assignmentsByAgent, "assignmentsByAgent", c -> removeAssignment(c), Assignment.class);
			remove(id, relations.possesses, "possesses", c -> removePossesses(id, c), Capability.class);
			remove(id, relations.has, "possesses", c -> removeHas(id, c), Attribute.class);
			publisher.post(eventFactory.build(REMOVED, agent));
		}
	}

	@Override
	public void removeAgents(@NotNull final Collection<UniqueId<Agent>> ids) {
		checkNotNull(ids, "ids");
		ids.parallelStream().forEach(this::removeAgent);
	}

	@Override
	public void removeAllAgents() {
		removeAgents(entities.agents.keySet());
	}

	@Override
	public void addAttribute(@NotNull final Attribute attribute) {
		checkNotExists(attribute, "attribute", entities.attributes::containsKey);
		/* add the attribute, neededBy map, hadBy map, moderatedBy map */
		entities.attributes.put(attribute.getId(), attribute);
		relations.neededBy.put(attribute.getId(), new ConcurrentHashMap<>());
		relations.hadBy.put(attribute.getId(), new ConcurrentHashMap<>());
		relations.moderatedBy.put(attribute.getId(), new ConcurrentHashMap<>());
		publisher.post(eventFactory.build(ADDED, attribute));
	}

	@Override
	public void addAttributes(@NotNull final Collection<Attribute> attributes) {
		checkNotNull(attributes, "attributes");
		attributes.parallelStream().forEach(this::addAttribute);
	}

	@Override
	public Attribute getAttribute(@NotNull final UniqueId<Attribute> id) {
		checkNotNull(id, "id");
		return entities.attributes.get(id);
	}

	@Override
	public Set<Attribute> getAttributes() {
		return entities.attributes.values().parallelStream().collect(Collectors.toCollection(HashSet::new));
	}

	@Override
	public void removeAttribute(@NotNull final UniqueId<Attribute> id) {
		checkNotNull(id, "id");
		if (entities.attributes.containsKey(id)) {
			/* remove the attribute, all associated needs relations, all associated has relations, all associated moderates relations */
			final Attribute attribute = entities.attributes.remove(id);
			remove(id, relations.neededBy, "neededBy", c -> removeNeeds(c, id), Role.class);
			remove(id, relations.hadBy, "hadBy", c -> removeHas(c, id), Agent.class);
			remove(id, relations.moderatedBy, "moderatedBy", c -> removeModerates(c, id), Pmf.class);
			publisher.post(eventFactory.build(REMOVED, attribute));
		}
	}

	@Override
	public void removeAttributes(@NotNull final Collection<UniqueId<Attribute>> ids) {
		checkNotNull(ids, "ids");
		ids.parallelStream().forEach(this::removeAttribute);
	}

	@Override
	public void removeAllAttributes() {
		removeAttributes(entities.attributes.keySet());
	}

	@Override
	public void addCapability(@NotNull final Capability capability) {
		checkNotExists(capability, "capability", entities.capabilities::containsKey);
		/* add the capability, requiredBy map, possessedBy map */
		entities.capabilities.put(capability.getId(), capability);
		relations.requiredBy.put(capability.getId(), new ConcurrentHashMap<>());
		relations.possessedBy.put(capability.getId(), new ConcurrentHashMap<>());
		publisher.post(eventFactory.build(ADDED, capability));
	}

	@Override
	public void addCapabilities(@NotNull final Collection<Capability> capabilities) {
		checkNotNull(capabilities, "capabilities");
		capabilities.parallelStream().forEach(this::addCapability);
	}

	@Override
	public Capability getCapability(@NotNull final UniqueId<Capability> id) {
		checkNotNull(id, "id");
		return entities.capabilities.get(id);
	}

	@Override
	public Set<Capability> getCapabilities() {
		return entities.capabilities.values().parallelStream().collect(Collectors.toCollection(HashSet::new));
	}

	@Override
	public void removeCapability(@NotNull final UniqueId<Capability> id) {
		checkNotNull(id, "id");
		if (entities.capabilities.containsKey(id)) {
			/* remove the capability, all associated requires relations, all associated possesses relations */
			final Capability capability = entities.capabilities.remove(id);
			remove(id, relations.requiredBy, "requiredBy", c -> removeRequires(c, id), Role.class);
			remove(id, relations.possessedBy, "possessedBy", c -> removePossesses(c, id), Agent.class);
			publisher.post(eventFactory.build(REMOVED, capability));
		}
	}

	@Override
	public void removeCapabilities(@NotNull final Collection<UniqueId<Capability>> ids) {
		checkNotNull(ids, "ids");
		ids.parallelStream().forEach(this::removeCapability);
	}

	@Override
	public void removeAllCapabilities() {
		removeCapabilities(entities.capabilities.keySet());
	}

	@Override
	public void addCharacteristic(@NotNull final Characteristic characteristic) {
		checkNotExists(characteristic, "characteristic", entities.characteristics::containsKey);
		/* add the characteristic, containedBy map */
		entities.characteristics.put(characteristic.getId(), characteristic);
		relations.containedBy.put(characteristic.getId(), new ConcurrentHashMap<>());
		publisher.post(eventFactory.build(ADDED, characteristic));
	}

	@Override
	public void addCharacteristics(@NotNull final Collection<Characteristic> characteristics) {
		checkNotNull(characteristics, "characteristic");
		characteristics.parallelStream().forEach(this::addCharacteristic);
	}

	@Override
	public Characteristic getCharacteristic(@NotNull final UniqueId<Characteristic> id) {
		checkNotNull(id, "id");
		return entities.characteristics.get(id);
	}

	@Override
	public Set<Characteristic> getCharacteristics() {
		return entities.characteristics.values().parallelStream().collect(Collectors.toCollection(HashSet::new));
	}

	@Override
	public void removeCharacteristic(@NotNull final UniqueId<Characteristic> id) {
		checkNotNull(id, "id");
		if (entities.characteristics.containsKey(id)) {
			/* remove characteristics, all associated contains relations */
			final Characteristic characteristic = entities.characteristics.remove(id);
			remove(id, relations.containedBy, "containedBy", c -> removeContains(c, id), Role.class);
			publisher.post(eventFactory.build(REMOVED, characteristic));
		}
	}

	@Override
	public void removeCharacteristics(@NotNull final Collection<UniqueId<Characteristic>> ids) {
		checkNotNull(ids, "ids");
		ids.parallelStream().forEach(this::removeCharacteristic);
	}

	@Override
	public void removeAllCharacteristic() {
		removeCharacteristics(entities.characteristics.keySet());
	}

	@Override
	public void addInstanceGoal(@NotNull final InstanceGoal goal) {
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
		publisher.post(eventFactory.build(ADDED, goal));
	}

	@Override
	public void addInstanceGoals(@NotNull final Collection<InstanceGoal> goals) {
		checkNotNull(goals, "goals");
		goals.parallelStream().forEach(this::addInstanceGoal);
	}

	@Override
	public InstanceGoal getInstanceGoal(@NotNull final UniqueId<InstanceGoal> id) {
		checkNotNull(id, "id");
		return entities.instanceGoals.get(id);
	}

	@Override
	public Set<InstanceGoal> getInstanceGoals() {
		return entities.instanceGoals.values().parallelStream().collect(Collectors.toCollection(HashSet::new));
	}

	@Override
	public void removeInstanceGoal(@NotNull final UniqueId<InstanceGoal> id) {
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
			publisher.post(eventFactory.build(REMOVED, goal));
		}
	}

	@Override
	public void removeInstanceGoals(@NotNull final Collection<UniqueId<InstanceGoal>> ids) {
		checkNotNull(ids, "ids");
		ids.parallelStream().forEach(this::removeInstanceGoal);
	}

	@Override
	public void removeAllInstanceGoals() {
		removeInstanceGoals(entities.instanceGoals.keySet());
	}

	@Override
	public void addPmf(@NotNull final Pmf pmf) {
		checkNotExists(pmf, "pmf", entities.pmfs::containsKey);
		/* add the pmf */
		entities.pmfs.put(pmf.getId(), pmf);
		publisher.post(eventFactory.build(ADDED, pmf));
	}

	@Override
	public void addPmfs(@NotNull final Collection<Pmf> pmfs) {
		checkNotNull(pmfs, "pmfs");
		pmfs.parallelStream().forEach(this::addPmf);
	}

	@Override
	public Pmf getPmf(@NotNull final UniqueId<Pmf> id) {
		checkNotNull(id, "id");
		return entities.pmfs.get(id);
	}

	@Override
	public Set<Pmf> getPmfs() {
		return entities.pmfs.values().parallelStream().collect(Collectors.toCollection(HashSet::new));
	}

	@Override
	public void removePmf(@NotNull final UniqueId<Pmf> id) {
		checkNotNull(id, "id");
		if (entities.pmfs.containsKey(id)) {
			/* remove the pmf, all associated moderates relations */
			final Pmf pmf = entities.pmfs.remove(id);
			if (relations.moderates.containsKey(id)) {
				removeModerates(id, relations.moderates.get(id).getAttribute().getId());
			}
			publisher.post(eventFactory.build(REMOVED, pmf));
		}
	}

	@Override
	public void removePmfs(@NotNull final Collection<UniqueId<Pmf>> ids) {
		checkNotNull(ids, "ids");
		ids.parallelStream().forEach(this::removePmf);
	}

	@Override
	public void removeAllPmfs() {
		removePmfs(entities.pmfs.keySet());
	}

	@Override
	public void addPolicy(@NotNull final Policy policy) {
		checkNotExists(policy, "policy", entities.policies::containsKey);
		/* add the policy */
		entities.policies.put(policy.getId(), policy);
		publisher.post(eventFactory.build(ADDED, policy));
	}

	@Override
	public void addPolicies(@NotNull final Collection<Policy> policies) {
		checkNotNull(policies, "policies");
		policies.parallelStream().forEach(this::addPolicy);
	}

	@Override
	public Policy getPolicy(@NotNull final UniqueId<Policy> id) {
		checkNotNull(id, "id");
		return entities.policies.get(id);
	}

	@Override
	public Set<Policy> getPolicies() {
		return entities.policies.values().parallelStream().collect(Collectors.toCollection(HashSet::new));
	}

	@Override
	public void removePolicy(@NotNull final UniqueId<Policy> id) {
		checkNotNull(id, "id");
		if (entities.policies.containsKey(id)) {
			/* remove the policy */
			final Policy policy = entities.policies.remove(id);
			publisher.post(eventFactory.build(REMOVED, policy));
		}
	}

	@Override
	public void removePolicies(@NotNull final Collection<UniqueId<Policy>> ids) {
		checkNotNull(ids, "ids");
		ids.parallelStream().forEach(this::removePolicy);
	}

	@Override
	public void removeAllPolicies() {
		removePolicies(entities.policies.keySet());
	}

	@Override
	public void addRole(@NotNull final Role role) {
		checkNotExists(role, "role", entities.roles::containsKey);
		/* add the role, achieves map, requires map, needs map, uses, contains map */
		entities.roles.put(role.getId(), role);
		relations.achieves.put(role.getId(), new ConcurrentHashMap<>());
		relations.requires.put(role.getId(), new ConcurrentHashMap<>());
		relations.needs.put(role.getId(), new ConcurrentHashMap<>());
		relations.uses.put(role.getId(), new ConcurrentHashMap<>());
		relations.contains.put(role.getId(), new ConcurrentHashMap<>());
		functions.goodnesses.put(role.getId(), goodness);
		publisher.post(eventFactory.build(ADDED, role));
	}

	@Override
	public void addRoles(@NotNull final Collection<Role> roles) {
		checkNotNull(roles, "roles");
		roles.parallelStream().forEach(this::addRole);
	}

	@Override
	public Role getRole(@NotNull final UniqueId<Role> id) {
		checkNotNull(id, "id");
		return entities.roles.get(id);
	}

	@Override
	public Set<Role> getRoles() {
		return entities.roles.values().parallelStream().collect(Collectors.toCollection(HashSet::new));
	}

	@Override
	public void removeRole(@NotNull final UniqueId<Role> id) {
		checkNotNull(id, "id");
		if (entities.roles.containsKey(id)) {
			/*
			 * remove role, all associated achieves relations, all associated requires relations, all associated needs relations, all associated uses relations,
			 * all associated contains relations
			 */
			final Role role = entities.roles.remove(id);
			remove(id, relations.achieves, "achieves", c -> removeAchieves(id, c), SpecificationGoal.class);
			remove(id, relations.requires, "requires", c -> removeRequires(id, c), Capability.class);
			remove(id, relations.needs, "needs", c -> removeNeeds(id, c), Attribute.class);
			remove(id, relations.uses, "uses", c -> removeUses(id, c), Pmf.class);
			remove(id, relations.contains, "contains", c -> removeContains(id, c), Characteristic.class);
			functions.goodnesses.remove(id);
			publisher.post(eventFactory.build(REMOVED, role));
		}
	}

	@Override
	public void removeRoles(@NotNull final Collection<UniqueId<Role>> ids) {
		checkNotNull(ids, "ids");
		ids.parallelStream().forEach(this::removeRole);
	}

	@Override
	public void removeAllRoles() {
		removeRoles(entities.roles.keySet());
	}

	@Override
	public void addSpecificationGoal(@NotNull final SpecificationGoal goal) {
		checkNotExists(goal, "goal", entities.specificationGoals::containsKey);
		/* add the specification goal, instanceGoalsBySpecificationGoal map, achievedBy map */
		entities.specificationGoals.put(goal.getId(), goal);
		entities.instanceGoalsBySpecificationGoal.put(goal.getId(), new ConcurrentHashMap<>());
		relations.achievedBy.put(goal.getId(), new ConcurrentHashMap<>());
		publisher.post(eventFactory.build(ADDED, goal));
	}

	@Override
	public void addSpecificationGoals(@NotNull final Collection<SpecificationGoal> goals) {
		checkNotNull(goals, "goals");
		goals.parallelStream().forEach(this::addSpecificationGoal);
	}

	@Override
	public SpecificationGoal getSpecificationGoal(@NotNull final UniqueId<SpecificationGoal> id) {
		checkNotNull(id, "id");
		return entities.specificationGoals.get(id);
	}

	@Override
	public Set<SpecificationGoal> getSpecificationGoals() {
		return entities.specificationGoals.values().parallelStream().collect(Collectors.toCollection(HashSet::new));
	}

	@Override
	public void removeSpecificationGoal(@NotNull final UniqueId<SpecificationGoal> id) {
		checkNotNull(id, "id");
		if (entities.specificationGoals.containsKey(id)) {
			/* remove the specification goal, all associated instance goals, all associated achieves relations */
			final SpecificationGoal goal = entities.specificationGoals.remove(id);
			remove(id, entities.instanceGoalsBySpecificationGoal, "instanceGoalsBySpecificationGoal", c -> removeInstanceGoal(c), InstanceGoal.class);
			remove(id, relations.achievedBy, "achievedBy", c -> removeAchieves(c, id), Role.class);
			publisher.post(eventFactory.build(REMOVED, goal));
		}
	}

	@Override
	public void removeSpecificationGoals(@NotNull final Collection<UniqueId<SpecificationGoal>> ids) {
		checkNotNull(ids, "ids");
		ids.parallelStream().forEach(this::removeSpecificationGoal);
	}

	@Override
	public void removeAllSpecificationGoals() {
		removeSpecificationGoals(entities.specificationGoals.keySet());
	}

	@Override
	public void addAchieves(@NotNull final UniqueId<Role> roleId, @NotNull final UniqueId<SpecificationGoal> goalId) {
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
		publisher.post(eventFactory.build(ADDED, achieves));
	}

	@Override
	public Set<SpecificationGoal> getAchieves(@NotNull final UniqueId<Role> id) {
		checkNotNull(id, "id");
		return get(id, relations.achieves, Achieves::getGoal);
	}

	@Override
	public Set<Role> getAchievedBy(@NotNull final UniqueId<SpecificationGoal> id) {
		checkNotNull(id, "id");
		return get(id, relations.achievedBy, Achieves::getRole);
	}

	@Override
	public void removeAchieves(@NotNull final UniqueId<Role> roleId, @NotNull final UniqueId<SpecificationGoal> goalId) {
		checkNotNull(roleId, "roleId");
		checkNotNull(goalId, "goalId");
		if (relations.achieves.containsKey(roleId) && relations.achieves.get(roleId).containsKey(goalId)) {
			final Achieves achieves = relations.achieves.get(roleId).remove(goalId);
			removeBy(goalId, roleId, relations.achievedBy, "achievedBy");
			publisher.post(eventFactory.build(REMOVED, achieves));
		}
	}

	@Override
	public void removeAllAchieves() {
		removeAll(relations.achieves, this::removeAchieves, f -> f.getRole().getId(), f -> f.getGoal().getId());
	}

	@Override
	public void addAssignment(@NotNull final Assignment assignment) {
		checkNotExists(assignment, "assignment", relations.assignments::containsKey);
		checkExists(assignment.getAgent().getId(), null, this::getAgent);
		checkExists(assignment.getRole().getId(), null, this::getRole);
		checkExists(assignment.getGoal().getId(), null, this::getInstanceGoal);
		/* add the assignment */
		relations.assignments.put(assignment.getId(), assignment);
		relations.assignmentsByAgent.get(assignment.getAgent().getId()).put(assignment.getId(), assignment);
		publisher.post(eventFactory.build(ADDED, assignment));
	}

	@Override
	public void addAssignments(@NotNull final Collection<Assignment> assignments) {
		checkNotNull(assignments, "assignments");
		assignments.parallelStream().forEach(this::addAssignment);
	}

	@Override
	public Assignment getAssignment(@NotNull final UniqueId<Assignment> id) {
		checkNotNull(id, "id");
		return relations.assignments.get(id);
	}

	@Override
	public Set<Assignment> getAssignments() {
		return relations.assignments.values().parallelStream().collect(Collectors.toCollection(HashSet::new));
	}

	@Override
	public Set<Assignment> getAssignmentsByAgent(@NotNull final UniqueId<Agent> id) {
		checkNotNull(id, "id");
		if (relations.assignmentsByAgent.containsKey(id)) {
			return relations.assignmentsByAgent.get(id).values().parallelStream().collect(Collectors.toCollection(HashSet::new));
		}
		return new HashSet<>();
	}

	@Override
	public void removeAssignment(@NotNull final UniqueId<Assignment> id) {
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
			publisher.post(eventFactory.build(REMOVED, assignment));
		}
	}

	@Override
	public void removeAssignments(@NotNull final Collection<UniqueId<Assignment>> ids) {
		checkNotNull(ids, "ids");
		ids.parallelStream().forEach(this::removeAssignment);
	}

	@Override
	public void removeAllAssignments() {
		removeAssignments(relations.assignments.keySet());
	}

	@Override
	public void addContains(@NotNull final UniqueId<Role> roleId, @NotNull final UniqueId<Characteristic> characteristicId, final double value) {
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
		publisher.post(eventFactory.build(ADDED, contains));
	}

	@Override
	public Set<Characteristic> getContains(@NotNull final UniqueId<Role> id) {
		checkNotNull(id, "id");
		return get(id, relations.contains, Contains::getCharacteristic);
	}

	@Override
	public Set<Role> getContainedBy(@NotNull final UniqueId<Characteristic> id) {
		checkNotNull(id, "id");
		return get(id, relations.containedBy, Contains::getRole);
	}

	@Override
	public Double getContainsValue(@NotNull final UniqueId<Role> roleId, @NotNull final UniqueId<Characteristic> characteristicId) {
		checkNotNull(roleId, "roleId");
		checkNotNull(characteristicId, "characteristicId");
		if (relations.contains.containsKey(roleId) && relations.contains.get(roleId).containsKey(characteristicId)) {
			return relations.contains.get(roleId).get(characteristicId).getValue();
		}
		return null;
	}

	@Override
	public void setContainsValue(@NotNull final UniqueId<Role> roleId, @NotNull final UniqueId<Characteristic> characteristicId, final double value) {
		checkNotNull(roleId, "roleId");
		checkNotNull(characteristicId, "characteristicId");
		if (relations.contains.containsKey(roleId) && relations.contains.get(roleId).containsKey(characteristicId)) {
			final Contains contains = relations.contains.get(roleId).get(characteristicId);
			contains.setValue(value);
			publisher.post(eventFactory.build(CHANGED, contains));
		}
	}

	@Override
	public void removeContains(@NotNull final UniqueId<Role> roleId, @NotNull final UniqueId<Characteristic> characteristicId) {
		checkNotNull(roleId, "roleId");
		checkNotNull(characteristicId, "characteristicId");
		if (relations.contains.containsKey(roleId) && relations.contains.get(roleId).containsKey(characteristicId)) {
			final Contains contains = relations.contains.get(roleId).remove(characteristicId);
			removeBy(characteristicId, roleId, relations.containedBy, "containedBy");
			publisher.post(eventFactory.build(REMOVED, contains));
		}
	}

	@Override
	public void removeAllContains() {
		removeAll(relations.contains, this::removeContains, f -> f.getRole().getId(), f -> f.getCharacteristic().getId());
	}

	@Override
	public void addHas(@NotNull final UniqueId<Agent> agentId, @NotNull final UniqueId<Attribute> attributeId, final double value) {
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
		publisher.post(eventFactory.build(ADDED, has));
	}

	@Override
	public Set<Attribute> getHas(@NotNull final UniqueId<Agent> id) {
		checkNotNull(id, "id");
		return get(id, relations.has, Has::getAttribute);
	}

	@Override
	public Set<Agent> getHadBy(@NotNull final UniqueId<Attribute> id) {
		checkNotNull(id, "id");
		return get(id, relations.hadBy, Has::getAgent);
	}

	@Override
	public Double getHasValue(@NotNull final UniqueId<Agent> agentId, @NotNull final UniqueId<Attribute> attributeId) {
		checkNotNull(agentId, "agentId");
		checkNotNull(attributeId, "attributeId");
		if (relations.has.containsKey(agentId) && relations.has.get(agentId).containsKey(attributeId)) {
			return relations.has.get(agentId).get(attributeId).getValue();
		}
		return null;
	}

	@Override
	public void setHasValue(@NotNull final UniqueId<Agent> agentId, @NotNull final UniqueId<Attribute> attributeId, final double value) {
		checkNotNull(agentId, "agentId");
		checkNotNull(attributeId, "attributeId");
		if (relations.has.containsKey(agentId) && relations.has.get(agentId).containsKey(attributeId)) {
			final Has has = relations.has.get(agentId).get(attributeId);
			has.setValue(value);
			publisher.post(eventFactory.build(CHANGED, has));
		}
	}

	@Override
	public void removeHas(@NotNull final UniqueId<Agent> agentId, @NotNull final UniqueId<Attribute> attributeId) {
		checkNotNull(agentId, "agentId");
		checkNotNull(attributeId, "attributeId");
		if (relations.has.containsKey(agentId) && relations.has.get(agentId).containsKey(attributeId)) {
			final Has has = relations.has.get(agentId).remove(attributeId);
			removeBy(attributeId, agentId, relations.hadBy, "hadBy");
			publisher.post(eventFactory.build(REMOVED, has));
		}
	}

	@Override
	public void removeAllHas() {
		removeAll(relations.has, this::removeHas, f -> f.getAgent().getId(), f -> f.getAttribute().getId());
	}

	@Override
	public void addModerates(@NotNull final UniqueId<Pmf> pmfId, @NotNull final UniqueId<Attribute> attributeId) {
		final Pmf pmf = checkExists(pmfId, "pmfId", this::getPmf);
		final Attribute attribute = checkExists(attributeId, "attributeId", this::getAttribute);
		if (relations.moderates.containsKey(pmfId)) {
			return;
		}
		final Moderates moderates = relationFactory.buildModerates(pmf, attribute);
		relations.moderates.put(pmfId, moderates);
		addBy(moderates, relations.moderatedBy, "moderatedBy", attributeId, pmfId);
		publisher.post(eventFactory.build(ADDED, moderates));
	}

	@Override
	public Attribute getModerates(@NotNull final UniqueId<Pmf> id) {
		checkNotNull(id, "id");
		if (relations.moderates.containsKey(id)) {
			return relations.moderates.get(id).getAttribute();
		}
		return null;
	}

	@Override
	public Set<Pmf> getModeratedBy(@NotNull final UniqueId<Attribute> id) {
		checkNotNull(id, "id");
		return get(id, relations.moderatedBy, Moderates::getPmf);
	}

	@Override
	public void removeModerates(@NotNull final UniqueId<Pmf> pmfId, @NotNull final UniqueId<Attribute> attributeId) {
		checkNotNull(pmfId, "pmfId");
		checkNotNull(attributeId, "attributeId");
		if (relations.moderates.containsKey(pmfId)) {
			final Moderates moderates = relations.moderates.remove(pmfId);
			removeBy(attributeId, pmfId, relations.moderatedBy, "moderatedBy");
			publisher.post(eventFactory.build(REMOVED, moderates));
		}
	}

	@Override
	public void removeAllModerates() {
		relations.moderates.values().parallelStream().collect(Collectors.toCollection(HashSet::new)).parallelStream()
				.forEach(c -> removeModerates(c.getPmf().getId(), c.getAttribute().getId()));
	}

	@Override
	public void addNeeds(@NotNull final UniqueId<Role> roleId, @NotNull final UniqueId<Attribute> attributeId) {
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
		publisher.post(eventFactory.build(ADDED, needs));
	}

	@Override
	public Set<Attribute> getNeeds(@NotNull final UniqueId<Role> id) {
		checkNotNull(id, "id");
		return get(id, relations.needs, Needs::getAttribute);
	}

	@Override
	public Set<Role> getNeededBy(@NotNull final UniqueId<Attribute> id) {
		checkNotNull(id, "id");
		return get(id, relations.neededBy, Needs::getRole);
	}

	@Override
	public void removeNeeds(@NotNull final UniqueId<Role> roleId, @NotNull final UniqueId<Attribute> attributeId) {
		checkNotNull(roleId, "roleId");
		checkNotNull(attributeId, "attributeId");
		if (relations.needs.containsKey(roleId) && relations.needs.get(roleId).containsKey(attributeId)) {
			final Needs needs = relations.needs.get(roleId).remove(attributeId);
			removeBy(attributeId, roleId, relations.neededBy, "neededBy");
			publisher.post(eventFactory.build(REMOVED, needs));
		}
	}

	@Override
	public void removeAllNeeds() {
		removeAll(relations.needs, this::removeNeeds, f -> f.getRole().getId(), f -> f.getAttribute().getId());
	}

	@Override
	public void addPossesses(@NotNull final UniqueId<Agent> agentId, @NotNull final UniqueId<Capability> capabilityId, final double score) {
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
		publisher.post(eventFactory.build(possesses, ADDED));
	}

	@Override
	public Set<Capability> getPossesses(@NotNull final UniqueId<Agent> id) {
		checkNotNull(id, "id");
		return get(id, relations.possesses, Possesses::getCapability);
	}

	@Override
	public Set<Agent> getPossessedBy(@NotNull final UniqueId<Capability> id) {
		checkNotNull(id, "id");
		return get(id, relations.possessedBy, Possesses::getAgent);
	}

	@Override
	public double getPossessesScore(@NotNull final UniqueId<Agent> agentId, @NotNull final UniqueId<Capability> capabilityId) {
		checkNotNull(agentId, "agentId");
		checkNotNull(capabilityId, "capabilityId");
		if (relations.possesses.containsKey(agentId) && relations.possesses.get(agentId).containsKey(capabilityId)) {
			return relations.possesses.get(agentId).get(capabilityId).getScore();
		}
		return Possesses.MIN_SCORE;
	}

	@Override
	public void setPossessesScore(@NotNull final UniqueId<Agent> agentId, @NotNull final UniqueId<Capability> capabilityId, final double score) {
		checkNotNull(agentId, "agentId");
		checkNotNull(capabilityId, "capabilityId");
		if (relations.possesses.containsKey(agentId) && relations.possesses.get(agentId).containsKey(capabilityId)) {
			final Possesses possesses = relations.possesses.get(agentId).get(capabilityId);
			possesses.setScore(score);
			publisher.post(eventFactory.build(possesses, CHANGED));
		}
	}

	@Override
	public void removePossesses(@NotNull final UniqueId<Agent> agentId, @NotNull final UniqueId<Capability> capabilityId) {
		checkNotNull(agentId, "agentId");
		checkNotNull(capabilityId, "capabilityId");
		if (relations.possesses.containsKey(agentId) && relations.possesses.get(agentId).containsKey(capabilityId)) {
			final Possesses possesses = relations.possesses.get(agentId).remove(capabilityId);
			removeBy(capabilityId, agentId, relations.possessedBy, "possessedBy");
			publisher.post(eventFactory.build(possesses, REMOVED));
		}
	}

	@Override
	public void removeAllPossesses() {
		removeAll(relations.possesses, this::removePossesses, f -> f.getAgent().getId(), f -> f.getCapability().getId());
	}

	@Override
	public void addRequires(@NotNull final UniqueId<Role> roleId, @NotNull final UniqueId<Capability> capabilityId) {
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
		publisher.post(eventFactory.build(requires, ADDED));
	}

	@Override
	public Set<Capability> getRequires(@NotNull final UniqueId<Role> id) {
		checkNotNull(id, "id");
		return get(id, relations.requires, Requires::getCapability);
	}

	@Override
	public Set<Role> getRequiredBy(@NotNull final UniqueId<Capability> id) {
		checkNotNull(id, "id");
		return get(id, relations.requiredBy, Requires::getRole);
	}

	@Override
	public void removeRequires(@NotNull final UniqueId<Role> roleId, @NotNull final UniqueId<Capability> capabilityId) {
		checkNotNull(roleId, "roleId");
		checkNotNull(capabilityId, "capabilityId");
		if (relations.requires.containsKey(roleId) && relations.requires.get(roleId).containsKey(capabilityId)) {
			final Requires requires = relations.requires.get(roleId).remove(capabilityId);
			removeBy(capabilityId, roleId, relations.requiredBy, "requiredBy");
			publisher.post(eventFactory.build(requires, REMOVED));
		}
	}

	@Override
	public void removeAllRequires() {
		removeAll(relations.requires, this::removeRequires, f -> f.getRole().getId(), f -> f.getCapability().getId());
	}

	@Override
	public void addUses(@NotNull final UniqueId<Role> roleId, @NotNull final UniqueId<Pmf> pmfId) {
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
		publisher.post(eventFactory.build(uses, ADDED));
	}

	@Override
	public Set<Pmf> getUses(@NotNull final UniqueId<Role> id) {
		checkNotNull(id, "id");
		return get(id, relations.uses, Uses::getPmf);
	}

	@Override
	public Set<Role> getUsedBy(@NotNull final UniqueId<Pmf> id) {
		checkNotNull(id, "id");
		return get(id, relations.usedBy, Uses::getRole);
	}

	@Override
	public void removeUses(@NotNull final UniqueId<Role> roleId, @NotNull final UniqueId<Pmf> pmfId) {
		checkNotNull(roleId, "roleId");
		checkNotNull(pmfId, "pmfId");
		if (relations.uses.containsKey(roleId) && relations.uses.get(roleId).containsKey(pmfId)) {
			final Uses uses = relations.uses.get(roleId).remove(pmfId);
			removeBy(pmfId, roleId, relations.usedBy, "usedBy");
			publisher.post(eventFactory.build(uses, REMOVED));
		}
	}

	@Override
	public void removeAllUses() {
		removeAll(relations.uses, this::removeUses, f -> f.getRole().getId(), f -> f.getPmf().getId());
	}

	@Override
	public double effectiveness(@NotNull final Collection<Assignment> assignments) {
		return effectiveness.compute(this, assignments);
	}

	@Override
	public Goodness getGoodness(@NotNull final UniqueId<Role> id) {
		checkNotNull(id, "id");
		return functions.goodnesses.get(id);
	}

	@Override
	public void setGoodness(@NotNull final UniqueId<Role> id, @NotNull final Goodness goodness) {
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
