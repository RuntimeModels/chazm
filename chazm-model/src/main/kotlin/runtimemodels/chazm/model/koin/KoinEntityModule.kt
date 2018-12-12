package runtimemodels.chazm.model.koin

import org.koin.core.parameter.parametersOf
import org.koin.dsl.module.module
import org.koin.experimental.builder.create
import org.koin.standalone.KoinComponent
import org.koin.standalone.get
import runtimemodels.chazm.api.entity.*
import runtimemodels.chazm.model.entity.*
import runtimemodels.chazm.model.entity.impl.*

private class KoinEntityModule

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

val EntityFactoryModule = module(path = KoinEntityModule::class.java.packageName) {
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

val EntityModule = module(path = KoinEntityModule::class.java.packageName) {
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

val EntityManagerModule = module(path = KoinEntityModule::class.java.packageName) {
    factory<AgentManager> { DefaultAgentManager(mutableMapOf()) }
    factory<AttributeManager> { DefaultAttributeManager(mutableMapOf()) }
    factory<CapabilityManager> { DefaultCapabilityManager(mutableMapOf()) }
    factory<CharacteristicManager> { DefaultCharacteristicManager(mutableMapOf()) }
    factory<InstanceGoalManager> { DefaultInstanceGoalManager(mutableMapOf(), mutableMapOf()) }
    factory<PmfManager> { DefaultPmfManager(mutableMapOf()) }
    factory<PolicyManager> { DefaultPolicyManager(mutableMapOf()) }
    factory<RoleManager> { DefaultRoleManager(mutableMapOf()) }
    factory<SpecificationGoalManager> { DefaultSpecificationGoalManager(mutableMapOf()) }
}
