package runtimemodels.chazm.model.parsers;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import runtimemodels.chazm.api.Organization;
import runtimemodels.chazm.api.entity.*;
import runtimemodels.chazm.api.id.UniqueId;
import runtimemodels.chazm.api.relation.Assignment;
import runtimemodels.chazm.model.id.IdFactory;
import runtimemodels.chazm.model.message.E;

import javax.inject.Provider;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class)
public class XmlParserTest {

    private final Injector injector = Guice.createInjector(new ParsersModule());
    private final Provider<XmlParser> provider = injector.getProvider(XmlParser.class);
    private final IdFactory idf = injector.getInstance(IdFactory.class);

    @Test
    public void testXmlParser() {
        final XmlParser parser1 = provider.get();
        final XmlParser parser2 = provider.get();

        assertAll(
                () -> assertThat(parser1).isNotNull(),
                () -> assertThat(parser1).isSameAs(parser2)
        );
    }

    @Test
    @Disabled
    public void testParseCMS() throws XMLStreamException {
        final XmlParser parser = provider.get();
        final Organization organization = injector.getInstance(Organization.class);
        final InputStream inputStream = ClassLoader.getSystemResourceAsStream("CMS.xml");

        parser.parse(organization, inputStream);

        // TODO assert that organization state inputStream correct
    }

    @Test
    @Disabled
    public void testParseSample1() throws XMLStreamException {
        final XmlParser parser = provider.get();
        final Organization organization = injector.getInstance(Organization.class);
        final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample1.xml");

        parser.parse(organization, inputStream);

        final UniqueId<Agent> agent1 = idf.build(Agent.class, "Agent 1");
        final UniqueId<Attribute> attribute1 = idf.build(Attribute.class, "Attribute 1");
        final UniqueId<Capability> capability1 = idf.build(Capability.class, "Capability 1");
        final UniqueId<Characteristic> characteristic1 = idf.build(Characteristic.class, "Characteristic 1");
        final UniqueId<InstanceGoal> instanceGoal1 = idf.build(InstanceGoal.class, "Instance Goal 1");
        final UniqueId<Pmf> pmf1 = idf.build(Pmf.class, "Pmf 1");
        final UniqueId<Policy> policy1 = idf.build(Policy.class, "Policy 1");
        final UniqueId<Role> role1 = idf.build(Role.class, "Role 1");
        final UniqueId<SpecificationGoal> specificationGoal1 = idf.build(SpecificationGoal.class, "Goal 1");


        assertAll(
                // check all 9 entities entities
                () -> assertThat(organization.getAgents().size()).isEqualTo(1),
                () -> assertThat(organization.getAgent(agent1)).isNotNull(),
                () -> assertThat(organization.getAttributes().size()).isEqualTo(1),
                () -> assertThat(organization.getAttribute(attribute1)).isNotNull(),
                () -> assertThat(organization.getCapabilities().size()).isEqualTo(1),
                () -> assertThat(organization.getCapability(capability1)).isNotNull(),
                () -> assertThat(organization.getCharacteristics().size()).isEqualTo(1),
                () -> assertThat(organization.getCharacteristic(characteristic1)).isNotNull(),
                () -> assertThat(organization.getInstanceGoals().size()).isEqualTo(1),
                () -> assertThat(organization.getInstanceGoal(instanceGoal1)).isNotNull(),
                () -> assertThat(organization.getPmfs().size()).isEqualTo(1),
                () -> assertThat(organization.getPmf(pmf1)).isNotNull(),
                () -> assertThat(organization.getPolicies().size()).isEqualTo(1),
                () -> assertThat(organization.getPolicy(policy1)).isNotNull(),
                () -> assertThat(organization.getRoles().size()).isEqualTo(1),
                () -> assertThat(organization.getRole(role1)).isNotNull(),
                () -> assertThat(organization.getSpecificationGoals().size()).isEqualTo(1),
                () -> assertThat(organization.getSpecificationGoal(specificationGoal1)).isNotNull(),

                // check all 8 relations
                () -> assertThat(organization.getAchieves(role1).size()).isEqualTo(1),
                () -> assertThat(organization.getAchieves(role1).stream().map(SpecificationGoal::getId).collect(Collectors.toSet())).containsExactly(specificationGoal1),
                () -> assertThat(organization.getAssignments().size()).isEqualTo(1),
                () -> assertThat(organization.getAssignments().stream().map(Assignment::getAgent).map(Agent::getId).collect(Collectors.toSet())).containsExactly(agent1),
                () -> assertThat(organization.getAssignments().stream().map(Assignment::getRole).map(Role::getId).collect(Collectors.toSet())).containsExactly(role1),
                () -> assertThat(organization.getAssignments().stream().map(Assignment::getGoal).map(InstanceGoal::getId).collect(Collectors.toSet())).containsExactly(instanceGoal1),
                () -> assertThat(organization.getContains(role1).size()).isEqualTo(1),
                () -> assertThat(organization.getContains(role1).stream().map(Characteristic::getId).collect(Collectors.toSet())).containsExactly(characteristic1),
                () -> assertThat(organization.getHas(agent1).size()).isEqualTo(1),
                () -> assertThat(organization.getHas(agent1).stream().map(Attribute::getId).collect(Collectors.toSet())).containsExactly(attribute1),
                () -> assertThat(organization.getModerates(pmf1)).isNotNull(),
                () -> assertThat(organization.getModerates(pmf1).getId()).isEqualTo(attribute1),
                () -> assertThat(organization.getNeeds(role1).size()).isEqualTo(1),
                () -> assertThat(organization.getNeeds(role1).stream().map(Attribute::getId).collect(Collectors.toSet())).containsExactly(attribute1),
                () -> assertThat(organization.getPossesses(agent1).size()).isEqualTo(1),
                () -> assertThat(organization.getPossesses(agent1).stream().map(Capability::getId).collect(Collectors.toSet())).containsExactly(capability1),
                () -> assertThat(organization.getRequires(role1).size()).isEqualTo(1),
                () -> assertThat(organization.getRequires(role1).stream().map(Capability::getId).collect(Collectors.toSet())).containsExactly(capability1)
        );
    }

