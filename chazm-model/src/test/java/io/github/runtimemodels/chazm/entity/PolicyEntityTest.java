package io.github.runtimemodels.chazm.entity;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import io.github.runtimemodels.chazm.id.IdFactory;
import io.github.runtimemodels.chazm.id.UniqueId;
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
public class PolicyEntityTest {

    private final Injector injector = Guice.createInjector(new EntityModule());
    private final PolicyFactory policyFactory = injector.getInstance(PolicyFactory.class);
    private final IdFactory idFactory = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testPolicyFactory() {
        final UniqueId<Policy> i1 = idFactory.build(Policy.class, 1L);
        final Policy p1 = policyFactory.buildPolicy(i1);
        final Policy p2 = policyFactory.buildPolicy(i1);

        assertThat(p1, is(not(nullValue())));
        assertThat(p1, is(not(sameInstance(p2))));
    }

    @Test
    public void testPolicyFactoryWithNullId() {
        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage((containsString("1st parameter of io.github.runtimemodels.chazm.entity.PolicyEntity.<init>(PolicyEntity.java:13) is not @Nullable")));
        policyFactory.buildPolicy(null);
    }

    @Test
    public void testGetId() {
        final UniqueId<Policy> i1 = idFactory.build(Policy.class, 1L);
        final UniqueId<Policy> i2 = idFactory.build(Policy.class, 1L);
        final Policy p1 = policyFactory.buildPolicy(i1);
        final Policy p2 = policyFactory.buildPolicy(i2);

        assertThat(p1.getId(), is(sameInstance(i1)));
        assertThat(p1.getId(), is(not(sameInstance(p2.getId()))));
    }

    @Test
    public void testEqualsObject() {
        final UniqueId<Policy> i1 = idFactory.build(Policy.class, 1L);
        final UniqueId<Policy> i2 = idFactory.build(Policy.class, 2L);
        final Policy p1 = policyFactory.buildPolicy(i1);
        final Policy p2 = policyFactory.buildPolicy(i2);
        final Policy p3 = policyFactory.buildPolicy(i1);

        assertThat(p1, is(not(equalTo(p2))));
        assertThat(p1, is(equalTo(p3)));
        assertThat(p1, is(not(equalTo(""))));
    }

    @Test
    public void testHashCode() {
        final UniqueId<Policy> i1 = idFactory.build(Policy.class, 1L);
        final UniqueId<Policy> i2 = idFactory.build(Policy.class, 2L);
        final Policy p1 = policyFactory.buildPolicy(i1);
        final Policy p2 = policyFactory.buildPolicy(i2);
        final Policy p3 = policyFactory.buildPolicy(i1);

        assertThat(p1.hashCode(), is(not(equalTo(p2.hashCode()))));
        assertThat(p1.hashCode(), is(equalTo(p3.hashCode())));
    }

    @Test
    public void testToString() {
        final UniqueId<Policy> i1 = idFactory.build(Policy.class, 1L);
        final UniqueId<Policy> i2 = idFactory.build(Policy.class, 2L);
        final Policy p1 = policyFactory.buildPolicy(i1);
        final Policy p2 = policyFactory.buildPolicy(i2);
        final Policy p3 = policyFactory.buildPolicy(i1);

        assertThat(p1.toString(), is(not(equalTo(p2.toString()))));
        assertThat(p1.toString(), is(equalTo(p3.toString())));
    }

}
