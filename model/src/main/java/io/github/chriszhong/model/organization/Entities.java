package io.github.chriszhong.model.organization;

import io.github.chriszhong.model.organization.entity.Agent;
import io.github.chriszhong.model.organization.entity.Attribute;
import io.github.chriszhong.model.organization.entity.Capability;
import io.github.chriszhong.model.organization.entity.Characteristic;
import io.github.chriszhong.model.organization.entity.InstanceGoal;
import io.github.chriszhong.model.organization.entity.Pmf;
import io.github.chriszhong.model.organization.entity.Policy;
import io.github.chriszhong.model.organization.entity.Role;
import io.github.chriszhong.model.organization.entity.SpecificationGoal;
import io.github.chriszhong.model.organization.id.UniqueId;

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