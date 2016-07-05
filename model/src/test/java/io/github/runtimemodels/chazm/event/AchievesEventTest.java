package io.github.runtimemodels.chazm.event;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import io.github.runtimemodels.chazm.entity.Role;
import io.github.runtimemodels.chazm.entity.RoleFactory;
import io.github.runtimemodels.chazm.entity.SpecificationGoal;
import io.github.runtimemodels.chazm.entity.SpecificationGoalFactory;
import io.github.runtimemodels.chazm.id.IdFactory;
import io.github.runtimemodels.chazm.relation.Achieves;
import io.github.runtimemodels.chazm.relation.AchievesFactory;
import io.github.runtimemodels.chazm.relation.RelationModule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

@SuppressWarnings("javadoc")
public class AchievesEventTest {

    private final Injector injector = Guice.createInjector(new RelationModule(), new EventModule());
    private final AchievesEventFactory aef = injector.getInstance(AchievesEventFactory.class);
    private final AchievesFactory af = injector.getInstance(AchievesFactory.class);
    private final RoleFactory rf = injector.getInstance(RoleFactory.class);
    private final SpecificationGoalFactory sgf = injector.getInstance(SpecificationGoalFactory.class);
    private final IdFactory idf = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testAchievesEvent() {
        final Role r = rf.buildRole(idf.build(Role.class, "r"));
        final SpecificationGoal sg = sgf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "sg"));
        final Achieves a = af.buildAchieves(r, sg);
        final AchievesEvent ae1 = aef.build(EventCategory.ADDED, a);
        final AchievesEvent ae2 = aef.build(EventCategory.ADDED, a);

        assertThat(ae1, is(not(nullValue())));
        assertThat(ae1, is(not(sameInstance(ae2))));
    }

    @Test
    public void testAchievesEvent1() {
        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

        aef.build(null, null);
    }

    @Test
    public void testAchievesEvent2() {
        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

        aef.build(EventCategory.ADDED, null);
    }

    @Test
    public void testGetRoleId() {
        final Role r = rf.buildRole(idf.build(Role.class, "r"));
        final SpecificationGoal sg = sgf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "sg"));
        final Achieves a = af.buildAchieves(r, sg);
        final AchievesEvent ae = aef.build(EventCategory.ADDED, a);

        assertThat(ae.getRoleId(), is(sameInstance(r.getId())));
    }

    @Test
    public void testGetGoalId() {
        final Role r = rf.buildRole(idf.build(Role.class, "r"));
        final SpecificationGoal sg = sgf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "sg"));
        final Achieves a = af.buildAchieves(r, sg);
        final AchievesEvent ae = aef.build(EventCategory.ADDED, a);

        assertThat(ae.getGoalId(), is(sameInstance(sg.getId())));
    }

    @Test
    public void testEquals() {
        final Role r1 = rf.buildRole(idf.build(Role.class, "r1"));
        final Role r2 = rf.buildRole(idf.build(Role.class, "r2"));
        final SpecificationGoal g = sgf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "g"));
        final Achieves a1 = af.buildAchieves(r1, g);
        final Achieves a2 = af.buildAchieves(r2, g);
        final Achieves a3 = af.buildAchieves(r1, g);
        final AchievesEvent ae1 = aef.build(EventCategory.ADDED, a1);
        final AchievesEvent ae2 = aef.build(EventCategory.ADDED, a2);
        final AchievesEvent ae3 = aef.build(EventCategory.ADDED, a3);

        assertThat(ae1, is(not(equalTo(ae2))));
        assertThat(ae1, is(equalTo(ae3)));
        assertThat(ae1, is(not(equalTo(""))));
    }

    @Test
    public void testHashCode() {
        final Role r1 = rf.buildRole(idf.build(Role.class, "r1"));
        final Role r2 = rf.buildRole(idf.build(Role.class, "r2"));
        final SpecificationGoal g = sgf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "g"));
        final Achieves a1 = af.buildAchieves(r1, g);
        final Achieves a2 = af.buildAchieves(r2, g);
        final Achieves a3 = af.buildAchieves(r1, g);
        final AchievesEvent ae1 = aef.build(EventCategory.ADDED, a1);
        final AchievesEvent ae2 = aef.build(EventCategory.ADDED, a2);
        final AchievesEvent ae3 = aef.build(EventCategory.ADDED, a3);

        assertThat(ae1.hashCode(), is(not(equalTo(ae2.hashCode()))));
        assertThat(ae1.hashCode(), is(equalTo(ae3.hashCode())));
    }

    @Test
    public void testToString() {
        final Role r1 = rf.buildRole(idf.build(Role.class, "r1"));
        final Role r2 = rf.buildRole(idf.build(Role.class, "r2"));
        final SpecificationGoal g = sgf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "g"));
        final Achieves a1 = af.buildAchieves(r1, g);
        final Achieves a2 = af.buildAchieves(r2, g);
        final Achieves a3 = af.buildAchieves(r1, g);
        final AchievesEvent ae1 = aef.build(EventCategory.ADDED, a1);
        final AchievesEvent ae2 = aef.build(EventCategory.ADDED, a2);
        final AchievesEvent ae3 = aef.build(EventCategory.ADDED, a3);

        assertThat(ae1.toString(), is(not(equalTo(ae2.toString()))));
        assertThat(ae1.toString(), is(equalTo(ae3.toString())));
    }

}
