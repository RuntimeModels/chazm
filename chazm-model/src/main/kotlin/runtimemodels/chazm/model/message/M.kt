package runtimemodels.chazm.model.message

import org.slf4j.LoggerFactory
import java.util.*

enum class M(private val string: String) { // everything else

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

    operator fun get(vararg args: Any): String {
        return try {
            String.format(string, *args)
        } catch (e: MissingFormatArgumentException) {
            log.warn("{}", e)
            ""
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(M::class.java)
    }
}
