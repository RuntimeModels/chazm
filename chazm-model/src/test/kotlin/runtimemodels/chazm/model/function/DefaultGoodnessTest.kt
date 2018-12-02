package runtimemodels.chazm.model.function

import com.google.inject.Guice
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.function.Goodness
import runtimemodels.chazm.api.organization.Organization
import runtimemodels.chazm.model.entity.EntityFactory
import runtimemodels.chazm.model.id.*
import runtimemodels.chazm.model.organization.OrganizationModule
import runtimemodels.chazm.model.relation.AchievesRelation

class DefaultGoodnessTest {

    private val injector = Guice.createInjector(OrganizationModule(), FunctionModule())
    private val provider = injector.getProvider(Organization::class.java)
    private val entityFactory = injector.getInstance(EntityFactory::class.java)
    private val goodness = injector.getInstance(Goodness::class.java)

    @Test
    fun `test that the compute function works properly`() {
        val o = provider.get()
        val a = entityFactory.buildAgent(DefaultAgentId("a"), mapOf())
        val r = entityFactory.buildRole(DefaultRoleId("r"))
        val sg = entityFactory.buildSpecificationGoal(DefaultSpecificationGoalId("sg"))
        val ig = entityFactory.buildInstanceGoal(DefaultInstanceGoalId("ig"), sg, mapOf())
        val c1 = entityFactory.buildCapability(DefaultCapabilityId("c1"))
        val c2 = entityFactory.buildCapability(DefaultCapabilityId("c2"))
        val t = entityFactory.buildAttribute(DefaultAttributeId("t"), Attribute.Type.NEGATIVE_QUALITY)

        assertThat(goodness.compute(o, a, r, ig, setOf())).isEqualTo(DefaultGoodness.MIN_SCORE)

        o.add(a)
        o.add(r)
        o.add(sg)
        o.add(ig)
        o.add(c1)
        o.add(AchievesRelation(r, sg))

        assertThat(goodness.compute(o, a, r, ig, setOf())).isEqualTo(DefaultGoodness.MAX_SCORE)

        o.addRequires(r.id, c1.id)

        assertThat(goodness.compute(o, a, r, ig, setOf())).isEqualTo(DefaultGoodness.MIN_SCORE)

        o.addPossesses(a.id, c1.id, 0.0)

        assertThat(goodness.compute(o, a, r, ig, setOf())).isEqualTo(DefaultGoodness.MIN_SCORE)

        o.setPossessesScore(a.id, c1.id, 1.0)

        assertThat(goodness.compute(o, a, r, ig, setOf())).isEqualTo(DefaultGoodness.MAX_SCORE)

        o.add(c2)
        o.addRequires(r.id, c2.id)
        o.addPossesses(a.id, c2.id, 1.0)

        assertThat(goodness.compute(o, a, r, ig, setOf())).isEqualTo(DefaultGoodness.MAX_SCORE)

        o.add(t)
        o.addNeeds(r.id, t.id)

        assertThat(goodness.compute(o, a, r, ig, setOf())).isEqualTo(DefaultGoodness.MIN_SCORE)
    }
}
