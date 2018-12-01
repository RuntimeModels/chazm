package runtimemodels.chazm.model.parsers

import com.google.inject.Guice
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import runtimemodels.chazm.api.entity.Agent
import runtimemodels.chazm.api.entity.InstanceGoal
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.entity.SpecificationGoal
import runtimemodels.chazm.api.organization.Organization
import runtimemodels.chazm.model.id.*
import runtimemodels.chazm.model.message.E
import java.io.InputStream
import javax.xml.namespace.QName
import javax.xml.stream.XMLEventReader
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.XMLStreamException
import javax.xml.stream.events.XMLEvent

class XmlParserTest {

    private val injector = Guice.createInjector(ParsersModule())
    private val provider = injector.getProvider(XmlParser::class.java)

    @Test
    fun `XmlParser instantiates as a singleton`() {
        val parser1 = provider.get()
        val parser2 = provider.get()

        assertAll(
            { assertThat(parser1).isNotNull() },
            { assertThat(parser1).isSameAs(parser2) }
        )
    }

    @Test
    @Disabled
    @Throws(XMLStreamException::class)
    fun testParseCMS() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("CMS.xml")

        parser.parse(organization, inputStream)

        // TODO assert that organization state inputStream correct
    }

    @Test
    fun `reads the Sample1 file correctly`() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Sample1.xml")

        parser.parse(organization, inputStream)

        val agent1 = DefaultAgentId("Agent 1")
        val attribute1 = DefaultAttributeId("Attribute 1")
        val capability1 = DefaultCapabilityId("Capability 1")
        val characteristic1 = DefaultCharacteristicId("Characteristic 1")
        val instanceGoal1 = DefaultInstanceGoalId("Instance Goal 1")
        val pmf1 = DefaultPmfId("Pmf 1")
        val policy1 = DefaultPolicyId("Policy 1")
        val role1 = DefaultRoleId("Role 1")
        val specificationGoal1 = DefaultSpecificationGoalId("Goal 1")

        assertAll(
            // check all 9 entities entities
            { assertThat(organization.agents.size).isEqualTo(1) },
            { assertThat(organization.agents[agent1]).isNotNull() },
            { assertThat(organization.attributes.size).isEqualTo(1) },
            { assertThat(organization.attributes[attribute1]).isNotNull() },
            { assertThat(organization.capabilities.size).isEqualTo(1) },
            { assertThat(organization.capabilities[capability1]).isNotNull() },
            { assertThat(organization.characteristics.size).isEqualTo(1) },
            { assertThat(organization.characteristics[characteristic1]).isNotNull() },
            { assertThat(organization.instanceGoals.size).isEqualTo(1) },
            { assertThat(organization.instanceGoals[instanceGoal1]).isNotNull() },
            { assertThat(organization.pmfs.size).isEqualTo(1) },
            { assertThat(organization.pmfs[pmf1]).isNotNull() },
            { assertThat(organization.policies.size).isEqualTo(1) },
            { assertThat(organization.policies[policy1]).isNotNull() },
            { assertThat(organization.roles.size).isEqualTo(1) },
            { assertThat(organization.getRole(role1)).isNotNull() },
            { assertThat(organization.specificationGoals.size).isEqualTo(1) },
            { assertThat(organization.getSpecificationGoal(specificationGoal1)).isNotNull() },

            // check all 8 relations
            { assertThat(organization.getAchieves(role1).size).isEqualTo(1) },
            { assertThat(organization.getAchieves(role1).map { it.id }).containsOnly(specificationGoal1) },
            { assertThat(organization.assignments.size).isEqualTo(1) },
            { assertThat(organization.assignments.map { it.agent }.map { it.id }).containsOnly(agent1) },
            { assertThat(organization.assignments.map { it.role }.map { it.id }).containsOnly(role1) },
            { assertThat(organization.assignments.map { it.goal }.map { it.id }).containsOnly(instanceGoal1) },
            { assertThat(organization.getContains(role1).size).isEqualTo(1) },
            { assertThat(organization.getContains(role1).map { it.id }).containsOnly(characteristic1) },
            { assertThat(organization.getHas(agent1).size).isEqualTo(1) },
            { assertThat(organization.getHas(agent1).map { it.id }).containsOnly(attribute1) },
            { assertThat(organization.getModerates(pmf1)).isNotNull() },
            { assertThat(organization.getModerates(pmf1).id).isEqualTo(attribute1) },
            { assertThat(organization.getNeeds(role1).size).isEqualTo(1) },
            { assertThat(organization.getNeeds(role1).map { it.id }).containsOnly(attribute1) },
            { assertThat(organization.getPossesses(agent1).size).isEqualTo(1) },
            { assertThat(organization.getPossesses(agent1).map { it.id }).containsOnly(capability1) },
            { assertThat(organization.getRequires(role1).size).isEqualTo(1) },
            { assertThat(organization.getRequires(role1).map { it.id }).containsOnly(capability1) }
        )
    }

    @Test
    fun `reads the Sample2 file correctly`() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Sample2.xml")

        parser.parse(organization, inputStream)

        val c1 = DefaultCapabilityId("Capability 1")
        val r1 = DefaultRoleId("Role 1")
        val g1 = DefaultSpecificationGoalId("Goal 1")

        assertAll(
            { assertThat(organization.capabilities.size).isEqualTo(1) },
            { assertThat(organization.capabilities[c1]).isNotNull() },

            { assertThat(organization.roles.size).isEqualTo(1) },
            { assertThat(organization.getRole(r1)).isNotNull() },

            { assertThat(organization.specificationGoals.size).isEqualTo(1) },
            { assertThat(organization.getSpecificationGoal(g1)).isNotNull() },

            { assertThat(organization.getAchieves(r1).size).isEqualTo(1) },
            { assertThat(organization.getAchieves(r1).map { it.id }).containsOnly(g1) },

            { assertThat(organization.getRequires(r1).size).isEqualTo(1) },
            { assertThat(organization.getRequires(r1).map { it.id }).containsOnly(c1) }
        )
    }

    @Test
    fun `reads the Sample3 file correctly`() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Sample3.xml")

        parser.parse(organization, inputStream)

        val c1 = DefaultCapabilityId("Capability 1")
        val g1 = DefaultSpecificationGoalId("Goal 1")
        val r1 = DefaultRoleId("Role 1")

        assertAll(
            { assertThat(organization.capabilities.size).isEqualTo(1) },
            { assertThat(organization.capabilities[c1]).isNotNull() },

            { assertThat(organization.specificationGoals.size).isEqualTo(1) },
            { assertThat(organization.getSpecificationGoal(g1)).isNotNull() },

            { assertThat(organization.roles.size).isEqualTo(1) },
            { assertThat(organization.getRole(r1)).isNotNull() },

            { assertThat(organization.getAchieves(r1).size).isEqualTo(1) },
            { assertThat(organization.getAchieves(r1).map { it.id }).containsOnly(g1) },

            { assertThat(organization.getRequires(r1).size).isEqualTo(1) },
            { assertThat(organization.getRequires(r1).map { it.id }).containsOnly(c1) }
        )
    }

    @Test
    fun `reads the Sample4 file correctly`() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Sample4.xml")

        parser.parse(organization, inputStream)

        val c1 = DefaultCapabilityId("Capability 1")
        val c2 = DefaultCapabilityId("Capability 2")
        val c3 = DefaultCapabilityId("Capability 3")
        val g1 = DefaultSpecificationGoalId("Goal 1")
        val g2 = DefaultSpecificationGoalId("Goal 2")
        val g3 = DefaultSpecificationGoalId("Goal 3")
        val r1 = DefaultRoleId("Role 1")

        assertAll(
            { assertThat(organization.capabilities.size).isEqualTo(3) },
            { assertThat(organization.capabilities[c1]).isNotNull() },
            { assertThat(organization.capabilities[c2]).isNotNull() },
            { assertThat(organization.capabilities[c3]).isNotNull() },

            { assertThat(organization.specificationGoals.size).isEqualTo(3) },
            { assertThat(organization.getSpecificationGoal(g1)).isNotNull() },
            { assertThat(organization.getSpecificationGoal(g2)).isNotNull() },
            { assertThat(organization.getSpecificationGoal(g3)).isNotNull() },

            { assertThat(organization.roles.size).isEqualTo(1) },
            { assertThat(organization.getRole(r1)).isNotNull() },

            { assertThat(organization.getAchieves(r1).size).isEqualTo(3) },
            { assertThat(organization.getAchieves(r1).map { it.id }).containsOnly(g1, g2, g3) },

            { assertThat(organization.getRequires(r1).size).isEqualTo(3) },
            { assertThat(organization.getRequires(r1).map { it.id }).containsOnly(c1, c2, c3) }
        )
    }

    @Test
    fun `reads the Sample5 file correctly`() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Sample5.xml")

        parser.parse(organization, inputStream)

        val c1 = DefaultCapabilityId("Capability 1")
        val c2 = DefaultCapabilityId("Capability 2")
        val c3 = DefaultCapabilityId("Capability 3")
        val g1 = DefaultSpecificationGoalId("Goal 1")
        val g2 = DefaultSpecificationGoalId("Goal 2")
        val g3 = DefaultSpecificationGoalId("Goal 3")
        val r1 = DefaultRoleId("Role 1")

        assertAll(
            { assertThat(organization.capabilities.size).isEqualTo(3) },
            { assertThat(organization.capabilities[c1]).isNotNull() },
            { assertThat(organization.capabilities[c2]).isNotNull() },
            { assertThat(organization.capabilities[c3]).isNotNull() },

            { assertThat(organization.specificationGoals.size).isEqualTo(3) },
            { assertThat(organization.getSpecificationGoal(g1)).isNotNull() },
            { assertThat(organization.getSpecificationGoal(g2)).isNotNull() },
            { assertThat(organization.getSpecificationGoal(g3)).isNotNull() },

            { assertThat(organization.roles.size).isEqualTo(1) },
            { assertThat(organization.getRole(r1)).isNotNull() },

            { assertThat(organization.getAchieves(r1).size).isEqualTo(3) },
            { assertThat(organization.getAchieves(r1).map { it.id }).containsOnly(g1, g2, g3) },

            { assertThat(organization.getRequires(r1).size).isEqualTo(3) },
            { assertThat(organization.getRequires(r1).map { it.id }).containsOnly(c1, c2, c3) }
        )
    }

    @Test
    fun `reads the Sample6 file correctly`() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Sample6.xml")

        parser.parse(organization, inputStream)

        val r1 = DefaultRoleId("Role 1")

        assertAll(
            { assertThat(organization.roles.size).isEqualTo(1) },
            { assertThat(organization.getRole(r1)).isNotNull() },

            { assertThat(organization.getAchieves(r1).size).isEqualTo(0) },

            { assertThat(organization.getRequires(r1).size).isEqualTo(0) }
        )
    }

    @Test
    fun `handles duplicate entities correctly`() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Bad1.xml")

        val exception = assertThrows<XMLStreamException> { parser.parse(organization, inputStream) }

        assertAll(
            { assertThat(exception.cause).isInstanceOf(IllegalArgumentException::class.java) },
            { assertThat(exception.message).isEqualTo(IllegalArgumentException(E.ENTITY_ALREADY_EXISTS["Capability", DefaultCapabilityId("Capability 1")]).toString()) }
        )
    }

    @Test
    fun `handles missing or incorrect Id used in creating relations`() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Bad2.xml")

        val exception = assertThrows<XMLStreamException> { parser.parse(organization, inputStream) }

        assertThat(exception.message).isEqualTo(E.INCOMPLETE_XML_FILE[SpecificationGoal::class.java.simpleName, "7c57110b-d910-437a-b674-0817ce92a963"])
    }

    @Test
    fun `handles undefined attribute type`() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Bad3.xml")

        val exception = assertThrows<XMLStreamException> { parser.parse(organization, inputStream) }

        assertAll(
            { assertThat(exception.cause).isInstanceOf(IllegalArgumentException::class.java) },
            { assertThat(exception.message).isEqualTo("${IllegalArgumentException("No enum constant runtimemodels.chazm.api.entity.Attribute.Type.!")}") }
        )
    }

    @Test
    fun `handles missing attribute`() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Bad4.xml")

        val exception = assertThrows<XMLStreamException> { parser.parse(organization, inputStream) }

        assertThat(exception.message).isEqualTo(E.MISSING_ATTRIBUTE_IN_TAG["Attribute", "type"])
    }

    @Test
    fun `handles incorrect value in the 'value' attribute of the 'has' element`() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Bad5.xml")

        val exception = assertThrows<XMLStreamException> { parser.parse(organization, inputStream) }

        assertAll(
            { assertThat(exception.cause).isInstanceOf(NumberFormatException::class.java) },
            { assertThat(exception.message).isEqualTo(NumberFormatException("For input string: \"a\"").toString()) }
        )
    }

    @Test
    fun `handles incorrect value in the 'score' attribute of the 'possesses' element`() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Bad6.xml")

        val exception = assertThrows<XMLStreamException> { parser.parse(organization, inputStream) }

        assertAll(
            { assertThat(exception.cause).isInstanceOf(NumberFormatException::class.java) },
            { assertThat(exception.message).isEqualTo(NumberFormatException("For input string: \"a\"").toString()) }
        )
    }

    @Test
    fun `handles incorrect value in the 'value' attribute of the 'contains' element`() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Bad7.xml")

        val exception = assertThrows<XMLStreamException> { parser.parse(organization, inputStream) }

        assertAll(
            { assertThat(exception.cause).isInstanceOf(NumberFormatException::class.java) },
            { assertThat(exception.message).isEqualTo(NumberFormatException("For input string: \"a\"").toString()) }
        )
    }

    @Test
    fun `handles missing agent for 'assignment' element`() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Bad8.xml")

        val exception = assertThrows<XMLStreamException> { parser.parse(organization, inputStream) }

        assertThat(exception.message).isEqualTo(E.INCOMPLETE_XML_FILE[Agent::class.java.simpleName, "15cf816f-fcde-4e5e-8758-e004f48e4ee3"])
    }

    @Test
    fun `missing role for 'assignment' element`() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Bad9.xml")

        val exception = assertThrows<XMLStreamException> { parser.parse(organization, inputStream) }

        assertThat(exception.message).isEqualTo(E.INCOMPLETE_XML_FILE[Role::class.java.simpleName, "d3a3aa33-88c7-41a6-91a0-423e84354e7c"])
    }

    @Test
    fun `missing goal for 'assignment' element`() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Bad10.xml")

        val exception = assertThrows<XMLStreamException> { parser.parse(organization, inputStream) }

        assertThat(exception.message).isEqualTo(E.INCOMPLETE_XML_FILE[InstanceGoal::class.java.simpleName, "5a95c6f2-ee0a-48c4-be2d-9e4ad29a25b8"])
    }

    @Test
    fun `handles missing specification goal`() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Bad11.xml")

        val exception = assertThrows<XMLStreamException> { parser.parse(organization, inputStream) }

        assertThat(exception.message).isEqualTo(E.INCOMPLETE_XML_FILE[SpecificationGoal::class.java.simpleName, "7c57110b-d910-437a-b674-0817ce92a963"])
    }

    @Test
    fun `ignores unknown child elements in the 'Agent' element`() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Bad12.xml")

        parser.parse(organization, inputStream)

        assertThat(organization.agents.size).isEqualTo(1)
    }

    @Test
    fun `ignores unknown child elements in the 'Pmf' element`() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Bad13.xml")

        parser.parse(organization, inputStream)

        assertThat(organization.pmfs.size).isEqualTo(1)
    }

    @Test
    fun `ignores unknown child elements in the 'Role' element`() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Bad14.xml")

        parser.parse(organization, inputStream)

        assertThat(organization.roles.size).isEqualTo(1)
    }

    @Test
    fun `ignores unknown child elements in 'has' element`() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Bad15.xml")

        parser.parse(organization, inputStream)

        assertThat(organization.agents.size).isEqualTo(1)
    }

    @Test
    @Disabled
    @Throws(XMLStreamException::class)
    fun testMock1() {
        val inputStream = mock(InputStream::class.java)
        val factory = mock(XMLInputFactory::class.java)
        val reader = mock(XMLEventReader::class.java)
        val event = mock(XMLEvent::class.java)
        val name = mock(QName::class.java)
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        /* test missing </RoleDiagram> end tag */
        `when`(reader.hasNext()).thenReturn(true, false)
        `when`(reader.nextEvent()).thenReturn(event)
        `when`(event.isStartElement).thenReturn(true)
        `when`(name.localPart).thenReturn("RoleDiagram")
        `when`(name.toString()).thenReturn("RoleDiagram")

        val exception = assertThrows<XMLStreamException> { parser.parse(organization, inputStream) }

        assertThat(exception.message).isEqualTo(E.MISSING_END_TAG["RoleDiagram"])
    }

    @Test
    @Disabled
    @Throws(XMLStreamException::class)
    fun testMock2() {
        val inputStream = mock(InputStream::class.java)
        val factory = mock(XMLInputFactory::class.java)
        val reader = mock(XMLEventReader::class.java)
        val event = mock(XMLEvent::class.java)
        val name = mock(QName::class.java)
        val attribute = mock(javax.xml.stream.events.Attribute::class.java)
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        /* test missing </Agent> end tag */
        `when`(reader.hasNext()).thenReturn(true, true, false)
        `when`(reader.nextEvent()).thenReturn(event)
        `when`(event.isStartElement).thenReturn(true)
        `when`(name.localPart).thenReturn("RoleDiagram", "Agent")
        `when`(name.toString()).thenReturn("Agent")
        `when`(attribute.value).thenReturn("Agent 1")

        val exception = assertThrows<XMLStreamException> { parser.parse(organization, inputStream) }

        assertThat(exception.message).isEqualTo(E.MISSING_END_TAG["Agent"])
    }

    @Test
    @Disabled
    @Throws(XMLStreamException::class)
    fun testMock3() {
        val inputStream = mock(InputStream::class.java)
        val factory = mock(XMLInputFactory::class.java)
        val reader = mock(XMLEventReader::class.java)
        val event = mock(XMLEvent::class.java)
        val name = mock(QName::class.java)
        val attribute = mock(javax.xml.stream.events.Attribute::class.java)
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        /* test missing </Pmf> end tag */
        `when`(reader.hasNext()).thenReturn(true, true, false)
        `when`(reader.nextEvent()).thenReturn(event)
        `when`(event.isStartElement).thenReturn(true)
        `when`(name.localPart).thenReturn("RoleDiagram", "Pmf")
        `when`(name.toString()).thenReturn("Pmf")
        `when`(attribute.value).thenReturn("Pmf 1")

        val exception = assertThrows<XMLStreamException> { parser.parse(organization, inputStream) }

        assertThat(exception.message).isEqualTo(E.MISSING_END_TAG["Pmf"])
    }

    @Test
    @Disabled
    @Throws(XMLStreamException::class)
    fun testMock4() {
        val inputStream = mock(InputStream::class.java)
        val factory = mock(XMLInputFactory::class.java)
        val reader = mock(XMLEventReader::class.java)
        val event = mock(XMLEvent::class.java)
        val name = mock(QName::class.java)
        val attribute = mock(javax.xml.stream.events.Attribute::class.java)
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        /* test missing </Role> end tag */
        `when`(reader.hasNext()).thenReturn(true, true, false)
        `when`(reader.nextEvent()).thenReturn(event)
        `when`(event.isStartElement).thenReturn(true)
        `when`(name.localPart).thenReturn("RoleDiagram", "Role")
        `when`(name.toString()).thenReturn("Role")
        `when`(attribute.value).thenReturn("Role 1")

        val exception = assertThrows<XMLStreamException> { parser.parse(organization, inputStream) }

        assertThat(exception.message).isEqualTo(E.MISSING_END_TAG["Role"])
    }

    @Test
    @Disabled
    @Throws(XMLStreamException::class)
    fun testMock5() {
        val inputStream = mock(InputStream::class.java)
        val factory = mock(XMLInputFactory::class.java)
        val reader = mock(XMLEventReader::class.java)
        val event = mock(XMLEvent::class.java)
        val name = mock(QName::class.java)
        val attribute = mock(javax.xml.stream.events.Attribute::class.java)
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        /* test missing relations end tags: </has>, </possesses>, </moderates>, </achieves>, </contains>, </needs>, </requires> */
        `when`(reader.hasNext()).thenReturn(true, true, true, false)
        `when`(reader.nextEvent()).thenReturn(event)
        `when`(event.isStartElement).thenReturn(true)
        `when`(name.localPart).thenReturn("RoleDiagram", "Agent", "has")
        `when`(name.toString()).thenReturn("has")
        `when`(attribute.value).thenReturn("Agent 1")

        val exception = assertThrows<XMLStreamException> { parser.parse(organization, inputStream) }

        assertThat(exception.message).isEqualTo(E.MISSING_END_TAG["has"])
    }

}
