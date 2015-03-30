package aop.validation;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.not;

import com.google.inject.AbstractModule;

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