    @Test
    @Disabled
    public void testParseSample2() throws XMLStreamException {
        final XmlParser parser = provider.get();
        final Organization organization = injector.getInstance(Organization.class);
        final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample2.xml");

        parser.parse(organization, inputStream);

        final UniqueId<Capability> c1 = idf.build(Capability.class, "Capability 1");
        final UniqueId<Role> r1 = idf.build(Role.class, "Role 1");
        final UniqueId<SpecificationGoal> g1 = idf.build(SpecificationGoal.class, "Goal 1");

        assertAll(
                () -> assertThat(organization.getCapabilities().size()).isEqualTo(1),
                () -> assertThat(organization.getCapability(c1)).isNotNull(),

                () -> assertThat(organization.getRoles().size()).isEqualTo(1),
                () -> assertThat(organization.getRole(r1)).isNotNull(),

                () -> assertThat(organization.getSpecificationGoals().size()).isEqualTo(1),
                () -> assertThat(organization.getSpecificationGoal(g1)).isNotNull(),

                () -> assertThat(organization.getAchieves(r1).size()).isEqualTo(1),
                () -> assertThat(organization.getAchieves(r1).stream().map(SpecificationGoal::getId).collect(Collectors.toSet())).containsExactly(g1),

                () -> assertThat(organization.getRequires(r1).size()).isEqualTo(1),
                () -> assertThat(organization.getRequires(r1).stream().map(Capability::getId).collect(Collectors.toSet())).containsExactly(c1)
        );
    }

