package runtimemodels.chazm.model.event;

import runtimemodels.chazm.api.relation.Needs;

/**
 * The {@linkplain NeedsEventFactory} interface defines the API for constructing {@linkplain NeedsEvent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface NeedsEventFactory {

    /**
     * Constructs an {@linkplain NeedsEvent}.
     *
     * @param category the {@linkplain EventType}.
     * @param needs    the {@linkplain Needs}.
     * @return a {@linkplain NeedsEvent}.
     */
    NeedsEvent build(EventType category, Needs needs);

}
