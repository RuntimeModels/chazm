package runtimemodels.chazm.model.parser.entity

import runtimemodels.chazm.api.entity.CapabilityId
import runtimemodels.chazm.api.organization.Organization
import runtimemodels.chazm.model.entity.EntityFactory
import runtimemodels.chazm.model.entity.impl.DefaultCapabilityId
import runtimemodels.chazm.model.parser.attribute
import runtimemodels.chazm.model.parser.build
import javax.inject.Inject
import javax.inject.Singleton
import javax.xml.namespace.QName
import javax.xml.stream.events.StartElement

@Singleton
internal class ParseCapability @Inject constructor(
    private val entityFactory: EntityFactory
) {
    fun canParse(qName: QName): Boolean = CAPABILITY_ELEMENT == qName.localPart

    operator fun invoke(element: StartElement, organization: Organization, capabilities: MutableMap<String, CapabilityId>) {
        val id = DefaultCapabilityId(element attribute NAME_ATTRIBUTE)
        build(id, capabilities, element, entityFactory::build, organization::add)
    }

    companion object {
        private const val CAPABILITY_ELEMENT = "Capability" //$NON-NLS-1$
        private const val NAME_ATTRIBUTE = "name" //$NON-NLS-1$
    }
}
