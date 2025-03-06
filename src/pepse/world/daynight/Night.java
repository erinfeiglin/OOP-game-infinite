package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.Constants;

import java.awt.*;
/**
 * Responsible for creating and managing the night cycle effect.
 */
public class Night {

    /**
     * Creates a GameObject representing the night cycle effect.
     * The object's opaqueness transitions periodically to simulate the night and day cycle.
     *
     * @param windowDimensions The dimensions of the game window.
     * @param cycleLength      The length of a full day-night cycle in seconds.
     * @return A GameObject representing the night cycle.
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength){
        RectangleRenderable blackRectangle = new RectangleRenderable(Color.BLACK);

        GameObject night = new GameObject(Vector2.ZERO, windowDimensions, blackRectangle);
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        night.setTag("night");
        new Transition<Float>(
                night, // the game object being changed
                night.renderer()::setOpaqueness, // the method to call
                Constants.INITIAL_TRANSITION_VALUE, // initial transition value
                Constants.MIDNIGHT_OPACITY, // final transition value
                Transition.CUBIC_INTERPOLATOR_FLOAT,// use a cubic interpolator
                cycleLength / Constants.HALF, // transition fully over half a day
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, // Choose appropriate ENUM value
                 null // nothing further to execute upon reaching final value
                );
        return night;
    }

}
