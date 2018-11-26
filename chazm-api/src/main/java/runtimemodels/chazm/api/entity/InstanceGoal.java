package runtimemodels.chazm.api.entity;

import runtimemodels.chazm.api.Organization;
import runtimemodels.chazm.api.id.Identifiable;

import java.util.Map;

/**
 * The {@linkplain InstanceGoal} interface defines the instance goal, which is a concrete instantiation of a {@linkplain SpecificationGoal}, of an
 * {@linkplain Organization}.
 *
 * @author Christopher Zhong
 * @since 3.4
 */
public interface InstanceGoal extends Identifiable<InstanceGoal> {
    /**
     * Returns the {@linkplain SpecificationGoal} that instantiated this {@linkplain InstanceGoal}.
     *
     * @return the {@linkplain SpecificationGoal} that instantiated this {@linkplain InstanceGoal}.
     */
    SpecificationGoal getGoal();

    /**
     * Returns a {@linkplain Map} that represents the parameters of this {@linkplain InstanceGoal}.
     *
     * @return a {@linkplain Map} that represents the parameters of this {@linkplain InstanceGoal}.
     */
    Map<Object, Object> getParameters();

    /**
     * Adds the given {@linkplain Object} {@code key} and {@linkplain Object} {@code value} as a parameter to this {@linkplain InstanceGoal}.
     *
     * @param key   an {@linkplain Object} representing the {@code key}.
     * @param value an {@linkplain Object} representing the {@code value}.
     */
    void addParameter(Object key, Object value);

    /**
     * Removes a parameter by the given {@linkplain Object} {@code key} from this {@linkplain InstanceGoal}.
     *
     * @param key an {@linkplain Object} representing the {@code key}.
     */
    void removeParameter(Object key);
}
