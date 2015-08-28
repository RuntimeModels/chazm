package io.github.runtimemodels.aop.profiling;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import mockit.Capturing;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.NonStrictExpectations;
import mockit.Tested;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

@SuppressWarnings("javadoc")
@RunWith(JMockit.class)
public class ExecutionTimeTest {

	static class MockExecutionTime extends MockUp<ExecutionTime> {

		List<String> methodNames = new ArrayList<>();

		MockExecutionTime() {
			super(ExecutionTime.class);
		}

		@Mock
		public Object invoke(final MethodInvocation invocation) throws Throwable {
			methodNames.add(invocation.getMethod().getName());
			return true;
		}
	}

	static class TestClass {

		public void methodA() {}

		@DoNotProfile
		public void methodB() {}

		public void methodC() {}

		@DoNotProfile
		public void methodD() {}

	}

	@Tested
	private ExecutionTime executionTime;

	@Test
	public void testLogic(@Injectable final MethodInvocation invocation, @Injectable final Method method, @Injectable final Type type,
			@Capturing final Logger logger) throws Throwable {
		new NonStrictExpectations() {
			{
				invocation.proceed();
				result = true;

				invocation.getMethod();
				result = method;

				method.getGenericReturnType();
				result = type;

				method.getDeclaringClass();
				result = ExecutionTimeTest.class;

				method.getName();
				result = "name";

				type.getTypeName();
				result = "type name";
			}
		};

		final Object result = executionTime.invoke(invocation);

		assertThat(result, is(equalTo(true)));
		new Verifications() {
			{
				invocation.proceed();
				times = 1;

				logger.trace("{}: {} nanoseconds: {} {}.{}()", ExecutionTime.class.getSimpleName(), any, "type name", ExecutionTimeTest.class.getSimpleName(),
						"name");
				times = 1;
			}
		};
	}

	@Test
	public void testInterception() throws Throwable {
		final Module module = new AbstractModule() {

			@Override
			public void configure() {
				install(new ProfilingModule());
				bind(TestClass.class);
			}

		};
		final MockExecutionTime mockExecutionTime = new MockExecutionTime();
		final Injector injector = Guice.createInjector(module);
		final TestClass testClass = injector.getInstance(TestClass.class);

		testClass.methodA();
		testClass.methodB();
		testClass.methodC();
		testClass.methodD();
		testClass.methodA();
		testClass.methodA();
		testClass.methodA();
		testClass.methodB();
		testClass.methodB();
		testClass.methodB();
		testClass.methodC();

		final String[] expected = { "methodA", "methodC", "methodA", "methodA", "methodA", "methodC" };

		assertThat(mockExecutionTime.methodNames.size(), is(equalTo(expected.length)));
		for (int i = 0; i < expected.length; i++) {
			assertThat(mockExecutionTime.methodNames.get(i), is(equalTo(expected[i])));
		}
	}

}
