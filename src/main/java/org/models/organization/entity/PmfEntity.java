/*
 * PerformanceFunctionImpl.java
 *
 * Created on May 18, 2010
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity;

import java.util.Collection;

import org.models.organization.M;
import org.models.organization.id.UniqueId;
import org.models.organization.relation.Assignment;

/**
 * The {@linkplain PmfEntity} class is an implementation of the {@link Pmf}.
 *
 * @author Christopher Zhong
 * @see Pmf
 * @since 6.0
 */
public class PmfEntity implements Pmf {
	/**
	 * The {@linkplain UniqueId} that represents this {@linkplain Pmf}.
	 */
	private final UniqueId<Pmf> id;

	/**
	 * Constructs a new instance of {@linkplain Pmf}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents this {@linkplain Pmf}.
	 */
	public PmfEntity(final UniqueId<Pmf> id) {
		if (id == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, "id"));
		}
		this.id = id;
	}

	@Override
	public UniqueId<Pmf> getId() {
		return id;
	}

	@Override
	public final Double pmf(final Agent agent, final Role role, final InstanceGoal goal, final Collection<Assignment> assignments) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Pmf) {
			final Pmf pmf = (Pmf) object;
			return getId().equals(pmf.getId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	@Override
	public String toString() {
		return getId().toString();
	}
}
