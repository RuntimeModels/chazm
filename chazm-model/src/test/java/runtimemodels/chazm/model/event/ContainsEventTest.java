package runtimemodels.chazm.model.event;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import runtimemodels.chazm.api.entity.Characteristic;
import runtimemodels.chazm.api.entity.Role;
import runtimemodels.chazm.api.relation.Contains;
import runtimemodels.chazm.model.entity.CharacteristicFactory;
import runtimemodels.chazm.model.entity.RoleFactory;
import runtimemodels.chazm.model.id.IdFactory;
import runtimemodels.chazm.model.relation.ContainsFactory;
import runtimemodels.chazm.model.relation.RelationModule;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@SuppressWarnings("javadoc")
public class ContainsEventTest {

    private final Injector injector = Guice.createInjector(new RelationModule(), new EventModule());
    private final ContainsEventFactory cef = injector.getInstance(ContainsEventFactory.class);
    private final ContainsFactory cf = injector.getInstance(ContainsFactory.class);
    private final RoleFactory rf = injector.getInstance(RoleFactory.class);
    private final CharacteristicFactory ccf = injector.getInstance(CharacteristicFactory.class);
    private final IdFactory idf = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testContainsEventFactory() {
        final Role r = rf.buildRole(idf.build(Role.class, "r"));
        final Characteristic cc = ccf.buildCharacteristic(idf.build(Characteristic.class, "cc"));
        final Contains c = cf.buildContains(r, cc, 1d);
        final ContainsEvent ce1 = cef.build(EventType.ADDED, c);
        final ContainsEvent ce2 = cef.build(EventType.ADDED, c);

        assertThat(ce1, is(not(nullValue())));
        assertThat(ce1, is(not(sameInstance(ce2))));
    }

    @Test
    @Ignore
    public void testContainsEventFactoryWithNullCategoryAndNullContains() {
        exception.expect(instanceOf(ProvisionException.class));
//        exception.expectMessage(allOf(
//                containsString("1st parameter of ContainsEvent.<init>(ContainsEvent.java:31) is not @Nullable"),
//                containsString("2nd parameter of ContainsEvent.<init>(ContainsEvent.java:31) is not @Nullable")
//        ));

        cef.build(null, null);
    }

    @Test
    @Ignore
    public void testContainsEventFactoryWithNullContains() {
        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage((containsString("2nd parameter of ContainsEvent.<init>(ContainsEvent.java:31) is not @Nullable")));

        cef.build(EventType.ADDED, null);
    }

    @Test
    public void testGetRoleId() {
        final Role r = rf.buildRole(idf.build(Role.class, "r"));
        final Characteristic cc = ccf.buildCharacteristic(idf.build(Characteristic.class, "cc"));
        final Contains c = cf.buildContains(r, cc, 1d);
        final ContainsEvent ce = cef.build(EventType.ADDED, c);

        assertThat(ce.getRoleId(), is(sameInstance(r.getId())));
    }

    @Test
    public void testGetCharacteristicId() {
        final Role r = rf.buildRole(idf.build(Role.class, "r"));
        final Characteristic cc = ccf.buildCharacteristic(idf.build(Characteristic.class, "cc"));
        final Contains c = cf.buildContains(r, cc, 1d);
        final ContainsEvent ce = cef.build(EventType.ADDED, c);

        assertThat(ce.getCharacteristicId(), is(sameInstance(cc.getId())));
    }

    @Test
    public void testGetValue() {
        final Role r = rf.buildRole(idf.build(Role.class, "r"));
        final Characteristic cc = ccf.buildCharacteristic(idf.build(Characteristic.class, "cc"));
        final Contains c = cf.buildContains(r, cc, 1d);
        final ContainsEvent ce = cef.build(EventType.ADDED, c);

        assertThat(ce.getValue(), is(equalTo(c.getValue())));
    }

    @Test
    public void testEquals() {
        final Role r1 = rf.buildRole(idf.build(Role.class, "r1"));
        final Role r2 = rf.buildRole(idf.build(Role.class, "r2"));
        final Characteristic cc = ccf.buildCharacteristic(idf.build(Characteristic.class, "cc"));
        final Contains c1 = cf.buildContains(r1, cc, 1d);
        final Contains c2 = cf.buildContains(r2, cc, 1d);
        final Contains c3 = cf.buildContains(r1, cc, 1d);
        final ContainsEvent ce1 = cef.build(EventType.ADDED, c1);
        final ContainsEvent ce2 = cef.build(EventType.ADDED, c2);
        final ContainsEvent ce3 = cef.build(EventType.ADDED, c3);

        assertThat(ce1, is(not(equalTo(ce2))));
        assertThat(ce1, is(equalTo(ce3)));
        assertThat(ce1, is(not(equalTo(""))));
    }

    @Test
    public void testHashCode() {
        final Role r1 = rf.buildRole(idf.build(Role.class, "r1"));
        final Role r2 = rf.buildRole(idf.build(Role.class, "r2"));
        final Characteristic cc = ccf.buildCharacteristic(idf.build(Characteristic.class, "cc"));
        final Contains c1 = cf.buildContains(r1, cc, 1d);
        final Contains c2 = cf.buildContains(r2, cc, 1d);
        final Contains c3 = cf.buildContains(r1, cc, 1d);
        final ContainsEvent ce1 = cef.build(EventType.ADDED, c1);
        final ContainsEvent ce2 = cef.build(EventType.ADDED, c2);
        final ContainsEvent ce3 = cef.build(EventType.ADDED, c3);

        assertThat(ce1.hashCode(), is(not(equalTo(ce2.hashCode()))));
        assertThat(ce1.hashCode(), is(equalTo(ce3.hashCode())));
    }

    @Test
    public void testToString() {
        final Role r1 = rf.buildRole(idf.build(Role.class, "r1"));
        final Role r2 = rf.buildRole(idf.build(Role.class, "r2"));
        final Characteristic cc = ccf.buildCharacteristic(idf.build(Characteristic.class, "c"));
        final Contains c1 = cf.buildContains(r1, cc, 1d);
        final Contains c2 = cf.buildContains(r2, cc, 1d);
        final Contains c3 = cf.buildContains(r1, cc, 1d);
        final ContainsEvent ce1 = cef.build(EventType.ADDED, c1);
        final ContainsEvent ce2 = cef.build(EventType.ADDED, c2);
        final ContainsEvent ce3 = cef.build(EventType.ADDED, c3);

        assertThat(ce1.toString(), is(not(equalTo(ce2.toString()))));
        assertThat(ce1.toString(), is(equalTo(ce3.toString())));
    }

}
