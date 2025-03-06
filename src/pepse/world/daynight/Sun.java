package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.world.Terrain;
import pepse.util.Constants;

import java.awt.*;

/**
 * A class responsible for creating and managing the Sun GameObject and its movement.
 */
public class Sun {


    /**
     * Creates a GameObject representing the sun and sets up its rotational movement.
     *
     * @param windowDimensions The dimensions of the game window.
     * @param cycleLength      The duration of a full rotation of the sun (day-night cycle).
     * @return The GameObject representing the sun.
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength){
        float initialPosY = windowDimensions.y() * (Constants.SUN_OFFSET_FACTOR_SCREEN);
        float initialPosX = windowDimensions.x() / Constants.HALF;

        Vector2 cycleCenter = new Vector2(initialPosX, initialPosY);

        Vector2 objectDimensions = new Vector2(Constants.SUN_SIZE, Constants.SUN_SIZE);
        Vector2 initialSunCenter = new Vector2(initialPosX, initialPosY - Constants.SUN_INIT_OFFSET);

        GameObject sun = new GameObject(initialSunCenter, objectDimensions,
                new OvalRenderable(Constants.SUN_COLOR));
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag("sun");

        new Transition<Float>(
                sun,
                (Float angle) -> sun.setCenter(initialSunCenter.subtract(cycleCenter)
                        .rotated(angle).add(cycleCenter)),
                Constants.SUN_INITIAL_ANGLE,
                Constants.SUN_FINAL_ANGLE,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                cycleLength,
                Transition.TransitionType.TRANSITION_LOOP,
                null
        );
        return sun;
    }
}