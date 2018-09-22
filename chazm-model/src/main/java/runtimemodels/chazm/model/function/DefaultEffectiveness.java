package runtimemodels.chazm.model.function;

import runtimemodels.chazm.api.Organization;
import runtimemodels.chazm.api.function.Effectiveness;
import runtimemodels.chazm.api.relation.Assignment;

import javax.inject.Singleton;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Singleton
class DefaultEffectiveness implements Effectiveness {

    @Override
    public double compute(@NotNull final Organization organization, @NotNull final Collection<Assignment> assignments) {
        return assignments
                .parallelStream()
                .mapToDouble(
                        m -> organization.getGoodness(m.getRole().getId()).compute(organization, m.getAgent(), m.getRole(), m.getGoal(),
                                organization.getAssignments())).sum();
    }

}
