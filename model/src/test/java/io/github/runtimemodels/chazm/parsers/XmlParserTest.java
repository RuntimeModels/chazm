package io.github.runtimemodels.chazm.parsers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import io.github.chriszhong.message.E;
import io.github.runtimemodels.chazm.Organization;
import io.github.runtimemodels.chazm.entity.Agent;
import io.github.runtimemodels.chazm.entity.Attribute;
import io.github.runtimemodels.chazm.entity.Capability;
import io.github.runtimemodels.chazm.entity.Characteristic;
import io.github.runtimemodels.chazm.entity.InstanceGoal;
import io.github.runtimemodels.chazm.entity.Pmf;
import io.github.runtimemodels.chazm.entity.Policy;
import io.github.runtimemodels.chazm.entity.Role;
import io.github.runtimemodels.chazm.entity.SpecificationGoal;
import io.github.runtimemodels.chazm.id.IdFactory;
import io.github.runtimemodels.chazm.id.UniqueId;
import io.github.runtimemodels.chazm.parsers.ParsersModule;
import io.github.runtimemodels.chazm.parsers.XmlParser;
import io.github.runtimemodels.chazm.relation.Assignment;

import java.io.InputStream;
import java.util.HashSet;
import java.util.stream.Collectors;

import javax.inject.Provider;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import com.google.inject.Guice;
import com.google.inject.Injector;

@SuppressWarnings({ "javadoc", "unchecked" })
@RunWith(JMockit.class)
public class XmlParserTest {

	private final Injector injector = Guice.createInjector(new ParsersModule());
	private final Provider<XmlParser> provider = injector.getProvider(XmlParser.class);
	private final IdFactory idf = injector.getInstance(IdFactory.class);

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testXmlParser() throws XMLStreamException {
		final XmlParser parser1 = provider.get();
		final XmlParser parser2 = provider.get();

		assertThat(parser1, is(not(nullValue())));
		assertThat(parser1, is(sameInstance(parser2)));
	}

	@Test
	public void testParseCMS() throws XMLStreamException {
		final XmlParser parser = provider.get();
		final Organization organization = injector.getInstance(Organization.class);
		final InputStream inputStream = ClassLoader.getSystemResourceAsStream("CMS.xml");

		parser.parse(organization, inputStream);

		// TODO assert that organization state inputStream correct
	}

	@Test
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

		// check all 9 entities entities
		assertThat(organization.getAgents().size(), is(equalTo(1)));
		assertThat(organization.getAgent(agent1), is(not(nullValue())));
		assertThat(organization.getAttributes().size(), is(equalTo(1)));
		assertThat(organization.getAttribute(attribute1), is(not(nullValue())));
		assertThat(organization.getCapabilities().size(), is(equalTo(1)));
		assertThat(organization.getCapability(capability1), is(not(nullValue())));
		assertThat(organization.getCharacteristics().size(), is(equalTo(1)));
		assertThat(organization.getCharacteristic(characteristic1), is(not(nullValue())));
		assertThat(organization.getInstanceGoals().size(), is(equalTo(1)));
		assertThat(organization.getInstanceGoal(instanceGoal1), is(not(nullValue())));
		assertThat(organization.getPmfs().size(), is(equalTo(1)));
		assertThat(organization.getPmf(pmf1), is(not(nullValue())));
		assertThat(organization.getPolicies().size(), is(equalTo(1)));
		assertThat(organization.getPolicy(policy1), is(not(nullValue())));
		assertThat(organization.getRoles().size(), is(equalTo(1)));
		assertThat(organization.getRole(role1), is(not(nullValue())));
		assertThat(organization.getSpecificationGoals().size(), is(equalTo(1)));
		assertThat(organization.getSpecificationGoal(specificationGoal1), is(not(nullValue())));

