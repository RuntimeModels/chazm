package model.organization.entity;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import model.organization.id.IdFactory;
import model.organization.id.IdModule;
import model.organization.id.UniqueId;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;

@SuppressWarnings("javadoc")
public class RoleTest {

	private final Injector injector = Guice.createInjector(new EntityModule(), new IdModule());
	private final RoleFactory roleFactory = injector.getInstance(RoleFactory.class);
	private final IdFactory idFactory = injector.getInstance(IdFactory.class);

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testRole() {
		final UniqueId<Role> i1 = idFactory.build(Role.class, 1L);
		final Role r1 = roleFactory.buildRole(i1);
		assertThat(r1, is(not(nullValue())));
	}

	@Test
	public void testRole1() {
		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));
		roleFactory.buildRole(null);
	}

	@Test
	public void testRole2() {
		exception.expect(instanceOf(IllegalArgumentException.class));
		exception.expectMessage(equalTo("Parameter (id) cannot be null"));
		new RoleEntity(null);
	}

	@Test
	public void testGetId() {
		final UniqueId<Role> i1 = idFactory.build(Role.class, 1L);
		final UniqueId<Role> i2 = idFactory.build(Role.class, 1L);
		final Role r1 = roleFactory.buildRole(i1);
		final Role r2 = roleFactory.buildRole(i2);

		assertThat(r1.getId(), is(sameInstance(i1)));
		assertThat(r1.getId(), is(not(sameInstance(r2.getId()))));
	}

	@Test
	public void testEqualsObject() {
		final UniqueId<Role> i1 = idFactory.build(Role.class, 1L);
		final UniqueId<Role> i2 = idFactory.build(Role.class, 2L);
		final Role r1 = roleFactory.buildRole(i1);
		final Role r2 = roleFactory.buildRole(i2);
		final Role r3 = roleFactory.buildRole(i1);

		assertThat(r1, is(not(equalTo(i1))));
		assertThat(r2, is(not(equalTo(i2))));
		assertThat(r1, is(not(equalTo(r2))));
		assertThat(r1, is(not(sameInstance(r3))));
		assertThat(r1, is(equalTo(r3)));
		assertThat(r1, is(not(equalTo(""))));
	}

	@Test
	public void testHashCode() {
		final UniqueId<Role> i1 = idFactory.build(Role.class, 1L);
		final UniqueId<Role> i2 = idFactory.build(Role.class, 2L);
		final Role r1 = roleFactory.buildRole(i1);
		final Role r2 = roleFactory.buildRole(i2);

		assertThat(r1.hashCode(), is(equalTo(i1.hashCode())));
		assertThat(r2.hashCode(), is(equalTo(i2.hashCode())));
		assertThat(r1.hashCode(), is(not(equalTo(r2.hashCode()))));
	}

	@Test
	public void testToString() {
		final UniqueId<Role> i1 = idFactory.build(Role.class, 1L);
		final UniqueId<Role> i2 = idFactory.build(Role.class, 2L);
		final Role r1 = roleFactory.buildRole(i1);
		final Role r2 = roleFactory.buildRole(i2);

		assertThat(r1.toString(), is(equalTo(i1.toString())));
		assertThat(r2.toString(), is(equalTo(i2.toString())));
		assertThat(r1.toString(), is(not(equalTo(r2.toString()))));
	}

}
