package runtimemodels.chazm.model.entity.impl

import com.google.inject.assistedinject.Assisted
import runtimemodels.chazm.api.entity.*
import runtimemodels.chazm.api.id.*
import javax.inject.Inject

sealed class DefaultEntity

internal data class DefaultAgent @Inject constructor(
    @Assisted override val id: AgentId
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
    @Assisted override val id: AttributeId,
    @param:Assisted override val type: Attribute.Type
) : Attribute, DefaultEntity()

internal data class DefaultCapability @Inject constructor(
    @Assisted override val id: CapabilityId
) : Capability, DefaultEntity()

internal data class DefaultCharacteristic @Inject constructor(
    @Assisted override val id: CharacteristicId
) : Characteristic, DefaultEntity()

internal data class DefaultInstanceGoal @Inject constructor(
    @Assisted override val id: InstanceGoalId,
    @param:Assisted override val goal: SpecificationGoal
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
    @Assisted override val id: PmfId
) : Pmf, DefaultEntity()

internal data class DefaultPolicy @Inject constructor(
    @Assisted override val id: PolicyId
) : Policy, DefaultEntity()

internal data class DefaultRole @Inject constructor(
    @Assisted override val id: RoleId
) : Role, DefaultEntity()

internal data class DefaultSpecificationGoal @Inject constructor(
    @Assisted override val id: SpecificationGoalId
) : SpecificationGoal, DefaultEntity()
