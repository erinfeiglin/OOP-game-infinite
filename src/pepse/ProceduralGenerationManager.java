package pepse;

import danogl.GameObject;
import danogl.util.Vector2;
import danogl.collisions.Layer;
import pepse.util.Constants;
import pepse.world.Block;
import pepse.world.Terrain;
import pepse.world.trees.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Manages procedural generation of terrain and trees based on the camera's position.
 */
public class ProceduralGenerationManager {
    private final Terrain terrain;
    private final Flora flora;
    private final GameObject toFollow;
    private final Vector2 windowDimensions;
    private final GameObjectManager objectManager;
    private final Set<Integer> genetatedChunks = new HashSet<>();
    private final List<GameObject> activeBlocks = new ArrayList<>();
    private final List<GameObject> activeTrees = new ArrayList<>();


    /**
     * a constructor
     *
     * @param terrain          terrain object for generating terrain
     * @param flora            flora object for generating trees
     * @param camera           camera object to get coordinates
     * @param windowDimensions size of window
     */
    public ProceduralGenerationManager(Terrain terrain, Flora flora, GameObjectManager objectManager,
                                       GameObject toFollow, Vector2 windowDimensions) {
        this.terrain = terrain;
        this.flora = flora;
        this.toFollow = toFollow;
        this.windowDimensions = windowDimensions;
        this.objectManager = objectManager;
    }

    /**
     * Updates the procedural generation logic.
     * Removes objects outside the visible range and generates new terrain and trees within bounds.
     *
     * @param deltaTime Time since the last update (unused here but provided for consistency).
     */
    public void update(float deltaTime) {
        int chunkSize = Constants.BLOCK_SIZE * Constants.CHUNK_WIDTH;
        float centerX = toFollow.getCenter().x();

        int minChunk = (int) Math.floor((centerX - windowDimensions.x() / Constants.HALF) / chunkSize);
        int maxChunk = (int) Math.ceil((centerX + windowDimensions.x() / Constants.HALF) / chunkSize);
        activeBlocks.removeIf(block -> {
            if (!isWithinBounds(block, minChunk, maxChunk, chunkSize)) {
                objectManager.removeGameObject(block, Layer.STATIC_OBJECTS);
                return true;
            }
            return false;
        });
        activeTrees.removeIf(tree -> {
            if (!isWithinBounds(tree, minChunk, maxChunk, chunkSize)) {
                objectManager.removeGameObject(tree, Layer.STATIC_OBJECTS);
                return true;
            }
            return false;
        });
        for(int chunk = minChunk; chunk <= maxChunk; chunk++) {
            if(!genetatedChunks.contains(chunk)) {
                int minX = chunk * chunkSize;
                int maxX = minX + chunkSize;

                List<Block> generatedTerrain = terrain.createInRange(minX, maxX);
                for (Block block : generatedTerrain) {
                    objectManager.addGameObject(block, Layer.STATIC_OBJECTS);
                    activeBlocks.add(block);
                }
                List<Tree> generatedFlora = flora.createInRange(minX, maxX);
                for (Tree tree : generatedFlora) {
                    GameObject trunk = tree.getTrunk();
                    objectManager.addGameObject(trunk, Layer.STATIC_OBJECTS);
                    activeTrees.add(trunk);
                    for (GameObject leaf : tree.getLeaves()) {
                        objectManager.addGameObject(leaf, Layer.FOREGROUND);
                        activeTrees.add(leaf);
                    }
                    for (GameObject fruit : tree.getFruits()) {
                        objectManager.addGameObject(fruit, Layer.STATIC_OBJECTS);
                        activeTrees.add(fruit);
                    }
                }
                genetatedChunks.add(chunk);
            }
        }
        genetatedChunks.removeIf(chunk -> chunk < minChunk || chunk > maxChunk);
    }

    /**
     * Checks if a game object is within the visible range of chunks.
     *
     * @param gameObject The game object to check.
     * @param minChunk   The minimum visible chunk index.
     * @param maxChunk   The maximum visible chunk index.
     * @param chunkSize  The size of a single chunk.
     * @return True if the game object is within bounds, false otherwise.
     */
    private boolean isWithinBounds(GameObject gameObject, int minChunk, int maxChunk, int chunkSize) {
        float x = gameObject.getCenter().x();
        int chunk = (int) Math.floor(x / chunkSize);
        return chunk >= minChunk && chunk <= maxChunk;
    }
}
