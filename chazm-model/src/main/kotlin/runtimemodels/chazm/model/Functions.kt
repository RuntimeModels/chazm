package runtimemodels.chazm.model

import runtimemodels.chazm.api.entity.RoleId
import runtimemodels.chazm.api.function.Goodness
import java.util.concurrent.ConcurrentHashMap

internal class Functions {

    val goodness: MutableMap<RoleId, Goodness> = ConcurrentHashMap()

}
