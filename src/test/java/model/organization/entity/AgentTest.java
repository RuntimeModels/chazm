package model.organization.entity;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import model.organization.id.IdFactory;
import model.organization.id.IdModule;
import model.organization.id.UniqueId;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;

@SuppressWarnings("javadoc")
public class AgentTest {

	private final Injector injector = Guice.createInjector(new EntityModule(), new IdModule());
	private final AgentFactory agentFactory = injector.getInstance(AgentFactory.class);
	private final IdFactory idFactory = injector.getInstance(IdFactory.class);

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testAgent() {
		final UniqueId<Agent> i1 = idFactory.build(Agent.class, 1L);
		final Agent a1 = agentFactory.buildAgent(i1, new Agent.ContactInfo() {});
		assertThat(a1, is(not(nullValue())));
	}

	@Test
	public void testAgent1() {
		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));
		agentFactory.buildAgent(null, new Agent.ContactInfo() {});
	}

	@Test
	public void testAgent2() {
		exception.expect(instanceOf(IllegalArgumentException.class));
		exception.expectMessage(equalTo("Parameter (id) cannot be null"));
		new AgentEntity(null, new Agent.ContactInfo() {});
	}

	@Test
	public void testAgent3() {
		final UniqueId<Agent> i1 = idFactory.build(Agent.class, 1L);
		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));
		agentFactory.buildAgent(i1, null);
	}

	@Test
	public void testAgent4() {
		final UniqueId<Agent> i1 = idFactory.build(Agent.class, 1L);
		exception.expect(instanceOf(IllegalArgumentException.class));
		exception.expectMessage(equalTo("Parameter (contactInfo) cannot be null"));
		new AgentEntity(i1, null);
	}

	@Test
	public void testGetContactInfo() {
		final UniqueId<Agent> i1 = idFactory.build(Agent.class, 1L);
		final Agent.ContactInfo contactInfo = new Agent.ContactInfo() {};
		final Agent a1 = agentFactory.buildAgent(i1, contactInfo);
		assertThat(a1.getContactInfo(), is(sameInstance(contactInfo)));
	}

	@Test
	public void testGetId() {
		final UniqueId<Agent> i1 = idFactory.build(Agent.class, 1L);
		final UniqueId<Agent> i2 = idFactory.build(Agent.class, 1L);
		final Agent a1 = agentFactory.buildAgent(i1, new Agent.ContactInfo() {});
		final Agent a2 = agentFactory.buildAgent(i2, new Agent.ContactInfo() {});

		assertThat(a1.getId(), is(sameInstance(i1)));
		assertThat(a1.getId(), is(not(sameInstance(a2.getId()))));
	}

	@Test
	public void testEqualsObject() {
		final UniqueId<Agent> i1 = idFactory.build(Agent.class, 1L);
		final UniqueId<Agent> i2 = idFactory.build(Agent.class, 2L);
		final Agent a1 = agentFactory.buildAgent(i1, new Agent.ContactInfo() {});
		final Agent a2 = agentFactory.buildAgent(i2, new Agent.ContactInfo() {});
		final Agent a3 = agentFactory.buildAgent(i1, new Agent.ContactInfo() {});

		assertThat(a1, is(not(equalTo(i1))));
		assertThat(a2, is(not(equalTo(i2))));
		assertThat(a1, is(not(equalTo(a2))));
		assertThat(a1, is(not(sameInstance(a3))));
		assertThat(a1, is(equalTo(a3)));
		assertThat(a1, is(not(equalTo(""))));
	}

	@Test
	public void testHashCode() {
		final UniqueId<Agent> i1 = idFactory.build(Agent.class, 1L);
		final UniqueId<Agent> i2 = idFactory.build(Agent.class, 2L);
		final Agent a1 = agentFactory.buildAgent(i1, new Agent.ContactInfo() {});
		final Agent a2 = agentFactory.buildAgent(i2, new Agent.ContactInfo() {});

		assertThat(a1.hashCode(), is(equalTo(i1.hashCode())));
		assertThat(a2.hashCode(), is(equalTo(i2.hashCode())));
		assertThat(a1.hashCode(), is(not(equalTo(a2.hashCode()))));
	}

	@Test
	public void testToString() {
		final UniqueId<Agent> i1 = idFactory.build(Agent.class, 1L);
		final UniqueId<Agent> i2 = idFactory.build(Agent.class, 2L);
		final Agent a1 = agentFactory.buildAgent(i1, new Agent.ContactInfo() {});
		final Agent a2 = agentFactory.buildAgent(i2, new Agent.ContactInfo() {});

		assertThat(a1.toString(), is(equalTo(i1.toString())));
		assertThat(a2.toString(), is(equalTo(i2.toString())));
		assertThat(a1.toString(), is(not(equalTo(a2.toString()))));
	}

}
