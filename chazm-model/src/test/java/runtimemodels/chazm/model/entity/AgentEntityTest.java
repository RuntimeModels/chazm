package runtimemodels.chazm.model.entity;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import org.junit.jupiter.api.Test;
import runtimemodels.chazm.api.entity.Agent;
import runtimemodels.chazm.api.id.UniqueId;
import runtimemodels.chazm.model.id.IdFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class AgentEntityTest {

    private final Injector injector = Guice.createInjector(new EntityModule());
    private final AgentFactory agentFactory = injector.getInstance(AgentFactory.class);
    private final IdFactory idFactory = injector.getInstance(IdFactory.class);

    @Test
    public void testAgentFactory() {
        final UniqueId<Agent> i1 = idFactory.build(Agent.class, 1L);
        final Agent a1 = agentFactory.buildAgent(i1, new Agent.ContactInfo() {
        });
        final Agent a2 = agentFactory.buildAgent(i1, new Agent.ContactInfo() {
        });
        assertAll(
                () -> assertThat(a1).isNotNull(),
                () -> assertThat(a1).isNotSameAs(a2)
        );
    }

    @Test
    public void testAgentFactoryWithNullIdAndNullContactInfo() {
        assertThrows(ProvisionException.class, () -> agentFactory.buildAgent(null, null));
    }

    @Test
    public void testAgentFactoryWithNullContactInfo() {
        final UniqueId<Agent> i1 = idFactory.build(Agent.class, 1L);
        assertThrows(ProvisionException.class, () -> agentFactory.buildAgent(i1, null));
    }

    @Test
    public void testGetContactInfo() {
        final UniqueId<Agent> i1 = idFactory.build(Agent.class, 1L);
        final Agent.ContactInfo contactInfo = new Agent.ContactInfo() {
        };
        final Agent a1 = agentFactory.buildAgent(i1, contactInfo);

        assertThat(a1.getContactInfo()).isSameAs(contactInfo);
    }

    @Test
    public void testGetId() {
        final UniqueId<Agent> i1 = idFactory.build(Agent.class, 1L);
        final UniqueId<Agent> i2 = idFactory.build(Agent.class, 1L);
        final Agent a1 = agentFactory.buildAgent(i1, new Agent.ContactInfo() {
        });
        final Agent a2 = agentFactory.buildAgent(i2, new Agent.ContactInfo() {
        });

        assertAll(
                () -> assertThat(a1.getId()).isSameAs(i1),
                () -> assertThat(a1.getId()).isNotSameAs(a2.getId())
        );
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

        assertAll(
                () -> assertThat(a1).isNotEqualTo(a2),
                () -> assertThat(a1).isNotEqualTo("")
        );
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

        assertAll(
                () -> assertThat(a1.hashCode()).isNotEqualTo(a2.hashCode()),
                () -> assertThat(a1.hashCode()).isEqualTo(a3.hashCode())
        );
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

        assertAll(
                () -> assertThat(a1.toString()).isNotEqualTo(a2.toString()),
                () -> assertThat(a1.toString()).isEqualTo(a3.toString())
        );
    }

}
