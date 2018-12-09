package runtimemodels.chazm.model.koin

import org.junit.jupiter.api.Test
import org.koin.test.KoinTest
import org.koin.test.checkModules

internal class KoinModuleTest : KoinTest {
    @Test
    fun `check entity module`() {
        checkModules(listOf(EntityModule))
    }

    @Test
    fun `check organization module`() {
        checkModules(listOf(OrganizationModule))
    }

    @Test
    fun `check parser module`() {
        checkModules(listOf(ParserModule))
    }

    @Test
    fun `check relation module`() {
        checkModules(listOf(RelationModule))
    }
}
