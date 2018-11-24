package runtimemodels.chazm.model.aop.validation

import com.google.inject.AbstractModule

import com.google.inject.matcher.Matchers.annotatedWith
import com.google.inject.matcher.Matchers.not
import java.lang.reflect.Method

/**
 * The [ValidationModule] class provides a Guice binding module for validations.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
class ValidationModule : AbstractModule() {

    override fun configure() {
        bindInterceptor(not(annotatedWith(DoNotCheck::class.java)), not<Method>(annotatedWith(DoNotCheck::class.java)), CheckNotNull())
    }

}
