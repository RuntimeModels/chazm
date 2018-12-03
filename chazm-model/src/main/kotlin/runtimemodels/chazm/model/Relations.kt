package runtimemodels.chazm.model

import runtimemodels.chazm.api.id.*
import runtimemodels.chazm.api.relation.Assignment
import runtimemodels.chazm.api.relation.Requires
import runtimemodels.chazm.api.relation.Uses
import java.util.concurrent.ConcurrentHashMap

internal class Relations {

    //    val achieves: MutableMap<RoleId, MutableMap<SpecificationGoalId, Achieves>> = ConcurrentHashMap()
//    val achievedBy: MutableMap<SpecificationGoalId, MutableMap<RoleId, Achieves>> = ConcurrentHashMap()
    val assignments: MutableMap<UniqueId<Assignment>, Assignment> = ConcurrentHashMap()
    val assignmentsByAgent: MutableMap<AgentId, MutableMap<UniqueId<Assignment>, Assignment>> = ConcurrentHashMap()
    //    val contains: MutableMap<RoleId, MutableMap<CharacteristicId, Contains>> = ConcurrentHashMap()
//    val containedBy: MutableMap<CharacteristicId, MutableMap<RoleId, Contains>> = ConcurrentHashMap()
//    val has: MutableMap<AgentId, MutableMap<AttributeId, Has>> = ConcurrentHashMap()
//    val hadBy: MutableMap<AttributeId, MutableMap<AgentId, Has>> = ConcurrentHashMap()
//    val moderates: MutableMap<PmfId, Moderates> = ConcurrentHashMap()
//    val moderatedBy: MutableMap<AttributeId, MutableMap<PmfId, Moderates>> = ConcurrentHashMap()
//    val needs: MutableMap<RoleId, MutableMap<AttributeId, Needs>> = ConcurrentHashMap()
//    val neededBy: MutableMap<AttributeId, MutableMap<RoleId, Needs>> = ConcurrentHashMap()
//    val possesses: MutableMap<AgentId, MutableMap<CapabilityId, Possesses>> = ConcurrentHashMap()
//    val possessedBy: MutableMap<CapabilityId, MutableMap<AgentId, Possesses>> = ConcurrentHashMap()
    val requires: MutableMap<RoleId, MutableMap<CapabilityId, Requires>> = ConcurrentHashMap()
    val requiredBy: MutableMap<CapabilityId, MutableMap<RoleId, Requires>> = ConcurrentHashMap()
    val uses: MutableMap<RoleId, MutableMap<PmfId, Uses>> = ConcurrentHashMap()
    val usedBy: MutableMap<PmfId, MutableMap<RoleId, Uses>> = ConcurrentHashMap()

}
