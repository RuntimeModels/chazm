package model.organization.relation;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import model.organization.entity.Agent;
import model.organization.entity.AgentFactory;
import model.organization.entity.InstanceGoal;
import model.organization.entity.InstanceGoalFactory;
import model.organization.entity.Role;
import model.organization.entity.RoleFactory;
import model.organization.entity.SpecificationGoal;
import model.organization.entity.SpecificationGoalFactory;
import model.organization.id.IdFactory;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;

@SuppressWarnings({ "javadoc", "serial" })
public class AssignmentTest {

	private final Injector injector = Guice.createInjector(new RelationModule());
	private final AssignmentFactory assignmentFactory = injector.getInstance(AssignmentFactory.class);
	private final AgentFactory agentFactory = injector.getInstance(AgentFactory.class);
	private final RoleFactory roleFactory = injector.getInstance(RoleFactory.class);
	private final SpecificationGoalFactory specGoalFactory = injector.getInstance(SpecificationGoalFactory.class);
	private final InstanceGoalFactory instGoalFactory = injector.getInstance(InstanceGoalFactory.class);
	private final IdFactory idFactory = injector.getInstance(IdFactory.class);

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testAssignment() {
		final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
		final SpecificationGoal sg = specGoalFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "sg"));
		final InstanceGoal ig = instGoalFactory.buildInstanceGoal(idFactory.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {});
		final Assignment a1 = assignmentFactory.buildAssignment(a, r, ig);
		final Assignment a2 = assignmentFactory.buildAssignment(a, r, ig);

		assertThat(a1, is(not(nullValue())));
		assertThat(a1, is(not(sameInstance(a2))));
	}

	@Test
	public void testAssignment1() {
		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		assignmentFactory.buildAssignment(null, null, null);
	}

	@Test
	public void testAssignment2() {
		exception.expect(instanceOf(IllegalArgumentException.class));
		exception.expectMessage(equalTo("Parameter (agent) cannot be null"));

		new AssignmentRelation(null, null, null);
	}

	@Test
	public void testAssignment3() {
		final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});

		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		assignmentFactory.buildAssignment(a, null, null);
	}

	@Test
	public void testAssignment4() {
		final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});

		exception.expect(instanceOf(IllegalArgumentException.class));
		exception.expectMessage(equalTo("Parameter (role) cannot be null"));

		new AssignmentRelation(a, null, null);
	}

	@Test
	public void testAssignment5() {
		final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));

		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		assignmentFactory.buildAssignment(a, r, null);
	}

	@Test
	public void testAssignment6() {
		final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));

		exception.expect(instanceOf(IllegalArgumentException.class));
		exception.expectMessage(equalTo("Parameter (goal) cannot be null"));

		new AssignmentRelation(a, r, null);
	}

	@Test
	public void testGetAgent() {
		final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
		final SpecificationGoal sg = specGoalFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "sg"));
		final InstanceGoal ig = instGoalFactory.buildInstanceGoal(idFactory.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {});
		final Assignment as = assignmentFactory.buildAssignment(a, r, ig);

		assertThat(as.getAgent(), is(sameInstance(a)));

	}

	@Test
	public void testGetRole() {
		final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
		final SpecificationGoal sg = specGoalFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "sg"));
		final InstanceGoal ig = instGoalFactory.buildInstanceGoal(idFactory.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {});
		final Assignment as = assignmentFactory.buildAssignment(a, r, ig);

		assertThat(as.getRole(), is(sameInstance(r)));
	}

	@Test
	public void testGetGoal() {
		final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
		final SpecificationGoal sg = specGoalFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "sg"));
		final InstanceGoal ig = instGoalFactory.buildInstanceGoal(idFactory.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {});
		final Assignment as = assignmentFactory.buildAssignment(a, r, ig);

		assertThat(as.getGoal(), is(sameInstance(ig)));
	}

	@Test
	public void testEquals() {
		final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Role r1 = roleFactory.buildRole(idFactory.build(Role.class, "r1"));
		final Role r2 = roleFactory.buildRole(idFactory.build(Role.class, "r2"));
		final SpecificationGoal sg = specGoalFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "sg"));
		final InstanceGoal ig = instGoalFactory.buildInstanceGoal(idFactory.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {});
		final Assignment a1 = assignmentFactory.buildAssignment(a, r1, ig);
		final Assignment a2 = assignmentFactory.buildAssignment(a, r2, ig);
		final Assignment a3 = assignmentFactory.buildAssignment(a, r1, ig);

		assertThat(a1, is(not(equalTo(a2))));
		assertThat(a1, is(equalTo(a3)));
		assertThat(a1, is(not(equalTo(""))));
	}

	@Test
	public void testHashCode() {
		final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Role r1 = roleFactory.buildRole(idFactory.build(Role.class, "r1"));
		final Role r2 = roleFactory.buildRole(idFactory.build(Role.class, "r2"));
		final SpecificationGoal sg = specGoalFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "sg"));
		final InstanceGoal ig = instGoalFactory.buildInstanceGoal(idFactory.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {});
		final Assignment a1 = assignmentFactory.buildAssignment(a, r1, ig);
		final Assignment a2 = assignmentFactory.buildAssignment(a, r2, ig);
		final Assignment a3 = assignmentFactory.buildAssignment(a, r1, ig);

		assertThat(a1.hashCode(), is(not(equalTo(a2.hashCode()))));
		assertThat(a1.hashCode(), is(equalTo(a3.hashCode())));
	}

	@Test
	public void testToString() {
		final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {});
		final Role r1 = roleFactory.buildRole(idFactory.build(Role.class, "r1"));
		final Role r2 = roleFactory.buildRole(idFactory.build(Role.class, "r2"));
		final SpecificationGoal sg = specGoalFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "sg"));
		final InstanceGoal ig = instGoalFactory.buildInstanceGoal(idFactory.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {});
		final Assignment a1 = assignmentFactory.buildAssignment(a, r1, ig);
		final Assignment a2 = assignmentFactory.buildAssignment(a, r2, ig);
		final Assignment a3 = assignmentFactory.buildAssignment(a, r1, ig);

		assertThat(a1.toString(), is(not(equalTo(a2.toString()))));
		assertThat(a1.toString(), is(equalTo(a3.toString())));
	}

}