    @Test
    @Disabled
    public void testSample3() throws XMLStreamException {
        final XmlParser parser = provider.get();
        final Organization organization = injector.getInstance(Organization.class);
        final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample3.xml");

        parser.parse(organization, inputStream);

        final UniqueId<Capability> c1 = idf.build(Capability.class, "Capability 1");
        final UniqueId<SpecificationGoal> g1 = idf.build(SpecificationGoal.class, "Goal 1");
        final UniqueId<Role> r1 = idf.build(Role.class, "Role 1");

        assertAll(
                () -> assertThat(organization.getCapabilities().size()).isEqualTo(1),
                () -> assertThat(organization.getCapability(c1)).isNotNull(),

                () -> assertThat(organization.getSpecificationGoals().size()).isEqualTo(1),
                () -> assertThat(organization.getSpecificationGoal(g1)).isNotNull(),

                () -> assertThat(organization.getRoles().size()).isEqualTo(1),
                () -> assertThat(organization.getRole(r1)).isNotNull(),

                () -> assertThat(organization.getAchieves(r1).size()).isEqualTo(1),
                () -> assertThat(organization.getAchieves(r1).stream().map(SpecificationGoal::getId).collect(Collectors.toSet())).containsExactly(g1),

                () -> assertThat(organization.getRequires(r1).size()).isEqualTo(1),
                () -> assertThat(organization.getRequires(r1).stream().map(Capability::getId).collect(Collectors.toSet())).containsExactly(c1)
        );
    }

    @Test
    @Disabled
    public void testSample4() throws XMLStreamException {
        final XmlParser parser = provider.get();
        final Organization organization = injector.getInstance(Organization.class);
        final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample4.xml");

        parser.parse(organization, inputStream);

        final UniqueId<Capability> c1 = idf.build(Capability.class, "Capability 1");
        final UniqueId<Capability> c2 = idf.build(Capability.class, "Capability 2");
        final UniqueId<Capability> c3 = idf.build(Capability.class, "Capability 3");
        final UniqueId<SpecificationGoal> g1 = idf.build(SpecificationGoal.class, "Goal 1");
        final UniqueId<SpecificationGoal> g2 = idf.build(SpecificationGoal.class, "Goal 2");
        final UniqueId<SpecificationGoal> g3 = idf.build(SpecificationGoal.class, "Goal 3");
        final UniqueId<Role> r1 = idf.build(Role.class, "Role 1");

        assertAll(
                () -> assertThat(organization.getCapabilities().size()).isEqualTo(3),
                () -> assertThat(organization.getCapability(c1)).isNotNull(),
                () -> assertThat(organization.getCapability(c2)).isNotNull(),
                () -> assertThat(organization.getCapability(c3)).isNotNull(),

                () -> assertThat(organization.getSpecificationGoals().size()).isEqualTo(3),
                () -> assertThat(organization.getSpecificationGoal(g1)).isNotNull(),
                () -> assertThat(organization.getSpecificationGoal(g2)).isNotNull(),
                () -> assertThat(organization.getSpecificationGoal(g3)).isNotNull(),

                () -> assertThat(organization.getRoles().size()).isEqualTo(1),
                () -> assertThat(organization.getRole(r1)).isNotNull(),

                () -> assertThat(organization.getAchieves(r1).size()).isEqualTo(3),
                () -> assertThat(organization.getAchieves(r1).stream().map(SpecificationGoal::getId).collect(Collectors.toSet())).containsExactly(g1, g2, g3),

                () -> assertThat(organization.getRequires(r1).size()).isEqualTo(3),
                () -> assertThat(organization.getRequires(r1).stream().map(Capability::getId).collect(Collectors.toSet())).containsExactly(c1, c2, c3)
        );
    }

