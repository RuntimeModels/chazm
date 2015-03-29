package model.organization;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import model.organization.entity.Role;
import model.organization.function.Goodness;
import model.organization.id.UniqueId;

class Functions {

	final Map<UniqueId<Role>, Goodness> goodnesses = new ConcurrentHashMap<>();

}