package io.github.runtimemodels.chazm.id;

import com.google.inject.AbstractModule;

/**
 * The {@linkplain IdModule} class provides a Guice binding module for id.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class IdModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IdFactory.class).to(IdFactoryImpl.class);
	}

}
