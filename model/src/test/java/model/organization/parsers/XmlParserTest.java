package model.organization.parsers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.io.InputStream;
import java.util.HashSet;
import java.util.stream.Collectors;

import javax.inject.Provider;
import javax.xml.stream.XMLStreamException;

import model.organization.Organization;
import model.organization.entity.Capability;
import model.organization.entity.Role;
import model.organization.entity.SpecificationGoal;
import model.organization.id.IdFactory;
import model.organization.id.UniqueId;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.inject.Guice;
import com.google.inject.Injector;

@SuppressWarnings({ "javadoc", "unchecked" })
public class XmlParserTest {

	private final Injector injector = Guice.createInjector(new ParsersModule());
	private final Provider<XmlParser> provider = injector.getProvider(XmlParser.class);
	private final IdFactory idf = injector.getInstance(IdFactory.class);

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testXmlParser() throws XMLStreamException {
		final XmlParser p1 = provider.get();
		final XmlParser p2 = provider.get();

		assertThat(p1, is(not(nullValue())));
		assertThat(p1, is(sameInstance(p2)));
	}

	@Test
	public void testParse1() throws XMLStreamException {
		final XmlParser p1 = provider.get();
		final Organization o1 = injector.getInstance(Organization.class);
		final InputStream is = ClassLoader.getSystemResourceAsStream("RoleDiagram1.xml");

		p1.parse(o1, is);

		// TODO assert that o1 state is correct
	}

	@Test
	public void testParse2() throws XMLStreamException {
		final XmlParser p1 = provider.get();
		final Organization o1 = injector.getInstance(Organization.class);
		final InputStream is = ClassLoader.getSystemResourceAsStream("RoleDiagram2.xml");

		p1.parse(o1, is);

		final UniqueId<Capability> c1 = idf.build(Capability.class, "Capability 1");
		final UniqueId<Capability> c2 = idf.build(Capability.class, "Capability 2");
		final UniqueId<Capability> c3 = idf.build(Capability.class, "Capability 3");
		final UniqueId<SpecificationGoal> g1 = idf.build(SpecificationGoal.class, "Goal 1");
		final UniqueId<SpecificationGoal> g2 = idf.build(SpecificationGoal.class, "Goal 2");
		final UniqueId<SpecificationGoal> g3 = idf.build(SpecificationGoal.class, "Goal 3");
		final UniqueId<Role> r1 = idf.build(Role.class, "Role 1");
		final UniqueId<Role> r2 = idf.build(Role.class, "Role 2");
		final UniqueId<Role> r3 = idf.build(Role.class, "Role 3");

		assertThat(o1.getCapabilities().size(), is(equalTo(3)));
		assertThat(o1.getCapability(c1), is(not(nullValue())));
		assertThat(o1.getCapability(c2), is(not(nullValue())));
		assertThat(o1.getCapability(c3), is(not(nullValue())));

		assertThat(o1.getSpecificationGoals().size(), is(equalTo(3)));
		assertThat(o1.getSpecificationGoal(g1), is(not(nullValue())));
		assertThat(o1.getSpecificationGoal(g2), is(not(nullValue())));
		assertThat(o1.getSpecificationGoal(g3), is(not(nullValue())));

		assertThat(o1.getRoles().size(), is(equalTo(3)));
		assertThat(o1.getRole(r1), is(not(nullValue())));
		assertThat(o1.getRole(r2), is(not(nullValue())));
		assertThat(o1.getRole(r3), is(not(nullValue())));

		assertThat(o1.getAchieves(r1).size(), is(equalTo(1)));
		assertThat(o1.getAchieves(r1).stream().map(SpecificationGoal::getId).collect(Collectors.toCollection(HashSet::new)), hasItem(g1));

		assertThat(o1.getRequires(r1).size(), is(equalTo(1)));
		assertThat(o1.getRequires(r1).stream().map(Capability::getId).collect(Collectors.toCollection(HashSet::new)), hasItem(c1));

		assertThat(o1.getAchieves(r2).size(), is(equalTo(1)));
		assertThat(o1.getAchieves(r2).stream().map(SpecificationGoal::getId).collect(Collectors.toCollection(HashSet::new)), hasItem(g2));

		assertThat(o1.getRequires(r2).size(), is(equalTo(1)));
		assertThat(o1.getRequires(r2).stream().map(Capability::getId).collect(Collectors.toCollection(HashSet::new)), hasItem(c2));

		assertThat(o1.getAchieves(r3).size(), is(equalTo(1)));
		assertThat(o1.getAchieves(r3).stream().map(SpecificationGoal::getId).collect(Collectors.toCollection(HashSet::new)), hasItem(g3));

		assertThat(o1.getRequires(r3).size(), is(equalTo(1)));
		assertThat(o1.getRequires(r3).stream().map(Capability::getId).collect(Collectors.toCollection(HashSet::new)), hasItem(c3));
	}

