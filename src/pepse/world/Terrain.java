
package pepse.world;

import danogl.GameObject;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.Constants;
import pepse.util.NoiseGenerator;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Class that manages the creation and procedural generation of terrain in the simulation.
 * The terrain is represented as a collection of block GameObjects.
 */
public class Terrain {

    private final int seed;
    private float groundHeightAtX0;
    private NoiseGenerator noiseGenerator;
    private Vector2 windowDimensions;

    private Random random = new Random();

    /**
     * Constructor for the Terrain class.
     *
     * @param windowDimensions The dimensions of the game window.
     * @param seed             The seed for procedural noise generation.
     */
    public Terrain(Vector2 windowDimensions, int seed){
        this.seed = seed;
        this.windowDimensions = windowDimensions;
        this.groundHeightAtX0 = this.windowDimensions.y() * (Constants.SUN_OFFSET_FACTOR_SCREEN);
        this.noiseGenerator = new NoiseGenerator(seed, (int) groundHeightAtX0);
    }

    /**
     * Calculates the height of the ground at a given x-coordinate.
     *
     * @param x The x-coordinate.
     * @return The height of the ground at the given x-coordinate.
     */
    public float groundHeightAt(float x) {
        if(x==0){
            return groundHeightAtX0;
        }
        float baseNoise = (float) noiseGenerator.noise(x, Constants.BLOCK_SIZE
                * Constants.TERRAIN_BASE_NOISE_FACTOR) * Constants.TERRAIN_BASE_NOISE_MULTIPLIER;
        float fineNoise = (float) noiseGenerator.noise(x * Constants.TERRAIN_X_NOISE_FACTOR,
                Constants.BLOCK_SIZE * Constants.TERRAIN_FINE_NOISE_FACTOR)
                * Constants.TERRAIN_FINE_NOISE_MULTIPLIER;

        // Combine noise layers with a gradient effect
        float combinedNoise = baseNoise + fineNoise;

        // Adjust to ensure smooth transitions
        return groundHeightAtX0 + combinedNoise;
    }


    /**
     * Generates the terrain as blocks within a specified range.
     *
     * @param minX The minimum x-coordinate of the range.
     * @param maxX The maximum x-coordinate of the range.
     * @return A list of Block objects representing the terrain.
     */
    public List<Block> createInRange(int minX, int maxX) {
        // Ensure minX and maxX are aligned with BLOCK_SIZE
        minX = (int) Math.floor(minX / (double) Constants.BLOCK_SIZE) * (int) Constants.BLOCK_SIZE;
        maxX = (int) Math.ceil(maxX / (double) Constants.BLOCK_SIZE) * (int) Constants.BLOCK_SIZE;

        List<Block> blockArray = new ArrayList<>();

        // Generate new blocks and cache them
        for (int x = minX; x < maxX; x += Constants.BLOCK_SIZE) {
            // Generate new blocks
            int groundHeight = (int) (Math.floor(groundHeightAt(x) /
                    Constants.BLOCK_SIZE) * Constants.BLOCK_SIZE);
            List<Block> newBlocks = new ArrayList<>();

            for (int y = groundHeight; y < groundHeight + Constants.BLOCK_SIZE * Constants.TERRAIN_DEPTH;
                 y += Constants.BLOCK_SIZE) {
                Vector2 blockPosition = new Vector2(x, y);
                RectangleRenderable groundRenderable = new RectangleRenderable(
                        ColorSupplier.chooseRandomColor(Constants.GROUND_COLORS));
                Block block = new Block(blockPosition, groundRenderable);
                block.setTag("ground");
                newBlocks.add(block);
            }
            blockArray.addAll(newBlocks);
        }
        return blockArray;
    }
}
