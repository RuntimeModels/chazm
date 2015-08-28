package io.github.runtimemodels.chazm.event;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import io.github.runtimemodels.chazm.entity.Policy;
import io.github.runtimemodels.chazm.entity.PolicyFactory;
import io.github.runtimemodels.chazm.event.EventCategory;
import io.github.runtimemodels.chazm.event.EventModule;
import io.github.runtimemodels.chazm.event.PolicyEvent;
import io.github.runtimemodels.chazm.event.PolicyEventFactory;
import io.github.runtimemodels.chazm.id.IdFactory;
import io.github.runtimemodels.chazm.relation.RelationModule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;

@SuppressWarnings("javadoc")
public class PolicyEventTest {

	private final Injector injector = Guice.createInjector(new RelationModule(), new EventModule());
	private final PolicyEventFactory pef = injector.getInstance(PolicyEventFactory.class);
	private final PolicyFactory pf = injector.getInstance(PolicyFactory.class);
	private final IdFactory idf = injector.getInstance(IdFactory.class);

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testPolicyEvent() {
		final Policy p = pf.buildPolicy(idf.build(Policy.class, "p"));
		final PolicyEvent pe1 = pef.build(EventCategory.ADDED, p);
		final PolicyEvent pe2 = pef.build(EventCategory.ADDED, p);

		assertThat(pe1, is(not(nullValue())));
		assertThat(pe1, is(not(sameInstance(pe2))));
	}

	@Test
	public void testPolicyEvent1() {
		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		pef.build(null, null);
	}

	@Test
	public void testPolicyEvent2() {
		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		pef.build(EventCategory.ADDED, null);
	}

	@Test
	public void testGetId() {
		final Policy p = pf.buildPolicy(idf.build(Policy.class, "p"));
		final PolicyEvent pe = pef.build(EventCategory.ADDED, p);

		assertThat(pe.getId(), is(sameInstance(p.getId())));
	}

	@Test
	public void testEquals() {
		final Policy p1 = pf.buildPolicy(idf.build(Policy.class, "p1"));
		final Policy p2 = pf.buildPolicy(idf.build(Policy.class, "p2"));
		final Policy p3 = pf.buildPolicy(idf.build(Policy.class, "p1"));
		final PolicyEvent pe1 = pef.build(EventCategory.ADDED, p1);
		final PolicyEvent pe2 = pef.build(EventCategory.ADDED, p2);
		final PolicyEvent pe3 = pef.build(EventCategory.ADDED, p3);

		assertThat(pe1, is(not(equalTo(pe2))));
		assertThat(pe1, is(equalTo(pe3)));
		assertThat(pe1, is(not(equalTo(""))));
	}

	@Test
	public void testHashCode() {
		final Policy p1 = pf.buildPolicy(idf.build(Policy.class, "p1"));
		final Policy p2 = pf.buildPolicy(idf.build(Policy.class, "p2"));
		final Policy p3 = pf.buildPolicy(idf.build(Policy.class, "p1"));
		final PolicyEvent pe1 = pef.build(EventCategory.ADDED, p1);
		final PolicyEvent pe2 = pef.build(EventCategory.ADDED, p2);
		final PolicyEvent pe3 = pef.build(EventCategory.ADDED, p3);

		assertThat(pe1.hashCode(), is(not(equalTo(pe2.hashCode()))));
		assertThat(pe1.hashCode(), is(equalTo(pe3.hashCode())));
	}

	@Test
	public void testToString() {
		final Policy p1 = pf.buildPolicy(idf.build(Policy.class, "p1"));
		final Policy p2 = pf.buildPolicy(idf.build(Policy.class, "p2"));
		final Policy p3 = pf.buildPolicy(idf.build(Policy.class, "p1"));
		final PolicyEvent pe1 = pef.build(EventCategory.ADDED, p1);
		final PolicyEvent pe2 = pef.build(EventCategory.ADDED, p2);
		final PolicyEvent pe3 = pef.build(EventCategory.ADDED, p3);

		assertThat(pe1.toString(), is(not(equalTo(pe2.toString()))));
		assertThat(pe1.toString(), is(equalTo(pe3.toString())));
	}

}
