package runtimemodels.chazm.model.entity;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import runtimemodels.chazm.api.entity.Agent;
import runtimemodels.chazm.api.id.UniqueId;
import runtimemodels.chazm.model.id.IdFactory;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@SuppressWarnings("javadoc")
public class AgentEntityTest {

    private final Injector injector = Guice.createInjector(new EntityModule());
    private final AgentFactory agentFactory = injector.getInstance(AgentFactory.class);
    private final IdFactory idFactory = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testAgentFactory() {
        final UniqueId<Agent> i1 = idFactory.build(Agent.class, 1L);
        final Agent a1 = agentFactory.buildAgent(i1, new Agent.ContactInfo() {
        });
        final Agent a2 = agentFactory.buildAgent(i1, new Agent.ContactInfo() {
        });
        assertThat(a1, is(not(nullValue())));
        assertThat(a1, is(not(sameInstance(a2))));
    }

    @Test
    @Ignore
    public void testAgentFactoryWithNullIdAndNullContactInfo() {
        exception.expect(instanceOf(ProvisionException.class));
//        exception.expectMessage(allOf(
//                containsString("1st parameter of AgentEntity.<init>(AgentEntity.java:17) is not @Nullable"),
//                containsString("2nd parameter of AgentEntity.<init>(AgentEntity.java:17) is not @Nullable")
//        ));
        agentFactory.buildAgent(null, null);
    }

    @Test
    @Ignore
    public void testAgentFactoryWithNullContactInfo() {
        final UniqueId<Agent> i1 = idFactory.build(Agent.class, 1L);
        exception.expect(instanceOf(ProvisionException.class));
//        exception.expectMessage(containsString("2nd parameter of AgentEntity.<init>(AgentEntity.java:17) is not @Nullable"));
        agentFactory.buildAgent(i1, null);
    }

    @Test
    public void testGetContactInfo() {
        final UniqueId<Agent> i1 = idFactory.build(Agent.class, 1L);
        final Agent.ContactInfo contactInfo = new Agent.ContactInfo() {
        };
        final Agent a1 = agentFactory.buildAgent(i1, contactInfo);
        assertThat(a1.getContactInfo(), is(sameInstance(contactInfo)));
    }

    @Test
    public void testGetId() {
        final UniqueId<Agent> i1 = idFactory.build(Agent.class, 1L);
        final UniqueId<Agent> i2 = idFactory.build(Agent.class, 1L);
        final Agent a1 = agentFactory.buildAgent(i1, new Agent.ContactInfo() {
        });
        final Agent a2 = agentFactory.buildAgent(i2, new Agent.ContactInfo() {
        });

        assertThat(a1.getId(), is(sameInstance(i1)));
        assertThat(a1.getId(), is(not(sameInstance(a2.getId()))));
    }

    @Test
    public void testEqualsObject() {
        final UniqueId<Agent> i1 = idFactory.build(Agent.class, 1L);
        final UniqueId<Agent> i2 = idFactory.build(Agent.class, 2L);
        final Agent a1 = agentFactory.buildAgent(i1, new Agent.ContactInfo() {
        });
        final Agent a2 = agentFactory.buildAgent(i2, new Agent.ContactInfo() {
        });
        final Agent a3 = agentFactory.buildAgent(i1, new Agent.ContactInfo() {
        });

        assertThat(a1, is(not(equalTo(a2))));
        assertThat(a1, is(equalTo(a3)));
        assertThat(a1, is(not(equalTo(""))));
    }

    @Test
    public void testHashCode() {
        final UniqueId<Agent> i1 = idFactory.build(Agent.class, 1L);
        final UniqueId<Agent> i2 = idFactory.build(Agent.class, 2L);
        final Agent a1 = agentFactory.buildAgent(i1, new Agent.ContactInfo() {
        });
        final Agent a2 = agentFactory.buildAgent(i2, new Agent.ContactInfo() {
        });
        final Agent a3 = agentFactory.buildAgent(i1, new Agent.ContactInfo() {
        });

        assertThat(a1.hashCode(), is(not(equalTo(a2.hashCode()))));
        assertThat(a1.hashCode(), is(equalTo(a3.hashCode())));
    }

    @Test
    public void testToString() {
        final UniqueId<Agent> i1 = idFactory.build(Agent.class, 1L);
        final UniqueId<Agent> i2 = idFactory.build(Agent.class, 2L);
        final Agent a1 = agentFactory.buildAgent(i1, new Agent.ContactInfo() {
        });
        final Agent a2 = agentFactory.buildAgent(i2, new Agent.ContactInfo() {
        });
        final Agent a3 = agentFactory.buildAgent(i1, new Agent.ContactInfo() {
        });

        assertThat(a1.toString(), is(not(equalTo(a2.toString()))));
        assertThat(a1.toString(), is(equalTo(a3.toString())));
    }

}