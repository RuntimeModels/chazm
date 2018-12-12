package runtimemodels.chazm.model.parser.entity

import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.entity.AttributeId
import runtimemodels.chazm.api.entity.PmfId
import runtimemodels.chazm.api.organization.Organization
import runtimemodels.chazm.model.entity.EntityFactory
import runtimemodels.chazm.model.entity.impl.DefaultPmfId
import runtimemodels.chazm.model.message.E
import runtimemodels.chazm.model.parser.addRelation
import runtimemodels.chazm.model.parser.attribute
import runtimemodels.chazm.model.parser.build
import runtimemodels.chazm.model.parser.collectIds
import runtimemodels.chazm.model.relation.RelationFactory
import javax.inject.Inject
import javax.inject.Singleton
import javax.xml.namespace.QName
import javax.xml.stream.XMLEventReader
import javax.xml.stream.XMLStreamException
import javax.xml.stream.events.StartElement

@Singleton
internal class ParsePmf @Inject constructor(
    private val entityFactory: EntityFactory,
    private val relationFactory: RelationFactory
) {
    fun canParse(qName: QName): Boolean = PMF_ELEMENT == qName.localPart

    operator fun invoke(
        element: StartElement,
        organization: Organization,
        reader: XMLEventReader,
        tagName: QName,
        pmfs: MutableMap<String, PmfId>,
        attributes: MutableMap<String, AttributeId>,
        list: MutableList<() -> Unit>
    ) {
        val id = DefaultPmfId(element attribute NAME_ATTRIBUTE)
        build(id, pmfs, element, entityFactory::build, organization::add)
        parsePmf(organization, reader, tagName, id, attributes, list)
    }

    private fun parsePmf(
        organization: Organization,
        reader: XMLEventReader,
        tagName: QName,
        id: PmfId,
        attributes: Map<String, AttributeId>,
        list: MutableList<() -> Unit>
    ) {
        while (reader.hasNext()) {
            val event = reader.nextEvent()
            if (event.isStartElement) {
                val element = event.asStartElement()
                val name = element.name
                if (MODERATES_ELEMENT == name.localPart) {
                    val ids = collectIds(reader, name)
                    list.add {
                        addRelation(
                            id,
                            ids,
                            attributes,
                            { pmfId, attributeId ->
                                organization.moderatesRelations.add(relationFactory.build(
                                    organization.pmfs[pmfId]!!,
                                    organization.attributes[attributeId]!!
                                ))
                            },
                            Attribute::class.java
                        )
                    }
                }
            } else if (event.isEndElement) {
                val element = event.asEndElement()
                if (element.name == tagName) {
                    return
                }
            }
        }
        throw XMLStreamException(E.MISSING_END_TAG[tagName]) // should not happen as XMLEventReader will do it for us
    }

    companion object {
        private const val PMF_ELEMENT = "Pmf" //$NON-NLS-1$
        private const val MODERATES_ELEMENT = "moderates" //$NON-NLS-1$
        private const val NAME_ATTRIBUTE = "name" //$NON-NLS-1$
    }
}
