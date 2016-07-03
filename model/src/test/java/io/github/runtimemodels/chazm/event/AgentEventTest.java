package io.github.runtimemodels.chazm.event;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import io.github.runtimemodels.chazm.entity.Agent;
import io.github.runtimemodels.chazm.entity.AgentFactory;
import io.github.runtimemodels.chazm.id.IdFactory;
import io.github.runtimemodels.chazm.relation.RelationModule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.*;

@SuppressWarnings("javadoc")
public class AgentEventTest {

    private final Injector injector = Guice.createInjector(new RelationModule(), new EventModule());
    private final AgentEventFactory aef = injector.getInstance(AgentEventFactory.class);
    private final AgentFactory af = injector.getInstance(AgentFactory.class);
    private final IdFactory idf = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testAgentEvent() {
        final Agent a = af.buildAgent(idf.build(Agent.class, "a"), new Agent.ContactInfo() {});
        final AgentEvent ae1 = aef.build(EventCategory.ADDED, a);
        final AgentEvent ae2 = aef.build(EventCategory.ADDED, a);

        assertThat(ae1, is(not(nullValue())));
        assertThat(ae1, is(not(sameInstance(ae2))));
    }

    @Test
    public void testAgentEvent1() {
        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

        aef.build(null, null);
    }

    @Test
    public void testAgentEvent2() {
        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

        aef.build(EventCategory.ADDED, null);
    }

    @Test
    public void testGetId() {
        final Agent a = af.buildAgent(idf.build(Agent.class, "a"), new Agent.ContactInfo() {});
        final AgentEvent ae = aef.build(EventCategory.ADDED, a);

        assertThat(ae.getId(), is(sameInstance(a.getId())));
    }

    @Test
    public void testEquals() {
        final Agent a1 = af.buildAgent(idf.build(Agent.class, "a1"), new Agent.ContactInfo() {});
        final Agent a2 = af.buildAgent(idf.build(Agent.class, "a2"), new Agent.ContactInfo() {});
        final Agent a3 = af.buildAgent(idf.build(Agent.class, "a1"), new Agent.ContactInfo() {});
        final AgentEvent ae1 = aef.build(EventCategory.ADDED, a1);
        final AgentEvent ae2 = aef.build(EventCategory.ADDED, a2);
        final AgentEvent ae3 = aef.build(EventCategory.ADDED, a3);

        assertThat(ae1, is(not(equalTo(ae2))));
        assertThat(ae1, is(equalTo(ae3)));
        assertThat(ae1, is(not(equalTo(""))));
    }

    @Test
    public void testHashCode() {
        final Agent a1 = af.buildAgent(idf.build(Agent.class, "a1"), new Agent.ContactInfo() {});
        final Agent a2 = af.buildAgent(idf.build(Agent.class, "a2"), new Agent.ContactInfo() {});
        final Agent a3 = af.buildAgent(idf.build(Agent.class, "a1"), new Agent.ContactInfo() {});
        final AgentEvent ae1 = aef.build(EventCategory.ADDED, a1);
        final AgentEvent ae2 = aef.build(EventCategory.ADDED, a2);
        final AgentEvent ae3 = aef.build(EventCategory.ADDED, a3);

        assertThat(ae1.hashCode(), is(not(equalTo(ae2.hashCode()))));
        assertThat(ae1.hashCode(), is(equalTo(ae3.hashCode())));
    }

    @Test
    public void testToString() {
        final Agent a1 = af.buildAgent(idf.build(Agent.class, "a1"), new Agent.ContactInfo() {});
        final Agent a2 = af.buildAgent(idf.build(Agent.class, "a2"), new Agent.ContactInfo() {});
        final Agent a3 = af.buildAgent(idf.build(Agent.class, "a1"), new Agent.ContactInfo() {});
        final AgentEvent ae1 = aef.build(EventCategory.ADDED, a1);
        final AgentEvent ae2 = aef.build(EventCategory.ADDED, a2);
        final AgentEvent ae3 = aef.build(EventCategory.ADDED, a3);

        assertThat(ae1.toString(), is(not(equalTo(ae2.toString()))));
        assertThat(ae1.toString(), is(equalTo(ae3.toString())));
    }

}
