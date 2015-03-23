package model.organization.event;

import model.organization.entity.Pmf;

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
	 * @param pmf
	 *            the {@linkplain Pmf}.
	 * @param category
	 *            the {@linkplain EventCategory}.
	 * @return a {@linkplain PmfEvent}.
	 */
	PmfEvent build(Pmf pmf, EventCategory category);

}