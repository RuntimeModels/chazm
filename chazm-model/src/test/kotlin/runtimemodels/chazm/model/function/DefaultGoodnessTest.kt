package runtimemodels.chazm.model.function

import com.google.inject.Guice
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.function.Goodness
import runtimemodels.chazm.api.organization.Organization
import runtimemodels.chazm.model.factory.EntityFactory
import runtimemodels.chazm.model.guice.OrganizationModule
import runtimemodels.chazm.model.id.*
import runtimemodels.chazm.model.relation.AchievesRelation
import runtimemodels.chazm.model.relation.NeedsRelation

class DefaultGoodnessTest {

    private val injector = Guice.createInjector(OrganizationModule(), FunctionModule())
    private val provider = injector.getProvider(Organization::class.java)
    private val entityFactory = injector.getInstance(EntityFactory::class.java)
    private val goodness = injector.getInstance(Goodness::class.java)

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

        organization.addRequires(role.id, capability1.id)

        assertThat(goodness.compute(organization, agent, role, instanceGoal, setOf())).isEqualTo(DefaultGoodness.MIN_SCORE)

        organization.addPossesses(agent.id, capability1.id, 0.0)

        assertThat(goodness.compute(organization, agent, role, instanceGoal, setOf())).isEqualTo(DefaultGoodness.MIN_SCORE)

        organization.setPossessesScore(agent.id, capability1.id, 1.0)

        assertThat(goodness.compute(organization, agent, role, instanceGoal, setOf())).isEqualTo(DefaultGoodness.MAX_SCORE)

        organization.add(capability2)
        organization.addRequires(role.id, capability2.id)
        organization.addPossesses(agent.id, capability2.id, 1.0)

        assertThat(goodness.compute(organization, agent, role, instanceGoal, setOf())).isEqualTo(DefaultGoodness.MAX_SCORE)

        organization.add(attribute)
        organization.add(NeedsRelation(role, attribute))

        assertThat(goodness.compute(organization, agent, role, instanceGoal, setOf())).isEqualTo(DefaultGoodness.MIN_SCORE)
    }
}
