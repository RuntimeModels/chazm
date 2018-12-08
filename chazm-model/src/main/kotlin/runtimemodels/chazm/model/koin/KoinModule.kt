package runtimemodels.chazm.model.koin

import org.koin.dsl.module.module
import runtimemodels.chazm.api.entity.*
import runtimemodels.chazm.api.id.*
import runtimemodels.chazm.api.relation.*
import runtimemodels.chazm.model.entity.*
import runtimemodels.chazm.model.relation.*
import javax.xml.stream.XMLInputFactory

val KoinModule = module {

    val EntityModule = module("entity") {
        factory<Agent> { (id: AgentId) -> AgentEntity(id = id) }
        factory<Attribute> { (id: AttributeId, type: Attribute.Type) -> AttributeEntity(id = id, type = type) }
        factory<Capability> { (id: CapabilityId) -> CapabilityEntity(id = id) }
        factory<Characteristic> { (id: CharacteristicId) -> CharacteristicEntity(id = id) }
        factory<InstanceGoal> { (id: InstanceGoalId, goal: SpecificationGoal) -> InstanceGoalEntity(id = id, goal = goal) }
        factory<Pmf> { (id: PmfId) -> PmfEntity(id = id) }
        factory<Policy> { (id: PolicyId) -> PolicyEntity(id = id) }
        factory<Role> { (id: RoleId) -> RoleEntity(id = id) }
        factory<SpecificationGoal> { (id: SpecificationGoalId) -> SpecificationGoalEntity(id = id) }
    }

    val OrganizationModule = module(path = "organization") {
        //    factory<Organization> {
//        DefaultOrganization(
//            it
//        )
//    }
    }

    val ParserModule = module(path = "parser") {
        single<XMLInputFactory> { XMLInputFactory.newInstance() }
    }

    val RelationModule = module(path = "relation") {
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
}
