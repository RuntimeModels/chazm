package runtimemodels.chazm.model.event;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import runtimemodels.chazm.api.entity.Capability;
import runtimemodels.chazm.model.entity.CapabilityFactory;
import runtimemodels.chazm.model.id.IdFactory;
import runtimemodels.chazm.model.relation.RelationModule;

import static org.hamcrest.CoreMatchers.*;
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
        final CapabilityEvent ce1 = cef.build(EventType.ADDED, c);
        final CapabilityEvent ce2 = cef.build(EventType.ADDED, c);

        assertThat(ce1, is(not(nullValue())));
        assertThat(ce1, is(not(sameInstance(ce2))));
    }

    @Test
    @Ignore
    public void testCapabilityEventFactoryWithNullCategoryAndNullCapability() {
        exception.expect(instanceOf(ProvisionException.class));
//        exception.expectMessage(allOf(
//                containsString("1st parameter of CapabilityEvent.<init>(CapabilityEvent.java:27) is not @Nullable"),
//                containsString("2nd parameter of CapabilityEvent.<init>(CapabilityEvent.java:27) is not @Nullable")
//        ));

        cef.build(null, null);
    }

    @Test
    @Ignore
    public void testCapabilityEventFactoryWithNullCapability() {
        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(containsString("2nd parameter of CapabilityEvent.<init>(CapabilityEvent.java:27) is not @Nullable"));

        cef.build(EventType.ADDED, null);
    }

    @Test
    public void testGetId() {
        final Capability c = cf.buildCapability(idf.build(Capability.class, "c"));
        final CapabilityEvent ce = cef.build(EventType.ADDED, c);

        assertThat(ce.getId(), is(sameInstance(c.getId())));
    }

    @Test
    public void testEquals() {
        final Capability c1 = cf.buildCapability(idf.build(Capability.class, "c1"));
        final Capability c2 = cf.buildCapability(idf.build(Capability.class, "c2"));
        final Capability c3 = cf.buildCapability(idf.build(Capability.class, "c1"));
        final CapabilityEvent ce1 = cef.build(EventType.ADDED, c1);
        final CapabilityEvent ce2 = cef.build(EventType.ADDED, c2);
        final CapabilityEvent ce3 = cef.build(EventType.ADDED, c3);

        assertThat(ce1, is(not(equalTo(ce2))));
        assertThat(ce1, is(equalTo(ce3)));
        assertThat(ce1, is(not(equalTo(""))));
    }

    @Test
    public void testHashCode() {
        final Capability c1 = cf.buildCapability(idf.build(Capability.class, "c1"));
        final Capability c2 = cf.buildCapability(idf.build(Capability.class, "c2"));
        final Capability c3 = cf.buildCapability(idf.build(Capability.class, "c1"));
        final CapabilityEvent ce1 = cef.build(EventType.ADDED, c1);
        final CapabilityEvent ce2 = cef.build(EventType.ADDED, c2);
        final CapabilityEvent ce3 = cef.build(EventType.ADDED, c3);

        assertThat(ce1.hashCode(), is(not(equalTo(ce2.hashCode()))));
        assertThat(ce1.hashCode(), is(equalTo(ce3.hashCode())));
    }

    @Test
    public void testToString() {
        final Capability c1 = cf.buildCapability(idf.build(Capability.class, "c1"));
        final Capability c2 = cf.buildCapability(idf.build(Capability.class, "c2"));
        final Capability c3 = cf.buildCapability(idf.build(Capability.class, "c1"));
        final CapabilityEvent ce1 = cef.build(EventType.ADDED, c1);
        final CapabilityEvent ce2 = cef.build(EventType.ADDED, c2);
        final CapabilityEvent ce3 = cef.build(EventType.ADDED, c3);

        assertThat(ce1.toString(), is(not(equalTo(ce2.toString()))));
        assertThat(ce1.toString(), is(equalTo(ce3.toString())));
    }

}
