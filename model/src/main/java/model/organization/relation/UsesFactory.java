package model.organization.relation;

import model.organization.entity.Pmf;
import model.organization.entity.Role;

/**
 * The {@linkplain UsesFactory} interface defines the API for constructing {@linkplain Uses} relations.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface UsesFactory {

	/**
	 * Constructs an {@linkplain Uses}.
	 *
	 * @param role
	 *            the {@linkplain Role} of the {@linkplain Uses}.
	 * @param pmf
	 *            the {@linkplain Pmf} of the {@linkplain Uses}.
	 * @return an {@linkplain Uses}.
	 */
	Uses buildUses(Role role, Pmf pmf);

}