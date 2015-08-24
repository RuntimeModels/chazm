package io.github.chriszhong.model.organization;

import io.github.chriszhong.model.organization.entity.Agent;
import io.github.chriszhong.model.organization.entity.Attribute;
import io.github.chriszhong.model.organization.entity.Capability;
import io.github.chriszhong.model.organization.entity.Characteristic;
import io.github.chriszhong.model.organization.entity.Pmf;
import io.github.chriszhong.model.organization.entity.Role;
import io.github.chriszhong.model.organization.entity.SpecificationGoal;
import io.github.chriszhong.model.organization.id.UniqueId;
import io.github.chriszhong.model.organization.relation.Achieves;
import io.github.chriszhong.model.organization.relation.Assignment;
import io.github.chriszhong.model.organization.relation.Contains;
import io.github.chriszhong.model.organization.relation.Has;
import io.github.chriszhong.model.organization.relation.Moderates;
import io.github.chriszhong.model.organization.relation.Needs;
import io.github.chriszhong.model.organization.relation.Possesses;
import io.github.chriszhong.model.organization.relation.Requires;
import io.github.chriszhong.model.organization.relation.Uses;

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