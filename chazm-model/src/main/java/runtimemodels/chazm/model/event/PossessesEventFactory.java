package runtimemodels.chazm.model.event;

import runtimemodels.chazm.api.relation.Possesses;

/**
 * The {@linkplain PossessesEventFactory} interface defines the API for constructing {@linkplain PossessesEvent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface PossessesEventFactory {

    /**
     * Constructs an {@linkplain PossessesEvent}.
     *
     * @param category  the {@linkplain EventType}.
     * @param possesses the {@linkplain Possesses}.
     * @return a {@linkplain PossessesEvent}.
     */
    PossessesEvent build(EventType category, Possesses possesses);

}
