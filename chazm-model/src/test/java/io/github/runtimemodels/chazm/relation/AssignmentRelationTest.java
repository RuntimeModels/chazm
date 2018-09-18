package io.github.runtimemodels.chazm.relation;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import io.github.runtimemodels.chazm.entity.AgentFactory;
import io.github.runtimemodels.chazm.entity.InstanceGoalFactory;
import io.github.runtimemodels.chazm.entity.RoleFactory;
import io.github.runtimemodels.chazm.entity.SpecificationGoalFactory;
import io.github.runtimemodels.chazm.id.IdFactory;
import io.github.runtimemodels.chazm.relation.AssignmentRelation.Id;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import runtimemodels.chazm.api.entity.Agent;
import runtimemodels.chazm.api.entity.InstanceGoal;
import runtimemodels.chazm.api.entity.Role;
import runtimemodels.chazm.api.entity.SpecificationGoal;
import runtimemodels.chazm.api.id.UniqueId;
import runtimemodels.chazm.api.relation.Assignment;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@SuppressWarnings({"javadoc", "serial"})
public class AssignmentRelationTest {

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
    public void testAssignmentRelationFactory() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {
        });
        final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
        final SpecificationGoal sg = specGoalFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "sg"));
        final InstanceGoal ig = instGoalFactory.buildInstanceGoal(idFactory.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {
        });
        final Assignment a1 = assignmentFactory.buildAssignment(a, r, ig);
        final Assignment a2 = assignmentFactory.buildAssignment(a, r, ig);

        assertThat(a1, is(not(nullValue())));
        assertThat(a1, is(not(sameInstance(a2))));
    }

    @Test
    public void testAssignmentRelationFactoryWithNullAgentAndNullRoleAndNullGoal() {
        exception.expect(instanceOf(ProvisionException.class));
//        exception.expectMessage(allOf(
//                containsString("1st InstanceGoal.Parameter of io.github.runtimemodels.chazm.relation.AssignmentRelation.<init>(AssignmentRelation.java:134) is not @Nullable"),
//                containsString("2nd InstanceGoal.Parameter of io.github.runtimemodels.chazm.relation.AssignmentRelation.<init>(AssignmentRelation.java:134) is not @Nullable"),
//                containsString("3rd InstanceGoal.Parameter of io.github.runtimemodels.chazm.relation.AssignmentRelation.<init>(AssignmentRelation.java:134) is not @Nullable")
//        ));

        assignmentFactory.buildAssignment(null, null, null);
    }

    @Test
    public void testAssignmentRelationFactoryWithNullRoleAndNullGoal() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {
        });

        exception.expect(instanceOf(ProvisionException.class));
