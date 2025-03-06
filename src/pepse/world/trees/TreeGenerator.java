package pepse.world.trees;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.Constants;
import pepse.world.Terrain;

import java.awt.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * A class that handles the creation and management of trees in the simulation,
 * including trunks, leaves, and fruits.
 */
public class TreeGenerator {

    private int leafCanopyDimension;
    private Random seed = new Random();
    /**
     * Creates a tree trunk at a specified position.
     *
     * @param position The position of the trunk's base.
     * @return A GameObject representing the trunk.
     */
    private GameObject createTrunk(Vector2 position) {
        int heightOfTrunk = this.seed.nextInt(Constants.MIN_TRUNK_SIZE, Constants.MAX_TRUNK_SIZE)
                * Constants.TRUNK_ELEMENT_SIZE;
        RectangleRenderable trunkRenderable = new RectangleRenderable(ColorSupplier.approximateColor(
                Constants.TREE_TRUNK_COLOR, 4));
        Vector2 trunkDims = new Vector2(Constants.TRUNK_ELEMENT_SIZE, heightOfTrunk);
        return new GameObject(position, trunkDims, trunkRenderable);

    }

    /**
     * Creates a single leaf at a specified position.
     *
     * @param position The position of the leaf.
     * @return A GameObject representing the leaf.
     */
    private GameObject createLeaf(Vector2 position) {
        RectangleRenderable leafRenderer =
                new RectangleRenderable(ColorSupplier.chooseRandomColor(Constants.LEAF_COLORS));
        Vector2 leafDims = new Vector2(Constants.LEAF_DIMENSION, Constants.LEAF_DIMENSION);

        return new GameObject(position, leafDims, leafRenderer);
    }


    /**
     * Creates a transition to oscillate a GameObject's angle.
     *
     * @param object    The GameObject to apply the transition to.
     * @param delayTime Delay before starting the transition.
     * @return The created Transition.
     */
    private Transition createAngleTransition(GameObject object, float delayTime){
        return new Transition<>(
                object,
                (Float angle) -> object.renderer().setRenderableAngle(angle),
                Constants.INITIAL_LEAF_ANGLE,
                Constants.FINAL_LEAF_ANGLE,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                Constants.TRANSITION_TIME,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null
        );
    }

    /**
     * Creates a transition to oscillate an object's width.
     *
     * @param object    The GameObject to apply the transition to.
     * @param delayTime Delay before starting the transition.
     * @return The created Transition.
     */
    private Transition createStretchTransition(GameObject object, float delayTime){
        return new Transition<Float>(
                object,
                (Float width) -> object.setDimensions(new Vector2(width, Constants.LEAF_DIMENSION)),
                (float) Constants.LEAF_DIMENSION,
                (float) Constants.LEAF_DIMENSION + (float) Constants.ADDED_DIMENSION_LEAF,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                Constants.TRANSITION_TIME,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null
        );
    }

    /**
     * Creates a transition to oscillate an object's position vertically.
     *
     * @param object    The GameObject to apply the transition to.
     * @param delayTime Delay before starting the transition.
     * @return The created Transition.
     */
    private Transition createPositionTransition(GameObject object, float delayTime){
        return new Transition<>(
                object,
                (Vector2 pos) -> object.setTopLeftCorner(pos),
                object.getTopLeftCorner(),
                object.getTopLeftCorner().add(new Vector2(0, Constants.POSITION_TRANSITION_Y_OFFSET)),
                Transition.LINEAR_INTERPOLATOR_VECTOR,
                Constants.TRANSITION_TIME,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null
        );
    }

    /**
     * Creates a leaf or fruit with transitions and delays.
     *
     * @param object   The GameObject to modify.
     * @param delayTime Delay before starting transitions.
     * @param isLeaf   Whether the object is a leaf (true) or fruit (false).
     * @return The modified GameObject.
     */
    private GameObject makeObjectWithDelay(GameObject object, float delayTime, boolean isLeaf) {
            Runnable angleTransition = () -> {
                createAngleTransition(object, delayTime);
            };

            Runnable stretchTransition = () -> {
                createStretchTransition(object, delayTime);
            };

        Runnable positionTransition = () -> {
            createPositionTransition(object, delayTime);
        };

        Runnable createTransitions = () -> {
            if (isLeaf) {
                stretchTransition.run();
            } else {
                positionTransition.run(); // Add position transition for fruits
            }
            angleTransition.run();
        };

        new ScheduledTask(
                object,
                delayTime,
                false,
                createTransitions
        );
        return object;
    }

