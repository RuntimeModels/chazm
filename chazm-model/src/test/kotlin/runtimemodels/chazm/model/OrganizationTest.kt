package runtimemodels.chazm.model

import com.google.inject.Guice
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import runtimemodels.chazm.api.organization.Organization
import runtimemodels.chazm.model.organizations.OrganizationModule

class OrganizationTest {

    private val injector = Guice.createInjector(OrganizationModule())
    private val provider = injector.getProvider(Organization::class.java)

    @Test
    fun testOrganization() {
        val o1 = provider.get()
        val o2 = provider.get()

        assertAll(
            { assertThat(o1).isNotNull },
            { assertThat(o2).isNotNull },
            { assertThat(o1).isNotSameAs(o2) },
            { assertThat(o1.agents.size).isEqualTo(0) },
            { assertThat(o1.attributes.size).isEqualTo(0) },
            { assertThat(o1.capabilities.size).isEqualTo(0) },
            { assertThat(o1.characteristics.size).isEqualTo(0) },
            { assertThat(o1.instanceGoals.size).isEqualTo(0) },
            { assertThat(o1.pmfs.size).isEqualTo(0) },
            { assertThat(o1.policies.size).isEqualTo(0) },
            { assertThat(o1.roles.size).isEqualTo(0) },
            { assertThat(o1.specificationGoals.size).isEqualTo(0) }
        )
    }

}