	@Test
	public void testParse3() throws XMLStreamException {
		final XmlParser p1 = provider.get();
		final Organization o1 = injector.getInstance(Organization.class);
		final InputStream is = ClassLoader.getSystemResourceAsStream("RoleDiagram3.xml");

		p1.parse(o1, is);

		final UniqueId<Capability> c1 = idf.build(Capability.class, "Capability 1");
		final UniqueId<Capability> c2 = idf.build(Capability.class, "Capability 2");
		final UniqueId<Capability> c3 = idf.build(Capability.class, "Capability 3");
		final UniqueId<SpecificationGoal> g1 = idf.build(SpecificationGoal.class, "Goal 1");
		final UniqueId<SpecificationGoal> g2 = idf.build(SpecificationGoal.class, "Goal 2");
		final UniqueId<SpecificationGoal> g3 = idf.build(SpecificationGoal.class, "Goal 3");
		final UniqueId<Role> r1 = idf.build(Role.class, "Role 1");
		final UniqueId<Role> r2 = idf.build(Role.class, "Role 2");
		final UniqueId<Role> r3 = idf.build(Role.class, "Role 3");

		assertThat(o1.getCapabilities().size(), is(equalTo(3)));
		assertThat(o1.getCapability(c1), is(not(nullValue())));
		assertThat(o1.getCapability(c2), is(not(nullValue())));
		assertThat(o1.getCapability(c3), is(not(nullValue())));

		assertThat(o1.getSpecificationGoals().size(), is(equalTo(3)));
		assertThat(o1.getSpecificationGoal(g1), is(not(nullValue())));
		assertThat(o1.getSpecificationGoal(g2), is(not(nullValue())));
		assertThat(o1.getSpecificationGoal(g3), is(not(nullValue())));

		assertThat(o1.getRoles().size(), is(equalTo(3)));
		assertThat(o1.getRole(r1), is(not(nullValue())));
		assertThat(o1.getRole(r2), is(not(nullValue())));
		assertThat(o1.getRole(r3), is(not(nullValue())));

		assertThat(o1.getAchieves(r1).size(), is(equalTo(1)));
		assertThat(o1.getAchieves(r1).stream().map(SpecificationGoal::getId).collect(Collectors.toCollection(HashSet::new)), hasItem(g1));

		assertThat(o1.getRequires(r1).size(), is(equalTo(1)));
		assertThat(o1.getRequires(r1).stream().map(Capability::getId).collect(Collectors.toCollection(HashSet::new)), hasItem(c1));

		assertThat(o1.getAchieves(r2).size(), is(equalTo(1)));
		assertThat(o1.getAchieves(r2).stream().map(SpecificationGoal::getId).collect(Collectors.toCollection(HashSet::new)), hasItem(g2));

		assertThat(o1.getRequires(r2).size(), is(equalTo(1)));
		assertThat(o1.getRequires(r2).stream().map(Capability::getId).collect(Collectors.toCollection(HashSet::new)), hasItem(c2));

		assertThat(o1.getAchieves(r3).size(), is(equalTo(1)));
		assertThat(o1.getAchieves(r3).stream().map(SpecificationGoal::getId).collect(Collectors.toCollection(HashSet::new)), hasItem(g3));

		assertThat(o1.getRequires(r3).size(), is(equalTo(1)));
		assertThat(o1.getRequires(r3).stream().map(Capability::getId).collect(Collectors.toCollection(HashSet::new)), hasItem(c3));
	}

