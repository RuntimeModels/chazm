package runtimemodels.chazm.model.parsers

import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.entity.InstanceGoal
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.id.AgentId
import runtimemodels.chazm.api.id.InstanceGoalId
import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.organization.Organization
import runtimemodels.chazm.model.factory.RelationFactory
import runtimemodels.chazm.model.message.E
import javax.inject.Inject
import javax.inject.Singleton
import javax.xml.namespace.QName
import javax.xml.stream.XMLStreamException
import javax.xml.stream.events.StartElement

@Singleton
internal class AssignmentParser @Inject constructor(
    private val relationFactory: RelationFactory
) {
    fun canParse(qName: QName): Boolean = ASSIGNMENT_ELEMENT == qName.localPart

    fun parse(
        element: StartElement,
        agents: Map<String, AgentId>,
        roles: Map<String, RoleId>,
        instanceGoals: Map<String, InstanceGoalId>,
        organization: Organization,
        list: MutableList<() -> Unit>
    ) {
        /* construction of an assignment depends on the existence of the instance goal */
        list.add {
            val idString = element attribute AGENT_ATTRIBUTE
            val agentId = agents[idString]
                    ?: throw XMLStreamException(E.INCOMPLETE_XML_FILE[Agent::class.java.simpleName, idString])
            val agent = organization.agents[agentId]!!
            val rId = element attribute ROLE_ATTRIBUTE
            val roleId = roles[rId]
                    ?: throw XMLStreamException(E.INCOMPLETE_XML_FILE[Role::class.java.simpleName, rId])
            val role = organization.roles[roleId]!!
            val gId = element attribute GOAL_ATTRIBUTE
            val goalId = instanceGoals[gId]
                    ?: throw XMLStreamException(E.INCOMPLETE_XML_FILE[InstanceGoal::class.java.simpleName, gId])
            val goal = organization.instanceGoals[goalId]!!
            val assignment = relationFactory.build(agent, role, goal)
            organization.add(assignment)
        }
    }

    companion object {
        private const val ASSIGNMENT_ELEMENT = "assignment" //$NON-NLS-1$
        private const val AGENT_ATTRIBUTE = "agent" //$NON-NLS-1$
        private const val ROLE_ATTRIBUTE = "role" //$NON-NLS-1$
        private const val GOAL_ATTRIBUTE = "goal" //$NON-NLS-1$
    }
}
