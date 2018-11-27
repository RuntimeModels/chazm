package runtimemodels.chazm.model

import runtimemodels.chazm.api.entity.*
import runtimemodels.chazm.api.id.UniqueId
import java.util.concurrent.ConcurrentHashMap

internal class Entities {

    //    val agents: MutableMap<AgentId, Agent> = ConcurrentHashMap()
    val attributes: MutableMap<UniqueId<Attribute>, Attribute> = ConcurrentHashMap()
    val capabilities: MutableMap<UniqueId<Capability>, Capability> = ConcurrentHashMap()
    val characteristics: MutableMap<UniqueId<Characteristic>, Characteristic> = ConcurrentHashMap()
    val instanceGoals: MutableMap<UniqueId<InstanceGoal>, InstanceGoal> = ConcurrentHashMap()
    val instanceGoalsBySpecificationGoal: MutableMap<UniqueId<SpecificationGoal>, MutableMap<UniqueId<InstanceGoal>, InstanceGoal>> = ConcurrentHashMap()
    val pmfs: MutableMap<UniqueId<Pmf>, Pmf> = ConcurrentHashMap()
    val policies: MutableMap<UniqueId<Policy>, Policy> = ConcurrentHashMap()
    val roles: MutableMap<UniqueId<Role>, Role> = ConcurrentHashMap()
    val specificationGoals: MutableMap<UniqueId<SpecificationGoal>, SpecificationGoal> = ConcurrentHashMap()

}
