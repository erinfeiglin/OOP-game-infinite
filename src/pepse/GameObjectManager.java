package pepse;

import danogl.GameObject;

/**
 * Interface for managing game objects within the game world.
 */
public interface GameObjectManager {

    /**
     * Adds a game object to the specified layer.
     *
     * @param gameObject The game object to add.
     * @param layer The layer where the game object will be added.
     */
    void addGameObject(GameObject gameObject, int layer);
    /**
     * Removes a game object from the specified layer.
     *
     * @param gameObject The game object to remove.
     * @param layer The layer from which the game object will be removed.
     */
    void removeGameObject(GameObject gameObject, int layer);
}
