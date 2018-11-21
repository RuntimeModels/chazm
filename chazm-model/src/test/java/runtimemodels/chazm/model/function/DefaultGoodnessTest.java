package runtimemodels.chazm.model.function;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import org.junit.jupiter.api.Test;
import runtimemodels.chazm.api.Organization;
import runtimemodels.chazm.api.entity.*;
import runtimemodels.chazm.api.function.Goodness;
import runtimemodels.chazm.model.OrganizationModule;
import runtimemodels.chazm.model.entity.EntityFactory;
import runtimemodels.chazm.model.id.IdFactory;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DefaultGoodnessTest {

    private final Injector injector = Guice.createInjector(new OrganizationModule(), new FunctionModule());
    private final Provider<Organization> provider = injector.getProvider(Organization.class);
    private final IdFactory idFactory = injector.getInstance(IdFactory.class);
    private final EntityFactory entityFactory = injector.getInstance(EntityFactory.class);
    private final Goodness goodness = injector.getInstance(Goodness.class);

    @Test
    public void testCompute() {
        final Organization o = provider.get();
        final Agent a = entityFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {
        });
        final Role r = entityFactory.buildRole(idFactory.build(Role.class, "r"));
        final SpecificationGoal sg = entityFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "sg"));
        final InstanceGoal ig = entityFactory.buildInstanceGoal(idFactory.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {
        });
        final Capability c1 = entityFactory.buildCapability(idFactory.build(Capability.class, "c1"));
        final Capability c2 = entityFactory.buildCapability(idFactory.build(Capability.class, "c2"));
        final Attribute t = entityFactory.buildAttribute(idFactory.build(Attribute.class, "t"), Attribute.Type.NEGATIVE_QUALITY);

        assertThat(goodness.compute(o, a, r, ig, new HashSet<>())).isEqualTo(DefaultGoodness.MIN_SCORE);

        o.addAgent(a);
        o.addRole(r);
        o.addSpecificationGoal(sg);
        o.addInstanceGoal(ig);
        o.addCapability(c1);
        o.addAchieves(r.getId(), sg.getId());

        assertThat(goodness.compute(o, a, r, ig, new HashSet<>())).isEqualTo(DefaultGoodness.MAX_SCORE);

        o.addRequires(r.getId(), c1.getId());

        assertThat(goodness.compute(o, a, r, ig, new HashSet<>())).isEqualTo(DefaultGoodness.MIN_SCORE);

        o.addPossesses(a.getId(), c1.getId(), 0.0);

        assertThat(goodness.compute(o, a, r, ig, new HashSet<>())).isEqualTo(DefaultGoodness.MIN_SCORE);

        o.setPossessesScore(a.getId(), c1.getId(), 1.0);

        assertThat(goodness.compute(o, a, r, ig, new HashSet<>())).isEqualTo(DefaultGoodness.MAX_SCORE);

        o.addCapability(c2);
        o.addRequires(r.getId(), c2.getId());
        o.addPossesses(a.getId(), c2.getId(), 1.0);

        assertThat(goodness.compute(o, a, r, ig, new HashSet<>())).isEqualTo(DefaultGoodness.MAX_SCORE);

        o.addAttribute(t);
        o.addNeeds(r.getId(), t.getId());

        assertThat(goodness.compute(o, a, r, ig, new HashSet<>())).isEqualTo(DefaultGoodness.MIN_SCORE);
    }

    @Test
    public void testCompute1() {
        assertThrows(IllegalArgumentException.class, () -> goodness.compute(null, null, null, null, null));
    }

    @Test
    public void testCompute2() {
        final Organization o = provider.get();
        assertThrows(IllegalArgumentException.class, () -> goodness.compute(o, null, null, null, null));
    }

    @Test
    public void testCompute3() {
        final Organization o = provider.get();
        final Agent a = entityFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {
        });

        assertThrows(IllegalArgumentException.class, () -> goodness.compute(o, a, null, null, null));
    }

    @Test
    public void testCompute4() {
        final Organization o = provider.get();
        final Agent a = entityFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {
        });
        final Role r = entityFactory.buildRole(idFactory.build(Role.class, "r"));

        assertThrows(IllegalArgumentException.class, () -> goodness.compute(o, a, r, null, null));
    }

    @Test
    public void testCompute5() {
        final Organization o = provider.get();
        final Agent a = entityFactory.buildAgent(idFactory.build(Agent.class, "a"), new Agent.ContactInfo() {
        });
        final Role r = entityFactory.buildRole(idFactory.build(Role.class, "r"));
        final SpecificationGoal sg = entityFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "sg"));
        final InstanceGoal ig = entityFactory.buildInstanceGoal(idFactory.build(InstanceGoal.class, "ig"), sg, new InstanceGoal.Parameter() {
        });

        assertThrows(IllegalArgumentException.class, () -> goodness.compute(o, a, r, ig, null));
    }

}
