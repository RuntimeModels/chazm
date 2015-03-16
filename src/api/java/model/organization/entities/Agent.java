/*
 * Agent.java
 *
 * Created on Jul 30, 2007
 *
 * See License.txt file the license agreement.
 */
package model.organization.entities;

import model.organization.Organization;
import model.organization.id.Identifiable;

/**
 * The {@linkplain Agent} interface defines the agent entity of an {@linkplain Organization}.
 *
 * @author Christopher Zhong
 * @since 3.4
 */
public interface Agent extends Identifiable<Agent> {
	/**
	 * The {@linkplain ContactInfo} defines the interface on how {@linkplain Agent}s can be contacted.
	 *
	 * @author Christopher Zhong
	 * @since 7.0.0
	 */
	interface ContactInfo {
	}

	/**
	 * Returns the {@linkplain ContactInfo} for this {@linkplain Agent}.
	 *
	 * @return the {@linkplain ContactInfo} of this {@linkplain Agent}.
	 */
	ContactInfo getContactInfo();

	/**
	 * Sets the {@linkplain ContactInfo} for this {@linkplain Agent}.
	 *
	 * @param contactInfo
	 *            the new {@linkplain ContactInfo} to set.
	 */
	void setContactInfo(ContactInfo contactInfo);
}