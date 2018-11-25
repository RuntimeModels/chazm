package runtimemodels.chazm.model.parsers

import com.google.inject.Guice
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import runtimemodels.chazm.api.Organization
import runtimemodels.chazm.api.entity.*
import runtimemodels.chazm.model.id.IdFactory
import runtimemodels.chazm.model.message.E
import java.io.InputStream
import javax.xml.namespace.QName
import javax.xml.stream.XMLEventReader
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.XMLStreamException
import javax.xml.stream.events.XMLEvent

//@ExtendWith(MockitoExtension.class)
class XmlParserTest {

    private val injector = Guice.createInjector(ParsersModule())
    private val provider = injector.getProvider(XmlParser::class.java)
    private val idf = injector.getInstance(IdFactory::class.java)

    @Test
    fun testXmlParser() {
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
    @Disabled
    @Throws(XMLStreamException::class)
    fun testParseSample1() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Sample1.xml")

        parser.parse(organization, inputStream)

        val agent1 = idf.build(Agent::class.java, "Agent 1")
        val attribute1 = idf.build(Attribute::class.java, "Attribute 1")
        val capability1 = idf.build(Capability::class.java, "Capability 1")
        val characteristic1 = idf.build(Characteristic::class.java, "Characteristic 1")
        val instanceGoal1 = idf.build(InstanceGoal::class.java, "Instance Goal 1")
        val pmf1 = idf.build(Pmf::class.java, "Pmf 1")
        val policy1 = idf.build(Policy::class.java, "Policy 1")
        val role1 = idf.build(Role::class.java, "Role 1")
        val specificationGoal1 = idf.build(SpecificationGoal::class.java, "Goal 1")


        assertAll(
            // check all 9 entities entities
            { assertThat(organization.agents.size).isEqualTo(1) },
            { assertThat(organization.getAgent(agent1)).isNotNull() },
            { assertThat(organization.attributes.size).isEqualTo(1) },
            { assertThat(organization.getAttribute(attribute1)).isNotNull() },
            { assertThat(organization.capabilities.size).isEqualTo(1) },
            { assertThat(organization.getCapability(capability1)).isNotNull() },
            { assertThat(organization.characteristics.size).isEqualTo(1) },
            { assertThat(organization.getCharacteristic(characteristic1)).isNotNull() },
            { assertThat(organization.instanceGoals.size).isEqualTo(1) },
            { assertThat(organization.getInstanceGoal(instanceGoal1)).isNotNull() },
            { assertThat(organization.pmfs.size).isEqualTo(1) },
            { assertThat(organization.getPmf(pmf1)).isNotNull() },
            { assertThat(organization.policies.size).isEqualTo(1) },
            { assertThat(organization.getPolicy(policy1)).isNotNull() },
            { assertThat(organization.roles.size).isEqualTo(1) },
            { assertThat(organization.getRole(role1)).isNotNull() },
            { assertThat(organization.specificationGoals.size).isEqualTo(1) },
            { assertThat(organization.getSpecificationGoal(specificationGoal1)).isNotNull() },

            // check all 8 relations
            { assertThat(organization.getAchieves(role1).size).isEqualTo(1) },
            { assertThat(organization.getAchieves(role1).map { it.id }.toSet()).containsExactly(specificationGoal1) },
            { assertThat(organization.assignments.size).isEqualTo(1) },
            { assertThat(organization.assignments.map { it.agent }.map { it.id }.toSet()).containsExactly(agent1) },
            { assertThat(organization.assignments.map { it.role }.map { it.id }.toSet()).containsExactly(role1) },
            { assertThat(organization.assignments.map { it.goal }.map { it.id }.toSet()).containsExactly(instanceGoal1) },
            { assertThat(organization.getContains(role1).size).isEqualTo(1) },
            { assertThat(organization.getContains(role1).map { it.id }.toSet()).containsExactly(characteristic1) },
            { assertThat(organization.getHas(agent1).size).isEqualTo(1) },
            { assertThat(organization.getHas(agent1).map { it.id }.toSet()).containsExactly(attribute1) },
            { assertThat(organization.getModerates(pmf1)).isNotNull() },
            { assertThat(organization.getModerates(pmf1).id).isEqualTo(attribute1) },
            { assertThat(organization.getNeeds(role1).size).isEqualTo(1) },
            { assertThat(organization.getNeeds(role1).map { it.id }.toSet()).containsExactly(attribute1) },
            { assertThat(organization.getPossesses(agent1).size).isEqualTo(1) },
            { assertThat(organization.getPossesses(agent1).map { it.id }.toSet()).containsExactly(capability1) },
            { assertThat(organization.getRequires(role1).size).isEqualTo(1) },
            { assertThat(organization.getRequires(role1).map { it.id }.toSet()).containsExactly(capability1) }
        )
    }

