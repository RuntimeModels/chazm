package runtimemodels.chazm.model.entity;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import runtimemodels.chazm.api.entity.SpecificationGoal;
import runtimemodels.chazm.api.id.UniqueId;
import runtimemodels.chazm.model.id.IdFactory;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@SuppressWarnings("javadoc")
public class SpecificationGoalEntityTest {

    private final Injector injector = Guice.createInjector(new EntityModule());
    private final SpecificationGoalFactory goalFactory = injector.getInstance(SpecificationGoalFactory.class);
    private final IdFactory idFactory = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testSpecificationGoalFactory() {
        final UniqueId<SpecificationGoal> i1 = idFactory.build(SpecificationGoal.class, 1L);
        final SpecificationGoal g1 = goalFactory.buildSpecificationGoal(i1);
        final SpecificationGoal g2 = goalFactory.buildSpecificationGoal(i1);

        assertThat(g1, is(not(nullValue())));
        assertThat(g1, is(not(sameInstance(g2))));
    }

    @Test
    @Ignore
    public void testSpecificationGoalFactoryWithNullId() {
        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(containsString("1st parameter of SpecificationGoalEntity.<init>(SpecificationGoalEntity.java:13) is not @Nullable"));
        goalFactory.buildSpecificationGoal(null);
    }

    @Test
    public void testGetId() {
        final UniqueId<SpecificationGoal> i1 = idFactory.build(SpecificationGoal.class, 1L);
        final UniqueId<SpecificationGoal> i2 = idFactory.build(SpecificationGoal.class, 1L);
        final SpecificationGoal g1 = goalFactory.buildSpecificationGoal(i1);
        final SpecificationGoal g2 = goalFactory.buildSpecificationGoal(i2);

        assertThat(g1.getId(), is(sameInstance(i1)));
        assertThat(g1.getId(), is(not(sameInstance(g2.getId()))));
    }

    @Test
    public void testEqualsObject() {
        final UniqueId<SpecificationGoal> i1 = idFactory.build(SpecificationGoal.class, 1L);
        final UniqueId<SpecificationGoal> i2 = idFactory.build(SpecificationGoal.class, 2L);
        final SpecificationGoal g1 = goalFactory.buildSpecificationGoal(i1);
        final SpecificationGoal g2 = goalFactory.buildSpecificationGoal(i2);
        final SpecificationGoal g3 = goalFactory.buildSpecificationGoal(i1);

        assertThat(g1, is(not(equalTo(g2))));
        assertThat(g1, is(equalTo(g3)));
        assertThat(g1, is(not(equalTo(""))));
    }

    @Test
    public void testHashCode() {
        final UniqueId<SpecificationGoal> i1 = idFactory.build(SpecificationGoal.class, 1L);
        final UniqueId<SpecificationGoal> i2 = idFactory.build(SpecificationGoal.class, 2L);
        final SpecificationGoal g1 = goalFactory.buildSpecificationGoal(i1);
        final SpecificationGoal g2 = goalFactory.buildSpecificationGoal(i2);
        final SpecificationGoal g3 = goalFactory.buildSpecificationGoal(i1);

        assertThat(g1.hashCode(), is(not(equalTo(g2.hashCode()))));
        assertThat(g1.hashCode(), is(equalTo(g3.hashCode())));
    }

    @Test
    public void testToString() {
        final UniqueId<SpecificationGoal> i1 = idFactory.build(SpecificationGoal.class, 1L);
        final UniqueId<SpecificationGoal> i2 = idFactory.build(SpecificationGoal.class, 2L);
        final SpecificationGoal g1 = goalFactory.buildSpecificationGoal(i1);
        final SpecificationGoal g2 = goalFactory.buildSpecificationGoal(i2);
        final SpecificationGoal g3 = goalFactory.buildSpecificationGoal(i1);

        assertThat(g1.toString(), is(not(equalTo(g2.toString()))));
        assertThat(g1.toString(), is(equalTo(g3.toString())));
    }

}
