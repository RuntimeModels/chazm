package runtimemodels.chazm.model.parser

import runtimemodels.chazm.api.id.*
import runtimemodels.chazm.api.organization.Organization
import runtimemodels.chazm.model.entity.EntityFactory
import runtimemodels.chazm.model.message.E
import runtimemodels.chazm.model.parser.entity.*
import runtimemodels.chazm.model.parser.relation.AssignmentParser
import javax.inject.Inject
import javax.inject.Singleton
import javax.xml.namespace.QName
import javax.xml.stream.XMLEventReader
import javax.xml.stream.XMLStreamException
import javax.xml.stream.events.StartElement
import javax.xml.stream.events.XMLEvent

@Singleton
internal class RoleDiagramParser @Inject constructor(
    val entityFactory: EntityFactory,
    private val parseAgent: ParseAgent,
    private val assignmentParser: AssignmentParser,
    private val parseAttribute: ParseAttribute,
    private val parseCapability: ParseCapability,
    private val parseCharacteristic: ParseCharacteristic,
    private val parseInstanceGoal: ParseInstanceGoal,
    private val parsePmf: ParsePmf,
    private val parsePolicy: ParsePolicy,
    private val parseRole: ParseRole,
    private val parseSpecificationGoal: ParseSpecificationGoal
) {
    fun canParse(qName: QName): Boolean = ROLE_DIAGRAM_ELEMENT == qName.localPart

    fun parse(organization: Organization, reader: XMLEventReader, tagName: QName) {
        val agents = mutableMapOf<String, AgentId>()
        val attributes = mutableMapOf<String, AttributeId>()
        val capabilities = mutableMapOf<String, CapabilityId>()
        val characteristics = mutableMapOf<String, CharacteristicId>()
        val instanceGoals = mutableMapOf<String, InstanceGoalId>()
        val pmfs = mutableMapOf<String, PmfId>()
        val policies = mutableMapOf<String, PolicyId>()
        val roles = mutableMapOf<String, RoleId>()
        val specificationGoals = mutableMapOf<String, SpecificationGoalId>()
        val list1 = mutableListOf<() -> Unit>()
        val list2 = mutableListOf<() -> Unit>()
        reader.forEach {
            when {
                it is XMLEvent && it.isStartElement -> {
                    startElement(it, organization, reader, agents, attributes, capabilities, characteristics, instanceGoals, pmfs, policies, roles, specificationGoals, list1, list2)
                }
                it is XMLEvent && it.isEndElement -> {
                    val element = it.asEndElement()
                    if (element.name == tagName) {
                        for (r in list1) {
                            r()
                        }
                        for (r in list2) {
                            r()
                        }
                        return
                    }
                }
            }
        }
        throw XMLStreamException(E.MISSING_END_TAG[tagName]) // should not happen as XMLEventReader will do it for us
    }

    private fun startElement(
        it: XMLEvent,
        organization: Organization,
        reader: XMLEventReader,
        agents: MutableMap<String, AgentId>,
        attributes: MutableMap<String, AttributeId>,
        capabilities: MutableMap<String, CapabilityId>,
        characteristics: MutableMap<String, CharacteristicId>,
        instanceGoals: MutableMap<String, InstanceGoalId>,
        pmfs: MutableMap<String, PmfId>,
        policies: MutableMap<String, PolicyId>,
        roles: MutableMap<String, RoleId>,
        specificationGoals: MutableMap<String, SpecificationGoalId>,
        list1: MutableList<() -> Unit>,
        list2: MutableList<() -> Unit>
    ) {
        val element = it.asStartElement()
        val name = element.name
        when {
            parseAgent.canParse(name) -> parseAgent(
                element,
                organization,
                reader,
                name,
                agents,
                attributes,
                capabilities,
                list1
            )
            assignmentParser.canParse(name) -> assignmentParser.parse(
                element,
                organization,
                agents,
                roles,
                instanceGoals,
                list2
            )
            parseAttribute.canParse(name) -> parseAttribute(element, organization, attributes)
            parseCapability.canParse(name) -> parseCapability(element, organization, capabilities)
            parseCharacteristic.canParse(name) -> parseCharacteristic(element, organization, characteristics)
            parseInstanceGoal.canParse(name) -> parseInstanceGoal(element, organization, instanceGoals, specificationGoals, list1)
            parsePmf.canParse(name) -> parsePmf(element, organization, reader, name, pmfs, attributes, list1)
            parsePolicy.canParse(name) -> parsePolicy(element, organization, policies)
            parseRole.canParse(name) -> parseRole(
                element,
                organization,
                reader,
                name,
                roles,
                attributes,
                capabilities,
                characteristics,
                specificationGoals,
                list1
            )
            parseSpecificationGoal.canParse(name) -> parseSpecificationGoal(element, organization, specificationGoals)
        }
    }

    companion object {
        private const val ROLE_DIAGRAM_ELEMENT = "RoleDiagram" //$NON-NLS-1$
    }
}

private const val ID_ATTRIBUTE = "id" //$NON-NLS-1$
fun <T : UniqueId<U>, U> build(
    id: T,
    map: MutableMap<String, T>,
    element: StartElement,
    f: (T) -> U,
    c: (U) -> Unit
) {
    map[element attribute ID_ATTRIBUTE] = id
    val t = f(id)
    try {
        c(t)
    } catch (e: IllegalArgumentException) {
        throw XMLStreamException(e)
    }

}

infix fun StartElement.attribute(localPart: String): String {
    val attribute = getAttributeByName(QName(localPart))
        ?: throw XMLStreamException(E.MISSING_ATTRIBUTE_IN_TAG[name.localPart, localPart])
    return attribute.value
}

fun <T : UniqueId<U>, U, V : UniqueId<W>, W> addRelation(
    id1: T,
    ids: List<String>,
    map: Map<String, V>,
    c: (T, V) -> Unit,
    clazz: Class<W>
) {
    for (id in ids) {
        val id2 = map[id] ?: throw XMLStreamException(E.INCOMPLETE_XML_FILE[clazz.simpleName, id])
        c(id1, id2)
    }
}

inline fun <T : UniqueId<U>, U, V : UniqueId<W>, reified W : Any> addRelation(
    id1: T,
    ids: List<String>,
    map: Map<String, V>,
    c: (T, V) -> Unit
) {
    for (id in ids) {
        val id2 = map[id] ?: throw XMLStreamException(E.INCOMPLETE_XML_FILE[W::class.java.simpleName, id])
        c(id1, id2)
    }
}


private const val CHILD_ELEMENT = "child" //$NON-NLS-1$
fun collectIds(reader: XMLEventReader, tagName: QName): List<String> {
    val ids = mutableListOf<String>()
    reader.forEach {
        when {
            it is XMLEvent && it.isStartElement -> {
                val element = it.asStartElement()
                val name = element.name
                if (CHILD_ELEMENT == name.localPart) {
                    ids.add(reader.nextEvent().asCharacters().data)
                }
            }
            it is XMLEvent && it.isEndElement -> {
                val element = it.asEndElement()
                if (element.name == tagName) {
                    return ids
                }
            }
        }
    }
    throw XMLStreamException(E.MISSING_END_TAG[tagName]) // should not happen as XMLEventReader will do it for us
}
