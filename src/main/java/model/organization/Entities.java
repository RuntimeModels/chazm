package model.organization;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import model.organization.entity.Agent;
import model.organization.entity.Attribute;
import model.organization.entity.Capability;
import model.organization.entity.Characteristic;
import model.organization.entity.InstanceGoal;
import model.organization.entity.Pmf;
import model.organization.entity.Policy;
import model.organization.entity.Role;
import model.organization.entity.SpecificationGoal;
import model.organization.id.UniqueId;

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