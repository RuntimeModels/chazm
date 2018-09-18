package io.github.runtimemodels.chazm;

import runtimemodels.chazm.api.entity.Role;
import runtimemodels.chazm.api.function.Goodness;
import runtimemodels.chazm.api.id.UniqueId;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class Functions {

    final Map<UniqueId<Role>, Goodness> goodness = new ConcurrentHashMap<>();

}
