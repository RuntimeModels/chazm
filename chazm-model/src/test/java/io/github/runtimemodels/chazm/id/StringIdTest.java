package io.github.runtimemodels.chazm.id;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.github.runtimemodels.message.E;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import runtimemodels.chazm.api.id.UniqueId;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@SuppressWarnings("javadoc")
public class StringIdTest {

    private final Injector injector = Guice.createInjector(new IdModule());
    private final IdFactory idFactory = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testStringId() {
        final UniqueId<Object> i1 = idFactory.build(Object.class, "");
        assertThat(i1, is(not(nullValue())));
        assertThat(i1, is(instanceOf(StringId.class)));
    }

    @Test
    public void testStringId1() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(equalTo(E.PARAMETER_CANNOT_BE_NULL.get("id")));
        idFactory.build(Object.class, (String) null);
    }

    @Test
    public void testGetType() {
        final UniqueId<Object> i1 = idFactory.build(Object.class, "");
        final UniqueId<String> i2 = idFactory.build(String.class, "");
        final UniqueId<Double> i3 = idFactory.build(Double.class, "");

        assertThat(i1.getType(), is(equalTo(Object.class)));
        assertThat(i2.getType(), is(equalTo(String.class)));
        assertThat(i3.getType(), is(equalTo(Double.class)));
    }

    @Test
    public void testHashCode() {
        final UniqueId<Object> i1 = idFactory.build(Object.class, "r1");
        final UniqueId<Object> i2 = idFactory.build(Object.class, "r1");
        final UniqueId<Object> i3 = idFactory.build(Object.class, "r2");
        final UniqueId<Integer> i4 = idFactory.build(Integer.class, "r2");

        assertThat(i1, is(not(sameInstance(i2))));
        assertThat(i1, is(not(sameInstance(i3))));
        assertThat(i1, is(not(sameInstance(i4))));
        assertThat(i2, is(not(sameInstance(i3))));
        assertThat(i2, is(not(sameInstance(i4))));
        assertThat(i3, is(not(sameInstance(i4))));

        assertThat(i1.hashCode(), is(equalTo(i2.hashCode())));
        assertThat(i1.hashCode(), is(not(equalTo(i3.hashCode()))));
        assertThat(i1.hashCode(), is(not(equalTo(i4.hashCode()))));
        assertThat(i2.hashCode(), is(not(equalTo(i3.hashCode()))));
        assertThat(i2.hashCode(), is(not(equalTo(i4.hashCode()))));
        assertThat(i3.hashCode(), is(not(equalTo(i4.hashCode()))));
    }

    @Test
    public void testEqualsObject() {
        final UniqueId<Object> i1 = idFactory.build(Object.class, "r1");
        final UniqueId<Object> i2 = idFactory.build(Object.class, "r1");
        final UniqueId<Object> i3 = idFactory.build(Object.class, "r2");
        final UniqueId<Integer> i4 = idFactory.build(Integer.class, "r2");

        assertThat(i1, is(not(sameInstance(i2))));
        assertThat(i1, is(not(sameInstance(i3))));
        assertThat(i1, is(not(sameInstance(i4))));
        assertThat(i2, is(not(sameInstance(i3))));
        assertThat(i2, is(not(sameInstance(i4))));
        assertThat(i3, is(not(sameInstance(i4))));

        assertThat(i1, is(equalTo(i2)));
        assertThat(i1, is(not(equalTo(i3))));
        assertThat(i1, is(not(equalTo(i4))));
        assertThat(i1, is(not(equalTo(""))));
        assertThat(i2, is(not(equalTo(i3))));
        assertThat(i2, is(not(equalTo(i4))));
        assertThat(i3, is(not(equalTo(i4))));
    }

    @Test
    public void testToString() {
        final UniqueId<Object> i1 = idFactory.build(Object.class, "r1");
        final UniqueId<Object> i2 = idFactory.build(Object.class, "r1");
        final UniqueId<Object> i3 = idFactory.build(Object.class, "r2");
        final UniqueId<Integer> i4 = idFactory.build(Integer.class, "r2");

        assertThat(i1, is(not(sameInstance(i2))));
        assertThat(i1, is(not(sameInstance(i3))));
        assertThat(i1, is(not(sameInstance(i4))));
        assertThat(i2, is(not(sameInstance(i3))));
        assertThat(i2, is(not(sameInstance(i4))));
        assertThat(i3, is(not(sameInstance(i4))));

        assertThat(i1.toString(), is(equalTo(i2.toString())));
        assertThat(i1.toString(), is(not(equalTo(i3.toString()))));
        assertThat(i1.toString(), is(not(equalTo(i4.toString()))));
        assertThat(i2.toString(), is(not(equalTo(i3.toString()))));
        assertThat(i2.toString(), is(not(equalTo(i4.toString()))));
        assertThat(i3.toString(), is(not(equalTo(i4.toString()))));
    }

}
