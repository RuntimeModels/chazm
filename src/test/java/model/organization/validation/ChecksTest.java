package model.organization.validation;

import static model.organization.validation.Checks.checkExists;
import static model.organization.validation.Checks.checkNotExists;
import static model.organization.validation.Checks.checkNotNull;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import model.organization.entity.Capability;
import model.organization.entity.EntityFactory;
import model.organization.entity.EntityModule;
import model.organization.entity.Role;
import model.organization.id.IdFactory;
import model.organization.id.IdModule;
import model.organization.id.UniqueId;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.inject.Guice;
import com.google.inject.Injector;

@SuppressWarnings("javadoc")
public class ChecksTest {

	private final Injector injector = Guice.createInjector(new IdModule(), new EntityModule());
	private final IdFactory idFactory = injector.getInstance(IdFactory.class);
	private final EntityFactory entityFactory = injector.getInstance(EntityFactory.class);

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testChecks() {
		assertThat(new Checks(), is(not(nullValue())));
	}

	@Test
	public void testCheckNotNull() {
		checkNotNull("", "");
	}

	@Test
	public void testCheckNotNull1() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(equalTo("Parameter () cannot be null"));

		checkNotNull(null, "");
	}

	@Test
	public void testCheckNotNull2() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(equalTo("Parameter (<parameter>) cannot be null"));

		checkNotNull(null, null);
	}

	@Test
	public void testCheckNotExists() {
		final UniqueId<Capability> i1 = idFactory.buildId(Capability.class, 1L);
		final Capability c1 = entityFactory.buildCapability(i1);

		checkNotExists(c1, "i1", p -> false);
	}

	@Test
	public void testCheckNotExists1() {
		final UniqueId<Role> i1 = idFactory.buildId(Role.class, 1L);
		final Role r1 = entityFactory.buildRole(i1);

		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(equalTo("([Role]) entity (1) already exists"));

		checkNotExists(r1, "i1", p -> true);
	}

	@Test
	public void testCheckNotExists2() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(equalTo("Parameter (<parameter>) cannot be null"));

		checkNotExists(null, null, null);
	}

	@Test
	public void testCheckNotExists3() {
		final UniqueId<Role> i1 = idFactory.buildId(Role.class, 1L);
		final Role r1 = entityFactory.buildRole(i1);

		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(equalTo("Parameter (p) cannot be null"));

		checkNotExists(r1, "r1", null);
	}

	@Test
	public void testCheckExists() {
		final UniqueId<Role> i1 = idFactory.buildId(Role.class, 1L);
		final Role r1 = entityFactory.buildRole(i1);

		checkExists(i1, "i1", f -> r1);
	}

	@Test
	public void testCheckExists1() {
		final UniqueId<Role> i1 = idFactory.buildId(Role.class, 1L);

		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(equalTo("(Role) entity (1) does not exists"));

		checkExists(i1, "i1", f -> null);
	}

	@Test
	public void testCheckExists2() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(equalTo("Parameter (<parameter>) cannot be null"));

		checkExists(null, null, null);
	}

	@Test
	public void testCheckExists3() {
		final UniqueId<Role> i1 = idFactory.buildId(Role.class, 1L);

		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(equalTo("Parameter (f) cannot be null"));

		checkExists(i1, "i1", null);
	}

}
