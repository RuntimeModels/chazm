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


class DefaultEffectivenessTest {

    private val injector = Guice.createInjector(OrganizationModule(), FunctionModule())
    private val provider = injector.getProvider(Organization::class.java)
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
        val ar1 = relationFactory.buildAchieves(r, sg)

        assertThat(effectiveness.compute(o, o.assignments)).isEqualTo(0.0)

        o.add(a1)
        o.add(a2)
        o.add(r)
        o.add(sg)
        o.add(ig1)
        o.add(ig2)
        o.add(c1)
        o.add(c2)
        o.add(ar1)
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
