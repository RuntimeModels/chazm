package aop.profiling;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import mockit.Capturing;
import mockit.Injectable;
import mockit.NonStrictExpectations;
import mockit.Tested;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

@SuppressWarnings("javadoc")
@RunWith(JMockit.class)
public class ExecutionTimeLogicTest {

	@Tested
	private ExecutionTime executionTime;

	@Test
	public void test(@Injectable final MethodInvocation invocation, @Injectable final Method method, @Injectable final Type type, @Capturing final Logger logger)
			throws Throwable {
		new NonStrictExpectations() {
			{
				invocation.proceed();
				result = true;

				invocation.getMethod();
				result = method;

				method.getGenericReturnType();
				result = type;

				method.getDeclaringClass();
				result = ExecutionTimeLogicTest.class;

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

				logger.trace("{}: {} nanoseconds: {} {}.{}()", "ExecutionTime", any, "type name", "ExecutionTimeLogicTest", "name");
				times = 1;
			}
		};
	}

}
