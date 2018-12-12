package runtimemodels.chazm.model.entity.impl

import runtimemodels.chazm.api.entity.*
import javax.inject.Inject

internal sealed class DefaultEntity

internal data class DefaultAgent @Inject constructor(
    override val id: AgentId
) : Agent, DefaultEntity() {
    internal data class DefaultContactInfo(
        private val map: MutableMap<Any, Any> = mutableMapOf()
    ) : Agent.ContactInfo, Map<Any, Any> by map {
        override fun add(key: Any, value: Any) {
            map[key] = value
        }

        override fun remove(key: Any) {
            map.remove(key)
        }
    }

    override val contactInfo: Agent.ContactInfo = DefaultContactInfo()
}

internal data class DefaultAttribute @Inject constructor(
    override val id: AttributeId,
    override val type: Attribute.Type
) : Attribute, DefaultEntity()

internal data class DefaultCapability @Inject constructor(
    override val id: CapabilityId
) : Capability, DefaultEntity()

internal data class DefaultCharacteristic @Inject constructor(
    override val id: CharacteristicId
) : Characteristic, DefaultEntity()

internal data class DefaultInstanceGoal @Inject constructor(
    override val id: InstanceGoalId,
    override val goal: SpecificationGoal
) : InstanceGoal, DefaultEntity() {
    internal data class DefaultGoalParameter(
        private val map: MutableMap<Any, Any> = mutableMapOf()
    ) : InstanceGoal.GoalParameter, Map<Any, Any> by map {
        override fun add(key: Any, value: Any) {
            map[key] = value
        }

        override fun remove(key: Any) {
            map.remove(key)
        }
    }

    override val parameters: InstanceGoal.GoalParameter = DefaultGoalParameter()
}

internal data class DefaultPmf @Inject constructor(
    override val id: PmfId
) : Pmf, DefaultEntity()

internal data class DefaultPolicy @Inject constructor(
    override val id: PolicyId
) : Policy, DefaultEntity()

internal data class DefaultRole @Inject constructor(
    override val id: RoleId
) : Role, DefaultEntity()

internal data class DefaultSpecificationGoal @Inject constructor(
    override val id: SpecificationGoalId
) : SpecificationGoal, DefaultEntity()