    /**
     * Creates a fruit at the specified position.
     *
     * @param position The position of the fruit.
     * @return A GameObject representing the fruit.
     */
    private GameObject createFruit(Vector2 position) {
        OvalRenderable leafRenderer = new OvalRenderable(Color.RED);
        Vector2 fruitDims = new Vector2(Constants.FRUIT_DIMENSION, Constants.FRUIT_DIMENSION);
        return new GameObject(position, fruitDims, leafRenderer);
    }

    /**
     * Assigns leaves and fruits to positions in a canopy grid.
     *
     * @param canopySize The size of the canopy grid.
     * @return A 2D array representing the canopy grid.
     */
    private int[][] assignLeavesAndFruits(int canopySize) {
        int gridSize = canopySize / Constants.LEAF_DIMENSION;
        int[][] canopyGrid = new int[gridSize][gridSize];

        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                if (seed.nextDouble() < Constants.LEAF_SUCCESS_RATE) {
                    canopyGrid[x][y] = seed.nextDouble()
                            < Constants.FRUIT_PROBABILITY ? Constants.FRUITANDLEAFCELL : Constants.LEAFCELL;
                    // Leaf with or without fruit
                } else if (seed.nextDouble() < Constants.FRUIT_PROBABILITY) {
                    canopyGrid[x][y] = Constants.FRUITCELL; // Fruit only
                }
            }
        }
        return canopyGrid;
    }

    /**
     * Generates leaves and fruits for a given tree trunk.
     *
     * @param trunk      The trunk GameObject.
     * @param canopySize The size of the canopy grid.
     * @return A list containing two lists: leaves and fruits.
     */
    private List<List<GameObject>> generateLeavesAndFruits(GameObject trunk, int canopySize) {
        List<GameObject> leaves = new ArrayList<>();
        List<GameObject> fruits = new ArrayList<>();
        Vector2 centerTopLeft = new Vector2(trunk.getCenter().x(), trunk.getTopLeftCorner().y());

        int[][] canopyGrid = assignLeavesAndFruits(canopySize);
        Vector2 startPosition = centerTopLeft.subtract(new Vector2((canopySize )  / (float)Constants.HALF,
                (canopySize )  / (float)Constants.HALF));

        for (int x = 0; x < canopyGrid.length; x++) {
            for (int y = 0; y < canopyGrid[x].length; y++) {
                Vector2 position = startPosition.add(new Vector2(x * Constants.LEAF_DIMENSION,
                        y * Constants.LEAF_DIMENSION));
                int cellValue = canopyGrid[x][y];

                if (cellValue == Constants.LEAFCELL || cellValue == Constants.FRUITANDLEAFCELL) {
                    GameObject leaf = createLeaf(position);
                    leaves.add(makeObjectWithDelay(leaf, Constants.TRANSITION_TIME * seed.nextFloat(), true));
                }
                if (cellValue == Constants.FRUITCELL || cellValue == Constants.FRUITANDLEAFCELL) {
                    GameObject fruit = createFruit(position);
                    fruit.setTag("fruit");
                    fruits.add(makeObjectWithDelay(fruit,
                            Constants.TRANSITION_TIME * seed.nextFloat(), false));
                }
            }
        }
        return List.of(leaves, fruits);
    }

    /**
     * Creates a tree consisting of a trunk, leaves, and fruits.
     *
     * @param position The position of the tree.
     * @return A Tree object representing the entire tree.
     */
    public Tree createTree(Vector2 position) {
        float treeX = Math.round(position.x() / Constants.BLOCK_SIZE) * Constants.BLOCK_SIZE;

        float groundHeight = (float) Math.ceil(position.y() / Constants.BLOCK_SIZE) * Constants.BLOCK_SIZE;

        Vector2 adjustedPosition = new Vector2(treeX, groundHeight);
        GameObject trunk = createTrunk(adjustedPosition);
        trunk.physics().preventIntersectionsFromDirection(Vector2.ZERO);
        trunk.physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);

        float trunkHeight = trunk.getDimensions().y();
        trunk.setTopLeftCorner(trunk.getTopLeftCorner().add(new Vector2(0, -trunkHeight)));
        // The leaves' canopy size is based on the height of the trunk
        int leafCanopyDimension = (int) (trunk.getDimensions().y() / Constants.TRUNK_ELEMENT_SIZE)
                * Constants.CANOPY_MULT_FACTOR;
        List<List<GameObject>> leavesAndFruits = generateLeavesAndFruits(trunk, leafCanopyDimension);
        return new Tree(trunk, leavesAndFruits.get(0), leavesAndFruits.get(1));
    }

}
