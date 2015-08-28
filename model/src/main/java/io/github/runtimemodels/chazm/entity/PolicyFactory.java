package io.github.runtimemodels.chazm.entity;

import io.github.runtimemodels.chazm.id.UniqueId;

/**
 * The {@linkplain PolicyFactory} interface defines the APIs for constructing {@linkplain Policy}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface PolicyFactory {

	/**
	 * Constructs a {@linkplain Policy}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Policy}.
	 * @return a {@linkplain Agent}.
	 */
	Policy buildPolicy(UniqueId<Policy> id);

}