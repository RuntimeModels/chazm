package io.github.runtimemodels.chazm.event;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import io.github.runtimemodels.chazm.entity.Pmf;
import io.github.runtimemodels.chazm.entity.PmfFactory;
import io.github.runtimemodels.chazm.entity.Role;
import io.github.runtimemodels.chazm.entity.RoleFactory;
import io.github.runtimemodels.chazm.id.IdFactory;
import io.github.runtimemodels.chazm.relation.RelationModule;
import io.github.runtimemodels.chazm.relation.Uses;
import io.github.runtimemodels.chazm.relation.UsesFactory;
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
public class UsesEventTest {

    private final Injector injector = Guice.createInjector(new RelationModule(), new EventModule());
    private final UsesEventFactory uef = injector.getInstance(UsesEventFactory.class);
    private final UsesFactory uf = injector.getInstance(UsesFactory.class);
    private final RoleFactory rf = injector.getInstance(RoleFactory.class);
    private final PmfFactory pf = injector.getInstance(PmfFactory.class);
    private final IdFactory idf = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testUsesEventFactory() {
        final Role r = rf.buildRole(idf.build(Role.class, "r"));
        final Pmf p = pf.buildPmf(idf.build(Pmf.class, "p"));
        final Uses u = uf.buildUses(r, p);
        final UsesEvent ue1 = uef.build(EventCategory.ADDED, u);
        final UsesEvent ue2 = uef.build(EventCategory.ADDED, u);

        assertThat(ue1, is(not(nullValue())));
        assertThat(ue1, is(not(sameInstance(ue2))));
    }

    @Test
    public void testUsesEventFactoryWithNullCategoryAndNullUses() {
        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(allOf(
                containsString("1st parameter of io.github.runtimemodels.chazm.event.UsesEvent.<init>(UsesEvent.java:30) is not @Nullable"),
                containsString("2nd parameter of io.github.runtimemodels.chazm.event.UsesEvent.<init>(UsesEvent.java:30) is not @Nullable")
        ));

        uef.build(null, null);
    }

    @Test
    public void testUsesEvent2() {
        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(containsString("2nd parameter of io.github.runtimemodels.chazm.event.UsesEvent.<init>(UsesEvent.java:30) is not @Nullable"));

        uef.build(EventCategory.ADDED, null);
    }

    @Test
    public void testGetRoleId() {
        final Role r = rf.buildRole(idf.build(Role.class, "r"));
        final Pmf p = pf.buildPmf(idf.build(Pmf.class, "p"));
        final Uses u = uf.buildUses(r, p);
        final UsesEvent ue = uef.build(EventCategory.ADDED, u);

        assertThat(ue.getRoleId(), is(sameInstance(r.getId())));
    }

    @Test
    public void testGetPmfId() {
        final Role r = rf.buildRole(idf.build(Role.class, "r"));
        final Pmf p = pf.buildPmf(idf.build(Pmf.class, "p"));
        final Uses u = uf.buildUses(r, p);
        final UsesEvent ue = uef.build(EventCategory.ADDED, u);

        assertThat(ue.getPmfId(), is(sameInstance(p.getId())));
    }

    @Test
    public void testEquals() {
        final Role r1 = rf.buildRole(idf.build(Role.class, "r1"));
        final Role r2 = rf.buildRole(idf.build(Role.class, "r2"));
        final Pmf p = pf.buildPmf(idf.build(Pmf.class, "p"));
        final Uses u1 = uf.buildUses(r1, p);
        final Uses u2 = uf.buildUses(r2, p);
        final Uses u3 = uf.buildUses(r1, p);
        final UsesEvent ue1 = uef.build(EventCategory.ADDED, u1);
        final UsesEvent ue2 = uef.build(EventCategory.ADDED, u2);
        final UsesEvent ue3 = uef.build(EventCategory.ADDED, u3);

        assertThat(ue1, is(not(equalTo(ue2))));
        assertThat(ue1, is(equalTo(ue3)));
        assertThat(ue1, is(not(equalTo(""))));
    }

    @Test
    public void testUseshCode() {
        final Role r1 = rf.buildRole(idf.build(Role.class, "r1"));
        final Role r2 = rf.buildRole(idf.build(Role.class, "r2"));
        final Pmf p = pf.buildPmf(idf.build(Pmf.class, "p"));
        final Uses u1 = uf.buildUses(r1, p);
        final Uses u2 = uf.buildUses(r2, p);
        final Uses u3 = uf.buildUses(r1, p);
        final UsesEvent ue1 = uef.build(EventCategory.ADDED, u1);
        final UsesEvent ue2 = uef.build(EventCategory.ADDED, u2);
        final UsesEvent ue3 = uef.build(EventCategory.ADDED, u3);

        assertThat(ue1.hashCode(), is(not(equalTo(ue2.hashCode()))));
        assertThat(ue1.hashCode(), is(equalTo(ue3.hashCode())));
    }

    @Test
    public void testToString() {
        final Role r1 = rf.buildRole(idf.build(Role.class, "r1"));
        final Role r2 = rf.buildRole(idf.build(Role.class, "r2"));
        final Pmf p = pf.buildPmf(idf.build(Pmf.class, "p"));
        final Uses u1 = uf.buildUses(r1, p);
        final Uses u2 = uf.buildUses(r2, p);
        final Uses u3 = uf.buildUses(r1, p);
        final UsesEvent ue1 = uef.build(EventCategory.ADDED, u1);
        final UsesEvent ue2 = uef.build(EventCategory.ADDED, u2);
        final UsesEvent ue3 = uef.build(EventCategory.ADDED, u3);

        assertThat(ue1.toString(), is(not(equalTo(ue2.toString()))));
        assertThat(ue1.toString(), is(equalTo(ue3.toString())));
    }

}
