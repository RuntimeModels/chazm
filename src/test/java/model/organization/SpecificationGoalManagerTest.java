package model.organization;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import model.organization.entity.EntityFactory;
import model.organization.entity.SpecificationGoal;
import model.organization.id.IdFactory;
import model.organization.id.UniqueId;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;

@SuppressWarnings("javadoc")
public class SpecificationGoalManagerTest {

	private final Injector injector = Guice.createInjector(new OrganizationModule());
	private final Provider<Organization> provider = injector.getProvider(Organization.class);
	private final EntityFactory entityFactory = injector.getInstance(EntityFactory.class);
	private final IdFactory idFactory = injector.getInstance(IdFactory.class);

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testAddSpecificationGoal() {
		final Organization o = provider.get();
		final UniqueId<SpecificationGoal> i1 = idFactory.buildId(SpecificationGoal.class, "goal1");
		final UniqueId<SpecificationGoal> i2 = idFactory.buildId(SpecificationGoal.class, "");
		final SpecificationGoal g1 = entityFactory.buildSpecificationGoal(i1);
		final SpecificationGoal g2 = entityFactory.buildSpecificationGoal(i2);

		assertThat(o.getSpecificationGoals().size(), is(equalTo(0)));
		o.addSpecificationGoal(g1);
		assertThat(o.getSpecificationGoals().size(), is(equalTo(1)));
		assertThat(o.getSpecificationGoal(i1), is(not(nullValue())));
		assertThat(o.getSpecificationGoal(i2), is(nullValue()));
		o.addSpecificationGoal(g2);
		assertThat(o.getSpecificationGoals().size(), is(equalTo(2)));
	}

	@Test
	public void testAddSpecificationGoal1() {
		final Organization o = provider.get();
		exception.expect(is(instanceOf(IllegalArgumentException.class)));
		exception.expectMessage(equalTo("Parameter (goal) cannot be null"));
		o.addSpecificationGoal(null);
	}

	@Test
	public void testAddSpecificationGoal2() {
		final Organization o = provider.get();
		final UniqueId<SpecificationGoal> i1 = idFactory.buildId(SpecificationGoal.class, "goal1");
		final SpecificationGoal g1 = entityFactory.buildSpecificationGoal(i1);
		final SpecificationGoal g2 = entityFactory.buildSpecificationGoal(i1);
		exception.expect(is(instanceOf(IllegalArgumentException.class)));
		exception.expectMessage(equalTo("(SpecificationGoalEntity) entity (goal1) already exists"));
		assertThat(g1, is(not(sameInstance(g2))));
		o.addSpecificationGoal(g1);
		o.addSpecificationGoal(g2);
	}

}
