package model.organization.validation;

import java.util.function.Function;
import java.util.function.Predicate;

import javax.validation.constraints.NotNull;

import message.M;
import model.organization.id.Identifiable;
import model.organization.id.UniqueId;

/**
 * The {@linkplain Checks} provides various methods for preconditions and postconditions checking.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public final class Checks {

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
	public static <T, U> void checkNotNull(final T variable, final U variableName) throws IllegalArgumentException {
		if (variable == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_PARAMETER_CANNOT_BE_NULL, variableName == null ? "<parameter>" : variableName));
		}
	}

	/**
	 * Checks and throws an {@linkplain IllegalArgumentException} if the {@linkplain Identifiable} object exists.
	 *
	 * @param t
	 *            the {@linkplain Identifiable} object.
	 * @param name
	 *            the variable name.
	 * @param p
	 *            the {@linkplain Predicate}.
	 */
	public static <T extends Identifiable<T>> void checkNotExists(@NotNull final T t, final String name, @NotNull final Predicate<UniqueId<T>> p) {
		checkNotNull(t, name);
		checkNotNull(p, "p");
		if (p.test(t.getId())) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_ENTITY_EXISTS, t.getId().getType().getSimpleName(), t.getId()));
		}
	}

	/**
	 * Checks and returns the object represented by the {@linkplain UniqueId} if it exists, otherwise throws an {@linkplain IllegalArgumentException}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the object.
	 * @param name
	 *            the variable name.
	 * @param f
	 *            the {@linkplain Function}.
	 * @return the object if it exists.
	 */
	public static <T, U extends UniqueId<T>> T checkExists(@NotNull final U id, final String name, @NotNull final Function<U, T> f) {
		checkNotNull(id, name);
		checkNotNull(f, "f");
		final T t = f.apply(id);
		if (t == null) {
			throw new IllegalArgumentException(String.format(M.EXCEPTION_ENTITY_DOES_NOT_EXISTS, id.getType().getSimpleName(), id));
		}
		return t;
	}

}
