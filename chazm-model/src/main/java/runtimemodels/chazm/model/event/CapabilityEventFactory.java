package runtimemodels.chazm.model.event;

import runtimemodels.chazm.api.entity.Capability;

/**
 * The {@linkplain CapabilityEventFactory} interface defines the API for constructing {@linkplain CapabilityEvent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface CapabilityEventFactory {

    /**
     * Constructs an {@linkplain CapabilityEvent}.
     *
     * @param category   the {@linkplain EventType}.
     * @param capability the {@linkplain Capability}.
     * @return a {@linkplain CapabilityEvent}.
     */
    CapabilityEvent build(EventType category, Capability capability);

}
