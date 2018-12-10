package runtimemodels.chazm.model.koin

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import org.koin.dsl.module.Module
import org.koin.test.KoinTest
import org.koin.test.checkModules
import java.util.stream.Stream

internal class KoinModuleTest : KoinTest {
    @ParameterizedTest(name = "{index} - {1}")
    @ArgumentsSource(ModuleSource::class)
    fun `check modules`(modules: List<Module>, s: String) {
        checkModules(modules)
    }
}

private class ModuleSource : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> = Stream.of(
        Arguments.of(listOf(EntityModule), "EntityModule"),
        Arguments.of(listOf(EntityFactoryModule), "EntityFactoryModule"),
//        Arguments.of(listOf(OrganizationModule), "OrganizationModule"),
//        Arguments.of(listOf(ParserModule),"ParserModule"),
        Arguments.of(listOf(RelationModule), "RelationModule"),
        Arguments.of(listOf(RelationFactoryModule), "RelationFactoryModule"),

        Arguments.of(EntitiesModules, "EntitiesModules"),
        Arguments.of(RelationsModules, "RelationsModules"),
        Arguments.of(ParsingModules, "ParsingModules")
    )
}
