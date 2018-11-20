package runtimemodels.chazm.model.validation;

import runtimemodels.chazm.model.message.E;

import javax.inject.Singleton;

/**
 * The {@linkplain Checks} provides various methods for preconditions and postconditions checking.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@Singleton
public final class Checks {

    /**
     * Checks and throws an {@linkplain IllegalArgumentException} if the variable is <code>null</code>.
     *
     * @param <T> the type of the variable.
     * @param <U> the type of the variable name.
     * @param variable     the variable to check.
     * @param variableName the variable name.
     * @throws IllegalArgumentException if the variable is <code>null</code>.
     */
    public static <T, U> void checkNotNull(final T variable, final U variableName) throws IllegalArgumentException {
        if (variable == null) {
            throw new IllegalArgumentException(E.PARAMETER_CANNOT_BE_NULL.get(variableName == null ? "<parameter>" : variableName)); //$NON-NLS-1$
        }
    }

}
