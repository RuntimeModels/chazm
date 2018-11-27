package runtimemodels.chazm.model.function

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import runtimemodels.chazm.api.Organization
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.function.Effectiveness
import runtimemodels.chazm.api.function.Goodness
import runtimemodels.chazm.api.relation.Assignment

class DefaultEffectivenessTest {
    @Test
    fun `test the compute value when there are no assignments`() {
        val organization: Organization = mock(Organization::class.java)
        val goodness: Goodness = mock(Goodness::class.java)

        `when`(organization.getGoodness(any())).thenReturn(goodness)
        `when`(organization.assignments).thenReturn(setOf())

        val effectiveness: Effectiveness = DefaultEffectiveness()

        assertThat(effectiveness.compute(organization, organization.assignments).scaleInt()).isEqualTo(0)
    }

    @Test
    fun `test the compute value when there is one assignment`() {
        val organization: Organization = mock(Organization::class.java)
        val goodness: Goodness = mock(Goodness::class.java)
        val assignment: Assignment = mock(Assignment::class.java)
        val role: Role = mock(Role::class.java)

        `when`(organization.getGoodness(any())).thenReturn(goodness)
        `when`(organization.assignments).thenReturn(setOf(assignment))
        `when`(assignment.role).thenReturn(role)
        `when`(goodness.compute(any(), any(), any(), any(), any())).thenReturn(0.0, 0.5, 1.0)

        val effectiveness: Effectiveness = DefaultEffectiveness()

        assertAll(
            { assertThat(effectiveness.compute(organization, organization.assignments).scaleInt()).isEqualTo(0) },
            { assertThat(effectiveness.compute(organization, organization.assignments).scaleInt()).isEqualTo(5) },
            { assertThat(effectiveness.compute(organization, organization.assignments).scaleInt()).isEqualTo(10) }
        )
    }

    @Test
    fun `test the compute value when there are two assignments`() {
        val organization: Organization = mock(Organization::class.java)
        val goodness: Goodness = mock(Goodness::class.java)
        val assignment1: Assignment = mock(Assignment::class.java)
        val assignment2: Assignment = mock(Assignment::class.java)
        val role: Role = mock(Role::class.java)

        `when`(organization.getGoodness(any())).thenReturn(goodness)
        `when`(organization.assignments).thenReturn(setOf(assignment1, assignment2))
        `when`(assignment1.role).thenReturn(role)
        `when`(assignment2.role).thenReturn(role)
        `when`(goodness.compute(any(), any(), any(), any(), any())).thenReturn(
            0.0, 0.0,
            1.0, 0.0,
            0.2, 0.3,
            1.0, 1.0
        )

        val effectiveness: Effectiveness = DefaultEffectiveness()

        assertAll(
            { assertThat(effectiveness.compute(organization, organization.assignments).scaleInt()).isEqualTo(0) },
            { assertThat(effectiveness.compute(organization, organization.assignments).scaleInt()).isEqualTo(10) },
            { assertThat(effectiveness.compute(organization, organization.assignments).scaleInt()).isEqualTo(5) },
            { assertThat(effectiveness.compute(organization, organization.assignments).scaleInt()).isEqualTo(20) }
        )
    }

    @Test
    fun `test the compute value when there are three assignments`() {
        val organization: Organization = mock(Organization::class.java)
        val goodness: Goodness = mock(Goodness::class.java)
        val assignment1: Assignment = mock(Assignment::class.java)
        val assignment2: Assignment = mock(Assignment::class.java)
        val assignment3: Assignment = mock(Assignment::class.java)
        val role: Role = mock(Role::class.java)

        `when`(organization.getGoodness(any())).thenReturn(goodness)
        `when`(organization.assignments).thenReturn(setOf(assignment1, assignment2, assignment3))
        `when`(assignment1.role).thenReturn(role)
        `when`(assignment2.role).thenReturn(role)
        `when`(assignment3.role).thenReturn(role)
        `when`(goodness.compute(any(), any(), any(), any(), any())).thenReturn(
            0.0, 0.0, 0.0,
            0.1, 0.2, 0.3,
            1.0, 1.0, 1.0
        )

        val effectiveness: Effectiveness = DefaultEffectiveness()

        assertAll(
            { assertThat(effectiveness.compute(organization, organization.assignments).scaleInt()).isEqualTo(0) },
            { assertThat(effectiveness.compute(organization, organization.assignments).scaleInt()).isEqualTo(6) },
            { assertThat(effectiveness.compute(organization, organization.assignments).scaleInt()).isEqualTo(30) }
        )
    }

    private fun Double.scaleInt() = (this * 10).toInt()
}
