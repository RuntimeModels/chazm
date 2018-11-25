package runtimemodels.chazm.model

import runtimemodels.chazm.api.entity.*
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.api.relation.*
import java.util.concurrent.ConcurrentHashMap

internal class Relations {

    val achieves: MutableMap<UniqueId<Role>, MutableMap<UniqueId<SpecificationGoal>, Achieves>> = ConcurrentHashMap()
    val achievedBy: MutableMap<UniqueId<SpecificationGoal>, MutableMap<UniqueId<Role>, Achieves>> = ConcurrentHashMap()
    val assignments: MutableMap<UniqueId<Assignment>, Assignment> = ConcurrentHashMap()
    val assignmentsByAgent: MutableMap<UniqueId<Agent>, MutableMap<UniqueId<Assignment>, Assignment>> = ConcurrentHashMap()
    val contains: MutableMap<UniqueId<Role>, MutableMap<UniqueId<Characteristic>, Contains>> = ConcurrentHashMap()
    val containedBy: MutableMap<UniqueId<Characteristic>, MutableMap<UniqueId<Role>, Contains>> = ConcurrentHashMap()
    val has: MutableMap<UniqueId<Agent>, MutableMap<UniqueId<Attribute>, Has>> = ConcurrentHashMap()
    val hadBy: MutableMap<UniqueId<Attribute>, MutableMap<UniqueId<Agent>, Has>> = ConcurrentHashMap()
    val moderates: MutableMap<UniqueId<Pmf>, Moderates> = ConcurrentHashMap()
    val moderatedBy: MutableMap<UniqueId<Attribute>, MutableMap<UniqueId<Pmf>, Moderates>> = ConcurrentHashMap()
    val needs: MutableMap<UniqueId<Role>, MutableMap<UniqueId<Attribute>, Needs>> = ConcurrentHashMap()
    val neededBy: MutableMap<UniqueId<Attribute>, MutableMap<UniqueId<Role>, Needs>> = ConcurrentHashMap()
    val possesses: MutableMap<UniqueId<Agent>, MutableMap<UniqueId<Capability>, Possesses>> = ConcurrentHashMap()
    val possessedBy: MutableMap<UniqueId<Capability>, MutableMap<UniqueId<Agent>, Possesses>> = ConcurrentHashMap()
    val requires: MutableMap<UniqueId<Role>, MutableMap<UniqueId<Capability>, Requires>> = ConcurrentHashMap()
    val requiredBy: MutableMap<UniqueId<Capability>, MutableMap<UniqueId<Role>, Requires>> = ConcurrentHashMap()
    val uses: MutableMap<UniqueId<Role>, MutableMap<UniqueId<Pmf>, Uses>> = ConcurrentHashMap()
    val usedBy: MutableMap<UniqueId<Pmf>, MutableMap<UniqueId<Role>, Uses>> = ConcurrentHashMap()

}
