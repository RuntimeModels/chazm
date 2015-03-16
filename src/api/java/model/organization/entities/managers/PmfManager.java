package model.organization.entities.managers;

import java.util.Collection;
import java.util.Set;

import model.organization.entities.Pmf;
import model.organization.id.UniqueId;

/**
 * The {@linkplain PmfManager} interface defines the necessary APIs for managing {@linkplain Pmf}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface PmfManager {
	/**
	 * Adds a {@linkplain Pmf} to this {@linkplain PmfManager}.
	 *
	 * @param pmf
	 *            the {@linkplain Pmf} to add.
	 */
	void addPmf(Pmf pmf);

	/**
	 * Adds a set of {@linkplain Pmf}s to this {@linkplain PmfManager}.
	 *
	 * @param pmfs
	 *            the set of {@linkplain Pmf}s to add.
	 */
	void addPmfs(Collection<Pmf> pmfs);

	/**
	 * Returns a {@linkplain Pmf} from this {@linkplain PmfManager}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Pmf} to retrieve.
	 * @return the {@linkplain Pmf} if it exists, <code>null</code> otherwise.
	 */
	Pmf getPmf(UniqueId<Pmf> id);

	/**
	 * Returns a set of {@linkplain Pmf}s from this {@linkplain PmfManager}.
	 *
	 * @return the set of {@linkplain Pmf}s.
	 */
	Set<Pmf> getPmfs();

	/**
	 * Removes a {@linkplain Pmf} from this {@linkplain PmfManager}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Pmf} to remove.
	 */
	void removePmf(UniqueId<Pmf> id);

	/**
	 * Removes a set of {@linkplain Pmf}s from this {@linkplain PmfManager}.
	 *
	 * @param ids
	 *            the set of {@linkplain UniqueId}s that represents the {@linkplain Pmf}s to remove.
	 */
	void removePmfs(Collection<UniqueId<Pmf>> ids);

	/**
	 * Removes all {@linkplain Pmf}s from this {@linkplain PmfManager}.
	 */
	void removeAllPmfs();
}