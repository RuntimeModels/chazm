package runtimemodels.chazm.model.function

import runtimemodels.chazm.api.Organization
import runtimemodels.chazm.api.function.Effectiveness
import runtimemodels.chazm.api.relation.Assignment

import javax.inject.Singleton

@Singleton
internal open class DefaultEffectiveness : Effectiveness {

    override fun compute(organization: Organization, assignments: Collection<Assignment>): Double {
        return assignments
            .parallelStream()
            .mapToDouble { m ->
                organization.getGoodness(m.role.id).compute(organization, m.agent, m.role, m.goal,
                    organization.assignments)
            }.sum()
    }

}
