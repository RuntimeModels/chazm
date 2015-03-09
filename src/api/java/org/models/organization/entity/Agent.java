/*
 * Agent.java
 *
 * Created on Jul 30, 2007
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity;

import java.util.Set;

import org.models.organization.entity.basic.SimpleAgent;
import org.models.organization.identifier.UniqueId;

/**
 * The <code>Agent</code> interface defines the basic agent entity of the Organization Model by extending the {@link SimpleAgent} interface.
 *
 * @author Christopher Zhong
 * @since 3.4
 */
public interface Agent extends SimpleAgent {

	/**
	 * The {@linkplain ContactInfo} defines the interface on how {@linkplain Agent}s can be contacted.
	 *
	 * @author Christopher Zhong
	 * @since 7.0.0
	 */
	interface ContactInfo {
	}

	/**
	 * Adds the given <code>Capability</code> to the the set of <code>Capability</code> that this <code>Agent</code> possesses.
	 *
	 * @param capability
	 *            the <code>Capability</code> to be added.
	 * @param score
	 *            the score indicating how well this <code>Agent</code> can use the given <code>Capability</code>.
	 */
	void addPossesses(Capability capability, double score);

	/**
	 * Returns the <code>Capability</code> that this <code>Agent</code> possesses by the given <code>UniqueIdentifier</code>.
	 *
	 * @param capabilityIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Capability</code> to retrieve.
	 * @return the <code>Capability</code> if it exists, <code>null</code> otherwise.
	 */
	Capability getPossesses(UniqueId capabilityIdentifier);

	/**
	 * Returns the set of <code>Capability</code> that this <code>Agent</code> possesses.
	 *
	 * @return the set of <code>Capability</code>.
	 */
	Set<Capability> getPossessesSet();

	/**
	 * Returns the score of the given <code>Capability</code> that this <code>Agent</code> possesses.
	 *
	 * @param capabilityIdentifier
	 *            the score of the <code>Capability</code> to retrieve.
	 * @return the score if it exists, <code>0.0</code> otherwise.
	 */
	double getPossessesScore(UniqueId capabilityIdentifier);

	/**
	 * Changes the score for the given <code>Capability</code> to the given score.
	 *
	 * @param capabilityIdentifier
	 *            the <code>Capability</code> to change to the given score.
	 * @param score
	 *            the new score for the given <code>Capability</code>.
	 */
	void setPossessesScore(UniqueId capabilityIdentifier, double score);

	/**
	 * Removes the given <code>Capability</code> from the set of <code>Capability</code> that this <code>Agent</code> possesses.
	 *
	 * @param capabilityIdentifier
	 *            the <code>Capability</code> to be removed.
	 */
	void removePossesses(UniqueId capabilityIdentifier);

	/**
	 * Clears the set of <code>Capability</code> that this <code>Agent</code> possesses.
	 */
	void removeAllPossesses();

	/**
	 * Adds the given <code>Attribute</code> to the set of <code>Attribute</code> that this <code>Agent</code> has.
	 *
	 * @param attribute
	 *            the <code>Attribute</code> to be added.
	 * @param value
	 *            the <code>double</code> value of the <code>Attribute</code> of the <code>Agent</code>.
	 */
	void addHas(Attribute attribute, double value);

	/**
	 * Returns the set of <code>Attribute</code> that this <code>Agent</code> has.
	 *
	 * @return the set of <code>Attribute</code>.
	 */
	Set<Attribute> getHasSet();

	/**
	 * Returns the score of the given <code>Attribute</code> that this <code>Agent</code> has.
	 *
	 * @param attributeIdentifier
	 *            the score of the <code>Attribute</code> to retrieve.
	 * @return the score if it exists, <code>null</code> otherwise.
	 */
	Double getHasValue(UniqueId attributeIdentifier);

	/**
	 * Changes the score for the given <code>Attribute</code> to the given score.
	 *
	 * @param attributeIdentifier
	 *            the <code>Attribute</code> to change to the given score.
	 * @param value
	 *            the new score for the given <code>Attribute</code>.
	 */
	void setHasValue(UniqueId attributeIdentifier, double value);

	/**
	 * Removes the given <code>Attribute</code> from the set of <code>Attribute</code> that this <code>Agent</code> has.
	 *
	 * @param attributeIdentifier
	 *            the <code>Attribute</code> to be removed.
	 */
	void removeHas(UniqueId attributeIdentifier);

	/**
	 * Clears the set of <code>Attribute</code> that this <code>Agent</code> has.
	 */
	void removeAllHas();

	/**
	 * Returns the {@linkplain ContactInfo} for this <code>Agent</code>.
	 *
	 * @return the {@linkplain ContactInfo} of this <code>Agent</code>.
	 */
	ContactInfo getContactInfo();

	/**
	 * Sets the {@linkplain ContactInfo} for this <code>Agent</code>.
	 *
	 * @param contactInfo
	 *            the new {@linkplain ContactInfo} to set.
	 */
	void setContactInfo(ContactInfo contactInfo);

	/**
	 * Determines whether this <code>Agent</code> possesses the given <code>Capability</code> and returns the score indicating the ability of this
	 * <code>Agent</code> of the given <code>Capability</code>.
	 *
	 * @param capability
	 *            the <code>Capability</code> to be checked.
	 * @return <code>0.0</code> if the <code>Agent</code> does not possess the <code>Capability</code>, otherwise a score of (<code>0.0</code> &lt; score &le;
	 *         <code>1.0</code>).
	 */
	double possesses(Capability capability);

	/**
	 * Determines whether this <code>Agent</code> has the given <code>Attribute</code> and returns the value of the <code>Attribute</code> of this
	 * <code>Agent</code> if the <code>Agent</code> has that <code>Attribute</code>.
	 *
	 * @param attribute
	 *            the <code>Attribute</code> to be checked.
	 * @return the value of the <code>Attribute</code> of this <code>Agent</code> if the <code>Agent</code> has that <code>Attribute</code>, <code>null</code>
	 *         otherwise.
	 */
	Double has(Attribute attribute);

}