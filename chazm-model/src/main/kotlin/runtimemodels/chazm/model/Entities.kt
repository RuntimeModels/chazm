package runtimemodels.chazm.model

import runtimemodels.chazm.api.entity.*
import runtimemodels.chazm.api.id.*
import java.util.concurrent.ConcurrentHashMap

internal class Entities {

    //    val agents: MutableMap<AgentId, Agent> = ConcurrentHashMap()
    val attributes: MutableMap<AttributeId, Attribute> = ConcurrentHashMap()
    val capabilities: MutableMap<CapabilityId, Capability> = ConcurrentHashMap()
    val characteristics: MutableMap<CharacteristicId, Characteristic> = ConcurrentHashMap()
    val instanceGoals: MutableMap<InstanceGoalId, InstanceGoal> = ConcurrentHashMap()
    val instanceGoalsBySpecificationGoal: MutableMap<SpecificationGoalId, MutableMap<InstanceGoalId, InstanceGoal>> = ConcurrentHashMap()
    val pmfs: MutableMap<PmfId, Pmf> = ConcurrentHashMap()
    val policies: MutableMap<PolicyId, Policy> = ConcurrentHashMap()
    val roles: MutableMap<RoleId, Role> = ConcurrentHashMap()
    val specificationGoals: MutableMap<SpecificationGoalId, SpecificationGoal> = ConcurrentHashMap()

}
