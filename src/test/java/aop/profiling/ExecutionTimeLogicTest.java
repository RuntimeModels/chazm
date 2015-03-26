package aop.profiling;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.junit.runner.RunWith;

@SuppressWarnings("javadoc")
@RunWith(JMockit.class)
public class ExecutionTimeLogicTest {

	@Test
	public void test(@Injectable MethodInvocation invocation, @Injectable Method method, @Injectable Type type) throws Throwable {
		new Expectations() {
			{
				invocation.proceed();
				times = 1;
				result = true;

				invocation.getMethod();
				times = 3;
				result = method;

				method.getGenericReturnType();
				times = 1;
				result = type;

				method.getDeclaringClass();
				times = 1;
				result = ExecutionTimeLogicTest.class;

				method.getName();
				times = 1;
				result = "name";

				type.getTypeName();
				times = 1;
				result = "type name";
			}
		};

		ExecutionTime executionTime = new ExecutionTime();
		Object result = executionTime.invoke(invocation);
		assertThat(result, is(true));
	}

}