	@Test
	public void testParse4() throws XMLStreamException {
		final XmlParser p1 = provider.get();
		final Organization o1 = injector.getInstance(Organization.class);
		final InputStream is = ClassLoader.getSystemResourceAsStream("RoleDiagram4.xml");

		p1.parse(o1, is);

		final UniqueId<Capability> c1 = idf.build(Capability.class, "Capability 1");
		final UniqueId<SpecificationGoal> g1 = idf.build(SpecificationGoal.class, "Goal 1");
		final UniqueId<Role> r1 = idf.build(Role.class, "Role 1");

		assertThat(o1.getCapabilities().size(), is(equalTo(1)));
		assertThat(o1.getCapability(c1), is(not(nullValue())));

		assertThat(o1.getSpecificationGoals().size(), is(equalTo(1)));
		assertThat(o1.getSpecificationGoal(g1), is(not(nullValue())));

		assertThat(o1.getRoles().size(), is(equalTo(1)));
		assertThat(o1.getRole(r1), is(not(nullValue())));

		assertThat(o1.getAchieves(r1).size(), is(equalTo(1)));
		assertThat(o1.getAchieves(r1).stream().map(SpecificationGoal::getId).collect(Collectors.toCollection(HashSet::new)), hasItem(g1));

		assertThat(o1.getRequires(r1).size(), is(equalTo(1)));
		assertThat(o1.getRequires(r1).stream().map(Capability::getId).collect(Collectors.toCollection(HashSet::new)), hasItem(c1));
	}

	@Test
	public void testParse5() throws XMLStreamException {
		final XmlParser p1 = provider.get();
		final Organization o1 = injector.getInstance(Organization.class);
		final InputStream is = ClassLoader.getSystemResourceAsStream("RoleDiagram5.xml");

		p1.parse(o1, is);

		final UniqueId<Capability> c1 = idf.build(Capability.class, "Capability 1");
		final UniqueId<SpecificationGoal> g1 = idf.build(SpecificationGoal.class, "Goal 1");
		final UniqueId<Role> r1 = idf.build(Role.class, "Role 1");

		assertThat(o1.getCapabilities().size(), is(equalTo(1)));
		assertThat(o1.getCapability(c1), is(not(nullValue())));

		assertThat(o1.getSpecificationGoals().size(), is(equalTo(1)));
		assertThat(o1.getSpecificationGoal(g1), is(not(nullValue())));

		assertThat(o1.getRoles().size(), is(equalTo(1)));
		assertThat(o1.getRole(r1), is(not(nullValue())));

		assertThat(o1.getAchieves(r1).size(), is(equalTo(1)));
		assertThat(o1.getAchieves(r1).stream().map(SpecificationGoal::getId).collect(Collectors.toCollection(HashSet::new)), hasItem(g1));

		assertThat(o1.getRequires(r1).size(), is(equalTo(1)));
		assertThat(o1.getRequires(r1).stream().map(Capability::getId).collect(Collectors.toCollection(HashSet::new)), hasItem(c1));
	}

