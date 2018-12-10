package runtimemodels.chazm.model.koin

import org.koin.core.parameter.parametersOf
import org.koin.dsl.context.ModuleDefinition
import org.koin.dsl.definition.BeanDefinition
import org.koin.dsl.module.module
import org.koin.experimental.builder.create
import org.koin.standalone.KoinComponent
import org.koin.standalone.get
import runtimemodels.chazm.api.entity.*
import runtimemodels.chazm.api.id.*
import runtimemodels.chazm.api.organization.Organization
import runtimemodels.chazm.api.relation.*
import runtimemodels.chazm.model.entity.*
import runtimemodels.chazm.model.factory.EntityFactory
import runtimemodels.chazm.model.factory.RelationFactory
import runtimemodels.chazm.model.factory.entity.*
import runtimemodels.chazm.model.organization.DefaultOrganization
import runtimemodels.chazm.model.parsers.*
import runtimemodels.chazm.model.relation.*
import javax.xml.stream.XMLInputFactory

private class KoinModule

private class KoinAgentFactory : AgentFactory, KoinComponent {
    override fun build(id: AgentId, contactInfo: Map<Any, Any>): Agent =
        get(parameters = { parametersOf(id) })
}

private class KoinAttributeFactory : AttributeFactory, KoinComponent {
    override fun build(id: AttributeId, type: Attribute.Type): Attribute =
        get(parameters = { parametersOf(id, type) })
}

private class KoinCapabilityFactory : CapabilityFactory, KoinComponent {
    override fun build(id: CapabilityId): Capability =
        get(parameters = { parametersOf(id) })
}

private class KoinCharacteristicFactory : CharacteristicFactory, KoinComponent {
    override fun build(id: CharacteristicId): Characteristic =
        get(parameters = { parametersOf(id) })
}

private class KoinInstanceGoalFactory : InstanceGoalFactory, KoinComponent {
    override fun build(id: InstanceGoalId, goal: SpecificationGoal, parameter: Map<Any, Any>): InstanceGoal =
        get(parameters = { parametersOf(id, goal) })
}

private class KoinPmfFactory : PmfFactory, KoinComponent {
    override fun build(id: PmfId): Pmf =
        get(parameters = { parametersOf(id) })
}

private class KoinPolicyFactory : PolicyFactory, KoinComponent {
    override fun build(id: PolicyId): Policy =
        get(parameters = { parametersOf(id) })
}

private class KoinRoleFactory : RoleFactory, KoinComponent {
    override fun build(id: RoleId): Role =
        get(parameters = { parametersOf(id) })
}

private class KoinSpecifcationGoalFactory : SpecificationGoalFactory, KoinComponent {
    override fun build(id: SpecificationGoalId): SpecificationGoal =
        get(parameters = { parametersOf(id) })
}

private class KoinEntityFactory(
    agentFactory: AgentFactory,
    attributeFactory: AttributeFactory,
    capabilityFactory: CapabilityFactory,
    characteristicFactory: CharacteristicFactory,
    instanceGoalFactory: InstanceGoalFactory,
    pmfFactory: PmfFactory,
    policyFactory: PolicyFactory,
    roleFactory: RoleFactory,
    specificationGoalFactory: SpecificationGoalFactory
) : EntityFactory, KoinComponent,
    AgentFactory by agentFactory,
    AttributeFactory by attributeFactory,
    CapabilityFactory by capabilityFactory,
    CharacteristicFactory by characteristicFactory,
    InstanceGoalFactory by instanceGoalFactory,
    PmfFactory by pmfFactory,
    PolicyFactory by policyFactory,
    RoleFactory by roleFactory,
    SpecificationGoalFactory by specificationGoalFactory

private class KoinRelationFactory : RelationFactory, KoinComponent {
    override fun build(role: Role, goal: SpecificationGoal): Achieves =
        get(parameters = { parametersOf(role, goal) })

    override fun build(agent: Agent, role: Role, goal: InstanceGoal): Assignment =
        get(parameters = { parametersOf(agent, role, goal) })

    override fun build(role: Role, characteristic: Characteristic, value: Double): Contains =
        get(parameters = { parametersOf(role, characteristic, value) })

    override fun build(agent: Agent, attribute: Attribute, value: Double): Has =
        get(parameters = { parametersOf(agent, attribute, value) })

