package model.organization.relation;

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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;

@SuppressWarnings("javadoc")
public class PossessesTest {

	private final Injector injector = Guice.createInjector(new RelationModule());
	private final PossessesFactory possessesFactory = injector.getInstance(PossessesFactory.class);
	private final AgentFactory agentFactory = injector.getInstance(AgentFactory.class);
	private final CapabilityFactory capabilityFactory = injector.getInstance(CapabilityFactory.class);
	private final IdFactory idFactory = injector.getInstance(IdFactory.class);

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testPossesses() {
		final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));
		final Possesses ps1 = possessesFactory.buildPossesses(a, c, 1d);
		final Possesses ps2 = possessesFactory.buildPossesses(a, c, 1d);

		assertThat(ps1, is(not(nullValue())));
		assertThat(ps1, is(not(sameInstance(ps2))));
	}

	@Test
	public void testPossesses1() {
		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		possessesFactory.buildPossesses(null, null, 1d);
	}

	@Test
	public void testPossesses2() {
		exception.expect(instanceOf(IllegalArgumentException.class));
		exception.expectMessage(equalTo("Parameter (agent) cannot be null"));

		new PossessesRelation(null, null, 1d);
	}

	@Test
	public void testPossesses3() {
		final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});

		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		possessesFactory.buildPossesses(a, null, 1d);
	}

	@Test
	public void testPossesses4() {
		final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});

		exception.expect(instanceOf(IllegalArgumentException.class));
		exception.expectMessage(equalTo("Parameter (capability) cannot be null"));

		new PossessesRelation(a, null, 1d);
	}

	@Test
	public void testPossesses5() {
		final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));

		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(equalTo("Score (-0.01) must be between (0.0) and (1.0)"));

		possessesFactory.buildPossesses(a, c, -0.01);
	}

	@Test
	public void testPossesses6() {
		final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));

		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(equalTo("Score (1.01) must be between (0.0) and (1.0)"));

		possessesFactory.buildPossesses(a, c, 1.01);
	}

	@Test
	public void testGetAgent() {
		final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));
		final Possesses ps = possessesFactory.buildPossesses(a, c, 1d);

		assertThat(ps.getAgent(), is(sameInstance(a)));
	}

	@Test
	public void testGetCapability() {
		final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));
		final Possesses ps = possessesFactory.buildPossesses(a, c, 1d);

		assertThat(ps.getCapability(), is(sameInstance(c)));
	}

	@Test
	public void testGetScore() {
		final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));
		final Possesses ps = possessesFactory.buildPossesses(a, c, 1d);

		assertThat(ps.getScore(), is(equalTo(1d)));
	}

	@Test
	public void testSetScore() {
		final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));
		final Possesses ps = possessesFactory.buildPossesses(a, c, 1d);
		ps.setScore(0.5);

		assertThat(ps.getScore(), is(equalTo(0.5)));
	}

	@Test
	public void testSetScore1() {
		final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));
		final Possesses ps = possessesFactory.buildPossesses(a, c, 1d);

		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(equalTo("Score (-0.01) must be between (0.0) and (1.0)"));

		ps.setScore(-0.01);
	}

	@Test
	public void testSetScore2() {
		final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));
		final Possesses ps = possessesFactory.buildPossesses(a, c, 1d);

		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(equalTo("Score (1.01) must be between (0.0) and (1.0)"));

		ps.setScore(1.01);
	}

	@Test
	public void testEquals() {
		final Agent a1 = agentFactory.buildAgent(idFactory.build(Agent.class, "a1"), new Agent.ContactInfo() {});
		final Agent a2 = agentFactory.buildAgent(idFactory.build(Agent.class, "a2"), new Agent.ContactInfo() {});
		final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));
		final Possesses ps1 = possessesFactory.buildPossesses(a1, c, 1d);
		final Possesses ps2 = possessesFactory.buildPossesses(a2, c, 1d);
		final Possesses ps3 = possessesFactory.buildPossesses(a1, c, 1d);

		assertThat(ps1, is(not(equalTo(ps2))));
		assertThat(ps1, is(equalTo(ps3)));
		assertThat(ps1, is(not(equalTo(""))));
	}

	@Test
	public void testHashCode() {
		final Agent a1 = agentFactory.buildAgent(idFactory.build(Agent.class, "a1"), new Agent.ContactInfo() {});
		final Agent a2 = agentFactory.buildAgent(idFactory.build(Agent.class, "a2"), new Agent.ContactInfo() {});
		final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));
		final Possesses ps1 = possessesFactory.buildPossesses(a1, c, 1d);
		final Possesses ps2 = possessesFactory.buildPossesses(a2, c, 1d);
		final Possesses ps3 = possessesFactory.buildPossesses(a1, c, 1d);

		assertThat(ps1.hashCode(), is(not(equalTo(ps2.hashCode()))));
		assertThat(ps1.hashCode(), is(equalTo(ps3.hashCode())));
	}

	@Test
	public void testToString() {
		final Agent a1 = agentFactory.buildAgent(idFactory.build(Agent.class, "a1"), new Agent.ContactInfo() {});
		final Agent a2 = agentFactory.buildAgent(idFactory.build(Agent.class, "a2"), new Agent.ContactInfo() {});
		final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));
		final Possesses ps1 = possessesFactory.buildPossesses(a1, c, 1d);
		final Possesses ps2 = possessesFactory.buildPossesses(a2, c, 1d);
		final Possesses ps3 = possessesFactory.buildPossesses(a1, c, 1d);

		assertThat(ps1.toString(), is(not(equalTo(ps2.toString()))));
		assertThat(ps1.toString(), is(equalTo(ps3.toString())));
	}

}
