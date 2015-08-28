package io.github.runtimemodels.chazm.relation;

import io.github.runtimemodels.chazm.entity.Agent;
import io.github.runtimemodels.chazm.entity.Attribute;

/**
 * The {@linkplain HasFactory} interface defines the API for constructing {@linkplain Has} relations.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface HasFactory {

	/**
	 * Constructs a {@linkplain Has}.
	 *
	 * @param agent
	 *            the {@linkplain Agent} of the {@linkplain Has}.
	 * @param attribute
	 *            the {@linkplain Attribute} of the {@linkplain Has}.
	 * @param value
	 *            the <code>double</code> value of the {@linkplain Has}.
	 * @return a {@linkplain Has}.
	 */
	Has buildHas(Agent agent, Attribute attribute, double value);

}