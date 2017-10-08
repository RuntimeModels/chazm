package io.github.runtimemodels.chazm.event;

import io.github.runtimemodels.chazm.entity.Pmf;

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
     * @param category the {@linkplain EventCategory}.
     * @param pmf      the {@linkplain Pmf}.
     * @return a {@linkplain PmfEvent}.
     */
    PmfEvent build(EventCategory category, Pmf pmf);

}
