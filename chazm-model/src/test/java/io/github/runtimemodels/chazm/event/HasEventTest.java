package io.github.runtimemodels.chazm.event;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import io.github.runtimemodels.chazm.entity.AgentFactory;
import io.github.runtimemodels.chazm.entity.AttributeFactory;
import io.github.runtimemodels.chazm.id.IdFactory;
import io.github.runtimemodels.chazm.relation.HasFactory;
import io.github.runtimemodels.chazm.relation.RelationModule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import runtimemodels.chazm.api.entity.Agent;
import runtimemodels.chazm.api.entity.Attribute;
import runtimemodels.chazm.api.relation.Has;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@SuppressWarnings("javadoc")
public class HasEventTest {

    private final Injector injector = Guice.createInjector(new RelationModule(), new EventModule());
    private final HasEventFactory hef = injector.getInstance(HasEventFactory.class);
    private final HasFactory hf = injector.getInstance(HasFactory.class);
    private final AgentFactory af = injector.getInstance(AgentFactory.class);
    private final AttributeFactory aaf = injector.getInstance(AttributeFactory.class);
    private final IdFactory idf = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testHasEventFactory() {
        final Agent a = af.buildAgent(idf.build(Agent.class, "a"), new Agent.ContactInfo() {
        });
        final Attribute aa = aaf.buildAttribute(idf.build(Attribute.class, "aa"), Attribute.Type.NEGATIVE_QUALITY);
        final Has h = hf.buildHas(a, aa, 1d);
        final HasEvent he1 = hef.build(EventCategory.ADDED, h);
        final HasEvent he2 = hef.build(EventCategory.ADDED, h);

        assertThat(he1, is(not(nullValue())));
        assertThat(he1, is(not(sameInstance(he2))));
    }

    @Test
    @Ignore
    public void testHasEventFactoryWithNullCategoryAndNullHas() {
        exception.expect(instanceOf(ProvisionException.class));
//        exception.expectMessage(allOf(
//                containsString("1st parameter of io.github.runtimemodels.chazm.event.HasEvent.<init>(HasEvent.java:31) is not @Nullable"),
//                containsString("2nd parameter of io.github.runtimemodels.chazm.event.HasEvent.<init>(HasEvent.java:31) is not @Nullable")
//        ));

        hef.build(null, null);
    }

    @Test
    @Ignore
    public void testHasEventFactoryWithNullHas() {
        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(containsString("2nd parameter of io.github.runtimemodels.chazm.event.HasEvent.<init>(HasEvent.java:31) is not @Nullable"));

        hef.build(EventCategory.ADDED, null);
    }

    @Test
    public void testGetAgentId() {
        final Agent a = af.buildAgent(idf.build(Agent.class, "a"), new Agent.ContactInfo() {
        });
        final Attribute aa = aaf.buildAttribute(idf.build(Attribute.class, "aa"), Attribute.Type.NEGATIVE_QUALITY);
        final Has h = hf.buildHas(a, aa, 1d);
        final HasEvent he = hef.build(EventCategory.ADDED, h);

        assertThat(he.getAgentId(), is(sameInstance(a.getId())));
    }

    @Test
    public void testGetAttributeId() {
        final Agent a = af.buildAgent(idf.build(Agent.class, "a"), new Agent.ContactInfo() {
        });
        final Attribute aa = aaf.buildAttribute(idf.build(Attribute.class, "aa"), Attribute.Type.NEGATIVE_QUALITY);
        final Has h = hf.buildHas(a, aa, 1d);
        final HasEvent he = hef.build(EventCategory.ADDED, h);

        assertThat(he.getAttributeId(), is(sameInstance(aa.getId())));
    }

    @Test
    public void testGetValue() {
        final Agent a = af.buildAgent(idf.build(Agent.class, "a"), new Agent.ContactInfo() {
        });
        final Attribute aa = aaf.buildAttribute(idf.build(Attribute.class, "aa"), Attribute.Type.NEGATIVE_QUALITY);
        final Has h = hf.buildHas(a, aa, 1d);
        final HasEvent he = hef.build(EventCategory.ADDED, h);

        assertThat(he.getValue(), is(equalTo(h.getValue())));
    }

    @Test
    public void testEquals() {
        final Agent a1 = af.buildAgent(idf.build(Agent.class, "a1"), new Agent.ContactInfo() {
        });
        final Agent a2 = af.buildAgent(idf.build(Agent.class, "a2"), new Agent.ContactInfo() {
        });
        final Attribute aa = aaf.buildAttribute(idf.build(Attribute.class, "aa"), Attribute.Type.NEGATIVE_QUALITY);
        final Has h1 = hf.buildHas(a1, aa, 1d);
        final Has h2 = hf.buildHas(a2, aa, 1d);
        final Has h3 = hf.buildHas(a1, aa, 1d);
        final HasEvent he1 = hef.build(EventCategory.ADDED, h1);
        final HasEvent he2 = hef.build(EventCategory.ADDED, h2);
        final HasEvent he3 = hef.build(EventCategory.ADDED, h3);

        assertThat(he1, is(not(equalTo(he2))));
        assertThat(he1, is(equalTo(he3)));
        assertThat(he1, is(not(equalTo(""))));
    }

    @Test
    public void testHashCode() {
        final Agent a1 = af.buildAgent(idf.build(Agent.class, "a1"), new Agent.ContactInfo() {
        });
        final Agent a2 = af.buildAgent(idf.build(Agent.class, "a2"), new Agent.ContactInfo() {
        });
        final Attribute aa = aaf.buildAttribute(idf.build(Attribute.class, "aa"), Attribute.Type.NEGATIVE_QUALITY);
        final Has h1 = hf.buildHas(a1, aa, 1d);
        final Has h2 = hf.buildHas(a2, aa, 1d);
        final Has h3 = hf.buildHas(a1, aa, 1d);
        final HasEvent he1 = hef.build(EventCategory.ADDED, h1);
        final HasEvent he2 = hef.build(EventCategory.ADDED, h2);
        final HasEvent he3 = hef.build(EventCategory.ADDED, h3);

        assertThat(he1.hashCode(), is(not(equalTo(he2.hashCode()))));
        assertThat(he1.hashCode(), is(equalTo(he3.hashCode())));
    }

    @Test
    public void testToString() {
        final Agent a1 = af.buildAgent(idf.build(Agent.class, "a1"), new Agent.ContactInfo() {
        });
        final Agent a2 = af.buildAgent(idf.build(Agent.class, "a2"), new Agent.ContactInfo() {
        });
        final Attribute aa = aaf.buildAttribute(idf.build(Attribute.class, "aa"), Attribute.Type.NEGATIVE_QUALITY);
        final Has h1 = hf.buildHas(a1, aa, 1d);
        final Has h2 = hf.buildHas(a2, aa, 1d);
        final Has h3 = hf.buildHas(a1, aa, 1d);
        final HasEvent he1 = hef.build(EventCategory.ADDED, h1);
        final HasEvent he2 = hef.build(EventCategory.ADDED, h2);
        final HasEvent he3 = hef.build(EventCategory.ADDED, h3);

        assertThat(he1.toString(), is(not(equalTo(he2.toString()))));
        assertThat(he1.toString(), is(equalTo(he3.toString())));
    }

}
