/*
 * ModeratesRelation.java
 *
 * Created on May 18, 2010
 *
 * See License.txt file the license agreement.
 */
package model.organization.relation
import model.organization.entity.Attribute;
import model.organization.entity.PerformanceFunction;
/**
 * Represents the moderates relation, where a {@link PerformanceFunction}
 * moderates an {@link Attribute}.
 *
 * @author Christopher Zhong
 * @version $Revision: 1.1.2.3 $, $Date: 2010/06/24 19:26:06 $
 * @see PerformanceFunction
 * @see Attribute
 * @since 6.0
 */
class ModeratesRelation {
	/**
	 * The <code>PerformanceFunction</code> of this
	 * <code>ModeratesRelation</code>.
	 */
	val PerformanceFunction performanceFunction
	/**
	 * The <code>Attribute</code> of this <code>ModeratesRelation</code>.
	 */
	val Attribute attribute
	/**
	 * Optimization for hash code computation since it never changes.
	 */
	Integer hashCode = null
	/**
	 * Optimization for <code>toString</code> method since it never changes.
	 */
	String toString = null
	/**
	 * Constructs a new instance of <code>ModeratesRelation</code>.
	 *
	 * @param performanceFunction
	 *            the <code>Role</code> of this <code>ModeratesRelation</code>.
	 * @param attribute
	 *            the <code>Attribute</code> of this
	 *            <code>ModeratesRelation</code>.
	 */
	new(PerformanceFunction performanceFunction, Attribute attribute) {
		if (performanceFunction == null || attribute == null) {
			throw new IllegalArgumentException('''Parameters (performanceFunction: «performanceFunction», attribute: «attribute») cannot be null''')
		}
		this.performanceFunction = performanceFunction
		this.attribute = attribute
	}
	/**
	 * Returns the <code>PerformanceFunction</code> of this
	 * <code>ModeratesRelation</code>.
	 *
	 * @return the <code>PerformanceFunction</code> of this
	 *         <code>ModeratesRelation</code>.
	 */
	def getPerformanceFunction() {
		performanceFunction
	}
	/**
	 * Returns the <code>Attribute</code> of this <code>ModeratesRelation</code>.
	 *
	 * @return the <code>Attribute</code> of this <code>ModeratesRelation</code>.
	 */
	def getAttribute() {
		attribute
	}
	override boolean equals(Object object) {
		if (object instanceof ModeratesRelation) {
			val moderatesRelation = object as ModeratesRelation
			return performanceFunction.equals(moderatesRelation.performanceFunction) && attribute.equals(moderatesRelation.attribute)
		}
		false
	}
	override int hashCode() {
		if (hashCode == null) {
			hashCode = 1
			hashCode = 31 * hashCode + performanceFunction.hashCode
			hashCode = 31 * hashCode + attribute.hashCode
		}
		hashCode
	}
	override String toString() {
		if (toString == null) {
			toString = '''«performanceFunction.identifier» <-> «attribute.identifier»'''
		}
		toString
	}
}