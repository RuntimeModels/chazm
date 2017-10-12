package io.github.runtimemodels.aop.profiling;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The {@linkplain DoNotProfile} annotation can be used to inform any profiling interceptors to ignore the annotated class or method.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@Documented
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface DoNotProfile {}
