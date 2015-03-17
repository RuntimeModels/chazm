package model.organization.entity;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
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
public class CapabilityTest {

	private final Injector injector = Guice.createInjector(new EntityModule(), new IdModule());
	private final EntityFactory entityFactory = injector.getInstance(EntityFactory.class);
	private final IdFactory idFactory = injector.getInstance(IdFactory.class);

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testCapability() {
		final UniqueId<Capability> i1 = idFactory.buildId(Capability.class, 1);
		final Capability c1 = entityFactory.buildCapability(i1);

		exception.expect(ProvisionException.class);
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		assertThat("c1 == null", c1, is(notNullValue()));
		entityFactory.buildCapability(null);
	}

	@Test
	public void testGetId() {
		final UniqueId<Capability> i1 = idFactory.buildId(Capability.class, 1);
		final UniqueId<Capability> i2 = idFactory.buildId(Capability.class, 1);
		final Capability c1 = entityFactory.buildCapability(i1);
		final Capability c2 = entityFactory.buildCapability(i2);

		assertThat("i1.id != c1", i1, is(sameInstance(c1.getId())));
		assertThat("c1.id == c2.id", c1.getId(), is(not(sameInstance(c2.getId()))));
	}

	@Test
	public void testEqualsObject() {
		final UniqueId<Capability> i1 = idFactory.buildId(Capability.class, 1);
		final UniqueId<Capability> i2 = idFactory.buildId(Capability.class, 2);
		final Capability c1 = entityFactory.buildCapability(i1);
		final Capability c2 = entityFactory.buildCapability(i2);
		final Capability c3 = entityFactory.buildCapability(i1);

		assertThat("i1.equals == c1.equals", i1, is(not(c1)));
		assertThat("i2.equals == c2.equals", i2, is(not(c2)));
		assertThat("i1.equals == i2.equals", i1, is(not(i2)));
		assertThat("c1.equals == c2.equals", c1, is(not(c2)));
		assertThat("c1 == c3", c1, is(not(sameInstance(c3))));
		assertThat("c1.equals == c3.equals", c1, is(c3));
	}

	@Test
	public void testHashCode() {
		final UniqueId<Capability> i1 = idFactory.buildId(Capability.class, 1);
		final UniqueId<Capability> i2 = idFactory.buildId(Capability.class, 2);
		final Capability c1 = entityFactory.buildCapability(i1);
		final Capability c2 = entityFactory.buildCapability(i2);

		assertThat("i1.hashCode != c1.hashCode", i1.hashCode(), is(c1.hashCode()));
		assertThat("i2.hashCode != c2.hashCode", i2.hashCode(), is(c2.hashCode()));
		assertThat("i1.hashCode == i2.hashCode", i1.hashCode(), is(not(i2.hashCode())));
		assertThat("c1.hashCode == c2.hashCode", c1.hashCode(), is(not(c2.hashCode())));
	}

	@Test
	public void testToString() {
		final UniqueId<Capability> i1 = idFactory.buildId(Capability.class, 1);
		final UniqueId<Capability> i2 = idFactory.buildId(Capability.class, 2);
		final Capability c1 = entityFactory.buildCapability(i1);
		final Capability c2 = entityFactory.buildCapability(i2);

		assertThat("i1.toString != c1.toString", i1.toString(), is(c1.toString()));
		assertThat("i2.toString != c2.toString", i2.toString(), is(c2.toString()));
		assertThat("i1.toString == i2.toString", i1.toString(), is(not(i2.toString())));
		assertThat("c1.toString == c2.toString", c1.toString(), is(not(c2.toString())));
	}

}
