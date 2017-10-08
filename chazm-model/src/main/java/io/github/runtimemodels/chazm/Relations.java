package io.github.runtimemodels.chazm;

import io.github.runtimemodels.chazm.entity.Agent;
import io.github.runtimemodels.chazm.entity.Attribute;
import io.github.runtimemodels.chazm.entity.Capability;
import io.github.runtimemodels.chazm.entity.Characteristic;
import io.github.runtimemodels.chazm.entity.Pmf;
import io.github.runtimemodels.chazm.entity.Role;
import io.github.runtimemodels.chazm.entity.SpecificationGoal;
import io.github.runtimemodels.chazm.id.UniqueId;
import io.github.runtimemodels.chazm.relation.Achieves;
import io.github.runtimemodels.chazm.relation.Assignment;
import io.github.runtimemodels.chazm.relation.Contains;
import io.github.runtimemodels.chazm.relation.Has;
import io.github.runtimemodels.chazm.relation.Moderates;
import io.github.runtimemodels.chazm.relation.Needs;
import io.github.runtimemodels.chazm.relation.Possesses;
import io.github.runtimemodels.chazm.relation.Requires;
import io.github.runtimemodels.chazm.relation.Uses;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class Relations {

    final Map<UniqueId<Role>, Map<UniqueId<SpecificationGoal>, Achieves>> achieves = new ConcurrentHashMap<>();
    final Map<UniqueId<SpecificationGoal>, Map<UniqueId<Role>, Achieves>> achievedBy = new ConcurrentHashMap<>();
    final Map<UniqueId<Assignment>, Assignment> assignments = new ConcurrentHashMap<>();
    final Map<UniqueId<Agent>, Map<UniqueId<Assignment>, Assignment>> assignmentsByAgent = new ConcurrentHashMap<>();
    final Map<UniqueId<Role>, Map<UniqueId<Characteristic>, Contains>> contains = new ConcurrentHashMap<>();
    final Map<UniqueId<Characteristic>, Map<UniqueId<Role>, Contains>> containedBy = new ConcurrentHashMap<>();
    final Map<UniqueId<Agent>, Map<UniqueId<Attribute>, Has>> has = new ConcurrentHashMap<>();
    final Map<UniqueId<Attribute>, Map<UniqueId<Agent>, Has>> hadBy = new ConcurrentHashMap<>();
    final Map<UniqueId<Pmf>, Moderates> moderates = new ConcurrentHashMap<>();
    final Map<UniqueId<Attribute>, Map<UniqueId<Pmf>, Moderates>> moderatedBy = new ConcurrentHashMap<>();
    final Map<UniqueId<Role>, Map<UniqueId<Attribute>, Needs>> needs = new ConcurrentHashMap<>();
    final Map<UniqueId<Attribute>, Map<UniqueId<Role>, Needs>> neededBy = new ConcurrentHashMap<>();
    final Map<UniqueId<Agent>, Map<UniqueId<Capability>, Possesses>> possesses = new ConcurrentHashMap<>();
    final Map<UniqueId<Capability>, Map<UniqueId<Agent>, Possesses>> possessedBy = new ConcurrentHashMap<>();
    final Map<UniqueId<Role>, Map<UniqueId<Capability>, Requires>> requires = new ConcurrentHashMap<>();
    final Map<UniqueId<Capability>, Map<UniqueId<Role>, Requires>> requiredBy = new ConcurrentHashMap<>();
    final Map<UniqueId<Role>, Map<UniqueId<Pmf>, Uses>> uses = new ConcurrentHashMap<>();
    final Map<UniqueId<Pmf>, Map<UniqueId<Role>, Uses>> usedBy = new ConcurrentHashMap<>();

}
