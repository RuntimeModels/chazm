package runtimemodels.chazm.model.parser.entity

import runtimemodels.chazm.api.id.SpecificationGoalId
import runtimemodels.chazm.api.organization.Organization
import runtimemodels.chazm.model.entity.EntityFactory
import runtimemodels.chazm.model.id.impl.DefaultSpecificationGoalId
import runtimemodels.chazm.model.parser.attribute
import runtimemodels.chazm.model.parser.build
import javax.inject.Inject
import javax.inject.Singleton
import javax.xml.namespace.QName
import javax.xml.stream.events.StartElement

@Singleton
internal class ParseSpecificationGoal @Inject constructor(
    private val entityFactory: EntityFactory
) {
    fun canParse(qName: QName): Boolean = GOAL_ELEMENT == qName.localPart

    operator fun invoke(element: StartElement, organization: Organization, specificationGoals: MutableMap<String, SpecificationGoalId>) {
        val id = DefaultSpecificationGoalId(element attribute NAME_ATTRIBUTE)
        build(id, specificationGoals, element, entityFactory::build, organization::add)
    }

    companion object {
        private const val GOAL_ELEMENT = "Goal" //$NON-NLS-1$
        private const val NAME_ATTRIBUTE = "name" //$NON-NLS-1$
    }

}
