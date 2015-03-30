package aop.validation;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

import javax.validation.constraints.NotNull;

import message.M;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@linkplain CheckNotNull} is a method interceptor that checks parameters that are annotated with {@linkplain NotNull} are not {@code null}.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class CheckNotNull implements MethodInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(CheckNotNull.class);

	@Override
	public Object invoke(@NotNull final MethodInvocation invocation) throws Throwable {
		final Object[] arguments = invocation.getArguments();
		final Method method = invocation.getMethod();
		logger.info("Checking @NotNull for {} with {}", method, Arrays.asList(arguments));
		final Parameter[] parameters = method.getParameters();
		for (int i = 0; i < parameters.length; i++) {
			if (parameters[i].isAnnotationPresent(NotNull.class) && arguments[i] == null) {
				throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, parameters[i].getName()));
			}
		}
		return invocation.proceed();
	}

}