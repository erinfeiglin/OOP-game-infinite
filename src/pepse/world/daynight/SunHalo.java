package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.util.Constants;


import java.awt.*;

/**
 * A class responsible for creating and managing the halo effect around the sun.
 */
public class SunHalo {


    /**
     * Creates a halo for the sun and ensures it follows the sun's position each frame.
     *
     * @param sun The GameObject representing the sun.
     * @return A GameObject representing the sun's halo.
     */
    public static GameObject create(GameObject sun){
        Vector2 sunDimensions = sun.getDimensions();
        Vector2 sunCenter = sun.getCenter();

        Vector2 haloDimensions = sunDimensions.mult(Constants.HALO_MULTIPLICATION_FACTOR);

        GameObject sunHalo = new GameObject(sun.getTopLeftCorner(),
                haloDimensions, new OvalRenderable(Constants.HALO_COLOR));
        sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

        sunHalo.addComponent(deltaTime -> {
            sunHalo.setCenter(sun.getCenter());
        });
        return sunHalo;
    }
}
