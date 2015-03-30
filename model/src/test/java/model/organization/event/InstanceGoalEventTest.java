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
import model.organization.entity.InstanceGoal;
import model.organization.entity.InstanceGoalFactory;
import model.organization.entity.SpecificationGoal;
import model.organization.entity.SpecificationGoalFactory;
import model.organization.id.IdFactory;
import model.organization.relation.RelationModule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;

@SuppressWarnings({ "javadoc", "serial" })
public class InstanceGoalEventTest {

	private final Injector injector = Guice.createInjector(new RelationModule(), new EventModule());
	private final InstanceGoalEventFactory igef = injector.getInstance(InstanceGoalEventFactory.class);
	private final InstanceGoalFactory igf = injector.getInstance(InstanceGoalFactory.class);
	private final SpecificationGoalFactory sgf = injector.getInstance(SpecificationGoalFactory.class);
	private final IdFactory idf = injector.getInstance(IdFactory.class);

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testInstanceGoalEvent() {
		final SpecificationGoal sg = sgf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "sg"));
		final InstanceGoal ig = igf.buildInstanceGoal(idf.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {});
		final InstanceGoalEvent ige1 = igef.build(EventCategory.ADDED, ig);
		final InstanceGoalEvent ige2 = igef.build(EventCategory.ADDED, ig);

		assertThat(ige1, is(not(nullValue())));
		assertThat(ige1, is(not(sameInstance(ige2))));
	}

	@Test
	public void testInstanceGoalEvent1() {
		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		igef.build(null, null);
	}

	@Test
	public void testInstanceGoalEvent2() {
		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		igef.build(EventCategory.ADDED, null);
	}

	@Test
	public void testGetId() {
		final SpecificationGoal sg = sgf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "sg"));
		final InstanceGoal ig = igf.buildInstanceGoal(idf.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {});
		final InstanceGoalEvent ige = igef.build(EventCategory.ADDED, ig);

		assertThat(ige.getId(), is(sameInstance(ig.getId())));
	}

	@Test
	public void testGetSpecificationGoalId() {
		final SpecificationGoal sg = sgf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "sg"));
		final InstanceGoal ig = igf.buildInstanceGoal(idf.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {});
		final InstanceGoalEvent ige = igef.build(EventCategory.ADDED, ig);

		assertThat(ige.getSpecificationGoalId(), is(sameInstance(ig.getGoal().getId())));
	}

	@Test
	public void testGetParameter() {
		final SpecificationGoal sg = sgf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "sg"));
		final InstanceGoal ig = igf.buildInstanceGoal(idf.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {});
		final InstanceGoalEvent ige = igef.build(EventCategory.ADDED, ig);

		assertThat(ige.getParameter(), is(sameInstance(ig.getParameter())));
	}

	@Test
	public void testEquals() {
		final SpecificationGoal sg = sgf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "sg"));
		final InstanceGoal ig1 = igf.buildInstanceGoal(idf.build(InstanceGoal.class, "ig1"), sg, new InstanceGoal.Parameter() {});
		final InstanceGoal ig2 = igf.buildInstanceGoal(idf.build(InstanceGoal.class, "ig2"), sg, new InstanceGoal.Parameter() {});
		final InstanceGoal ig3 = igf.buildInstanceGoal(idf.build(InstanceGoal.class, "ig1"), sg, new InstanceGoal.Parameter() {});
		final InstanceGoalEvent ige1 = igef.build(EventCategory.ADDED, ig1);
		final InstanceGoalEvent ige2 = igef.build(EventCategory.ADDED, ig2);
		final InstanceGoalEvent ige3 = igef.build(EventCategory.ADDED, ig3);

		assertThat(ige1, is(not(equalTo(ige2))));
		assertThat(ige1, is(equalTo(ige3)));
		assertThat(ige1, is(not(equalTo(""))));
	}

	@Test
	public void testInstanceGoalhCode() {
		final SpecificationGoal sg = sgf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "sg"));
		final InstanceGoal ig1 = igf.buildInstanceGoal(idf.build(InstanceGoal.class, "ig1"), sg, new InstanceGoal.Parameter() {});
		final InstanceGoal ig2 = igf.buildInstanceGoal(idf.build(InstanceGoal.class, "ig2"), sg, new InstanceGoal.Parameter() {});
		final InstanceGoal ig3 = igf.buildInstanceGoal(idf.build(InstanceGoal.class, "ig1"), sg, new InstanceGoal.Parameter() {});
		final InstanceGoalEvent ige1 = igef.build(EventCategory.ADDED, ig1);
		final InstanceGoalEvent ige2 = igef.build(EventCategory.ADDED, ig2);
		final InstanceGoalEvent ige3 = igef.build(EventCategory.ADDED, ig3);

		assertThat(ige1.hashCode(), is(not(equalTo(ige2.hashCode()))));
		assertThat(ige1.hashCode(), is(equalTo(ige3.hashCode())));
	}

	@Test
	public void testToString() {
		final SpecificationGoal sg = sgf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "sg"));
		final InstanceGoal.Parameter p1 = new InstanceGoal.Parameter() {};
		final InstanceGoal ig1 = igf.buildInstanceGoal(idf.build(InstanceGoal.class, "ig1"), sg, p1);
		final InstanceGoal ig2 = igf.buildInstanceGoal(idf.build(InstanceGoal.class, "ig2"), sg, p1);
		final InstanceGoal ig3 = igf.buildInstanceGoal(idf.build(InstanceGoal.class, "ig1"), sg, p1);
		final InstanceGoalEvent ige1 = igef.build(EventCategory.ADDED, ig1);
		final InstanceGoalEvent ige2 = igef.build(EventCategory.ADDED, ig2);
		final InstanceGoalEvent ige3 = igef.build(EventCategory.ADDED, ig3);

		assertThat(ige1.toString(), is(not(equalTo(ige2.toString()))));
		assertThat(ige1.toString(), is(equalTo(ige3.toString())));
	}

}
