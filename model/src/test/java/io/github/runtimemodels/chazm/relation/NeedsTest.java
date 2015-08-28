package io.github.runtimemodels.chazm.relation;

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
public class NeedsTest {

	private final Injector injector = Guice.createInjector(new RelationModule());
	private final NeedsFactory needsFactory = injector.getInstance(NeedsFactory.class);
	private final RoleFactory roleFactory = injector.getInstance(RoleFactory.class);
	private final AttributeFactory attributeFactory = injector.getInstance(AttributeFactory.class);
	private final IdFactory idFactory = injector.getInstance(IdFactory.class);

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testNeeds() {
		final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
		final Attribute a = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
		final Needs nd1 = needsFactory.buildNeeds(r, a);
		final Needs nd2 = needsFactory.buildNeeds(r, a);

		assertThat(nd1, is(not(nullValue())));
		assertThat(nd1, is(not(sameInstance(nd2))));
	}

	@Test
	public void testNeeds1() {
		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		needsFactory.buildNeeds(null, null);
	}

	@Test
	public void testNeeds2() {
		final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));

		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		needsFactory.buildNeeds(r, null);
	}

	@Test
	public void testGetRole() {
		final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
		final Attribute a = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
		final Needs nd = needsFactory.buildNeeds(r, a);

		assertThat(nd.getRole(), is(sameInstance(r)));
	}

	@Test
	public void testGetAttribute() {
		final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
		final Attribute a = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
		final Needs nd = needsFactory.buildNeeds(r, a);

		assertThat(nd.getAttribute(), is(sameInstance(a)));
	}

	@Test
	public void testEquals() {
		final Role r1 = roleFactory.buildRole(idFactory.build(Role.class, "r1"));
		final Role r2 = roleFactory.buildRole(idFactory.build(Role.class, "r2"));
		final Attribute a = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
		final Needs nd1 = needsFactory.buildNeeds(r1, a);
		final Needs nd2 = needsFactory.buildNeeds(r2, a);
		final Needs nd3 = needsFactory.buildNeeds(r1, a);

		assertThat(nd1, is(not(equalTo(nd2))));
		assertThat(nd1, is(equalTo(nd3)));
		assertThat(nd1, is(not(equalTo(""))));
	}

	@Test
	public void testHashCode() {
		final Role r1 = roleFactory.buildRole(idFactory.build(Role.class, "r1"));
		final Role r2 = roleFactory.buildRole(idFactory.build(Role.class, "r2"));
		final Attribute a = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
		final Needs nd1 = needsFactory.buildNeeds(r1, a);
		final Needs nd2 = needsFactory.buildNeeds(r2, a);
		final Needs nd3 = needsFactory.buildNeeds(r1, a);

		assertThat(nd1.hashCode(), is(not(equalTo(nd2.hashCode()))));
		assertThat(nd1.hashCode(), is(equalTo(nd3.hashCode())));
	}

	@Test
	public void testToString() {
		final Role r1 = roleFactory.buildRole(idFactory.build(Role.class, "r1"));
		final Role r2 = roleFactory.buildRole(idFactory.build(Role.class, "r2"));
		final Attribute a = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
		final Needs nd1 = needsFactory.buildNeeds(r1, a);
		final Needs nd2 = needsFactory.buildNeeds(r2, a);
		final Needs nd3 = needsFactory.buildNeeds(r1, a);

		assertThat(nd1.toString(), is(not(equalTo(nd2.toString()))));
		assertThat(nd1.toString(), is(equalTo(nd3.toString())));
	}

}
