package model.organization.function;

import static model.organization.validation.Checks.checkNotNull;

import java.util.Collection;

import javax.inject.Singleton;
import javax.validation.constraints.NotNull;

import model.organization.Organization;
import model.organization.relation.Assignment;

@Singleton
class DefaultEffectiveness implements Effectiveness {

	@Override
	public double compute(@NotNull final Organization organization, @NotNull final Collection<Assignment> assignments) {
		checkNotNull(organization, "organization");
		checkNotNull(assignments, "assignments");
		return assignments
				.parallelStream()
				.mapToDouble(
						m -> organization.getGoodness(m.getRole().getId()).compute(organization, m.getAgent(), m.getRole(), m.getGoal(),
								organization.getAssignments())).sum();
	}

}
