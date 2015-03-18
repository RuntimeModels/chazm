package model.organization.entity;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
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
	private final CapabilityFactory capabilityFactory = injector.getInstance(CapabilityFactory.class);
	private final IdFactory idFactory = injector.getInstance(IdFactory.class);

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testCapability1() {
		final UniqueId<Capability> i1 = idFactory.buildId(Capability.class, 1);
		final Capability c1 = capabilityFactory.buildCapability(i1);

		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		assertThat("c1 == null", c1, notNullValue());
		capabilityFactory.buildCapability(null);
	}

	@Test
	public void testCapability2() {
		final UniqueId<Capability> i1 = idFactory.buildId(Capability.class, 1);
		final Capability c1 = capabilityFactory.buildCapability(i1);

		exception.expect(instanceOf(IllegalArgumentException.class));
		exception.expectMessage(equalTo("Parameter (id) cannot be null"));

		assertThat("c1 == null", c1, notNullValue());
		new CapabilityEntity(null);
	}

	@Test
	public void testGetId() {
		final UniqueId<Capability> i1 = idFactory.buildId(Capability.class, 1);
		final UniqueId<Capability> i2 = idFactory.buildId(Capability.class, 1);
		final Capability c1 = capabilityFactory.buildCapability(i1);
		final Capability c2 = capabilityFactory.buildCapability(i2);

		assertThat("i1.id != c1", i1, sameInstance(c1.getId()));
		assertThat("c1.id == c2.id", c1.getId(), not(sameInstance(c2.getId())));
	}

	@Test
	public void testEqualsObject() {
		final UniqueId<Capability> i1 = idFactory.buildId(Capability.class, 1);
		final UniqueId<Capability> i2 = idFactory.buildId(Capability.class, 2);
		final Capability c1 = capabilityFactory.buildCapability(i1);
		final Capability c2 = capabilityFactory.buildCapability(i2);
		final Capability c3 = capabilityFactory.buildCapability(i1);

		assertThat("i1.equals <=> c1.equals", i1, not(equalTo(c1)));
		assertThat("i2.equals <=> c2.equals", i2, not(equalTo(c2)));
		assertThat("i1.equals <=> i2.equals", i1, not(equalTo(i2)));
		assertThat("c1.equals <=> c2.equals", c1, not(equalTo(c2)));
		assertThat("c1 == c3", c1, not(sameInstance(c3)));
		assertThat("c1.equals <!> c3.equals", c1, equalTo(c3));
	}

	@Test
	public void testHashCode() {
		final UniqueId<Capability> i1 = idFactory.buildId(Capability.class, 1);
		final UniqueId<Capability> i2 = idFactory.buildId(Capability.class, 2);
		final Capability c1 = capabilityFactory.buildCapability(i1);
		final Capability c2 = capabilityFactory.buildCapability(i2);

		assertThat("i1.hashCode <!> c1.hashCode", i1.hashCode(), equalTo(c1.hashCode()));
		assertThat("i2.hashCode <!> c2.hashCode", i2.hashCode(), equalTo(c2.hashCode()));
		assertThat("i1.hashCode <=> i2.hashCode", i1.hashCode(), not(equalTo(i2.hashCode())));
		assertThat("c1.hashCode <=> c2.hashCode", c1.hashCode(), not(equalTo(c2.hashCode())));
	}

	@Test
	public void testToString() {
		final UniqueId<Capability> i1 = idFactory.buildId(Capability.class, 1);
		final UniqueId<Capability> i2 = idFactory.buildId(Capability.class, 2);
		final Capability c1 = capabilityFactory.buildCapability(i1);
		final Capability c2 = capabilityFactory.buildCapability(i2);

		assertThat("i1.toString != c1.toString", i1.toString(), equalTo(c1.toString()));
		assertThat("i2.toString != c2.toString", i2.toString(), equalTo(c2.toString()));
		assertThat("i1.toString == i2.toString", i1.toString(), not(equalTo(i2.toString())));
		assertThat("c1.toString == c2.toString", c1.toString(), not(equalTo(c2.toString())));
	}

}
