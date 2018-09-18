package io.github.runtimemodels.chazm;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import io.github.runtimemodels.chazm.entity.EntityFactory;
import io.github.runtimemodels.chazm.id.IdFactory;
import io.github.runtimemodels.message.E;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import runtimemodels.chazm.api.Organization;
import runtimemodels.chazm.api.entity.Role;
import runtimemodels.chazm.api.entity.SpecificationGoal;
import runtimemodels.chazm.api.id.UniqueId;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@SuppressWarnings("javadoc")
public class AchievesManagerTest {

    private final Injector injector = Guice.createInjector(new OrganizationModule());
    private final Provider<Organization> provider = injector.getProvider(Organization.class);
    private final EntityFactory entityFactory = injector.getInstance(EntityFactory.class);
    private final IdFactory idFactory = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testAddAchieves() {
        final Organization o = provider.get();
        final UniqueId<Role> i1 = idFactory.build(Role.class, "role1");
        final UniqueId<SpecificationGoal> i2 = idFactory.build(SpecificationGoal.class, "goal1");
        final Role r1 = entityFactory.buildRole(i1);
        final SpecificationGoal g1 = entityFactory.buildSpecificationGoal(i2);
        o.addRole(r1);
        o.addSpecificationGoal(g1);
        o.addAchieves(i1, i2);
        assertThat(o.getAchieves(i1).size(), is(equalTo(1)));
        assertThat(o.getAchievedBy(i2).size(), is(equalTo(1)));
    }

    @Test
    public void testAddAchieves1() {
        final Organization o = provider.get();
        exception.expect(is(instanceOf(IllegalArgumentException.class)));
        exception.expectMessage(equalTo(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "addAchieves")));
        o.addAchieves(null, null);
    }

    @Test
    public void testAddAchieves2() {
        final Organization o = provider.get();
        final UniqueId<Role> i1 = idFactory.build(Role.class, "role1");
        final Role r1 = entityFactory.buildRole(i1);
        o.addRole(r1);
        exception.expect(is(instanceOf(IllegalArgumentException.class)));
        exception.expectMessage(equalTo(E.PARAMETER_CANNOT_BE_NULL.get("arg1", "addAchieves")));
        o.addAchieves(i1, null);
    }

    @Test
    public void testAddAchieves3() {
        final Organization o = provider.get();
        final UniqueId<Role> i1 = idFactory.build(Role.class, "role1");
        final UniqueId<SpecificationGoal> i2 = idFactory.build(SpecificationGoal.class, "goal1");
        final SpecificationGoal g1 = entityFactory.buildSpecificationGoal(i2);
        o.addSpecificationGoal(g1);
        exception.expect(is(instanceOf(IllegalArgumentException.class)));
        exception.expectMessage(equalTo(E.ENTITY_DOES_NOT_EXISTS.get("Role", Role.class.getName() + ":" + "role1")));
        o.addAchieves(i1, i2);
    }

    @Test
    public void testAddAchieves4() {
        final Organization o = provider.get();
        final UniqueId<Role> i1 = idFactory.build(Role.class, "role1");
        final UniqueId<SpecificationGoal> i2 = idFactory.build(SpecificationGoal.class, "goal1");
        final Role r1 = entityFactory.buildRole(i1);
        o.addRole(r1);
        exception.expect(is(instanceOf(IllegalArgumentException.class)));
        exception.expectMessage(equalTo(E.ENTITY_DOES_NOT_EXISTS.get("SpecificationGoal", SpecificationGoal.class.getName() + ":" + "goal1")));
        o.addAchieves(i1, i2);
    }

}
