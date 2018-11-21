package runtimemodels.chazm.model.event;

/**
 * The [EventType] enumerates the three type of updates that can occur: something was added, something was removed, something was changed.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public enum EventType {

    /**
     * Indicates that associated object was added.
     */
    ADDED,
    /**
     * Indicates that associated object was removed.
     */
    REMOVED,
    /**
     * Indicates that associated object was updated.
     */
    UPDATED

}
