package runtimemodels.chazm.model;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import runtimemodels.chazm.api.Organization;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@SuppressWarnings("javadoc")
public class OrganizationTest {

    private final Injector injector = Guice.createInjector(new OrganizationModule());
    private final Provider<Organization> provider = injector.getProvider(Organization.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testOrganization() {
        final Organization o1 = provider.get();
        final Organization o2 = provider.get();
        assertThat(o1, is(not(sameInstance(o2))));
        assertThat(o1.getAgents().size(), is(equalTo(0)));
        assertThat(o1.getAttributes().size(), is(equalTo(0)));
        assertThat(o1.getCapabilities().size(), is(equalTo(0)));
        assertThat(o1.getCharacteristics().size(), is(equalTo(0)));
        assertThat(o1.getInstanceGoals().size(), is(equalTo(0)));
        assertThat(o1.getPmfs().size(), is(equalTo(0)));
        assertThat(o1.getPolicies().size(), is(equalTo(0)));
        assertThat(o1.getRoles().size(), is(equalTo(0)));
        assertThat(o1.getSpecificationGoals().size(), is(equalTo(0)));
    }

}
