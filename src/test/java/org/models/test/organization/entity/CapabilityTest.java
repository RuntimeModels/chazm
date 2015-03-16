package org.models.test.organization.entity;

import model.organization.entity.Capability;
import model.organization.entity.EntityFactory;
import model.organization.entity.EntityModule;
import model.organization.id.UniqueId;
import model.organization.ids.IntId;

import org.junit.Assert;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

@SuppressWarnings("javadoc")
public class CapabilityTest {

	private Injector injector = Guice.createInjector(new EntityModule());
	private EntityFactory entityFactory = injector.getInstance(EntityFactory.class);

	@Test
	public void testCapabilityEntity() {
		final UniqueId<Capability> i1 = new IntId<>(Capability.class, 1);
		final Capability c1 = entityFactory.buildCapability(i1);
		Assert.assertNotNull("c1 is null", c1);
		try {
			entityFactory.buildCapability(null);
			Assert.fail("IllegalArgumentException should have been thrown");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testHashCode() {
		final UniqueId<Capability> i1 = new IntId<>(Capability.class, 1);
		final Capability c1 = entityFactory.buildCapability(i1);
		Assert.assertEquals("i1's hash code is not the same c1's hash code", i1.hashCode(), c1.hashCode());
		final UniqueId<Capability> i2 = new IntId<>(Capability.class, 2);
		Assert.assertNotEquals("i1's hash code is the same as i2's hash code", i1.hashCode(), i2.hashCode());
		final Capability c2 = entityFactory.buildCapability(i2);
		Assert.assertNotEquals("c1's hash code is the same as c2's hash code", c1.hashCode(), c2.hashCode());
	}

	@Test
	public void testGetId() {
		final UniqueId<Capability> i1 = new IntId<>(Capability.class, 1);
		final Capability c1 = entityFactory.buildCapability(i1);
		Assert.assertSame("i1 is not the same instance as c1's id", i1, c1.getId());
		final UniqueId<Capability> i2 = new IntId<>(Capability.class, 1);
		final Capability c2 = entityFactory.buildCapability(i2);
		Assert.assertNotSame("c1's id is the same instance as c2's id", c1.getId(), c2.getId());
	}

	@Test
	public void testEqualsObject() {
		final UniqueId<Capability> i1 = new IntId<>(Capability.class, 1);
		final Capability c1 = entityFactory.buildCapability(i1);
		final UniqueId<Capability> i2 = new IntId<>(Capability.class, 1);
		final Capability c2 = entityFactory.buildCapability(i2);
		Assert.assertEquals("c1 is not equals to c2", c1, c2);
		final UniqueId<Capability> i3 = new IntId<>(Capability.class, 2);
		final Capability c3 = entityFactory.buildCapability(i3);
		Assert.assertNotEquals("c1 is equals to c3", c1, c3);
	}

	@Test
	public void testToString() {
		final UniqueId<Capability> i1 = new IntId<>(Capability.class, 1);
		final Capability c1 = entityFactory.buildCapability(i1);
		Assert.assertEquals("i1's toString is not equals to c1's toString", i1.toString(), c1.toString());
		final UniqueId<Capability> i2 = new IntId<>(Capability.class, 2);
		final Capability c2 = entityFactory.buildCapability(i2);
		Assert.assertNotEquals("c1's toString is equals to c2's toString", c1, c2);
	}

}
