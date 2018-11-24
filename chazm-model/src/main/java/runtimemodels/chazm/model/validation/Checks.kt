package runtimemodels.chazm.model.validation

import runtimemodels.chazm.model.message.E

import javax.inject.Singleton

/**
 * The [Checks] provides various methods for preconditions and postconditions checking.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@Singleton
object Checks {

    /**
     * Checks and throws an [IllegalArgumentException] if the variable is `null`.
     *
     * @param <T> the type of the variable.
     * @param <U> the type of the variable name.
     * @param variable     the variable to check.
     * @param variableName the variable name.
     * @throws IllegalArgumentException if the variable is `null`.
    </U></T> */
    @JvmStatic
    @Throws(IllegalArgumentException::class)
    fun <T, U> checkNotNull(variable: T?, variableName: U?) {
        if (variable == null) {
            throw IllegalArgumentException(E.PARAMETER_CANNOT_BE_NULL.get(variableName ?: "<parameter>")) //$NON-NLS-1$
        }
    }

}
