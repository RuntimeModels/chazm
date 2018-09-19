package io.github.runtimemodels.chazm.entity;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import io.github.runtimemodels.chazm.id.IdFactory;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import runtimemodels.chazm.api.entity.Pmf;
import runtimemodels.chazm.api.id.UniqueId;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@SuppressWarnings("javadoc")
public class PmfEntityTest {

    private final Injector injector = Guice.createInjector(new EntityModule());
    private final PmfFactory pmfFactory = injector.getInstance(PmfFactory.class);
    private final IdFactory idFactory = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testPmfFactory() {
        final UniqueId<Pmf> i1 = idFactory.build(Pmf.class, 1L);
        final Pmf p1 = pmfFactory.buildPmf(i1);
        final Pmf p2 = pmfFactory.buildPmf(i1);

        assertThat(p1, is(not(nullValue())));
        assertThat(p1, is(not(sameInstance(p2))));
    }

    @Test
    @Ignore
    public void testPmfFactoryWithNullId() {
        exception.expect(instanceOf(ProvisionException.class));
//        exception.expectMessage((containsString("1st parameter of io.github.runtimemodels.chazm.entity.PmfEntity.<init>(PmfEntity.java:13) is not @Nullable")));
        pmfFactory.buildPmf(null);
    }

    @Test
    public void testGetId() {
        final UniqueId<Pmf> i1 = idFactory.build(Pmf.class, 1L);
        final UniqueId<Pmf> i2 = idFactory.build(Pmf.class, 1L);
        final Pmf p1 = pmfFactory.buildPmf(i1);
        final Pmf p2 = pmfFactory.buildPmf(i2);

        assertThat(p1.getId(), is(sameInstance(i1)));
        assertThat(p1.getId(), is(not(sameInstance(p2.getId()))));
    }

    @Test
    public void testEqualsObject() {
        final UniqueId<Pmf> i1 = idFactory.build(Pmf.class, 1L);
        final UniqueId<Pmf> i2 = idFactory.build(Pmf.class, 2L);
        final Pmf p1 = pmfFactory.buildPmf(i1);
        final Pmf p2 = pmfFactory.buildPmf(i2);
        final Pmf p3 = pmfFactory.buildPmf(i1);

        assertThat(p1, is(not(equalTo(p2))));
        assertThat(p1, is(equalTo(p3)));
        assertThat(p1, is(not(equalTo(""))));
    }

    @Test
    public void testHashCode() {
        final UniqueId<Pmf> i1 = idFactory.build(Pmf.class, 1L);
        final UniqueId<Pmf> i2 = idFactory.build(Pmf.class, 2L);
        final Pmf p1 = pmfFactory.buildPmf(i1);
        final Pmf p2 = pmfFactory.buildPmf(i2);
        final Pmf p3 = pmfFactory.buildPmf(i1);

        assertThat(p1.hashCode(), is(not(equalTo(p2.hashCode()))));
        assertThat(p1.hashCode(), is(equalTo(p3.hashCode())));
    }

    @Test
    public void testToString() {
        final UniqueId<Pmf> i1 = idFactory.build(Pmf.class, 1L);
        final UniqueId<Pmf> i2 = idFactory.build(Pmf.class, 2L);
        final Pmf p1 = pmfFactory.buildPmf(i1);
        final Pmf p2 = pmfFactory.buildPmf(i2);
        final Pmf p3 = pmfFactory.buildPmf(i1);

        assertThat(p1.toString(), is(not(equalTo(p2.toString()))));
        assertThat(p1.toString(), is(equalTo(p3.toString())));
    }

}
