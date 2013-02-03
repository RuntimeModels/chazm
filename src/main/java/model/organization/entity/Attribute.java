/*
 * Attribute.java
 *
 * Created on Oct 15, 2009
 *
 * See License.txt file the license agreement.
 */
package model.organization.entity;

import java.util.Set;

import model.organization.entity.basic.SimpleAttribute;
import model.organization.identifier.UniqueIdentifier;

/**
 * The <code>Attribute</code> interface defines the basic attribute entity of
 * the Organization Model by extending the {@link SimpleAttribute} interface.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.1.4.3 $, $Date: 2010/05/25 22:18:33 $
 * @see SimpleAttribute
 * @since 5.0
 */
public interface Attribute extends SimpleAttribute {

	/**
	 * The <code>Type</code> enumeration lists all the possible types that an
	 * <code>Attribute</code> can be.
	 * 
	 * @author Christopher Zhong
	 * @version $Revision: 1.1.4.3 $, $Date: 2010/05/25 22:18:33 $
	 * @since 5.0
	 */
	enum Type {
		/**
		 * Indicates a quantity-type <code>Attribute</code> (whose range is from
		 * <code>0.0</code> to <code>&#8734;</code>), and that higher values are
		 * better.
		 */
		POSITIVE_QUANTITY {

			@Override
			public String toString() {
				return "+ Quantity";
			}

		},
		/**
		 * Indicates a quantity-type <code>Attribute</code> (whose range is from
		 * <code>0.0</code> to <code>&#8734;</code>), and that lower values are
		 * better.
		 */
		NEGATIVE_QUANTITY {

			@Override
			public String toString() {
				return "- Quantity";
			}

		},
		/**
		 * Indicates a quantity-type <code>Attribute</code> (whose range is from
		 * <code>0.0</code> to <code>1.0</code>), and that higher values are
		 * better.
		 */
		POSITIVE_QUALITY {

			@Override
			public String toString() {
				return "+ Quality";
			}

		},
		/**
		 * Indicates a quantity-type <code>Attribute</code> (whose range is from
		 * <code>0.0</code> to <code>1.0</code>), and that lower values are
		 * better.
		 */
		NEGATIVE_QUALITY {

			@Override
			public String toString() {
				return "- Quality";
			}

		},
		/**
		 * Indicates an unbounded-type <code>Attribute</code> (whose range is
		 * from <code>-&#8734;</code> to <code>+&#8734;</code>), and that higher
		 * values are better.
		 */
		POSITIVE_UNBOUNDED {

			@Override
			public String toString() {
				return "+ Unbounded";
			}

		},
		/**
		 * Indicates an unbounded-type <code>Attribute</code> (whose range is
		 * from <code>-&#8734;</code> to <code>+&#8734;</code>), and that lower
		 * values are better.
		 */
		NEGATIVE_UNBOUNDED {

			@Override
			public String toString() {
				return "- Unbounded";
			}

		};

	}

	/**
	 * Returns the <code>Type</code> of this <code>Attribute</code>.
	 * 
	 * @return the <code>Type</code> of this <code>Attribute</code>.
	 */
	Type getType();

	/**
	 * Returns the set of <code>Role</code>s that influences this
	 * <code>Attribute</code>.
	 * 
	 * @return the set of <code>Role</code>s that influences this
	 *         <code>Attribute</code>.
	 */
	Set<Role> getInfluencedBySet();

	/**
	 * Returns the set of <code>Agent</code> that has this
	 * <code>Attribute</code>.
	 * 
	 * @return the set of <code>Agent</code> that has this
	 *         <code>Attribute</code>.
	 */
	Set<Agent<?>> getHadBySet();

	/**
	 * Returns the score of the <code>Agent</code> (from the given
	 * <code>UniqueIdentifier</code>) that has this <code>Attribute</code>.
	 * 
	 * @param agentIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the
	 *            <code>Agent</code> to retrieve the score.
	 * @return the score if it exists, <code>null</code> otherwise.
	 */
	Double getHadByValue(UniqueIdentifier agentIdentifier);

	/**
	 * Returns the set of <code>PerformanceFunction</code> that moderates this
	 * <code>Attribute</code>.
	 * 
	 * @return the set of <code>PerformanceFunction</code> that moderates this
	 *         <code>Attribute</code>.
	 */
	Set<PerformanceFunction> getModeratedBySet();
}
