package runtimemodels.chazm.model

import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.function.Goodness
import runtimemodels.chazm.api.id.UniqueId
import java.util.concurrent.ConcurrentHashMap

internal class Functions {

    val goodness: MutableMap<UniqueId<Role>, Goodness> = ConcurrentHashMap()

}
