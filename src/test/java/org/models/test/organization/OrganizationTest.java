package org.models.test.organization;

import model.organization.Organization;
import model.organization.OrganizationFactory;
import model.organization.OrganizationModule;
import model.organization.entity.EntityFactory;
import model.organization.entity.EntityModule;
import model.organization.entity.Role;
import model.organization.entity.SpecificationGoal;
import model.organization.ids.StringId;
import model.organization.relation.RelationModule;

import org.junit.Assert;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

@SuppressWarnings("javadoc")
public class OrganizationTest {

	private Injector injector = Guice.createInjector(new OrganizationModule(), new EntityModule(), new RelationModule());
	private OrganizationFactory organizationFactory = injector.getInstance(OrganizationFactory.class);
	private EntityFactory entityFactory = injector.getInstance(EntityFactory.class);

	@Test
	public void testAddRole() {
		final Organization o = organizationFactory.buildOrganization();
		Assert.assertEquals("# roles should be 0", 0, o.getRoles().size());
		try {
			o.addRole(null);
			Assert.fail("IllegalArgumentException should be thrown when adding a null role");
		} catch (final IllegalArgumentException e) {
		}
		final Role role1 = entityFactory.buildRole(new StringId<>(Role.class, "role1"));
		o.addRole(role1);
		Assert.assertEquals("# roles should be 1", 1, o.getRoles().size());
		final Role role2 = o.getRole(new StringId<>(Role.class, "role1"));
		Assert.assertNotNull("role2 should not be null", role2);
		final Role role3 = o.getRole(new StringId<>(Role.class, ""));
		Assert.assertNull("role3 should be null", role3);
		final Role role4 = entityFactory.buildRole(new StringId<>(Role.class, "role2"));
		o.addRole(role4);
		Assert.assertEquals("# roles should be 2", 2, o.getRoles().size());
		final Role role5 = entityFactory.buildRole(new StringId<>(Role.class, "role1"));
		Assert.assertNotSame("role1 should not be the same instance as role5", role1, role5);
		try {
			o.addRole(role5);
			Assert.fail("IllegalArgumentException should be thrown when adding a duplicate role");
		} catch (final IllegalArgumentException e) {
		}
	}

	@Test
	public void testAddSpecificationGoal() {
		final Organization o = organizationFactory.buildOrganization();
		Assert.assertEquals("# specification goals should be 0", 0, o.getSpecificationGoals().size());
		final SpecificationGoal goal1 = entityFactory.buildSpecificationGoal(new StringId<>(SpecificationGoal.class, "goal1"));
		o.addSpecificationGoal(goal1);
		Assert.assertEquals("# specification goals should be 1", 1, o.getSpecificationGoals().size());
		final SpecificationGoal goal2 = entityFactory.buildSpecificationGoal(new StringId<>(SpecificationGoal.class, ""));
	}

	@Test
	public void testAddAchieves() {
		final Organization o = organizationFactory.buildOrganization();
		final Role role = entityFactory.buildRole(new StringId<>(Role.class, "role1"));
		o.addRole(role);
		final SpecificationGoal goal = entityFactory.buildSpecificationGoal(new StringId<>(SpecificationGoal.class, "goal1"));
		o.addSpecificationGoal(goal);
		try {
			o.addAchieves(null, null);
			Assert.fail("IllegalArgumentException should be thrown when adding null elements");
		} catch (final IllegalArgumentException e) {
		}
		try {
			o.addAchieves(role.getId(), null);
			Assert.fail("IllegalArgumentException should be thrown when adding null elements");
		} catch (final IllegalArgumentException e) {
		}
		try {
			o.addAchieves(null, goal.getId());
			Assert.fail("IllegalArgumentException should be thrown when adding null elements");
		} catch (final IllegalArgumentException e) {
		}
		o.addAchieves(new StringId<>(Role.class, "role1"), new StringId<>(SpecificationGoal.class, "goal1"));
		Assert.assertEquals("# goals achieved by role1 should be 1", 1, o.getAchieves(new StringId<>(Role.class, "role1")).size());
		Assert.assertEquals("# roles that achieves goal1 should be 1", 1, o.getAchievedBy(new StringId<>(SpecificationGoal.class, "goal1")).size());
	}

}
