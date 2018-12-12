package runtimemodels.chazm.model.koin

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import org.koin.dsl.module.Module
import org.koin.standalone.StandAloneContext
import org.koin.test.KoinTest
import org.koin.test.checkModules
import java.util.stream.Stream

internal class KoinModuleTest : KoinTest {
    @ParameterizedTest(name = "{index} - {1}")
    @ArgumentsSource(ModuleSource::class)
    fun `check modules`(modules: List<Module>, s: String) {
        checkModules(modules)
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

private class ModuleSource : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> = Stream.of(
        Arguments.of(listOf(EntityModule), "EntityModule"),
        Arguments.of(listOf(EntityManagerModule), "EntityManagerModule"),
        Arguments.of(listOf(EntityFactoryModule), "EntityFactoryModule"),
        Arguments.of(listOf(EventModule), "EventModule"),
        Arguments.of(listOf(EventFactoryModule), "EventFactoryModule"),
        Arguments.of(listOf(RelationModule), "RelationModule"),
        Arguments.of(listOf(RelationManagerModule), "RelationManagerModule"),
        Arguments.of(listOf(RelationFactoryModule), "RelationFactoryModule"),

        Arguments.of(EntitiesModules, "EntitiesModules"),
        Arguments.of(RelationsModules, "RelationsModules"),
        Arguments.of(OrganizationModules, "OrganizationModules"),
        Arguments.of(ParsingModules, "ParsingModules")
    )
}
