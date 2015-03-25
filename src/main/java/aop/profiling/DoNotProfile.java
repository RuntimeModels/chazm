package aop.profiling;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author Christopher Zhong
 * @since 7.0.0
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface DoNotProfile {}
