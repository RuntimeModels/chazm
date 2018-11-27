package runtimemodels.chazm.model

import runtimemodels.chazm.api.entity.*
import runtimemodels.chazm.api.id.AgentId
import runtimemodels.chazm.api.id.AttributeId
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.api.relation.*
import java.util.concurrent.ConcurrentHashMap

internal class Relations {

    val achieves: MutableMap<UniqueId<Role>, MutableMap<UniqueId<SpecificationGoal>, Achieves>> = ConcurrentHashMap()
    val achievedBy: MutableMap<UniqueId<SpecificationGoal>, MutableMap<UniqueId<Role>, Achieves>> = ConcurrentHashMap()
    val assignments: MutableMap<UniqueId<Assignment>, Assignment> = ConcurrentHashMap()
    val assignmentsByAgent: MutableMap<AgentId, MutableMap<UniqueId<Assignment>, Assignment>> = ConcurrentHashMap()
    val contains: MutableMap<UniqueId<Role>, MutableMap<UniqueId<Characteristic>, Contains>> = ConcurrentHashMap()
    val containedBy: MutableMap<UniqueId<Characteristic>, MutableMap<UniqueId<Role>, Contains>> = ConcurrentHashMap()
    val has: MutableMap<AgentId, MutableMap<AttributeId, Has>> = ConcurrentHashMap()
    val hadBy: MutableMap<AttributeId, MutableMap<AgentId, Has>> = ConcurrentHashMap()
    val moderates: MutableMap<UniqueId<Pmf>, Moderates> = ConcurrentHashMap()
    val moderatedBy: MutableMap<AttributeId, MutableMap<UniqueId<Pmf>, Moderates>> = ConcurrentHashMap()
    val needs: MutableMap<UniqueId<Role>, MutableMap<AttributeId, Needs>> = ConcurrentHashMap()
    val neededBy: MutableMap<AttributeId, MutableMap<UniqueId<Role>, Needs>> = ConcurrentHashMap()
    val possesses: MutableMap<AgentId, MutableMap<UniqueId<Capability>, Possesses>> = ConcurrentHashMap()
    val possessedBy: MutableMap<UniqueId<Capability>, MutableMap<AgentId, Possesses>> = ConcurrentHashMap()
    val requires: MutableMap<UniqueId<Role>, MutableMap<UniqueId<Capability>, Requires>> = ConcurrentHashMap()
    val requiredBy: MutableMap<UniqueId<Capability>, MutableMap<UniqueId<Role>, Requires>> = ConcurrentHashMap()
    val uses: MutableMap<UniqueId<Role>, MutableMap<UniqueId<Pmf>, Uses>> = ConcurrentHashMap()
    val usedBy: MutableMap<UniqueId<Pmf>, MutableMap<UniqueId<Role>, Uses>> = ConcurrentHashMap()

}
