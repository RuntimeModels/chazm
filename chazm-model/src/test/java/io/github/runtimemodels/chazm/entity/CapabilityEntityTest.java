package io.github.runtimemodels.chazm.entity;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import io.github.runtimemodels.chazm.id.IdFactory;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import runtimemodels.chazm.api.entity.Capability;
import runtimemodels.chazm.api.id.UniqueId;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@SuppressWarnings("javadoc")
public class CapabilityEntityTest {

    private final Injector injector = Guice.createInjector(new EntityModule());
    private final CapabilityFactory capabilityFactory = injector.getInstance(CapabilityFactory.class);
    private final IdFactory idFactory = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testCapabilityFactory() {
        final UniqueId<Capability> i1 = idFactory.build(Capability.class, 1L);
        final Capability c1 = capabilityFactory.buildCapability(i1);
        final Capability c2 = capabilityFactory.buildCapability(i1);

        assertThat(c1, is(not(nullValue())));
        assertThat(c1, is(not(sameInstance(c2))));
    }

    @Test
    @Ignore
    public void testCapabilityFactoryWithNullId() {
        exception.expect(instanceOf(ProvisionException.class));
//        exception.expectMessage(containsString("1st parameter of io.github.runtimemodels.chazm.entity.CapabilityEntity.<init>(CapabilityEntity.java:13) is not @Nullable"));
        capabilityFactory.buildCapability(null);
    }

    @Test
    public void testGetId() {
        final UniqueId<Capability> i1 = idFactory.build(Capability.class, 1L);
        final UniqueId<Capability> i2 = idFactory.build(Capability.class, 1L);
        final Capability c1 = capabilityFactory.buildCapability(i1);
        final Capability c2 = capabilityFactory.buildCapability(i2);

        assertThat(c1.getId(), is(sameInstance(i1)));
        assertThat(c1.getId(), is(not(sameInstance(c2.getId()))));
    }

    @Test
    public void testEqualsObject() {
        final UniqueId<Capability> i1 = idFactory.build(Capability.class, 1L);
        final UniqueId<Capability> i2 = idFactory.build(Capability.class, 2L);
        final Capability c1 = capabilityFactory.buildCapability(i1);
        final Capability c2 = capabilityFactory.buildCapability(i2);
        final Capability c3 = capabilityFactory.buildCapability(i1);

        assertThat(c1, is(not(equalTo(c2))));
        assertThat(c1, is(equalTo(c3)));
        assertThat(c1, is(not(equalTo(""))));
    }

    @Test
    public void testHashCode() {
        final UniqueId<Capability> i1 = idFactory.build(Capability.class, 1L);
        final UniqueId<Capability> i2 = idFactory.build(Capability.class, 2L);
        final Capability c1 = capabilityFactory.buildCapability(i1);
        final Capability c2 = capabilityFactory.buildCapability(i2);
        final Capability c3 = capabilityFactory.buildCapability(i1);

        assertThat(c1.hashCode(), is(not(equalTo(c2.hashCode()))));
        assertThat(c1.hashCode(), is(equalTo(c3.hashCode())));
    }

    @Test
    public void testToString() {
        final UniqueId<Capability> i1 = idFactory.build(Capability.class, 1L);
        final UniqueId<Capability> i2 = idFactory.build(Capability.class, 2L);
        final Capability c1 = capabilityFactory.buildCapability(i1);
        final Capability c2 = capabilityFactory.buildCapability(i2);
        final Capability c3 = capabilityFactory.buildCapability(i1);

        assertThat(c1.toString(), is(not(equalTo(c2.toString()))));
        assertThat(c1.toString(), is(equalTo(c3.toString())));
    }

}
