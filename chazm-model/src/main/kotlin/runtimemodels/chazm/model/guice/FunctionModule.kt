package runtimemodels.chazm.model.guice

import com.google.inject.AbstractModule
import runtimemodels.chazm.api.function.Effectiveness
import runtimemodels.chazm.api.function.Goodness
import runtimemodels.chazm.model.function.DefaultEffectiveness
import runtimemodels.chazm.model.function.DefaultGoodness

/**
 * The [FunctionModule] class provides a Guice binding module for functions.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
class FunctionModule : AbstractModule() {

    override fun configure() {
        bind(Effectiveness::class.java).to(DefaultEffectiveness::class.java)
        bind(Goodness::class.java).to(DefaultGoodness::class.java)
    }

}
