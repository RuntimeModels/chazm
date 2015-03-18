package model.organization.entity;

import java.io.Serializable;

import model.organization.Organization;
import model.organization.id.Identifiable;

/**
 * The {@linkplain InstanceGoal} interface defines the instance goal, which is a concrete instantiation of a {@linkplain SpecificationGoal}, of an
 * {@linkplain Organization}.
 *
 * @author Christopher Zhong
 * @since 3.4
 */
public interface InstanceGoal extends Identifiable<InstanceGoal> {

	/**
	 * The {@linkplain Parameter} interface defines the parameter of an {@linkplain InstanceGoal}.
	 *
	 * @author Christopher Zhong
	 * @since 7.0.0
	 */
	interface Parameter extends Serializable {}

	/**
	 * Returns the {@linkplain SpecificationGoal} that instantiated this {@linkplain InstanceGoal}.
	 *
	 * @return the {@linkplain SpecificationGoal} that instantiated this {@linkplain InstanceGoal}.
	 */
	SpecificationGoal getGoal();

	/**
	 * Returns the {@linkplain InstanceGoal.Parameter} of this {@linkplain InstanceGoal}.
	 *
	 * @return the {@linkplain InstanceGoal.Parameter} of this {@linkplain InstanceGoal}.
	 */
	Parameter getParameter();

}