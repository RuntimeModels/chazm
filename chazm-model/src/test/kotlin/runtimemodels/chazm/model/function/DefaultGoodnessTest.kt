package runtimemodels.chazm.model.function

import any
import com.google.inject.Guice
import mock
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import runtimemodels.chazm.api.entity.*
import runtimemodels.chazm.api.function.Goodness
import runtimemodels.chazm.api.id.AttributeId
import runtimemodels.chazm.api.id.RoleId
import runtimemodels.chazm.api.id.SpecificationGoalId
import runtimemodels.chazm.api.organization.*
import runtimemodels.chazm.api.relation.Achieves
import runtimemodels.chazm.api.relation.Needs
import runtimemodels.chazm.model.factory.EntityFactory
import runtimemodels.chazm.model.guice.OrganizationModule
import runtimemodels.chazm.model.id.*
import runtimemodels.chazm.model.relation.AchievesRelation
import runtimemodels.chazm.model.relation.NeedsRelation
import runtimemodels.chazm.model.relation.PossessesRelation
import runtimemodels.chazm.model.relation.RequiresRelation
import scaleInt

class DefaultGoodnessTest {

    private val injector = Guice.createInjector(OrganizationModule(), FunctionModule())
    private val provider = injector.getProvider(Organization::class.java)
    private val entityFactory = injector.getInstance(EntityFactory::class.java)
    private val goodness = injector.getInstance(Goodness::class.java)

    @Test
    fun `when the agent does not have the required attribute`() {
        val organization: Organization = mock()
        val agent: Agent = mock()
        val role: Role = mock()
        val goal: InstanceGoal = mock()
        val roleId: RoleId = mock()
        val needsRelations: NeedsManager = mock()
        val attributeId: AttributeId = mock()
        val needs: Needs = mock()
        val hasRelations: HasManager = mock()

        `when`(role.id).thenReturn(roleId)
        `when`(organization.needsRelations).thenReturn(needsRelations)
        `when`(needsRelations[roleId]).thenReturn(mapOf(attributeId to needs))
        `when`(organization.hasRelations).thenReturn(hasRelations)
        `when`(hasRelations[any(), any()]).thenReturn(null)

        val goodness = DefaultGoodness()

        assertThat(goodness.compute(organization, agent, role, goal, emptySet()).scaleInt()).isEqualTo(0)
    }

    @Test
    fun `when the role does not achieve the goal`() {
        val organization: Organization = mock()
        val agent: Agent = mock()
        val role: Role = mock()
        val goal: InstanceGoal = mock()
        val roleId: RoleId = mock()
        val needsRelations: NeedsManager = mock()
        val achievesRelations: AchievesManager = mock()
        val specificationGoal: SpecificationGoal = mock()

        `when`(role.id).thenReturn(roleId)
        `when`(organization.needsRelations).thenReturn(needsRelations)
        `when`(needsRelations[roleId]).thenReturn(emptyMap())
        `when`(organization.achievesRelations).thenReturn(achievesRelations)
        `when`(achievesRelations[roleId]).thenReturn(emptyMap())
        `when`(goal.goal).thenReturn(specificationGoal)

        val goodness = DefaultGoodness()

        assertThat(goodness.compute(organization, agent, role, goal, emptySet()).scaleInt()).isEqualTo(0)
    }

    @Test
    fun `when the role does not require any capability`() {
        val organization: Organization = mock()
        val agent: Agent = mock()
        val role: Role = mock()
        val goal: InstanceGoal = mock()
        val roleId: RoleId = mock()
        val needsRelations: NeedsManager = mock()
        val achievesRelations: AchievesManager = mock()
        val specificationGoal: SpecificationGoal = mock()
        val specificationGoalId: SpecificationGoalId = mock()
        val achieves: Achieves = mock()
        val requiresRelation: RequiresManager = mock()

        `when`(role.id).thenReturn(roleId)
        `when`(organization.needsRelations).thenReturn(needsRelations)
        `when`(needsRelations[roleId]).thenReturn(emptyMap())
        `when`(organization.achievesRelations).thenReturn(achievesRelations)
        `when`(achievesRelations[roleId]).thenReturn(mapOf(specificationGoalId to achieves))
        `when`(goal.goal).thenReturn(specificationGoal)
        `when`(specificationGoal.id).thenReturn(specificationGoalId)
        `when`(organization.requiresRelations).thenReturn(requiresRelation)
        `when`(requiresRelation[roleId]).thenReturn(emptyMap())

        val goodness = DefaultGoodness()

        assertThat(goodness.compute(organization, agent, role, goal, emptySet()).scaleInt()).isEqualTo(10)
    }

    @Test
    fun `test that the compute function works properly`() {
        val organization = provider.get()
        val agent = entityFactory.buildAgent(DefaultAgentId("a"), mapOf())
        val role = entityFactory.buildRole(DefaultRoleId("r"))
        val specificationGoal = entityFactory.buildSpecificationGoal(DefaultSpecificationGoalId("sg"))
        val instanceGoal = entityFactory.buildInstanceGoal(DefaultInstanceGoalId("ig"), specificationGoal, mapOf())
        val capability1 = entityFactory.buildCapability(DefaultCapabilityId("c1"))
        val capability2 = entityFactory.buildCapability(DefaultCapabilityId("c2"))
        val attribute = entityFactory.buildAttribute(DefaultAttributeId("t"), Attribute.Type.NEGATIVE_QUALITY)

        assertThat(goodness.compute(organization, agent, role, instanceGoal, setOf())).isEqualTo(DefaultGoodness.MIN_SCORE)

        organization.add(agent)
        organization.add(role)
        organization.add(specificationGoal)
        organization.add(instanceGoal)
        organization.add(capability1)
        organization.add(AchievesRelation(role, specificationGoal))

        assertThat(goodness.compute(organization, agent, role, instanceGoal, setOf())).isEqualTo(DefaultGoodness.MAX_SCORE)

        organization.add(RequiresRelation(role, capability1))

        assertThat(goodness.compute(organization, agent, role, instanceGoal, setOf())).isEqualTo(DefaultGoodness.MIN_SCORE)

        organization.add(PossessesRelation(agent, capability1, 0.0))

        assertThat(goodness.compute(organization, agent, role, instanceGoal, setOf())).isEqualTo(DefaultGoodness.MIN_SCORE)

        organization.possessesRelations[agent.id, capability1.id]!!.score = 1.0

        assertThat(goodness.compute(organization, agent, role, instanceGoal, setOf())).isEqualTo(DefaultGoodness.MAX_SCORE)

        organization.add(capability2)
        organization.add(RequiresRelation(role, capability2))
        organization.add(PossessesRelation(agent, capability2, 1.0))

        assertThat(goodness.compute(organization, agent, role, instanceGoal, setOf())).isEqualTo(DefaultGoodness.MAX_SCORE)

        organization.add(attribute)
        organization.add(NeedsRelation(role, attribute))

        assertThat(goodness.compute(organization, agent, role, instanceGoal, setOf())).isEqualTo(DefaultGoodness.MIN_SCORE)
    }
}
