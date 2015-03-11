package org.models.organization.relation;

import org.models.organization.Organization;
import org.models.organization.entity.Attribute;
import org.models.organization.entity.Pmf;

/**
 * The {@linkplain Moderates} interface defines the moderates relation, where a {@linkplain Pmf} moderates an {@linkplain Attribute}, of an
 * {@linkplain Organization}.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface Moderates {
	/**
	 * Returns the {@linkplain Pmf} of this {@linkplain Moderates}.
	 *
	 * @return the {@linkplain Pmf} of this {@linkplain Moderates}.
	 */
	Pmf getPmf();

	/**
	 * Returns the {@linkplain Attribute} of this {@linkplain Moderates}.
	 *
	 * @return the {@linkplain Attribute} of this {@linkplain Moderates}.
	 */
	Attribute getAttribute();
}