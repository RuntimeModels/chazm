package runtimemodels.chazm.model.guice

import com.google.inject.AbstractModule
import com.google.inject.Provides
import runtimemodels.chazm.model.parser.XmlParser
import javax.xml.stream.XMLInputFactory

/**
 * The [ParsersModule] class provides a Guice binding module for parser.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
internal class ParsersModule : AbstractModule() {

    @Provides
    fun xmlInputFactory(): XMLInputFactory = javax.xml.stream.XMLInputFactory.newInstance()

    override fun configure() {
        bind(XmlParser::class.java)

        install(OrganizationModule())
    }

}
