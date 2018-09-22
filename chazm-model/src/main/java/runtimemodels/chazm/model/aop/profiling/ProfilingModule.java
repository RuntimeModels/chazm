package runtimemodels.chazm.model.aop.profiling;

import com.google.inject.AbstractModule;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.not;

/**
 * The {@linkplain ProfilingModule} class adds AOP interceptors to profile various aspects of an application.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
class ProfilingModule extends AbstractModule {

    @Override
    protected void configure() {
        bindInterceptor(not(annotatedWith(DoNotProfile.class)), not(annotatedWith(DoNotProfile.class)), new ExecutionTime());
    }

}
