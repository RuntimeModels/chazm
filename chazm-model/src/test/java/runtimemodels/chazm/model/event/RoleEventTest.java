package runtimemodels.chazm.model.event;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import runtimemodels.chazm.api.entity.Role;
import runtimemodels.chazm.model.entity.RoleFactory;
import runtimemodels.chazm.model.id.IdFactory;
import runtimemodels.chazm.model.relation.RelationModule;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@SuppressWarnings("javadoc")
public class RoleEventTest {

    private final Injector injector = Guice.createInjector(new RelationModule(), new EventModule());
    private final RoleEventFactory ref = injector.getInstance(RoleEventFactory.class);
    private final RoleFactory rf = injector.getInstance(RoleFactory.class);
    private final IdFactory idf = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testRoleEventFactory() {
        final Role r = rf.buildRole(idf.build(Role.class, "r"));
        final RoleEvent re1 = ref.build(EventType.ADDED, r);
        final RoleEvent re2 = ref.build(EventType.ADDED, r);

        assertThat(re1, is(not(nullValue())));
        assertThat(re1, is(not(sameInstance(re2))));
    }

    @Test
    @Ignore
    public void testRoleEventFactoryWithNullCategoryAndNullRole() {
        exception.expect(instanceOf(ProvisionException.class));
//        exception.expectMessage(allOf(
//                containsString("1st parameter of RoleEvent.<init>(RoleEvent.java:28) is not @Nullable"),
//                containsString("2nd parameter of RoleEvent.<init>(RoleEvent.java:28) is not @Nullable")));

        ref.build(null, null);
    }

    @Test
    @Ignore
    public void testRoleEventFactoryWithNullRole() {
        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(containsString("2nd parameter of RoleEvent.<init>(RoleEvent.java:28) is not @Nullable"));

        ref.build(EventType.ADDED, null);
    }

    @Test
    public void testGetId() {
        final Role r = rf.buildRole(idf.build(Role.class, "r"));
        final RoleEvent re = ref.build(EventType.ADDED, r);

        assertThat(re.getId(), is(sameInstance(r.getId())));
    }

    @Test
    public void testEquals() {
        final Role r1 = rf.buildRole(idf.build(Role.class, "r1"));
        final Role r2 = rf.buildRole(idf.build(Role.class, "r2"));
        final Role r3 = rf.buildRole(idf.build(Role.class, "r1"));
        final RoleEvent re1 = ref.build(EventType.ADDED, r1);
        final RoleEvent re2 = ref.build(EventType.ADDED, r2);
        final RoleEvent re3 = ref.build(EventType.ADDED, r3);

        assertThat(re1, is(not(equalTo(re2))));
        assertThat(re1, is(equalTo(re3)));
        assertThat(re1, is(not(equalTo(""))));
    }

    @Test
    public void testHashCode() {
        final Role r1 = rf.buildRole(idf.build(Role.class, "r1"));
        final Role r2 = rf.buildRole(idf.build(Role.class, "r2"));
        final Role r3 = rf.buildRole(idf.build(Role.class, "r1"));
        final RoleEvent re1 = ref.build(EventType.ADDED, r1);
        final RoleEvent re2 = ref.build(EventType.ADDED, r2);
        final RoleEvent re3 = ref.build(EventType.ADDED, r3);

        assertThat(re1.hashCode(), is(not(equalTo(re2.hashCode()))));
        assertThat(re1.hashCode(), is(equalTo(re3.hashCode())));
    }

    @Test
    public void testToString() {
        final Role r1 = rf.buildRole(idf.build(Role.class, "r1"));
        final Role r2 = rf.buildRole(idf.build(Role.class, "r2"));
        final Role r3 = rf.buildRole(idf.build(Role.class, "r1"));
        final RoleEvent re1 = ref.build(EventType.ADDED, r1);
        final RoleEvent re2 = ref.build(EventType.ADDED, r2);
        final RoleEvent re3 = ref.build(EventType.ADDED, r3);

        assertThat(re1.toString(), is(not(equalTo(re2.toString()))));
        assertThat(re1.toString(), is(equalTo(re3.toString())));
    }

}
