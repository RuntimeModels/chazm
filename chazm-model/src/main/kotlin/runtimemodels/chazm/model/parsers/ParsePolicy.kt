package runtimemodels.chazm.model.parsers

import runtimemodels.chazm.api.id.PolicyId
import runtimemodels.chazm.api.organization.Organization
import runtimemodels.chazm.model.factory.EntityFactory
import runtimemodels.chazm.model.id.DefaultPolicyId
import javax.inject.Inject
import javax.inject.Singleton
import javax.xml.namespace.QName
import javax.xml.stream.events.StartElement

@Singleton
internal class ParsePolicy @Inject constructor(
    private val entityFactory: EntityFactory
) {
    fun canParse(qName: QName): Boolean = POLICY_ELEMENT == qName.localPart

    operator fun invoke(element: StartElement, organization: Organization, policies: MutableMap<String, PolicyId>) {
        val id = DefaultPolicyId(element attribute NAME_ATTRIBUTE)
        build(id, policies, element, entityFactory::build, organization::add)
    }

    companion object {
        private const val POLICY_ELEMENT = "Policy" //$NON-NLS-1$
        private const val NAME_ATTRIBUTE = "name" //$NON-NLS-1$
    }
}
