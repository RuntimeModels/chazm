package runtimemodels.chazm.model.entity;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import runtimemodels.chazm.api.entity.Characteristic;
import runtimemodels.chazm.api.id.UniqueId;
import runtimemodels.chazm.model.id.IdFactory;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@SuppressWarnings("javadoc")
public class CharacteristicEntityTest {

    private final Injector injector = Guice.createInjector(new EntityModule());
    private final CharacteristicFactory characteristicFactory = injector.getInstance(CharacteristicFactory.class);
    private final IdFactory idFactory = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testCharacteristicFactory() {
        final UniqueId<Characteristic> i1 = idFactory.build(Characteristic.class, 1L);
        final Characteristic c1 = characteristicFactory.buildCharacteristic(i1);
        final Characteristic c2 = characteristicFactory.buildCharacteristic(i1);

        assertThat(c1, is(not(nullValue())));
        assertThat(c1, is(not(sameInstance(c2))));
    }

    @Test
    @Ignore
    public void testCharacteristicFactoryWithNullId() {
        exception.expect(instanceOf(ProvisionException.class));
//        exception.expectMessage(containsString("1st parameter of CharacteristicEntity.<init>(CharacteristicEntity.java:13) is not @Nullable"));
        characteristicFactory.buildCharacteristic(null);
    }

    @Test
    public void testGetId() {
        final UniqueId<Characteristic> i1 = idFactory.build(Characteristic.class, 1L);
        final UniqueId<Characteristic> i2 = idFactory.build(Characteristic.class, 1L);
        final Characteristic c1 = characteristicFactory.buildCharacteristic(i1);
        final Characteristic c2 = characteristicFactory.buildCharacteristic(i2);

        assertThat(c1.getId(), is(sameInstance(i1)));
        assertThat(c1.getId(), is(not(sameInstance(c2.getId()))));
    }

    @Test
    public void testEqualsObject() {
        final UniqueId<Characteristic> i1 = idFactory.build(Characteristic.class, 1L);
        final UniqueId<Characteristic> i2 = idFactory.build(Characteristic.class, 2L);
        final Characteristic c1 = characteristicFactory.buildCharacteristic(i1);
        final Characteristic c2 = characteristicFactory.buildCharacteristic(i2);
        final Characteristic c3 = characteristicFactory.buildCharacteristic(i1);

        assertThat(c1, is(not(equalTo(c2))));
        assertThat(c1, is(equalTo(c3)));
        assertThat(c1, is(not(equalTo(""))));
    }

    @Test
    public void testHashCode() {
        final UniqueId<Characteristic> i1 = idFactory.build(Characteristic.class, 1L);
        final UniqueId<Characteristic> i2 = idFactory.build(Characteristic.class, 2L);
        final Characteristic c1 = characteristicFactory.buildCharacteristic(i1);
        final Characteristic c2 = characteristicFactory.buildCharacteristic(i2);
        final Characteristic c3 = characteristicFactory.buildCharacteristic(i1);

        assertThat(c1.hashCode(), is(not(equalTo(c2.hashCode()))));
        assertThat(c1.hashCode(), is(equalTo(c3.hashCode())));
    }

    @Test
    public void testToString() {
        final UniqueId<Characteristic> i1 = idFactory.build(Characteristic.class, 1L);
        final UniqueId<Characteristic> i2 = idFactory.build(Characteristic.class, 2L);
        final Characteristic c1 = characteristicFactory.buildCharacteristic(i1);
        final Characteristic c2 = characteristicFactory.buildCharacteristic(i2);
        final Characteristic c3 = characteristicFactory.buildCharacteristic(i1);

        assertThat(c1.toString(), is(not(equalTo(c2.toString()))));
        assertThat(c1.toString(), is(equalTo(c3.toString())));
    }

}
