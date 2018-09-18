package io.github.runtimemodels.chazm.event;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import io.github.runtimemodels.chazm.entity.AgentFactory;
import io.github.runtimemodels.chazm.entity.InstanceGoalFactory;
import io.github.runtimemodels.chazm.entity.RoleFactory;
import io.github.runtimemodels.chazm.entity.SpecificationGoalFactory;
import io.github.runtimemodels.chazm.id.IdFactory;
import io.github.runtimemodels.chazm.relation.AssignmentFactory;
import io.github.runtimemodels.chazm.relation.RelationModule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import runtimemodels.chazm.api.entity.Agent;
import runtimemodels.chazm.api.entity.InstanceGoal;
import runtimemodels.chazm.api.entity.Role;
import runtimemodels.chazm.api.entity.SpecificationGoal;
import runtimemodels.chazm.api.relation.Assignment;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@SuppressWarnings({"javadoc", "serial"})
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
    public void testAssignmentEventFactory() {
        final Agent aa = agf.buildAgent(idf.build(Agent.class, "aa"), new Agent.ContactInfo() {
        });
        final Role r = rf.buildRole(idf.build(Role.class, "r"));
        final SpecificationGoal sg = sgf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "sg"));
        final InstanceGoal ig = igf.buildInstanceGoal(idf.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {
        });
        final Assignment a = af.buildAssignment(aa, r, ig);
        final AssignmentEvent ae1 = aef.build(EventCategory.ADDED, a);
        final AssignmentEvent ae2 = aef.build(EventCategory.ADDED, a);

        assertThat(ae1, is(not(nullValue())));
        assertThat(ae1, is(not(sameInstance(ae2))));
    }

    @Test
    public void testAssignmentEventFactoryWithNullCategoryAndNullAssignment() {
        exception.expect(instanceOf(ProvisionException.class));
//        exception.expectMessage(allOf(
//                containsString("1st parameter of io.github.runtimemodels.chazm.event.AssignmentEvent.<init>(AssignmentEvent.java:32) is not @Nullable"),
//                containsString("2nd parameter of io.github.runtimemodels.chazm.event.AssignmentEvent.<init>(AssignmentEvent.java:32) is not @Nullable")
//        ));

        aef.build(null, null);
    }

    @Test
    public void testAssignmentEventFactoryWithNullAssignment() {
        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage((containsString("2nd parameter of io.github.runtimemodels.chazm.event.AssignmentEvent.<init>(AssignmentEvent.java:32) is not @Nullable")));

        aef.build(EventCategory.ADDED, null);
    }

    @Test
    public void testGetAgentId() {
        final Agent aa = agf.buildAgent(idf.build(Agent.class, "aa"), new Agent.ContactInfo() {
        });
        final Role r = rf.buildRole(idf.build(Role.class, "r"));
        final SpecificationGoal sg = sgf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "sg"));
        final InstanceGoal ig = igf.buildInstanceGoal(idf.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {
        });
        final Assignment a = af.buildAssignment(aa, r, ig);
        final AssignmentEvent ae = aef.build(EventCategory.ADDED, a);

        assertThat(ae.getAgentId(), is(sameInstance(aa.getId())));
    }

    @Test
    public void testGetRoleId() {
        final Agent aa = agf.buildAgent(idf.build(Agent.class, "aa"), new Agent.ContactInfo() {
        });
        final Role r = rf.buildRole(idf.build(Role.class, "r"));
        final SpecificationGoal sg = sgf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "sg"));
        final InstanceGoal ig = igf.buildInstanceGoal(idf.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {
        });
        final Assignment a = af.buildAssignment(aa, r, ig);
        final AssignmentEvent ae = aef.build(EventCategory.ADDED, a);

        assertThat(ae.getRoleId(), is(sameInstance(r.getId())));
    }

    @Test
    public void testGetGoalId() {
        final Agent aa = agf.buildAgent(idf.build(Agent.class, "aa"), new Agent.ContactInfo() {
        });
        final Role r = rf.buildRole(idf.build(Role.class, "r"));
        final SpecificationGoal sg = sgf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "sg"));
        final InstanceGoal ig = igf.buildInstanceGoal(idf.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {
        });
        final Assignment a = af.buildAssignment(aa, r, ig);
        final AssignmentEvent ae = aef.build(EventCategory.ADDED, a);

        assertThat(ae.getGoalId(), is(sameInstance(ig.getId())));
    }

    @Test
    public void testEquals() {
        final Agent aa = agf.buildAgent(idf.build(Agent.class, "aa"), new Agent.ContactInfo() {
        });
        final Role r1 = rf.buildRole(idf.build(Role.class, "r1"));
        final Role r2 = rf.buildRole(idf.build(Role.class, "r2"));
        final SpecificationGoal sg = sgf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "sg"));
        final InstanceGoal ig = igf.buildInstanceGoal(idf.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {
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
        final Agent aa = agf.buildAgent(idf.build(Agent.class, "aa"), new Agent.ContactInfo() {
        });
        final Role r1 = rf.buildRole(idf.build(Role.class, "r1"));
        final Role r2 = rf.buildRole(idf.build(Role.class, "r2"));
        final SpecificationGoal sg = sgf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "sg"));
        final InstanceGoal ig = igf.buildInstanceGoal(idf.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {
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
        final Agent aa = agf.buildAgent(idf.build(Agent.class, "aa"), new Agent.ContactInfo() {
        });
        final Role r1 = rf.buildRole(idf.build(Role.class, "r1"));
        final Role r2 = rf.buildRole(idf.build(Role.class, "r2"));
        final SpecificationGoal sg = sgf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "sg"));
        final InstanceGoal ig = igf.buildInstanceGoal(idf.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {
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
