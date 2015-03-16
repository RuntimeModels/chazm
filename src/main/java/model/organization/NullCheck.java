package model.organization;

import java.util.function.Function;
import java.util.function.Predicate;

import message.M;
import model.organization.id.Identifiable;
import model.organization.id.UniqueId;

/**
 * The {@linkplain NullCheck} interface defines a check for variables that are <code>null</code>.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface NullCheck {
	/**
	 * Checks and throws an {@linkplain IllegalArgumentException} if the variable is <code>null</code>.
	 *
	 * @param variable
	 *            the variable to check.
	 * @param variableName
	 *            the variable name.
	 * @throws IllegalArgumentException
	 *             if the variable is <code>null</code>.
	 */
	default <T, U> void checkNotNull(final T variable, final U variableName) throws IllegalArgumentException {
		if (variable == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, variableName.toString()));
		}
	}

	default <T extends Identifiable<T>> void checkNotExists(final T t, final String name, final Predicate<UniqueId<T>> p) {
		checkNotNull(t, name);
		if (p.test(t.getId())) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_ENTITY_EXISTS, t.getClass().getSimpleName(), t));
		}
	}

	default <T, U extends UniqueId<T>> T checkExists(final U id, final String name, final Function<U, T> f) {
		checkNotNull(id, name);
		final T t = f.apply(id);
		if (t == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_ENTITY_DOES_NOT_EXISTS, id.getType().getSimpleName(), id));
		}
		return t;
	}
}
