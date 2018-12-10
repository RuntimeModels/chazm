package runtimemodels.chazm.model.parser.entity

import runtimemodels.chazm.api.entity.InstanceGoalId
import runtimemodels.chazm.api.entity.SpecificationGoal
import runtimemodels.chazm.api.entity.SpecificationGoalId
import runtimemodels.chazm.api.organization.Organization
import runtimemodels.chazm.model.entity.EntityFactory
import runtimemodels.chazm.model.entity.impl.DefaultInstanceGoalId
import runtimemodels.chazm.model.message.E
import runtimemodels.chazm.model.parser.attribute
import runtimemodels.chazm.model.parser.build
import javax.inject.Inject
import javax.inject.Singleton
import javax.xml.namespace.QName
import javax.xml.stream.XMLStreamException
import javax.xml.stream.events.StartElement

@Singleton
internal class ParseInstanceGoal @Inject constructor(
    private val entityFactory: EntityFactory
) {
    fun canParse(qName: QName): Boolean = INSTANCEGOAL_ELEMENT == qName.localPart

    operator fun invoke(
        element: StartElement,
        organization: Organization,
        instanceGoals: MutableMap<String, InstanceGoalId>,
        specificationGoals: Map<String, SpecificationGoalId>,
        list: MutableList<() -> Unit>
    ) {
        /* construction of an instance goal depends on the existence of the specification goal */
        list.add {
            val value = element attribute SPECIFICATION_ATTRIBUTE
            val goalId = specificationGoals[value]
                ?: throw XMLStreamException(E.INCOMPLETE_XML_FILE[SpecificationGoal::class.java.simpleName, value])
            val goal = organization.specificationGoals[goalId]!!
            val id = DefaultInstanceGoalId(element attribute NAME_ATTRIBUTE)
            // TODO parse parameter
            build(id, instanceGoals, element, { entityFactory.build(it, goal, emptyMap()) }, organization::add)
        }
    }

    companion object {
        private const val INSTANCEGOAL_ELEMENT = "InstanceGoal" //$NON-NLS-1$
        private const val NAME_ATTRIBUTE = "name" //$NON-NLS-1$
        private const val SPECIFICATION_ATTRIBUTE = "specification" //$NON-NLS-1$
    }
}
