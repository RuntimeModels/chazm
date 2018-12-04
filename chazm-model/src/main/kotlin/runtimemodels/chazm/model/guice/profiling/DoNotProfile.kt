package runtimemodels.chazm.model.guice.profiling


/**
 * The [DoNotProfile] annotation can be used to inform any profiling interceptors to ignore the annotated class or method.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class DoNotProfile
