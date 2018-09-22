package runtimemodels.chazm.model.event;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import runtimemodels.chazm.api.entity.Capability;
import runtimemodels.chazm.api.entity.Role;
import runtimemodels.chazm.api.relation.Requires;
import runtimemodels.chazm.model.entity.CapabilityFactory;
import runtimemodels.chazm.model.entity.RoleFactory;
import runtimemodels.chazm.model.id.IdFactory;
import runtimemodels.chazm.model.relation.RelationModule;
import runtimemodels.chazm.model.relation.RequiresFactory;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@SuppressWarnings("javadoc")
public class RequiresEventTest {

    private final Injector injector = Guice.createInjector(new RelationModule(), new EventModule());
    private final RequiresEventFactory ref = injector.getInstance(RequiresEventFactory.class);
    private final RequiresFactory rf = injector.getInstance(RequiresFactory.class);
    private final RoleFactory rrf = injector.getInstance(RoleFactory.class);
    private final CapabilityFactory cf = injector.getInstance(CapabilityFactory.class);
    private final IdFactory idf = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testRequiresEventFactory() {
        final Role rr = rrf.buildRole(idf.build(Role.class, "rr"));
        final Capability c = cf.buildCapability(idf.build(Capability.class, "c"));
        final Requires r = rf.buildRequires(rr, c);
        final RequiresEvent re1 = ref.build(EventCategory.ADDED, r);
        final RequiresEvent re2 = ref.build(EventCategory.ADDED, r);

        assertThat(re1, is(not(nullValue())));
        assertThat(re1, is(not(sameInstance(re2))));
    }

    @Test
    @Ignore
    public void testRequiresEventFactoryWithNullCategoryAndNullRequires() {
        exception.expect(instanceOf(ProvisionException.class));
//        exception.expectMessage(allOf(
//                containsString("1st parameter of RequiresEvent.<init>(RequiresEvent.java:30) is not @Nullable"),
//                containsString("2nd parameter of RequiresEvent.<init>(RequiresEvent.java:30) is not @Nullable")
//        ));

        ref.build(null, null);
    }

    @Test
    @Ignore
    public void testRequiresEventFactoryWithNullRequires() {
        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(containsString("2nd parameter of RequiresEvent.<init>(RequiresEvent.java:30) is not @Nullable"));

        ref.build(EventCategory.ADDED, null);
    }

    @Test
    public void testGetRoleId() {
        final Role rr = rrf.buildRole(idf.build(Role.class, "rr"));
        final Capability c = cf.buildCapability(idf.build(Capability.class, "c"));
        final Requires r = rf.buildRequires(rr, c);
        final RequiresEvent re = ref.build(EventCategory.ADDED, r);

        assertThat(re.getRoleId(), is(sameInstance(rr.getId())));
    }

    @Test
    public void testGetCapabilityId() {
        final Role rr = rrf.buildRole(idf.build(Role.class, "rr"));
        final Capability c = cf.buildCapability(idf.build(Capability.class, "c"));
        final Requires r = rf.buildRequires(rr, c);
        final RequiresEvent re = ref.build(EventCategory.ADDED, r);

        assertThat(re.getCapabilityId(), is(sameInstance(c.getId())));
    }

    @Test
    public void testEquals() {
        final Role rr1 = rrf.buildRole(idf.build(Role.class, "rr1"));
        final Role rr2 = rrf.buildRole(idf.build(Role.class, "rr2"));
        final Capability c = cf.buildCapability(idf.build(Capability.class, "c"));
        final Requires r1 = rf.buildRequires(rr1, c);
        final Requires r2 = rf.buildRequires(rr2, c);
        final Requires r3 = rf.buildRequires(rr1, c);
        final RequiresEvent re1 = ref.build(EventCategory.ADDED, r1);
        final RequiresEvent re2 = ref.build(EventCategory.ADDED, r2);
        final RequiresEvent re3 = ref.build(EventCategory.ADDED, r3);

        assertThat(re1, is(not(equalTo(re2))));
        assertThat(re1, is(equalTo(re3)));
        assertThat(re1, is(not(equalTo(""))));
    }

    @Test
    public void testRequireshCode() {
        final Role rr1 = rrf.buildRole(idf.build(Role.class, "rr1"));
        final Role rr2 = rrf.buildRole(idf.build(Role.class, "rr2"));
        final Capability c = cf.buildCapability(idf.build(Capability.class, "c"));
        final Requires r1 = rf.buildRequires(rr1, c);
        final Requires r2 = rf.buildRequires(rr2, c);
        final Requires r3 = rf.buildRequires(rr1, c);
        final RequiresEvent re1 = ref.build(EventCategory.ADDED, r1);
        final RequiresEvent re2 = ref.build(EventCategory.ADDED, r2);
        final RequiresEvent re3 = ref.build(EventCategory.ADDED, r3);

        assertThat(re1.hashCode(), is(not(equalTo(re2.hashCode()))));
        assertThat(re1.hashCode(), is(equalTo(re3.hashCode())));
    }

    @Test
    public void testToString() {
        final Role rr1 = rrf.buildRole(idf.build(Role.class, "rr1"));
        final Role rr2 = rrf.buildRole(idf.build(Role.class, "rr2"));
        final Capability c = cf.buildCapability(idf.build(Capability.class, "c"));
        final Requires r1 = rf.buildRequires(rr1, c);
        final Requires r2 = rf.buildRequires(rr2, c);
        final Requires r3 = rf.buildRequires(rr1, c);
        final RequiresEvent re1 = ref.build(EventCategory.ADDED, r1);
        final RequiresEvent re2 = ref.build(EventCategory.ADDED, r2);
        final RequiresEvent re3 = ref.build(EventCategory.ADDED, r3);

        assertThat(re1.toString(), is(not(equalTo(re2.toString()))));
        assertThat(re1.toString(), is(equalTo(re3.toString())));
    }

}
