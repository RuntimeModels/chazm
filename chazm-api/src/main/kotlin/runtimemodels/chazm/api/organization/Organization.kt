package runtimemodels.chazm.api.organization

import runtimemodels.chazm.api.entity.*
import runtimemodels.chazm.api.function.Goodness
import runtimemodels.chazm.api.id.*
import runtimemodels.chazm.api.relation.Achieves
import runtimemodels.chazm.api.relation.Assignment
import runtimemodels.chazm.api.relation.Contains
import runtimemodels.chazm.api.relation.Has
import runtimemodels.chazm.api.relation.manager.*
import java.util.function.Predicate

/**
 * The [Organization] interface defines an organization, which consists of entities and relations between the entities.
 *
 * @author Christopher Zhong
 * @since 4.0
 */
interface Organization : AssignmentManager, ModeratesManager, NeedsManager, PossessesManager, RequiresManager, UsesManager {
    val agents: AgentManager
    fun add(agent: Agent)
    fun remove(id: AgentId)

    val attributes: AttributeManager
    fun add(attribute: Attribute)
    fun remove(id: AttributeId)

    val capabilities: CapabilityManager
    fun add(capability: Capability)
    fun remove(id: CapabilityId)

    val characteristics: CharacteristicManager
    fun add(characteristic: Characteristic)
    fun remove(id: CharacteristicId)

    val instanceGoals: InstanceGoalManager
    fun add(goal: InstanceGoal)
    fun remove(id: InstanceGoalId)

    val pmfs: PmfManager
    fun add(pmf: Pmf)
    fun remove(id: PmfId)

    val policies: PolicyManager
    fun add(policy: Policy)
    fun remove(id: PolicyId)

    val roles: RoleManager
    fun add(role: Role)
    fun remove(id: RoleId)

    val specificationGoals: SpecificationGoalManager
    fun add(goal: SpecificationGoal)
    fun remove(id: SpecificationGoalId)

    val achievesRelations: AchievesManager
    fun add(achieves: Achieves)
    fun remove(roleId: RoleId, goalId: SpecificationGoalId)

    // save for assignment

    val containsRelations: ContainsManager
    fun add(contains: Contains)
    fun remove(roleId: RoleId, characteristicId: CharacteristicId)

    val hasRelations: HasManager
    fun add(has: Has)
    fun remove(agentId: AgentId, attributeId: AttributeId)

    /**
     * Checks if this [Organization] is valid. Validity rules differ from one organization to another, so there is no general algorithm to determine
     * the validity of an [Organization].
     *
     * @param p the [Predicate] that tests the validity of this [Organization].
     * @return `true` if the [Organization] is valid, `false` otherwise.
     */
    fun isValid(p: Predicate<Organization>): Boolean {
        return p.test(this)
    }

    /**
     * Returns a `double`-valued score of this [Organization]'s effectiveness.
     *
     * @param assignments a set of [Assignment]s.
     * @return a `double`-valued score.
     */
    fun effectiveness(assignments: Collection<Assignment>): Double

    /**
     * Returns the [Goodness] function associated with a [Role] from this [Organization].
     *
     * @param id the [UniqueId] that represents a [Role].
     * @return a [Goodness] function.
     */
    fun getGoodness(id: RoleId): Goodness?

    /**
     * Sets a new [Goodness] function for a [Role] in this [Organization].
     *
     * @param id       the [UniqueId] that represents a [Role].
     * @param goodness the [Goodness] function.
     */
    fun setGoodness(id: RoleId, goodness: Goodness)

}
