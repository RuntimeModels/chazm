package model.organization.function;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashSet;

import model.organization.Organization;
import model.organization.OrganizationModule;
import model.organization.entity.Agent;
import model.organization.entity.Attribute;
import model.organization.entity.Capability;
import model.organization.entity.EntityFactory;
import model.organization.entity.InstanceGoal;
import model.organization.entity.Role;
import model.organization.entity.SpecificationGoal;
import model.organization.id.IdFactory;
import model.organization.relation.Possesses;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;

@SuppressWarnings({ "javadoc", "serial" })
public class GoodnessTest {

	private final Injector injector = Guice.createInjector(new OrganizationModule(), new FunctionModule());
	private final Provider<Organization> provider = injector.getProvider(Organization.class);
	private final IdFactory idFactory = injector.getInstance(IdFactory.class);
	private final EntityFactory entityFactory = injector.getInstance(EntityFactory.class);
	private final Goodness goodness = injector.getInstance(Goodness.class);

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testCompute() {
		final Organization o = provider.get();
		final Agent a = entityFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Role r = entityFactory.buildRole(idFactory.build(Role.class, "r"));
		final SpecificationGoal sg = entityFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "sg"));
		final InstanceGoal ig = entityFactory.buildInstanceGoal(idFactory.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {});
		final Capability c1 = entityFactory.buildCapability(idFactory.build(Capability.class, "c1"));
		final Capability c2 = entityFactory.buildCapability(idFactory.build(Capability.class, "c2"));
		final Attribute t = entityFactory.buildAttribute(idFactory.build(Attribute.class, "t"), Attribute.Type.NEGATIVE_QUALITY);

		assertThat(goodness.compute(o, a, r, ig, new HashSet<>()), is(equalTo(Goodness.MIN_SCORE)));

		o.addAgent(a);
		o.addRole(r);
		o.addSpecificationGoal(sg);
		o.addInstanceGoal(ig);
		o.addCapability(c1);
		o.addAchieves(r.getId(), sg.getId());

		assertThat(goodness.compute(o, a, r, ig, new HashSet<>()), is(equalTo(Goodness.MAX_SCORE)));

		o.addRequires(r.getId(), c1.getId());

		assertThat(goodness.compute(o, a, r, ig, new HashSet<>()), is(equalTo(Goodness.MIN_SCORE)));

		o.addPossesses(a.getId(), c1.getId(), Possesses.MIN_SCORE);

		assertThat(goodness.compute(o, a, r, ig, new HashSet<>()), is(equalTo(Goodness.MIN_SCORE)));

		o.setPossessesScore(a.getId(), c1.getId(), Possesses.MAX_SCORE);

		assertThat(goodness.compute(o, a, r, ig, new HashSet<>()), is(equalTo(Goodness.MAX_SCORE)));

		o.addCapability(c2);
		o.addRequires(r.getId(), c2.getId());
		o.addPossesses(a.getId(), c2.getId(), Possesses.MAX_SCORE);

		assertThat(goodness.compute(o, a, r, ig, new HashSet<>()), is(equalTo(Goodness.MAX_SCORE)));

		o.addAttribute(t);
		o.addNeeds(r.getId(), t.getId());

		assertThat(goodness.compute(o, a, r, ig, new HashSet<>()), is(equalTo(Goodness.MIN_SCORE)));
	}

	@Test
	public void testCompute1() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(equalTo("Parameter (arg0) cannot be null"));

		goodness.compute(null, null, null, null, null);
	}

	@Test
	public void testCompute2() {
		final Organization o = provider.get();

		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(equalTo("Parameter (arg1) cannot be null"));

		goodness.compute(o, null, null, null, null);
	}

	@Test
	public void testCompute3() {
		final Organization o = provider.get();
		final Agent a = entityFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});

		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(equalTo("Parameter (arg2) cannot be null"));

		goodness.compute(o, a, null, null, null);
	}

	@Test
	public void testCompute4() {
		final Organization o = provider.get();
		final Agent a = entityFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Role r = entityFactory.buildRole(idFactory.build(Role.class, "r"));

		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(equalTo("Parameter (arg3) cannot be null"));

		goodness.compute(o, a, r, null, null);
	}

	@Test
	public void testCompute5() {
		final Organization o = provider.get();
		final Agent a = entityFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Role r = entityFactory.buildRole(idFactory.build(Role.class, "r"));
		final SpecificationGoal sg = entityFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "sg"));
		final InstanceGoal ig = entityFactory.buildInstanceGoal(idFactory.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {});

		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(equalTo("Parameter (arg4) cannot be null"));

		goodness.compute(o, a, r, ig, null);
	}

}