//        exception.expectMessage(allOf(
//                containsString("2nd InstanceGoal.Parameter of io.github.runtimemodels.chazm.relation.AssignmentRelation.<init>(AssignmentRelation.java:134) is not @Nullable"),
//                containsString("3rd InstanceGoal.Parameter of io.github.runtimemodels.chazm.relation.AssignmentRelation.<init>(AssignmentRelation.java:134) is not @Nullable")
//        ));

        assignmentFactory.buildAssignment(a, null, null);
    }

    @Test
    public void testAssignmentRelationFactoryWithNullGoal() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {
        });
        final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));

        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(containsString("3rd InstanceGoal.Parameter of io.github.runtimemodels.chazm.relation.AssignmentRelation.<init>(AssignmentRelation.java:134) is not @Nullable"));

        assignmentFactory.buildAssignment(a, r, null);
    }

    @Test
    public void testGetId() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {
        });
        final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
        final SpecificationGoal sg = specGoalFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "sg"));
        final InstanceGoal ig = instGoalFactory.buildInstanceGoal(idFactory.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {
        });
        final Assignment as = assignmentFactory.buildAssignment(a, r, ig);

        assertThat(as.getId(), is(not(nullValue())));
    }

    @Test
    public void testGetAgent() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {
        });
        final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
        final SpecificationGoal sg = specGoalFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "sg"));
        final InstanceGoal ig = instGoalFactory.buildInstanceGoal(idFactory.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {
        });
        final Assignment as = assignmentFactory.buildAssignment(a, r, ig);

        assertThat(as.getAgent(), is(sameInstance(a)));

    }

    @Test
    public void testGetRole() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {
        });
        final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
        final SpecificationGoal sg = specGoalFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "sg"));
        final InstanceGoal ig = instGoalFactory.buildInstanceGoal(idFactory.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {
        });
        final Assignment as = assignmentFactory.buildAssignment(a, r, ig);

        assertThat(as.getRole(), is(sameInstance(r)));
    }

    @Test
    public void testGetGoal() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {
        });
        final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
        final SpecificationGoal sg = specGoalFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "sg"));
        final InstanceGoal ig = instGoalFactory.buildInstanceGoal(idFactory.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {
        });
        final Assignment as = assignmentFactory.buildAssignment(a, r, ig);

        assertThat(as.getGoal(), is(sameInstance(ig)));
    }

    @Test
    public void testEquals() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {
        });
        final Role r1 = roleFactory.buildRole(idFactory.build(Role.class, "r1"));
        final Role r2 = roleFactory.buildRole(idFactory.build(Role.class, "r2"));
        final SpecificationGoal sg = specGoalFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "sg"));
        final InstanceGoal ig = instGoalFactory.buildInstanceGoal(idFactory.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {
        });
        final Assignment a1 = assignmentFactory.buildAssignment(a, r1, ig);
        final Assignment a2 = assignmentFactory.buildAssignment(a, r2, ig);
        final Assignment a3 = assignmentFactory.buildAssignment(a, r1, ig);

        assertThat(a1, is(not(equalTo(a2))));
        assertThat(a1, is(equalTo(a3)));
        assertThat(a1, is(not(equalTo(""))));
    }

    @Test
    public void testHashCode() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {
        });
        final Role r1 = roleFactory.buildRole(idFactory.build(Role.class, "r1"));
        final Role r2 = roleFactory.buildRole(idFactory.build(Role.class, "r2"));
        final SpecificationGoal sg = specGoalFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "sg"));
        final InstanceGoal ig = instGoalFactory.buildInstanceGoal(idFactory.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {
        });
        final Assignment a1 = assignmentFactory.buildAssignment(a, r1, ig);
        final Assignment a2 = assignmentFactory.buildAssignment(a, r2, ig);
        final Assignment a3 = assignmentFactory.buildAssignment(a, r1, ig);

        assertThat(a1.hashCode(), is(not(equalTo(a2.hashCode()))));
        assertThat(a1.hashCode(), is(equalTo(a3.hashCode())));
    }

    @Test
    public void testToString() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {
        });
        final Role r1 = roleFactory.buildRole(idFactory.build(Role.class, "r1"));
        final Role r2 = roleFactory.buildRole(idFactory.build(Role.class, "r2"));
        final SpecificationGoal sg = specGoalFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "sg"));
        final InstanceGoal ig = instGoalFactory.buildInstanceGoal(idFactory.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {
        });
        final Assignment a1 = assignmentFactory.buildAssignment(a, r1, ig);
        final Assignment a2 = assignmentFactory.buildAssignment(a, r2, ig);
        final Assignment a3 = assignmentFactory.buildAssignment(a, r1, ig);

        assertThat(a1.toString(), is(not(equalTo(a2.toString()))));
        assertThat(a1.toString(), is(equalTo(a3.toString())));
    }

    @Test
    public void testAssignmentId() {
        final Id i = new Id(idFactory.build(Agent.class, "a"), idFactory.build(Role.class, "r"), idFactory.build(
                InstanceGoal.class, "g"));

        assertThat(i, is(not(nullValue())));
    }

    @Test
    public void testAssignmentIdEquals() {
        final UniqueId<Agent> x = idFactory.build(Agent.class, "a");
        final UniqueId<Role> y1 = idFactory.build(Role.class, "r1");
        final UniqueId<Role> y2 = idFactory.build(Role.class, "r2");
        final UniqueId<InstanceGoal> z = idFactory.build(InstanceGoal.class, "g");
        final Id i1 = new Id(x, y1, z);
        final Id i2 = new Id(x, y2, z);
        final Id i3 = new Id(x, y1, z);

        assertThat(i1, is(not(equalTo(i2))));
        assertThat(i1, is(equalTo(i3)));
        assertThat(i1, is(not(equalTo(""))));
    }

    @Test
    public void testAssignmentIdHashCode() {
        final UniqueId<Agent> x = idFactory.build(Agent.class, "a");
        final UniqueId<Role> y1 = idFactory.build(Role.class, "r1");
        final UniqueId<Role> y2 = idFactory.build(Role.class, "r2");
        final UniqueId<InstanceGoal> z = idFactory.build(InstanceGoal.class, "g");
        final Id i1 = new Id(x, y1, z);
        final Id i2 = new Id(x, y2, z);
        final Id i3 = new Id(x, y1, z);

        assertThat(i1.hashCode(), is(not(equalTo(i2.hashCode()))));
        assertThat(i1.hashCode(), is(equalTo(i3.hashCode())));
    }

    @Test
    public void testAssignmentIdToString() {
        final UniqueId<Agent> x = idFactory.build(Agent.class, "a");
        final UniqueId<Role> y1 = idFactory.build(Role.class, "r1");
        final UniqueId<Role> y2 = idFactory.build(Role.class, "r2");
        final UniqueId<InstanceGoal> z = idFactory.build(InstanceGoal.class, "g");
        final Id i1 = new Id(x, y1, z);
        final Id i2 = new Id(x, y2, z);
        final Id i3 = new Id(x, y1, z);

        assertThat(i1.toString(), is(not(equalTo(i2.toString()))));
        assertThat(i1.toString(), is(equalTo(i3.toString())));
    }

}
