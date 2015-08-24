package io.github.chriszhong.model.organization;

import io.github.chriszhong.model.organization.entity.Role;
import io.github.chriszhong.model.organization.function.Goodness;
import io.github.chriszhong.model.organization.id.UniqueId;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class Functions {

	final Map<UniqueId<Role>, Goodness> goodnesses = new ConcurrentHashMap<>();

}