package runtimemodels.chazm.model.parsers

import com.google.inject.AbstractModule
import runtimemodels.chazm.model.OrganizationModule

/**
 * The [ParsersModule] class provides a Guice binding module for parsers.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
internal class ParsersModule : AbstractModule() {

    override fun configure() {
        bind(XmlParser::class.java)
        install(OrganizationModule())
    }

}
