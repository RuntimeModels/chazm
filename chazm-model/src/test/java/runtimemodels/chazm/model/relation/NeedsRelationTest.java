package runtimemodels.chazm.model.relation;

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

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@SuppressWarnings("javadoc")
public class NeedsRelationTest {

    private final Injector injector = Guice.createInjector(new RelationModule());
    private final NeedsFactory needsFactory = injector.getInstance(NeedsFactory.class);
    private final RoleFactory roleFactory = injector.getInstance(RoleFactory.class);
    private final AttributeFactory attributeFactory = injector.getInstance(AttributeFactory.class);
    private final IdFactory idFactory = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testNeedsRelationFactory() {
        final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
        final Attribute a = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
        final Needs nd1 = needsFactory.buildNeeds(r, a);
        final Needs nd2 = needsFactory.buildNeeds(r, a);

        assertThat(nd1, is(not(nullValue())));
        assertThat(nd1, is(not(sameInstance(nd2))));
    }

    @Test
    @Ignore
    public void testNeedsRelationFactoryWithNullRoleAndNullAttribute() {
        exception.expect(instanceOf(ProvisionException.class));
//        exception.expectMessage(allOf(
//                containsString("1st parameter of NeedsRelation.<init>(NeedsRelation.java:23) is not @Nullable"),
//                containsString("2nd parameter of NeedsRelation.<init>(NeedsRelation.java:23) is not @Nullable")
//        ));

        needsFactory.buildNeeds(null, null);
    }

    @Test
    @Ignore
    public void testNeedsRelationFactoryWithNullAttribute() {
        final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));

        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(containsString("2nd parameter of NeedsRelation.<init>(NeedsRelation.java:23) is not @Nullable"));

        needsFactory.buildNeeds(r, null);
    }

    @Test
    public void testGetRole() {
        final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
        final Attribute a = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
        final Needs nd = needsFactory.buildNeeds(r, a);

        assertThat(nd.getRole(), is(sameInstance(r)));
    }

    @Test
    public void testGetAttribute() {
        final Role r = roleFactory.buildRole(idFactory.build(Role.class, "r"));
        final Attribute a = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
        final Needs nd = needsFactory.buildNeeds(r, a);

        assertThat(nd.getAttribute(), is(sameInstance(a)));
    }

    @Test
    public void testEquals() {
        final Role r1 = roleFactory.buildRole(idFactory.build(Role.class, "r1"));
        final Role r2 = roleFactory.buildRole(idFactory.build(Role.class, "r2"));
        final Attribute a = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
        final Needs nd1 = needsFactory.buildNeeds(r1, a);
        final Needs nd2 = needsFactory.buildNeeds(r2, a);
        final Needs nd3 = needsFactory.buildNeeds(r1, a);

        assertThat(nd1, is(not(equalTo(nd2))));
        assertThat(nd1, is(equalTo(nd3)));
        assertThat(nd1, is(not(equalTo(""))));
    }

    @Test
    public void testHashCode() {
        final Role r1 = roleFactory.buildRole(idFactory.build(Role.class, "r1"));
        final Role r2 = roleFactory.buildRole(idFactory.build(Role.class, "r2"));
        final Attribute a = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
        final Needs nd1 = needsFactory.buildNeeds(r1, a);
        final Needs nd2 = needsFactory.buildNeeds(r2, a);
        final Needs nd3 = needsFactory.buildNeeds(r1, a);

        assertThat(nd1.hashCode(), is(not(equalTo(nd2.hashCode()))));
        assertThat(nd1.hashCode(), is(equalTo(nd3.hashCode())));
    }

    @Test
    public void testToString() {
        final Role r1 = roleFactory.buildRole(idFactory.build(Role.class, "r1"));
        final Role r2 = roleFactory.buildRole(idFactory.build(Role.class, "r2"));
        final Attribute a = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "a"), Attribute.Type.NEGATIVE_QUALITY);
        final Needs nd1 = needsFactory.buildNeeds(r1, a);
        final Needs nd2 = needsFactory.buildNeeds(r2, a);
        final Needs nd3 = needsFactory.buildNeeds(r1, a);

        assertThat(nd1.toString(), is(not(equalTo(nd2.toString()))));
        assertThat(nd1.toString(), is(equalTo(nd3.toString())));
    }

}