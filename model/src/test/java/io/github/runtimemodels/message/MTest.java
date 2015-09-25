package io.github.runtimemodels.message;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class MTest {

	@Test
	public void test() {
		for (final M m : M.values()) {
			assertThat(m.get(), is(nullValue()));
		}
	}

}
