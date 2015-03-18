package model.organization.function;

import java.util.Collection;

import javax.inject.Singleton;

import model.organization.Organization;
import model.organization.relation.Assignment;

@Singleton
class DefaultEffectiveness implements Effectiveness {

	@Override
	public double compute(final Organization organization, final Collection<Assignment> assignments) {
		return assignments.parallelStream()
				.mapToDouble(m -> organization.getGoodness(m.getRole().getId()).compute(organization, m, organization.getAssignments())).sum();
	}

}
