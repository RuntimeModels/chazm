package runtimemodels.chazm.model.function

import com.google.inject.Guice
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import runtimemodels.chazm.api.function.Effectiveness
import runtimemodels.chazm.api.organization.Organization
import runtimemodels.chazm.model.entity.EntityFactory
import runtimemodels.chazm.model.id.*
import runtimemodels.chazm.model.organizations.OrganizationModule
import runtimemodels.chazm.model.relation.RelationFactory


class DefaultEffectivenessTest {

    private val injector = Guice.createInjector(OrganizationModule(), FunctionModule())
    private val provider = injector.getProvider(Organization::class.java)
    private val idFactory = injector.getInstance(IdFactory::class.java)
    private val entityFactory = injector.getInstance(EntityFactory::class.java)
    private val relationFactory = injector.getInstance(RelationFactory::class.java)
    private val effectiveness = injector.getInstance(Effectiveness::class.java)

    @Test
    fun `test that compute works correctly`() {
        val o = provider.get()
        val a1 = entityFactory.buildAgent(DefaultAgentId("a1"), mapOf())
        val a2 = entityFactory.buildAgent(DefaultAgentId("a2"), mapOf())
        val r = entityFactory.buildRole(DefaultRoleId("r"))
        val sg = entityFactory.buildSpecificationGoal(DefaultSpecificationGoalId("sg"))
        val ig1 = entityFactory.buildInstanceGoal(DefaultInstanceGoalId("ig1"), sg, mapOf())
        val ig2 = entityFactory.buildInstanceGoal(DefaultInstanceGoalId("ig2"), sg, mapOf())
        val c1 = entityFactory.buildCapability(DefaultCapabilityId("c1"))
        val c2 = entityFactory.buildCapability(DefaultCapabilityId("c2"))
        val as1 = relationFactory.buildAssignment(a1, r, ig1)
        val as2 = relationFactory.buildAssignment(a1, r, ig2)
        val as3 = relationFactory.buildAssignment(a2, r, ig1)
        val as4 = relationFactory.buildAssignment(a2, r, ig2)

        assertThat(effectiveness.compute(o, o.assignments)).isEqualTo(0.0)

        o.addAgent(a1)
        o.addAgent(a2)
        o.addRole(r)
        o.addSpecificationGoal(sg)
        o.addInstanceGoal(ig1)
        o.addInstanceGoal(ig2)
        o.addCapability(c1)
        o.addCapability(c2)
        o.addAchieves(r.id, sg.id)
        o.addRequires(r.id, c1.id)
        o.addPossesses(a1.id, c1.id, 1.0)
        o.addAssignment(as1)

        assertThat(effectiveness.compute(o, o.assignments)).isEqualTo(1.0)

        o.addAssignment(as2)

        assertThat(effectiveness.compute(o, o.assignments)).isEqualTo(2.0)

        o.addAssignment(as3)

        assertThat(effectiveness.compute(o, o.assignments)).isEqualTo(2.0)

        o.addAssignment(as4)

        assertThat(effectiveness.compute(o, o.assignments)).isEqualTo(2.0)
    }
}
