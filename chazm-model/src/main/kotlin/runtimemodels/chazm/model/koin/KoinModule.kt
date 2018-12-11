package runtimemodels.chazm.model.koin

import org.koin.dsl.module.module
import org.koin.experimental.builder.create
import runtimemodels.chazm.api.function.Effectiveness
import runtimemodels.chazm.api.function.Goodness
import runtimemodels.chazm.api.organization.Organization
import runtimemodels.chazm.model.function.DefaultEffectiveness
import runtimemodels.chazm.model.function.DefaultGoodness
import runtimemodels.chazm.model.notification.DefaultMediator
import runtimemodels.chazm.model.notification.DefaultPublisher
import runtimemodels.chazm.model.notification.Mediator
import runtimemodels.chazm.model.notification.Publisher
import runtimemodels.chazm.model.organization.DefaultOrganization
import runtimemodels.chazm.model.parser.RoleDiagramParser
import runtimemodels.chazm.model.parser.XmlParser
import runtimemodels.chazm.model.parser.entity.*
import runtimemodels.chazm.model.parser.relation.AssignmentParser
import runtimemodels.chazm.model.parser.relation.HasParser
import runtimemodels.chazm.model.parser.relation.PossessesParser
import javax.xml.stream.XMLInputFactory

private class KoinModule





private val OrganizationModule = module(path = KoinModule::class.java.packageName) {
    single<Mediator> { create<DefaultMediator>() }
    single<Publisher> { create<DefaultPublisher>() }
    single<Effectiveness> { create<DefaultEffectiveness>() }
    single<Goodness> { create<DefaultGoodness>() }
    factory<Organization> { create<DefaultOrganization>() }
}
private val ParserModule = module(path = KoinModule::class.java.packageName) {
    single<XMLInputFactory> { XMLInputFactory.newInstance() }

    single<XmlParser>()

    single<RoleDiagramParser>()

    single<ParseAgent>()
    single<ParseAttribute>()
    single<ParseCapability>()
    single<ParseCharacteristic>()
    single<ParseInstanceGoal>()
    single<ParsePmf>()
    single<ParsePolicy>()
    single<ParseRole>()
    single<ParseSpecificationGoal>()

    single<AssignmentParser>()
    single<HasParser>()
    single<PossessesParser>()
}

val EntitiesModules = listOf(EntityModule, EntityFactoryModule)
val EventModules = listOf(EventModule, EventFactoryModule)
val RelationsModules = listOf(RelationModule, RelationFactoryModule)
val OrganizationModules = EventModules + OrganizationModule + EntityManagerModule
val ParsingModules = EntitiesModules + RelationsModules + ParserModule
