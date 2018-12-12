package runtimemodels.chazm.model.function

import any
import mock
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.mockito.Mockito.`when`
import runtimemodels.chazm.api.entity.Role
import runtimemodels.chazm.api.function.Effectiveness
import runtimemodels.chazm.api.function.Goodness
import runtimemodels.chazm.api.organization.Organization
import runtimemodels.chazm.api.relation.Assignment
import runtimemodels.chazm.api.relation.AssignmentManager
import scaleInt

internal class DefaultEffectivenessTest {
    @Test
    fun `test the compute value when there are no assignments`() {
        val organization: Organization = mock()

        val effectiveness: Effectiveness = DefaultEffectiveness()

        assertThat(effectiveness.compute(organization, setOf()).scaleInt()).isEqualTo(0)
    }

    @Test
    fun `test the compute value when there is one assignment`() {
        val organization: Organization = mock()
        val assignmentManager: AssignmentManager = mock()
        val goodness: Goodness = mock()
        val assignment: Assignment = mock()
        val role: Role = mock()

        `when`(organization.getGoodness(any())).thenReturn(goodness)
        `when`(organization.assignmentRelations).thenReturn(assignmentManager)
        `when`(assignment.role).thenReturn(role)
        `when`(goodness.compute(any(), any(), any(), any(), any())).thenReturn(0.0, 0.5, 1.0)

        val effectiveness: Effectiveness = DefaultEffectiveness()

        assertAll(
            { assertThat(effectiveness.compute(organization, setOf(assignment)).scaleInt()).isEqualTo(0) },
            { assertThat(effectiveness.compute(organization, setOf(assignment)).scaleInt()).isEqualTo(5) },
            { assertThat(effectiveness.compute(organization, setOf(assignment)).scaleInt()).isEqualTo(10) }
        )
    }

    @Test
    fun `test the compute value when there are two assignments`() {
        val organization: Organization = mock()
        val assignmentManager: AssignmentManager = mock()
        val goodness: Goodness = mock()
        val assignment1: Assignment = mock()
        val assignment2: Assignment = mock()
        val role: Role = mock()

        `when`(organization.getGoodness(any())).thenReturn(goodness)
        `when`(organization.assignmentRelations).thenReturn(assignmentManager)
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
            { assertThat(effectiveness.compute(organization, setOf(assignment1, assignment2)).scaleInt()).isEqualTo(0) },
            { assertThat(effectiveness.compute(organization, setOf(assignment1, assignment2)).scaleInt()).isEqualTo(10) },
            { assertThat(effectiveness.compute(organization, setOf(assignment1, assignment2)).scaleInt()).isEqualTo(5) },
            { assertThat(effectiveness.compute(organization, setOf(assignment1, assignment2)).scaleInt()).isEqualTo(20) }
        )
    }

    @Test
    fun `test the compute value when there are three assignments`() {
        val organization: Organization = mock()
        val assignmentManager: AssignmentManager = mock()
        val goodness: Goodness = mock()
        val assignment1: Assignment = mock()
        val assignment2: Assignment = mock()
        val assignment3: Assignment = mock()
        val role: Role = mock()

        `when`(organization.getGoodness(any())).thenReturn(goodness)
        `when`(organization.assignmentRelations).thenReturn(assignmentManager)
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
            { assertThat(effectiveness.compute(organization, setOf(assignment1, assignment2, assignment3)).scaleInt()).isEqualTo(0) },
            { assertThat(effectiveness.compute(organization, setOf(assignment1, assignment2, assignment3)).scaleInt()).isEqualTo(6) },
            { assertThat(effectiveness.compute(organization, setOf(assignment1, assignment2, assignment3)).scaleInt()).isEqualTo(30) }
        )
    }
}
