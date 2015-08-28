package io.github.runtimemodels.chazm.event;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import io.github.runtimemodels.chazm.entity.Attribute;
import io.github.runtimemodels.chazm.entity.AttributeFactory;
import io.github.runtimemodels.chazm.entity.Role;
import io.github.runtimemodels.chazm.entity.RoleFactory;
import io.github.runtimemodels.chazm.event.EventCategory;
import io.github.runtimemodels.chazm.event.EventModule;
import io.github.runtimemodels.chazm.event.NeedsEvent;
import io.github.runtimemodels.chazm.event.NeedsEventFactory;
import io.github.runtimemodels.chazm.id.IdFactory;
import io.github.runtimemodels.chazm.relation.Needs;
import io.github.runtimemodels.chazm.relation.NeedsFactory;
import io.github.runtimemodels.chazm.relation.RelationModule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;

@SuppressWarnings("javadoc")
public class NeedsEventTest {

	private final Injector injector = Guice.createInjector(new RelationModule(), new EventModule());
	private final NeedsEventFactory nef = injector.getInstance(NeedsEventFactory.class);
	private final NeedsFactory nf = injector.getInstance(NeedsFactory.class);
	private final RoleFactory rf = injector.getInstance(RoleFactory.class);
	private final AttributeFactory af = injector.getInstance(AttributeFactory.class);
	private final IdFactory idf = injector.getInstance(IdFactory.class);

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testNeedsEvent() {
		final Role r = rf.buildRole(idf.build(Role.class, "r"));
		final Attribute a = af.buildAttribute(idf.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
		final Needs n = nf.buildNeeds(r, a);
		final NeedsEvent ne1 = nef.build(EventCategory.ADDED, n);
		final NeedsEvent ne2 = nef.build(EventCategory.ADDED, n);

		assertThat(ne1, is(not(nullValue())));
		assertThat(ne1, is(not(sameInstance(ne2))));
	}

	@Test
	public void testNeedsEvent1() {
		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		nef.build(null, null);
	}

	@Test
	public void testNeedsEvent2() {
		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		nef.build(EventCategory.ADDED, null);
	}

	@Test
	public void testGetRoleId() {
		final Role r = rf.buildRole(idf.build(Role.class, "r"));
		final Attribute a = af.buildAttribute(idf.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
		final Needs n = nf.buildNeeds(r, a);
		final NeedsEvent ne = nef.build(EventCategory.ADDED, n);

		assertThat(ne.getRoleId(), is(sameInstance(r.getId())));
	}

	@Test
	public void testGetAttributeId() {
		final Role r = rf.buildRole(idf.build(Role.class, "r"));
		final Attribute a = af.buildAttribute(idf.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
		final Needs n = nf.buildNeeds(r, a);
		final NeedsEvent ne = nef.build(EventCategory.ADDED, n);

		assertThat(ne.getAttributeId(), is(sameInstance(a.getId())));
	}

	@Test
	public void testEquals() {
		final Role r1 = rf.buildRole(idf.build(Role.class, "r1"));
		final Role r2 = rf.buildRole(idf.build(Role.class, "r2"));
		final Attribute a = af.buildAttribute(idf.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
		final Needs n1 = nf.buildNeeds(r1, a);
		final Needs n2 = nf.buildNeeds(r2, a);
		final Needs n3 = nf.buildNeeds(r1, a);
		final NeedsEvent ne1 = nef.build(EventCategory.ADDED, n1);
		final NeedsEvent ne2 = nef.build(EventCategory.ADDED, n2);
		final NeedsEvent ne3 = nef.build(EventCategory.ADDED, n3);

		assertThat(ne1, is(not(equalTo(ne2))));
		assertThat(ne1, is(equalTo(ne3)));
		assertThat(ne1, is(not(equalTo(""))));
	}

	@Test
	public void testNeedshCode() {
		final Role r1 = rf.buildRole(idf.build(Role.class, "r1"));
		final Role r2 = rf.buildRole(idf.build(Role.class, "r2"));
		final Attribute a = af.buildAttribute(idf.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
		final Needs n1 = nf.buildNeeds(r1, a);
		final Needs n2 = nf.buildNeeds(r2, a);
		final Needs n3 = nf.buildNeeds(r1, a);
		final NeedsEvent ne1 = nef.build(EventCategory.ADDED, n1);
		final NeedsEvent ne2 = nef.build(EventCategory.ADDED, n2);
		final NeedsEvent ne3 = nef.build(EventCategory.ADDED, n3);

		assertThat(ne1.hashCode(), is(not(equalTo(ne2.hashCode()))));
		assertThat(ne1.hashCode(), is(equalTo(ne3.hashCode())));
	}

	@Test
	public void testToString() {
		final Role r1 = rf.buildRole(idf.build(Role.class, "r1"));
		final Role r2 = rf.buildRole(idf.build(Role.class, "r2"));
		final Attribute a = af.buildAttribute(idf.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
		final Needs n1 = nf.buildNeeds(r1, a);
		final Needs n2 = nf.buildNeeds(r2, a);
		final Needs n3 = nf.buildNeeds(r1, a);
		final NeedsEvent ne1 = nef.build(EventCategory.ADDED, n1);
		final NeedsEvent ne2 = nef.build(EventCategory.ADDED, n2);
		final NeedsEvent ne3 = nef.build(EventCategory.ADDED, n3);

		assertThat(ne1.toString(), is(not(equalTo(ne2.toString()))));
		assertThat(ne1.toString(), is(equalTo(ne3.toString())));
	}

}
