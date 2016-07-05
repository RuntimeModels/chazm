package io.github.runtimemodels.chazm.relation;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import io.github.runtimemodels.chazm.entity.Attribute;
import io.github.runtimemodels.chazm.entity.AttributeFactory;
import io.github.runtimemodels.chazm.entity.Pmf;
import io.github.runtimemodels.chazm.entity.PmfFactory;
import io.github.runtimemodels.chazm.id.IdFactory;
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
public class ModeratesTest {

    private final Injector injector = Guice.createInjector(new RelationModule());
    private final ModeratesFactory moderatesFactory = injector.getInstance(ModeratesFactory.class);
    private final PmfFactory pmfFactory = injector.getInstance(PmfFactory.class);
    private final AttributeFactory attributeFactory = injector.getInstance(AttributeFactory.class);
    private final IdFactory idFactory = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testModerates() {
        final Pmf p = pmfFactory.buildPmf(idFactory.build(Pmf.class, "p"));
        final Attribute a = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
        final Moderates md1 = moderatesFactory.buildModerates(p, a);
        final Moderates md2 = moderatesFactory.buildModerates(p, a);

        assertThat(md1, is(not(nullValue())));
        assertThat(md1, is(not(sameInstance(md2))));
    }

    @Test
    public void testModerates1() {
        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

        moderatesFactory.buildModerates(null, null);
    }

    @Test
    public void testModerates2() {
        final Pmf p = pmfFactory.buildPmf(idFactory.build(Pmf.class, "p"));

        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

        moderatesFactory.buildModerates(p, null);
    }

    @Test
    public void testGetPmf() {
        final Pmf p = pmfFactory.buildPmf(idFactory.build(Pmf.class, "p"));
        final Attribute a = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
        final Moderates md = moderatesFactory.buildModerates(p, a);

        assertThat(md.getPmf(), is(sameInstance(p)));
    }

    @Test
    public void testGetAttribute() {
        final Pmf p = pmfFactory.buildPmf(idFactory.build(Pmf.class, "p"));
        final Attribute a = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
        final Moderates md = moderatesFactory.buildModerates(p, a);

        assertThat(md.getAttribute(), is(sameInstance(a)));
    }

    @Test
    public void testEquals() {
        final Pmf p1 = pmfFactory.buildPmf(idFactory.build(Pmf.class, "p1"));
        final Pmf p2 = pmfFactory.buildPmf(idFactory.build(Pmf.class, "p2"));
        final Attribute a = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
        final Moderates md1 = moderatesFactory.buildModerates(p1, a);
        final Moderates md2 = moderatesFactory.buildModerates(p2, a);
        final Moderates md3 = moderatesFactory.buildModerates(p1, a);

        assertThat(md1, is(not(equalTo(md2))));
        assertThat(md1, is(equalTo(md3)));
        assertThat(md1, is(not(equalTo(""))));
    }

    @Test
    public void testHashCode() {
        final Pmf p1 = pmfFactory.buildPmf(idFactory.build(Pmf.class, "p1"));
        final Pmf p2 = pmfFactory.buildPmf(idFactory.build(Pmf.class, "p2"));
        final Attribute a = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
        final Moderates md1 = moderatesFactory.buildModerates(p1, a);
        final Moderates md2 = moderatesFactory.buildModerates(p2, a);
        final Moderates md3 = moderatesFactory.buildModerates(p1, a);

        assertThat(md1.hashCode(), is(not(equalTo(md2.hashCode()))));
        assertThat(md1.hashCode(), is(equalTo(md3.hashCode())));
    }

    @Test
    public void testToString() {
        final Pmf p1 = pmfFactory.buildPmf(idFactory.build(Pmf.class, "p1"));
        final Pmf p2 = pmfFactory.buildPmf(idFactory.build(Pmf.class, "p2"));
        final Attribute a = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
        final Moderates md1 = moderatesFactory.buildModerates(p1, a);
        final Moderates md2 = moderatesFactory.buildModerates(p2, a);
        final Moderates md3 = moderatesFactory.buildModerates(p1, a);

        assertThat(md1.toString(), is(not(equalTo(md2.toString()))));
        assertThat(md1.toString(), is(equalTo(md3.toString())));
    }

}
