package pepse.world.weather;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.Constants;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A class that generates raindrops in a grid pattern with animations and physics.
 */
public class RaindropGenerator {

    private final Random rand = new Random();

    /**
     * Generates a grid of raindrops around a specified center point.
     *
     * @param center The center point for generating the raindrop grid.
     * @return A list of GameObjects representing the raindrops.
     */
    public List<GameObject> generateGrid(Vector2 center) {
        List<GameObject> rainGrid = new ArrayList<>();
        Vector2 dimensions = new Vector2(Constants.RAINDROP_WIDTH, Constants.RAINDROP_HEIGHT);
        for (int x = 0; x < Constants.RAINDROP_GRID_SIZE; x++) {
            for (int y = 0; y < Constants.RAINDROP_GRID_SIZE; y++) {
                if (rand.nextDouble() < Constants.RAINDROP_SUCCESS_RATE) {
                    RectangleRenderable raindropRenderable =
                            new RectangleRenderable(Constants.RAINDROP_COLOR);
                    Vector2 offset = new Vector2(
                            (x - Constants.RAINDROP_GRID_SIZE/ Constants.HALF) * Constants.RAINDROP_WIDTH,
                            (y + Constants.RAINDROP_GRID_SIZE) * Constants.RAINDROP_HEIGHT);
                    Vector2 position = center.add(offset);
                    GameObject raindrop = new GameObject(position, dimensions, raindropRenderable);
                    raindrop.setVelocity(new Vector2(x, y + Constants.RAINDROP_GRAVITY));
                    raindrop.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
                    createRaindropTransition(raindrop);
                    rainGrid.add(raindrop);
                }
            }
        }
        return rainGrid;

    }

    /**
     * Creates a transition to fade out the raindrop as it falls (change the opacity).
     *
     * @param raindrop The GameObject representing the raindrop.
     */
    private void createRaindropTransition(GameObject raindrop) {
         new Transition<>(raindrop,
                (Float value)->raindrop.renderer().setOpaqueness(value),
                 Constants.RAINDROP_INIT_OPAQUENESS, Constants.RAINDROP_FINAL_OPAQUENESS,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                Constants.RAINDROP_TRANSITION_TIME,
                Transition.TransitionType.TRANSITION_ONCE,
                null);
        }
    }
