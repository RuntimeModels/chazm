package io.github.runtimemodels.chazm.relation;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import io.github.runtimemodels.chazm.entity.CapabilityFactory;
import io.github.runtimemodels.chazm.entity.RoleFactory;
import io.github.runtimemodels.chazm.id.IdFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import runtimemodels.chazm.api.entity.Capability;
import runtimemodels.chazm.api.entity.Role;
import runtimemodels.chazm.api.relation.Requires;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@SuppressWarnings("javadoc")
public class RequiresRelationTest {

    private final Injector injector = Guice.createInjector(new RelationModule());
    private final RequiresFactory requiresFactory = injector.getInstance(RequiresFactory.class);
    private final RoleFactory roleFactory = injector.getInstance(RoleFactory.class);
    private final CapabilityFactory capabilityFactory = injector.getInstance(CapabilityFactory.class);
    private final IdFactory idFactory = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testRequiresRelationFactory() {
        final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
        final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));
        final Requires rq1 = requiresFactory.buildRequires(r, c);
        final Requires rq2 = requiresFactory.buildRequires(r, c);

        assertThat(rq1, is(not(nullValue())));
        assertThat(rq1, is(not(sameInstance(rq2))));
    }

    @Test
    public void testRequiresRelationFactoryWithNullRoleAndNullCapability() {
        exception.expect(instanceOf(ProvisionException.class));
//        exception.expectMessage(allOf(
//                containsString("1st parameter of io.github.runtimemodels.chazm.relation.RequiresRelation.<init>(RequiresRelation.java:23) is not @Nullable"),
//                containsString("2nd parameter of io.github.runtimemodels.chazm.relation.RequiresRelation.<init>(RequiresRelation.java:23) is not @Nullable")
//        ));

        requiresFactory.buildRequires(null, null);
    }

    @Test
    public void testRequiresRelationFactoryWithNullCapability() {
        final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));

        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(containsString("2nd parameter of io.github.runtimemodels.chazm.relation.RequiresRelation.<init>(RequiresRelation.java:23) is not @Nullable"));

        requiresFactory.buildRequires(r, null);
    }

    @Test
    public void testGetRole() {
        final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
        final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));
        final Requires rq = requiresFactory.buildRequires(r, c);

        assertThat(rq.getRole(), is(sameInstance(r)));
    }

    @Test
    public void testGetCapability() {
        final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
        final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));
        final Requires rq = requiresFactory.buildRequires(r, c);

        assertThat(rq.getCapability(), is(sameInstance(c)));
    }

    @Test
    public void testEquals() {
        final Role r1 = roleFactory.buildRole(idFactory.build(Role.class, "r1"));
        final Role r2 = roleFactory.buildRole(idFactory.build(Role.class, "r2"));
        final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));
        final Requires rq1 = requiresFactory.buildRequires(r1, c);
        final Requires rq2 = requiresFactory.buildRequires(r2, c);
        final Requires rq3 = requiresFactory.buildRequires(r1, c);

        assertThat(rq1, is(not(equalTo(rq2))));
        assertThat(rq1, is(equalTo(rq3)));
        assertThat(rq1, is(not(equalTo(""))));
    }

    @Test
    public void testHashCode() {
        final Role r1 = roleFactory.buildRole(idFactory.build(Role.class, "r1"));
        final Role r2 = roleFactory.buildRole(idFactory.build(Role.class, "r2"));
        final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));
        final Requires rq1 = requiresFactory.buildRequires(r1, c);
        final Requires rq2 = requiresFactory.buildRequires(r2, c);
        final Requires rq3 = requiresFactory.buildRequires(r1, c);

        assertThat(rq1.hashCode(), is(not(equalTo(rq2.hashCode()))));
        assertThat(rq1.hashCode(), is(equalTo(rq3.hashCode())));
    }

    @Test
    public void testToString() {
        final Role r1 = roleFactory.buildRole(idFactory.build(Role.class, "r1"));
        final Role r2 = roleFactory.buildRole(idFactory.build(Role.class, "r2"));
        final Capability c = capabilityFactory.buildCapability(idFactory.build(Capability.class, "c"));
        final Requires rq1 = requiresFactory.buildRequires(r1, c);
        final Requires rq2 = requiresFactory.buildRequires(r2, c);
        final Requires rq3 = requiresFactory.buildRequires(r1, c);

        assertThat(rq1.toString(), is(not(equalTo(rq2.toString()))));
        assertThat(rq1.toString(), is(equalTo(rq3.toString())));
    }

}
