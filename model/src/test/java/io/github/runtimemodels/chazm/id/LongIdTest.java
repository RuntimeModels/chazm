package io.github.runtimemodels.chazm.id;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import io.github.chriszhong.message.E;
import io.github.runtimemodels.chazm.id.IdFactory;
import io.github.runtimemodels.chazm.id.IdModule;
import io.github.runtimemodels.chazm.id.LongId;
import io.github.runtimemodels.chazm.id.UniqueId;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.inject.Guice;
import com.google.inject.Injector;

@SuppressWarnings("javadoc")
public class LongIdTest {

	private final Injector injector = Guice.createInjector(new IdModule());
	private final IdFactory idFactory = injector.getInstance(IdFactory.class);

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testLongId() {
		final UniqueId<Object> i1 = idFactory.build(Object.class, 1L);
		assertThat(i1, is(not(nullValue())));
		assertThat(i1, is(instanceOf(LongId.class)));
	}

	@Test
	public void testLongId1() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(is(E.PARAMETER_CANNOT_BE_NULL.get("id")));
		idFactory.build(Object.class, (Long) null);
	}

	@Test
	public void testGetType() {
		final UniqueId<Object> i1 = idFactory.build(Object.class, 1L);
		final UniqueId<String> i2 = idFactory.build(String.class, 1L);
		final UniqueId<Double> i3 = idFactory.build(Double.class, 1L);

		assertThat(i1.getType(), is(equalTo(Object.class)));
		assertThat(i2.getType(), is(equalTo(String.class)));
		assertThat(i3.getType(), is(equalTo(Double.class)));
	}

	@Test
	public void testHashCode() {
		final UniqueId<Object> i1 = idFactory.build(Object.class, 1L);
		final UniqueId<Object> i2 = idFactory.build(Object.class, 1L);
		final UniqueId<Object> i3 = idFactory.build(Object.class, 2L);

		assertThat(i1, is(not(sameInstance(i2))));
		assertThat(i1, is(not(sameInstance(i3))));
		assertThat(i2, is(not(sameInstance(i3))));

		assertThat(i1.hashCode(), is(equalTo(i2.hashCode())));
		assertThat(i1.hashCode(), is(not(equalTo(i3.hashCode()))));
	}

	@Test
	public void testEqualsObject() {
		final UniqueId<Object> i1 = idFactory.build(Object.class, 1L);
		final UniqueId<Object> i2 = idFactory.build(Object.class, 1L);
		final UniqueId<Object> i3 = idFactory.build(Object.class, 2L);

		assertThat(i1, is(not(sameInstance(i2))));
		assertThat(i1, is(not(sameInstance(i3))));
		assertThat(i2, is(not(sameInstance(i3))));

		assertThat(i1, is(equalTo(i2)));
		assertThat(i1, is(not(equalTo(i3))));
		assertThat(i1, is(not(equalTo(""))));
	}

	@Test
	public void testToString() {
		final UniqueId<Object> i1 = idFactory.build(Object.class, 1L);
		final UniqueId<Object> i2 = idFactory.build(Object.class, 1L);
		final UniqueId<Object> i3 = idFactory.build(Object.class, 2L);

		assertThat(i1, is(not(sameInstance(i2))));
		assertThat(i1, is(not(sameInstance(i3))));
		assertThat(i2, is(not(sameInstance(i3))));

		assertThat(i1.toString(), is(equalTo(i2.toString())));
		assertThat(i1.toString(), is(not(equalTo(i3.toString()))));
	}

}
