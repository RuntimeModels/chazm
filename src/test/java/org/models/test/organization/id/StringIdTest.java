package org.models.test.organization.id;

import model.organization.id.StringId;

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class StringIdTest {

	@Test
	public void testHashCode() {
		final StringId<Object> id1 = new StringId<>(Object.class, "r1");
		final StringId<Object> id2 = new StringId<>(Object.class, "r1");
		Assert.assertEquals("id1's hash code should be the same as id2's hash code", id1.hashCode(), id2.hashCode());
		final StringId<Object> id3 = new StringId<>(Object.class, "r3");
		Assert.assertNotEquals("id1's hash code should be different from id3's hash code", id1.hashCode(), id3.hashCode());
	}

	@Test
	public void testEqualsObject() {
		final StringId<Object> id1 = new StringId<>(Object.class, "r1");
		final StringId<Object> id2 = new StringId<>(Object.class, "r1");
		Assert.assertEquals("id1 should be the same as id2", id1, id2);
		final StringId<Object> id3 = new StringId<>(Object.class, "r3");
		Assert.assertNotEquals("id1 should be different from id3", id1, id3);
	}

	@Test
	public void testToString() {
		final StringId<Object> id1 = new StringId<>(Object.class, "r1");
		final StringId<Object> id2 = new StringId<>(Object.class, "r1");
		Assert.assertEquals("id1's string should be the same as id2's string", id1.toString(), id2.toString());
		final StringId<Object> id3 = new StringId<>(Object.class, "r3");
		Assert.assertNotEquals("id1's string should be different from id3's string", id1.toString(), id3.toString());
	}

	@Test
	public void testStringId() {
		final StringId<Object> id1 = new StringId<>(Object.class, "");
		Assert.assertNotNull("id1 should not be null", id1);
		try {
			final StringId<Object> id2 = new StringId<>(Object.class, null);
			Assert.assertNull("id2 should be null", id2);
		} catch (final IllegalArgumentException e) {
		}
	}

}
