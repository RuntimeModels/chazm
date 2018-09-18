package io.github.runtimemodels.chazm.relation;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import io.github.runtimemodels.chazm.entity.Characteristic;
import io.github.runtimemodels.chazm.entity.CharacteristicFactory;
import io.github.runtimemodels.chazm.entity.Role;
import io.github.runtimemodels.chazm.entity.RoleFactory;
import io.github.runtimemodels.chazm.id.IdFactory;
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
public class ContainsRelationTest {

    private final Injector injector = Guice.createInjector(new RelationModule());
    private final ContainsFactory containsFactory = injector.getInstance(ContainsFactory.class);
    private final RoleFactory roleFactory = injector.getInstance(RoleFactory.class);
    private final CharacteristicFactory characteristicFactory = injector.getInstance(CharacteristicFactory.class);
    private final IdFactory idFactory = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testContainsRelationFactory() {
        final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
        final Characteristic c = characteristicFactory.buildCharacteristic(idFactory.build(Characteristic.class, "c"));
        final Contains ct1 = containsFactory.buildContains(r, c, 1d);
        final Contains ct2 = containsFactory.buildContains(r, c, 1d);

        assertThat(ct1, is(not(nullValue())));
        assertThat(ct1, is(not(sameInstance(ct2))));
    }

    @Test
    public void testContainsRelationFactoryWithNullRoleNullCharacteristic() {
        exception.expect(instanceOf(ProvisionException.class));
//        exception.expectMessage(allOf(
//                containsString("1st parameter of io.github.runtimemodels.chazm.relation.ContainsRelation.<init>(ContainsRelation.java:26) is not @Nullable"),
//                containsString("2nd parameter of io.github.runtimemodels.chazm.relation.ContainsRelation.<init>(ContainsRelation.java:26) is not @Nullable")));

        containsFactory.buildContains(null, null, 1d);
    }

    @Test
    public void testContainsRelationFactoryWithNullCharacteristic() {
        final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));

        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(containsString("2nd parameter of io.github.runtimemodels.chazm.relation.ContainsRelation.<init>(ContainsRelation.java:26) is not @Nullable"));

        containsFactory.buildContains(r, null, 1d);
    }

    @Test
    public void testGetRole() {
        final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
        final Characteristic c = characteristicFactory.buildCharacteristic(idFactory.build(Characteristic.class, "c"));
        final Contains ct = containsFactory.buildContains(r, c, 1d);

        assertThat(ct.getRole(), is(sameInstance(r)));
    }

    @Test
    public void testGetCharacteristic() {
        final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
        final Characteristic c = characteristicFactory.buildCharacteristic(idFactory.build(Characteristic.class, "c"));
        final Contains ct = containsFactory.buildContains(r, c, 1d);

        assertThat(ct.getCharacteristic(), is(sameInstance(c)));
    }

    @Test
    public void testGetValue() {
        final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
        final Characteristic c = characteristicFactory.buildCharacteristic(idFactory.build(Characteristic.class, "c"));
        final Contains ct = containsFactory.buildContains(r, c, 1d);

        assertThat(ct.getValue(), is(equalTo(1d)));
    }

    @Test
    public void testSetValue() {
        final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
        final Characteristic c = characteristicFactory.buildCharacteristic(idFactory.build(Characteristic.class, "c"));
        final Contains ct = containsFactory.buildContains(r, c, 1d);
        ct.setValue(2);

        assertThat(ct.getValue(), is(equalTo(2d)));
    }

    @Test
    public void testEquals() {
        final Role r1 = roleFactory.buildRole(idFactory.build(Role.class, "r1"));
        final Role r2 = roleFactory.buildRole(idFactory.build(Role.class, "r2"));
        final Characteristic c = characteristicFactory.buildCharacteristic(idFactory.build(Characteristic.class, "c"));
        final Contains ct1 = containsFactory.buildContains(r1, c, 1d);
        final Contains ct2 = containsFactory.buildContains(r2, c, 1d);
        final Contains ct3 = containsFactory.buildContains(r1, c, 1d);

        assertThat(ct1, is(not(equalTo(ct2))));
        assertThat(ct1, is(equalTo(ct3)));
        assertThat(ct1, is(not(equalTo(""))));
    }

    @Test
    public void testHashCode() {
        final Role r1 = roleFactory.buildRole(idFactory.build(Role.class, "r1"));
        final Role r2 = roleFactory.buildRole(idFactory.build(Role.class, "r2"));
        final Characteristic c = characteristicFactory.buildCharacteristic(idFactory.build(Characteristic.class, "c"));
        final Contains ct1 = containsFactory.buildContains(r1, c, 1d);
        final Contains ct2 = containsFactory.buildContains(r2, c, 1d);
        final Contains ct3 = containsFactory.buildContains(r1, c, 1d);

        assertThat(ct1.hashCode(), is(not(equalTo(ct2.hashCode()))));
        assertThat(ct1.hashCode(), is(equalTo(ct3.hashCode())));
    }

    @Test
    public void testToString() {
        final Role r1 = roleFactory.buildRole(idFactory.build(Role.class, "r1"));
        final Role r2 = roleFactory.buildRole(idFactory.build(Role.class, "r2"));
        final Characteristic c = characteristicFactory.buildCharacteristic(idFactory.build(Characteristic.class, "c"));
        final Contains ct1 = containsFactory.buildContains(r1, c, 1d);
        final Contains ct2 = containsFactory.buildContains(r2, c, 1d);
        final Contains ct3 = containsFactory.buildContains(r1, c, 1d);

        assertThat(ct1.toString(), is(not(equalTo(ct2.toString()))));
        assertThat(ct1.toString(), is(equalTo(ct3.toString())));
    }

}
