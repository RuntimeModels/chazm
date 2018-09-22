package runtimemodels.chazm.model.entity;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import runtimemodels.chazm.api.entity.Agent;
import runtimemodels.chazm.api.id.UniqueId;
import runtimemodels.chazm.model.id.IdFactory;
import runtimemodels.chazm.model.id.IdModule;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@SuppressWarnings("javadoc")
public class AbstractEntityTest {

    private final Injector injector = Guice.createInjector(new IdModule());
    private final IdFactory idf = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testAbstractEntity() {
        final UniqueId<Agent> i1 = idf.build(Agent.class, "i1");
        final AbstractEntity<Agent> e1 = new AbstractEntity<Agent>(i1) {
        };
        final AbstractEntity<Agent> e2 = new AbstractEntity<Agent>(i1) {
        };

        assertThat(e1, is(not(nullValue())));
        assertThat(e1, is(not(sameInstance(e2))));
    }

    @Test
    public void testGetId() {
        final UniqueId<Agent> i1 = idf.build(Agent.class, "i1");
        final AbstractEntity<Agent> e1 = new AbstractEntity<Agent>(i1) {
        };
        final AbstractEntity<Agent> e2 = new AbstractEntity<Agent>(i1) {
        };

        assertThat(e1.getId(), is(sameInstance(i1)));
        assertThat(e2.getId(), is(sameInstance(i1)));
    }

    @Test
    public void testEquals() {
        final UniqueId<Agent> i1 = idf.build(Agent.class, "i1");
        final UniqueId<Agent> i2 = idf.build(Agent.class, "i2");
        final AbstractEntity<Agent> e1 = new AbstractEntity<Agent>(i1) {
        };
        final AbstractEntity<Agent> e2 = new AbstractEntity<Agent>(i2) {
        };
        final AbstractEntity<Agent> e3 = new AbstractEntity<Agent>(i1) {
        };

        assertThat(e1, is(not(equalTo(e2))));
        assertThat(e1, is(equalTo(e3)));
        assertThat(e1, is(not(equalTo(""))));
    }

    @Test
    public void testHashCode() {
        final UniqueId<Agent> i1 = idf.build(Agent.class, "i1");
        final UniqueId<Agent> i2 = idf.build(Agent.class, "i2");
        final AbstractEntity<Agent> e1 = new AbstractEntity<Agent>(i1) {
        };
        final AbstractEntity<Agent> e2 = new AbstractEntity<Agent>(i2) {
        };
        final AbstractEntity<Agent> e3 = new AbstractEntity<Agent>(i1) {
        };

        assertThat(e1.hashCode(), is(not(equalTo(e2.hashCode()))));
        assertThat(e1.hashCode(), is(equalTo(e3.hashCode())));
    }

    @Test
    public void testToString() {
        final UniqueId<Agent> i1 = idf.build(Agent.class, "i1");
        final UniqueId<Agent> i2 = idf.build(Agent.class, "i2");
        final AbstractEntity<Agent> e1 = new AbstractEntity<Agent>(i1) {
        };
        final AbstractEntity<Agent> e2 = new AbstractEntity<Agent>(i2) {
        };
        final AbstractEntity<Agent> e3 = new AbstractEntity<Agent>(i1) {
        };

        assertThat(e1.toString(), is(not(equalTo(e2.toString()))));
        assertThat(e1.toString(), is(equalTo(e3.toString())));
    }

}
