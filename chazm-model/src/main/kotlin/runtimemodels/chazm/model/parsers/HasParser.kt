package runtimemodels.chazm.model.parsers

import runtimemodels.chazm.api.id.AgentId
import runtimemodels.chazm.api.id.AttributeId
import runtimemodels.chazm.api.organization.Organization
import runtimemodels.chazm.model.factory.RelationFactory
import javax.inject.Inject
import javax.inject.Singleton
import javax.xml.namespace.QName
import javax.xml.stream.XMLEventReader
import javax.xml.stream.XMLStreamException
import javax.xml.stream.events.StartElement

@Singleton
internal class HasParser @Inject constructor(
    private val relationFactory: RelationFactory
) {
    fun canParse(qName: QName): Boolean = HAS_ELEMENT == qName.localPart

    fun parse(
        id: AgentId,
        element: StartElement,
        organization: Organization,
        reader: XMLEventReader,
        name: QName,
        attributes: Map<String, AttributeId>,
        list: MutableList<RunLater>
    ) {
        val ids = collectIds(reader, name)
        try {
            val value = (element attribute VALUE_ATTRIBUTE).toDouble()
            list.add(object : RunLater {
                override fun run() {
                    addRelation(
                        id,
                        ids,
                        attributes
                    ) { agentId, attributeId ->
                        organization.hasRelations.add(
                            relationFactory.build(
                                organization.agents[agentId]!!,
                                organization.attributes[attributeId]!!,
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
        private const val HAS_ELEMENT = "has" //$NON-NLS-1$
        private const val VALUE_ATTRIBUTE = "value" //$NON-NLS-1$
    }
}
