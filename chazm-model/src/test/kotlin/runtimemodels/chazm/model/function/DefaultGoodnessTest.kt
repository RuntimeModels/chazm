package runtimemodels.chazm.model.function

import com.google.inject.Guice
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import runtimemodels.chazm.api.Organization
import runtimemodels.chazm.api.entity.Attribute
import runtimemodels.chazm.api.entity.InstanceGoal
import runtimemodels.chazm.api.function.Goodness
import runtimemodels.chazm.model.OrganizationModule
import runtimemodels.chazm.model.entity.EntityFactory
import runtimemodels.chazm.model.id.IdFactory
import runtimemodels.chazm.model.id.build

class DefaultGoodnessTest {

    private val injector = Guice.createInjector(OrganizationModule(), FunctionModule())
    private val provider = injector.getProvider(Organization::class.java)
    private val idFactory = injector.getInstance(IdFactory::class.java)
    private val entityFactory = injector.getInstance(EntityFactory::class.java)
    private val goodness = injector.getInstance(Goodness::class.java)

    @Test
    fun `test that the compute function works properly`() {
        val o = provider.get()
        val a = entityFactory.buildAgent(idFactory.build("a"), mapOf())
        val r = entityFactory.buildRole(idFactory.build("r"))
        val sg = entityFactory.buildSpecificationGoal(idFactory.build("sg"))
        val ig = entityFactory.buildInstanceGoal(idFactory.build("ig"), sg, object : InstanceGoal.Parameter {

        })
        val c1 = entityFactory.buildCapability(idFactory.build("c1"))
        val c2 = entityFactory.buildCapability(idFactory.build("c2"))
        val t = entityFactory.buildAttribute(idFactory.build("t"), Attribute.Type.NEGATIVE_QUALITY)

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
    fun `test that the compute function throws an IllegalArgumentException when called with all parameters as null`() {
        assertThrows<IllegalArgumentException> { goodness.compute(null, null, null, null, null) }
    }

    @Test
    fun `test that the compute function throws an IllegalArgumentException when called with a null agent, a null role, a null goal, and a null assignment`() {
        val o = provider.get()
        assertThrows<IllegalArgumentException> { goodness.compute(o, null, null, null, null) }
    }

    @Test
    fun `test that the compute function throws an IllegalArgumentException when called with a null role, a null goal, and a null assignment`() {
        val o = provider.get()
        val a = entityFactory.buildAgent(idFactory.build("a"), mapOf())

        assertThrows<IllegalArgumentException> { goodness.compute(o, a, null, null, null) }
    }

    @Test
    fun `test that the compute function throws an IllegalArgumentException when called with a null goal and a null assignment`() {
        val o = provider.get()
        val a = entityFactory.buildAgent(idFactory.build("a"), mapOf())
        val r = entityFactory.buildRole(idFactory.build("r"))

        assertThrows<IllegalArgumentException> { goodness.compute(o, a, r, null, null) }
    }

    @Test
    fun `test that the compute function throws an IllegalArgumentException when called with a null assignment`() {
        val o = provider.get()
        val a = entityFactory.buildAgent(idFactory.build("a"), mapOf())
        val r = entityFactory.buildRole(idFactory.build("r"))
        val sg = entityFactory.buildSpecificationGoal(idFactory.build("sg"))
        val ig = entityFactory.buildInstanceGoal(idFactory.build("ig"), sg, object : InstanceGoal.Parameter {

        })

        assertThrows<IllegalArgumentException> { goodness.compute(o, a, r, ig, null) }
    }

}