		// check all 8 relations
		assertThat(organization.getAchieves(role1).size(), is(equalTo(1)));
		assertThat(organization.getAchieves(role1).stream().map(SpecificationGoal::getId).collect(Collectors.toCollection(HashSet::new)),
				hasItem(specificationGoal1));
		assertThat(organization.getAssignments().size(), is(equalTo(1)));
		assertThat(organization.getAssignments().stream().map(Assignment::getAgent).map(Agent::getId).collect(Collectors.toCollection(HashSet::new)),
				hasItem(agent1));
		assertThat(organization.getAssignments().stream().map(Assignment::getRole).map(Role::getId).collect(Collectors.toCollection(HashSet::new)),
				hasItem(role1));
		assertThat(organization.getAssignments().stream().map(Assignment::getGoal).map(InstanceGoal::getId).collect(Collectors.toCollection(HashSet::new)),
				hasItem(instanceGoal1));
		assertThat(organization.getContains(role1).size(), is(equalTo(1)));
		assertThat(organization.getContains(role1).stream().map(Characteristic::getId).collect(Collectors.toCollection(HashSet::new)), hasItem(characteristic1));
		assertThat(organization.getHas(agent1).size(), is(equalTo(1)));
		assertThat(organization.getHas(agent1).stream().map(Attribute::getId).collect(Collectors.toCollection(HashSet::new)), hasItem(attribute1));
		assertThat(organization.getModerates(pmf1), is(not(nullValue())));
		assertThat(organization.getModerates(pmf1).getId(), is(equalTo(attribute1)));
		assertThat(organization.getNeeds(role1).size(), is(equalTo(1)));
		assertThat(organization.getNeeds(role1).stream().map(Attribute::getId).collect(Collectors.toCollection(HashSet::new)), hasItem(attribute1));
		assertThat(organization.getPossesses(agent1).size(), is(equalTo(1)));
		assertThat(organization.getPossesses(agent1).stream().map(Capability::getId).collect(Collectors.toCollection(HashSet::new)), hasItem(capability1));
		assertThat(organization.getRequires(role1).size(), is(equalTo(1)));
		assertThat(organization.getRequires(role1).stream().map(Capability::getId).collect(Collectors.toCollection(HashSet::new)), hasItem(capability1));
	}

	@Test
	public void testParseSample2() throws XMLStreamException {
		final XmlParser parser = provider.get();
		final Organization organization = injector.getInstance(Organization.class);
		final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample2.xml");

		parser.parse(organization, inputStream);

		final UniqueId<Capability> c1 = idf.build(Capability.class, "Capability 1");
		final UniqueId<Role> r1 = idf.build(Role.class, "Role 1");
		final UniqueId<SpecificationGoal> g1 = idf.build(SpecificationGoal.class, "Goal 1");

		assertThat(organization.getCapabilities().size(), is(equalTo(1)));
		assertThat(organization.getCapability(c1), is(not(nullValue())));

		assertThat(organization.getRoles().size(), is(equalTo(1)));
		assertThat(organization.getRole(r1), is(not(nullValue())));

		assertThat(organization.getSpecificationGoals().size(), is(equalTo(1)));
		assertThat(organization.getSpecificationGoal(g1), is(not(nullValue())));

		assertThat(organization.getAchieves(r1).size(), is(equalTo(1)));
		assertThat(organization.getAchieves(r1).stream().map(SpecificationGoal::getId).collect(Collectors.toCollection(HashSet::new)), hasItem(g1));

		assertThat(organization.getRequires(r1).size(), is(equalTo(1)));
		assertThat(organization.getRequires(r1).stream().map(Capability::getId).collect(Collectors.toCollection(HashSet::new)), hasItem(c1));
	}

	@Test
	public void testSample3() throws XMLStreamException {
		final XmlParser parser = provider.get();
		final Organization organization = injector.getInstance(Organization.class);
		final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample3.xml");

		parser.parse(organization, inputStream);

		final UniqueId<Capability> c1 = idf.build(Capability.class, "Capability 1");
		final UniqueId<SpecificationGoal> g1 = idf.build(SpecificationGoal.class, "Goal 1");
		final UniqueId<Role> r1 = idf.build(Role.class, "Role 1");

		assertThat(organization.getCapabilities().size(), is(equalTo(1)));
		assertThat(organization.getCapability(c1), is(not(nullValue())));

		assertThat(organization.getSpecificationGoals().size(), is(equalTo(1)));
		assertThat(organization.getSpecificationGoal(g1), is(not(nullValue())));

		assertThat(organization.getRoles().size(), is(equalTo(1)));
		assertThat(organization.getRole(r1), is(not(nullValue())));

		assertThat(organization.getAchieves(r1).size(), is(equalTo(1)));
		assertThat(organization.getAchieves(r1).stream().map(SpecificationGoal::getId).collect(Collectors.toCollection(HashSet::new)), hasItem(g1));

		assertThat(organization.getRequires(r1).size(), is(equalTo(1)));
		assertThat(organization.getRequires(r1).stream().map(Capability::getId).collect(Collectors.toCollection(HashSet::new)), hasItem(c1));
	}

	@Test
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

		assertThat(organization.getCapabilities().size(), is(equalTo(3)));
		assertThat(organization.getCapability(c1), is(not(nullValue())));
		assertThat(organization.getCapability(c2), is(not(nullValue())));
		assertThat(organization.getCapability(c3), is(not(nullValue())));

		assertThat(organization.getSpecificationGoals().size(), is(equalTo(3)));
		assertThat(organization.getSpecificationGoal(g1), is(not(nullValue())));
		assertThat(organization.getSpecificationGoal(g2), is(not(nullValue())));
		assertThat(organization.getSpecificationGoal(g3), is(not(nullValue())));

		assertThat(organization.getRoles().size(), is(equalTo(1)));
		assertThat(organization.getRole(r1), is(not(nullValue())));

		assertThat(organization.getAchieves(r1).size(), is(equalTo(3)));
		assertThat(organization.getAchieves(r1).stream().map(SpecificationGoal::getId).collect(Collectors.toCollection(HashSet::new)), hasItems(g1, g2, g3));

		assertThat(organization.getRequires(r1).size(), is(equalTo(3)));
		assertThat(organization.getRequires(r1).stream().map(Capability::getId).collect(Collectors.toCollection(HashSet::new)), hasItems(c1, c2, c3));
	}

	@Test
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

		assertThat(organization.getCapabilities().size(), is(equalTo(3)));
		assertThat(organization.getCapability(c1), is(not(nullValue())));
		assertThat(organization.getCapability(c2), is(not(nullValue())));
		assertThat(organization.getCapability(c3), is(not(nullValue())));

		assertThat(organization.getSpecificationGoals().size(), is(equalTo(3)));
		assertThat(organization.getSpecificationGoal(g1), is(not(nullValue())));
		assertThat(organization.getSpecificationGoal(g2), is(not(nullValue())));
		assertThat(organization.getSpecificationGoal(g3), is(not(nullValue())));

		assertThat(organization.getRoles().size(), is(equalTo(1)));
		assertThat(organization.getRole(r1), is(not(nullValue())));

		assertThat(organization.getAchieves(r1).size(), is(equalTo(3)));
		assertThat(organization.getAchieves(r1).stream().map(SpecificationGoal::getId).collect(Collectors.toCollection(HashSet::new)), hasItems(g1, g2, g3));

		assertThat(organization.getRequires(r1).size(), is(equalTo(3)));
		assertThat(organization.getRequires(r1).stream().map(Capability::getId).collect(Collectors.toCollection(HashSet::new)), hasItems(c1, c2, c3));
	}

	@Test
	public void testSample6() throws XMLStreamException {
		final XmlParser parser = provider.get();
		final Organization organization = injector.getInstance(Organization.class);
		final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample6.xml");

		parser.parse(organization, inputStream);

		final UniqueId<Role> r1 = idf.build(Role.class, "Role 1");

		assertThat(organization.getRoles().size(), is(equalTo(1)));
		assertThat(organization.getRole(r1), is(not(nullValue())));

		assertThat(organization.getAchieves(r1).size(), is(equalTo(0)));

		assertThat(organization.getRequires(r1).size(), is(equalTo(0)));
	}

	@Test
	public void testSample7() throws XMLStreamException {
		final XmlParser parser = provider.get();
		final Organization organization = injector.getInstance(Organization.class);
		final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample7.xml");

		exception.expect(XMLStreamException.class);
		exception.expectMessage(is(equalTo(new IllegalArgumentException(E.ENTITY_ALREADY_EXISTS.get("Capability", "Capability 1")).toString())));

		parser.parse(organization, inputStream);
	}

	@Test
	public void testSample8() throws XMLStreamException {
		final XmlParser parser = provider.get();
		final Organization organization = injector.getInstance(Organization.class);
		final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample8.xml");

		exception.expect(XMLStreamException.class);
		exception.expectMessage(is(equalTo(E.INCOMPLETE_XML_FILE.get(SpecificationGoal.class.getSimpleName(), "7c57110b-d910-437a-b674-0817ce92a963"))));

		parser.parse(organization, inputStream);
	}

	@Test
	public void testSample9() throws XMLStreamException {
		final XmlParser parser = provider.get();
		final Organization organization = injector.getInstance(Organization.class);
		final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample9.xml");

		exception.expect(XMLStreamException.class);
		exception.expectCause(is(instanceOf(IllegalArgumentException.class)));
		exception.expectMessage(containsString("No enum constant"));

		parser.parse(organization, inputStream);
	}

	@Test
	public void testSample10() throws XMLStreamException {
		final XmlParser parser = provider.get();
		final Organization organization = injector.getInstance(Organization.class);
		final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample10.xml");

		exception.expect(XMLStreamException.class);
		exception.expectMessage(is(equalTo(E.MISSING_ATTRIBUTE_IN_TAG.get("Attribute", "type"))));

		parser.parse(organization, inputStream);
	}

	@Test
	public void testSample11() throws XMLStreamException {
		final XmlParser parser = provider.get();
		final Organization organization = injector.getInstance(Organization.class);
		final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample11.xml");

		exception.expect(XMLStreamException.class);
		exception.expectCause(is(instanceOf(NumberFormatException.class)));
		exception.expectMessage(containsString("For input string: \"a\""));

		parser.parse(organization, inputStream);
	}

	@Test
	public void testSample12() throws XMLStreamException {
		final XmlParser parser = provider.get();
		final Organization organization = injector.getInstance(Organization.class);
		final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample12.xml");

		exception.expect(XMLStreamException.class);
		exception.expectCause(is(instanceOf(NumberFormatException.class)));
		exception.expectMessage(containsString("For input string: \"a\""));

		parser.parse(organization, inputStream);
	}

	@Test
	public void testSample13() throws XMLStreamException {
		final XmlParser parser = provider.get();
		final Organization organization = injector.getInstance(Organization.class);
		final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample13.xml");

		exception.expect(XMLStreamException.class);
		exception.expectCause(is(instanceOf(NumberFormatException.class)));
		exception.expectMessage(containsString("For input string: \"a\""));

		parser.parse(organization, inputStream);
	}

	@Test
	public void testSample14() throws XMLStreamException {
		final XmlParser parser = provider.get();
		final Organization organization = injector.getInstance(Organization.class);
		final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample14.xml");

		exception.expect(XMLStreamException.class);
		exception.expectMessage(is(equalTo(E.INCOMPLETE_XML_FILE.get(Agent.class.getSimpleName(), "15cf816f-fcde-4e5e-8758-e004f48e4ee3"))));

		parser.parse(organization, inputStream);
	}

	@Test
	public void testSample15() throws XMLStreamException {
		final XmlParser parser = provider.get();
		final Organization organization = injector.getInstance(Organization.class);
		final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample15.xml");

		exception.expect(XMLStreamException.class);
		exception.expectMessage(is(equalTo(E.INCOMPLETE_XML_FILE.get(Role.class.getSimpleName(), "d3a3aa33-88c7-41a6-91a0-423e84354e7c"))));

		parser.parse(organization, inputStream);
	}

	@Test
	public void testSample16() throws XMLStreamException {
		final XmlParser parser = provider.get();
		final Organization organization = injector.getInstance(Organization.class);
		final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample16.xml");

		exception.expect(XMLStreamException.class);
		exception.expectMessage(is(equalTo(E.INCOMPLETE_XML_FILE.get(InstanceGoal.class.getSimpleName(), "5a95c6f2-ee0a-48c4-be2d-9e4ad29a25b8"))));

		parser.parse(organization, inputStream);
	}

	@Test
	public void testSample17() throws XMLStreamException {
		final XmlParser parser = provider.get();
		final Organization organization = injector.getInstance(Organization.class);
		final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample17.xml");

		exception.expect(XMLStreamException.class);
		exception.expectMessage(is(equalTo(E.INCOMPLETE_XML_FILE.get(SpecificationGoal.class.getSimpleName(), "7c57110b-d910-437a-b674-0817ce92a963"))));

		parser.parse(organization, inputStream);
	}

	@Test
	public void testSample18() throws XMLStreamException {
		final XmlParser parser = provider.get();
		final Organization organization = injector.getInstance(Organization.class);
		final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample18.xml");

		parser.parse(organization, inputStream);

		assertThat(organization.getAgents().size(), is(equalTo(1)));
	}

	@Test
	public void testSample19() throws XMLStreamException {
		final XmlParser parser = provider.get();
		final Organization organization = injector.getInstance(Organization.class);
		final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample19.xml");

		parser.parse(organization, inputStream);

		assertThat(organization.getPmfs().size(), is(equalTo(1)));
	}

	@Test
	public void testSample20() throws XMLStreamException {
		final XmlParser parser = provider.get();
		final Organization organization = injector.getInstance(Organization.class);
		final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample20.xml");

		parser.parse(organization, inputStream);

		assertThat(organization.getRoles().size(), is(equalTo(1)));
	}

	@Test
	public void testSample21() throws XMLStreamException {
		final XmlParser parser = provider.get();
		final Organization organization = injector.getInstance(Organization.class);
		final InputStream inputStream = ClassLoader.getSystemResourceAsStream("Sample21.xml");

		parser.parse(organization, inputStream);

		assertThat(organization.getAgents().size(), is(equalTo(1)));
	}

	@Test
	public void testMock1(@Mocked final InputStream inputStream, @Mocked final XMLInputFactory factory, @Mocked final XMLEventReader reader,
			@Mocked final XMLEvent event, @Mocked final QName name) throws XMLStreamException {
		/* mock missing </RoleDiagram> end tag */
		new Expectations() {
			{
				reader.hasNext();
				returns(true, false);
				reader.nextEvent();
				result = event;
				event.isStartElement();
				result = true;
				name.getLocalPart();
				result = "RoleDiagram";
				name.toString();
				result = "RoleDiagram";
			}
		};
		final XmlParser parser = provider.get();
		final Organization organization = injector.getInstance(Organization.class);

		exception.expect(XMLStreamException.class);
		exception.expectMessage(is(equalTo(E.MISSING_END_TAG.get("RoleDiagram"))));

		parser.parse(organization, inputStream);
	}

	@Test
	public void testMock2(@Mocked final InputStream inputStream, @Mocked final XMLInputFactory factory, @Mocked final XMLEventReader reader,
			@Mocked final XMLEvent event, @Mocked final QName name, @Mocked final javax.xml.stream.events.Attribute attribute) throws XMLStreamException {
		/* mock missing </Agent> end tag */
		new Expectations() {
			{
				reader.hasNext();
				returns(true, true, false);
				reader.nextEvent();
				result = event;
				event.isStartElement();
				result = true;
				name.getLocalPart();
				returns("RoleDiagram", "Agent");
				attribute.getValue();
				result = "Agent 1";
				name.toString();
				result = "Agent";
			}
		};
		final XmlParser parser = provider.get();
		final Organization organization = injector.getInstance(Organization.class);

		exception.expect(XMLStreamException.class);
		exception.expectMessage(is(equalTo(E.MISSING_END_TAG.get("Agent"))));

		parser.parse(organization, inputStream);
	}

	@Test
	public void testMock3(@Mocked final InputStream inputStream, @Mocked final XMLInputFactory factory, @Mocked final XMLEventReader reader,
			@Mocked final XMLEvent event, @Mocked final QName name, @Mocked final javax.xml.stream.events.Attribute attribute) throws XMLStreamException {
		/* mock missing </Pmf> end tag */
		new Expectations() {
			{
				reader.hasNext();
				returns(true, true, false);
				reader.nextEvent();
				result = event;
				event.isStartElement();
				result = true;
				name.getLocalPart();
				returns("RoleDiagram", "Pmf");
				attribute.getValue();
				result = "Pmf 1";
				name.toString();
				result = "Pmf";
			}
		};
		final XmlParser parser = provider.get();
		final Organization organization = injector.getInstance(Organization.class);

		exception.expect(XMLStreamException.class);
		exception.expectMessage(is(equalTo(E.MISSING_END_TAG.get("Pmf"))));

		parser.parse(organization, inputStream);
	}

	@Test
	public void testMock4(@Mocked final InputStream inputStream, @Mocked final XMLInputFactory factory, @Mocked final XMLEventReader reader,
			@Mocked final XMLEvent event, @Mocked final QName name, @Mocked final javax.xml.stream.events.Attribute attribute) throws XMLStreamException {
		/* mock missing </Role> end tag */
		new Expectations() {
			{
				reader.hasNext();
				returns(true, true, false);
				reader.nextEvent();
				result = event;
				event.isStartElement();
				result = true;
				name.getLocalPart();
				returns("RoleDiagram", "Role");
				attribute.getValue();
				result = "Role 1";
				name.toString();
				result = "Role";
			}
		};
		final XmlParser parser = provider.get();
		final Organization organization = injector.getInstance(Organization.class);

		exception.expect(XMLStreamException.class);
		exception.expectMessage(is(equalTo(E.MISSING_END_TAG.get("Role"))));

		parser.parse(organization, inputStream);
	}

	@Test
	public void testMock5(@Mocked final InputStream inputStream, @Mocked final XMLInputFactory factory, @Mocked final XMLEventReader reader,
			@Mocked final XMLEvent event, @Mocked final QName name, @Mocked final javax.xml.stream.events.Attribute attribute) throws XMLStreamException {
		/* mock missing relations end tags: </has>, </possesses>, </moderates>, </achieves>, </contains>, </needs>, </requires> */
		new Expectations() {
			{
				reader.hasNext();
				returns(true, true, true, false);
				reader.nextEvent();
				result = event;
				event.isStartElement();
				result = true;
				name.getLocalPart();
				returns("RoleDiagram", "Agent", "has");
				attribute.getValue();
				result = "Agent 1";
				name.toString();
				result = "has";
			}
		};
		final XmlParser parser = provider.get();
		final Organization organization = injector.getInstance(Organization.class);

		exception.expect(XMLStreamException.class);
		exception.expectMessage(is(equalTo(E.MISSING_END_TAG.get("has"))));

		parser.parse(organization, inputStream);
	}

}
