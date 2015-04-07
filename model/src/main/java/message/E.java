package message;

@SuppressWarnings("javadoc")
public enum E { // for exceptions

	ENTITY_ALREADY_EXISTS("%s entity '%s' already exists"), //
	ENTITY_DOES_NOT_EXISTS("%s entity '%s' does not exists"), //
	MISSING_ATTRIBUTE_IN_TAG("Tag <%s> is missing attribute '%s'"), //
	MISSING_END_TAG("Tag (<%1$s>) is missing enclosing tag </ %1$s>"), //
	PARAMETER_CANNOT_BE_NULL("Parameter '%s' cannot be null"), //
	SCORE_BETWEEN("Score '%s' must be between '%s' and '%s'"), //
	VALUE_AT_LEAST("For '%s' atttribute type, value='%s' must be at least '%s'"), //
	VALUE_BETWEEN("For '%s' atttribute type, value='%s' must be between '%s' and '%s'");

	private final String string;

	private E(final String string) {
		this.string = string;
	}

	public String get(final Object... args) {
		return String.format(string, args);
	}

}