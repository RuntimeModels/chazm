package runtimemodels.chazm.api.entity

import runtimemodels.chazm.api.Organization
import runtimemodels.chazm.api.id.Identifiable

/**
 * The [Agent] interface defines an agent entity of an [Organization].
 *
 * @author Christopher Zhong
 * @since 3.4
 */
interface Agent : Identifiable<Agent> {
    /**
     * Returns a [Map] with information for contacting this [Agent].
     *
     * @return a [Map] with information for contacting this [Agent].
     */
    val contactInfo: Map<Any, Any>

    /**
     * Adds the given `key` and `value` as a contact information for this [Agent].
     *
     * @param key   an [Any] representing the `key`.
     * @param value an [Any] representing the `value`.
     */
    fun addContactInfo(key: Any, value: Any)

    /**
     * Removes a contact information by the given `key` from this [Agent].
     *
     * @param key   an [Any] representing the `key`.
     */
    fun removeContactInfo(key: Any)
}
