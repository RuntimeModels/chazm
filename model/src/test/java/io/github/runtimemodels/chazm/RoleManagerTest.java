package io.github.runtimemodels.chazm;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import io.github.runtimemodels.chazm.Organization;
import io.github.runtimemodels.chazm.OrganizationModule;
import io.github.runtimemodels.chazm.entity.EntityFactory;
import io.github.runtimemodels.chazm.entity.Role;
import io.github.runtimemodels.chazm.id.IdFactory;
import io.github.runtimemodels.chazm.id.UniqueId;
import io.github.runtimemodels.message.E;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;

@SuppressWarnings("javadoc")
public class RoleManagerTest {

	private final Injector injector = Guice.createInjector(new OrganizationModule());
	private final Provider<Organization> provider = injector.getProvider(Organization.class);
	private final EntityFactory entityFactory = injector.getInstance(EntityFactory.class);
	private final IdFactory idFactory = injector.getInstance(IdFactory.class);

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testAddRole() {
		final Organization o = provider.get();
		final UniqueId<Role> i1 = idFactory.build(Role.class, "role1");
		final UniqueId<Role> i2 = idFactory.build(Role.class, "");
		final Role r1 = entityFactory.buildRole(i1);
		final Role r2 = entityFactory.buildRole(idFactory.build(Role.class, "role2"));
		assertThat(o.getRoles().size(), is(equalTo(0)));
		o.addRole(r1);
		assertThat(o.getRoles().size(), is(equalTo(1)));
		assertThat(o.getRole(i1), is(not(nullValue())));
		assertThat(o.getRole(i2), is(nullValue()));
		o.addRole(r2);
		assertThat(o.getRoles().size(), is(equalTo(2)));
	}

	@Test
	public void testAddRole1() {
		final Organization o = provider.get();
		exception.expect(is(instanceOf(IllegalArgumentException.class)));
		exception.expectMessage(equalTo(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "addRole")));
		o.addRole(null);
	}

	@Test
	public void testAddRole2() {
		final Organization o = provider.get();
		final UniqueId<Role> i1 = idFactory.build(Role.class, "role1");
		final Role r1 = entityFactory.buildRole(i1);
		final Role r2 = entityFactory.buildRole(i1);
		exception.expect(is(instanceOf(IllegalArgumentException.class)));
		exception.expectMessage(equalTo(E.ENTITY_ALREADY_EXISTS.get("Role", "role1")));
		assertThat(r1, is(not(sameInstance(r2))));
		o.addRole(r1);
		o.addRole(r2);
	}

}
