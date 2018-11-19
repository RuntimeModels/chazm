package runtimemodels.chazm.model.entity;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import org.junit.jupiter.api.Test;
import runtimemodels.chazm.api.entity.Attribute;
import runtimemodels.chazm.api.id.UniqueId;
import runtimemodels.chazm.model.id.IdFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AttributeEntityTest {

    private final Injector injector = Guice.createInjector(new EntityModule());
    private final AttributeFactory attributeFactory = injector.getInstance(AttributeFactory.class);
    private final IdFactory idFactory = injector.getInstance(IdFactory.class);


    @Test
    public void testAttributeFactory() {
        final UniqueId<Attribute> i1 = idFactory.build(Attribute.class, 1L);
        final Attribute a1 = attributeFactory.buildAttribute(i1, Attribute.Type.NEGATIVE_QUALITY);
        final Attribute a2 = attributeFactory.buildAttribute(i1, Attribute.Type.NEGATIVE_QUALITY);

        assertAll(
                () -> assertThat(a1).isNotNull(),
                () -> assertThat(a2).isNotNull(),
                () -> assertThat(a1).isNotSameAs(a2)
        );
    }

    @Test
    public void testAttributeFactoryWithNullIdAndNullType() {
        assertThrows(ProvisionException.class, () -> attributeFactory.buildAttribute(null, null));
    }

    @Test
    public void testAttributeFactoryWithNullType() {
        final UniqueId<Attribute> i1 = idFactory.build(Attribute.class, 1L);

        assertThrows(ProvisionException.class, () -> attributeFactory.buildAttribute(i1, null));
    }

    @Test
    public void testGetType() {
        final UniqueId<Attribute> i1 = idFactory.build(Attribute.class, 1L);
        final Attribute a1 = attributeFactory.buildAttribute(i1, Attribute.Type.NEGATIVE_QUALITY);
        final Attribute a2 = attributeFactory.buildAttribute(i1, Attribute.Type.NEGATIVE_QUANTITY);
        final Attribute a3 = attributeFactory.buildAttribute(i1, Attribute.Type.NEGATIVE_UNBOUNDED);
        final Attribute a4 = attributeFactory.buildAttribute(i1, Attribute.Type.POSITIVE_QUALITY);
        final Attribute a5 = attributeFactory.buildAttribute(i1, Attribute.Type.POSITIVE_QUANTITY);
        final Attribute a6 = attributeFactory.buildAttribute(i1, Attribute.Type.POSITIVE_UNBOUNDED);

        assertAll(
                () -> assertThat(a1.getType()).isEqualTo(Attribute.Type.NEGATIVE_QUALITY),
                () -> assertThat(a2.getType()).isEqualTo(Attribute.Type.NEGATIVE_QUANTITY),
                () -> assertThat(a3.getType()).isEqualTo(Attribute.Type.NEGATIVE_UNBOUNDED),
                () -> assertThat(a4.getType()).isEqualTo(Attribute.Type.POSITIVE_QUALITY),
                () -> assertThat(a5.getType()).isEqualTo(Attribute.Type.POSITIVE_QUANTITY),
                () -> assertThat(a6.getType()).isEqualTo(Attribute.Type.POSITIVE_UNBOUNDED)
        );
    }

    @Test
    public void testGetId() {
        final UniqueId<Attribute> i1 = idFactory.build(Attribute.class, 1L);
        final UniqueId<Attribute> i2 = idFactory.build(Attribute.class, 1L);
        final Attribute c1 = attributeFactory.buildAttribute(i1, Attribute.Type.NEGATIVE_QUALITY);
        final Attribute c2 = attributeFactory.buildAttribute(i2, Attribute.Type.NEGATIVE_QUALITY);

        assertAll(
                () -> assertThat(c1.getId()).isSameAs(i1),
                () -> assertThat(c1.getId()).isNotSameAs(c2.getId())
        );
    }

    @Test
    public void testEqualsObject() {
        final UniqueId<Attribute> i1 = idFactory.build(Attribute.class, 1L);
        final UniqueId<Attribute> i2 = idFactory.build(Attribute.class, 2L);
        final Attribute c1 = attributeFactory.buildAttribute(i1, Attribute.Type.NEGATIVE_QUALITY);
        final Attribute c2 = attributeFactory.buildAttribute(i2, Attribute.Type.NEGATIVE_QUALITY);
        final Attribute c3 = attributeFactory.buildAttribute(i1, Attribute.Type.NEGATIVE_QUALITY);

        assertAll(
                () -> assertThat(c1).isNotEqualTo(c2),
                () -> assertThat(c1).isEqualTo(c3),
                () -> assertThat(c1).isNotEqualTo("")
        );
    }

    @Test
    public void testHashCode() {
        final UniqueId<Attribute> i1 = idFactory.build(Attribute.class, 1L);
        final UniqueId<Attribute> i2 = idFactory.build(Attribute.class, 2L);
        final Attribute c1 = attributeFactory.buildAttribute(i1, Attribute.Type.NEGATIVE_QUALITY);
        final Attribute c2 = attributeFactory.buildAttribute(i2, Attribute.Type.NEGATIVE_QUALITY);
        final Attribute c3 = attributeFactory.buildAttribute(i1, Attribute.Type.NEGATIVE_QUALITY);

        assertAll(
                () -> assertThat(c1.hashCode()).isNotEqualTo(c2.hashCode()),
                () -> assertThat(c1.hashCode()).isEqualTo(c3.hashCode())
        );
    }

    @Test
    public void testToString() {
        final UniqueId<Attribute> i1 = idFactory.build(Attribute.class, 1L);
        final UniqueId<Attribute> i2 = idFactory.build(Attribute.class, 2L);
        final Attribute c1 = attributeFactory.buildAttribute(i1, Attribute.Type.NEGATIVE_QUALITY);
        final Attribute c2 = attributeFactory.buildAttribute(i2, Attribute.Type.NEGATIVE_QUALITY);
        final Attribute c3 = attributeFactory.buildAttribute(i1, Attribute.Type.NEGATIVE_QUALITY);

        assertAll(
                () -> assertThat(c1.toString()).isNotEqualTo(c2.toString()),
                () -> assertThat(c1.toString()).isEqualTo(c3.toString())
        );
    }

}
