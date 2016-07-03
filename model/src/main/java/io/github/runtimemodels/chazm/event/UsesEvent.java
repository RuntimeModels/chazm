package io.github.runtimemodels.chazm.event;

import com.google.inject.assistedinject.Assisted;
import io.github.runtimemodels.chazm.entity.Pmf;
import io.github.runtimemodels.chazm.entity.Role;
import io.github.runtimemodels.chazm.id.UniqueId;
import io.github.runtimemodels.chazm.relation.Uses;
import io.github.runtimemodels.message.M;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * The {@linkplain UsesEvent} class indicates that there is an update about an {@linkplain Uses} relation.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class UsesEvent extends AbstractEvent {

    private static final long serialVersionUID = 5013118334434822084L;
    private final UniqueId<Role> roleId;
    private final UniqueId<Pmf> pmfId;
    private transient Integer hashCode = null;
    private transient String toString = null;

    @Inject
    UsesEvent(@NotNull @Assisted final EventCategory category, @NotNull @Assisted final Uses uses) {
        super(category);
        roleId = uses.getRole().getId();
        pmfId = uses.getPmf().getId();
    }

    /**
     * Returns a {@linkplain UniqueId} that represents a {@linkplain Role}.
     *
     * @return a {@linkplain UniqueId}.
     */
    public UniqueId<Role> getRoleId() {
        return roleId;
    }

    /**
     * Returns a {@linkplain UniqueId} that represents a {@linkplain Pmf}.
     *
     * @return a {@linkplain UniqueId}.
     */
    public UniqueId<Pmf> getPmfId() {
        return pmfId;
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof UsesEvent) {
            final UsesEvent event = (UsesEvent) object;
            return super.equals(event) && getRoleId().equals(event.getRoleId()) && getPmfId().equals(event.getPmfId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (hashCode == null) {
            hashCode = Objects.hash(getCategory(), getRoleId(), getPmfId());
        }
        return hashCode;
    }

    @Override
    public String toString() {
        if (toString == null) {
            toString = M.EVENT_WITH_2_IDS.get(super.toString(), getRoleId(), getPmfId());
        }
        return toString;
    }

}