    override fun build(pmf: Pmf, attribute: Attribute): Moderates =
        get(parameters = { parametersOf(pmf, attribute) })

    override fun build(role: Role, attribute: Attribute): Needs =
        get(parameters = { parametersOf(role, attribute) })

    override fun build(agent: Agent, capability: Capability, score: Double): Possesses =
        get(parameters = { parametersOf(agent, capability, score) })

    override fun build(role: Role, capability: Capability): Requires =
        get(parameters = { parametersOf(role, capability) })

    override fun build(role: Role, pmf: Pmf): Uses =
        get(parameters = { parametersOf(role, pmf) })
}

val EntityModule = module(path = KoinModule::class.java.packageName) {
    factory<Agent> { (id: AgentId) -> DefaultAgent(id = id) }
    factory<Attribute> { (id: AttributeId, type: Attribute.Type) -> DefaultAttribute(id = id, type = type) }
    factory<Capability> { (id: CapabilityId) -> DefaultCapability(id = id) }
    factory<Characteristic> { (id: CharacteristicId) -> DefaultCharacteristic(id = id) }
    factory<InstanceGoal> { (id: InstanceGoalId, goal: SpecificationGoal) -> DefaultInstanceGoal(id = id, goal = goal) }
    factory<Pmf> { (id: PmfId) -> DefaultPmf(id = id) }
    factory<Policy> { (id: PolicyId) -> DefaultPolicy(id = id) }
    factory<Role> { (id: RoleId) -> DefaultRole(id = id) }
    factory<SpecificationGoal> { (id: SpecificationGoalId) -> DefaultSpecificationGoal(id = id) }
}
val EntityFactoryModule = module(path = KoinModule::class.java.packageName) {
    single<AgentFactory> { create<KoinAgentFactory>() }
    single<AttributeFactory> { create<KoinAttributeFactory>() }
    single<CapabilityFactory> { create<KoinCapabilityFactory>() }
    single<CharacteristicFactory> { create<KoinCharacteristicFactory>() }
    single<InstanceGoalFactory> { create<KoinInstanceGoalFactory>() }
    single<PmfFactory> { create<KoinPmfFactory>() }
    single<PolicyFactory> { create<KoinPolicyFactory>() }
    single<RoleFactory> { create<KoinRoleFactory>() }
    single<SpecificationGoalFactory> { create<KoinSpecifcationGoalFactory>() }
    single<EntityFactory> { create<KoinEntityFactory>() }
}
val OrganizationModule = module(path = KoinModule::class.java.packageName) {
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
val RelationModule = module(path = KoinModule::class.java.packageName) {
    factory<Achieves> { (role: Role, goal: SpecificationGoal) -> AchievesRelation(role = role, goal = goal) }
    factory<Assignment> { (agent: Agent, role: Role, goal: InstanceGoal) -> AssignmentRelation(agent = agent, role = role, goal = goal) }
    factory<Contains> { (role: Role, characteristic: Characteristic, value: Double) -> ContainsRelation(role = role, characteristic = characteristic, value = value) }
    factory<Has> { (agent: Agent, attribute: Attribute, value: Double) -> HasRelation(agent = agent, attribute = attribute, value = value) }
    factory<Moderates> { (pmf: Pmf, attribute: Attribute) -> ModeratesRelation(pmf = pmf, attribute = attribute) }
    factory<Needs> { (role: Role, attribute: Attribute) -> NeedsRelation(role = role, attribute = attribute) }
    factory<Possesses> { (agent: Agent, capability: Capability, score: Double) -> PossessesRelation(agent = agent, capability = capability, score = score) }
    factory<Requires> { (role: Role, capability: Capability) -> RequiresRelation(role = role, capability = capability) }
    factory<Uses> { (role: Role, pmf: Pmf) -> UsesRelation(role = role, pmf = pmf) }
}
val RelationFactoryModule = module(KoinModule::class.java.packageName) {
    single<RelationFactory> { create<KoinRelationFactory>() }
}

val EntitiesModules = listOf(EntityModule, EntityFactoryModule)
val RelationsModules = listOf(RelationModule, RelationFactoryModule)
val ParsingModules = EntitiesModules + RelationsModules + ParserModule

inline fun <reified T : Any> ModuleDefinition.single(): BeanDefinition<T> {
    return single { create<T>() }
}
