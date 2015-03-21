package model.organization;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;

@SuppressWarnings("javadoc")
public class OrganizationTest {

	private final Injector injector = Guice.createInjector(new OrganizationModule());
	private final Provider<Organization> provider = injector.getProvider(Organization.class);

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testOrganization() {
		final Organization o1 = provider.get();
		final Organization o2 = provider.get();
		assertThat(o1, is(not(sameInstance(o2))));
	}

}
