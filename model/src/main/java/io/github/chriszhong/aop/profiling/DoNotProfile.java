package io.github.chriszhong.aop.profiling;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The {@linkplain DoNotProfile} annotation can be used to inform any profiling interceptors to ignore the annotated class or method.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface DoNotProfile {}
