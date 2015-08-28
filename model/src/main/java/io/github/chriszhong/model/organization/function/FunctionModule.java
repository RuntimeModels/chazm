package io.github.chriszhong.model.organization.function;

import io.github.runtimemodels.chazm.function.Effectiveness;
import io.github.runtimemodels.chazm.function.Goodness;

import com.google.inject.AbstractModule;

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
