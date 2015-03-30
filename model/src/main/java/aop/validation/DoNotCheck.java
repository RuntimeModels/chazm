package aop.validation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The {@linkplain DoNotCheck} annotation can be used to inform any validation interceptors to ignore the annotated class or method.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface DoNotCheck {}
