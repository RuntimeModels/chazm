package runtimemodels.chazm.model;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import runtimemodels.chazm.api.Organization;
import runtimemodels.chazm.api.entity.Role;
import runtimemodels.chazm.api.id.UniqueId;
import runtimemodels.chazm.message.E;
import runtimemodels.chazm.model.entity.EntityFactory;
import runtimemodels.chazm.model.id.IdFactory;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@SuppressWarnings("javadoc")
public class RoleManagerTest {

    private final Injector injector = Guice.createInjector(new OrganizationModule());
    private final Provider<Organization> provider = injector.getProvider(Organization.class);
    private final EntityFactory entityFactory = injector.getInstance(EntityFactory.class);
    private final IdFactory idFactory = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testAddRole() {
        final Organization o = provider.get();
        final UniqueId<Role> i1 = idFactory.build(Role.class, "role1");
        final UniqueId<Role> i2 = idFactory.build(Role.class, "");
        final Role r1 = entityFactory.buildRole(i1);
        final Role r2 = entityFactory.buildRole(idFactory.build(Role.class, "role2"));
        assertThat(o.getRoles().size(), is(equalTo(0)));
        o.addRole(r1);
        assertThat(o.getRoles().size(), is(equalTo(1)));
        assertThat(o.getRole(i1), is(not(nullValue())));
        assertThat(o.getRole(i2), is(nullValue()));
        o.addRole(r2);
        assertThat(o.getRoles().size(), is(equalTo(2)));
    }

    @Test
    public void testAddRole1() {
        final Organization o = provider.get();
        exception.expect(is(instanceOf(IllegalArgumentException.class)));
        exception.expectMessage(equalTo(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "addRole")));
        o.addRole(null);
    }

    @Test
    public void testAddRole2() {
        final Organization o = provider.get();
        final UniqueId<Role> i1 = idFactory.build(Role.class, "role1");
        final Role r1 = entityFactory.buildRole(i1);
        final Role r2 = entityFactory.buildRole(i1);
        exception.expect(is(instanceOf(IllegalArgumentException.class)));
        exception.expectMessage(equalTo(E.ENTITY_ALREADY_EXISTS.get("Role", Role.class.getName() + ":role1")));
        assertThat(r1, is(not(sameInstance(r2))));
        o.addRole(r1);
        o.addRole(r2);
    }

}
