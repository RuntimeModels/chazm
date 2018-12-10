package runtimemodels.chazm.model.parser.entity

import runtimemodels.chazm.api.id.CharacteristicId
import runtimemodels.chazm.api.organization.Organization
import runtimemodels.chazm.model.entity.EntityFactory
import runtimemodels.chazm.model.id.impl.DefaultCharacteristicId
import runtimemodels.chazm.model.parser.attribute
import runtimemodels.chazm.model.parser.build
import javax.inject.Inject
import javax.inject.Singleton
import javax.xml.namespace.QName
import javax.xml.stream.events.StartElement

@Singleton
internal class ParseCharacteristic @Inject constructor(
    private val entityFactory: EntityFactory
) {
    fun canParse(qName: QName): Boolean = CHARACTERISTIC_ELEMENT == qName.localPart

    operator fun invoke(element: StartElement, organization: Organization, characteristics: MutableMap<String, CharacteristicId>) {
        val id = DefaultCharacteristicId(element attribute NAME_ATTRIBUTE)
        build(id, characteristics, element, entityFactory::build, organization::add)
    }

    companion object {
        private const val CHARACTERISTIC_ELEMENT = "Characteristic" //$NON-NLS-1$
        private const val NAME_ATTRIBUTE = "name" //$NON-NLS-1$
    }
}
