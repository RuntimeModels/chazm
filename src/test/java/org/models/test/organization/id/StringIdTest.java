package org.models.test.organization.id;

import org.junit.Assert;
import org.junit.Test;
import org.models.organization.id.StringId;

@SuppressWarnings("javadoc")
public class StringIdTest {

	@Test
	public void testHashCode() {
		StringId<Object> id1 = new StringId<Object>("r1");
		StringId<Object> id2 = new StringId<Object>("r1");
		Assert.assertEquals("id1's hash code should be the same as id2's hash code", id1.hashCode(), id2.hashCode());
		StringId<Object> id3 = new StringId<Object>("r3");
		Assert.assertNotEquals("id1's hash code should be different from id3's hash code", id1.hashCode(), id3.hashCode());
	}

	@Test
	public void testEqualsObject() {
		StringId<Object> id1 = new StringId<Object>("r1");
		StringId<Object> id2 = new StringId<Object>("r1");
		Assert.assertEquals("id1 should be the same as id2", id1, id2);
		StringId<Object> id3 = new StringId<Object>("r3");
		Assert.assertNotEquals("id1 should be different from id3", id1, id3);
	}

	@Test
	public void testToString() {
		StringId<Object> id1 = new StringId<Object>("r1");
		StringId<Object> id2 = new StringId<Object>("r1");
		Assert.assertEquals("id1's string should be the same as id2's string", id1.toString(), id2.toString());
		StringId<Object> id3 = new StringId<Object>("r3");
		Assert.assertNotEquals("id1's string should be different from id3's string", id1.toString(), id3.toString());
	}

	@Test
	public void testStringId() {
		StringId<Object> id1 = new StringId<Object>("");
		Assert.assertNotNull("id1 should not be null", id1);
		try {
			StringId<Object> id2 = new StringId<Object>(null);
			Assert.assertNull("id2 should be null", id2);
		} catch (IllegalArgumentException e) {
		}
	}

}
