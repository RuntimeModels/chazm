package runtimemodels.chazm.model

import io.reactivex.FlowableEmitter
import io.reactivex.FlowableOnSubscribe
import runtimemodels.chazm.api.Organization
import runtimemodels.chazm.api.entity.*
import runtimemodels.chazm.api.function.Effectiveness
import runtimemodels.chazm.api.function.Goodness
import runtimemodels.chazm.api.id.Identifiable
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.api.relation.Assignment
import runtimemodels.chazm.model.event.*
import runtimemodels.chazm.model.message.E
import runtimemodels.chazm.model.message.L
import runtimemodels.chazm.model.notification.Publisher
import runtimemodels.chazm.model.relation.RelationFactory
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.function.BiConsumer
import java.util.function.Consumer
import java.util.function.Function
import java.util.function.Predicate
import javax.inject.Inject

internal open class DefaultOrganization @Inject constructor(
    private val relationFactory: RelationFactory,
    private val eventFactory: EventFactory,
    private val goodness: Goodness,
    private val effectiveness: Effectiveness,
    private val publisher: Publisher
) : Organization, FlowableOnSubscribe<Organization> {

    private val entities = Entities()
    private val relations = Relations()
    private val functions = Functions()

    override fun addAgent(agent: Agent) {
        checkNotExists(agent, Predicate { entities.agents.containsKey(it) })
        /* add the agent, assignmentsByAgent map, possesses map, has map */
        entities.agents[agent.id] = agent
        relations.assignmentsByAgent[agent.id] = ConcurrentHashMap()
        relations.possesses[agent.id] = ConcurrentHashMap()
        relations.has[agent.id] = ConcurrentHashMap()
        publisher.post(eventFactory.build(EventType.ADDED, agent))
    }

    override fun addAgents(agents: Collection<Agent>) {
        agents.forEach(::addAgent)
    }

    override fun getAgent(id: UniqueId<Agent>): Agent? {
        return entities.agents[id]
    }

    override fun getAgents(): Set<Agent> {
        return entities.agents.values.toSet()
    }

    override fun removeAgent(id: UniqueId<Agent>) {
        if (entities.agents.containsKey(id)) {
            /* remove the agent, all associated assignments, all associated possesses relations, all associated has relations */
            val agent = entities.agents.remove(id)!!
            remove(id, relations.assignmentsByAgent, ASSIGNMENTS_BY_AGENT, Consumer { removeAssignment(it) })
            remove(id, relations.possesses, POSSESSES, Consumer { removePossesses(id, it) })
            remove(id, relations.has, HAS, Consumer { removeHas(id, it) })
            publisher.post<AgentEvent>(eventFactory.build(EventType.REMOVED, agent))
        }
    }

    override fun removeAgents(ids: Collection<UniqueId<Agent>>) {
        ids.forEach(::removeAgent)
    }

    override fun removeAllAgents() {
        removeAgents(entities.agents.keys)
    }

    override fun addAttribute(attribute: Attribute) {
        checkNotExists(attribute, Predicate { entities.attributes.containsKey(it) })
        /* add the attribute, neededBy map, hadBy map, moderatedBy map */
        entities.attributes[attribute.id] = attribute
        relations.neededBy[attribute.id] = ConcurrentHashMap()
        relations.hadBy[attribute.id] = ConcurrentHashMap()
        relations.moderatedBy[attribute.id] = ConcurrentHashMap()
        publisher.post<AttributeEvent>(eventFactory.build(EventType.ADDED, attribute))
    }

    override fun addAttributes(attributes: Collection<Attribute>) {
        attributes.forEach(::addAttribute)
    }

    override fun getAttribute(id: UniqueId<Attribute>): Attribute? {
        return entities.attributes[id]
    }

    override fun getAttributes(): Set<Attribute> {
        return entities.attributes.values.toSet()
    }

    override fun removeAttribute(id: UniqueId<Attribute>) {
        if (entities.attributes.containsKey(id)) {
            /* remove the attribute, all associated needs relations, all associated has relations, all associated moderates relations */
            val attribute = entities.attributes.remove(id)!!
            remove(id, relations.neededBy, NEEDED_BY, Consumer { removeNeeds(it, id) })
            remove(id, relations.hadBy, HAD_BY, Consumer { removeHas(it, id) })
            remove(id, relations.moderatedBy, MODERATED_BY, Consumer { removeModerates(it, id) })
            publisher.post<AttributeEvent>(eventFactory.build(EventType.REMOVED, attribute))
        }
    }

    override fun removeAttributes(ids: Collection<UniqueId<Attribute>>) {
        ids.forEach(::removeAttribute)
    }

    override fun removeAllAttributes() {
        removeAttributes(entities.attributes.keys)
    }

    override fun addCapability(capability: Capability) {
        checkNotExists(capability, Predicate { entities.capabilities.containsKey(it) })
        /* add the capability, requiredBy map, possessedBy map */
        entities.capabilities[capability.id] = capability
        relations.requiredBy[capability.id] = ConcurrentHashMap()
        relations.possessedBy[capability.id] = ConcurrentHashMap()
        publisher.post<CapabilityEvent>(eventFactory.build(EventType.ADDED, capability))
    }

    override fun addCapabilities(capabilities: Collection<Capability>) {
        capabilities.forEach(::addCapability)
    }

    override fun getCapability(id: UniqueId<Capability>): Capability? {
        return entities.capabilities[id]
    }

    override fun getCapabilities(): Set<Capability> {
        return entities.capabilities.values.toSet()
    }

    override fun removeCapability(id: UniqueId<Capability>) {
        if (entities.capabilities.containsKey(id)) {
            /* remove the capability, all associated requires relations, all associated possesses relations */
            val capability = entities.capabilities.remove(id)!!
            remove(id, relations.requiredBy, REQUIRED_BY, Consumer { removeRequires(it, id) })
            remove(id, relations.possessedBy, POSSESSED_BY, Consumer { removePossesses(it, id) })
            publisher.post<CapabilityEvent>(eventFactory.build(EventType.REMOVED, capability))
        }
    }

    override fun removeCapabilities(ids: Collection<UniqueId<Capability>>) {
        ids.forEach(::removeCapability)
    }

    override fun removeAllCapabilities() {
        removeCapabilities(entities.capabilities.keys)
    }

    override fun addCharacteristic(characteristic: Characteristic) {
        checkNotExists(characteristic, Predicate { entities.characteristics.containsKey(it) })
        /* add the characteristic, containedBy map */
        entities.characteristics[characteristic.id] = characteristic
        relations.containedBy[characteristic.id] = ConcurrentHashMap()
        publisher.post<CharacteristicEvent>(eventFactory.build(EventType.ADDED, characteristic))
    }

    override fun addCharacteristics(characteristics: Collection<Characteristic>) {
        characteristics.forEach(::addCharacteristic)
    }

    override fun getCharacteristic(id: UniqueId<Characteristic>): Characteristic? {
        return entities.characteristics[id]
    }

    override fun getCharacteristics(): Set<Characteristic> {
        return entities.characteristics.values.toSet()
    }

    override fun removeCharacteristic(id: UniqueId<Characteristic>) {
        if (entities.characteristics.containsKey(id)) {
            /* remove characteristics, all associated contains relations */
            val characteristic = entities.characteristics.remove(id)!!
            remove(id, relations.containedBy, CONTAINED_BY, Consumer { removeContains(it, id) })
            publisher.post<CharacteristicEvent>(eventFactory.build(EventType.REMOVED, characteristic))
        }
    }

    override fun removeCharacteristics(ids: Collection<UniqueId<Characteristic>>) {
        ids.forEach(::removeCharacteristic)
    }

    override fun removeAllCharacteristic() {
        removeCharacteristics(entities.characteristics.keys)
    }

    override fun addInstanceGoal(goal: InstanceGoal) {
        checkNotExists(goal, Predicate { entities.instanceGoals.containsKey(it) })
        checkExists(goal.goal.id, Function { getSpecificationGoal(it) })
        /* add the instance goal, instanceGoalsBySpecificationGoal map */
        entities.instanceGoals[goal.id] = goal
        val map = get(goal.goal.id, entities.instanceGoalsBySpecificationGoal,
            INSTANCE_GOALS_BY_SPECIFICATION_GOAL)
        map[goal.id] = goal
        publisher.post<InstanceGoalEvent>(eventFactory.build(EventType.ADDED, goal))
    }

    override fun addInstanceGoals(goals: Collection<InstanceGoal>) {
        goals.forEach(::addInstanceGoal)
    }

    override fun getInstanceGoal(id: UniqueId<InstanceGoal>): InstanceGoal? {
        return entities.instanceGoals[id]
    }

    override fun getInstanceGoals(): Set<InstanceGoal> {
        return entities.instanceGoals.values.toSet()
    }

    override fun removeInstanceGoal(id: UniqueId<InstanceGoal>) {
        if (entities.instanceGoals.containsKey(id)) {
            /* remove the instance goal, instanceGoalsBySpecificationGoal map */
            val goal = entities.instanceGoals.remove(id)!!
            if (entities.instanceGoalsBySpecificationGoal.containsKey(goal.goal.id)) {
                val map = entities.instanceGoalsBySpecificationGoal[goal.goal.id]!!
                if (map.containsKey(goal.id)) {
                    map.remove(goal.id)
                } else {
                    log.warn(L.MAP_IS_MISSING_ENTRY.get(), INSTANCE_GOALS_BY_SPECIFICATION_GOAL, goal.goal.id, goal.id)
                }
            } else {
                log.warn(L.MAP_IS_MISSING_KEY.get(), INSTANCE_GOALS_BY_SPECIFICATION_GOAL, goal.goal.id)
                entities.instanceGoalsBySpecificationGoal[goal.goal.id] = ConcurrentHashMap()
            }
            publisher.post<InstanceGoalEvent>(eventFactory.build(EventType.REMOVED, goal))
        }
    }

    override fun removeInstanceGoals(ids: Collection<UniqueId<InstanceGoal>>) {
        ids.forEach(::removeInstanceGoal)
    }

    override fun removeAllInstanceGoals() {
        removeInstanceGoals(entities.instanceGoals.keys)
    }

    override fun addPmf(pmf: Pmf) {
        checkNotExists(pmf, Predicate { entities.pmfs.containsKey(it) })
        /* add the pmf */
        entities.pmfs[pmf.id] = pmf
        publisher.post<PmfEvent>(eventFactory.build(EventType.ADDED, pmf))
    }

    override fun addPmfs(pmfs: Collection<Pmf>) {
        pmfs.forEach(::addPmf)
    }

    override fun getPmf(id: UniqueId<Pmf>): Pmf? {
        return entities.pmfs[id]
    }

    override fun getPmfs(): Set<Pmf> {
        return entities.pmfs.values.toSet()
    }

    override fun removePmf(id: UniqueId<Pmf>) {
        if (entities.pmfs.containsKey(id)) {
            /* remove the pmf, all associated moderates relations */
            val pmf = entities.pmfs.remove(id)!!
            if (relations.moderates.containsKey(id)) {
                removeModerates(id, relations.moderates[id]!!.attribute.id)
            }
            publisher.post<PmfEvent>(eventFactory.build(EventType.REMOVED, pmf))
        }
    }

    override fun removePmfs(ids: Collection<UniqueId<Pmf>>) {
        ids.forEach(::removePmf)
    }

    override fun removeAllPmfs() {
        removePmfs(entities.pmfs.keys)
    }

    override fun addPolicy(policy: Policy) {
        checkNotExists(policy, Predicate { entities.policies.containsKey(it) })
        /* add the policy */
        entities.policies[policy.id] = policy
        publisher.post<PolicyEvent>(eventFactory.build(EventType.ADDED, policy))
    }

    override fun addPolicies(policies: Collection<Policy>) {
        policies.forEach(::addPolicy)
    }

    override fun getPolicy(id: UniqueId<Policy>): Policy? {
        return entities.policies[id]
    }

    override fun getPolicies(): Set<Policy> {
        return entities.policies.values.toSet()
    }

    override fun removePolicy(id: UniqueId<Policy>) {
        if (entities.policies.containsKey(id)) {
            /* remove the policy */
            val policy = entities.policies.remove(id)!!
            publisher.post<PolicyEvent>(eventFactory.build(EventType.REMOVED, policy))
        }
    }

    override fun removePolicies(ids: Collection<UniqueId<Policy>>) {
        ids.forEach(::removePolicy)
    }

    override fun removeAllPolicies() {
        removePolicies(entities.policies.keys)
    }

    override fun addRole(role: Role) {
        checkNotExists(role, Predicate { entities.roles.containsKey(it) })
        /* add the role, achieves map, requires map, needs map, uses, contains map */
        entities.roles[role.id] = role
        relations.achieves[role.id] = ConcurrentHashMap()
        relations.requires[role.id] = ConcurrentHashMap()
        relations.needs[role.id] = ConcurrentHashMap()
        relations.uses[role.id] = ConcurrentHashMap()
        relations.contains[role.id] = ConcurrentHashMap()
        functions.goodness[role.id] = goodness
        publisher.post<RoleEvent>(eventFactory.build(EventType.ADDED, role))
    }

    override fun addRoles(roles: Collection<Role>) {
        roles.forEach(::addRole)
    }

    override fun getRole(id: UniqueId<Role>): Role? {
        return entities.roles[id]
    }

    override fun getRoles(): Set<Role> {
        return entities.roles.values.toSet()
    }

    override fun removeRole(id: UniqueId<Role>) {
        if (entities.roles.containsKey(id)) {
            /*
             * remove role, all associated achieves relations, all associated requires relations, all associated needs relations, all associated uses relations,
			 * all associated contains relations
			 */
            val role = entities.roles.remove(id)!!
            remove(id, relations.achieves, ACHIEVES, Consumer { removeAchieves(id, it) })
            remove(id, relations.requires, REQUIRES, Consumer { removeRequires(id, it) })
            remove(id, relations.needs, NEEDS, Consumer { removeNeeds(id, it) })
            remove(id, relations.uses, USES, Consumer { removeUses(id, it) })
            remove(id, relations.contains, CONTAINS, Consumer { removeContains(id, it) })
            functions.goodness.remove(id)
            publisher.post<RoleEvent>(eventFactory.build(EventType.REMOVED, role))
        }
    }

    override fun removeRoles(ids: Collection<UniqueId<Role>>) {
        ids.forEach(::removeRole)
    }

    override fun removeAllRoles() {
        removeRoles(entities.roles.keys)
    }

    override fun addSpecificationGoal(goal: SpecificationGoal) {
        checkNotExists(goal, Predicate { entities.specificationGoals.containsKey(it) })
        /* add the specification goal, instanceGoalsBySpecificationGoal map, achievedBy map */
        entities.specificationGoals[goal.id] = goal
        entities.instanceGoalsBySpecificationGoal[goal.id] = ConcurrentHashMap()
        relations.achievedBy[goal.id] = ConcurrentHashMap()
        publisher.post<SpecificationGoalEvent>(eventFactory.build(EventType.ADDED, goal))
    }

    override fun addSpecificationGoals(goals: Collection<SpecificationGoal>) {
        goals.forEach(::addSpecificationGoal)
    }

    override fun getSpecificationGoal(id: UniqueId<SpecificationGoal>): SpecificationGoal? {
        return entities.specificationGoals[id]
    }

    override fun getSpecificationGoals(): Set<SpecificationGoal> {
        return entities.specificationGoals.values.toSet()
    }

    override fun removeSpecificationGoal(id: UniqueId<SpecificationGoal>) {
        if (entities.specificationGoals.containsKey(id)) {
            /* remove the specification goal, all associated instance goals, all associated achieves relations */
            val goal = entities.specificationGoals.remove(id)!!
            remove(id, entities.instanceGoalsBySpecificationGoal, INSTANCE_GOALS_BY_SPECIFICATION_GOAL, Consumer(::removeInstanceGoal))
            remove(id, relations.achievedBy, ACHIEVED_BY, Consumer { removeAchieves(it, id) })
            publisher.post<SpecificationGoalEvent>(eventFactory.build(EventType.REMOVED, goal))
        }
    }

    override fun removeSpecificationGoals(ids: Collection<UniqueId<SpecificationGoal>>) {
        ids.forEach(::removeSpecificationGoal)
    }

    override fun removeAllSpecificationGoals() {
        removeSpecificationGoals(entities.specificationGoals.keys)
    }

    override fun addAchieves(roleId: UniqueId<Role>, goalId: UniqueId<SpecificationGoal>) {
        val role = checkExists(roleId, Function<UniqueId<Role>, Role?>(::getRole))
        val goal = checkExists(goalId, Function<UniqueId<SpecificationGoal>, SpecificationGoal?>(::getSpecificationGoal))
        val map = get(roleId, relations.achieves, ACHIEVES)
        if (map.containsKey(goalId)) {
            /* relation already exists do nothing */
            return
        }
        val achieves = relationFactory.buildAchieves(role, goal)
        map[goalId] = achieves
        addBy(achieves, relations.achievedBy, ACHIEVED_BY, goalId, roleId)
        publisher.post<AchievesEvent>(eventFactory.build(EventType.ADDED, achieves))
    }

    override fun getAchieves(id: UniqueId<Role>): Set<SpecificationGoal> {
        return get(id, relations.achieves, Function { it.goal })
    }

    override fun getAchievedBy(id: UniqueId<SpecificationGoal>): Set<Role> {
        return get(id, relations.achievedBy, Function { it.role })
    }

    override fun removeAchieves(roleId: UniqueId<Role>, goalId: UniqueId<SpecificationGoal>) {
        if (relations.achieves.containsKey(roleId) && relations.achieves[roleId]!!.containsKey(goalId)) {
            val achieves = relations.achieves[roleId]!!.remove(goalId)!!
            removeBy(goalId, roleId, relations.achievedBy, ACHIEVED_BY)
            publisher.post<AchievesEvent>(eventFactory.build(EventType.REMOVED, achieves))
        }
    }

    override fun removeAllAchieves() {
        removeAll(relations.achieves, BiConsumer(::removeAchieves), Function { it.role.id }, Function { it.goal.id })
    }

    override fun addAssignment(assignment: Assignment) {
        checkNotExists(assignment, Predicate { relations.assignments.containsKey(it) })
        checkExists(assignment.agent.id, Function(::getAgent))
        checkExists(assignment.role.id, Function(::getRole))
        checkExists(assignment.goal.id, Function(::getInstanceGoal))
        /* add the assignment */
        relations.assignments[assignment.id] = assignment
        relations.assignmentsByAgent[assignment.agent.id]!![assignment.id] = assignment
        publisher.post<AssignmentEvent>(eventFactory.build(EventType.ADDED, assignment))
    }

    override fun addAssignments(assignments: Collection<Assignment>) {
        assignments.forEach(::addAssignment)
    }

    override fun getAssignment(id: UniqueId<Assignment>): Assignment? {
        return relations.assignments[id]
    }

    override fun getAssignments(): Set<Assignment> {
        return relations.assignments.values.toSet()
    }

    override fun getAssignmentsByAgent(id: UniqueId<Agent>): Set<Assignment> {
        return if (relations.assignmentsByAgent.containsKey(id)) {
            relations.assignmentsByAgent[id]!!.values.toSet()
        } else HashSet()
    }

    override fun removeAssignment(id: UniqueId<Assignment>) {
        if (relations.assignments.containsKey(id)) {
            val assignment = relations.assignments.remove(id)!!
            if (relations.assignmentsByAgent.containsKey(assignment.agent.id)) {
                if (relations.assignmentsByAgent[assignment.agent.id]!!.remove(id) == null) {
                    log.warn(L.MAP_IS_MISSING_ENTRY.get(), ASSIGNMENTS_BY_AGENT, assignment.agent.id, id)
                }
            } else {
                log.warn(L.MAP_IS_MISSING_KEY.get(), ASSIGNMENTS_BY_AGENT, assignment.agent.id)
                relations.assignmentsByAgent[assignment.agent.id] = ConcurrentHashMap()
            }
            publisher.post<AssignmentEvent>(eventFactory.build(EventType.REMOVED, assignment))
        }
    }

    override fun removeAssignments(ids: Collection<UniqueId<Assignment>>) {
        ids.forEach(::removeAssignment)
    }

    override fun removeAllAssignments() {
        removeAssignments(relations.assignments.keys)
    }

    override fun addContains(roleId: UniqueId<Role>, characteristicId: UniqueId<Characteristic>, value: Double) {
        val role = checkExists(roleId, Function<UniqueId<Role>, Role?>(::getRole))
        val characteristic = checkExists(characteristicId, Function<UniqueId<Characteristic>, Characteristic?>(::getCharacteristic))
        val map = get(roleId, relations.contains, CONTAINS)
        if (map.containsKey(characteristicId)) {
            /* relation already exists do nothing */
            return
        }
        val contains = relationFactory.buildContains(role, characteristic, value)
        map[characteristicId] = contains
        addBy(contains, relations.containedBy, CONTAINED_BY, characteristicId, roleId)
        publisher.post<ContainsEvent>(eventFactory.build(EventType.ADDED, contains))
    }

    override fun getContains(id: UniqueId<Role>): Set<Characteristic> {
        return get(id, relations.contains, Function { it.characteristic })
    }

    override fun getContainedBy(id: UniqueId<Characteristic>): Set<Role> {
        return get(id, relations.containedBy, Function { it.role })
    }

    override fun getContainsValue(roleId: UniqueId<Role>, characteristicId: UniqueId<Characteristic>): Double? {
        return if (relations.contains.containsKey(roleId) && relations.contains[roleId]!!.containsKey(characteristicId)) {
            relations.contains[roleId]!![characteristicId]!!.value
        } else null
    }

    override fun setContainsValue(roleId: UniqueId<Role>, characteristicId: UniqueId<Characteristic>, value: Double) {
        if (relations.contains.containsKey(roleId) && relations.contains[roleId]!!.containsKey(characteristicId)) {
            val contains = relations.contains[roleId]!![characteristicId]!!
            contains.value = value
            publisher.post<ContainsEvent>(eventFactory.build(EventType.UPDATED, contains))
        }
    }

    override fun removeContains(roleId: UniqueId<Role>, characteristicId: UniqueId<Characteristic>) {
        if (relations.contains.containsKey(roleId) && relations.contains[roleId]!!.containsKey(characteristicId)) {
            val contains = relations.contains[roleId]!!.remove(characteristicId)!!
            removeBy(characteristicId, roleId, relations.containedBy, CONTAINED_BY)
            publisher.post<ContainsEvent>(eventFactory.build(EventType.REMOVED, contains))
        }
    }

    override fun removeAllContains() {
        removeAll(relations.contains, BiConsumer(::removeContains), Function { it.role.id }, Function { it.characteristic.id })
    }

    override fun addHas(agentId: UniqueId<Agent>, attributeId: UniqueId<Attribute>, value: Double) {
        val agent = checkExists(agentId, Function<UniqueId<Agent>, Agent?>(::getAgent))
        val attribute = checkExists(attributeId, Function<UniqueId<Attribute>, Attribute?>(::getAttribute))
        val map = get(agentId, relations.has, HAS)
        if (map.containsKey(attributeId)) {
            /* relation already exists do nothing */
            return
        }
        val has = relationFactory.buildHas(agent, attribute, value)
        map[attributeId] = has
        addBy(has, relations.hadBy, HAD_BY, attributeId, agentId)
        publisher.post<HasEvent>(eventFactory.build(EventType.ADDED, has))
    }

    override fun getHas(id: UniqueId<Agent>): Set<Attribute> {
        return get(id, relations.has, Function { it.attribute })
    }

    override fun getHadBy(id: UniqueId<Attribute>): Set<Agent> {
        return get(id, relations.hadBy, Function { it.agent })
    }

    override fun getHasValue(agentId: UniqueId<Agent>, attributeId: UniqueId<Attribute>): Double? {
        return if (relations.has.containsKey(agentId) && relations.has[agentId]!!.containsKey(attributeId)) {
            relations.has[agentId]!![attributeId]!!.value
        } else null
    }

    override fun setHasValue(agentId: UniqueId<Agent>, attributeId: UniqueId<Attribute>, value: Double) {
        if (relations.has.containsKey(agentId) && relations.has[agentId]!!.containsKey(attributeId)) {
            val has = relations.has[agentId]!![attributeId]!!
            has.setValue(value)
            publisher.post<HasEvent>(eventFactory.build(EventType.UPDATED, has))
        }
    }

    override fun removeHas(agentId: UniqueId<Agent>, attributeId: UniqueId<Attribute>) {
        if (relations.has.containsKey(agentId) && relations.has[agentId]!!.containsKey(attributeId)) {
            val has = relations.has[agentId]!!.remove(attributeId)!!
            removeBy(attributeId, agentId, relations.hadBy, HAD_BY)
            publisher.post<HasEvent>(eventFactory.build(EventType.REMOVED, has))
        }
    }

    override fun removeAllHas() {
        removeAll(relations.has, BiConsumer(::removeHas), Function { it.agent.id }, Function { it.attribute.id })
    }

    override fun addModerates(pmfId: UniqueId<Pmf>, attributeId: UniqueId<Attribute>) {
        val pmf = checkExists(pmfId, Function<UniqueId<Pmf>, Pmf?>(::getPmf))
        val attribute = checkExists(attributeId, Function<UniqueId<Attribute>, Attribute?>(::getAttribute))
        if (relations.moderates.containsKey(pmfId)) {
            return
        }
        val moderates = relationFactory.buildModerates(pmf, attribute)
        relations.moderates[pmfId] = moderates
        addBy(moderates, relations.moderatedBy, MODERATED_BY, attributeId, pmfId)
        publisher.post<ModeratesEvent>(eventFactory.build(EventType.ADDED, moderates))
    }

    override fun getModerates(id: UniqueId<Pmf>): Attribute? {
        return if (relations.moderates.containsKey(id)) {
            relations.moderates[id]!!.getAttribute()
        } else null
    }

    override fun getModeratedBy(id: UniqueId<Attribute>): Set<Pmf> {
        return get(id, relations.moderatedBy, Function { it.pmf })
    }

    override fun removeModerates(pmfId: UniqueId<Pmf>, attributeId: UniqueId<Attribute>) {
        if (relations.moderates.containsKey(pmfId)) {
            val moderates = relations.moderates.remove(pmfId)!!
            removeBy(attributeId, pmfId, relations.moderatedBy, MODERATED_BY)
            publisher.post(eventFactory.build(EventType.REMOVED, moderates))
        }
    }

    override fun removeAllModerates() {
        relations.moderates.values.toSet().forEach { removeModerates(it.pmf.id, it.attribute.id) }
    }

    override fun addNeeds(roleId: UniqueId<Role>, attributeId: UniqueId<Attribute>) {
        val role = checkExists(roleId, Function<UniqueId<Role>, Role?>(::getRole))
        val attribute = checkExists(attributeId, Function<UniqueId<Attribute>, Attribute?>(::getAttribute))
        val map = get(roleId, relations.needs, NEEDS)
        if (map.containsKey(attributeId)) {
            /* relation already exists do nothing */
            return
        }
        val needs = relationFactory.buildNeeds(role, attribute)
        map[attributeId] = needs
        addBy(needs, relations.neededBy, NEEDED_BY, attributeId, roleId)
        publisher.post<NeedsEvent>(eventFactory.build(EventType.ADDED, needs))
    }

    override fun getNeeds(id: UniqueId<Role>): Set<Attribute> {
        return get(id, relations.needs, Function { it.attribute })
    }

    override fun getNeededBy(id: UniqueId<Attribute>): Set<Role> {
        return get(id, relations.neededBy, Function { it.role })
    }

    override fun removeNeeds(roleId: UniqueId<Role>, attributeId: UniqueId<Attribute>) {
        if (relations.needs.containsKey(roleId) && relations.needs[roleId]!!.containsKey(attributeId)) {
            val needs = relations.needs[roleId]!!.remove(attributeId)!!
            removeBy(attributeId, roleId, relations.neededBy, NEEDED_BY)
            publisher.post<NeedsEvent>(eventFactory.build(EventType.REMOVED, needs))
        }
    }

    override fun removeAllNeeds() {
        removeAll(relations.needs, BiConsumer(::removeNeeds), Function { it.role.id }, Function { it.attribute.id })
    }

    override fun addPossesses(agentId: UniqueId<Agent>, capabilityId: UniqueId<Capability>, score: Double) {
        val agent = checkExists(agentId, Function<UniqueId<Agent>, Agent?>(::getAgent))
        val capability = checkExists(capabilityId, Function<UniqueId<Capability>, Capability?>(::getCapability))
        val map = get(agentId, relations.possesses, POSSESSES)
        if (map.containsKey(capabilityId)) {
            /* relation already exists do nothing */
            return
        }
        val possesses = relationFactory.buildPossesses(agent, capability, score)
        map[capabilityId] = possesses
        addBy(possesses, relations.possessedBy, POSSESSED_BY, capabilityId, agentId)
        publisher.post<PossessesEvent>(eventFactory.build(EventType.ADDED, possesses))
    }

    override fun getPossesses(id: UniqueId<Agent>): Set<Capability> {
        return get(id, relations.possesses, Function { it.capability })
    }

    override fun getPossessedBy(id: UniqueId<Capability>): Set<Agent> {
        return get(id, relations.possessedBy, Function { it.agent })
    }

    override fun getPossessesScore(agentId: UniqueId<Agent>, capabilityId: UniqueId<Capability>): Double {
        return if (relations.possesses.containsKey(agentId) && relations.possesses[agentId]!!.containsKey(capabilityId)) {
            relations.possesses[agentId]!![capabilityId]!!.score
        } else 0.0
    }

    override fun setPossessesScore(agentId: UniqueId<Agent>, capabilityId: UniqueId<Capability>, score: Double) {
        if (relations.possesses.containsKey(agentId) && relations.possesses[agentId]!!.containsKey(capabilityId)) {
            val possesses = relations.possesses[agentId]!![capabilityId]!!
            possesses.setScore(score)
            publisher.post<PossessesEvent>(eventFactory.build(EventType.UPDATED, possesses))
        }
    }

    override fun removePossesses(agentId: UniqueId<Agent>, capabilityId: UniqueId<Capability>) {
        if (relations.possesses.containsKey(agentId) && relations.possesses[agentId]!!.containsKey(capabilityId)) {
            val possesses = relations.possesses[agentId]!!.remove(capabilityId)!!
            removeBy(capabilityId, agentId, relations.possessedBy, POSSESSED_BY)
            publisher.post<PossessesEvent>(eventFactory.build(EventType.REMOVED, possesses))
        }
    }

    override fun removeAllPossesses() {
        removeAll(relations.possesses, BiConsumer(::removePossesses), Function { it.agent.id }, Function { f -> f.capability.id })
    }

    override fun addRequires(roleId: UniqueId<Role>, capabilityId: UniqueId<Capability>) {
        val role = checkExists(roleId, Function<UniqueId<Role>, Role?>(::getRole))
        val capability = checkExists(capabilityId, Function<UniqueId<Capability>, Capability?>(::getCapability))
        val map = get(roleId, relations.requires, REQUIRES)
        if (map.containsKey(capabilityId)) {
            /* relation already exists do nothing */
            return
        }
        val requires = relationFactory.buildRequires(role, capability)
        map[capabilityId] = requires
        addBy(requires, relations.requiredBy, REQUIRED_BY, capabilityId, roleId)
        publisher.post<RequiresEvent>(eventFactory.build(EventType.ADDED, requires))
    }

    override fun getRequires(id: UniqueId<Role>): Set<Capability> {
        return get(id, relations.requires, Function { it.capability })
    }

    override fun getRequiredBy(id: UniqueId<Capability>): Set<Role> {
        return get(id, relations.requiredBy, Function { it.role })
    }

    override fun removeRequires(roleId: UniqueId<Role>, capabilityId: UniqueId<Capability>) {
        if (relations.requires.containsKey(roleId) && relations.requires[roleId]!!.containsKey(capabilityId)) {
            val requires = relations.requires[roleId]!!.remove(capabilityId)!!
            removeBy(capabilityId, roleId, relations.requiredBy, REQUIRED_BY)
            publisher.post<RequiresEvent>(eventFactory.build(EventType.REMOVED, requires))
        }
    }

    override fun removeAllRequires() {
        removeAll(relations.requires, BiConsumer(::removeRequires), Function { it.role.id }, Function { it.capability.id })
    }

    override fun addUses(roleId: UniqueId<Role>, pmfId: UniqueId<Pmf>) {
        val role = checkExists(roleId, Function<UniqueId<Role>, Role?>(::getRole))
        val pmf = checkExists(pmfId, Function<UniqueId<Pmf>, Pmf?>(::getPmf))
        val map = get(roleId, relations.uses, USES)
        if (map.containsKey(pmfId)) {
            /* relation already exists do nothing */
            return
        }
        val uses = relationFactory.buildUses(role, pmf)
        map[pmfId] = uses
        addBy(uses, relations.usedBy, USED_BY, pmfId, roleId)
        publisher.post<UsesEvent>(eventFactory.build(EventType.ADDED, uses))
    }

    override fun getUses(id: UniqueId<Role>): Set<Pmf> {
        return get(id, relations.uses, Function { it.pmf })
    }

    override fun getUsedBy(id: UniqueId<Pmf>): Set<Role> {
        return get(id, relations.usedBy, Function { it.role })
    }

    override fun removeUses(roleId: UniqueId<Role>, pmfId: UniqueId<Pmf>) {
        if (relations.uses.containsKey(roleId) && relations.uses[roleId]!!.containsKey(pmfId)) {
            val uses = relations.uses[roleId]!!.remove(pmfId)!!
            removeBy(pmfId, roleId, relations.usedBy, USED_BY)
            publisher.post<UsesEvent>(eventFactory.build(EventType.REMOVED, uses))
        }
    }

    override fun removeAllUses() {
        removeAll(relations.uses, BiConsumer(::removeUses), Function { it.role.id }, Function { it.pmf.id })
    }

    override fun effectiveness(assignments: Collection<Assignment>): Double {
        return effectiveness.compute(this, assignments)
    }

    override fun getGoodness(id: UniqueId<Role>): Goodness? {
        return functions.goodness[id]
    }

    override fun setGoodness(id: UniqueId<Role>, goodness: Goodness) {
        checkExists(id, Function<UniqueId<Role>, Role?>(::getRole))
        functions.goodness[id] = goodness
    }

    @Throws(Exception::class)
    override fun subscribe(emitter: FlowableEmitter<Organization>) {
        emitter.onNext(this)
    }

    companion object {

        private val POSSESSES = "possesses"
        private val ASSIGNMENTS_BY_AGENT = "assignmentsByAgent"
        private val NEEDED_BY = "neededBy"
        private val HAD_BY = "hadBy"
        private val MODERATED_BY = "moderatedBy"
        private val HAS = "has"
        private val REQUIRED_BY = "requiredBy"
        private val POSSESSED_BY = "possessedBy"
        private val CONTAINED_BY = "containedBy"
        private val INSTANCE_GOALS_BY_SPECIFICATION_GOAL = "instanceGoalsBySpecificationGoal"
        private val ACHIEVES = "achieves"
        private val REQUIRES = "requires"
        private val NEEDS = "needs"
        private val USES = "uses"
        private val CONTAINS = "contains"
        private val ACHIEVED_BY = "achievedBy"
        private val USED_BY = "usedBy"
        private val log = org.slf4j.LoggerFactory.getLogger(DefaultOrganization::class.java)

        private fun <T, U, V> addBy(
            value: T,
            map: MutableMap<UniqueId<U>, MutableMap<UniqueId<V>, T>>,
            mapName: String,
            id1: UniqueId<U>,
            id2: UniqueId<V>
        ) {
            map.computeIfAbsent(id1) {
                log.warn(L.MAP_IS_MISSING_KEY.get(), mapName, id1)
                ConcurrentHashMap()
            }
            map[id1]!![id2] = value
        }

        private operator fun <T, U, V> get(
            roleId: UniqueId<T>,
            map: MutableMap<UniqueId<T>, MutableMap<UniqueId<U>, V>>,
            mapName: String
        ): MutableMap<UniqueId<U>, V> {
            return map.computeIfAbsent(roleId) {
                log.warn(L.MAP_IS_MISSING_KEY.get(), mapName, roleId)
                ConcurrentHashMap()
            }
        }

        private operator fun <T, U, V> get(
            id: UniqueId<U>,
            map: Map<UniqueId<U>, Map<UniqueId<T>, V>>,
            f: Function<V, T>
        ): Set<T> {
            return if (map.containsKey(id)) {
                map[id]!!.values.map { f.apply(it) }.toSet()
            } else setOf()
        }

        private fun <T, U, V> remove(
            id: UniqueId<T>,
            map: MutableMap<UniqueId<T>, MutableMap<UniqueId<U>, V>>,
            mapName: String,
            consumer: Consumer<UniqueId<U>>
        ) {
            if (map.containsKey(id)) {
                val ids = map[id]!!.keys
                ids.forEach(consumer)
                map.remove(id)
            } else {
                log.warn(L.MAP_IS_MISSING_KEY.get(), mapName, id)
            }
        }

        private fun <T, U, V> removeBy(
            id1: UniqueId<T>,
            id2: UniqueId<U>,
            map: MutableMap<UniqueId<T>, MutableMap<UniqueId<U>, V>>,
            mapName: String
        ) {
            if (map.containsKey(id1)) {
                if (map[id1]!!.containsKey(id2)) {
                    map[id1]!!.remove(id2)
                } else {
                    log.warn(L.MAP_IS_MISSING_ENTRY.get(), mapName, id1, id2)
                }
            } else {
                log.warn(L.MAP_IS_MISSING_KEY.get(), mapName, id1)
                map[id1] = ConcurrentHashMap()
            }
        }

        private fun <T, U, V, W : UniqueId<T>, X : UniqueId<U>> removeAll(
            map: Map<W, Map<X, V>>,
            consumer: BiConsumer<W, X>,
            function1: Function<V, W>,
            function2: Function<V, X>
        ) {
            map.values.flatMap { it.values }.toSet().forEach { consumer.accept(function1.apply(it), function2.apply(it)) }
        }

        private fun <T : Identifiable<T>> checkNotExists(t: T, p: Predicate<UniqueId<T>>) {
            if (p.test(t.id)) {
                throw IllegalArgumentException(E.ENTITY_ALREADY_EXISTS[t.id.type.simpleName, t.id])
            }
        }

        private fun <T, U : UniqueId<T>> checkExists(id: U, f: Function<U, T?>): T {
            return f.apply(id) ?: throw IllegalArgumentException(E.ENTITY_DOES_NOT_EXISTS[id.type.simpleName, id])
        }

        /**
         * Determines whether this [Organization] is valid.
         *
         *
         * Validity checks include checks for the structural integrity of the current organization.
         *
         * @param organization the [Organization] to check.
         * @return `true` if the [Organization] is valid, `false` otherwise.
         */
        fun isOrganizationValid(organization: Organization): Boolean {
            var result = true
            /*
         * every goal can be achieved by at least one role from the organization
		 */
            for (goal in organization.specificationGoals) {
                var isAchievable = false
                for (role in organization.getAchievedBy(goal.id)) {
                    isAchievable = isAchievable or (organization.getRole(role.id) != null)
                    if (isAchievable) { /* short circuit */
                        /*
                     * can stop checking because there is at least one role that can achieve the goal
					 */
                        break
                    }
                }
                result = result and isAchievable
                if (!result) { /* short circuit */
                    /*
                 * can stop checking because there is at least one goal that cannot be achieved by any role in the organization
				 */
                    break
                }
            }
            /*
         * the set of capabilities is the union of all capabilities required by roles or possessed by agents in the organization
		 */
            if (result) { /* short circult */
                /*
             * there is no reason to continue checking if the previous results are false
			 */
                for (role in organization.roles) {
                    /*
                 * every role requires at least one capability
				 */
                    result = result and (organization.getRequires(role.id).size > 0)
                    if (!result) { /* short circuit */
                        /*
                     * can stop checking because there is a role that does not require at least one capability
					 */
                        break
                    }
                    for (capability in organization.getRequires(role.id)) {
                        result = result and (organization.getCapability(capability.id) != null)
                        if (!result) { /* short circuit */
                            /*
                         * can stop checking because there is at least one capability required by a role that is not in the organization
						 */
                            break
                        }
                    }
                    if (!result) { /* short circuit */
                        /*
                     * can stop checking because there is at least one capability required by a role that is not in the organization
					 */
                        break
                    }
                }
                if (result) { /* short circuit */
                    /*
                 * there is no reason to continue checking if the previous results are false
				 */
                    for (agent in organization.agents) {
                        for (capability in organization.getPossesses(agent.id)) {
                            result = result and (organization.getCapability(capability.id) != null)
                            if (!result) { /* short circuit */
                                /*
                             * can stop checking because there is at least one capability possessed by an agent that is not in the organization
							 */
                                break
                            }
                        }
                        if (!result) { /* short circuit */
                            /*
                         * can stop checking because there is at least one capability possessed by an agent that is not in the organization
						 */
                            break
                        }
                    }
                }
            }
            return result
        }
    }
}