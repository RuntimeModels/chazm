package runtimemodels.chazm.model.validation;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import runtimemodels.chazm.model.message.E;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static runtimemodels.chazm.model.validation.Checks.checkNotNull;

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
