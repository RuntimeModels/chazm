package runtimemodels.chazm.model.organization

import io.reactivex.FlowableEmitter
import io.reactivex.FlowableOnSubscribe
import runtimemodels.chazm.api.entity.*
import runtimemodels.chazm.api.function.Effectiveness
import runtimemodels.chazm.api.function.Goodness
import runtimemodels.chazm.api.id.*
import runtimemodels.chazm.api.organization.*
import runtimemodels.chazm.api.relation.*
import runtimemodels.chazm.model.Functions
import runtimemodels.chazm.model.Relations
import runtimemodels.chazm.model.event.*
import runtimemodels.chazm.model.factory.RelationFactory
import runtimemodels.chazm.model.message.E
import runtimemodels.chazm.model.message.L
import runtimemodels.chazm.model.notification.Publisher
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.function.BiConsumer
import java.util.function.Consumer
import java.util.function.Function
import javax.inject.Inject

internal open class DefaultOrganization @Inject constructor(
    private val relationFactory: RelationFactory,
    private val eventFactory: EventFactory,
    private val goodness: Goodness,
    private val effectiveness: Effectiveness,
    private val publisher: Publisher,
    override val agents: AgentManager,
    override val attributes: AttributeManager,
    override val capabilities: CapabilityManager,
    override val characteristics: CharacteristicManager,
    override val instanceGoals: InstanceGoalManager,
    override val pmfs: PmfManager,
    override val policies: PolicyManager,
    override val roles: RoleManager,
    override val specificationGoals: SpecificationGoalManager,
    override val achievesRelations: AchievesManager,
    override val containsRelations: ContainsManager,
    override val hasRelations: HasManager,
    override val moderatesRelations: ModeratesManager
) : Organization, FlowableOnSubscribe<Organization> {

    private val relations = Relations()
    private val functions = Functions()

    override fun add(agent: Agent) {
        checkNotExists(agent) { agents.containsKey(it) }
        agents.add(agent)
        relations.assignmentsByAgent[agent.id] = ConcurrentHashMap()
        relations.possesses[agent.id] = ConcurrentHashMap()
        publisher.post(eventFactory.build(EventType.ADDED, agent))
    }

    override fun remove(id: AgentId) {
        if (agents.contains(id)) {
            /* remove the agent, all associated assignments, all associated possesses relations, all associated has relations */
            val agent = agents.remove(id)
            remove(id, relations.assignmentsByAgent, ASSIGNMENTS_BY_AGENT, Consumer { removeAssignment(it) })
            hasRelations.remove(id)
            remove(id, relations.possesses, POSSESSES, Consumer { removePossesses(id, it) })
            publisher.post(eventFactory.build(EventType.REMOVED, agent))
        }
    }

    override fun add(attribute: Attribute) {
        checkNotExists(attribute) { attributes.containsKey(it) }
        attributes.add(attribute)
        relations.neededBy[attribute.id] = ConcurrentHashMap()
        publisher.post(eventFactory.build(EventType.ADDED, attribute))
    }

    override fun remove(id: AttributeId) {
        if (attributes.containsKey(id)) {
            // remove the attribute,
            // all associated has relations,
            // all associated moderates relations,
            // all associated needs relations,
            val attribute = attributes.remove(id)
            hasRelations.remove(id)
            moderatesRelations.remove(id)
            remove(id, relations.neededBy, NEEDED_BY, Consumer { removeNeeds(it, id) })
            publisher.post(eventFactory.build(EventType.REMOVED, attribute))
        }
    }

    override fun add(capability: Capability) {
        checkNotExists(capability) { capabilities.containsKey(it) }
        capabilities.add(capability)
        relations.requiredBy[capability.id] = ConcurrentHashMap()
        relations.possessedBy[capability.id] = ConcurrentHashMap()
        publisher.post(eventFactory.build(EventType.ADDED, capability))
    }

    override fun remove(id: CapabilityId) {
        if (capabilities.containsKey(id)) {
            /* remove the capability, all associated requires relations, all associated possesses relations */
            val capability = capabilities.remove(id)
            remove(id, relations.requiredBy, REQUIRED_BY, Consumer { removeRequires(it, id) })
            remove(id, relations.possessedBy, POSSESSED_BY, Consumer { removePossesses(it, id) })
            publisher.post(eventFactory.build(EventType.REMOVED, capability))
        }
    }

    override fun add(characteristic: Characteristic) {
        checkNotExists(characteristic) { characteristics.containsKey(it) }
        characteristics.add(characteristic)
        publisher.post(eventFactory.build(EventType.ADDED, characteristic))
    }

    override fun remove(id: CharacteristicId) {
        if (characteristics.containsKey(id)) {
            /* remove characteristics, all associated contains relations */
            val characteristic = characteristics.remove(id)
            containsRelations.remove(id)
            publisher.post(eventFactory.build(EventType.REMOVED, characteristic))
        }
    }

    override fun add(goal: InstanceGoal) {
        checkNotExists(goal) { instanceGoals.containsKey(it) }
        checkExists(goal.goal.id, specificationGoals::get)
        instanceGoals.add(goal)
        publisher.post(eventFactory.build(EventType.ADDED, goal))
    }

    override fun remove(id: InstanceGoalId) {
        if (instanceGoals.containsKey(id)) {
            /* remove the instance goal, instanceGoalsBySpecificationGoal map */
            val goal = instanceGoals.remove(id)
            publisher.post(eventFactory.build(EventType.REMOVED, goal))
        }
    }

    override fun add(pmf: Pmf) {
        checkNotExists(pmf) { pmfs.containsKey(it) }
        pmfs.add(pmf)
        publisher.post(eventFactory.build(EventType.ADDED, pmf))
    }

    override fun remove(id: PmfId) {
        if (pmfs.containsKey(id)) {
            /* remove the pmf, all associated moderates relations */
            val pmf = pmfs.remove(id)
            moderatesRelations.remove(id)
            publisher.post(eventFactory.build(EventType.REMOVED, pmf))
        }
    }

    override fun add(policy: Policy) {
        checkNotExists(policy) { policies.containsKey(it) }
        policies.add(policy)
        publisher.post(eventFactory.build(EventType.ADDED, policy))
    }

    override fun remove(id: PolicyId) {
        if (policies.containsKey(id)) {
            /* remove the policy */
            val policy = policies.remove(id)
            publisher.post(eventFactory.build(EventType.REMOVED, policy))
        }
    }

    override fun add(role: Role) {
        checkNotExists(role) { roles.containsKey(it) }
        roles.add(role)
        functions.goodness[role.id] = goodness
        publisher.post(eventFactory.build(EventType.ADDED, role))
    }

    override fun remove(id: RoleId) {
        if (roles.containsKey(id)) {
            // removes role,
            // all associated achieves relations,
            // all associated contains relations,
            // all associated requires relations,
            // all associated needs relations,
            // all associated uses relations,
            val role = roles.remove(id)
            achievesRelations.remove(id)
            containsRelations.remove(id)
            remove(id, relations.requires, REQUIRES, Consumer { removeRequires(id, it) })
            remove(id, relations.needs, NEEDS, Consumer { removeNeeds(id, it) })
            remove(id, relations.uses, USES, Consumer { removeUses(id, it) })
            functions.goodness.remove(id)
            publisher.post(eventFactory.build(EventType.REMOVED, role))
        }
    }

    override fun add(goal: SpecificationGoal) {
        checkNotExists(goal) { specificationGoals.containsKey(it) }
        specificationGoals.add(goal)
        publisher.post(eventFactory.build(EventType.ADDED, goal))
    }

    override fun remove(id: SpecificationGoalId) {
        if (specificationGoals.containsKey(id)) {
            /* remove the specification goal, all associated instance goals, all associated achieves relations */
            val goal = specificationGoals.remove(id)
            instanceGoals.remove(id)
            achievesRelations.remove(id)
            publisher.post(eventFactory.build(EventType.REMOVED, goal))
        }
    }

    override fun add(achieves: Achieves) {
        checkExists(achieves.role.id, roles::get)
        checkExists(achieves.goal.id, specificationGoals::get)
        achievesRelations.add(achieves)
        publisher.post(eventFactory.build(EventType.ADDED, achieves))
    }

    override fun remove(roleId: RoleId, goalId: SpecificationGoalId) {
        if (achievesRelations.containsKey(roleId) && achievesRelations[roleId].containsKey(goalId)) {
            val achieves = achievesRelations.remove(roleId, goalId)
            publisher.post(eventFactory.build(EventType.REMOVED, achieves))
        }
    }

    override fun addAssignment(assignment: Assignment) {
        checkNotExists(assignment) { relations.assignments.containsKey(it) }
        checkExists(assignment.agent.id, (agents::get))
        checkExists(assignment.role.id, (roles::get))
        checkExists(assignment.goal.id, (instanceGoals::get))
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

    override fun getAssignmentsByAgent(id: AgentId): Set<Assignment> {
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

    override fun add(contains: Contains) {
        checkExists(contains.role.id, roles::get)
        checkExists(contains.characteristic.id, characteristics::get)
        containsRelations.add(contains)
        publisher.post(eventFactory.build(EventType.ADDED, contains))
    }

    override fun remove(roleId: RoleId, characteristicId: CharacteristicId) {
        if (containsRelations.containsKey(roleId) && containsRelations[roleId].containsKey(characteristicId)) {
            val contains = containsRelations.remove(roleId, characteristicId)
            publisher.post(eventFactory.build(EventType.REMOVED, contains))
        }
    }

    override fun add(has: Has) {
        checkExists(has.agent.id, agents::get)
        checkExists(has.attribute.id, attributes::get)
        hasRelations.add(has)
        publisher.post(eventFactory.build(EventType.ADDED, has))
    }

    override fun remove(agentId: AgentId, attributeId: AttributeId) {
        if (hasRelations.containsKey(agentId) && hasRelations[agentId].containsKey(attributeId)) {
            val has = hasRelations.remove(agentId, attributeId)
            publisher.post(eventFactory.build(EventType.REMOVED, has))
        }
    }

    override fun add(moderates: Moderates) {
        checkExists(moderates.pmf.id, pmfs::get)
        checkExists(moderates.attribute.id, attributes::get)
        moderatesRelations.add(moderates)
        publisher.post(eventFactory.build(EventType.ADDED, moderates))
    }

    override fun remove(pmfId: PmfId, attributeId: AttributeId) {
        if (moderatesRelations.containsKey(pmfId)) {
            val moderates = moderatesRelations.remove(pmfId, attributeId)
            publisher.post(eventFactory.build(EventType.REMOVED, moderates))
        }
    }

    override fun addNeeds(roleId: RoleId, attributeId: AttributeId) {
        val role = checkExists(roleId, (roles::get))
        val attribute = checkExists(attributeId, (attributes::get))
        val map = getMap(roleId, relations.needs, NEEDS)
        if (map.containsKey(attributeId)) {
            /* relation already exists do nothing */
            return
        }
        val needs = relationFactory.buildNeeds(role, attribute)
        map[attributeId] = needs
        addBy(needs, relations.neededBy, NEEDED_BY, attributeId, roleId)
        publisher.post<NeedsEvent>(eventFactory.build(EventType.ADDED, needs))
    }

    override fun getNeeds(id: RoleId): Set<Attribute> {
        return getSet(id, relations.needs, Function { it.attribute })
    }

    override fun getNeededBy(id: AttributeId): Set<Role> {
        return getSet(id, relations.neededBy, Function { it.role })
    }

    override fun removeNeeds(roleId: RoleId, attributeId: AttributeId) {
        if (relations.needs.containsKey(roleId) && relations.needs[roleId]!!.containsKey(attributeId)) {
            val needs = relations.needs[roleId]!!.remove(attributeId)!!
            removeBy(attributeId, roleId, relations.neededBy, NEEDED_BY)
            publisher.post<NeedsEvent>(eventFactory.build(EventType.REMOVED, needs))
        }
    }

    override fun removeAllNeeds() {
        removeAll(relations.needs, BiConsumer(::removeNeeds), Function { it.role.id }, Function { it.attribute.id })
    }

    override fun addPossesses(agentId: AgentId, capabilityId: CapabilityId, score: Double) {
        val agent = checkExists(agentId, (agents::get))
        val capability = checkExists(capabilityId, (capabilities::get))
        val map = getMap(agentId, relations.possesses, POSSESSES)
        if (map.containsKey(capabilityId)) {
            /* relation already exists do nothing */
            return
        }
        val possesses = relationFactory.buildPossesses(agent, capability, score)
        map[capabilityId] = possesses
        addBy(possesses, relations.possessedBy, POSSESSED_BY, capabilityId, agentId)
        publisher.post<PossessesEvent>(eventFactory.build(EventType.ADDED, possesses))
    }

    override fun getPossesses(id: AgentId): Set<Capability> {
        return getSet(id, relations.possesses, Function { it.capability })
    }

    override fun getPossessedBy(id: CapabilityId): Set<Agent> {
        return getSet(id, relations.possessedBy, Function { it.agent })
    }

    override fun getPossessesScore(agentId: AgentId, capabilityId: CapabilityId): Double {
        return if (relations.possesses.containsKey(agentId) && relations.possesses[agentId]!!.containsKey(capabilityId)) {
            relations.possesses[agentId]!![capabilityId]!!.score
        } else 0.0
    }

    override fun setPossessesScore(agentId: AgentId, capabilityId: CapabilityId, score: Double) {
        if (relations.possesses.containsKey(agentId) && relations.possesses[agentId]!!.containsKey(capabilityId)) {
            val possesses = relations.possesses[agentId]!![capabilityId]!!
            possesses.score = score
            publisher.post<PossessesEvent>(eventFactory.build(EventType.UPDATED, possesses))
        }
    }

    override fun removePossesses(agentId: AgentId, capabilityId: CapabilityId) {
        if (relations.possesses.containsKey(agentId) && relations.possesses[agentId]!!.containsKey(capabilityId)) {
            val possesses = relations.possesses[agentId]!!.remove(capabilityId)!!
            removeBy(capabilityId, agentId, relations.possessedBy, POSSESSED_BY)
            publisher.post<PossessesEvent>(eventFactory.build(EventType.REMOVED, possesses))
        }
    }

    override fun removeAllPossesses() {
        removeAll(relations.possesses, BiConsumer(::removePossesses), Function { it.agent.id }, Function { f -> f.capability.id })
    }

    override fun addRequires(roleId: RoleId, capabilityId: CapabilityId) {
        val role = checkExists(roleId, (roles::get))
        val capability = checkExists(capabilityId, (capabilities::get))
        val map = getMap(roleId, relations.requires, REQUIRES)
        if (map.containsKey(capabilityId)) {
            /* relation already exists do nothing */
            return
        }
        val requires = relationFactory.buildRequires(role, capability)
        map[capabilityId] = requires
        addBy(requires, relations.requiredBy, REQUIRED_BY, capabilityId, roleId)
        publisher.post<RequiresEvent>(eventFactory.build(EventType.ADDED, requires))
    }

    override fun getRequires(id: RoleId): Set<Capability> {
        return getSet(id, relations.requires, Function { it.capability })
    }

    override fun getRequiredBy(id: CapabilityId): Set<Role> {
        return getSet(id, relations.requiredBy, Function { it.role })
    }

    override fun removeRequires(roleId: RoleId, capabilityId: CapabilityId) {
        if (relations.requires.containsKey(roleId) && relations.requires[roleId]!!.containsKey(capabilityId)) {
            val requires = relations.requires[roleId]!!.remove(capabilityId)!!
            removeBy(capabilityId, roleId, relations.requiredBy, REQUIRED_BY)
            publisher.post<RequiresEvent>(eventFactory.build(EventType.REMOVED, requires))
        }
    }

    override fun removeAllRequires() {
        removeAll(relations.requires, BiConsumer(::removeRequires), Function { it.role.id }, Function { it.capability.id })
    }

    override fun addUses(roleId: RoleId, pmfId: PmfId) {
        val role = checkExists(roleId, (roles::get))
        val pmf = checkExists(pmfId, (pmfs::get))
        val map = getMap(roleId, relations.uses, USES)
        if (map.containsKey(pmfId)) {
            /* relation already exists do nothing */
            return
        }
        val uses = relationFactory.buildUses(role, pmf)
        map[pmfId] = uses
        addBy(uses, relations.usedBy, USED_BY, pmfId, roleId)
        publisher.post<UsesEvent>(eventFactory.build(EventType.ADDED, uses))
    }

    override fun getUses(id: RoleId): Set<Pmf> {
        return getSet(id, relations.uses, Function { it.pmf })
    }

    override fun getUsedBy(id: PmfId): Set<Role> {
        return getSet(id, relations.usedBy, Function { it.role })
    }

    override fun removeUses(roleId: RoleId, pmfId: PmfId) {
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
        return effectiveness.compute(this, assignments.toSet())
    }

    override fun getGoodness(id: RoleId): Goodness? {
        return functions.goodness[id]
    }

    override fun setGoodness(id: RoleId, goodness: Goodness) {
        checkExists(id, (roles::get))
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
        private val REQUIRED_BY = "requiredBy"
        private val POSSESSED_BY = "possessedBy"
        private val REQUIRES = "requires"
        private val NEEDS = "needs"
        private val USES = "uses"
        private val USED_BY = "usedBy"
        private val log = org.slf4j.LoggerFactory.getLogger(DefaultOrganization::class.java)

        private fun <T, U, V> addBy(
            value: T,
            map: MutableMap<U, MutableMap<V, T>>,
            mapName: String,
            id1: U,
            id2: V
        ) {
            map.computeIfAbsent(id1) {
                log.warn(L.MAP_IS_MISSING_KEY.get(), mapName, id1)
                ConcurrentHashMap()
            }
            map[id1]!![id2] = value
        }

        private fun <T : UniqueId<W>, U : UniqueId<V>, V, W, X> getMap(
            id: T,
            map: MutableMap<T, MutableMap<U, X>>,
            mapName: String
        ): MutableMap<U, X> {
            return map.computeIfAbsent(id) {
                log.warn(L.MAP_IS_MISSING_KEY.get(), mapName, id)
                ConcurrentHashMap()
            }
        }

        private fun <T, U, V, W : UniqueId<U>, X : UniqueId<T>> getSet(
            id: W,
            map: Map<W, Map<X, V>>,
            f: Function<V, T>
        ): Set<T> {
            return if (map.containsKey(id)) {
                map[id]!!.values.map { f.apply(it) }.toSet()
            } else setOf()
        }

        private fun <T, U : UniqueId<V>, V, W> remove(
            id: T,
            map: MutableMap<T, MutableMap<U, W>>,
            mapName: String,
            consumer: Consumer<U>
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
            id1: T,
            id2: U,
            map: MutableMap<T, MutableMap<U, V>>,
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

        private fun <T : Identifiable<T>> checkNotExists(t: T, predicate: (UniqueId<T>) -> Boolean) {
            if (predicate(t.id)) {
                throw IllegalArgumentException(E.ENTITY_ALREADY_EXISTS[t.id.type.simpleName, t.id])
            }
        }

        private fun <T : UniqueId<U>, U> checkExists(id: T, function: (T) -> U?): U {
            return function(id) ?: throw IllegalArgumentException(E.ENTITY_DOES_NOT_EXISTS[id.type.simpleName, id])
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
            for ((_, goal) in organization.specificationGoals) {
                var isAchievable = false
                for ((_, achieves) in organization.achievesRelations[goal.id]) {
                    isAchievable = isAchievable or (organization.roles[achieves.role.id] != null)
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
                for ((_, role) in organization.roles) {
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
                        result = result and (organization.capabilities[capability.id] != null)
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
                    for ((_, agent) in organization.agents) {
                        for (capability in organization.getPossesses(agent.id)) {
                            result = result and (organization.capabilities[capability.id] != null)
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
