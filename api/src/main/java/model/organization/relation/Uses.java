package model.organization.relation;

import model.organization.entity.Pmf;
import model.organization.entity.Role;

/**
 * Represents the uses relation, where a {@linkplain Role} uses a {@linkplain Pmf}.
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