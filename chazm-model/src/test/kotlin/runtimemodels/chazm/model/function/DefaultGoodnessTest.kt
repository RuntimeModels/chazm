package runtimemodels.chazm.model.function

import any
import mock
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import runtimemodels.chazm.api.entity.*
import runtimemodels.chazm.api.organization.Organization
import runtimemodels.chazm.api.relation.*
import scaleInt

internal class DefaultGoodnessTest {
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
    fun `when the agent does not have the required any capability`() {
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
        val capabilityId: CapabilityId = mock()
        val requires: Requires = mock()
        val possessesRelations: PossessesManager = mock()

        `when`(role.id).thenReturn(roleId)
        `when`(organization.needsRelations).thenReturn(needsRelations)
        `when`(needsRelations[roleId]).thenReturn(emptyMap())
        `when`(organization.achievesRelations).thenReturn(achievesRelations)
        `when`(achievesRelations[roleId]).thenReturn(mapOf(specificationGoalId to achieves))
        `when`(goal.goal).thenReturn(specificationGoal)
        `when`(specificationGoal.id).thenReturn(specificationGoalId)
        `when`(organization.requiresRelations).thenReturn(requiresRelation)
        `when`(requiresRelation[roleId]).thenReturn(mapOf(capabilityId to requires))
        `when`(organization.possessesRelations).thenReturn(possessesRelations)
        `when`(possessesRelations[any(), any()]).thenReturn(null)

        val goodness = DefaultGoodness()

        assertThat(goodness.compute(organization, agent, role, goal, emptySet()).scaleInt()).isEqualTo(0)
    }

    @Test
    fun `when the required capability score is zero`() {
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
        val capabilityId: CapabilityId = mock()
        val requires: Requires = mock()
        val possessesRelations: PossessesManager = mock()
        val possesses: Possesses = mock()

        `when`(role.id).thenReturn(roleId)
        `when`(organization.needsRelations).thenReturn(needsRelations)
        `when`(needsRelations[roleId]).thenReturn(emptyMap())
        `when`(organization.achievesRelations).thenReturn(achievesRelations)
        `when`(achievesRelations[roleId]).thenReturn(mapOf(specificationGoalId to achieves))
        `when`(goal.goal).thenReturn(specificationGoal)
        `when`(specificationGoal.id).thenReturn(specificationGoalId)
        `when`(organization.requiresRelations).thenReturn(requiresRelation)
        `when`(requiresRelation[roleId]).thenReturn(mapOf(capabilityId to requires))
        `when`(organization.possessesRelations).thenReturn(possessesRelations)
        `when`(possessesRelations[any(), any()]).thenReturn(possesses)
        `when`(possesses.score).thenReturn(0.0)

        val goodness = DefaultGoodness()

        assertThat(goodness.compute(organization, agent, role, goal, emptySet()).scaleInt()).isEqualTo(0)
    }

    @Test
    fun `when the required capability score is one`() {
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
        val capabilityId: CapabilityId = mock()
        val requires: Requires = mock()
        val possessesRelations: PossessesManager = mock()
        val possesses: Possesses = mock()

        `when`(role.id).thenReturn(roleId)
        `when`(organization.needsRelations).thenReturn(needsRelations)
        `when`(needsRelations[roleId]).thenReturn(emptyMap())
        `when`(organization.achievesRelations).thenReturn(achievesRelations)
        `when`(achievesRelations[roleId]).thenReturn(mapOf(specificationGoalId to achieves))
        `when`(goal.goal).thenReturn(specificationGoal)
        `when`(specificationGoal.id).thenReturn(specificationGoalId)
        `when`(organization.requiresRelations).thenReturn(requiresRelation)
        `when`(requiresRelation[roleId]).thenReturn(mapOf(capabilityId to requires))
        `when`(organization.possessesRelations).thenReturn(possessesRelations)
        `when`(possessesRelations[any(), any()]).thenReturn(possesses)
        `when`(possesses.score).thenReturn(1.0)

        val goodness = DefaultGoodness()

        assertThat(goodness.compute(organization, agent, role, goal, emptySet()).scaleInt()).isEqualTo(10)
    }

    @Test
    fun `when there are two required capabilities with scores of one and zero`() {
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
        val capabilityId1: CapabilityId = mock()
        val capabilityId2: CapabilityId = mock()
        val requires: Requires = mock()
        val possessesRelations: PossessesManager = mock()
        val possesses: Possesses = mock()

        `when`(role.id).thenReturn(roleId)
        `when`(organization.needsRelations).thenReturn(needsRelations)
        `when`(needsRelations[roleId]).thenReturn(emptyMap())
        `when`(organization.achievesRelations).thenReturn(achievesRelations)
        `when`(achievesRelations[roleId]).thenReturn(mapOf(specificationGoalId to achieves))
        `when`(goal.goal).thenReturn(specificationGoal)
        `when`(specificationGoal.id).thenReturn(specificationGoalId)
        `when`(organization.requiresRelations).thenReturn(requiresRelation)
        `when`(requiresRelation[roleId]).thenReturn(mapOf(capabilityId1 to requires, capabilityId2 to requires))
        `when`(organization.possessesRelations).thenReturn(possessesRelations)
        `when`(possessesRelations[any(), any()]).thenReturn(possesses)
        `when`(possesses.score).thenReturn(1.0, 0.0)

        val goodness = DefaultGoodness()

        assertThat(goodness.compute(organization, agent, role, goal, emptySet()).scaleInt()).isEqualTo(0)
    }

    @Test
    fun `when there are two required capabilities with scores of one and one`() {
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
        val capabilityId1: CapabilityId = mock()
        val capabilityId2: CapabilityId = mock()
        val requires: Requires = mock()
        val possessesRelations: PossessesManager = mock()
        val possesses: Possesses = mock()

        `when`(role.id).thenReturn(roleId)
        `when`(organization.needsRelations).thenReturn(needsRelations)
        `when`(needsRelations[roleId]).thenReturn(emptyMap())
        `when`(organization.achievesRelations).thenReturn(achievesRelations)
        `when`(achievesRelations[roleId]).thenReturn(mapOf(specificationGoalId to achieves))
        `when`(goal.goal).thenReturn(specificationGoal)
        `when`(specificationGoal.id).thenReturn(specificationGoalId)
        `when`(organization.requiresRelations).thenReturn(requiresRelation)
        `when`(requiresRelation[roleId]).thenReturn(mapOf(capabilityId1 to requires, capabilityId2 to requires))
        `when`(organization.possessesRelations).thenReturn(possessesRelations)
        `when`(possessesRelations[any(), any()]).thenReturn(possesses)
        `when`(possesses.score).thenReturn(1.0, 1.0)

        val goodness = DefaultGoodness()

        assertThat(goodness.compute(organization, agent, role, goal, emptySet()).scaleInt()).isEqualTo(10)
    }
}
