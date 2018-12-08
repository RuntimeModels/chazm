package runtimemodels.chazm.model.function

import runtimemodels.chazm.api.function.Effectiveness
import runtimemodels.chazm.api.organization.Organization
import runtimemodels.chazm.api.relation.Assignment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal open class DefaultEffectiveness @Inject constructor() : Effectiveness {
    override fun compute(organization: Organization, assignments: Set<Assignment>): Double {
        return assignments
            .sumByDouble {
                organization.getGoodness(it.role.id)?.compute(
                    organization,
                    it.agent,
                    it.role,
                    it.goal,
                    organization.assignmentRelations.assignments
                ) ?: 0.0
            }
    }
}
