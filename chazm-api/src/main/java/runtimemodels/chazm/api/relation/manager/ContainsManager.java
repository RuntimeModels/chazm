package runtimemodels.chazm.api.relation.manager;

import runtimemodels.chazm.api.entity.Characteristic;
import runtimemodels.chazm.api.entity.Role;
import runtimemodels.chazm.api.id.CharacteristicId;
import runtimemodels.chazm.api.id.RoleId;
import runtimemodels.chazm.api.id.UniqueId;
import runtimemodels.chazm.api.relation.Contains;

import java.util.Set;

/**
 * The {@linkplain ContainsManager} interface defines the APIs for managing {@linkplain Contains}.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface ContainsManager {
    /**
     * Creates a contains relation between a {@linkplain Role} and a {@linkplain Characteristic} in this {@linkplain ContainsManager}.
     *
     * @param roleId           the {@linkplain UniqueId} that represents the {@linkplain Role}.
     * @param characteristicId the {@linkplain UniqueId} that represents the {@linkplain Characteristic}.
     * @param value            the <code>double</code> value.
     */
    void addContains(RoleId roleId, CharacteristicId characteristicId, double value);

    /**
     * Returns a set of {@linkplain Characteristic}s that is contained by a {@linkplain Role} from this {@linkplain ContainsManager}.
     *
     * @param id the {@linkplain UniqueId} that represents the {@linkplain Role}.
     * @return a set of {@linkplain Characteristic}s.
     */
    Set<Characteristic> getContains(RoleId id);

    /**
     * Returns a set of {@linkplain Role}s that contains a {@linkplain Characteristic} from this {@linkplain ContainsManager}.
     *
     * @param id the {@linkplain UniqueId} that represents the {@linkplain Characteristic}.
     * @return a set of {@linkplain Role}s.
     */
    Set<Role> getContainedBy(CharacteristicId id);

    /**
     * Return a {@linkplain Double} value (if it exists) of the contains relation between a {@linkplain Role} and a {@linkplain Characteristic} from this
     * {@linkplain ContainsManager}.
     *
     * @param roleId           the {@linkplain UniqueId} that represents the {@linkplain Role}.
     * @param characteristicId the {@linkplain UniqueId} that represents the {@linkplain Characteristic}.
     * @return the {@linkplain Double} value if it exists, <code>null</code> otherwise.
     */
    Double getContainsValue(RoleId roleId, CharacteristicId characteristicId);

    /**
     * Sets a new <code>double</code> value of the contains relation between a {@linkplain Role} and a {@linkplain Characteristic} in this
     * {@linkplain ContainsManager}.
     *
     * @param roleId           the {@linkplain UniqueId} that represents the {@linkplain Role}.
     * @param characteristicId the {@linkplain UniqueId} that represents the {@linkplain Characteristic}.
     * @param value            the new <code>double</code> value.
     */
    void setContainsValue(RoleId roleId, CharacteristicId characteristicId, double value);

    /**
     * Removes a contains relation between a {@linkplain Role} and a {@linkplain Characteristic} from this {@linkplain ContainsManager}.
     *
     * @param roleId           the {@linkplain UniqueId} of the {@linkplain Role} for the {@linkplain Contains}.
     * @param characteristicId the {@linkplain UniqueId} of the {@linkplain Characteristic} for the {@linkplain Contains}.
     */
    void removeContains(RoleId roleId, CharacteristicId characteristicId);

    /**
     * Removes all contains relations from this {@linkplain ContainsManager}.
     */
    void removeAllContains();
}
