package pepse.world;

import danogl.GameObject;

/**
 * Callback interface for responding to energy-related events in the game.
 * Classes implementing this interface can define custom behavior for energy changes and fruit consumption.
 */
public interface EnergyCallback {
    /**
     * Called when the avatar's energy level changes.
     *
     * @param currentEnergy The current energy level of the avatar.
     */
    void onEnergyChanged(double currentEnergy);

    /**
     * Called when a fruit is consumed by the avatar.
     *
     * @param fruit The GameObject representing the consumed fruit.
     */
    void onFruitConsumed(GameObject fruit);
}
