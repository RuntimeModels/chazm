package io.github.runtimemodels.chazm.entity;

import io.github.runtimemodels.chazm.id.UniqueId;

/**
 * The {@linkplain CapabilityFactory} interface defines the APIs for constructing {@linkplain Capability}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface CapabilityFactory {

	/**
	 * Constructs a {@linkplain Capability}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Capability}.
	 * @return a {@linkplain Capability}.
	 */
	Capability buildCapability(UniqueId<Capability> id);

}