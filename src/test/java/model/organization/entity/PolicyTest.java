package model.organization.entity;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import model.organization.id.IdFactory;
import model.organization.id.UniqueId;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;

@SuppressWarnings("javadoc")
public class PolicyTest {

	private final Injector injector = Guice.createInjector(new EntityModule());
	private final PolicyFactory policyFactory = injector.getInstance(PolicyFactory.class);
	private final IdFactory idFactory = injector.getInstance(IdFactory.class);

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testPolicy() {
		final UniqueId<Policy> i1 = idFactory.build(Policy.class, 1L);
		final Policy p1 = policyFactory.buildPolicy(i1);
		assertThat(p1, is(not(nullValue())));
	}

	@Test
	public void testPolicy1() {
		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));
		policyFactory.buildPolicy(null);
	}

	@Test
	public void testPolicy2() {
		exception.expect(instanceOf(IllegalArgumentException.class));
		exception.expectMessage(equalTo("Parameter (id) cannot be null"));
		new PolicyEntity(null);
	}

	@Test
	public void testGetId() {
		final UniqueId<Policy> i1 = idFactory.build(Policy.class, 1L);
		final UniqueId<Policy> i2 = idFactory.build(Policy.class, 1L);
		final Policy p1 = policyFactory.buildPolicy(i1);
		final Policy p2 = policyFactory.buildPolicy(i2);

		assertThat(p1.getId(), is(sameInstance(i1)));
		assertThat(p1.getId(), is(not(sameInstance(p2.getId()))));
	}

	@Test
	public void testEqualsObject() {
		final UniqueId<Policy> i1 = idFactory.build(Policy.class, 1L);
		final UniqueId<Policy> i2 = idFactory.build(Policy.class, 2L);
		final Policy p1 = policyFactory.buildPolicy(i1);
		final Policy p2 = policyFactory.buildPolicy(i2);
		final Policy p3 = policyFactory.buildPolicy(i1);

		assertThat(p1, is(not(equalTo(i1))));
		assertThat(p2, is(not(equalTo(i2))));
		assertThat(p1, is(not(equalTo(p2))));
		assertThat(p1, is(not(sameInstance(p3))));
		assertThat(p1, is(equalTo(p3)));
		assertThat(p1, is(not(equalTo(""))));
	}

	@Test
	public void testHashCode() {
		final UniqueId<Policy> i1 = idFactory.build(Policy.class, 1L);
		final UniqueId<Policy> i2 = idFactory.build(Policy.class, 2L);
		final Policy p1 = policyFactory.buildPolicy(i1);
		final Policy p2 = policyFactory.buildPolicy(i2);

		assertThat(p1.hashCode(), is(equalTo(i1.hashCode())));
		assertThat(p2.hashCode(), is(equalTo(i2.hashCode())));
		assertThat(p1.hashCode(), is(not(equalTo(p2.hashCode()))));
	}

	@Test
	public void testToString() {
		final UniqueId<Policy> i1 = idFactory.build(Policy.class, 1L);
		final UniqueId<Policy> i2 = idFactory.build(Policy.class, 2L);
		final Policy p1 = policyFactory.buildPolicy(i1);
		final Policy p2 = policyFactory.buildPolicy(i2);

		assertThat(p1.toString(), is(equalTo(i1.toString())));
		assertThat(p2.toString(), is(equalTo(i2.toString())));
		assertThat(p1.toString(), is(not(equalTo(p2.toString()))));
	}

}
