package io.github.chriszhong.model.organization.event;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import io.github.chriszhong.model.organization.entity.AgentFactory;
import io.github.chriszhong.model.organization.entity.InstanceGoalFactory;
import io.github.chriszhong.model.organization.entity.RoleFactory;
import io.github.chriszhong.model.organization.entity.SpecificationGoalFactory;
import io.github.chriszhong.model.organization.id.IdFactory;
import io.github.chriszhong.model.organization.relation.AssignmentFactory;
import io.github.chriszhong.model.organization.relation.RelationModule;
import io.github.runtimemodels.chazm.entity.Agent;
import io.github.runtimemodels.chazm.entity.InstanceGoal;
import io.github.runtimemodels.chazm.entity.Role;
import io.github.runtimemodels.chazm.entity.SpecificationGoal;
import io.github.runtimemodels.chazm.relation.Assignment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;

@SuppressWarnings({ "javadoc", "serial" })
public class AssignmentEventTest {

	private final Injector injector = Guice.createInjector(new RelationModule(), new EventModule());
	private final AssignmentEventFactory aef = injector.getInstance(AssignmentEventFactory.class);
	private final AssignmentFactory af = injector.getInstance(AssignmentFactory.class);
	private final AgentFactory agf = injector.getInstance(AgentFactory.class);
	private final RoleFactory rf = injector.getInstance(RoleFactory.class);
	private final SpecificationGoalFactory sgf = injector.getInstance(SpecificationGoalFactory.class);
	private final InstanceGoalFactory igf = injector.getInstance(InstanceGoalFactory.class);
	private final IdFactory idf = injector.getInstance(IdFactory.class);

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testAssignmentEvent() {
		final Agent aa = agf.buildAgent(idf.build(Agent.class, "aa"), new Agent.ContactInfo() {});
		final Role r = rf.buildRole(idf.build(Role.class, "r"));
		final SpecificationGoal sg = sgf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "sg"));
		final InstanceGoal ig = igf.buildInstanceGoal(idf.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {

			/**
			 *
			 */
			private static final long serialVersionUID = 4555321588208570740L;
		});
		final Assignment a = af.buildAssignment(aa, r, ig);
		final AssignmentEvent ae1 = aef.build(EventCategory.ADDED, a);
		final AssignmentEvent ae2 = aef.build(EventCategory.ADDED, a);

		assertThat(ae1, is(not(nullValue())));
		assertThat(ae1, is(not(sameInstance(ae2))));
	}

	@Test
	public void testAssignmentEvent1() {
		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		aef.build(null, null);
	}

	@Test
	public void testAssignmentEvent2() {
		exception.expect(instanceOf(ProvisionException.class));
		exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

		aef.build(EventCategory.ADDED, null);
	}

	@Test
	public void testGetAgentId() {
		final Agent aa = agf.buildAgent(idf.build(Agent.class, "aa"), new Agent.ContactInfo() {});
		final Role r = rf.buildRole(idf.build(Role.class, "r"));
		final SpecificationGoal sg = sgf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "sg"));
		final InstanceGoal ig = igf.buildInstanceGoal(idf.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {

			/**
			 *
			 */
			private static final long serialVersionUID = -2655425177027076252L;
		});
		final Assignment a = af.buildAssignment(aa, r, ig);
		final AssignmentEvent ae = aef.build(EventCategory.ADDED, a);

		assertThat(ae.getAgentId(), is(sameInstance(aa.getId())));
	}

	@Test
	public void testGetRoleId() {
		final Agent aa = agf.buildAgent(idf.build(Agent.class, "aa"), new Agent.ContactInfo() {});
		final Role r = rf.buildRole(idf.build(Role.class, "r"));
		final SpecificationGoal sg = sgf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "sg"));
		final InstanceGoal ig = igf.buildInstanceGoal(idf.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {

			/**
			 *
			 */
			private static final long serialVersionUID = -4843772475208669038L;
		});
		final Assignment a = af.buildAssignment(aa, r, ig);
		final AssignmentEvent ae = aef.build(EventCategory.ADDED, a);

		assertThat(ae.getRoleId(), is(sameInstance(r.getId())));
	}

	@Test
	public void testGetGoalId() {
		final Agent aa = agf.buildAgent(idf.build(Agent.class, "aa"), new Agent.ContactInfo() {});
		final Role r = rf.buildRole(idf.build(Role.class, "r"));
		final SpecificationGoal sg = sgf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "sg"));
		final InstanceGoal ig = igf.buildInstanceGoal(idf.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {

			/**
			 *
			 */
			private static final long serialVersionUID = 1441325266785662702L;
		});
		final Assignment a = af.buildAssignment(aa, r, ig);
		final AssignmentEvent ae = aef.build(EventCategory.ADDED, a);

		assertThat(ae.getGoalId(), is(sameInstance(ig.getId())));
	}

	@Test
	public void testEquals() {
		final Agent aa = agf.buildAgent(idf.build(Agent.class, "aa"), new Agent.ContactInfo() {});
		final Role r1 = rf.buildRole(idf.build(Role.class, "r1"));
		final Role r2 = rf.buildRole(idf.build(Role.class, "r2"));
		final SpecificationGoal sg = sgf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "sg"));
		final InstanceGoal ig = igf.buildInstanceGoal(idf.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {

			/**
			 *
			 */
			private static final long serialVersionUID = -3978773697809254587L;
		});
		final Assignment a1 = af.buildAssignment(aa, r1, ig);
		final Assignment a2 = af.buildAssignment(aa, r2, ig);
		final Assignment a3 = af.buildAssignment(aa, r1, ig);
		final AssignmentEvent ae1 = aef.build(EventCategory.ADDED, a1);
		final AssignmentEvent ae2 = aef.build(EventCategory.ADDED, a2);
		final AssignmentEvent ae3 = aef.build(EventCategory.ADDED, a3);

		assertThat(ae1, is(not(equalTo(ae2))));
		assertThat(ae1, is(equalTo(ae3)));
		assertThat(ae1, is(not(equalTo(""))));
	}

	@Test
	public void testHashCode() {
		final Agent aa = agf.buildAgent(idf.build(Agent.class, "aa"), new Agent.ContactInfo() {});
		final Role r1 = rf.buildRole(idf.build(Role.class, "r1"));
		final Role r2 = rf.buildRole(idf.build(Role.class, "r2"));
		final SpecificationGoal sg = sgf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "sg"));
		final InstanceGoal ig = igf.buildInstanceGoal(idf.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {

			/**
			 *
			 */
			private static final long serialVersionUID = -3073103630440762487L;
		});
		final Assignment a1 = af.buildAssignment(aa, r1, ig);
		final Assignment a2 = af.buildAssignment(aa, r2, ig);
		final Assignment a3 = af.buildAssignment(aa, r1, ig);
		final AssignmentEvent ae1 = aef.build(EventCategory.ADDED, a1);
		final AssignmentEvent ae2 = aef.build(EventCategory.ADDED, a2);
		final AssignmentEvent ae3 = aef.build(EventCategory.ADDED, a3);

		assertThat(ae1.hashCode(), is(not(equalTo(ae2.hashCode()))));
		assertThat(ae1.hashCode(), is(equalTo(ae3.hashCode())));
	}

	@Test
	public void testToString() {
		final Agent aa = agf.buildAgent(idf.build(Agent.class, "aa"), new Agent.ContactInfo() {});
		final Role r1 = rf.buildRole(idf.build(Role.class, "r1"));
		final Role r2 = rf.buildRole(idf.build(Role.class, "r2"));
		final SpecificationGoal sg = sgf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "sg"));
		final InstanceGoal ig = igf.buildInstanceGoal(idf.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {

			/**
			 *
			 */
			private static final long serialVersionUID = -6135101209843296571L;
		});
		final Assignment a1 = af.buildAssignment(aa, r1, ig);
		final Assignment a2 = af.buildAssignment(aa, r2, ig);
		final Assignment a3 = af.buildAssignment(aa, r1, ig);
		final AssignmentEvent ae1 = aef.build(EventCategory.ADDED, a1);
		final AssignmentEvent ae2 = aef.build(EventCategory.ADDED, a2);
		final AssignmentEvent ae3 = aef.build(EventCategory.ADDED, a3);

		assertThat(ae1.toString(), is(not(equalTo(ae2.toString()))));
		assertThat(ae1.toString(), is(equalTo(ae3.toString())));
	}

}
