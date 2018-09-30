package runtimemodels.chazm.model.event;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import runtimemodels.chazm.api.entity.Attribute;
import runtimemodels.chazm.api.entity.Role;
import runtimemodels.chazm.api.relation.Needs;
import runtimemodels.chazm.model.entity.AttributeFactory;
import runtimemodels.chazm.model.entity.RoleFactory;
import runtimemodels.chazm.model.id.IdFactory;
import runtimemodels.chazm.model.relation.NeedsFactory;
import runtimemodels.chazm.model.relation.RelationModule;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@SuppressWarnings("javadoc")
public class NeedsEventTest {

    private final Injector injector = Guice.createInjector(new RelationModule(), new EventModule());
    private final NeedsEventFactory nef = injector.getInstance(NeedsEventFactory.class);
    private final NeedsFactory nf = injector.getInstance(NeedsFactory.class);
    private final RoleFactory rf = injector.getInstance(RoleFactory.class);
    private final AttributeFactory af = injector.getInstance(AttributeFactory.class);
    private final IdFactory idf = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testNeedsEventFactory() {
        final Role r = rf.buildRole(idf.build(Role.class, "r"));
        final Attribute a = af.buildAttribute(idf.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
        final Needs n = nf.buildNeeds(r, a);
        final NeedsEvent ne1 = nef.build(EventType.ADDED, n);
        final NeedsEvent ne2 = nef.build(EventType.ADDED, n);

        assertThat(ne1, is(not(nullValue())));
        assertThat(ne1, is(not(sameInstance(ne2))));
    }

    @Test
    @Ignore
    public void testNeedsEventFactoryWithNullCategoryAndNullNeeds() {
        exception.expect(instanceOf(ProvisionException.class));
//        exception.expectMessage(allOf(
//                containsString("1st parameter of NeedsEvent.<init>(NeedsEvent.java:30) is not @Nullable"),
//                containsString("2nd parameter of NeedsEvent.<init>(NeedsEvent.java:30) is not @Nullable")
//        ));

        nef.build(null, null);
    }

    @Test
    @Ignore
    public void testNeedsEvent2() {
        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(containsString("2nd parameter of NeedsEvent.<init>(NeedsEvent.java:30) is not @Nullable"));

        nef.build(EventType.ADDED, null);
    }

    @Test
    public void testGetRoleId() {
        final Role r = rf.buildRole(idf.build(Role.class, "r"));
        final Attribute a = af.buildAttribute(idf.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
        final Needs n = nf.buildNeeds(r, a);
        final NeedsEvent ne = nef.build(EventType.ADDED, n);

        assertThat(ne.getRoleId(), is(sameInstance(r.getId())));
    }

    @Test
    public void testGetAttributeId() {
        final Role r = rf.buildRole(idf.build(Role.class, "r"));
        final Attribute a = af.buildAttribute(idf.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
        final Needs n = nf.buildNeeds(r, a);
        final NeedsEvent ne = nef.build(EventType.ADDED, n);

        assertThat(ne.getAttributeId(), is(sameInstance(a.getId())));
    }

    @Test
    public void testEquals() {
        final Role r1 = rf.buildRole(idf.build(Role.class, "r1"));
        final Role r2 = rf.buildRole(idf.build(Role.class, "r2"));
        final Attribute a = af.buildAttribute(idf.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
        final Needs n1 = nf.buildNeeds(r1, a);
        final Needs n2 = nf.buildNeeds(r2, a);
        final Needs n3 = nf.buildNeeds(r1, a);
        final NeedsEvent ne1 = nef.build(EventType.ADDED, n1);
        final NeedsEvent ne2 = nef.build(EventType.ADDED, n2);
        final NeedsEvent ne3 = nef.build(EventType.ADDED, n3);

        assertThat(ne1, is(not(equalTo(ne2))));
        assertThat(ne1, is(equalTo(ne3)));
        assertThat(ne1, is(not(equalTo(""))));
    }

    @Test
    public void testNeedshCode() {
        final Role r1 = rf.buildRole(idf.build(Role.class, "r1"));
        final Role r2 = rf.buildRole(idf.build(Role.class, "r2"));
        final Attribute a = af.buildAttribute(idf.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
        final Needs n1 = nf.buildNeeds(r1, a);
        final Needs n2 = nf.buildNeeds(r2, a);
        final Needs n3 = nf.buildNeeds(r1, a);
        final NeedsEvent ne1 = nef.build(EventType.ADDED, n1);
        final NeedsEvent ne2 = nef.build(EventType.ADDED, n2);
        final NeedsEvent ne3 = nef.build(EventType.ADDED, n3);

        assertThat(ne1.hashCode(), is(not(equalTo(ne2.hashCode()))));
        assertThat(ne1.hashCode(), is(equalTo(ne3.hashCode())));
    }

    @Test
    public void testToString() {
        final Role r1 = rf.buildRole(idf.build(Role.class, "r1"));
        final Role r2 = rf.buildRole(idf.build(Role.class, "r2"));
        final Attribute a = af.buildAttribute(idf.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
        final Needs n1 = nf.buildNeeds(r1, a);
        final Needs n2 = nf.buildNeeds(r2, a);
        final Needs n3 = nf.buildNeeds(r1, a);
        final NeedsEvent ne1 = nef.build(EventType.ADDED, n1);
        final NeedsEvent ne2 = nef.build(EventType.ADDED, n2);
        final NeedsEvent ne3 = nef.build(EventType.ADDED, n3);

        assertThat(ne1.toString(), is(not(equalTo(ne2.toString()))));
        assertThat(ne1.toString(), is(equalTo(ne3.toString())));
    }

}
