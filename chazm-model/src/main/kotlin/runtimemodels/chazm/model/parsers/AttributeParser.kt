package runtimemodels.chazm.model.parsers

import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.id.AttributeId
import runtimemodels.chazm.api.organization.Organization
import runtimemodels.chazm.model.factory.EntityFactory
import runtimemodels.chazm.model.id.DefaultAttributeId
import javax.inject.Inject
import javax.inject.Singleton
import javax.xml.namespace.QName
import javax.xml.stream.XMLStreamException
import javax.xml.stream.events.StartElement

@Singleton
internal class AttributeParser @Inject constructor(
    private val entityFactory: EntityFactory
) {
    fun canParse(qName: QName): Boolean = ATTRIBUTE_ELEMENT == qName.localPart

    fun parse(element: StartElement, attributes: MutableMap<String, AttributeId>, organization: Organization) {
        val id = DefaultAttributeId(element attribute NAME_ATTRIBUTE)
        try {
            val type = Attribute.Type.valueOf(element attribute TYPE_ATTRIBUTE)
            build(id, attributes, element, { entityFactory.build(it, type) }, { organization.add(it) })
        } catch (e: IllegalArgumentException) {
            throw XMLStreamException(e)
        }
    }

    companion object {
        private const val ATTRIBUTE_ELEMENT = "Attribute" //$NON-NLS-1$
        private const val NAME_ATTRIBUTE = "name" //$NON-NLS-1$
        private const val TYPE_ATTRIBUTE = "type" //$NON-NLS-1$
    }
}
