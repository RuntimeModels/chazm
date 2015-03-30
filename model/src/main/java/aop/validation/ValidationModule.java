package aop.validation;

import static com.google.inject.matcher.Matchers.any;

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
		bindInterceptor(any(), any(), new CheckNotNull());
	}

}