    @Test
    @Disabled
    public void testSample5() throws XMLStreamException {
        final XmlParser parser = provider.get();
        final Organization organization = injector.getInstance(Organization.class);
        final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample5.xml");

        parser.parse(organization, inputStream);

        final UniqueId<Capability> c1 = idf.build(Capability.class, "Capability 1");
        final UniqueId<Capability> c2 = idf.build(Capability.class, "Capability 2");
        final UniqueId<Capability> c3 = idf.build(Capability.class, "Capability 3");
        final UniqueId<SpecificationGoal> g1 = idf.build(SpecificationGoal.class, "Goal 1");
        final UniqueId<SpecificationGoal> g2 = idf.build(SpecificationGoal.class, "Goal 2");
        final UniqueId<SpecificationGoal> g3 = idf.build(SpecificationGoal.class, "Goal 3");
        final UniqueId<Role> r1 = idf.build(Role.class, "Role 1");

        assertAll(
                () -> assertThat(organization.getCapabilities().size()).isEqualTo(3),
                () -> assertThat(organization.getCapability(c1)).isNotNull(),
                () -> assertThat(organization.getCapability(c2)).isNotNull(),
                () -> assertThat(organization.getCapability(c3)).isNotNull(),

                () -> assertThat(organization.getSpecificationGoals().size()).isEqualTo(3),
                () -> assertThat(organization.getSpecificationGoal(g1)).isNotNull(),
                () -> assertThat(organization.getSpecificationGoal(g2)).isNotNull(),
                () -> assertThat(organization.getSpecificationGoal(g3)).isNotNull(),

                () -> assertThat(organization.getRoles().size()).isEqualTo(1),
                () -> assertThat(organization.getRole(r1)).isNotNull(),

                () -> assertThat(organization.getAchieves(r1).size()).isEqualTo(3),
                () -> assertThat(organization.getAchieves(r1).stream().map(SpecificationGoal::getId).collect(Collectors.toSet())).containsExactly(g1, g2, g3),

                () -> assertThat(organization.getRequires(r1).size()).isEqualTo(3),
                () -> assertThat(organization.getRequires(r1).stream().map(Capability::getId).collect(Collectors.toSet())).containsExactly(c1, c2, c3)
        );
    }

    @Test
    @Disabled
    public void testSample6() throws XMLStreamException {
        final XmlParser parser = provider.get();
        final Organization organization = injector.getInstance(Organization.class);
        final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample6.xml");

        parser.parse(organization, inputStream);

        final UniqueId<Role> r1 = idf.build(Role.class, "Role 1");

        assertAll(
                () -> assertThat(organization.getRoles().size()).isEqualTo(1),
                () -> assertThat(organization.getRole(r1)).isNotNull(),

                () -> assertThat(organization.getAchieves(r1).size()).isEqualTo(0),

                () -> assertThat(organization.getRequires(r1).size()).isEqualTo(0)
        );
    }

    @Test
    @Disabled
    public void testSample7() {
        final XmlParser parser = provider.get();
        final Organization organization = injector.getInstance(Organization.class);
        final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample7.xml");

        final XMLStreamException exception = assertThrows(XMLStreamException.class, () -> parser.parse(organization, inputStream));
        assertAll(
                () -> assertThat(exception.getCause()).isInstanceOf(IllegalArgumentException.class),
                () -> assertThat(exception.getMessage()).isEqualTo(E.ENTITY_ALREADY_EXISTS.get("Capability", Capability.class.getName() + ":Capability 1"))
        );
    }

    @Test
    @Disabled
    public void testSample8() {
        final XmlParser parser = provider.get();
        final Organization organization = injector.getInstance(Organization.class);
        final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample8.xml");

        final XMLStreamException exception = assertThrows(XMLStreamException.class, () -> parser.parse(organization, inputStream));

        assertThat(exception.getMessage()).isEqualTo(E.INCOMPLETE_XML_FILE.get(SpecificationGoal.class.getSimpleName(), "7c57110b-d910-437a-b674-0817ce92a963"));
    }

