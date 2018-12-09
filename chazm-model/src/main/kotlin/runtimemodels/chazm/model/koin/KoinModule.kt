package runtimemodels.chazm.model.koin

import org.koin.dsl.module.module
import org.koin.experimental.builder.create
import runtimemodels.chazm.api.entity.*
import runtimemodels.chazm.api.id.*
import runtimemodels.chazm.api.organization.Organization
import runtimemodels.chazm.api.relation.*
import runtimemodels.chazm.model.entity.*
import runtimemodels.chazm.model.organization.DefaultOrganization
import runtimemodels.chazm.model.parsers.XmlParser
import runtimemodels.chazm.model.relation.*
import javax.xml.stream.XMLInputFactory

val EntityModule = module(path = DefaultEntity::class.java.packageName) {
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

val OrganizationModule = module(path = "organization") {
    factory<Organization> { create<DefaultOrganization>() }
}

val ParserModule = module(path = XmlParser::class.java.packageName) {
    single<XMLInputFactory> { XMLInputFactory.newInstance() }
    single<XmlParser> { create() }
}

val RelationModule = module(path = DefaultRelation::class.java.packageName) {
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
