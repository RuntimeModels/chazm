package runtimemodels.chazm.model.event;

import runtimemodels.chazm.api.entity.Pmf;

/**
 * The {@linkplain PmfEventFactory} interface defines the API for constructing {@linkplain PmfEvent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface PmfEventFactory {

    /**
     * Constructs an {@linkplain PmfEvent}.
     *
     * @param category the {@linkplain EventType}.
     * @param pmf      the {@linkplain Pmf}.
     * @return a {@linkplain PmfEvent}.
     */
    PmfEvent build(EventType category, Pmf pmf);

}