    @Test
    @Disabled
    public void testSample9() {
        final XmlParser parser = provider.get();
        final Organization organization = injector.getInstance(Organization.class);
        final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample9.xml");

        final XMLStreamException exception = assertThrows(XMLStreamException.class, () -> parser.parse(organization, inputStream));

        assertAll(
                () -> assertThat(exception.getCause()).isInstanceOf(IllegalArgumentException.class),
                () -> assertThat(exception.getMessage()).isEqualTo("No enum constant")
        );
    }

    @Test
    @Disabled
    public void testSample10() {
        final XmlParser parser = provider.get();
        final Organization organization = injector.getInstance(Organization.class);
        final InputStream inputStream = ClassLoader.getSystemResourceAsStream("/Sample10.xml");

        final XMLStreamException exception = assertThrows(XMLStreamException.class, () -> parser.parse(organization, inputStream));

        assertThat(exception.getMessage()).isEqualTo(E.MISSING_ATTRIBUTE_IN_TAG.get("Attribute", "type"));
    }

    @Test
    @Disabled
    public void testSample11() {
        final XmlParser parser = provider.get();
        final Organization organization = injector.getInstance(Organization.class);
        final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample11.xml");

        final XMLStreamException exception = assertThrows(XMLStreamException.class, () -> parser.parse(organization, inputStream));

        assertAll(
                () -> assertThat(exception.getCause()).isInstanceOf(NumberFormatException.class),
                () -> assertThat(exception.getMessage()).isEqualTo("For input string: \"a\"")
        );
    }

    @Test
    @Disabled
    public void testSample12() {
        final XmlParser parser = provider.get();
        final Organization organization = injector.getInstance(Organization.class);
        final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample12.xml");

        final XMLStreamException exception = assertThrows(XMLStreamException.class, () -> parser.parse(organization, inputStream));

        assertAll(
                () -> assertThat(exception.getCause()).isInstanceOf(NumberFormatException.class),
                () -> assertThat(exception.getMessage()).isEqualTo("For input string: \"a\"")
        );
    }

    @Test
    @Disabled
    public void testSample13() {
        final XmlParser parser = provider.get();
        final Organization organization = injector.getInstance(Organization.class);
        final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample13.xml");

        final XMLStreamException exception = assertThrows(XMLStreamException.class, () -> parser.parse(organization, inputStream));

        assertAll(
                () -> assertThat(exception.getCause()).isInstanceOf(NumberFormatException.class),
                () -> assertThat(exception.getMessage()).isEqualTo("For input string: \"a\"")
        );
    }

    @Test
    @Disabled
    public void testSample14() {
        final XmlParser parser = provider.get();
        final Organization organization = injector.getInstance(Organization.class);
        final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample14.xml");

        final XMLStreamException exception = assertThrows(XMLStreamException.class, () -> parser.parse(organization, inputStream));

        assertThat(exception.getMessage()).isEqualTo(E.INCOMPLETE_XML_FILE.get(Agent.class.getSimpleName(), "15cf816f-fcde-4e5e-8758-e004f48e4ee3"));
    }

    @Test
    @Disabled
    public void testSample15() {
        final XmlParser parser = provider.get();
        final Organization organization = injector.getInstance(Organization.class);
        final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample15.xml");

        final XMLStreamException exception = assertThrows(XMLStreamException.class, () -> parser.parse(organization, inputStream));

        assertThat(exception.getMessage()).isEqualTo(E.INCOMPLETE_XML_FILE.get(Role.class.getSimpleName(), "d3a3aa33-88c7-41a6-91a0-423e84354e7c"));
    }

    @Test
    @Disabled
    public void testSample16() {
        final XmlParser parser = provider.get();
        final Organization organization = injector.getInstance(Organization.class);
        final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample16.xml");

        final XMLStreamException exception = assertThrows(XMLStreamException.class, () -> parser.parse(organization, inputStream));

        assertThat(exception.getMessage()).isEqualTo(E.INCOMPLETE_XML_FILE.get(InstanceGoal.class.getSimpleName(), "5a95c6f2-ee0a-48c4-be2d-9e4ad29a25b8"));
    }

