package model.organization.event;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import model.organization.entity.Characteristic;
import model.organization.entity.CharacteristicFactory;
import model.organization.entity.Role;
import model.organization.entity.RoleFactory;
import model.organization.id.IdFactory;
import model.organization.relation.Contains;
import model.organization.relation.ContainsFactory;
import model.organization.relation.RelationModule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;

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
	public void testContainsEvent() {
		final Role r = rf.buildRole(idf.build(Role.class, "r"));
		final Characteristic cc = ccf.buildCharacteristic(idf.build(Characteristic.class, "cc"));
		final Contains c = cf.buildContains(r, cc, 1d);
		final ContainsEvent ce1 = cef.build(EventCategory.ADDED, c);
		final ContainsEvent ce2 = cef.build(EventCategory.ADDED, c);

		assertThat(ce1, is(not(nullValue())));
		assertThat(ce1, is(not(sameInstance(ce2))));
	}

	@Test
	public void testContainsEvent1() {
		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		cef.build(null, null);
	}

	@Test
	public void testContainsEvent2() {
		exception.expect(instanceOf(IllegalArgumentException.class));
		exception.expectMessage(equalTo("Parameter (category) cannot be null"));

		new ContainsEvent(null, null);
	}

	@Test
	public void testContainsEvent3() {
		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		cef.build(EventCategory.ADDED, null);
	}

	@Test
	public void testContainsEvent4() {
		exception.expect(instanceOf(IllegalArgumentException.class));
		exception.expectMessage(equalTo("Parameter (contains) cannot be null"));

		new ContainsEvent(EventCategory.ADDED, null);
	}

	@Test
	public void testGetRoleId() {
		final Role r = rf.buildRole(idf.build(Role.class, "r"));
		final Characteristic cc = ccf.buildCharacteristic(idf.build(Characteristic.class, "cc"));
		final Contains c = cf.buildContains(r, cc, 1d);
		final ContainsEvent ce = cef.build(EventCategory.ADDED, c);

		assertThat(ce.getRoleId(), is(sameInstance(r.getId())));
	}

	@Test
	public void testGetCharacteristicId() {
		final Role r = rf.buildRole(idf.build(Role.class, "r"));
		final Characteristic cc = ccf.buildCharacteristic(idf.build(Characteristic.class, "cc"));
		final Contains c = cf.buildContains(r, cc, 1d);
		final ContainsEvent ce = cef.build(EventCategory.ADDED, c);

		assertThat(ce.getCharacteristicId(), is(sameInstance(cc.getId())));
	}

	@Test
	public void testGetValue() {
		final Role r = rf.buildRole(idf.build(Role.class, "r"));
		final Characteristic cc = ccf.buildCharacteristic(idf.build(Characteristic.class, "cc"));
		final Contains c = cf.buildContains(r, cc, 1d);
		final ContainsEvent ce = cef.build(EventCategory.ADDED, c);

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
		final ContainsEvent ce1 = cef.build(EventCategory.ADDED, c1);
		final ContainsEvent ce2 = cef.build(EventCategory.ADDED, c2);
		final ContainsEvent ce3 = cef.build(EventCategory.ADDED, c3);

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
		final ContainsEvent ce1 = cef.build(EventCategory.ADDED, c1);
		final ContainsEvent ce2 = cef.build(EventCategory.ADDED, c2);
		final ContainsEvent ce3 = cef.build(EventCategory.ADDED, c3);

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
		final ContainsEvent ce1 = cef.build(EventCategory.ADDED, c1);
		final ContainsEvent ce2 = cef.build(EventCategory.ADDED, c2);
		final ContainsEvent ce3 = cef.build(EventCategory.ADDED, c3);

		assertThat(ce1.toString(), is(not(equalTo(ce2.toString()))));
		assertThat(ce1.toString(), is(equalTo(ce3.toString())));
	}

}
