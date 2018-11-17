package runtimemodels.chazm.model.entity;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.Test;
import runtimemodels.chazm.api.entity.Agent;
import runtimemodels.chazm.api.id.UniqueId;
import runtimemodels.chazm.model.id.IdFactory;
import runtimemodels.chazm.model.id.IdModule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AbstractEntityTest {

    private final Injector injector = Guice.createInjector(new IdModule());
    private final IdFactory idf = injector.getInstance(IdFactory.class);

    @Test
    public void testAbstractEntity() {
        final UniqueId<Agent> i1 = idf.build(Agent.class, "i1");
        final AbstractEntity<Agent> e1 = new AbstractEntity<Agent>(i1) {
        };
        final AbstractEntity<Agent> e2 = new AbstractEntity<Agent>(i1) {
        };

        assertAll(
                () -> assertThat(e1).isNotNull(),
                () -> assertThat(e2).isNotNull(),
                () -> assertThat(e1).isNotSameAs(e2)
        );
    }

    @Test
    public void testGetId() {
        final UniqueId<Agent> i1 = idf.build(Agent.class, "i1");
        final AbstractEntity<Agent> e1 = new AbstractEntity<Agent>(i1) {
        };
        final AbstractEntity<Agent> e2 = new AbstractEntity<Agent>(i1) {
        };

        assertAll(
                () -> assertThat(e1.getId()).isSameAs(i1),
                () -> assertThat(e2.getId()).isSameAs(i1)
        );
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

        assertAll(
                () -> assertThat(e1).isNotEqualTo(e2),
                () -> assertThat(e1).isEqualTo(e3),
                () -> assertThat(e1).isNotEqualTo("")
        );
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

        assertAll(
                () -> assertThat(e1.hashCode()).isNotEqualTo(e2.hashCode()),
                () -> assertThat(e1.hashCode()).isEqualTo(e3.hashCode())
        );
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

        assertAll(
                () -> assertThat(e1.toString()).isNotEqualTo(e2.toString()),
                () -> assertThat(e1.toString()).isEqualTo(e3.toString())
        );
    }

}
