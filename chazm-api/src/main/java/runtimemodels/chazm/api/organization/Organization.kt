package runtimemodels.chazm.api.organization

import runtimemodels.chazm.api.entity.*
import runtimemodels.chazm.api.entity.manager.PmfManager
import runtimemodels.chazm.api.entity.manager.PolicyManager
import runtimemodels.chazm.api.entity.manager.RoleManager
import runtimemodels.chazm.api.entity.manager.SpecificationGoalManager
import runtimemodels.chazm.api.function.Goodness
import runtimemodels.chazm.api.id.*
import runtimemodels.chazm.api.relation.Assignment
import runtimemodels.chazm.api.relation.manager.*
import java.util.function.Predicate

/**
 * The [Organization] interface defines an organization, which consists of entities and relations between the entities.
 *
 * @author Christopher Zhong
 * @since 4.0
 */
interface Organization : PmfManager, PolicyManager, RoleManager, SpecificationGoalManager, AchievesManager, AssignmentManager, ContainsManager, HasManager, ModeratesManager, NeedsManager, PossessesManager, RequiresManager, UsesManager {
    val agents: AgentManager

    fun addAgent(agent: Agent)

    fun removeAgent(id: AgentId)

    val attributes: AttributeManager

    fun addAttribute(attribute: Attribute)

    fun removeAttribute(id: AttributeId)

    val capabilities: CapabilityManager

    fun addCapability(capability: Capability)

    fun removeCapability(id: CapabilityId)

    val characteristics: CharacteristicManager

    fun addCharacteristic(characteristic: Characteristic)

    fun removeCharacteristic(id: CharacteristicId)

    val instanceGoals: InstanceGoalManager

    fun addInstanceGoal(goal: InstanceGoal)

    fun removeInstanceGoal(id: InstanceGoalId)

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
