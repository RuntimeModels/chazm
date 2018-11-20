package runtimemodels.chazm.model.entity;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import org.junit.jupiter.api.Test;
import runtimemodels.chazm.api.entity.Capability;
import runtimemodels.chazm.api.id.UniqueId;
import runtimemodels.chazm.model.id.IdFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class CapabilityEntityTest {

    private final Injector injector = Guice.createInjector(new EntityModule());
    private final CapabilityFactory capabilityFactory = injector.getInstance(CapabilityFactory.class);
    private final IdFactory idFactory = injector.getInstance(IdFactory.class);


    @Test
    public void testCapabilityFactory() {
        final UniqueId<Capability> i1 = idFactory.build(Capability.class, 1L);
        final Capability c1 = capabilityFactory.buildCapability(i1);
        final Capability c2 = capabilityFactory.buildCapability(i1);

        assertAll(
                () -> assertThat(c1).isNotNull(),
                () -> assertThat(c2).isNotNull(),
                () -> assertThat(c1).isNotSameAs(c2)
        );
    }

    @Test
    public void testCapabilityFactoryWithNullId() {
        assertThrows(ProvisionException.class, () -> capabilityFactory.buildCapability(null));
    }

    @Test
    public void testGetId() {
        final UniqueId<Capability> i1 = idFactory.build(Capability.class, 1L);
        final UniqueId<Capability> i2 = idFactory.build(Capability.class, 1L);
        final Capability c1 = capabilityFactory.buildCapability(i1);
        final Capability c2 = capabilityFactory.buildCapability(i2);

        assertAll(
                () -> assertThat(c1.getId()).isSameAs(i1),
                () -> assertThat(c1.getId()).isNotSameAs(c2.getId())
        );
    }

    @Test
    public void testEqualsObject() {
        final UniqueId<Capability> i1 = idFactory.build(Capability.class, 1L);
        final UniqueId<Capability> i2 = idFactory.build(Capability.class, 2L);
        final Capability c1 = capabilityFactory.buildCapability(i1);
        final Capability c2 = capabilityFactory.buildCapability(i2);
        final Capability c3 = capabilityFactory.buildCapability(i1);

        assertAll(
                () -> assertThat(c1).isNotEqualTo(c2),
                () -> assertThat(c1).isEqualTo(c3),
                () -> assertThat(c1).isNotEqualTo("")
        );
    }

    @Test
    public void testHashCode() {
        final UniqueId<Capability> i1 = idFactory.build(Capability.class, 1L);
        final UniqueId<Capability> i2 = idFactory.build(Capability.class, 2L);
        final Capability c1 = capabilityFactory.buildCapability(i1);
        final Capability c2 = capabilityFactory.buildCapability(i2);
        final Capability c3 = capabilityFactory.buildCapability(i1);

        assertAll(
                () -> assertThat(c1.hashCode()).isNotEqualTo(c2.hashCode()),
                () -> assertThat(c1.hashCode()).isEqualTo(c3.hashCode())
        );
    }

    @Test
    public void testToString() {
        final UniqueId<Capability> i1 = idFactory.build(Capability.class, 1L);
        final UniqueId<Capability> i2 = idFactory.build(Capability.class, 2L);
        final Capability c1 = capabilityFactory.buildCapability(i1);
        final Capability c2 = capabilityFactory.buildCapability(i2);
        final Capability c3 = capabilityFactory.buildCapability(i1);

        assertAll(
                () -> assertThat(c1.toString()).isNotEqualTo(c2.toString()),
                () -> assertThat(c1.toString()).isEqualTo(c3.toString())
        );
    }

}
