package io.github.runtimemodels.chazm.relation;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import io.github.runtimemodels.chazm.entity.Role;
import io.github.runtimemodels.chazm.entity.RoleFactory;
import io.github.runtimemodels.chazm.entity.SpecificationGoal;
import io.github.runtimemodels.chazm.entity.SpecificationGoalFactory;
import io.github.runtimemodels.chazm.id.IdFactory;
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
import static org.junit.Assert.*;

@SuppressWarnings("javadoc")
public class AchievesTest {

    private final Injector injector = Guice.createInjector(new RelationModule());
    private final AchievesFactory achievesFactory = injector.getInstance(AchievesFactory.class);
    private final RoleFactory roleFactory = injector.getInstance(RoleFactory.class);
    private final SpecificationGoalFactory goalFactory = injector.getInstance(SpecificationGoalFactory.class);
    private final IdFactory idFactory = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testAchieves() {
        final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
        final SpecificationGoal g = goalFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "g"));
        final Achieves a1 = achievesFactory.buildAchieves(r, g);
        final Achieves a2 = achievesFactory.buildAchieves(r, g);

        assertThat(a1, is(not(nullValue())));
        assertThat(a1, is(not(sameInstance(a2))));
    }

    @Test
    public void testAchieves1() {
        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

        achievesFactory.buildAchieves(null, null);
    }

    @Test
    public void testAchieves2() {
        final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));

        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

        achievesFactory.buildAchieves(r, null);
    }

    @Test
    public void testGetRole() {
        final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
        final SpecificationGoal g = goalFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "g"));
        final Achieves a = achievesFactory.buildAchieves(r, g);

        assertThat(a.getRole(), is(sameInstance(r)));
    }

    @Test
    public void testGetGoal() {
        final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
        final SpecificationGoal g = goalFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "g"));
        final Achieves a = achievesFactory.buildAchieves(r, g);

        assertThat(a.getGoal(), is(sameInstance(g)));
    }

    @Test
    public void testEquals() {
        final Role r1 = roleFactory.buildRole(idFactory.build(Role.class, "r1"));
        final Role r2 = roleFactory.buildRole(idFactory.build(Role.class, "r2"));
        final SpecificationGoal g = goalFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "g"));
        final Achieves a1 = achievesFactory.buildAchieves(r1, g);
        final Achieves a2 = achievesFactory.buildAchieves(r2, g);
        final Achieves a3 = achievesFactory.buildAchieves(r1, g);

        assertThat(a1, is(not(equalTo(a2))));
        assertThat(a1, is(equalTo(a3)));
        assertThat(a1, is(not(equalTo(""))));
    }

    @Test
    public void testHashCode() {
        final Role r1 = roleFactory.buildRole(idFactory.build(Role.class, "r1"));
        final Role r2 = roleFactory.buildRole(idFactory.build(Role.class, "r2"));
        final SpecificationGoal g = goalFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "g"));
        final Achieves a1 = achievesFactory.buildAchieves(r1, g);
        final Achieves a2 = achievesFactory.buildAchieves(r2, g);
        final Achieves a3 = achievesFactory.buildAchieves(r1, g);

        assertThat(a1.hashCode(), is(not(equalTo(a2.hashCode()))));
        assertThat(a1.hashCode(), is(equalTo(a3.hashCode())));
    }

    @Test
    public void testToString() {
        final Role r1 = roleFactory.buildRole(idFactory.build(Role.class, "r1"));
        final Role r2 = roleFactory.buildRole(idFactory.build(Role.class, "r2"));
        final SpecificationGoal g = goalFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "g"));
        final Achieves a1 = achievesFactory.buildAchieves(r1, g);
        final Achieves a2 = achievesFactory.buildAchieves(r2, g);
        final Achieves a3 = achievesFactory.buildAchieves(r1, g);

        assertThat(a1.toString(), is(not(equalTo(a2.toString()))));
        assertThat(a1.toString(), is(equalTo(a3.toString())));
    }

}
