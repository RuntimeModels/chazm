package io.github.runtimemodels.chazm.parsers;

import com.google.inject.AbstractModule;
import io.github.runtimemodels.chazm.OrganizationModule;

/**
 * The {@linkplain ParsersModule} class provides a Guice binding module for parsers.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
class ParsersModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(XmlParser.class);
        install(new OrganizationModule());
    }

}
