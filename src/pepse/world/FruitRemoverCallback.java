package pepse.world;

import danogl.GameObject;
import danogl.collisions.Layer;
import pepse.GameObjectManager;
import pepse.PepseGameManager;

/**
 * Callback class for handling fruit consumption events.
 * Removes the consumed fruit from the game world.
 */
public class FruitRemoverCallback implements EnergyCallback {
    private final GameObjectManager gameManager;

    /**
     * Constructor for the FruitRemoverCallback class.
     *
     * @param gameManager The GameObjectManager used to manage game objects in the world.
     */
    public FruitRemoverCallback(PepseGameManager gameManager){
        this.gameManager = gameManager;
    }

/**
 * Called when the energy level changes. No action is taken in this implementation.
 *
 * @param currentEnergy The current energy level of the avatar.
 */
    @Override
    public void onEnergyChanged(double currentEnergy) {
        return;
    }

    /**
     * Called when a fruit is consumed. Removes the fruit from the game world.
     *
     * @param fruit The GameObject representing the consumed fruit.
     */
    @Override
    public void onFruitConsumed(GameObject fruit) {
        if (fruit != null) {
            this.gameManager.removeGameObject(fruit, Layer.STATIC_OBJECTS);
        }
    }
}
