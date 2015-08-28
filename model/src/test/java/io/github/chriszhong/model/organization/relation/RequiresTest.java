package io.github.chriszhong.model.organization.relation;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import io.github.chriszhong.model.organization.entity.CapabilityFactory;
import io.github.chriszhong.model.organization.entity.RoleFactory;
import io.github.chriszhong.model.organization.id.IdFactory;
import io.github.runtimemodels.chazm.entity.Capability;
import io.github.runtimemodels.chazm.entity.Role;
import io.github.runtimemodels.chazm.relation.Requires;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;

@SuppressWarnings("javadoc")
public class RequiresTest {

	private final Injector injector = Guice.createInjector(new RelationModule());
	private final RequiresFactory requiresFactory = injector.getInstance(RequiresFactory.class);
	private final RoleFactory roleFactory = injector.getInstance(RoleFactory.class);
	private final CapabilityFactory capabilityFactory = injector.getInstance(CapabilityFactory.class);
	private final IdFactory idFactory = injector.getInstance(IdFactory.class);

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testRequires() {
		final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
		final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));
		final Requires rq1 = requiresFactory.buildRequires(r, c);
		final Requires rq2 = requiresFactory.buildRequires(r, c);

		assertThat(rq1, is(not(nullValue())));
		assertThat(rq1, is(not(sameInstance(rq2))));
	}

	@Test
	public void testRequires1() {
		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		requiresFactory.buildRequires(null, null);
	}

	@Test
	public void testRequires2() {
		final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));

		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		requiresFactory.buildRequires(r, null);
	}

	@Test
	public void testGetRole() {
		final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
		final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));
		final Requires rq = requiresFactory.buildRequires(r, c);

		assertThat(rq.getRole(), is(sameInstance(r)));
	}

	@Test
	public void testGetCapability() {
		final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
		final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));
		final Requires rq = requiresFactory.buildRequires(r, c);

		assertThat(rq.getCapability(), is(sameInstance(c)));
	}

	@Test
	public void testEquals() {
		final Role r1 = roleFactory.buildRole(idFactory.build(Role.class, "r1"));
		final Role r2 = roleFactory.buildRole(idFactory.build(Role.class, "r2"));
		final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));
		final Requires rq1 = requiresFactory.buildRequires(r1, c);
		final Requires rq2 = requiresFactory.buildRequires(r2, c);
		final Requires rq3 = requiresFactory.buildRequires(r1, c);

		assertThat(rq1, is(not(equalTo(rq2))));
		assertThat(rq1, is(equalTo(rq3)));
		assertThat(rq1, is(not(equalTo(""))));
	}

	@Test
	public void testHashCode() {
		final Role r1 = roleFactory.buildRole(idFactory.build(Role.class, "r1"));
		final Role r2 = roleFactory.buildRole(idFactory.build(Role.class, "r2"));
		final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));
		final Requires rq1 = requiresFactory.buildRequires(r1, c);
		final Requires rq2 = requiresFactory.buildRequires(r2, c);
		final Requires rq3 = requiresFactory.buildRequires(r1, c);

		assertThat(rq1.hashCode(), is(not(equalTo(rq2.hashCode()))));
		assertThat(rq1.hashCode(), is(equalTo(rq3.hashCode())));
	}

	@Test
	public void testToString() {
		final Role r1 = roleFactory.buildRole(idFactory.build(Role.class, "r1"));
		final Role r2 = roleFactory.buildRole(idFactory.build(Role.class, "r2"));
		final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));
		final Requires rq1 = requiresFactory.buildRequires(r1, c);
		final Requires rq2 = requiresFactory.buildRequires(r2, c);
		final Requires rq3 = requiresFactory.buildRequires(r1, c);

		assertThat(rq1.toString(), is(not(equalTo(rq2.toString()))));
		assertThat(rq1.toString(), is(equalTo(rq3.toString())));
	}

}
