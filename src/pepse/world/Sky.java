package pepse.world;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.Constants;

import java.awt.*;

/**
 * A class to create the sky for the simulation, which serves as the background.
 */
public class Sky {


    /**
     * Creates a GameObject representing the sky, covering the entire window size.
     *
     * @param windowDimensions A vector representing the size of the game window.
     * @return A GameObject representing the sky.
     */
    public static GameObject create(Vector2 windowDimensions){
        GameObject sky = new GameObject(
                Vector2.ZERO, windowDimensions,
                new RectangleRenderable(Constants.BASIC_SKY_COLOR));
        sky.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sky.setTag("sky");

        return sky;
    }

}
