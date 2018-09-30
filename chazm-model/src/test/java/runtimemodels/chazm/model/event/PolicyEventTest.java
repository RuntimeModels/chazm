package runtimemodels.chazm.model.event;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import runtimemodels.chazm.api.entity.Policy;
import runtimemodels.chazm.model.entity.PolicyFactory;
import runtimemodels.chazm.model.id.IdFactory;
import runtimemodels.chazm.model.relation.RelationModule;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@SuppressWarnings("javadoc")
public class PolicyEventTest {

    private final Injector injector = Guice.createInjector(new RelationModule(), new EventModule());
    private final PolicyEventFactory pef = injector.getInstance(PolicyEventFactory.class);
    private final PolicyFactory pf = injector.getInstance(PolicyFactory.class);
    private final IdFactory idf = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testPolicyEventFactory() {
        final Policy p = pf.buildPolicy(idf.build(Policy.class, "p"));
        final PolicyEvent pe1 = pef.build(EventType.ADDED, p);
        final PolicyEvent pe2 = pef.build(EventType.ADDED, p);

        assertThat(pe1, is(not(nullValue())));
        assertThat(pe1, is(not(sameInstance(pe2))));
    }

    @Test
    @Ignore
    public void testPolicyEventFactoryWithNullCategoryAndNullPolicy() {
        exception.expect(instanceOf(ProvisionException.class));
//        exception.expectMessage(allOf(
//                containsString("1st parameter of PolicyEvent.<init>(PolicyEvent.java:27) is not @Nullable"),
//                containsString("2nd parameter of PolicyEvent.<init>(PolicyEvent.java:27) is not @Nullable")
//        ));

        pef.build(null, null);
    }

    @Test
    @Ignore
    public void testPolicyEventFactoryWithNullPolicy() {
        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(containsString("2nd parameter of PolicyEvent.<init>(PolicyEvent.java:27) is not @Nullable"));

        pef.build(EventType.ADDED, null);
    }

    @Test
    public void testGetId() {
        final Policy p = pf.buildPolicy(idf.build(Policy.class, "p"));
        final PolicyEvent pe = pef.build(EventType.ADDED, p);

        assertThat(pe.getId(), is(sameInstance(p.getId())));
    }

    @Test
    public void testEquals() {
        final Policy p1 = pf.buildPolicy(idf.build(Policy.class, "p1"));
        final Policy p2 = pf.buildPolicy(idf.build(Policy.class, "p2"));
        final Policy p3 = pf.buildPolicy(idf.build(Policy.class, "p1"));
        final PolicyEvent pe1 = pef.build(EventType.ADDED, p1);
        final PolicyEvent pe2 = pef.build(EventType.ADDED, p2);
        final PolicyEvent pe3 = pef.build(EventType.ADDED, p3);

        assertThat(pe1, is(not(equalTo(pe2))));
        assertThat(pe1, is(equalTo(pe3)));
        assertThat(pe1, is(not(equalTo(""))));
    }

    @Test
    public void testHashCode() {
        final Policy p1 = pf.buildPolicy(idf.build(Policy.class, "p1"));
        final Policy p2 = pf.buildPolicy(idf.build(Policy.class, "p2"));
        final Policy p3 = pf.buildPolicy(idf.build(Policy.class, "p1"));
        final PolicyEvent pe1 = pef.build(EventType.ADDED, p1);
        final PolicyEvent pe2 = pef.build(EventType.ADDED, p2);
        final PolicyEvent pe3 = pef.build(EventType.ADDED, p3);

        assertThat(pe1.hashCode(), is(not(equalTo(pe2.hashCode()))));
        assertThat(pe1.hashCode(), is(equalTo(pe3.hashCode())));
    }

    @Test
    public void testToString() {
        final Policy p1 = pf.buildPolicy(idf.build(Policy.class, "p1"));
        final Policy p2 = pf.buildPolicy(idf.build(Policy.class, "p2"));
        final Policy p3 = pf.buildPolicy(idf.build(Policy.class, "p1"));
        final PolicyEvent pe1 = pef.build(EventType.ADDED, p1);
        final PolicyEvent pe2 = pef.build(EventType.ADDED, p2);
        final PolicyEvent pe3 = pef.build(EventType.ADDED, p3);

        assertThat(pe1.toString(), is(not(equalTo(pe2.toString()))));
        assertThat(pe1.toString(), is(equalTo(pe3.toString())));
    }

}
