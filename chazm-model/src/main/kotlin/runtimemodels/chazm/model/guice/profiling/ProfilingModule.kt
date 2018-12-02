package runtimemodels.chazm.model.guice.profiling

import com.google.inject.AbstractModule

import com.google.inject.matcher.Matchers.annotatedWith
import com.google.inject.matcher.Matchers.not
import java.lang.reflect.Method

/**
 * The [ProfilingModule] class adds AOP interceptors to profile various aspects of an application.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
internal class ProfilingModule : AbstractModule() {

    override fun configure() {
        bindInterceptor(not(annotatedWith(DoNotProfile::class.java)), not<Method>(annotatedWith(DoNotProfile::class.java)), ExecutionTime())
    }

}
