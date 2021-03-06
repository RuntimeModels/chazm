package runtimemodels.chazm.model.organization

import io.reactivex.FlowableEmitter
import io.reactivex.FlowableOnSubscribe
import runtimemodels.chazm.api.entity.*
import runtimemodels.chazm.api.function.Effectiveness
import runtimemodels.chazm.api.function.Goodness
import runtimemodels.chazm.api.id.Identifiable
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.api.organization.Organization
import runtimemodels.chazm.api.relation.*
import runtimemodels.chazm.model.Functions
import runtimemodels.chazm.model.event.EventFactory
import runtimemodels.chazm.model.event.EventType
import runtimemodels.chazm.model.message.E
import runtimemodels.chazm.model.notification.Publisher
import javax.inject.Inject

internal open class DefaultOrganization @Inject constructor(
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
    override val assignmentRelations: AssignmentManager,
    override val containsRelations: ContainsManager,
    override val hasRelations: HasManager,
    override val moderatesRelations: ModeratesManager,
    override val needsRelations: NeedsManager,
    override val possessesRelations: PossessesManager,
    override val requiresRelations: RequiresManager,
    override val usesRelations: UsesManager
) : Organization, FlowableOnSubscribe<Organization> {

    private val functions = Functions()

    override fun add(agent: Agent) {
        checkNotExists(agent, agents::containsKey)
        agents.add(agent)
        publisher.post(eventFactory.build(EventType.ADDED, agent))
    }

    override fun remove(id: AgentId) {
        agents.remove(id)?.also {
            // if the agent is removed
            // remove all associated assignment, has, and possesses relations
            // and publish the event
            assignmentRelations.remove(id)
            hasRelations.remove(id)
            possessesRelations.remove(id)
            publisher.post(eventFactory.build(EventType.REMOVED, it))
        }
    }

    override fun add(attribute: Attribute) {
        checkNotExists(attribute, attributes::containsKey)
        attributes.add(attribute)
        publisher.post(eventFactory.build(EventType.ADDED, attribute))
    }

    override fun remove(id: AttributeId) {
        attributes.remove(id)?.also {
            // if the attribute is removed
            // remove all associated has, moderates, and needs relations
            // and publish the event
            hasRelations.remove(id)
            moderatesRelations.remove(id)
            needsRelations.remove(id)
            publisher.post(eventFactory.build(EventType.REMOVED, it))
        }
    }

    override fun add(capability: Capability) {
        checkNotExists(capability, capabilities::containsKey)
        capabilities.add(capability)
        publisher.post(eventFactory.build(EventType.ADDED, capability))
    }

    override fun remove(id: CapabilityId) {
        capabilities.remove(id)?.also {
            // if the capability is removed
            // remove all associated possesses and requires relations
            // and publish the event
            possessesRelations.remove(id)
            requiresRelations.remove(id)
            publisher.post(eventFactory.build(EventType.REMOVED, it))
        }
    }

    override fun add(characteristic: Characteristic) {
        checkNotExists(characteristic, characteristics::containsKey)
        characteristics.add(characteristic)
        publisher.post(eventFactory.build(EventType.ADDED, characteristic))
    }

    override fun remove(id: CharacteristicId) {
        characteristics.remove(id)?.also {
            // if the characteristic is removed
            // remove all associated contains relations
            // and publish the event
            containsRelations.remove(id)
            publisher.post(eventFactory.build(EventType.REMOVED, it))
        }
    }

    override fun add(goal: InstanceGoal) {
        checkNotExists(goal, instanceGoals::containsKey)
        checkExists(goal.goal.id, specificationGoals::get)
        instanceGoals.add(goal)
        publisher.post(eventFactory.build(EventType.ADDED, goal))
    }

    override fun remove(id: InstanceGoalId) {
        instanceGoals.remove(id)?.also {
            /* remove the instance goal, instanceGoalsBySpecificationGoal map */
            // and publish the event
            publisher.post(eventFactory.build(EventType.REMOVED, it))
        }
    }

    override fun add(pmf: Pmf) {
        checkNotExists(pmf) { pmfs.containsKey(it) }
        pmfs.add(pmf)
        publisher.post(eventFactory.build(EventType.ADDED, pmf))
    }

    override fun remove(id: PmfId) {
        pmfs.remove(id)?.also {
            // if the pmf is removed
            // remove all associated moderates relations
            // and publish the event
            moderatesRelations.remove(id)
            publisher.post(eventFactory.build(EventType.REMOVED, it))
        }
    }

    override fun add(policy: Policy) {
        checkNotExists(policy) { policies.containsKey(it) }
        policies.add(policy)
        publisher.post(eventFactory.build(EventType.ADDED, policy))
    }

    override fun remove(id: PolicyId) {
        policies.remove(id)?.also {
            // if the policy is removed
            // publish the event
            publisher.post(eventFactory.build(EventType.REMOVED, it))
        }
    }

    override fun add(role: Role) {
        checkNotExists(role) { roles.containsKey(it) }
        roles.add(role)
        functions.goodness[role.id] = goodness
        publisher.post(eventFactory.build(EventType.ADDED, role))
    }

    override fun remove(id: RoleId) {
        roles.remove(id)?.also {
            // if the role is removed
            // remove all associated achieves, contains, needs, requires, and uses relations,
            // remove the associated goodness function
            // and publish the event
            achievesRelations.remove(id)
            containsRelations.remove(id)
            needsRelations.remove(id)
            requiresRelations.remove(id)
            usesRelations.remove(id)
            functions.goodness.remove(id)
            publisher.post(eventFactory.build(EventType.REMOVED, it))
        }
    }

    override fun add(goal: SpecificationGoal) {
        checkNotExists(goal) { specificationGoals.containsKey(it) }
        specificationGoals.add(goal)
        publisher.post(eventFactory.build(EventType.ADDED, goal))
    }

    override fun remove(id: SpecificationGoalId) {
        specificationGoals.remove(id)?.also {
            // if the goal is removed
            // remove all associated instance goals and achieves relations
            // and publish the event
            instanceGoals.remove(id)
            achievesRelations.remove(id)
            publisher.post(eventFactory.build(EventType.REMOVED, it))
        }
    }

    override fun add(achieves: Achieves) {
        checkExists(achieves.role.id, roles::get)
        checkExists(achieves.goal.id, specificationGoals::get)
        achievesRelations.add(achieves)
        publisher.post(eventFactory.build(EventType.ADDED, achieves))
    }

    override fun remove(roleId: RoleId, goalId: SpecificationGoalId) {
        achievesRelations.remove(roleId, goalId)?.also {
            publisher.post(eventFactory.build(EventType.REMOVED, it))
        }
    }

    override fun add(assignment: Assignment) {
        checkExists(assignment.agent.id, agents::get)
        checkExists(assignment.role.id, roles::get)
        checkExists(assignment.goal.id, instanceGoals::get)
        assignmentRelations.add(assignment)
        publisher.post(eventFactory.build(EventType.ADDED, assignment))
    }

    override fun remove(agentId: AgentId, roleId: RoleId, goalId: InstanceGoalId) {
        assignmentRelations.remove(agentId, roleId, goalId)?.also {
            publisher.post(eventFactory.build(EventType.REMOVED, it))
        }
    }

    override fun add(contains: Contains) {
        checkExists(contains.role.id, roles::get)
        checkExists(contains.characteristic.id, characteristics::get)
        containsRelations.add(contains)
        publisher.post(eventFactory.build(EventType.ADDED, contains))
    }

    override fun remove(roleId: RoleId, characteristicId: CharacteristicId) {
        containsRelations.remove(roleId, characteristicId)?.also {
            publisher.post(eventFactory.build(EventType.REMOVED, it))
        }
    }

    override fun add(has: Has) {
        checkExists(has.agent.id, agents::get)
        checkExists(has.attribute.id, attributes::get)
        hasRelations.add(has)
        publisher.post(eventFactory.build(EventType.ADDED, has))
    }

    override fun remove(agentId: AgentId, attributeId: AttributeId) {
        hasRelations.remove(agentId, attributeId)?.also {
            publisher.post(eventFactory.build(EventType.REMOVED, it))
        }
    }

    override fun add(moderates: Moderates) {
        checkExists(moderates.pmf.id, pmfs::get)
        checkExists(moderates.attribute.id, attributes::get)
        moderatesRelations.add(moderates)
        publisher.post(eventFactory.build(EventType.ADDED, moderates))
    }

    override fun remove(pmfId: PmfId, attributeId: AttributeId) {
        moderatesRelations.remove(pmfId, attributeId)?.also {
            publisher.post(eventFactory.build(EventType.REMOVED, it))
        }
    }

    override fun add(needs: Needs) {
        checkExists(needs.role.id, roles::get)
        checkExists(needs.attribute.id, attributes::get)
        needsRelations.add(needs)
        publisher.post(eventFactory.build(EventType.ADDED, needs))
    }

    override fun remove(roleId: RoleId, attributeId: AttributeId) {
        needsRelations.remove(roleId, attributeId)?.also {
            publisher.post(eventFactory.build(EventType.REMOVED, it))
        }
    }

    override fun add(possesses: Possesses) {
        checkExists(possesses.agent.id, agents::get)
        checkExists(possesses.capability.id, capabilities::get)
        possessesRelations.add(possesses)
        publisher.post(eventFactory.build(EventType.ADDED, possesses))
    }

    override fun remove(agentId: AgentId, capabilityId: CapabilityId) {
        possessesRelations.remove(agentId, capabilityId)?.also {
            publisher.post(eventFactory.build(EventType.REMOVED, it))
        }
    }

    override fun add(requires: Requires) {
        checkExists(requires.role.id, roles::get)
        checkExists(requires.capability.id, capabilities::get)
        requiresRelations.add(requires)
        publisher.post(eventFactory.build(EventType.ADDED, requires))
    }

    override fun remove(roleId: RoleId, capabilityId: CapabilityId) {
        requiresRelations.remove(roleId, capabilityId)?.also {
            publisher.post(eventFactory.build(EventType.REMOVED, it))
        }
    }

    override fun add(uses: Uses) {
        checkExists(uses.role.id, roles::get)
        checkExists(uses.pmf.id, pmfs::get)
        usesRelations.add(uses)
        publisher.post(eventFactory.build(EventType.ADDED, uses))
    }

    override fun remove(roleId: RoleId, pmfId: PmfId) {
        usesRelations.remove(roleId, pmfId)?.also {
            publisher.post(eventFactory.build(EventType.REMOVED, it))
        }
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
        private fun <T : Identifiable<T, U>, U : UniqueId<T>> checkNotExists(t: T, predicate: (U) -> Boolean) {
            if (predicate(t.id)) {
                throw IllegalArgumentException(E.ENTITY_ALREADY_EXISTS[t.id.type, t.id])
            }
        }

        private fun <T : UniqueId<U>, U> checkExists(id: T, function: (T) -> U?): U {
            return function(id) ?: throw IllegalArgumentException(E.ENTITY_DOES_NOT_EXISTS[id.type, id])
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
            if (!isEveryGoalAchievedByAtLeastOneRole(organization)) {
                return false
            }
            if (!doesEveryRoleRequiresAtLeastOneCapability(organization)) {
                return false
            }
            if (!doesEveryAgentPossessAtLeastOneCapability(organization)) {
                return false
            }
            return true
        }

        private fun isEveryGoalAchievedByAtLeastOneRole(organization: Organization): Boolean {
            // every goal can be achieved by at least one role of the given organization
            for ((goalId, _) in organization.specificationGoals) {
                val map = organization.achievesRelations[goalId]
                if (map.isEmpty()) { // short circuit because there is a goal that cannot be achieved by any role
                    return false
                }
                // ensure that all roles exists in the organization
                if (!organization.roles.keys.containsAll(map.keys)) {
                    return false
                }
            }
            return true
        }

        private fun doesEveryRoleRequiresAtLeastOneCapability(organization: Organization): Boolean {
            // every role requires at least one capability
            for ((roleId, _) in organization.roles) {
                val map = organization.requiresRelations[roleId]
                if (map.isEmpty()) { // short circuit because there is a role that requires no capabilities
                    return false
                }
                // ensure that all the required capabilities exists in the organization
                if (!organization.capabilities.keys.containsAll(map.keys)) {
                    return false
                }
            }
            return true
        }

        private fun doesEveryAgentPossessAtLeastOneCapability(organization: Organization): Boolean {
            // every agent must have at least one capability
            for ((agentId, _) in organization.agents) {
                val map = organization.possessesRelations[agentId]
                if (map.isEmpty()) { // short circuit because there is an agent that possess no capabilities
                    return false
                }
                // ensure that all possessed capabilities exists in the organization
                if (!organization.capabilities.keys.containsAll(map.keys)) {
                    return false
                }
            }
            return true
        }
    }
}
