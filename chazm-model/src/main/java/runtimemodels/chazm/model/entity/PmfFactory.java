package runtimemodels.chazm.model.entity;

import runtimemodels.chazm.api.entity.Pmf;
import runtimemodels.chazm.api.id.UniqueId;

/**
 * The {@linkplain PmfFactory} interface defines the APIs for constructing {@linkplain Pmf}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface PmfFactory {

    /**
     * Constructs a {@linkplain Pmf}.
     *
     * @param id the {@linkplain UniqueId} that represents the {@linkplain Pmf}.
     * @return a {@linkplain Pmf}.
     */
    Pmf buildPmf(UniqueId<Pmf> id);

}
