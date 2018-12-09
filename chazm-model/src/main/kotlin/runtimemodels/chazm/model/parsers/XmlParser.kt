package runtimemodels.chazm.model.parsers

import runtimemodels.chazm.api.organization.Organization
import runtimemodels.chazm.model.message.L
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.XMLStreamException
import javax.xml.stream.events.XMLEvent

/**
 * The [XmlParser] class is able to parse XML files and populate an [Organization].
 *
 * @author Christopher Zhong
 * @since 4.0
 */
@Singleton
internal open class XmlParser @Inject constructor(
    private val xmlFactory: XMLInputFactory,
    private val roleDiagramParser: RoleDiagramParser
) {
    /**
     * Parses an XML file and populates an [Organization].
     *
     * @param organization the [Organization].
     * @param inputStream  the [InputStream].
     * @throws XMLStreamException
     */
    @Throws(XMLStreamException::class)
    fun parse(organization: Organization, inputStream: InputStream) {
        val reader = xmlFactory.createXMLEventReader(inputStream)
        reader.forEach {
            if (it is XMLEvent && it.isStartElement) {
                val element = it.asStartElement()
                logger.debug(L.START_TAG.get(), element)
                val name = element.name
                if (roleDiagramParser.canParse(name)) {
                    roleDiagramParser.parse(organization, reader, name)
                }
            }
        }
    }

    companion object {
        private val logger = org.slf4j.LoggerFactory.getLogger(XmlParser::class.java)
    }
}
