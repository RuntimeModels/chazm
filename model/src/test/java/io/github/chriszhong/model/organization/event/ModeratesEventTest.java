package io.github.chriszhong.model.organization.event;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import io.github.chriszhong.model.organization.entity.Attribute;
import io.github.chriszhong.model.organization.entity.AttributeFactory;
import io.github.chriszhong.model.organization.entity.Pmf;
import io.github.chriszhong.model.organization.entity.PmfFactory;
import io.github.chriszhong.model.organization.id.IdFactory;
import io.github.chriszhong.model.organization.relation.Moderates;
import io.github.chriszhong.model.organization.relation.ModeratesFactory;
import io.github.chriszhong.model.organization.relation.RelationModule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;

@SuppressWarnings("javadoc")
public class ModeratesEventTest {

	private final Injector injector = Guice.createInjector(new RelationModule(), new EventModule());
	private final ModeratesEventFactory mef = injector.getInstance(ModeratesEventFactory.class);
	private final ModeratesFactory mf = injector.getInstance(ModeratesFactory.class);
	private final PmfFactory pf = injector.getInstance(PmfFactory.class);
	private final AttributeFactory af = injector.getInstance(AttributeFactory.class);
	private final IdFactory idf = injector.getInstance(IdFactory.class);

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testModeratesEvent() {
		final Pmf p = pf.buildPmf(idf.build(Pmf.class, "p"));
		final Attribute a = af.buildAttribute(idf.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
		final Moderates m = mf.buildModerates(p, a);
		final ModeratesEvent me1 = mef.build(EventCategory.ADDED, m);
		final ModeratesEvent me2 = mef.build(EventCategory.ADDED, m);

		assertThat(me1, is(not(nullValue())));
		assertThat(me1, is(not(sameInstance(me2))));
	}

	@Test
	public void testModeratesEvent1() {
		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		mef.build(null, null);
	}

	@Test
	public void testModeratesEvent2() {
		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		mef.build(EventCategory.ADDED, null);
	}

	@Test
	public void testGetPmfId() {
		final Pmf p = pf.buildPmf(idf.build(Pmf.class, "p"));
		final Attribute a = af.buildAttribute(idf.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
		final Moderates m = mf.buildModerates(p, a);
		final ModeratesEvent me = mef.build(EventCategory.ADDED, m);

		assertThat(me.getPmfId(), is(sameInstance(p.getId())));
	}

	@Test
	public void testGetAttributeId() {
		final Pmf p = pf.buildPmf(idf.build(Pmf.class, "p"));
		final Attribute a = af.buildAttribute(idf.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
		final Moderates m = mf.buildModerates(p, a);
		final ModeratesEvent me = mef.build(EventCategory.ADDED, m);

		assertThat(me.getAttributeId(), is(sameInstance(a.getId())));
	}

	@Test
	public void testEquals() {
		final Pmf p1 = pf.buildPmf(idf.build(Pmf.class, "p1"));
		final Pmf p2 = pf.buildPmf(idf.build(Pmf.class, "p2"));
		final Attribute a = af.buildAttribute(idf.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
		final Moderates m1 = mf.buildModerates(p1, a);
		final Moderates m2 = mf.buildModerates(p2, a);
		final Moderates m3 = mf.buildModerates(p1, a);
		final ModeratesEvent me1 = mef.build(EventCategory.ADDED, m1);
		final ModeratesEvent me2 = mef.build(EventCategory.ADDED, m2);
		final ModeratesEvent me3 = mef.build(EventCategory.ADDED, m3);

		assertThat(me1, is(not(equalTo(me2))));
		assertThat(me1, is(equalTo(me3)));
		assertThat(me1, is(not(equalTo(""))));
	}

	@Test
	public void testModerateshCode() {
		final Pmf p1 = pf.buildPmf(idf.build(Pmf.class, "p1"));
		final Pmf p2 = pf.buildPmf(idf.build(Pmf.class, "p2"));
		final Attribute a = af.buildAttribute(idf.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
		final Moderates m1 = mf.buildModerates(p1, a);
		final Moderates m2 = mf.buildModerates(p2, a);
		final Moderates m3 = mf.buildModerates(p1, a);
		final ModeratesEvent me1 = mef.build(EventCategory.ADDED, m1);
		final ModeratesEvent me2 = mef.build(EventCategory.ADDED, m2);
		final ModeratesEvent me3 = mef.build(EventCategory.ADDED, m3);

		assertThat(me1.hashCode(), is(not(equalTo(me2.hashCode()))));
		assertThat(me1.hashCode(), is(equalTo(me3.hashCode())));
	}

	@Test
	public void testToString() {
		final Pmf p1 = pf.buildPmf(idf.build(Pmf.class, "p1"));
		final Pmf p2 = pf.buildPmf(idf.build(Pmf.class, "p2"));
		final Attribute a = af.buildAttribute(idf.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
		final Moderates m1 = mf.buildModerates(p1, a);
		final Moderates m2 = mf.buildModerates(p2, a);
		final Moderates m3 = mf.buildModerates(p1, a);
		final ModeratesEvent me1 = mef.build(EventCategory.ADDED, m1);
		final ModeratesEvent me2 = mef.build(EventCategory.ADDED, m2);
		final ModeratesEvent me3 = mef.build(EventCategory.ADDED, m3);

		assertThat(me1.toString(), is(not(equalTo(me2.toString()))));
		assertThat(me1.toString(), is(equalTo(me3.toString())));
	}

}
