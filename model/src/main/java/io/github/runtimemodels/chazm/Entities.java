package io.github.runtimemodels.chazm;

import io.github.runtimemodels.chazm.entity.Agent;
import io.github.runtimemodels.chazm.entity.Attribute;
import io.github.runtimemodels.chazm.entity.Capability;
import io.github.runtimemodels.chazm.entity.Characteristic;
import io.github.runtimemodels.chazm.entity.InstanceGoal;
import io.github.runtimemodels.chazm.entity.Pmf;
import io.github.runtimemodels.chazm.entity.Policy;
import io.github.runtimemodels.chazm.entity.Role;
import io.github.runtimemodels.chazm.entity.SpecificationGoal;
import io.github.runtimemodels.chazm.id.UniqueId;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class Entities {

    final Map<UniqueId<Agent>, Agent> agents = new ConcurrentHashMap<>();
    final Map<UniqueId<Attribute>, Attribute> attributes = new ConcurrentHashMap<>();
    final Map<UniqueId<Capability>, Capability> capabilities = new ConcurrentHashMap<>();
    final Map<UniqueId<Characteristic>, Characteristic> characteristics = new ConcurrentHashMap<>();
    final Map<UniqueId<InstanceGoal>, InstanceGoal> instanceGoals = new ConcurrentHashMap<>();
    final Map<UniqueId<SpecificationGoal>, Map<UniqueId<InstanceGoal>, InstanceGoal>> instanceGoalsBySpecificationGoal = new ConcurrentHashMap<>();
    final Map<UniqueId<Pmf>, Pmf> pmfs = new ConcurrentHashMap<>();
    final Map<UniqueId<Policy>, Policy> policies = new ConcurrentHashMap<>();
    final Map<UniqueId<Role>, Role> roles = new ConcurrentHashMap<>();
    final Map<UniqueId<SpecificationGoal>, SpecificationGoal> specificationGoals = new ConcurrentHashMap<>();

}
