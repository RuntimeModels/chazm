package model.organization.id;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.inject.Guice;
import com.google.inject.Injector;

@SuppressWarnings("javadoc")
public class StringIdTest {

	private final Injector injector = Guice.createInjector(new IdModule());
	private final IdFactory idFactory = injector.getInstance(IdFactory.class);

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testStringId() {
		final UniqueId<Object> i1 = idFactory.buildId(Object.class, "");
		assertThat("i1 == null", i1, not(nullValue()));
		assertThat("i1 is not an instance of StringId", i1, instanceOf(StringId.class));
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(equalTo("Parameter (id) cannot be null"));
		idFactory.buildId(Object.class, (String) null);
	}

	@Test
	public void testGetType() {
		final UniqueId<Object> i1 = idFactory.buildId(Object.class, "");
		final UniqueId<String> i2 = idFactory.buildId(String.class, "");
		final UniqueId<Double> i3 = idFactory.buildId(Double.class, "");

		assertThat("i1.type != Object.class", i1.getType(), equalTo(Object.class));
		assertThat("i2.type != String.class", i2.getType(), equalTo(String.class));
		assertThat("i3.type != Double.class", i3.getType(), equalTo(Double.class));
	}

	@Test
	public void testHashCode() {
		final UniqueId<Object> i1 = idFactory.buildId(Object.class, "r1");
		final UniqueId<Object> i2 = idFactory.buildId(Object.class, "r1");
		final UniqueId<Object> i3 = idFactory.buildId(Object.class, "r3");

		assertThat("i1 == i2", i1, not(sameInstance(i2)));
		assertThat("i1 == i3", i1, not(sameInstance(i3)));
		assertThat("i2 == i3", i2, not(sameInstance(i3)));

		assertThat("i1.hashCode != i2.hashCode", i1.hashCode(), equalTo(i2.hashCode()));
		assertThat("i1.hashCode == i3.hashCode", i1.hashCode(), not(equalTo(i3.hashCode())));
	}

	@Test
	public void testEqualsObject() {
		final UniqueId<Object> i1 = idFactory.buildId(Object.class, "r1");
		final UniqueId<Object> i2 = idFactory.buildId(Object.class, "r1");
		final UniqueId<Object> i3 = idFactory.buildId(Object.class, "r3");

		assertThat("i1 == i2", i1, not(sameInstance(i2)));
		assertThat("i1 == i3", i1, not(sameInstance(i3)));
		assertThat("i2 == i3", i2, not(sameInstance(i3)));

		assertThat("i1.equals != i2.equals", i1, equalTo(i2));
		assertThat("i1.equals == i3.equals", i1, not(equalTo(i3)));
	}

	@Test
	public void testToString() {
		final UniqueId<Object> i1 = idFactory.buildId(Object.class, "r1");
		final UniqueId<Object> i2 = idFactory.buildId(Object.class, "r1");
		final UniqueId<Object> i3 = idFactory.buildId(Object.class, "r3");

		assertThat("i1 == i2", i1, not(sameInstance(i2)));
		assertThat("i1 == i3", i1, not(sameInstance(i3)));
		assertThat("i2 == i3", i2, not(sameInstance(i3)));

		assertThat("i1.toString != i2.toString", i1.toString(), equalTo(i2.toString()));
		assertThat("i1.toString == i3.toString", i1.toString(), not(equalTo(i3.toString())));
	}

}