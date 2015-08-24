package io.github.chriszhong.model.organization.validation;

import static io.github.chriszhong.model.organization.validation.Checks.checkNotNull;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import io.github.chriszhong.message.E;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

@SuppressWarnings("javadoc")
public class ChecksTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testChecks() {
		assertThat(new Checks(), is(not(nullValue())));
	}

	@Test
	public void testCheckNotNull() {
		checkNotNull("", "");
	}

	@Test
	public void testCheckNotNull1() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(equalTo(E.PARAMETER_CANNOT_BE_NULL.get("")));

		checkNotNull(null, "");
	}

	@Test
	public void testCheckNotNull2() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(equalTo(E.PARAMETER_CANNOT_BE_NULL.get("<parameter>")));

		checkNotNull(null, null);
	}

}
