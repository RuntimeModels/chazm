package io.github.runtimemodels.chazm.relation;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import io.github.runtimemodels.chazm.entity.Agent;
import io.github.runtimemodels.chazm.entity.Agent.ContactInfo;
import io.github.runtimemodels.chazm.entity.AgentFactory;
import io.github.runtimemodels.chazm.entity.Capability;
import io.github.runtimemodels.chazm.entity.CapabilityFactory;
import io.github.runtimemodels.chazm.id.IdFactory;
import io.github.runtimemodels.message.E;
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
import static org.junit.Assert.assertThat;

@SuppressWarnings("javadoc")
public class PossessesRelationTest {

    private final Injector injector = Guice.createInjector(new RelationModule());
    private final PossessesFactory possessesFactory = injector.getInstance(PossessesFactory.class);
    private final AgentFactory agentFactory = injector.getInstance(AgentFactory.class);
    private final CapabilityFactory capabilityFactory = injector.getInstance(CapabilityFactory.class);
    private final IdFactory idFactory = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testPossessesRelationFactory() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));
        final Possesses ps1 = possessesFactory.buildPossesses(a, c, 1d);
        final Possesses ps2 = possessesFactory.buildPossesses(a, c, 1d);

        assertThat(ps1, is(not(nullValue())));
        assertThat(ps1, is(not(sameInstance(ps2))));
    }

    @Test
    public void testPossessesRelationFactoryWithNullAgentAndNullCapability() {
        exception.expect(instanceOf(ProvisionException.class));
//        exception.expectMessage(allOf(
//                containsString("1st parameter of io.github.runtimemodels.chazm.relation.PossessesRelation.<init>(PossessesRelation.java:34) is not @Nullable"),
//                containsString("2nd parameter of io.github.runtimemodels.chazm.relation.PossessesRelation.<init>(PossessesRelation.java:34) is not @Nullable")
//        ));

        possessesFactory.buildPossesses(null, null, 1d);
    }

    @Test
    public void testPossessesRelationFactoryWithNullCapability() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});

        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(containsString("2nd parameter of io.github.runtimemodels.chazm.relation.PossessesRelation.<init>(PossessesRelation.java:34) is not @Nullable"));

        possessesFactory.buildPossesses(a, null, 1d);
    }

    @Test
    public void testPossesses3() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(equalTo(E.SCORE_BETWEEN.get(-0.01, PossessesRelation.MIN_SCORE, PossessesRelation.MAX_SCORE)));

        possessesFactory.buildPossesses(a, c, -0.01);
    }

    @Test
    public void testPossesses4() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(equalTo(E.SCORE_BETWEEN.get(1.01, PossessesRelation.MIN_SCORE, PossessesRelation.MAX_SCORE)));

        possessesFactory.buildPossesses(a, c, 1.01);
    }

    @Test
    public void testGetAgent() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));
        final Possesses ps = possessesFactory.buildPossesses(a, c, 1d);

        assertThat(ps.getAgent(), is(sameInstance(a)));
    }

    @Test
    public void testGetCapability() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));
        final Possesses ps = possessesFactory.buildPossesses(a, c, 1d);

        assertThat(ps.getCapability(), is(sameInstance(c)));
    }

    @Test
    public void testGetScore() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));
        final Possesses ps = possessesFactory.buildPossesses(a, c, 1d);

        assertThat(ps.getScore(), is(equalTo(1d)));
    }

    @Test
    public void testSetScore() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));
        final Possesses ps = possessesFactory.buildPossesses(a, c, 1d);
        ps.setScore(0.5);

        assertThat(ps.getScore(), is(equalTo(0.5)));
    }

    @Test
    public void testSetScore1() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));
        final Possesses ps = possessesFactory.buildPossesses(a, c, 1d);

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(equalTo(E.SCORE_BETWEEN.get(-0.01, PossessesRelation.MIN_SCORE, PossessesRelation.MAX_SCORE)));

        ps.setScore(-0.01);
    }

    @Test
    public void testSetScore2() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));
        final Possesses ps = possessesFactory.buildPossesses(a, c, 1d);

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(equalTo(E.SCORE_BETWEEN.get(1.01, PossessesRelation.MIN_SCORE, PossessesRelation.MAX_SCORE)));

        ps.setScore(1.01);
    }

    @Test
    public void testEquals() {
        final Agent a1 = agentFactory.buildAgent(idFactory.build(Agent.class, "a1"), new ContactInfo() {});
        final Agent a2 = agentFactory.buildAgent(idFactory.build(Agent.class, "a2"), new ContactInfo() {});
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
        final Agent a1 = agentFactory.buildAgent(idFactory.build(Agent.class, "a1"), new ContactInfo() {});
        final Agent a2 = agentFactory.buildAgent(idFactory.build(Agent.class, "a2"), new ContactInfo() {});
        final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));
        final Possesses ps1 = possessesFactory.buildPossesses(a1, c, 1d);
        final Possesses ps2 = possessesFactory.buildPossesses(a2, c, 1d);
        final Possesses ps3 = possessesFactory.buildPossesses(a1, c, 1d);

        assertThat(ps1.hashCode(), is(not(equalTo(ps2.hashCode()))));
        assertThat(ps1.hashCode(), is(equalTo(ps3.hashCode())));
    }

    @Test
    public void testToString() {
        final Agent a1 = agentFactory.buildAgent(idFactory.build(Agent.class, "a1"), new ContactInfo() {});
        final Agent a2 = agentFactory.buildAgent(idFactory.build(Agent.class, "a2"), new ContactInfo() {});
        final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));
        final Possesses ps1 = possessesFactory.buildPossesses(a1, c, 1d);
        final Possesses ps2 = possessesFactory.buildPossesses(a2, c, 1d);
        final Possesses ps3 = possessesFactory.buildPossesses(a1, c, 1d);

        assertThat(ps1.toString(), is(not(equalTo(ps2.toString()))));
        assertThat(ps1.toString(), is(equalTo(ps3.toString())));
    }

}
