package pepse.world.weather;

import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import pepse.util.Constants;
import pepse.world.Block;
import pepse.util.ColorSupplier;

import danogl.GameObject;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * A class that handles the creation and animation of clouds in the game world.
 * Clouds consist of multiple blocks and move across the screen with a transition.
 */
public class Cloud {


    private final Random random = new Random();
    private final List<Block> blocks = new ArrayList<>();
    private final List<Float> blockOffsets = new ArrayList<>();
    private final Vector2 initialPosition;
    private final Vector2 finalPosition;

    /**
     * Constructor for the Cloud class.
     *
     * @param windowDimensions Dimensions of the game window.
     * @param cameraPosition   Position of the camera (unused but kept for flexibility).
     */
    public Cloud(Vector2 windowDimensions, Vector2 cameraPosition) {
        this.initialPosition = new Vector2(-Constants.BLOCK_SIZE * (Constants.CLOUD_GRID_SIZE + 1),
                windowDimensions.y() * (Constants.CLOUD_Y_DIMENSIONS_FACTOR));
        this.finalPosition = new Vector2(windowDimensions.x() + Constants.BLOCK_SIZE *
                Constants.CLOUD_GRID_SIZE,
                windowDimensions.y() * (Constants.CLOUD_Y_DIMENSIONS_FACTOR));
    }

    /**
     * Creates the cloud by generating blocks based on a binary grid.
     */
    public void create() {
        int[][] binaryGrid = generateBinaryGrid(getProbabilityGrid());

        for (int row = 0; row < binaryGrid.length; row++) {
            for (int col = 0; col < binaryGrid[0].length; col++) {
                if (binaryGrid[row][col] == 1) {
                    Vector2 blockPosition = initialPosition.add(new Vector2(col * Constants.BLOCK_SIZE,
                            row * Constants.BLOCK_SIZE));
                    RectangleRenderable blockRenderable = new RectangleRenderable(
                            ColorSupplier.approximateMonoColor(Constants.BASE_CLOUD_COLOR)
                    );
                    Block block = new Block(blockPosition, blockRenderable);
                    block.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
                    blocks.add(block);
                    blockOffsets.add((float) col * Constants.BLOCK_SIZE);
                }
            }
        }
    }

    /**
     * Starts the animation for the cloud's movement across the screen.
     *
     * @param owner             The GameObject controlling the animation.
     * @param animationDuration The duration of the cloud's transition.
     * @param onComplete        A Runnable to execute upon animation completion.
     */
    public void startTransition(GameObject owner, float animationDuration, Runnable onComplete) {
        new Transition<>(
                owner,
                dx -> {
                    for (int i = 0; i < blocks.size(); i++) {
                        Block block = blocks.get(i);
                        float offsetX = blockOffsets.get(i);
                        Vector2 newPosition = new Vector2(initialPosition.x() + dx + offsetX,
                                block.getTopLeftCorner().y());
                        block.setTopLeftCorner(newPosition);
                    }
                },
                0f,
                (finalPosition.x() - initialPosition.x()),
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                animationDuration,
                Transition.TransitionType.TRANSITION_ONCE,
                onComplete);
    }

    /**
     * Returns the list of blocks composing the cloud.
     *
     * @return A list of Block objects.
     */
    public List<Block> getBlocks() {
        return blocks;
    }

    /**
     * Generates a binary grid based on a probability grid.
     *
     * @param probabilities A 2D array of probabilities for block placement.
     * @return A binary grid where 1 indicates block placement and 0 indicates no block.
     */
    private static int[][] generateBinaryGrid(float[][] probabilities) {
        int rows = probabilities.length;
        int cols = probabilities[0].length;

        int[][] result = new int[rows][cols];
        Random random = new Random();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                float randomValue = random.nextFloat();
                result[i][j] = randomValue < probabilities[i][j] ? 1 : 0;
            }
        }

        return result;
    }

    /**
     * Creates a probability grid for block placement in the cloud.
     *
     * @return A 2D array of probabilities for block placement.
     */
    private float[][] getProbabilityGrid() {
        float[][] probabilities = new float[Constants.CLOUD_GRID_SIZE][Constants.CLOUD_GRID_SIZE];
        float center = Constants.CLOUD_GRID_SIZE / Constants.HALF;
        float denseRadius = center * Constants.CLOUD_DENSE_RADIUS;

        for (int row = 0; row < Constants.CLOUD_GRID_SIZE; row++) {
            for (int col = 0; col < Constants.CLOUD_GRID_SIZE; col++) {
                float distance = (float) Math.sqrt(Math.pow(row - center, Constants.POWER_OF_TWO)
                        + Math.pow(col - center, Constants.POWER_OF_TWO));

                // Fully dense within a certain radius from the center
                if (distance <= denseRadius) {
                    probabilities[row][col] = 1.0f;
                } else {
                    // Gradually decrease probability outside the dense radius
                    probabilities[row][col] = Math.max(0, 1f - ((distance - denseRadius) /
                            (center - denseRadius)));
                }
            }
        }

        return probabilities;
    }

    /**
     * Returns the center of the cloud's grid.
     *
     * @return A Vector2 representing the cloud's center position.
     */
    public Vector2 getCenter() {
        return blocks.get((Constants.CLOUD_GRID_SIZE / Constants.HALF) + 1).getCenter();
    }


}
