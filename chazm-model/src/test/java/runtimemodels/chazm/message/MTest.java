package runtimemodels.chazm.message;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class MTest {

    @Test
    public void test() {
        for (final M m : M.values()) {
            assertThat(m.get(), is(nullValue()));
        }
    }

}
