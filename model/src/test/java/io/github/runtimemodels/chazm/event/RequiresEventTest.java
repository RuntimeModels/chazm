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
import io.github.runtimemodels.chazm.entity.Capability;
import io.github.runtimemodels.chazm.entity.CapabilityFactory;
import io.github.runtimemodels.chazm.entity.Role;
import io.github.runtimemodels.chazm.entity.RoleFactory;
import io.github.runtimemodels.chazm.id.IdFactory;
import io.github.runtimemodels.chazm.relation.RelationModule;
import io.github.runtimemodels.chazm.relation.Requires;
import io.github.runtimemodels.chazm.relation.RequiresFactory;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;

@SuppressWarnings("javadoc")
public class RequiresEventTest {

	private final Injector injector = Guice.createInjector(new RelationModule(), new EventModule());
	private final RequiresEventFactory ref = injector.getInstance(RequiresEventFactory.class);
	private final RequiresFactory rf = injector.getInstance(RequiresFactory.class);
	private final RoleFactory rrf = injector.getInstance(RoleFactory.class);
	private final CapabilityFactory cf = injector.getInstance(CapabilityFactory.class);
	private final IdFactory idf = injector.getInstance(IdFactory.class);

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testRequiresEvent() {
		final Role rr = rrf.buildRole(idf.build(Role.class, "rr"));
		final Capability c = cf.buildCapability(idf.build(Capability.class, "c"));
		final Requires r = rf.buildRequires(rr, c);
		final RequiresEvent re1 = ref.build(EventCategory.ADDED, r);
		final RequiresEvent re2 = ref.build(EventCategory.ADDED, r);

		assertThat(re1, is(not(nullValue())));
		assertThat(re1, is(not(sameInstance(re2))));
	}

	@Test
	public void testRequiresEvent1() {
		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		ref.build(null, null);
	}

	@Test
	public void testRequiresEvent2() {
		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		ref.build(EventCategory.ADDED, null);
	}

	@Test
	public void testGetRoleId() {
		final Role rr = rrf.buildRole(idf.build(Role.class, "rr"));
		final Capability c = cf.buildCapability(idf.build(Capability.class, "c"));
		final Requires r = rf.buildRequires(rr, c);
		final RequiresEvent re = ref.build(EventCategory.ADDED, r);

		assertThat(re.getRoleId(), is(sameInstance(rr.getId())));
	}

	@Test
	public void testGetCapabilityId() {
		final Role rr = rrf.buildRole(idf.build(Role.class, "rr"));
		final Capability c = cf.buildCapability(idf.build(Capability.class, "c"));
		final Requires r = rf.buildRequires(rr, c);
		final RequiresEvent re = ref.build(EventCategory.ADDED, r);

		assertThat(re.getCapabilityId(), is(sameInstance(c.getId())));
	}

	@Test
	public void testEquals() {
		final Role rr1 = rrf.buildRole(idf.build(Role.class, "rr1"));
		final Role rr2 = rrf.buildRole(idf.build(Role.class, "rr2"));
		final Capability c = cf.buildCapability(idf.build(Capability.class, "c"));
		final Requires r1 = rf.buildRequires(rr1, c);
		final Requires r2 = rf.buildRequires(rr2, c);
		final Requires r3 = rf.buildRequires(rr1, c);
		final RequiresEvent re1 = ref.build(EventCategory.ADDED, r1);
		final RequiresEvent re2 = ref.build(EventCategory.ADDED, r2);
		final RequiresEvent re3 = ref.build(EventCategory.ADDED, r3);

		assertThat(re1, is(not(equalTo(re2))));
		assertThat(re1, is(equalTo(re3)));
		assertThat(re1, is(not(equalTo(""))));
	}

	@Test
	public void testRequireshCode() {
		final Role rr1 = rrf.buildRole(idf.build(Role.class, "rr1"));
		final Role rr2 = rrf.buildRole(idf.build(Role.class, "rr2"));
		final Capability c = cf.buildCapability(idf.build(Capability.class, "c"));
		final Requires r1 = rf.buildRequires(rr1, c);
		final Requires r2 = rf.buildRequires(rr2, c);
		final Requires r3 = rf.buildRequires(rr1, c);
		final RequiresEvent re1 = ref.build(EventCategory.ADDED, r1);
		final RequiresEvent re2 = ref.build(EventCategory.ADDED, r2);
		final RequiresEvent re3 = ref.build(EventCategory.ADDED, r3);

		assertThat(re1.hashCode(), is(not(equalTo(re2.hashCode()))));
		assertThat(re1.hashCode(), is(equalTo(re3.hashCode())));
	}

	@Test
	public void testToString() {
		final Role rr1 = rrf.buildRole(idf.build(Role.class, "rr1"));
		final Role rr2 = rrf.buildRole(idf.build(Role.class, "rr2"));
		final Capability c = cf.buildCapability(idf.build(Capability.class, "c"));
		final Requires r1 = rf.buildRequires(rr1, c);
		final Requires r2 = rf.buildRequires(rr2, c);
		final Requires r3 = rf.buildRequires(rr1, c);
		final RequiresEvent re1 = ref.build(EventCategory.ADDED, r1);
		final RequiresEvent re2 = ref.build(EventCategory.ADDED, r2);
		final RequiresEvent re3 = ref.build(EventCategory.ADDED, r3);

		assertThat(re1.toString(), is(not(equalTo(re2.toString()))));
		assertThat(re1.toString(), is(equalTo(re3.toString())));
	}

}
