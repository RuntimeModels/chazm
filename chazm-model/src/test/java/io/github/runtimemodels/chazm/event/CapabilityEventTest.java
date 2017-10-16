package io.github.runtimemodels.chazm.event;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import io.github.runtimemodels.chazm.entity.Capability;
import io.github.runtimemodels.chazm.entity.CapabilityFactory;
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
import static org.junit.Assert.assertThat;

@SuppressWarnings("javadoc")
public class CapabilityEventTest {

    private final Injector injector = Guice.createInjector(new RelationModule(), new EventModule());
    private final CapabilityEventFactory cef = injector.getInstance(CapabilityEventFactory.class);
    private final CapabilityFactory cf = injector.getInstance(CapabilityFactory.class);
    private final IdFactory idf = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testCapabilityEventFactory() {
        final Capability c = cf.buildCapability(idf.build(Capability.class, "c"));
        final CapabilityEvent ce1 = cef.build(EventCategory.ADDED, c);
        final CapabilityEvent ce2 = cef.build(EventCategory.ADDED, c);

        assertThat(ce1, is(not(nullValue())));
        assertThat(ce1, is(not(sameInstance(ce2))));
    }

    @Test
    public void testCapabilityEventFactoryWithNullCategoryAndNullCapability() {
        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(allOf(
                containsString("1st parameter of io.github.runtimemodels.chazm.event.CapabilityEvent.<init>(CapabilityEvent.java:27) is not @Nullable"),
                containsString("2nd parameter of io.github.runtimemodels.chazm.event.CapabilityEvent.<init>(CapabilityEvent.java:27) is not @Nullable")
        ));

        cef.build(null, null);
    }

    @Test
    public void testCapabilityEventFactoryWithNullCapability() {
        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(containsString("2nd parameter of io.github.runtimemodels.chazm.event.CapabilityEvent.<init>(CapabilityEvent.java:27) is not @Nullable"));

        cef.build(EventCategory.ADDED, null);
    }

    @Test
    public void testGetId() {
        final Capability c = cf.buildCapability(idf.build(Capability.class, "c"));
        final CapabilityEvent ce = cef.build(EventCategory.ADDED, c);

        assertThat(ce.getId(), is(sameInstance(c.getId())));
    }

    @Test
    public void testEquals() {
        final Capability c1 = cf.buildCapability(idf.build(Capability.class, "c1"));
        final Capability c2 = cf.buildCapability(idf.build(Capability.class, "c2"));
        final Capability c3 = cf.buildCapability(idf.build(Capability.class, "c1"));
        final CapabilityEvent ce1 = cef.build(EventCategory.ADDED, c1);
        final CapabilityEvent ce2 = cef.build(EventCategory.ADDED, c2);
        final CapabilityEvent ce3 = cef.build(EventCategory.ADDED, c3);

        assertThat(ce1, is(not(equalTo(ce2))));
        assertThat(ce1, is(equalTo(ce3)));
        assertThat(ce1, is(not(equalTo(""))));
    }

    @Test
    public void testHashCode() {
        final Capability c1 = cf.buildCapability(idf.build(Capability.class, "c1"));
        final Capability c2 = cf.buildCapability(idf.build(Capability.class, "c2"));
        final Capability c3 = cf.buildCapability(idf.build(Capability.class, "c1"));
        final CapabilityEvent ce1 = cef.build(EventCategory.ADDED, c1);
        final CapabilityEvent ce2 = cef.build(EventCategory.ADDED, c2);
        final CapabilityEvent ce3 = cef.build(EventCategory.ADDED, c3);

        assertThat(ce1.hashCode(), is(not(equalTo(ce2.hashCode()))));
        assertThat(ce1.hashCode(), is(equalTo(ce3.hashCode())));
    }

    @Test
    public void testToString() {
        final Capability c1 = cf.buildCapability(idf.build(Capability.class, "c1"));
        final Capability c2 = cf.buildCapability(idf.build(Capability.class, "c2"));
        final Capability c3 = cf.buildCapability(idf.build(Capability.class, "c1"));
        final CapabilityEvent ce1 = cef.build(EventCategory.ADDED, c1);
        final CapabilityEvent ce2 = cef.build(EventCategory.ADDED, c2);
        final CapabilityEvent ce3 = cef.build(EventCategory.ADDED, c3);

        assertThat(ce1.toString(), is(not(equalTo(ce2.toString()))));
        assertThat(ce1.toString(), is(equalTo(ce3.toString())));
    }

}
