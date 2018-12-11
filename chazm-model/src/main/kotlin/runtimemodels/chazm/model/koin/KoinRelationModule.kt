package runtimemodels.chazm.model.koin

import org.koin.core.parameter.parametersOf
import org.koin.dsl.module.module
import org.koin.experimental.builder.create
import org.koin.standalone.KoinComponent
import org.koin.standalone.get
import runtimemodels.chazm.api.entity.*
import runtimemodels.chazm.api.relation.*
import runtimemodels.chazm.model.relation.*
import runtimemodels.chazm.model.relation.impl.*

private class KoinRelationModule

private class KoinAchievesFactory : AchievesFactory, KoinComponent {
    override fun build(role: Role, goal: SpecificationGoal): Achieves =
        get(parameters = { parametersOf(role, goal) })
}

private class KoinAssignmentFactory : AssignmentFactory, KoinComponent {
    override fun build(agent: Agent, role: Role, goal: InstanceGoal): Assignment =
        get(parameters = { parametersOf(agent, role, goal) })
}

private class KoinContainsFactory : ContainsFactory, KoinComponent {
    override fun build(role: Role, characteristic: Characteristic, value: Double): Contains =
        get(parameters = { parametersOf(role, characteristic, value) })
}

private class KoinHasFactory : HasFactory, KoinComponent {
    override fun build(agent: Agent, attribute: Attribute, value: Double): Has =
        get(parameters = { parametersOf(agent, attribute, value) })
}

private class KoinModeratesFactory : ModeratesFactory, KoinComponent {
    override fun build(pmf: Pmf, attribute: Attribute): Moderates =
        get(parameters = { parametersOf(pmf, attribute) })
}

private class KoinNeedsFactory : NeedsFactory, KoinComponent {
    override fun build(role: Role, attribute: Attribute): Needs =
        get(parameters = { parametersOf(role, attribute) })
}

private class KoinPossessesFactory : PossessesFactory, KoinComponent {
    override fun build(agent: Agent, capability: Capability, score: Double): Possesses =
        get(parameters = { parametersOf(agent, capability, score) })
}

private class KoinRequiresFactory : RequiresFactory, KoinComponent {
    override fun build(role: Role, capability: Capability): Requires =
        get(parameters = { parametersOf(role, capability) })
}

private class KoinUsesFactory : UsesFactory, KoinComponent {
    override fun build(role: Role, pmf: Pmf): Uses =
        get(parameters = { parametersOf(role, pmf) })
}

private class KoinRelationFactory(
    achievesFactory: AchievesFactory,
    assignmentFactory: AssignmentFactory,
    containsFactory: ContainsFactory,
    hasFactory: HasFactory,
    moderatesFactory: ModeratesFactory,
    needsFactory: NeedsFactory,
    possessesFactory: PossessesFactory,
    requiresFactory: RequiresFactory,
    usesFactory: UsesFactory
) : RelationFactory, KoinComponent,
    AchievesFactory by achievesFactory,
    AssignmentFactory by assignmentFactory,
    ContainsFactory by containsFactory,
    HasFactory by hasFactory,
    ModeratesFactory by moderatesFactory,
    NeedsFactory by needsFactory,
    PossessesFactory by possessesFactory,
    RequiresFactory by requiresFactory,
    UsesFactory by usesFactory

val RelationFactoryModule = module(KoinRelationModule::class.java.packageName) {
    single<AchievesFactory> { create<KoinAchievesFactory>() }
    single<AssignmentFactory> { create<KoinAssignmentFactory>() }
    single<ContainsFactory> { create<KoinContainsFactory>() }
    single<HasFactory> { create<KoinHasFactory>() }
    single<ModeratesFactory> { create<KoinModeratesFactory>() }
    single<NeedsFactory> { create<KoinNeedsFactory>() }
    single<PossessesFactory> { create<KoinPossessesFactory>() }
    single<RequiresFactory> { create<KoinRequiresFactory>() }
    single<UsesFactory> { create<KoinUsesFactory>() }
    single<RelationFactory> { create<KoinRelationFactory>() }
}

val RelationModule = module(path = KoinRelationModule::class.java.packageName) {
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

val RelationManagerModule = module(path = KoinRelationModule::class.java.packageName) {

}
