package model.organization.function;

import java.util.Collection;

import javax.inject.Singleton;
import javax.validation.constraints.NotNull;

import model.organization.Organization;
import model.organization.relation.Assignment;

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