	@Test
	public void testParse6() throws XMLStreamException {
		final XmlParser p1 = provider.get();
		final Organization o1 = injector.getInstance(Organization.class);
		final InputStream is = ClassLoader.getSystemResourceAsStream("RoleDiagram6.xml");

		p1.parse(o1, is);

		final UniqueId<Capability> c1 = idf.build(Capability.class, "Capability 1");
		final UniqueId<Capability> c2 = idf.build(Capability.class, "Capability 2");
		final UniqueId<Capability> c3 = idf.build(Capability.class, "Capability 3");
		final UniqueId<SpecificationGoal> g1 = idf.build(SpecificationGoal.class, "Goal 1");
		final UniqueId<SpecificationGoal> g2 = idf.build(SpecificationGoal.class, "Goal 2");
		final UniqueId<SpecificationGoal> g3 = idf.build(SpecificationGoal.class, "Goal 3");
		final UniqueId<Role> r1 = idf.build(Role.class, "Role 1");

		assertThat(o1.getCapabilities().size(), is(equalTo(3)));
		assertThat(o1.getCapability(c1), is(not(nullValue())));
		assertThat(o1.getCapability(c2), is(not(nullValue())));
		assertThat(o1.getCapability(c3), is(not(nullValue())));

		assertThat(o1.getSpecificationGoals().size(), is(equalTo(3)));
		assertThat(o1.getSpecificationGoal(g1), is(not(nullValue())));
		assertThat(o1.getSpecificationGoal(g2), is(not(nullValue())));
		assertThat(o1.getSpecificationGoal(g3), is(not(nullValue())));

		assertThat(o1.getRoles().size(), is(equalTo(1)));
		assertThat(o1.getRole(r1), is(not(nullValue())));

		assertThat(o1.getAchieves(r1).size(), is(equalTo(3)));
		assertThat(o1.getAchieves(r1).stream().map(SpecificationGoal::getId).collect(Collectors.toCollection(HashSet::new)), hasItems(g1, g2, g3));

		assertThat(o1.getRequires(r1).size(), is(equalTo(3)));
		assertThat(o1.getRequires(r1).stream().map(Capability::getId).collect(Collectors.toCollection(HashSet::new)), hasItems(c1, c2, c3));
	}

	@Test
	public void testParse7() throws XMLStreamException {
		final XmlParser p1 = provider.get();
		final Organization o1 = injector.getInstance(Organization.class);
		final InputStream is = ClassLoader.getSystemResourceAsStream("RoleDiagram7.xml");

		p1.parse(o1, is);

		final UniqueId<Capability> c1 = idf.build(Capability.class, "Capability 1");
		final UniqueId<Capability> c2 = idf.build(Capability.class, "Capability 2");
		final UniqueId<Capability> c3 = idf.build(Capability.class, "Capability 3");
		final UniqueId<SpecificationGoal> g1 = idf.build(SpecificationGoal.class, "Goal 1");
		final UniqueId<SpecificationGoal> g2 = idf.build(SpecificationGoal.class, "Goal 2");
		final UniqueId<SpecificationGoal> g3 = idf.build(SpecificationGoal.class, "Goal 3");
		final UniqueId<Role> r1 = idf.build(Role.class, "Role 1");

		assertThat(o1.getCapabilities().size(), is(equalTo(3)));
		assertThat(o1.getCapability(c1), is(not(nullValue())));
		assertThat(o1.getCapability(c2), is(not(nullValue())));
		assertThat(o1.getCapability(c3), is(not(nullValue())));

		assertThat(o1.getSpecificationGoals().size(), is(equalTo(3)));
		assertThat(o1.getSpecificationGoal(g1), is(not(nullValue())));
		assertThat(o1.getSpecificationGoal(g2), is(not(nullValue())));
		assertThat(o1.getSpecificationGoal(g3), is(not(nullValue())));

		assertThat(o1.getRoles().size(), is(equalTo(1)));
		assertThat(o1.getRole(r1), is(not(nullValue())));

		assertThat(o1.getAchieves(r1).size(), is(equalTo(3)));
		assertThat(o1.getAchieves(r1).stream().map(SpecificationGoal::getId).collect(Collectors.toCollection(HashSet::new)), hasItems(g1, g2, g3));

		assertThat(o1.getRequires(r1).size(), is(equalTo(3)));
		assertThat(o1.getRequires(r1).stream().map(Capability::getId).collect(Collectors.toCollection(HashSet::new)), hasItems(c1, c2, c3));
	}

	@Test
	public void testParse8() throws XMLStreamException {
		final XmlParser p1 = provider.get();
		final Organization o1 = injector.getInstance(Organization.class);
		final InputStream is = ClassLoader.getSystemResourceAsStream("RoleDiagram8.xml");

		p1.parse(o1, is);

		final UniqueId<Role> r1 = idf.build(Role.class, "Role 1");

		assertThat(o1.getRoles().size(), is(equalTo(1)));
		assertThat(o1.getRole(r1), is(not(nullValue())));

		assertThat(o1.getAchieves(r1).size(), is(equalTo(0)));

		assertThat(o1.getRequires(r1).size(), is(equalTo(0)));
	}

}
