package runtimemodels.chazm.model.parsers

import runtimemodels.chazm.api.entity.*
import runtimemodels.chazm.api.id.*
import runtimemodels.chazm.api.organization.Organization
import runtimemodels.chazm.model.factory.EntityFactory
import runtimemodels.chazm.model.factory.RelationFactory
import runtimemodels.chazm.model.id.*
import runtimemodels.chazm.model.message.E
import runtimemodels.chazm.model.message.L
import java.io.InputStream
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import javax.xml.namespace.QName
import javax.xml.stream.XMLEventReader
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.XMLStreamException
import javax.xml.stream.events.StartElement

/**
 * The [XmlParser] class is able to parse XML files and populate an [Organization].
 *
 * @author Christopher Zhong
 * @since 4.0
 */
@Singleton
internal open class XmlParser @Inject constructor(
    private val xmlFactory: XMLInputFactory,
    private val entityFactory: EntityFactory,
    private val relationFactory: RelationFactory
) {

    @FunctionalInterface
    private interface RunLater {
        @Throws(XMLStreamException::class)
        fun run()
    }

    /**
     * Parses an XML file and populates an [Organization].
     *
     * @param organization the [Organization].
     * @param inputStream  the [InputStream].
     * @throws XMLStreamException
     */
    @Throws(XMLStreamException::class)
    fun parse(organization: Organization, inputStream: InputStream) {
        val reader = xmlFactory.createXMLEventReader(inputStream)
        while (reader.hasNext()) {
            val event = reader.nextEvent()
            if (event.isStartElement) {
                val element = event.asStartElement()
                log.debug(L.START_TAG.get(), element)
                val name = element.name
                if (ROLE_DIAGRAM_ELEMENT == name.localPart) {
                    parseDiagram(organization, reader, name)
                }
            }
        }
    }

    @Throws(XMLStreamException::class)
    private fun parseDiagram(organization: Organization, reader: XMLEventReader, tagName: QName) {
        val agents = HashMap<String, AgentId>()
        val attributes = HashMap<String, AttributeId>()
        val capabilities = HashMap<String, CapabilityId>()
        val characteristics = HashMap<String, CharacteristicId>()
        val instanceGoals = HashMap<String, InstanceGoalId>()
        val pmfs = HashMap<String, PmfId>()
        val policies = HashMap<String, PolicyId>()
        val roles = HashMap<String, RoleId>()
        val specificationGoals = HashMap<String, SpecificationGoalId>()
        val list1 = ArrayList<RunLater>()
        val list2 = ArrayList<RunLater>()
        while (reader.hasNext()) {
            val event = reader.nextEvent()
            if (event.isStartElement) {
                val element = event.asStartElement()
                val name = element.name
                when (name.localPart) {
                    AGENT_ELEMENT -> {
                        val id: AgentId = DefaultAgentId(getAttributeValue(element, NAME_ATTRIBUTE))
                        // TODO parse contact info
                        build(id, agents, element, { entityFactory.build(it, emptyMap()) }, { organization.add(it) })
                        parseAgent(organization, reader, name, id, attributes, capabilities, list1)
                    }
                    ASSIGNMENT_ELEMENT -> parseAssignment(organization, element, agents, instanceGoals, roles, list2)
                    ATTRIBUTE_ELEMENT -> {
                        val id = DefaultAttributeId(getAttributeValue(element, NAME_ATTRIBUTE))
                        try {
                            val type = Attribute.Type.valueOf(getAttributeValue(element, TYPE_ATTRIBUTE))
                            build(id, attributes, element, { entityFactory.build(it, type) }, { organization.add(it) })
                        } catch (e: IllegalArgumentException) {
                            throw XMLStreamException(e)
                        }

                    }
                    CAPABILITY_ELEMENT -> {
                        val id = DefaultCapabilityId(getAttributeValue(element, NAME_ATTRIBUTE))
                        build(id, capabilities, element, { entityFactory.build(it) }, { organization.add(it) })
                    }
                    CHARACTERISTIC_ELEMENT -> {
                        val id = DefaultCharacteristicId(getAttributeValue(element, NAME_ATTRIBUTE))
                        build(id, characteristics, element, { entityFactory.build(it) }, { organization.add(it) })
                    }
                    INSTANCEGOAL_ELEMENT -> parseInstanceGoal(organization, element, specificationGoals, instanceGoals, list1)
                    PMF_ELEMENT -> {
                        val id = DefaultPmfId(getAttributeValue(element, NAME_ATTRIBUTE))
                        build(id, pmfs, element, { entityFactory.build(it) }, { organization.add(it) })
                        parsePmf(organization, reader, name, id, attributes, list1)
                    }
                    POLICY_ELEMENT -> {
                        val id = DefaultPolicyId(getAttributeValue(element, NAME_ATTRIBUTE))
                        build(id, policies, element, { entityFactory.build(it) }, { organization.add(it) })
                    }
                    ROLE_ELEMENT -> {
                        val id = DefaultRoleId(getAttributeValue(element, NAME_ATTRIBUTE))
                        build(id, roles, element, { entityFactory.build(it) }, { organization.add(it) })
                        parseRole(organization, reader, name, id, attributes, capabilities, characteristics, specificationGoals, list1)
                    }
                    GOAL_ELEMENT -> {
                        val id = DefaultSpecificationGoalId(getAttributeValue(element, NAME_ATTRIBUTE))
                        build(id, specificationGoals, element, { entityFactory.build(it) }, { organization.add(it) })
                    }
                }
            } else if (event.isEndElement) {
                val element = event.asEndElement()
                if (element.name == tagName) {
                    for (r in list1) {
                        r.run()
                    }
                    for (r in list2) {
                        r.run()
                    }
                    return
                }
            }
        }
        throw XMLStreamException(E.MISSING_END_TAG[tagName]) // should not happen as XMLEventReader will do it for us
    }

    @Throws(XMLStreamException::class)
    private fun <T : UniqueId<U>, U> build(
        id: T,
        map: MutableMap<String, T>,
        element: StartElement,
        f: (T) -> U,
        c: (U) -> Unit
    ) {
        map[getAttributeValue(element, ID_ATTRIBUTE)] = id
        val t = f(id)
        try {
            c(t)
        } catch (e: IllegalArgumentException) {
            throw XMLStreamException(e)
        }

    }

    @Throws(XMLStreamException::class)
    private fun getAttributeValue(element: StartElement, localPart: String): String {
        val attribute = element.getAttributeByName(QName(localPart))
            ?: throw XMLStreamException(E.MISSING_ATTRIBUTE_IN_TAG[element.name.localPart, localPart])
        return attribute.value
    }

    @Throws(XMLStreamException::class)
    private fun parseAgent(organization: Organization, reader: XMLEventReader, tagName: QName, id: AgentId,
                           attributes: Map<String, AttributeId>, capabilities: Map<String, CapabilityId>, list: MutableList<RunLater>) {
        while (reader.hasNext()) {
            val event = reader.nextEvent()
            if (event.isStartElement) {
                val element = event.asStartElement()
                val name = element.name
                when (name.localPart) {
                    HAS_ELEMENT -> {
                        val ids = collectChild(reader, name)
                        try {
                            val value = java.lang.Double.valueOf(getAttributeValue(element, VALUE_ATTRIBUTE))
                            list.add(object : RunLater {
                                override fun run() {
                                    return addRelation(
                                        id,
                                        ids,
                                        attributes,
                                        { agentId, attributeId ->
                                            organization.hasRelations.add(relationFactory.buildHas(
                                                organization.agents[agentId]!!,
                                                organization.attributes[attributeId]!!,
                                                value
                                            ))
                                        },
                                        Attribute::class.java
                                    )
                                }
                            })
                        } catch (e: NumberFormatException) {
                            throw XMLStreamException(e)
                        }

                    }
                    POSSESSES_ELEMENT -> {
                        val ids = collectChild(reader, name)
                        try {
                            val value = java.lang.Double.valueOf(getAttributeValue(element, SCORE_ATTRIBUTE))
                            list.add(object : RunLater {
                                override fun run() {
                                    return addRelation(
                                        id,
                                        ids,
                                        capabilities,
                                        { agentId, capabilityId ->
                                            organization.possessesRelations.add(relationFactory.buildPossesses(
                                                organization.agents[agentId]!!,
                                                organization.capabilities[capabilityId]!!,
                                                value
                                            ))
                                        },
                                        Capability::class.java
                                    )
                                }
                            })
                        } catch (e: NumberFormatException) {
                            throw XMLStreamException(e)
                        }

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

    private fun parseAssignment(organization: Organization, element: StartElement, agents: Map<String, AgentId>,
                                instanceGoals: Map<String, InstanceGoalId>, roles: Map<String, RoleId>, list: MutableList<RunLater>) {
        /* construction of an assignment depends on the existence of the instance goal */
        list.add(object : RunLater {
            override fun run() {
                val idString = getAttributeValue(element, AGENT_ATTRIBUTE)
                val agentId = agents[idString]
                    ?: throw XMLStreamException(E.INCOMPLETE_XML_FILE[Agent::class.java.simpleName, idString])
                val agent = organization.agents[agentId]!!
                val rId = getAttributeValue(element, ROLE_ATTRIBUTE)
                val roleId = roles[rId]
                    ?: throw XMLStreamException(E.INCOMPLETE_XML_FILE[Role::class.java.simpleName, rId])
                val role = organization.roles[roleId]!!
                val gId = getAttributeValue(element, GOAL_ATTRIBUTE)
                val goalId = instanceGoals[gId]
                    ?: throw XMLStreamException(E.INCOMPLETE_XML_FILE[InstanceGoal::class.java.simpleName, gId])
                val goal = organization.instanceGoals[goalId]!!
                val assignment = relationFactory.buildAssignment(agent, role, goal)
                return organization.add(assignment)
            }
        })
    }

    private fun parseInstanceGoal(organization: Organization, element: StartElement,
                                  specificationGoals: Map<String, SpecificationGoalId>, instanceGoals: MutableMap<String, InstanceGoalId>,
                                  list: MutableList<RunLater>) {
        /* construction of an instance goal depends on the existence of the specification goal */
        list.add(object : RunLater {
            override fun run() {
                val value = getAttributeValue(element, SPECIFICATION_ATTRIBUTE)
                val goalId = specificationGoals[value]
                    ?: throw XMLStreamException(E.INCOMPLETE_XML_FILE[SpecificationGoal::class.java.simpleName, value])
                val goal = organization.specificationGoals[goalId]!!
                val id = DefaultInstanceGoalId(getAttributeValue(element, NAME_ATTRIBUTE))
                // TODO parse parameter
                return build(id, instanceGoals, element, { entityFactory.build(it, goal, emptyMap()) }, { organization.add(it) })
            }
        })
    }

    @Throws(XMLStreamException::class)
    private fun parsePmf(organization: Organization, reader: XMLEventReader, tagName: QName, id: PmfId,
                         attributes: Map<String, AttributeId>, list: MutableList<RunLater>) {
        while (reader.hasNext()) {
            val event = reader.nextEvent()
            if (event.isStartElement) {
                val element = event.asStartElement()
                val name = element.name
                if (MODERATES_ELEMENT == name.localPart) {
                    val ids = collectChild(reader, name)
                    list.add(object : RunLater {
                        override fun run() {
                            return addRelation(
                                id,
                                ids,
                                attributes,
                                { pmfId, attributeId ->
                                    organization.moderatesRelations.add(relationFactory.buildModerates(
                                        organization.pmfs[pmfId]!!,
                                        organization.attributes[attributeId]!!
                                    ))
                                },
                                Attribute::class.java
                            )
                        }
                    })
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

    @Throws(XMLStreamException::class)
    private fun parseRole(organization: Organization, reader: XMLEventReader, tagName: QName, id: RoleId,
                          attributes: Map<String, AttributeId>, capabilities: Map<String, CapabilityId>,
                          characteristics: Map<String, CharacteristicId>, goals: Map<String, SpecificationGoalId>, list: MutableList<RunLater>) {
        while (reader.hasNext()) {
            val event = reader.nextEvent()
            if (event.isStartElement) {
                val element = event.asStartElement()
                val name = element.name
                when (name.localPart) {
                    ACHIEVES_ELEMENT -> {
                        val ids = collectChild(reader, name)
                        list.add(object : RunLater {
                            override fun run() {
                                return addRelation(
                                    id,
                                    ids,
                                    goals,
                                    { roleId, goalId ->
                                        organization.achievesRelations.add(relationFactory.buildAchieves(
                                            organization.roles[roleId]!!,
                                            organization.specificationGoals[goalId]!!
                                        ))
                                    },
                                    SpecificationGoal::class.java
                                )
                            }
                        })
                    }
                    CONTAINS_ELEMENT -> {
                        val ids = collectChild(reader, name)
                        try {
                            val value = java.lang.Double.valueOf(getAttributeValue(element, VALUE_ATTRIBUTE))
                            list.add(object : RunLater {
                                override fun run() {
                                    return addRelation(
                                        id,
                                        ids,
                                        characteristics,
                                        { roleId, characteristicId ->
                                            organization.containsRelations.add(relationFactory.buildContains(
                                                organization.roles[roleId]!!,
                                                organization.characteristics[characteristicId]!!,
                                                value
                                            ))
                                        },
                                        Characteristic::class.java
                                    )
                                }
                            })
                        } catch (e: NumberFormatException) {
                            throw XMLStreamException(e)
                        }

                    }
                    NEEDS_ELEMENT -> {
                        val ids = collectChild(reader, name)
                        list.add(object : RunLater {
                            override fun run() {
                                return addRelation(
                                    id,
                                    ids,
                                    attributes,
                                    { roleId, attributeId ->
                                        organization.needsRelations.add(relationFactory.buildNeeds(
                                            organization.roles[roleId]!!,
                                            organization.attributes[attributeId]!!
                                        ))
                                    },
                                    Attribute::class.java
                                )
                            }
                        })
                    }
                    REQUIRES_ELEMENT -> {
                        val ids = collectChild(reader, name)
                        list.add(object : RunLater {
                            override fun run() {
                                return addRelation(
                                    id,
                                    ids,
                                    capabilities,
                                    { roleId, capabilityId ->
                                        organization.requiresRelations.add(relationFactory.buildRequires(
                                            organization.roles[roleId]!!,
                                            organization.capabilities[capabilityId]!!
                                        ))
                                    },
                                    Capability::class.java
                                )
                            }
                        })
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

    @Throws(XMLStreamException::class)
    private fun <T : UniqueId<V>, U : UniqueId<W>, V, W> addRelation(
        entity1: T,
        ids: List<String>,
        map: Map<String, U>,
        c: (T, U) -> Unit,
        clazz: Class<W>
    ) {
        for (id in ids) {
            val entity2 = map[id] ?: throw XMLStreamException(E.INCOMPLETE_XML_FILE[clazz.simpleName, id])
            c(entity1, entity2)
        }
    }

    @Throws(XMLStreamException::class)
    private fun collectChild(reader: XMLEventReader, tagName: QName): List<String> {
        val ids = ArrayList<String>()
        while (reader.hasNext()) {
            val event = reader.nextEvent()
            if (event.isStartElement) {
                val element = event.asStartElement()
                val name = element.name
                if (CHILD_ELEMENT == name.localPart) {
                    ids.add(reader.nextEvent().asCharacters().data)
                }
            } else if (event.isEndElement) {
                val element = event.asEndElement()
                if (element.name == tagName) {
                    return ids
                }
            }
        }
        throw XMLStreamException(E.MISSING_END_TAG[tagName]) // should not happen as XMLEventReader will do it for us
    }

    companion object {
        private val log = org.slf4j.LoggerFactory.getLogger(XmlParser::class.java)

        private const val ROLE_DIAGRAM_ELEMENT = "RoleDiagram" //$NON-NLS-1$
        private const val AGENT_ELEMENT = "Agent" //$NON-NLS-1$
        private const val ATTRIBUTE_ELEMENT = "Attribute" //$NON-NLS-1$
        private const val CAPABILITY_ELEMENT = "Capability" //$NON-NLS-1$
        private const val CHARACTERISTIC_ELEMENT = "Characteristic" //$NON-NLS-1$
        private const val INSTANCEGOAL_ELEMENT = "InstanceGoal" //$NON-NLS-1$
        private const val PMF_ELEMENT = "Pmf" //$NON-NLS-1$
        private const val POLICY_ELEMENT = "Policy" //$NON-NLS-1$
        private const val ROLE_ELEMENT = "Role" //$NON-NLS-1$
        private const val GOAL_ELEMENT = "Goal" //$NON-NLS-1$
        private const val ACHIEVES_ELEMENT = "achieves" //$NON-NLS-1$
        private const val ASSIGNMENT_ELEMENT = "assignment" //$NON-NLS-1$
        private const val CONTAINS_ELEMENT = "contains" //$NON-NLS-1$
        private const val HAS_ELEMENT = "has" //$NON-NLS-1$
        private const val MODERATES_ELEMENT = "moderates" //$NON-NLS-1$
        private const val NEEDS_ELEMENT = "needs" //$NON-NLS-1$
        private const val POSSESSES_ELEMENT = "possesses" //$NON-NLS-1$
        private const val REQUIRES_ELEMENT = "requires" //$NON-NLS-1$
        private const val ID_ATTRIBUTE = "id" //$NON-NLS-1$
        private const val NAME_ATTRIBUTE = "name" //$NON-NLS-1$
        private const val TYPE_ATTRIBUTE = "type" //$NON-NLS-1$
        private const val SPECIFICATION_ATTRIBUTE = "specification" //$NON-NLS-1$
        private const val VALUE_ATTRIBUTE = "value" //$NON-NLS-1$
        private const val SCORE_ATTRIBUTE = "score" //$NON-NLS-1$
        private const val AGENT_ATTRIBUTE = "agent" //$NON-NLS-1$
        private const val ROLE_ATTRIBUTE = "role" //$NON-NLS-1$
        private const val GOAL_ATTRIBUTE = "goal" //$NON-NLS-1$
        private const val CHILD_ELEMENT = "child" //$NON-NLS-1$
    }
}
