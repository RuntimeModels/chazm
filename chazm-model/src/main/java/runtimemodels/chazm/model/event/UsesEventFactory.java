package runtimemodels.chazm.model.event;

import runtimemodels.chazm.api.relation.Uses;

/**
 * The {@linkplain UsesEventFactory} interface defines the API for constructing {@linkplain UsesEvent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface UsesEventFactory {

    /**
     * Constructs an {@linkplain UsesEvent}.
     *
     * @param category the {@linkplain EventType}.
     * @param uses     the {@linkplain Uses}.
     * @return a {@linkplain UsesEvent}.
     */
    UsesEvent build(EventType category, Uses uses);

}
