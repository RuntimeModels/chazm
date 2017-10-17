package io.github.runtimemodels.chazm.entity;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import io.github.runtimemodels.chazm.entity.Attribute.Type;
import io.github.runtimemodels.chazm.id.IdFactory;
import io.github.runtimemodels.chazm.id.UniqueId;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

@SuppressWarnings("javadoc")
public class AttributeEntityTest {

    private final Injector injector = Guice.createInjector(new EntityModule());
    private final AttributeFactory attributeFactory = injector.getInstance(AttributeFactory.class);
    private final IdFactory idFactory = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testAttributeFactory() {
        final UniqueId<Attribute> i1 = idFactory.build(Attribute.class, 1L);
        final Attribute a1 = attributeFactory.buildAttribute(i1, Type.NEGATIVE_QUALITY);
        final Attribute a2 = attributeFactory.buildAttribute(i1, Type.NEGATIVE_QUALITY);
        assertThat(a1, is(not(nullValue())));
        assertThat(a1, is(not(sameInstance(a2))));
    }

    @Test
    public void testAttributeFactoryWithNullIdAndNullType() {
        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(allOf(
                containsString("1st parameter of io.github.runtimemodels.chazm.entity.AttributeEntity.<init>(AttributeEntity.java:21) is not @Nullable"),
                containsString("2nd parameter of io.github.runtimemodels.chazm.entity.AttributeEntity.<init>(AttributeEntity.java:21) is not @Nullable")
        ));
        attributeFactory.buildAttribute(null, null);
    }

    @Test
    public void testAttributeFactoryWithNullType() {
        final UniqueId<Attribute> i1 = idFactory.build(Attribute.class, 1L);
        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(containsString("2nd parameter of io.github.runtimemodels.chazm.entity.AttributeEntity.<init>(AttributeEntity.java:21) is not @Nullable"));
        attributeFactory.buildAttribute(i1, null);
    }

    @Test
    public void testGetType() {
        final UniqueId<Attribute> i1 = idFactory.build(Attribute.class, 1L);
        final Attribute a1 = attributeFactory.buildAttribute(i1, Type.NEGATIVE_QUALITY);
        final Attribute a2 = attributeFactory.buildAttribute(i1, Type.NEGATIVE_QUANTITY);
        final Attribute a3 = attributeFactory.buildAttribute(i1, Type.NEGATIVE_UNBOUNDED);
        final Attribute a4 = attributeFactory.buildAttribute(i1, Type.POSITIVE_QUALITY);
        final Attribute a5 = attributeFactory.buildAttribute(i1, Type.POSITIVE_QUANTITY);
        final Attribute a6 = attributeFactory.buildAttribute(i1, Type.POSITIVE_UNBOUNDED);
        assertThat(a1.getType(), is(equalTo(Type.NEGATIVE_QUALITY)));
        assertThat(a2.getType(), is(equalTo(Type.NEGATIVE_QUANTITY)));
        assertThat(a3.getType(), is(equalTo(Type.NEGATIVE_UNBOUNDED)));
        assertThat(a4.getType(), is(equalTo(Type.POSITIVE_QUALITY)));
        assertThat(a5.getType(), is(equalTo(Type.POSITIVE_QUANTITY)));
        assertThat(a6.getType(), is(equalTo(Type.POSITIVE_UNBOUNDED)));
    }

    @Test
    public void testGetId() {
        final UniqueId<Attribute> i1 = idFactory.build(Attribute.class, 1L);
        final UniqueId<Attribute> i2 = idFactory.build(Attribute.class, 1L);
        final Attribute c1 = attributeFactory.buildAttribute(i1, Type.NEGATIVE_QUALITY);
        final Attribute c2 = attributeFactory.buildAttribute(i2, Type.NEGATIVE_QUALITY);

        assertThat(c1.getId(), is(sameInstance(i1)));
        assertThat(c1.getId(), is(not(sameInstance(c2.getId()))));
    }

    @Test
    public void testEqualsObject() {
        final UniqueId<Attribute> i1 = idFactory.build(Attribute.class, 1L);
        final UniqueId<Attribute> i2 = idFactory.build(Attribute.class, 2L);
        final Attribute c1 = attributeFactory.buildAttribute(i1, Type.NEGATIVE_QUALITY);
        final Attribute c2 = attributeFactory.buildAttribute(i2, Type.NEGATIVE_QUALITY);
        final Attribute c3 = attributeFactory.buildAttribute(i1, Type.NEGATIVE_QUALITY);

        assertThat(c1, is(not(equalTo(c2))));
        assertThat(c1, is(equalTo(c3)));
        assertThat(c1, is(not(equalTo(""))));
    }

    @Test
    public void testHashCode() {
        final UniqueId<Attribute> i1 = idFactory.build(Attribute.class, 1L);
        final UniqueId<Attribute> i2 = idFactory.build(Attribute.class, 2L);
        final Attribute c1 = attributeFactory.buildAttribute(i1, Type.NEGATIVE_QUALITY);
        final Attribute c2 = attributeFactory.buildAttribute(i2, Type.NEGATIVE_QUALITY);
        final Attribute c3 = attributeFactory.buildAttribute(i1, Type.NEGATIVE_QUALITY);

        assertThat(c1.hashCode(), is(not(equalTo(c2.hashCode()))));
        assertThat(c1.hashCode(), is(equalTo(c3.hashCode())));
    }

    @Test
    public void testToString() {
        final UniqueId<Attribute> i1 = idFactory.build(Attribute.class, 1L);
        final UniqueId<Attribute> i2 = idFactory.build(Attribute.class, 2L);
        final Attribute c1 = attributeFactory.buildAttribute(i1, Type.NEGATIVE_QUALITY);
        final Attribute c2 = attributeFactory.buildAttribute(i2, Type.NEGATIVE_QUALITY);
        final Attribute c3 = attributeFactory.buildAttribute(i1, Type.NEGATIVE_QUALITY);

        assertThat(c1.toString(), is(not(equalTo(c2.toString()))));
        assertThat(c1.toString(), is(equalTo(c3.toString())));
    }

}
