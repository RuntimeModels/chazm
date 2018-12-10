package runtimemodels.chazm.model.koin

import org.koin.core.parameter.parametersOf
import org.koin.dsl.context.ModuleDefinition
import org.koin.dsl.definition.BeanDefinition
import org.koin.dsl.module.module
import org.koin.experimental.builder.create
import org.koin.standalone.KoinComponent
import org.koin.standalone.get
import runtimemodels.chazm.api.entity.*
import runtimemodels.chazm.api.function.Effectiveness
import runtimemodels.chazm.api.function.Goodness
import runtimemodels.chazm.api.organization.Organization
import runtimemodels.chazm.api.relation.*
import runtimemodels.chazm.model.entity.*
import runtimemodels.chazm.model.entity.impl.*
import runtimemodels.chazm.model.event.*
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
import runtimemodels.chazm.model.relation.RelationFactory
import runtimemodels.chazm.model.relation.impl.*
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

private class KoinEventFactory : EventFactory, KoinComponent {
    override fun build(category: EventType, achieves: Achieves): AchievesEvent =
        get(parameters = { parametersOf(category, achieves) })

    override fun build(category: EventType, agent: Agent): AgentEvent =
        get(parameters = { parametersOf(category, agent) })

    override fun build(category: EventType, assignment: Assignment): AssignmentEvent =
        get(parameters = { parametersOf(category, assignment) })

    override fun build(category: EventType, attribute: Attribute): AttributeEvent =
        get(parameters = { parametersOf(category, attribute) })

    override fun build(category: EventType, capability: Capability): CapabilityEvent =
        get(parameters = { parametersOf(category, capability) })

    override fun build(category: EventType, characteristic: Characteristic): CharacteristicEvent =
        get(parameters = { parametersOf(category, characteristic) })

    override fun build(category: EventType, contains: Contains): ContainsEvent =
        get(parameters = { parametersOf(category, contains) })

    override fun build(category: EventType, has: Has): HasEvent =
        get(parameters = { parametersOf(category, has) })

    override fun build(category: EventType, goal: InstanceGoal): InstanceGoalEvent =
        get(parameters = { parametersOf(category, goal) })

    override fun build(category: EventType, moderates: Moderates): ModeratesEvent =
        get(parameters = { parametersOf(category, moderates) })

    override fun build(category: EventType, needs: Needs): NeedsEvent =
        get(parameters = { parametersOf(category, needs) })

    override fun build(category: EventType, pmf: Pmf): PmfEvent =
        get(parameters = { parametersOf(category, pmf) })

    override fun build(category: EventType, policy: Policy): PolicyEvent =
        get(parameters = { parametersOf(category, policy) })

    override fun build(category: EventType, possesses: Possesses): PossessesEvent =
        get(parameters = { parametersOf(category, possesses) })

    override fun build(category: EventType, requires: Requires): RequiresEvent =
        get(parameters = { parametersOf(category, requires) })

    override fun build(category: EventType, role: Role): RoleEvent =
        get(parameters = { parametersOf(category, role) })

    override fun build(category: EventType, goal: SpecificationGoal): SpecificationGoalEvent =
        get(parameters = { parametersOf(category, goal) })

    override fun build(category: EventType, uses: Uses): UsesEvent =
        get(parameters = { parametersOf(category, uses) })
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
val EventModule = module(path = KoinModule::class.java.packageName) {
    factory { (category: EventType, achieves: Achieves) -> AchievesEvent(category = category, achieves = achieves) }
    factory { (category: EventType, agent: Agent) -> AgentEvent(category = category, agent = agent) }
    factory { (category: EventType, assignment: Assignment) -> AssignmentEvent(category = category, assignment = assignment) }
    factory { (category: EventType, attribute: Attribute) -> AttributeEvent(category = category, attribute = attribute) }
    factory { (category: EventType, capability: Capability) -> CapabilityEvent(category = category, capability = capability) }
    factory { (category: EventType, characteristic: Characteristic) -> CharacteristicEvent(category = category, characteristic = characteristic) }
    factory { (category: EventType, contains: Contains) -> ContainsEvent(category = category, contains = contains) }
    factory { (category: EventType, has: Has) -> HasEvent(category = category, has = has) }
    factory { (category: EventType, goal: InstanceGoal) -> InstanceGoalEvent(category = category, goal = goal) }
    factory { (category: EventType, moderates: Moderates) -> ModeratesEvent(category = category, moderates = moderates) }
    factory { (category: EventType, needs: Needs) -> NeedsEvent(category = category, needs = needs) }
    factory { (category: EventType, pmf: Pmf) -> PmfEvent(category = category, pmf = pmf) }
    factory { (category: EventType, policy: Policy) -> PolicyEvent(category = category, policy = policy) }
    factory { (category: EventType, possesses: Possesses) -> PossessesEvent(category = category, possesses = possesses) }
    factory { (category: EventType, requires: Requires) -> RequiresEvent(category = category, requires = requires) }
    factory { (category: EventType, role: Role) -> RoleEvent(category = category, role = role) }
    factory { (category: EventType, goal: SpecificationGoal) -> SpecificationGoalEvent(category = category, goal = goal) }
    factory { (category: EventType, uses: Uses) -> UsesEvent(category = category, uses = uses) }
}
val EventFactoryModule = module(path = KoinModule::class.java.packageName) {
    single<EventFactory> { create<KoinEventFactory>() }
}
private val OrganizationModule = module(path = KoinModule::class.java.packageName) {
    factory<AgentManager> { create<DefaultAgentManager>() }
    factory<AttributeManager> { create<DefaultAttributeManager>() }
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
val EventModules = listOf(EventModule, EventFactoryModule)
val RelationsModules = listOf(RelationModule, RelationFactoryModule)
val OrganizationModules = EventModules + OrganizationModule
val ParsingModules = EntitiesModules + RelationsModules + ParserModule

inline fun <reified T : Any> ModuleDefinition.single(): BeanDefinition<T> {
    return single { create<T>() }
}