    @Test
    @Disabled
    public void testSample17() {
        final XmlParser parser = provider.get();
        final Organization organization = injector.getInstance(Organization.class);
        final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample17.xml");

        final XMLStreamException exception = assertThrows(XMLStreamException.class, () -> parser.parse(organization, inputStream));

        assertThat(exception.getMessage()).isEqualTo(E.INCOMPLETE_XML_FILE.get(SpecificationGoal.class.getSimpleName(), "7c57110b-d910-437a-b674-0817ce92a963"));
    }

    @Test
    @Disabled
    public void testSample18() throws XMLStreamException {
        final XmlParser parser = provider.get();
        final Organization organization = injector.getInstance(Organization.class);
        final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample18.xml");

        parser.parse(organization, inputStream);

        assertThat(organization.getAgents().size()).isEqualTo(1);
    }

    @Test
    @Disabled
    public void testSample19() throws XMLStreamException {
        final XmlParser parser = provider.get();
        final Organization organization = injector.getInstance(Organization.class);
        final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample19.xml");

        parser.parse(organization, inputStream);

        assertThat(organization.getPmfs().size()).isEqualTo(1);
    }

    @Test
    @Disabled
    public void testSample20() throws XMLStreamException {
        final XmlParser parser = provider.get();
        final Organization organization = injector.getInstance(Organization.class);
        final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample20.xml");

        parser.parse(organization, inputStream);

        assertThat(organization.getRoles().size()).isEqualTo(1);
    }

    @Test
    @Disabled
    public void testSample21() throws XMLStreamException {
        final XmlParser parser = provider.get();
        final Organization organization = injector.getInstance(Organization.class);
        final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample21.xml");

        parser.parse(organization, inputStream);

        assertThat(organization.getAgents().size()).isEqualTo(1);
    }

    @Test
    @Disabled
    public void testMock1() throws XMLStreamException {
        final InputStream inputStream = mock(InputStream.class);
        final XMLInputFactory factory = mock(XMLInputFactory.class);
        final XMLEventReader reader = mock(XMLEventReader.class);
        final XMLEvent event = mock(XMLEvent.class);
        final QName name = mock(QName.class);
        final XmlParser parser = provider.get();
        final Organization organization = injector.getInstance(Organization.class);
        /* test missing </RoleDiagram> end tag */
        when(reader.hasNext()).thenReturn(true, false);
        when(reader.nextEvent()).thenReturn(event);
        when(event.isStartElement()).thenReturn(true);
        when(name.getLocalPart()).thenReturn("RoleDiagram");
        when(name.toString()).thenReturn("RoleDiagram");

        final XMLStreamException exception = assertThrows(XMLStreamException.class, () -> parser.parse(organization, inputStream));

        assertThat(exception.getMessage()).isEqualTo(E.MISSING_END_TAG.get("RoleDiagram"));
    }

    @Test
    @Disabled
    public void testMock2() throws XMLStreamException {
        final InputStream inputStream = mock(InputStream.class);
        final XMLInputFactory factory = mock(XMLInputFactory.class);
        final XMLEventReader reader = mock(XMLEventReader.class);
        final XMLEvent event = mock(XMLEvent.class);
        final QName name = mock(QName.class);
        final javax.xml.stream.events.Attribute attribute = mock(javax.xml.stream.events.Attribute.class);
        final XmlParser parser = provider.get();
        final Organization organization = injector.getInstance(Organization.class);
        /* test missing </Agent> end tag */
        when(reader.hasNext()).thenReturn(true, true, false);
        when(reader.nextEvent()).thenReturn(event);
        when(event.isStartElement()).thenReturn(true);
        when(name.getLocalPart()).thenReturn("RoleDiagram", "Agent");
        when(name.toString()).thenReturn("Agent");
        when(attribute.getValue()).thenReturn("Agent 1");

        final XMLStreamException exception = assertThrows(XMLStreamException.class, () -> parser.parse(organization, inputStream));

        assertThat(exception.getMessage()).isEqualTo(E.MISSING_END_TAG.get("Agent"));
    }

