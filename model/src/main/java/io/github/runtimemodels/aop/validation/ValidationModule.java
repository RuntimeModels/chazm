package io.github.runtimemodels.aop.validation;

import com.google.inject.AbstractModule;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.not;

/**
 * The {@linkplain ValidationModule} class provides a Guice binding module for validations.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class ValidationModule extends AbstractModule {

    @Override
    protected void configure() {
        bindInterceptor(not(annotatedWith(DoNotCheck.class)), not(annotatedWith(DoNotCheck.class)), new CheckNotNull());
    }

}
