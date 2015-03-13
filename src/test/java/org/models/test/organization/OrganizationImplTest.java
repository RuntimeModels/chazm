package org.models.test.organization;

import org.junit.Assert;
import org.junit.Test;
import org.models.organization.Organization;
import org.models.organization.OrganizationImpl;
import org.models.organization.entity.Role;
import org.models.organization.entity.RoleEntity;
import org.models.organization.entity.SpecificationGoal;
import org.models.organization.entity.SpecificationGoalEntity;
import org.models.organization.id.StringId;

@SuppressWarnings("javadoc")
public class OrganizationImplTest {

	@Test
	public void testAddRole() {
		Organization o = new OrganizationImpl();
		Assert.assertEquals("# roles should be 0", 0, o.getRoles().size());
		try {
			o.addRole(null);
			Assert.fail("IllegalArgumentException should be thrown when adding a null role");
		} catch (IllegalArgumentException e) {
		}
		Role role1 = new RoleEntity(new StringId<Role>("role1"));
		o.addRole(role1);
		Assert.assertEquals("# roles should be 1", 1, o.getRoles().size());
		Role role2 = o.getRole(new StringId<Role>("role1"));
		Assert.assertNotNull("role2 should not be null", role2);
		Role role3 = o.getRole(new StringId<Role>(""));
		Assert.assertNull("role3 should be null", role3);
		Role role4 = new RoleEntity(new StringId<Role>("role2"));
		o.addRole(role4);
		Assert.assertEquals("# roles should be 2", 2, o.getRoles().size());
		Role role5 = new RoleEntity(new StringId<Role>("role1"));
		Assert.assertNotSame("role1 should not be the same instance as role5", role1, role5);
		try {
			o.addRole(role5);
			Assert.fail("IllegalArgumentException should be thrown when adding a duplicate role");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testAddSpecificationGoal() {
		Organization o = new OrganizationImpl();
		Assert.assertEquals("# specification goals should be 0", 0, o.getSpecificationGoals().size());
		SpecificationGoal goal1 = new SpecificationGoalEntity(new StringId<SpecificationGoal>("goal1"));
		o.addSpecificationGoal(goal1);
		Assert.assertEquals("# specification goals should be 1", 1, o.getSpecificationGoals().size());
		SpecificationGoal goal2 = new SpecificationGoalEntity(new StringId<SpecificationGoal>(""));
	}

	@Test
	public void testAddAchieves() {
		Organization o = new OrganizationImpl();
		Role role = new RoleEntity(new StringId<Role>("role1"));
		o.addRole(role);
		SpecificationGoal goal = new SpecificationGoalEntity(new StringId<SpecificationGoal>("goal1"));
		o.addSpecificationGoal(goal);
		try {
			o.addAchieves(null, null);
			Assert.fail("IllegalArgumentException should be thrown when adding null elements");
		} catch (IllegalArgumentException e) {
		}
		try {
			o.addAchieves(role.getId(), null);
			Assert.fail("IllegalArgumentException should be thrown when adding null elements");
		} catch (IllegalArgumentException e) {
		}
		try {
			o.addAchieves(null, goal.getId());
			Assert.fail("IllegalArgumentException should be thrown when adding null elements");
		} catch (IllegalArgumentException e) {
		}
		o.addAchieves(new StringId<Role>("role1"), new StringId<SpecificationGoal>("goal1"));
		Assert.assertEquals("# goals achieved by role1 should be 1", 1, o.getAchieves(new StringId<Role>("role1")).size());
		Assert.assertEquals("# roles that achieves goal1 should be 1", 1, o.getAchievedBy(new StringId<SpecificationGoal>("goal1")).size());
	}

}
