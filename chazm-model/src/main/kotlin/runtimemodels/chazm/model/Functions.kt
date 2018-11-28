package runtimemodels.chazm.model

import runtimemodels.chazm.api.function.Goodness
import runtimemodels.chazm.api.id.RoleId
import java.util.concurrent.ConcurrentHashMap

internal class Functions {

    val goodness: MutableMap<RoleId, Goodness> = ConcurrentHashMap()

}
