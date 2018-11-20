package runtimemodels.chazm.model;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import org.junit.jupiter.api.Test;
import runtimemodels.chazm.api.Organization;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class OrganizationTest {

    private final Injector injector = Guice.createInjector(new OrganizationModule());
    private final Provider<Organization> provider = injector.getProvider(Organization.class);

    @Test
    public void testOrganization() {
        final Organization o1 = provider.get();
        final Organization o2 = provider.get();

        assertAll(
                () -> assertThat(o1).isNotNull(),
                () -> assertThat(o2).isNotNull(),
                () -> assertThat(o1).isNotSameAs(o2),
                () -> assertThat(o1.getAgents().size()).isEqualTo(0),
                () -> assertThat(o1.getAttributes().size()).isEqualTo(0),
                () -> assertThat(o1.getCapabilities().size()).isEqualTo(0),
                () -> assertThat(o1.getCharacteristics().size()).isEqualTo(0),
                () -> assertThat(o1.getInstanceGoals().size()).isEqualTo(0),
                () -> assertThat(o1.getPmfs().size()).isEqualTo(0),
                () -> assertThat(o1.getPolicies().size()).isEqualTo(0),
                () -> assertThat(o1.getRoles().size()).isEqualTo(0),
                () -> assertThat(o1.getSpecificationGoals().size()).isEqualTo(0)
        );
    }

}
