package runtimemodels.chazm.model.event;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import runtimemodels.chazm.api.entity.Characteristic;
import runtimemodels.chazm.model.entity.CharacteristicFactory;
import runtimemodels.chazm.model.id.IdFactory;
import runtimemodels.chazm.model.relation.RelationModule;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@SuppressWarnings("javadoc")
public class CharacteristicEventTest {

    private final Injector injector = Guice.createInjector(new RelationModule(), new EventModule());
    private final CharacteristicEventFactory cef = injector.getInstance(CharacteristicEventFactory.class);
    private final CharacteristicFactory cf = injector.getInstance(CharacteristicFactory.class);
    private final IdFactory idf = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testCharacteristicEventFactory() {
        final Characteristic c = cf.buildCharacteristic(idf.build(Characteristic.class, "c"));
        final CharacteristicEvent ce1 = cef.build(EventCategory.ADDED, c);
        final CharacteristicEvent ce2 = cef.build(EventCategory.ADDED, c);

        assertThat(ce1, is(not(nullValue())));
        assertThat(ce1, is(not(sameInstance(ce2))));
    }

    @Test
    @Ignore
    public void testCharacteristicEventFactoryWithNullCategoryAndNullCharacteristic() {
        exception.expect(instanceOf(ProvisionException.class));
//        exception.expectMessage(allOf(
//                containsString("1st parameter of CharacteristicEvent.<init>(CharacteristicEvent.java:27) is not @Nullable"),
//                containsString("2nd parameter of CharacteristicEvent.<init>(CharacteristicEvent.java:27) is not @Nullable")
//        ));

        cef.build(null, null);
    }

    @Test
    @Ignore
    public void testCharacteristicEventWithNullCharacteristic() {
        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(containsString("2nd parameter of CharacteristicEvent.<init>(CharacteristicEvent.java:27) is not @Nullable"));

        cef.build(EventCategory.ADDED, null);
    }

    @Test
    public void testGetId() {
        final Characteristic c = cf.buildCharacteristic(idf.build(Characteristic.class, "c"));
        final CharacteristicEvent ce = cef.build(EventCategory.ADDED, c);

        assertThat(ce.getId(), is(sameInstance(c.getId())));
    }

    @Test
    public void testEquals() {
        final Characteristic c1 = cf.buildCharacteristic(idf.build(Characteristic.class, "c1"));
        final Characteristic c2 = cf.buildCharacteristic(idf.build(Characteristic.class, "c2"));
        final Characteristic c3 = cf.buildCharacteristic(idf.build(Characteristic.class, "c1"));
        final CharacteristicEvent ce1 = cef.build(EventCategory.ADDED, c1);
        final CharacteristicEvent ce2 = cef.build(EventCategory.ADDED, c2);
        final CharacteristicEvent ce3 = cef.build(EventCategory.ADDED, c3);

        assertThat(ce1, is(not(equalTo(ce2))));
        assertThat(ce1, is(equalTo(ce3)));
        assertThat(ce1, is(not(equalTo(""))));
    }

    @Test
    public void testHashCode() {
        final Characteristic c1 = cf.buildCharacteristic(idf.build(Characteristic.class, "c1"));
        final Characteristic c2 = cf.buildCharacteristic(idf.build(Characteristic.class, "c2"));
        final Characteristic c3 = cf.buildCharacteristic(idf.build(Characteristic.class, "c1"));
        final CharacteristicEvent ce1 = cef.build(EventCategory.ADDED, c1);
        final CharacteristicEvent ce2 = cef.build(EventCategory.ADDED, c2);
        final CharacteristicEvent ce3 = cef.build(EventCategory.ADDED, c3);

        assertThat(ce1.hashCode(), is(not(equalTo(ce2.hashCode()))));
        assertThat(ce1.hashCode(), is(equalTo(ce3.hashCode())));
    }

    @Test
    public void testToString() {
        final Characteristic c1 = cf.buildCharacteristic(idf.build(Characteristic.class, "c1"));
        final Characteristic c2 = cf.buildCharacteristic(idf.build(Characteristic.class, "c2"));
        final Characteristic c3 = cf.buildCharacteristic(idf.build(Characteristic.class, "c1"));
        final CharacteristicEvent ce1 = cef.build(EventCategory.ADDED, c1);
        final CharacteristicEvent ce2 = cef.build(EventCategory.ADDED, c2);
        final CharacteristicEvent ce3 = cef.build(EventCategory.ADDED, c3);

        assertThat(ce1.toString(), is(not(equalTo(ce2.toString()))));
        assertThat(ce1.toString(), is(equalTo(ce3.toString())));
    }

}
