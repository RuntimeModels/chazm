package runtimemodels.chazm.api.entity

/**
 * The [CharacteristicManager] interface defines APIs that can be used for managing a set [Characteristic]s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
interface CharacteristicManager : Map<CharacteristicId, Characteristic> {
    /**
     * Adds a [Characteristic] to this [CharacteristicManager].
     *
     * @param characteristic the [Characteristic] to add.
     */
    fun add(characteristic: Characteristic)

    /**
     * Removes a [Characteristic] from this [CharacteristicManager].
     *
     * @param id the [CharacteristicId] that represents the [Characteristic] to remove.
     * @return the [Characteristic] that was removed.
     */
    fun remove(id: CharacteristicId): Characteristic
}
