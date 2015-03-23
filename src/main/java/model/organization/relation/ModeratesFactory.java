package model.organization.relation;

import model.organization.entity.Attribute;
import model.organization.entity.Pmf;

/**
 * The {@linkplain ModeratesFactory} interface defines the API for constructing {@linkplain Moderates} relations.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface ModeratesFactory {

	/**
	 * Constructs a {@linkplain Moderates}.
	 *
	 * @param pmf
	 *            the {@linkplain Pmf} of the {@linkplain Moderates}.
	 * @param attribute
	 *            the {@linkplain Attribute} of the {@linkplain Moderates}.
	 * @return a {@linkplain Moderates}.
	 */
	Moderates buildModerates(Pmf pmf, Attribute attribute);

}