    @Test
    @Disabled
    @Throws(XMLStreamException::class)
    fun testParseSample2() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Sample2.xml")

        parser.parse(organization, inputStream)

        val c1 = idf.build(Capability::class.java, "Capability 1")
        val r1 = idf.build(Role::class.java, "Role 1")
        val g1 = idf.build(SpecificationGoal::class.java, "Goal 1")

        assertAll(
            { assertThat(organization.capabilities.size).isEqualTo(1) },
            { assertThat(organization.getCapability(c1)).isNotNull() },

            { assertThat(organization.roles.size).isEqualTo(1) },
            { assertThat(organization.getRole(r1)).isNotNull() },

            { assertThat(organization.specificationGoals.size).isEqualTo(1) },
            { assertThat(organization.getSpecificationGoal(g1)).isNotNull() },

            { assertThat(organization.getAchieves(r1).size).isEqualTo(1) },
            { assertThat(organization.getAchieves(r1).map { it.id }.toSet()).containsExactly(g1) },

            { assertThat(organization.getRequires(r1).size).isEqualTo(1) },
            { assertThat(organization.getRequires(r1).map { it.id }.toSet()).containsExactly(c1) }
        )
    }

    @Test
    @Disabled
    @Throws(XMLStreamException::class)
    fun testSample3() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Sample3.xml")

        parser.parse(organization, inputStream)

        val c1 = idf.build(Capability::class.java, "Capability 1")
        val g1 = idf.build(SpecificationGoal::class.java, "Goal 1")
        val r1 = idf.build(Role::class.java, "Role 1")

        assertAll(
            { assertThat(organization.capabilities.size).isEqualTo(1) },
            { assertThat(organization.getCapability(c1)).isNotNull() },

            { assertThat(organization.specificationGoals.size).isEqualTo(1) },
            { assertThat(organization.getSpecificationGoal(g1)).isNotNull() },

            { assertThat(organization.roles.size).isEqualTo(1) },
            { assertThat(organization.getRole(r1)).isNotNull() },

            { assertThat(organization.getAchieves(r1).size).isEqualTo(1) },
            { assertThat(organization.getAchieves(r1).map { it.id }.toSet()).containsExactly(g1) },

            { assertThat(organization.getRequires(r1).size).isEqualTo(1) },
            { assertThat(organization.getRequires(r1).map { it.id }.toSet()).containsExactly(c1) }
        )
    }

    @Test
    @Disabled
    @Throws(XMLStreamException::class)
    fun testSample4() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Sample4.xml")

        parser.parse(organization, inputStream)

        val c1 = idf.build(Capability::class.java, "Capability 1")
        val c2 = idf.build(Capability::class.java, "Capability 2")
        val c3 = idf.build(Capability::class.java, "Capability 3")
        val g1 = idf.build(SpecificationGoal::class.java, "Goal 1")
        val g2 = idf.build(SpecificationGoal::class.java, "Goal 2")
        val g3 = idf.build(SpecificationGoal::class.java, "Goal 3")
        val r1 = idf.build(Role::class.java, "Role 1")

        assertAll(
            { assertThat(organization.capabilities.size).isEqualTo(3) },
            { assertThat(organization.getCapability(c1)).isNotNull() },
            { assertThat(organization.getCapability(c2)).isNotNull() },
            { assertThat(organization.getCapability(c3)).isNotNull() },

            { assertThat(organization.specificationGoals.size).isEqualTo(3) },
            { assertThat(organization.getSpecificationGoal(g1)).isNotNull() },
            { assertThat(organization.getSpecificationGoal(g2)).isNotNull() },
            { assertThat(organization.getSpecificationGoal(g3)).isNotNull() },

            { assertThat(organization.roles.size).isEqualTo(1) },
            { assertThat(organization.getRole(r1)).isNotNull() },

            { assertThat(organization.getAchieves(r1).size).isEqualTo(3) },
            { assertThat(organization.getAchieves(r1).map { it.id }.toSet()).containsExactly(g1, g2, g3) },

            { assertThat(organization.getRequires(r1).size).isEqualTo(3) },
            { assertThat(organization.getRequires(r1).map { it.id }.toSet()).containsExactly(c1, c2, c3) }
        )
    }

    @Test
    @Disabled
    @Throws(XMLStreamException::class)
    fun testSample5() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Sample5.xml")

        parser.parse(organization, inputStream)

        val c1 = idf.build(Capability::class.java, "Capability 1")
        val c2 = idf.build(Capability::class.java, "Capability 2")
        val c3 = idf.build(Capability::class.java, "Capability 3")
        val g1 = idf.build(SpecificationGoal::class.java, "Goal 1")
        val g2 = idf.build(SpecificationGoal::class.java, "Goal 2")
        val g3 = idf.build(SpecificationGoal::class.java, "Goal 3")
        val r1 = idf.build(Role::class.java, "Role 1")

        assertAll(
            { assertThat(organization.capabilities.size).isEqualTo(3) },
            { assertThat(organization.getCapability(c1)).isNotNull() },
            { assertThat(organization.getCapability(c2)).isNotNull() },
            { assertThat(organization.getCapability(c3)).isNotNull() },

            { assertThat(organization.specificationGoals.size).isEqualTo(3) },
            { assertThat(organization.getSpecificationGoal(g1)).isNotNull() },
            { assertThat(organization.getSpecificationGoal(g2)).isNotNull() },
            { assertThat(organization.getSpecificationGoal(g3)).isNotNull() },

            { assertThat(organization.roles.size).isEqualTo(1) },
            { assertThat(organization.getRole(r1)).isNotNull() },

            { assertThat(organization.getAchieves(r1).size).isEqualTo(3) },
            { assertThat(organization.getAchieves(r1).map { it.id }.toSet()).containsExactly(g1, g2, g3) },

            { assertThat(organization.getRequires(r1).size).isEqualTo(3) },
            { assertThat(organization.getRequires(r1).map { it.id }.toSet()).containsExactly(c1, c2, c3) }
        )
    }

    @Test
    @Disabled
    @Throws(XMLStreamException::class)
    fun testSample6() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Sample6.xml")

        parser.parse(organization, inputStream)

        val r1 = idf.build(Role::class.java, "Role 1")

        assertAll(
            { assertThat(organization.roles.size).isEqualTo(1) },
            { assertThat(organization.getRole(r1)).isNotNull() },

            { assertThat(organization.getAchieves(r1).size).isEqualTo(0) },

            { assertThat(organization.getRequires(r1).size).isEqualTo(0) }
        )
    }

    @Test
    @Disabled
    fun testSample7() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Sample7.xml")

        val exception = assertThrows<XMLStreamException> { parser.parse(organization, inputStream) }
        assertAll(
            { assertThat(exception.cause).isInstanceOf(IllegalArgumentException::class.java) },
            { assertThat(exception.message).isEqualTo(E.ENTITY_ALREADY_EXISTS["Capability", Capability::class.java.name + ":Capability 1"]) }
        )
    }

    @Test
    @Disabled
    fun testSample8() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Sample8.xml")

        val exception = assertThrows<XMLStreamException> { parser.parse(organization, inputStream) }

        assertThat(exception.message).isEqualTo(E.INCOMPLETE_XML_FILE[SpecificationGoal::class.java.simpleName, "7c57110b-d910-437a-b674-0817ce92a963"])
    }

    @Test
    @Disabled
    fun testSample9() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Sample9.xml")

        val exception = assertThrows<XMLStreamException> { parser.parse(organization, inputStream) }

        assertAll(
            { assertThat(exception.cause).isInstanceOf(IllegalArgumentException::class.java) },
            { assertThat(exception.message).isEqualTo("No enum constant") }
        )
    }

    @Test
    @Disabled
    fun testSample10() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("/Sample10.xml")

        val exception = assertThrows<XMLStreamException> { parser.parse(organization, inputStream) }

        assertThat(exception.message).isEqualTo(E.MISSING_ATTRIBUTE_IN_TAG["Attribute", "type"])
    }

    @Test
    @Disabled
    fun testSample11() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Sample11.xml")

        val exception = assertThrows<XMLStreamException> { parser.parse(organization, inputStream) }

        assertAll(
            { assertThat(exception.cause).isInstanceOf(NumberFormatException::class.java) },
            { assertThat(exception.message).isEqualTo("For input string: \"a\"") }
        )
    }

    @Test
    @Disabled
    fun testSample12() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Sample12.xml")

        val exception = assertThrows<XMLStreamException> { parser.parse(organization, inputStream) }

        assertAll(
            { assertThat(exception.cause).isInstanceOf(NumberFormatException::class.java) },
            { assertThat(exception.message).isEqualTo("For input string: \"a\"") }
        )
    }

    @Test
    @Disabled
    fun testSample13() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Sample13.xml")

        val exception = assertThrows<XMLStreamException> { parser.parse(organization, inputStream) }

        assertAll(
            { assertThat(exception.cause).isInstanceOf(NumberFormatException::class.java) },
            { assertThat(exception.message).isEqualTo("For input string: \"a\"") }
        )
    }

    @Test
    @Disabled
    fun testSample14() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Sample14.xml")

        val exception = assertThrows<XMLStreamException> { parser.parse(organization, inputStream) }

        assertThat(exception.message).isEqualTo(E.INCOMPLETE_XML_FILE[Agent::class.java.simpleName, "15cf816f-fcde-4e5e-8758-e004f48e4ee3"])
    }

    @Test
    @Disabled
    fun testSample15() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Sample15.xml")

        val exception = assertThrows<XMLStreamException> { parser.parse(organization, inputStream) }

        assertThat(exception.message).isEqualTo(E.INCOMPLETE_XML_FILE[Role::class.java.simpleName, "d3a3aa33-88c7-41a6-91a0-423e84354e7c"])
    }

    @Test
    @Disabled
    fun testSample16() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Sample16.xml")

        val exception = assertThrows<XMLStreamException> { parser.parse(organization, inputStream) }

        assertThat(exception.message).isEqualTo(E.INCOMPLETE_XML_FILE[InstanceGoal::class.java.simpleName, "5a95c6f2-ee0a-48c4-be2d-9e4ad29a25b8"])
    }

    @Test
    @Disabled
    fun testSample17() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Sample17.xml")

        val exception = assertThrows<XMLStreamException> { parser.parse(organization, inputStream) }

        assertThat(exception.message).isEqualTo(E.INCOMPLETE_XML_FILE[SpecificationGoal::class.java.simpleName, "7c57110b-d910-437a-b674-0817ce92a963"])
    }

    @Test
    @Disabled
    @Throws(XMLStreamException::class)
    fun testSample18() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Sample18.xml")

        parser.parse(organization, inputStream)

        assertThat(organization.agents.size).isEqualTo(1)
    }

    @Test
    @Disabled
    @Throws(XMLStreamException::class)
    fun testSample19() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Sample19.xml")

        parser.parse(organization, inputStream)

        assertThat(organization.pmfs.size).isEqualTo(1)
    }

    @Test
    @Disabled
    @Throws(XMLStreamException::class)
    fun testSample20() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Sample20.xml")

        parser.parse(organization, inputStream)

        assertThat(organization.roles.size).isEqualTo(1)
    }

    @Test
    @Disabled
    @Throws(XMLStreamException::class)
    fun testSample21() {
        val parser = provider.get()
        val organization = injector.getInstance(Organization::class.java)
        val inputStream = ClassLoader.getSystemResourceAsStream("Sample21.xml")

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