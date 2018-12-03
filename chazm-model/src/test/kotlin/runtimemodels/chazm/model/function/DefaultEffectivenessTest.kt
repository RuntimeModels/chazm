package runtimemodels.chazm.model.function

import com.google.inject.Guice
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import runtimemodels.chazm.api.function.Effectiveness
import runtimemodels.chazm.api.organization.Organization
import runtimemodels.chazm.model.factory.EntityFactory
import runtimemodels.chazm.model.factory.RelationFactory
import runtimemodels.chazm.model.guice.OrganizationModule
import runtimemodels.chazm.model.id.*
import runtimemodels.chazm.model.relation.PossessesRelation
import runtimemodels.chazm.model.relation.RequiresRelation


class DefaultEffectivenessTest {

    private val injector = Guice.createInjector(OrganizationModule(), FunctionModule())
    private val provider = injector.getProvider(Organization::class.java)
    private val entityFactory = injector.getInstance(EntityFactory::class.java)
    private val relationFactory = injector.getInstance(RelationFactory::class.java)
    private val effectiveness = injector.getInstance(Effectiveness::class.java)

    @Test
    fun `test that compute works correctly`() {
        val organization = provider.get()
        val agent1 = entityFactory.buildAgent(DefaultAgentId("a1"), mapOf())
        val agent2 = entityFactory.buildAgent(DefaultAgentId("a2"), mapOf())
        val role = entityFactory.buildRole(DefaultRoleId("r"))
        val specificationGoal = entityFactory.buildSpecificationGoal(DefaultSpecificationGoalId("sg"))
        val instanceGoal1 = entityFactory.buildInstanceGoal(DefaultInstanceGoalId("ig1"), specificationGoal, mapOf())
        val instanceGoal2 = entityFactory.buildInstanceGoal(DefaultInstanceGoalId("ig2"), specificationGoal, mapOf())
        val capability1 = entityFactory.buildCapability(DefaultCapabilityId("c1"))
        val capability2 = entityFactory.buildCapability(DefaultCapabilityId("c2"))
        val assignment1 = relationFactory.buildAssignment(agent1, role, instanceGoal1)
        val assignment2 = relationFactory.buildAssignment(agent1, role, instanceGoal2)
        val assignment3 = relationFactory.buildAssignment(agent2, role, instanceGoal1)
        val assignment4 = relationFactory.buildAssignment(agent2, role, instanceGoal2)
        val achieves = relationFactory.buildAchieves(role, specificationGoal)

        assertThat(effectiveness.compute(organization, organization.assignments)).isEqualTo(0.0)

        organization.add(agent1)
        organization.add(agent2)
        organization.add(role)
        organization.add(specificationGoal)
        organization.add(instanceGoal1)
        organization.add(instanceGoal2)
        organization.add(capability1)
        organization.add(capability2)
        organization.add(achieves)
        organization.add(RequiresRelation(role, capability1))
        organization.add(PossessesRelation(agent1, capability1, 1.0))
        organization.addAssignment(assignment1)

        assertThat(effectiveness.compute(organization, organization.assignments)).isEqualTo(1.0)

        organization.addAssignment(assignment2)

        assertThat(effectiveness.compute(organization, organization.assignments)).isEqualTo(2.0)

        organization.addAssignment(assignment3)

        assertThat(effectiveness.compute(organization, organization.assignments)).isEqualTo(2.0)

        organization.addAssignment(assignment4)

        assertThat(effectiveness.compute(organization, organization.assignments)).isEqualTo(2.0)
    }
}
