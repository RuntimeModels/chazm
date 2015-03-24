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

@SuppressWarnings({ "javadoc", "serial" })
public class InstanceGoalTest {

	private final Injector injector = Guice.createInjector(new EntityModule());
	private final SpecificationGoalFactory specificationGoalFactory = injector.getInstance(SpecificationGoalFactory.class);
	private final InstanceGoalFactory instanceGoalFactory = injector.getInstance(InstanceGoalFactory.class);
	private final IdFactory idFactory = injector.getInstance(IdFactory.class);

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testInstanceGoal() {
		final UniqueId<SpecificationGoal> x1 = idFactory.build(SpecificationGoal.class, 1L);
		final SpecificationGoal y1 = specificationGoalFactory.buildSpecificationGoal(x1);

		final UniqueId<InstanceGoal> i1 = idFactory.build(InstanceGoal.class, 1L);
		final InstanceGoal g1 = instanceGoalFactory.buildInstanceGoal(i1, y1, new InstanceGoal.Parameter() {});

		assertThat(g1, is(not(nullValue())));
	}

	@Test
	public void testInstanceGoal1() {
		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		instanceGoalFactory.buildInstanceGoal(null, null, null);
	}

	@Test
	public void testInstanceGoal2() {
		exception.expect(instanceOf(IllegalArgumentException.class));
		exception.expectMessage(equalTo("Parameter (id) cannot be null"));

		new InstanceGoalEntity(null, null, null);
	}

	@Test
	public void testInstanceGoal3() {
		final UniqueId<InstanceGoal> i1 = idFactory.build(InstanceGoal.class, 1L);

		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		instanceGoalFactory.buildInstanceGoal(i1, null, null);
	}

	@Test
	public void testInstanceGoal4() {
		final UniqueId<InstanceGoal> i1 = idFactory.build(InstanceGoal.class, 1L);

		exception.expect(instanceOf(IllegalArgumentException.class));
		exception.expectMessage(equalTo("Parameter (goal) cannot be null"));

		new InstanceGoalEntity(i1, null, null);
	}

	@Test
	public void testInstanceGoal5() {
		final UniqueId<SpecificationGoal> x1 = idFactory.build(SpecificationGoal.class, 1L);
		final SpecificationGoal y1 = specificationGoalFactory.buildSpecificationGoal(x1);

		final UniqueId<InstanceGoal> i1 = idFactory.build(InstanceGoal.class, 1L);

		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		instanceGoalFactory.buildInstanceGoal(i1, y1, null);
	}

	@Test
	public void testInstanceGoal6() {
		final UniqueId<SpecificationGoal> x1 = idFactory.build(SpecificationGoal.class, 1L);
		final SpecificationGoal y1 = specificationGoalFactory.buildSpecificationGoal(x1);

		final UniqueId<InstanceGoal> i1 = idFactory.build(InstanceGoal.class, 1L);

		exception.expect(instanceOf(IllegalArgumentException.class));
		exception.expectMessage(equalTo("Parameter (parameter) cannot be null"));

		new InstanceGoalEntity(i1, y1, null);
	}

	@Test
	public void testGetGoal() {
		final UniqueId<SpecificationGoal> x1 = idFactory.build(SpecificationGoal.class, 1L);
		final UniqueId<SpecificationGoal> x2 = idFactory.build(SpecificationGoal.class, 2L);
		final SpecificationGoal y1 = specificationGoalFactory.buildSpecificationGoal(x1);
		final SpecificationGoal y2 = specificationGoalFactory.buildSpecificationGoal(x2);

		final UniqueId<InstanceGoal> i1 = idFactory.build(InstanceGoal.class, 1L);
		final UniqueId<InstanceGoal> i2 = idFactory.build(InstanceGoal.class, 2L);
		final InstanceGoal g1 = instanceGoalFactory.buildInstanceGoal(i1, y1, new InstanceGoal.Parameter() {});
		final InstanceGoal g2 = instanceGoalFactory.buildInstanceGoal(i2, y2, new InstanceGoal.Parameter() {});

		assertThat(g1.getGoal(), is(sameInstance(y1)));
		assertThat(g1.getGoal(), is(not(sameInstance(y2))));
		assertThat(g1.getGoal(), is(not(equalTo(g2.getGoal()))));
	}

	@Test
	public void testGetParameter() {
		final UniqueId<SpecificationGoal> x1 = idFactory.build(SpecificationGoal.class, 1L);
		final SpecificationGoal y1 = specificationGoalFactory.buildSpecificationGoal(x1);

		final UniqueId<InstanceGoal> i1 = idFactory.build(InstanceGoal.class, 1L);
		final InstanceGoal.Parameter p1 = new InstanceGoal.Parameter() {};
		final InstanceGoal g1 = instanceGoalFactory.buildInstanceGoal(i1, y1, p1);

		assertThat(g1.getParameter(), is(sameInstance(p1)));
	}

