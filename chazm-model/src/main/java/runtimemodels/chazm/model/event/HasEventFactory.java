package runtimemodels.chazm.model.event;

import runtimemodels.chazm.api.relation.Has;

/**
 * The {@linkplain HasEventFactory} interface defines the API for constructing {@linkplain HasEvent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface HasEventFactory {

    /**
     * Constructs an {@linkplain HasEvent}.
     *
     * @param category the {@linkplain EventType}.
     * @param has      the {@linkplain Has}.
     * @return a {@linkplain HasEvent}.
     */
    HasEvent build(EventType category, Has has);

}
