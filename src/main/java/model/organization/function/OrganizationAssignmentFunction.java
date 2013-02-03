/*
 * OrganizationAssignmentFunction.java
 * 
 * Created on Dec 24, 2006
 * 
 * See License.txt file the license agreement.
 */
package model.organization.function;

import java.util.Collection;

import model.organization.relation.Assignment;

/**
 * The <code>OrganizationAssignmentFunction</code> interface provides the
 * necessary functionality for computing the organization score.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.1 $, $Date: 2009/03/06 15:34:52 $
 * @since 3.2
 */
public interface OrganizationAssignmentFunction {

	/**
	 * Returns the organization score based on the current set of
	 * {@link Assignment}.
	 * 
	 * @return the organization score.
	 */
	double oaf();

	/**
	 * Returns the organization score of the given {@link Collection} of
	 * {@link Assignment}.
	 * <p>
	 * A default algorithm for computing the organization score is to sum up
	 * {@link Assignment} scores. Typically, the {@link #oaf(Collection)} method
	 * is application specific. Thus, it is highly recommended that organization
	 * designers provide their own algorithm for computing the organization
	 * score, as the default algorithm may not be suffice.
	 * 
	 * @param assignments
	 *            the {@link Collection} of {@link Assignment} to compute the
	 *            organization score.
	 * @return the organization score.
	 */
	double oaf(Collection<Assignment> assignments);

}