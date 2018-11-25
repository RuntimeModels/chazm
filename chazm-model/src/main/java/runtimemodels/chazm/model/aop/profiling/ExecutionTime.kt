package runtimemodels.chazm.model.aop.profiling

import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation
import runtimemodels.chazm.model.message.L

/**
 * The [ExecutionTime] is a method Interceptor that tracks the time it takes for a method to complete.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
internal class ExecutionTime : MethodInterceptor {

    @Throws(Throwable::class)
    override fun invoke(invocation: MethodInvocation): Any {
        val start = System.nanoTime()
        try {
            return invocation.proceed()
        } finally {
            val end = System.nanoTime()
            log.trace(L.EXECUTION_TIME.get(), ExecutionTime::class.java.simpleName, end - start, invocation.method.genericReturnType.typeName
                .replace("(\\w+\\.)*".toRegex(), ""), invocation.method.declaringClass.simpleName, invocation.method.name) //$NON-NLS-1$ //$NON-NLS-2$

        }
    }

    companion object {
        private val log = org.slf4j.LoggerFactory.getLogger(ExecutionTime::class.java)
    }

}
