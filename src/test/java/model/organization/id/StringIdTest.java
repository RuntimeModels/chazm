package model.organization.id;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
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
		assertThat("i1 == null", i1, is(notNullValue()));
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(is("Parameter (id) cannot be null"));
		idFactory.buildId(Object.class, (String) null);
	}

	@Test
	public void testHashCode() {
		final UniqueId<Object> i1 = idFactory.buildId(Object.class, "r1");
		final UniqueId<Object> i2 = idFactory.buildId(Object.class, "r1");
		final UniqueId<Object> i3 = idFactory.buildId(Object.class, "r3");

		assertThat("i1 == i2", i1, is(not(sameInstance(i2))));
		assertThat("i1 == i3", i1, is(not(sameInstance(i3))));
		assertThat("i2 == i3", i2, is(not(sameInstance(i3))));

		assertThat("i1.hashCode != i2.hashCode", i1.hashCode(), is(i2.hashCode()));
		assertThat("i1.hashCode == i3.hashCode", i1.hashCode(), is(not(i3.hashCode())));
	}

	@Test
	public void testEqualsObject() {
		final UniqueId<Object> i1 = idFactory.buildId(Object.class, "r1");
		final UniqueId<Object> i2 = idFactory.buildId(Object.class, "r1");
		final UniqueId<Object> i3 = idFactory.buildId(Object.class, "r3");

		assertThat("i1 == i2", i1, is(not(sameInstance(i2))));
		assertThat("i1 == i3", i1, is(not(sameInstance(i3))));
		assertThat("i2 == i3", i2, is(not(sameInstance(i3))));

		assertThat("i1.equals != i2.equals", i1, is(i2));
		assertThat("i1.equals == i3.equals", i1, is(not(i3)));
	}

	@Test
	public void testToString() {
		final UniqueId<Object> i1 = idFactory.buildId(Object.class, "r1");
		final UniqueId<Object> i2 = idFactory.buildId(Object.class, "r1");
		final UniqueId<Object> i3 = idFactory.buildId(Object.class, "r3");

		assertThat("i1 == i2", i1, is(not(sameInstance(i2))));
		assertThat("i1 == i3", i1, is(not(sameInstance(i3))));
		assertThat("i2 == i3", i2, is(not(sameInstance(i3))));

		assertThat("i1.toString != i2.toString", i1.toString(), is(i2.toString()));
		assertThat("i1.toString == i3.toString", i1.toString(), is(not(i3.toString())));
	}

}
