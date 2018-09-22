package runtimemodels.chazm.model.function;

import com.google.inject.AbstractModule;
import runtimemodels.chazm.api.function.Effectiveness;
import runtimemodels.chazm.api.function.Goodness;

/**
 * The {@linkplain FunctionModule} class provides a Guice binding module for functions.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class FunctionModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Effectiveness.class).to(DefaultEffectiveness.class);
        bind(Goodness.class).to(DefaultGoodness.class);
    }

}