	@Test
	public void testGetId() {
		final UniqueId<SpecificationGoal> x1 = idFactory.build(SpecificationGoal.class, 1L);
		final UniqueId<SpecificationGoal> x2 = idFactory.build(SpecificationGoal.class, 1L);
		final SpecificationGoal y1 = specificationGoalFactory.buildSpecificationGoal(x1);
		final SpecificationGoal y2 = specificationGoalFactory.buildSpecificationGoal(x2);

		final UniqueId<InstanceGoal> i1 = idFactory.build(InstanceGoal.class, 1L);
		final UniqueId<InstanceGoal> i2 = idFactory.build(InstanceGoal.class, 1L);
		final InstanceGoal g1 = instanceGoalFactory.buildInstanceGoal(i1, y1, new InstanceGoal.Parameter() {});
		final InstanceGoal g2 = instanceGoalFactory.buildInstanceGoal(i2, y2, new InstanceGoal.Parameter() {});

		assertThat(g1.getId(), is(sameInstance(i1)));
		assertThat(g1.getId(), is(not(sameInstance(g2.getId()))));
	}

	@Test
	public void testEqualsObject() {
		final UniqueId<SpecificationGoal> x1 = idFactory.build(SpecificationGoal.class, 1L);
		final UniqueId<SpecificationGoal> x2 = idFactory.build(SpecificationGoal.class, 2L);
		final SpecificationGoal y1 = specificationGoalFactory.buildSpecificationGoal(x1);
		final SpecificationGoal y2 = specificationGoalFactory.buildSpecificationGoal(x2);
		final SpecificationGoal y3 = specificationGoalFactory.buildSpecificationGoal(x1);

		final UniqueId<InstanceGoal> i1 = idFactory.build(InstanceGoal.class, 1L);
		final UniqueId<InstanceGoal> i2 = idFactory.build(InstanceGoal.class, 2L);
		final InstanceGoal g1 = instanceGoalFactory.buildInstanceGoal(i1, y1, new InstanceGoal.Parameter() {});
		final InstanceGoal g2 = instanceGoalFactory.buildInstanceGoal(i2, y2, new InstanceGoal.Parameter() {});
		final InstanceGoal g3 = instanceGoalFactory.buildInstanceGoal(i1, y3, new InstanceGoal.Parameter() {});

		assertThat(g1, is(not(equalTo(i1))));
		assertThat(g2, is(not(equalTo(i2))));
		assertThat(g1, is(not(equalTo(g2))));
		assertThat(g1, is(not(sameInstance(g3))));
		assertThat(g1, is(equalTo(g3)));
		assertThat(g1, is(not(equalTo(""))));
	}

	@Test
	public void testHashCode() {
		final UniqueId<SpecificationGoal> x1 = idFactory.build(SpecificationGoal.class, 1L);
		final UniqueId<SpecificationGoal> x2 = idFactory.build(SpecificationGoal.class, 2L);
		final SpecificationGoal y1 = specificationGoalFactory.buildSpecificationGoal(x1);
		final SpecificationGoal y2 = specificationGoalFactory.buildSpecificationGoal(x2);

		final UniqueId<InstanceGoal> i1 = idFactory.build(InstanceGoal.class, 1L);
		final UniqueId<InstanceGoal> i2 = idFactory.build(InstanceGoal.class, 2L);
		final InstanceGoal g1 = instanceGoalFactory.buildInstanceGoal(i1, y1, new InstanceGoal.Parameter() {});
		final InstanceGoal g2 = instanceGoalFactory.buildInstanceGoal(i2, y2, new InstanceGoal.Parameter() {});

		assertThat(g1.hashCode(), is(equalTo(i1.hashCode())));
		assertThat(g2.hashCode(), is(equalTo(i2.hashCode())));
		assertThat(g1.hashCode(), is(not(equalTo(g2.hashCode()))));
	}

	@Test
	public void testToString() {
		final UniqueId<SpecificationGoal> x1 = idFactory.build(SpecificationGoal.class, 1L);
		final UniqueId<SpecificationGoal> x2 = idFactory.build(SpecificationGoal.class, 2L);
		final SpecificationGoal y1 = specificationGoalFactory.buildSpecificationGoal(x1);
		final SpecificationGoal y2 = specificationGoalFactory.buildSpecificationGoal(x2);

		final UniqueId<InstanceGoal> i1 = idFactory.build(InstanceGoal.class, 1L);
		final UniqueId<InstanceGoal> i2 = idFactory.build(InstanceGoal.class, 2L);
		final InstanceGoal.Parameter p1 = new InstanceGoal.Parameter() {
			@Override
			public String toString() {
				return "1";
			}
		};
		final InstanceGoal.Parameter p2 = new InstanceGoal.Parameter() {
			@Override
			public String toString() {
				return "2";
			}
		};
		final InstanceGoal g1 = instanceGoalFactory.buildInstanceGoal(i1, y1, p1);
		final InstanceGoal g2 = instanceGoalFactory.buildInstanceGoal(i2, y2, p2);

		assertThat(g1.toString(), is(equalTo(String.format("%s (%s)", i1.toString(), p1.toString()))));
		assertThat(g2.toString(), is(equalTo(String.format("%s (%s)", i2.toString(), p2.toString()))));
		assertThat(g1.toString(), is(not(equalTo(g2.toString()))));
	}
}
