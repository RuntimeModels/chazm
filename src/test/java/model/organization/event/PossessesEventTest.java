package model.organization.event;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import model.organization.entity.Agent;
import model.organization.entity.AgentFactory;
import model.organization.entity.Capability;
import model.organization.entity.CapabilityFactory;
import model.organization.id.IdFactory;
import model.organization.relation.Possesses;
import model.organization.relation.PossessesFactory;
import model.organization.relation.RelationModule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;

@SuppressWarnings("javadoc")
public class PossessesEventTest {

	private final Injector injector = Guice.createInjector(new RelationModule(), new EventModule());
	private final PossessesEventFactory pef = injector.getInstance(PossessesEventFactory.class);
	private final PossessesFactory pf = injector.getInstance(PossessesFactory.class);
	private final AgentFactory af = injector.getInstance(AgentFactory.class);
	private final CapabilityFactory cf = injector.getInstance(CapabilityFactory.class);
	private final IdFactory idf = injector.getInstance(IdFactory.class);

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testPossessesEvent() {
		final Agent a = af.buildAgent(idf.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Capability c = cf.buildCapability(idf.build(Capability.class, "c"));
		final Possesses p = pf.buildPossesses(a, c, 1d);
		final PossessesEvent pe1 = pef.build(EventCategory.ADDED, p);
		final PossessesEvent pe2 = pef.build(EventCategory.ADDED, p);

		assertThat(pe1, is(not(nullValue())));
		assertThat(pe1, is(not(sameInstance(pe2))));
	}

	@Test
	public void testPossessesEvent1() {
		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		pef.build(null, null);
	}

	@Test
	public void testPossessesEvent2() {
		exception.expect(instanceOf(IllegalArgumentException.class));
		exception.expectMessage(equalTo("Parameter (category) cannot be null"));

		new PossessesEvent(null, null);
	}

	@Test
	public void testPossessesEvent3() {
		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		pef.build(EventCategory.ADDED, null);
	}

	@Test
	public void testPossessesEvent4() {
		exception.expect(instanceOf(IllegalArgumentException.class));
		exception.expectMessage(equalTo("Parameter (possesses) cannot be null"));

		new PossessesEvent(EventCategory.ADDED, null);
	}

	@Test
	public void testGetAgentId() {
		final Agent a = af.buildAgent(idf.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Capability c = cf.buildCapability(idf.build(Capability.class, "c"));
		final Possesses p = pf.buildPossesses(a, c, 1d);
		final PossessesEvent pe = pef.build(EventCategory.ADDED, p);

		assertThat(pe.getAgentId(), is(sameInstance(a.getId())));
	}

	@Test
	public void testGetCapabilityId() {
		final Agent a = af.buildAgent(idf.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Capability c = cf.buildCapability(idf.build(Capability.class, "c"));
		final Possesses p = pf.buildPossesses(a, c, 1d);
		final PossessesEvent pe = pef.build(EventCategory.ADDED, p);

		assertThat(pe.getCapabilityId(), is(sameInstance(c.getId())));
	}

	@Test
	public void testGetScore() {
		final Agent a = af.buildAgent(idf.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Capability c = cf.buildCapability(idf.build(Capability.class, "c"));
		final Possesses p = pf.buildPossesses(a, c, 1d);
		final PossessesEvent pe = pef.build(EventCategory.ADDED, p);

		assertThat(pe.getScore(), is(equalTo(1d)));
	}

	@Test
	public void testEquals() {
		final Agent a1 = af.buildAgent(idf.build(Agent.class, "a1"), new Agent.ContactInfo() {});
		final Agent a2 = af.buildAgent(idf.build(Agent.class, "a2"), new Agent.ContactInfo() {});
		final Capability c = cf.buildCapability(idf.build(Capability.class, "c"));
		final Possesses p1 = pf.buildPossesses(a1, c, 1d);
		final Possesses p2 = pf.buildPossesses(a2, c, 1d);
		final Possesses p3 = pf.buildPossesses(a1, c, 1d);
		final PossessesEvent pe1 = pef.build(EventCategory.ADDED, p1);
		final PossessesEvent pe2 = pef.build(EventCategory.ADDED, p2);
		final PossessesEvent pe3 = pef.build(EventCategory.ADDED, p3);

		assertThat(pe1, is(not(equalTo(pe2))));
		assertThat(pe1, is(equalTo(pe3)));
		assertThat(pe1, is(not(equalTo(""))));
	}

	@Test
	public void testPossesseshCode() {
		final Agent a1 = af.buildAgent(idf.build(Agent.class, "a1"), new Agent.ContactInfo() {});
		final Agent a2 = af.buildAgent(idf.build(Agent.class, "a2"), new Agent.ContactInfo() {});
		final Capability c = cf.buildCapability(idf.build(Capability.class, "c"));
		final Possesses p1 = pf.buildPossesses(a1, c, 1d);
		final Possesses p2 = pf.buildPossesses(a2, c, 1d);
		final Possesses p3 = pf.buildPossesses(a1, c, 1d);
		final PossessesEvent pe1 = pef.build(EventCategory.ADDED, p1);
		final PossessesEvent pe2 = pef.build(EventCategory.ADDED, p2);
		final PossessesEvent pe3 = pef.build(EventCategory.ADDED, p3);

		assertThat(pe1.hashCode(), is(not(equalTo(pe2.hashCode()))));
		assertThat(pe1.hashCode(), is(equalTo(pe3.hashCode())));
	}

	@Test
	public void testToString() {
		final Agent a1 = af.buildAgent(idf.build(Agent.class, "a1"), new Agent.ContactInfo() {});
		final Agent a2 = af.buildAgent(idf.build(Agent.class, "a2"), new Agent.ContactInfo() {});
		final Capability c = cf.buildCapability(idf.build(Capability.class, "c"));
		final Possesses p1 = pf.buildPossesses(a1, c, 1d);
		final Possesses p2 = pf.buildPossesses(a2, c, 1d);
		final Possesses p3 = pf.buildPossesses(a1, c, 1d);
		final PossessesEvent pe1 = pef.build(EventCategory.ADDED, p1);
		final PossessesEvent pe2 = pef.build(EventCategory.ADDED, p2);
		final PossessesEvent pe3 = pef.build(EventCategory.ADDED, p3);

		assertThat(pe1.toString(), is(not(equalTo(pe2.toString()))));
		assertThat(pe1.toString(), is(equalTo(pe3.toString())));
	}

}
