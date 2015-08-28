package io.github.runtimemodels.chazm.relation;

import io.github.runtimemodels.chazm.entity.Attribute;
import io.github.runtimemodels.chazm.entity.Role;
import io.github.runtimemodels.chazm.relation.Needs;

/**
 * The {@linkplain NeedsFactory} interface defines the API for constructing {@linkplain Needs} relations.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface NeedsFactory {

	/**
	 * Constructs a {@linkplain Needs}.
	 *
	 * @param role
	 *            the {@linkplain Role} of the {@linkplain Needs}.
	 * @param attribute
	 *            the {@linkplain Attribute} of the {@linkplain Needs}.
	 * @return a {@linkplain Needs}.
	 */
	Needs buildNeeds(Role role, Attribute attribute);

}