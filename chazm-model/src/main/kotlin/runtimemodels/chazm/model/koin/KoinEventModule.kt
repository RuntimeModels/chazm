package runtimemodels.chazm.model.koin

import org.koin.core.parameter.parametersOf
import org.koin.dsl.module.module
import org.koin.experimental.builder.create
import org.koin.standalone.KoinComponent
import org.koin.standalone.get
import runtimemodels.chazm.api.entity.*
import runtimemodels.chazm.api.relation.*
import runtimemodels.chazm.model.entity.*
import runtimemodels.chazm.model.event.EventFactory
import runtimemodels.chazm.model.event.EventType
import runtimemodels.chazm.model.relation.*

private class KoinEventModule

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

val EventFactoryModule = module(path = KoinEventModule::class.java.packageName) {
    single<EventFactory> { create<KoinEventFactory>() }
}

val EventModule = module(path = KoinEventModule::class.java.packageName) {
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
