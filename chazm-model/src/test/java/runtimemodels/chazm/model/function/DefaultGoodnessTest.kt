package runtimemodels.chazm.model.function

import com.google.inject.Guice
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import runtimemodels.chazm.api.Organization
import runtimemodels.chazm.api.entity.*
import runtimemodels.chazm.api.function.Goodness
import runtimemodels.chazm.model.OrganizationModule
import runtimemodels.chazm.model.entity.EntityFactory
import runtimemodels.chazm.model.id.IdFactory

class DefaultGoodnessTest {

    private val injector = Guice.createInjector(OrganizationModule(), FunctionModule())
    private val provider = injector.getProvider(Organization::class.java)
    private val idFactory = injector.getInstance(IdFactory::class.java)
    private val entityFactory = injector.getInstance(EntityFactory::class.java)
    private val goodness = injector.getInstance(Goodness::class.java)

    @Test
    fun testCompute() {
        val o = provider.get()
        val a = entityFactory.buildAgent(idFactory.build(Agent::class.java, "a"), object : Agent.ContactInfo {

        })
        val r = entityFactory.buildRole(idFactory.build(Role::class.java, "r"))
        val sg = entityFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal::class.java, "sg"))
        val ig = entityFactory.buildInstanceGoal(idFactory.build(InstanceGoal::class.java, "ig"), sg, object : InstanceGoal.Parameter {

        })
        val c1 = entityFactory.buildCapability(idFactory.build(Capability::class.java, "c1"))
        val c2 = entityFactory.buildCapability(idFactory.build(Capability::class.java, "c2"))
        val t = entityFactory.buildAttribute(idFactory.build(Attribute::class.java, "t"), Attribute.Type.NEGATIVE_QUALITY)

        assertThat(goodness.compute(o, a, r, ig, setOf())).isEqualTo(DefaultGoodness.MIN_SCORE)

        o.addAgent(a)
        o.addRole(r)
        o.addSpecificationGoal(sg)
        o.addInstanceGoal(ig)
        o.addCapability(c1)
        o.addAchieves(r.id, sg.id)

        assertThat(goodness.compute(o, a, r, ig, setOf())).isEqualTo(DefaultGoodness.MAX_SCORE)

        o.addRequires(r.id, c1.id)

        assertThat(goodness.compute(o, a, r, ig, setOf())).isEqualTo(DefaultGoodness.MIN_SCORE)

        o.addPossesses(a.id, c1.id, 0.0)

        assertThat(goodness.compute(o, a, r, ig, setOf())).isEqualTo(DefaultGoodness.MIN_SCORE)

        o.setPossessesScore(a.id, c1.id, 1.0)

        assertThat(goodness.compute(o, a, r, ig, setOf())).isEqualTo(DefaultGoodness.MAX_SCORE)

        o.addCapability(c2)
        o.addRequires(r.id, c2.id)
        o.addPossesses(a.id, c2.id, 1.0)

        assertThat(goodness.compute(o, a, r, ig, setOf())).isEqualTo(DefaultGoodness.MAX_SCORE)

        o.addAttribute(t)
        o.addNeeds(r.id, t.id)

        assertThat(goodness.compute(o, a, r, ig, setOf())).isEqualTo(DefaultGoodness.MIN_SCORE)
    }

    @Test
    fun testCompute1() {
        assertThrows(IllegalArgumentException::class.java) { goodness.compute(null, null, null, null, null) }
    }

    @Test
    fun testCompute2() {
        val o = provider.get()
        assertThrows(IllegalArgumentException::class.java) { goodness.compute(o, null, null, null, null) }
    }

    @Test
    fun testCompute3() {
        val o = provider.get()
        val a = entityFactory.buildAgent(idFactory.build(Agent::class.java, "a"), object : Agent.ContactInfo {

        })

        assertThrows(IllegalArgumentException::class.java) { goodness.compute(o, a, null, null, null) }
    }

    @Test
    fun testCompute4() {
        val o = provider.get()
        val a = entityFactory.buildAgent(idFactory.build(Agent::class.java, "a"), object : Agent.ContactInfo {

        })
        val r = entityFactory.buildRole(idFactory.build(Role::class.java, "r"))

        assertThrows(IllegalArgumentException::class.java) { goodness.compute(o, a, r, null, null) }
    }

    @Test
    fun testCompute5() {
        val o = provider.get()
        val a = entityFactory.buildAgent(idFactory.build(Agent::class.java, "a"), object : Agent.ContactInfo {

        })
        val r = entityFactory.buildRole(idFactory.build(Role::class.java, "r"))
        val sg = entityFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal::class.java, "sg"))
        val ig = entityFactory.buildInstanceGoal(idFactory.build(InstanceGoal::class.java, "ig"), sg, object : InstanceGoal.Parameter {

        })

        assertThrows(IllegalArgumentException::class.java) { goodness.compute(o, a, r, ig, null) }
    }

}
