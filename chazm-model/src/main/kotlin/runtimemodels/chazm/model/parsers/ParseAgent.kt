package runtimemodels.chazm.model.parsers

import runtimemodels.chazm.api.id.AgentId
import runtimemodels.chazm.api.id.AttributeId
import runtimemodels.chazm.api.id.CapabilityId
import runtimemodels.chazm.api.organization.Organization
import runtimemodels.chazm.model.factory.EntityFactory
import runtimemodels.chazm.model.id.DefaultAgentId
import runtimemodels.chazm.model.message.E
import javax.inject.Inject
import javax.inject.Singleton
import javax.xml.namespace.QName
import javax.xml.stream.XMLEventReader
import javax.xml.stream.XMLStreamException
import javax.xml.stream.events.StartElement
import javax.xml.stream.events.XMLEvent

@Singleton
internal class ParseAgent @Inject constructor(
    private val entityFactory: EntityFactory,
    private val hasParser: HasParser,
    private val possessesParser: PossessesParser
) {
    fun canParse(qName: QName): Boolean = AGENT_ELEMENT == qName.localPart

    operator fun invoke(
        element: StartElement,
        organization: Organization,
        reader: XMLEventReader,
        tagName: QName,
        agents: MutableMap<String, AgentId>,
        attributes: MutableMap<String, AttributeId>,
        capabilities: MutableMap<String, CapabilityId>,
        list: MutableList<() -> Unit>
    ) {
        val id: AgentId = DefaultAgentId(element attribute NAME_ATTRIBUTE)
        // TODO parse contact info
        build(
            id,
            agents,
            element,
            { entityFactory.build(it, emptyMap()) },
            organization::add
        )
        parseAgent(
            id,
            organization,
            reader,
            tagName,
            attributes,
            capabilities,
            list
        )
    }

    private fun parseAgent(
        id: AgentId,
        organization: Organization,
        reader: XMLEventReader,
        tagName: QName,
        attributes: Map<String, AttributeId>,
        capabilities: Map<String, CapabilityId>,
        list: MutableList<() -> Unit>
    ) {
        reader.forEach {
            when {
                it is XMLEvent && it.isStartElement -> {
                    val element = it.asStartElement()
                    val name = element.name
                    when {
                        hasParser.canParse(name) -> hasParser.parse(
                            id,
                            element,
                            organization,
                            reader,
                            name,
                            attributes,
                            list
                        )
                        possessesParser.canParse(name) -> possessesParser.parse(
                            id,
                            element,
                            organization,
                            reader,
                            name,
                            capabilities,
                            list
                        )
                    }
                }
                it is XMLEvent && it.isEndElement -> {
                    val element = it.asEndElement()
                    if (element.name == tagName) {
                        return
                    }
                }
            }
        }
        throw XMLStreamException(E.MISSING_END_TAG[tagName]) // should not happen as XMLEventReader will do it for us
    }

    companion object {
        private const val AGENT_ELEMENT = "Agent" //$NON-NLS-1$
        private const val NAME_ATTRIBUTE = "name" //$NON-NLS-1$
    }
}
