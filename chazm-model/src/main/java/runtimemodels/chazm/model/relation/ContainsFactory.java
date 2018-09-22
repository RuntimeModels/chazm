package runtimemodels.chazm.model.relation;

import runtimemodels.chazm.api.entity.Characteristic;
import runtimemodels.chazm.api.entity.Role;
import runtimemodels.chazm.api.relation.Contains;

/**
 * The {@linkplain ContainsFactory} interface defines the API for constructing {@linkplain Contains} relations.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface ContainsFactory {

    /**
     * Constructs a {@linkplain Contains}.
     *
     * @param role           the {@linkplain Role} of the {@linkplain Contains}.
     * @param characteristic the {@linkplain Characteristic} of the {@linkplain Contains}.
     * @param value          the <code>double</code> value of the {@linkplain Contains}.
     * @return a {@linkplain Contains}.
     */
    Contains buildContains(Role role, Characteristic characteristic, double value);

}
