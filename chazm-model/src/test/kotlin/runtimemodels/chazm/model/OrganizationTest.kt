package runtimemodels.chazm.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.koin.standalone.StandAloneContext.loadKoinModules
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.standalone.get
import org.koin.test.KoinTest
import runtimemodels.chazm.api.organization.Organization
import runtimemodels.chazm.model.koin.OrganizationModules

internal class OrganizationTest : KoinTest {
    @Test
    fun `the initial state of a new instance`() {
        val organization: Organization = get()

        assertAll(
            { assertThat(organization).isNotNull },
            { assertThat(organization.agents.size).isEqualTo(0) },
            { assertThat(organization.attributes.size).isEqualTo(0) },
            { assertThat(organization.capabilities.size).isEqualTo(0) },
            { assertThat(organization.characteristics.size).isEqualTo(0) },
            { assertThat(organization.instanceGoals.size).isEqualTo(0) },
            { assertThat(organization.pmfs.size).isEqualTo(0) },
            { assertThat(organization.policies.size).isEqualTo(0) },
            { assertThat(organization.roles.size).isEqualTo(0) },
            { assertThat(organization.specificationGoals.size).isEqualTo(0) }
        )
    }

    @Test
    fun `no two instances are the same`() {
        val organization: Organization = get()

        assertAll(
            { assertThat(organization).isNotSameAs(get<Organization>()) },
            { assertThat(organization).isNotSameAs(get<Organization>()) }
        )
    }

    companion object {
        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            loadKoinModules(OrganizationModules)
        }

        @AfterAll
        @JvmStatic
        fun afterAll() {
            stopKoin()
        }
    }
}
