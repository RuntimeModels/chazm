package runtimemodels.chazm.api.entity;

import runtimemodels.chazm.api.Organization;
import runtimemodels.chazm.api.id.Identifiable;

import java.util.Map;

/**
 * The {@linkplain Agent} interface defines the agent entity of an {@linkplain Organization}.
 *
 * @author Christopher Zhong
 * @since 3.4
 */
public interface Agent extends Identifiable<Agent> {
    /**
     * Returns a {@linkplain Map} with information for contacting this {@linkplain Agent}.
     *
     * @return a {@linkplain Map} with information for contacting this {@linkplain Agent}.
     */
    Map<Object, Object> getContactInfo();

    /**
     * Adds the given {@linkplain Object} {@code key} and {@linkplain Object} {@code value} as a contact information for this {@linkplain Agent}.
     *
     * @param key   an {@linkplain Object} representing the {@code key}.
     * @param value an {@linkplain Object} representing the {@code value}.
     */
    void addContactInfo(Object key, Object value);

    /**
     * Removes a contact information by the given {@linkplain Object} {@code key} from this {@linkplain Agent}.
     *
     * @param key   an {@linkplain Object} representing the {@code key}.
     */
    void removeContactInfo(Object key);
}
