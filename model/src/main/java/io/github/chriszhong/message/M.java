package io.github.chriszhong.message;

import java.util.MissingFormatArgumentException;

@SuppressWarnings("javadoc")
public enum M { // everything else

	ASSIGNMENT("<%s, %s, %s>"), //
	ASSIGNMENT_ID("%s(%s, %s, %s)"), //
	ENTITY_0("%s(%s)"), //
	ENTITY_1("%s: %s"), //
	ENTITY_2("%s: %s, %s"), //
	EVENT("%s(%s)"), //
	EVENT_WITH_1_ID("%s: %s"), //
	EVENT_WITH_2_IDS("%s: %s, %s"), //
	EVENT_WITH_2_IDS_AND_VALUE("%s: %s, %s: %s"), //
	EVENT_WITH_3_IDS("%s: %s, %s, %s"), //
	RELATION("<%s, %s>"), //
	RELATION_WITH_VALUE("<%s, %s>: %s");

	private final String string;

	private M(final String string) {
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
