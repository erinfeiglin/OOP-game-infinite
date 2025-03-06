package pepse.world;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.Constants;

/**
 * A class that represents an immovable block in the game world.
 * The block is a non-intersectable, immovable GameObject.
 */
public class Block extends GameObject {

    /**
     * Constructor for creating a block.
     *
     * @param topLeftCorner The top-left corner position of the block.
     * @param renderable    The renderable defining the block's appearance.
     */
    public Block(Vector2 topLeftCorner, Renderable renderable) {
        super(topLeftCorner, Vector2.ONES.mult(Constants.BLOCK_SIZE), renderable);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }
}
