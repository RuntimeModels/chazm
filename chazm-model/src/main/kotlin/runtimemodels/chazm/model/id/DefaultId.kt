package runtimemodels.chazm.model.id

import runtimemodels.chazm.api.entity.*
import runtimemodels.chazm.api.id.*
import javax.inject.Inject
import kotlin.reflect.KClass

internal sealed class DefaultId<T : Any> constructor(
    override val type: KClass<T>
) : UniqueId<T> {
    override fun equals(other: Any?): Boolean {
        if (other is UniqueId<*>) {
            return type == other.type
        }
        return false
    }

    override fun hashCode(): Int = type.hashCode()

    override fun toString(): String = type.toString()
}

internal data class DefaultAgentId @Inject constructor(
    private val id: String
) : DefaultId<Agent>(Agent::class), AgentId

internal data class DefaultAttributeId @Inject constructor(
    private val id: String
) : DefaultId<Attribute>(Attribute::class), AttributeId

internal data class DefaultCapabilityId @Inject constructor(
    private val id: String
) : DefaultId<Capability>(Capability::class), CapabilityId

internal data class DefaultCharacteristicId @Inject constructor(
    private val id: String
) : DefaultId<Characteristic>(Characteristic::class), CharacteristicId

internal data class DefaultInstanceGoalId @Inject constructor(
    private val id: String
) : DefaultId<InstanceGoal>(InstanceGoal::class), InstanceGoalId

internal data class DefaultPmfId @Inject constructor(
    private val id: String
) : DefaultId<Pmf>(Pmf::class), PmfId

internal data class DefaultPolicyId @Inject constructor(
    private val id: String
) : DefaultId<Policy>(Policy::class), PolicyId

internal data class DefaultRoleId @Inject constructor(
    private val id: String
) : DefaultId<Role>(Role::class), RoleId

internal data class DefaultSpecificationGoalId @Inject constructor(
    private val id: String
) : DefaultId<SpecificationGoal>(SpecificationGoal::class), SpecificationGoalId