    @Test
    @Disabled
    public void testMock3() throws XMLStreamException {
        final InputStream inputStream = mock(InputStream.class);
        final XMLInputFactory factory = mock(XMLInputFactory.class);
        final XMLEventReader reader = mock(XMLEventReader.class);
        final XMLEvent event = mock(XMLEvent.class);
        final QName name = mock(QName.class);
        final javax.xml.stream.events.Attribute attribute = mock(javax.xml.stream.events.Attribute.class);
        final XmlParser parser = provider.get();
        final Organization organization = injector.getInstance(Organization.class);
        /* test missing </Pmf> end tag */
        when(reader.hasNext()).thenReturn(true, true, false);
        when(reader.nextEvent()).thenReturn(event);
        when(event.isStartElement()).thenReturn(true);
        when(name.getLocalPart()).thenReturn("RoleDiagram", "Pmf");
        when(name.toString()).thenReturn("Pmf");
        when(attribute.getValue()).thenReturn("Pmf 1");

        final XMLStreamException exception = assertThrows(XMLStreamException.class, () -> parser.parse(organization, inputStream));

        assertThat(exception.getMessage()).isEqualTo(E.MISSING_END_TAG.get("Pmf"));
    }

    @Test
    @Disabled
    public void testMock4() throws XMLStreamException {
        final InputStream inputStream = mock(InputStream.class);
        final XMLInputFactory factory = mock(XMLInputFactory.class);
        final XMLEventReader reader = mock(XMLEventReader.class);
        final XMLEvent event = mock(XMLEvent.class);
        final QName name = mock(QName.class);
        final javax.xml.stream.events.Attribute attribute = mock(javax.xml.stream.events.Attribute.class);
        final XmlParser parser = provider.get();
        final Organization organization = injector.getInstance(Organization.class);
        /* test missing </Role> end tag */
        when(reader.hasNext()).thenReturn(true, true, false);
        when(reader.nextEvent()).thenReturn(event);
        when(event.isStartElement()).thenReturn(true);
        when(name.getLocalPart()).thenReturn("RoleDiagram", "Role");
        when(name.toString()).thenReturn("Role");
        when(attribute.getValue()).thenReturn("Role 1");

        final XMLStreamException exception = assertThrows(XMLStreamException.class, () -> parser.parse(organization, inputStream));

        assertThat(exception.getMessage()).isEqualTo(E.MISSING_END_TAG.get("Role"));
    }

    @Test
    @Disabled
    public void testMock5() throws XMLStreamException {
        final InputStream inputStream = mock(InputStream.class);
        final XMLInputFactory factory = mock(XMLInputFactory.class);
        final XMLEventReader reader = mock(XMLEventReader.class);
        final XMLEvent event = mock(XMLEvent.class);
        final QName name = mock(QName.class);
        final javax.xml.stream.events.Attribute attribute = mock(javax.xml.stream.events.Attribute.class);
        final XmlParser parser = provider.get();
        final Organization organization = injector.getInstance(Organization.class);
        /* test missing relations end tags: </has>, </possesses>, </moderates>, </achieves>, </contains>, </needs>, </requires> */
        when(reader.hasNext()).thenReturn(true, true, true, false);
        when(reader.nextEvent()).thenReturn(event);
        when(event.isStartElement()).thenReturn(true);
        when(name.getLocalPart()).thenReturn("RoleDiagram", "Agent", "has");
        when(name.toString()).thenReturn("has");
        when(attribute.getValue()).thenReturn("Agent 1");

        final XMLStreamException exception = assertThrows(XMLStreamException.class, () -> parser.parse(organization, inputStream));

        assertThat(exception.getMessage()).isEqualTo(E.MISSING_END_TAG.get("has"));
    }

}
