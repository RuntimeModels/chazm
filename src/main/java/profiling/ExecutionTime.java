package profiling;

import javax.interceptor.Interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@linkplain ExecutionTime} is a method Interceptor that tracks the time it takes for a method to complete.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@Interceptor
public class ExecutionTime implements MethodInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(ExecutionTime.class);

	@Override
	public Object invoke(final MethodInvocation invocation) throws Throwable {
		final long start = System.nanoTime();
		try {
			return invocation.proceed();
		} finally {
			final long end = System.nanoTime();
			logger.trace(String.format("%s: %s nanoseconds: %s %s.%s(): ", ExecutionTime.class.getSimpleName(), end - start, invocation.getMethod()
					.getGenericReturnType().getTypeName().replaceAll("(\\w+\\.)*", ""), invocation.getMethod().getDeclaringClass().getSimpleName(), invocation
					.getMethod().getName()));

		}
	}

}