package model.organization.function;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import model.organization.Organization;
import model.organization.OrganizationModule;
import model.organization.entity.Agent;
import model.organization.entity.Capability;
import model.organization.entity.EntityFactory;
import model.organization.entity.InstanceGoal;
import model.organization.entity.Role;
import model.organization.entity.SpecificationGoal;
import model.organization.id.IdFactory;
import model.organization.relation.Assignment;
import model.organization.relation.Possesses;
import model.organization.relation.RelationFactory;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;

@SuppressWarnings({ "javadoc", "serial" })
public class EffectivenessTest {

	private final Injector injector = Guice.createInjector(new OrganizationModule(), new FunctionModule());
	private final Provider<Organization> provider = injector.getProvider(Organization.class);
	private final IdFactory idFactory = injector.getInstance(IdFactory.class);
	private final EntityFactory entityFactory = injector.getInstance(EntityFactory.class);
	private final RelationFactory relationFactory = injector.getInstance(RelationFactory.class);
	private final Effectiveness effectiveness = injector.getInstance(Effectiveness.class);

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testCompute() {
		final Organization o = provider.get();
		final Agent a1 = entityFactory.buildAgent(idFactory.buildId(Agent.class, "a1"), new Agent.ContactInfo() {});
		final Agent a2 = entityFactory.buildAgent(idFactory.buildId(Agent.class, "a2"), new Agent.ContactInfo() {});
		final Role r = entityFactory.buildRole(idFactory.buildId(Role.class, "r"));
		final SpecificationGoal sg = entityFactory.buildSpecificationGoal(idFactory.buildId(SpecificationGoal.class, "sg"));
		final InstanceGoal ig1 = entityFactory.buildInstanceGoal(idFactory.buildId(InstanceGoal.class, "ig1"), sg, new InstanceGoal.Parameter() {});
		final InstanceGoal ig2 = entityFactory.buildInstanceGoal(idFactory.buildId(InstanceGoal.class, "ig2"), sg, new InstanceGoal.Parameter() {});
		final Capability c1 = entityFactory.buildCapability(idFactory.buildId(Capability.class, "c1"));
		final Capability c2 = entityFactory.buildCapability(idFactory.buildId(Capability.class, "c2"));
		final Assignment as1 = relationFactory.buildAssignment(a1, r, ig1);
		final Assignment as2 = relationFactory.buildAssignment(a1, r, ig2);
		final Assignment as3 = relationFactory.buildAssignment(a2, r, ig1);
		final Assignment as4 = relationFactory.buildAssignment(a2, r, ig2);

		assertThat(effectiveness.compute(o, o.getAssignments()), is(equalTo(0.0)));

		o.addAgent(a1);
		o.addAgent(a2);
		o.addRole(r);
		o.addSpecificationGoal(sg);
		o.addInstanceGoal(ig1);
		o.addInstanceGoal(ig2);
		o.addCapability(c1);
		o.addCapability(c2);
		o.addAchieves(r.getId(), sg.getId());
		o.addRequires(r.getId(), c1.getId());
		o.addPossesses(a1.getId(), c1.getId(), Possesses.MAX_SCORE);
		o.addAssignment(as1);

		assertThat(effectiveness.compute(o, o.getAssignments()), is(equalTo(1.0)));

		o.addAssignment(as2);

		assertThat(effectiveness.compute(o, o.getAssignments()), is(equalTo(2.0)));

		o.addAssignment(as3);

		assertThat(effectiveness.compute(o, o.getAssignments()), is(equalTo(2.0)));

		o.addAssignment(as4);

		assertThat(effectiveness.compute(o, o.getAssignments()), is(equalTo(2.0)));
	}

	@Test
	public void testCompute1() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(equalTo("Parameter (organization) cannot be null"));

		effectiveness.compute(null, null);
	}

	@Test
	public void testCompute2() {
		final Organization o = provider.get();

		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(equalTo("Parameter (assignments) cannot be null"));

		effectiveness.compute(o, null);
	}

}