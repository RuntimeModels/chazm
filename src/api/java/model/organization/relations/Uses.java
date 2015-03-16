package model.organization.relations;

import model.organization.entities.Pmf;
import model.organization.entities.Role;

/**
 * Represents the uses relation, where a {@link Role} uses a {@link Pmf}.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface Uses {
	/**
	 * Returns {@linkplain Role} of this {@linkplain Uses}.
	 *
	 * @return {@linkplain Role} of this {@linkplain Uses}.
	 */
	Role getRole();

	/**
	 * Returns the {@linkplain Pmf} of this {@linkplain Uses}.
	 *
	 * @return the {@linkplain Pmf} of this {@linkplain Uses}.
	 */
	Pmf getPmf();
}