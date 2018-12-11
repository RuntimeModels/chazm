package runtimemodels.chazm.model

import com.google.inject.Guice
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.koin.standalone.StandAloneContext
import org.koin.test.KoinTest
import runtimemodels.chazm.api.organization.Organization
import runtimemodels.chazm.model.guice.OrganizationModule
import runtimemodels.chazm.model.koin.ParsingModules

internal class OrganizationTest : KoinTest {

    private val injector = Guice.createInjector(OrganizationModule())
    private val provider = injector.getProvider(Organization::class.java)

    @Test
    fun `the initial state of a new instance`() {
//        val organization = DefaultOrganization()
    }

    @Test
    fun `koin`() {
//        startKoin(listOf(KoinModule))
//
//        val o1:Organization = get()
//        val o2:Organization = get()
//
//        assertAll(
//            { assertThat(o1).isNotNull },
//            { assertThat(o2).isNotNull },
//            { assertThat(o1).isNotSameAs(o2) },
//            { assertThat(o1.agents.size).isEqualTo(0) },
//            { assertThat(o1.attributes.size).isEqualTo(0) },
//            { assertThat(o1.capabilities.size).isEqualTo(0) },
//            { assertThat(o1.characteristics.size).isEqualTo(0) },
//            { assertThat(o1.instanceGoals.size).isEqualTo(0) },
//            { assertThat(o1.pmfs.size).isEqualTo(0) },
//            { assertThat(o1.policies.size).isEqualTo(0) },
//            { assertThat(o1.roles.size).isEqualTo(0) },
//            { assertThat(o1.specificationGoals.size).isEqualTo(0) }
//        )
    }

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

    companion object {
        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            StandAloneContext.loadKoinModules(ParsingModules)
        }

        @AfterAll
        @JvmStatic
        fun afterAll() {
            StandAloneContext.stopKoin()
        }
    }
}
