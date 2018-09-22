package runtimemodels.chazm.model.event;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import runtimemodels.chazm.api.entity.Pmf;
import runtimemodels.chazm.model.entity.PmfFactory;
import runtimemodels.chazm.model.id.IdFactory;
import runtimemodels.chazm.model.relation.RelationModule;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@SuppressWarnings("javadoc")
public class PmfEventTest {

    private final Injector injector = Guice.createInjector(new RelationModule(), new EventModule());
    private final PmfEventFactory pef = injector.getInstance(PmfEventFactory.class);
    private final PmfFactory pf = injector.getInstance(PmfFactory.class);
    private final IdFactory idf = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testPmfEventFactory() {
        final Pmf p = pf.buildPmf(idf.build(Pmf.class, "p"));
        final PmfEvent pe1 = pef.build(EventCategory.ADDED, p);
        final PmfEvent pe2 = pef.build(EventCategory.ADDED, p);

        assertThat(pe1, is(not(nullValue())));
        assertThat(pe1, is(not(sameInstance(pe2))));
    }

    @Test
    @Ignore
    public void testPmfEventFactoryWithNullCategoryAndNullPmf() {
        exception.expect(instanceOf(ProvisionException.class));
//        exception.expectMessage(allOf(
//                containsString("1st parameter of PmfEvent.<init>(PmfEvent.java:27) is not @Nullable"),
//                containsString("2nd parameter of PmfEvent.<init>(PmfEvent.java:27) is not @Nullable")
//        ));

        pef.build(null, null);
    }

    @Test
    @Ignore
    public void testPmfEventWithNullPmf() {
        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(containsString("2nd parameter of PmfEvent.<init>(PmfEvent.java:27) is not @Nullable"));

        pef.build(EventCategory.ADDED, null);
    }

    @Test
    public void testGetId() {
        final Pmf p = pf.buildPmf(idf.build(Pmf.class, "p"));
        final PmfEvent pe = pef.build(EventCategory.ADDED, p);

        assertThat(pe.getId(), is(sameInstance(p.getId())));
    }

    @Test
    public void testEquals() {
        final Pmf p1 = pf.buildPmf(idf.build(Pmf.class, "p1"));
        final Pmf p2 = pf.buildPmf(idf.build(Pmf.class, "p2"));
        final Pmf p3 = pf.buildPmf(idf.build(Pmf.class, "p1"));
        final PmfEvent pe1 = pef.build(EventCategory.ADDED, p1);
        final PmfEvent pe2 = pef.build(EventCategory.ADDED, p2);
        final PmfEvent pe3 = pef.build(EventCategory.ADDED, p3);

        assertThat(pe1, is(not(equalTo(pe2))));
        assertThat(pe1, is(equalTo(pe3)));
        assertThat(pe1, is(not(equalTo(""))));
    }

    @Test
    public void testHashCode() {
        final Pmf p1 = pf.buildPmf(idf.build(Pmf.class, "p1"));
        final Pmf p2 = pf.buildPmf(idf.build(Pmf.class, "p2"));
        final Pmf p3 = pf.buildPmf(idf.build(Pmf.class, "p1"));
        final PmfEvent pe1 = pef.build(EventCategory.ADDED, p1);
        final PmfEvent pe2 = pef.build(EventCategory.ADDED, p2);
        final PmfEvent pe3 = pef.build(EventCategory.ADDED, p3);

        assertThat(pe1.hashCode(), is(not(equalTo(pe2.hashCode()))));
        assertThat(pe1.hashCode(), is(equalTo(pe3.hashCode())));
    }

    @Test
    public void testToString() {
        final Pmf p1 = pf.buildPmf(idf.build(Pmf.class, "p1"));
        final Pmf p2 = pf.buildPmf(idf.build(Pmf.class, "p2"));
        final Pmf p3 = pf.buildPmf(idf.build(Pmf.class, "p1"));
        final PmfEvent pe1 = pef.build(EventCategory.ADDED, p1);
        final PmfEvent pe2 = pef.build(EventCategory.ADDED, p2);
        final PmfEvent pe3 = pef.build(EventCategory.ADDED, p3);

        assertThat(pe1.toString(), is(not(equalTo(pe2.toString()))));
        assertThat(pe1.toString(), is(equalTo(pe3.toString())));
    }

}
