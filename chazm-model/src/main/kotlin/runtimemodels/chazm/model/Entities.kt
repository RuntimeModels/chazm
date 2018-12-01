package runtimemodels.chazm.model

import runtimemodels.chazm.api.entity.Policy
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.entity.SpecificationGoal
import runtimemodels.chazm.api.id.PolicyId
import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.id.SpecificationGoalId
import java.util.concurrent.ConcurrentHashMap

internal class Entities {

    //    val agents: MutableMap<AgentId, Agent> = ConcurrentHashMap()
//    val attributes: MutableMap<AttributeId, Attribute> = ConcurrentHashMap()
//    val capabilities: MutableMap<CapabilityId, Capability> = ConcurrentHashMap()
//    val characteristics: MutableMap<CharacteristicId, Characteristic> = ConcurrentHashMap()
//    val instanceGoals: MutableMap<InstanceGoalId, InstanceGoal> = ConcurrentHashMap()
//    val instanceGoalsBySpecificationGoal: MutableMap<SpecificationGoalId, MutableMap<InstanceGoalId, InstanceGoal>> = ConcurrentHashMap()
//    val pmfs: MutableMap<PmfId, Pmf> = ConcurrentHashMap()
    val policies: MutableMap<PolicyId, Policy> = ConcurrentHashMap()
    val roles: MutableMap<RoleId, Role> = ConcurrentHashMap()
    val specificationGoals: MutableMap<SpecificationGoalId, SpecificationGoal> = ConcurrentHashMap()

}