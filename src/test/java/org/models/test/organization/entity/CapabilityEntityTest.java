package org.models.test.organization.entity;

import org.junit.Assert;
import org.junit.Test;
import org.models.organization.entity.Capability;
import org.models.organization.entity.CapabilityEntity;
import org.models.organization.id.IntId;
import org.models.organization.id.UniqueId;

@SuppressWarnings("javadoc")
public class CapabilityEntityTest {

	@Test(expected = IllegalArgumentException.class)
	public void testCapabilityEntity() {
		final UniqueId<Capability> id1 = new IntId<>(Capability.class, 1);
		final Capability capability1 = new CapabilityEntity(id1);
		Assert.assertNotNull("capability1 is null", capability1);
		new CapabilityEntity(null);
	}

	@Test
	public void testHashCode() {
		final UniqueId<Capability> id1 = new IntId<>(Capability.class, 1);
		final Capability capability1 = new CapabilityEntity(id1);
		Assert.assertEquals("id1's hash code is not the same capability1's hash code", id1.hashCode(), capability1.hashCode());
		final UniqueId<Capability> id2 = new IntId<>(Capability.class, 2);
		Assert.assertNotEquals("id1's hash code is the same as id2's hash code", id1.hashCode(), id2.hashCode());
		final Capability capability2 = new CapabilityEntity(id2);
		Assert.assertNotEquals("capability1's hash code is the same as capability2's hash code", capability1.hashCode(), capability2.hashCode());
	}

	@Test
	public void testGetId() {
		final UniqueId<Capability> id1 = new IntId<>(Capability.class, 1);
		final Capability capability1 = new CapabilityEntity(id1);
		Assert.assertSame("id1 is not the same instance as capability1's id", id1, capability1.getId());
		final UniqueId<Capability> id2 = new IntId<>(Capability.class, 1);
		final Capability capability2 = new CapabilityEntity(id2);
		Assert.assertNotSame("capability1's id is the same instance as capability2's id", capability1.getId(), capability2.getId());
	}

	@Test
	public void testEqualsObject() {
		final UniqueId<Capability> id1 = new IntId<>(Capability.class, 1);
		final Capability capability1 = new CapabilityEntity(id1);
		final UniqueId<Capability> id2 = new IntId<>(Capability.class, 1);
		final Capability capability2 = new CapabilityEntity(id2);
		Assert.assertEquals("capability1 is not equals to capability2", capability1, capability2);
		final UniqueId<Capability> id3 = new IntId<>(Capability.class, 2);
		final Capability capability3 = new CapabilityEntity(id3);
		Assert.assertNotEquals("capability1 is equals to capability3", capability1, capability3);
	}

	@Test
	public void testToString() {
		final UniqueId<Capability> id1 = new IntId<>(Capability.class, 1);
		final Capability capability1 = new CapabilityEntity(id1);
		Assert.assertEquals("id1's toString is not equals to capability1's toString", id1.toString(), capability1.toString());
		final UniqueId<Capability> id2 = new IntId<>(Capability.class, 2);
		final Capability capability2 = new CapabilityEntity(id2);
		Assert.assertNotEquals("capability1's toString is equals to capability2's toString", capability1, capability2);
	}

}
