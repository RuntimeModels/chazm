package io.github.runtimemodels.aop.validation;

import io.github.runtimemodels.message.E;
import io.github.runtimemodels.message.L;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

/**
 * The {@linkplain CheckNotNull} is a method interceptor that checks parameters that are annotated with {@linkplain NotNull} are not {@code null}.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
class CheckNotNull implements MethodInterceptor {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CheckNotNull.class);

    @Override
    public Object invoke(@NotNull final MethodInvocation invocation) throws Throwable {
        final Object[] arguments = invocation.getArguments();
        final Method method = invocation.getMethod();
        log.info(L.CHECK_NOT_NULL.get(), method, Arrays.asList(arguments));
        final Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].isAnnotationPresent(NotNull.class) && arguments[i] == null) {
                throw new IllegalArgumentException(E.PARAMETER_CANNOT_BE_NULL.get(parameters[i].getName(), method.getName()));
            }
        }
        return invocation.proceed();
    }

}
