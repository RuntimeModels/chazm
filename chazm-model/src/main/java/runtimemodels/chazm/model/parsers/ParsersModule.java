package runtimemodels.chazm.model.parsers;

import com.google.inject.AbstractModule;
import runtimemodels.chazm.model.OrganizationModule;

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
