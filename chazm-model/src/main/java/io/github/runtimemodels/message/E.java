package io.github.runtimemodels.message;

import java.util.MissingFormatArgumentException;

@SuppressWarnings("javadoc")
public enum E { // for exceptions

    ENTITY_ALREADY_EXISTS("%s entity '%s' already exists"), //
    ENTITY_DOES_NOT_EXISTS("%s entity '%s' does not exists"), //
    INCOMPLETE_XML_FILE("Incomplete XML file: <%s> element (id='%s') is missing"), //
    MISSING_ATTRIBUTE_IN_TAG("Tag <%s> is missing attribute '%s'"), //
    MISSING_END_TAG("Tag (<%1$s>) is missing enclosing tag </ %1$s>"), //
    PARAMETER_CANNOT_BE_NULL("'%s' of method '%s()' cannot be null"), //
    SCORE_BETWEEN("Score '%s' must be between '%s' and '%s'"), //
    VALUE_AT_LEAST("For '%s' attribute type, value='%s' must be at least '%s'"), //
    VALUE_BETWEEN("For '%s' attribute type, value='%s' must be between '%s' and '%s'");

    private final String string;

    E(final String string) {
        this.string = string;
    }

    public String get(final Object... args) {
        try {
            return String.format(string, args);
        } catch (final MissingFormatArgumentException e) {
            return null;
        }
    }
}
