package runtimemodels.chazm.model.parsers

import runtimemodels.chazm.api.id.AgentId
import runtimemodels.chazm.api.id.CapabilityId
import runtimemodels.chazm.api.organization.Organization
import runtimemodels.chazm.model.factory.RelationFactory
import javax.inject.Inject
import javax.inject.Singleton
import javax.xml.namespace.QName
import javax.xml.stream.XMLEventReader
import javax.xml.stream.XMLStreamException
import javax.xml.stream.events.StartElement

@Singleton
internal class PossessesParser @Inject constructor(
    val relationFactory: RelationFactory
) {
    fun canParse(qName: QName): Boolean = POSSESSES_ELEMENT == qName.localPart

    fun parse(
        id: AgentId,
        element: StartElement,
        organization: Organization,
        reader: XMLEventReader,
        name: QName,
        capabilities: Map<String, CapabilityId>,
        list: MutableList<RunLater>
    ) {
        val ids = collectIds(reader, name)
        try {
            val value = (element attribute SCORE_ATTRIBUTE).toDouble()
            list.add(object : RunLater {
                override fun run() {
                    addRelation(
                        id,
                        ids,
                        capabilities
                    ) { agentId, capabilityId ->
                        organization.possessesRelations.add(
                            relationFactory.build(
                                organization.agents[agentId]!!,
                                organization.capabilities[capabilityId]!!,
                                value
                            )
                        )
                    }
                }
            })
        } catch (e: NumberFormatException) {
            throw XMLStreamException(e)
        }
    }

    companion object {
        private val logger = org.slf4j.LoggerFactory.getLogger(PossessesParser::class.java)

        private const val POSSESSES_ELEMENT = "possesses" //$NON-NLS-1$
        private const val SCORE_ATTRIBUTE = "score" //$NON-NLS-1$
    }
}
