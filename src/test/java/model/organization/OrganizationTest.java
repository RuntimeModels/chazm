package model.organization;

import model.organization.Organization;
import model.organization.OrganizationFactory;
import model.organization.OrganizationModule;
import model.organization.entity.EntityFactory;
import model.organization.entity.EntityModule;
import model.organization.entity.Role;
import model.organization.entity.SpecificationGoal;
import model.organization.id.IdFactory;
import model.organization.id.IdModule;
import model.organization.relation.RelationModule;

import org.junit.Assert;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

@SuppressWarnings("javadoc")
public class OrganizationTest {

	private Injector injector = Guice.createInjector(new OrganizationModule(), new EntityModule(), new RelationModule(), new IdModule());
	private OrganizationFactory organizationFactory = injector.getInstance(OrganizationFactory.class);
	private EntityFactory entityFactory = injector.getInstance(EntityFactory.class);
	private IdFactory idFactory = injector.getInstance(IdFactory.class);

	@Test
	public void testAddRole() {
		final Organization o = organizationFactory.buildOrganization();
		Assert.assertEquals("# roles should be 0", 0, o.getRoles().size());
		try {
			o.addRole(null);
			Assert.fail("IllegalArgumentException should be thrown when adding a null role");
		} catch (final IllegalArgumentException e) {
		}
		final Role r1 = entityFactory.buildRole(idFactory.buildId(Role.class, "role1"));
		o.addRole(r1);
		Assert.assertEquals("# roles should be 1", 1, o.getRoles().size());
		final Role r2 = o.getRole(idFactory.buildId(Role.class, "role1"));
		Assert.assertNotNull("r2 should not be null", r2);
		final Role r3 = o.getRole(idFactory.buildId(Role.class, ""));
		Assert.assertNull("r3 should be null", r3);
		final Role r4 = entityFactory.buildRole(idFactory.buildId(Role.class, "role2"));
		o.addRole(r4);
		Assert.assertEquals("# roles should be 2", 2, o.getRoles().size());
		final Role r5 = entityFactory.buildRole(idFactory.buildId(Role.class, "role1"));
		Assert.assertNotSame("r1 should not be the same instance as r5", r1, r5);
		try {
			o.addRole(r5);
			Assert.fail("IllegalArgumentException should be thrown when adding a duplicate role");
		} catch (final IllegalArgumentException e) {
		}
	}

	@Test
	public void testAddSpecificationGoal() {
		final Organization o = organizationFactory.buildOrganization();
		Assert.assertEquals("# specification goals should be 0", 0, o.getSpecificationGoals().size());
		final SpecificationGoal goal1 = entityFactory.buildSpecificationGoal(idFactory.buildId(SpecificationGoal.class, "goal1"));
		o.addSpecificationGoal(goal1);
		Assert.assertEquals("# specification goals should be 1", 1, o.getSpecificationGoals().size());
		final SpecificationGoal goal2 = entityFactory.buildSpecificationGoal(idFactory.buildId(SpecificationGoal.class, ""));
	}

	@Test
	public void testAddAchieves() {
		final Organization o = organizationFactory.buildOrganization();
		final Role r1 = entityFactory.buildRole(idFactory.buildId(Role.class, "role1"));
		o.addRole(r1);
		final SpecificationGoal g1 = entityFactory.buildSpecificationGoal(idFactory.buildId(SpecificationGoal.class, "goal1"));
		o.addSpecificationGoal(g1);
		try {
			o.addAchieves(null, null);
			Assert.fail("IllegalArgumentException should be thrown when adding null elements");
		} catch (final IllegalArgumentException e) {
		}
		try {
			o.addAchieves(r1.getId(), null);
			Assert.fail("IllegalArgumentException should be thrown when adding null elements");
		} catch (final IllegalArgumentException e) {
		}
		try {
			o.addAchieves(null, g1.getId());
			Assert.fail("IllegalArgumentException should be thrown when adding null elements");
		} catch (final IllegalArgumentException e) {
		}
		o.addAchieves(idFactory.buildId(Role.class, "role1"), idFactory.buildId(SpecificationGoal.class, "goal1"));
		Assert.assertEquals("# goals achieved by role1 should be 1", 1, o.getAchieves(idFactory.buildId(Role.class, "role1")).size());
		Assert.assertEquals("# roles that achieves goal1 should be 1", 1, o.getAchievedBy(idFactory.buildId(SpecificationGoal.class, "goal1")).size());
	}

}
