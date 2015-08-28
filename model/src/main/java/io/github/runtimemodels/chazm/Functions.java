package io.github.runtimemodels.chazm;

import io.github.runtimemodels.chazm.entity.Role;
import io.github.runtimemodels.chazm.function.Goodness;
import io.github.runtimemodels.chazm.id.UniqueId;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class Functions {

	final Map<UniqueId<Role>, Goodness> goodnesses = new ConcurrentHashMap<>();

}