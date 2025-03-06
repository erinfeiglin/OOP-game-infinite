package pepse.world;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.*;
import danogl.util.Vector2;
import pepse.util.Constants;

import java.awt.*;

/**
 * A class that displays the avatar's energy level using a text indicator and a color-coded energy bar.
 * Implements the EnergyCallback interface to react to energy-related events.
 */
public class EnergyDisplay implements EnergyCallback{


    private TextRenderable energyText;  // To display the energy
    private GameObject energyBar;
    private Vector2 position;
    private final RectangleRenderable highEnergy;
    private final RectangleRenderable lowEnergy;
    private final RectangleRenderable veryLowEnergy;

    /**
     * Constructor for the EnergyDisplay class.
     *
     * @param position The position of the energy display on the screen.
     */
    public EnergyDisplay(Vector2 position) {
        this.position = position;

        this.energyText = new TextRenderable(Constants.INITIAL_ENERGY_TEXT);
        this.energyText.setColor(Color.WHITE);

        this.highEnergy = new RectangleRenderable(Constants.HIGH_ENERGY_COLOR);
        this.lowEnergy = new RectangleRenderable(Constants.MID_ENERGY_COLOR);
        this.veryLowEnergy = new RectangleRenderable(Constants.LOW_ENERGY_COLOR);

        this.energyBar = new GameObject(position, new Vector2(Constants.MAX_ENERGY_BAR_WIDTH,
                Constants.ENERGY_BAR_HEIGHT), this.highEnergy);
        this.energyBar.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
    }

    /**
     * Reacts to changes in energy level by updating the text and resizing/coloring the energy bar.
     *
     * @param currentEnergy The current energy level of the avatar.
     */
    @Override
    public void onEnergyChanged(double currentEnergy) {
        this.changeColor(currentEnergy);

        // Update the displayed text based on the new energy value
        energyText.setString("Energy: " + (int) currentEnergy);  // Convert to int for display

        float barWidth = (float) (Constants.MAX_ENERGY_BAR_WIDTH * currentEnergy
                / Constants.FULL_ENERGY_BAR_PERCENTAGE);
        energyBar.setDimensions(new Vector2(barWidth, Constants.ENERGY_BAR_HEIGHT));
        energyBar.setTopLeftCorner(this.position);
    }

    /**
     * Reacts to a fruit consumption event. No specific action is taken.
     *
     * @param fruit The GameObject representing the consumed fruit.
     */
    @Override
    public void onFruitConsumed(GameObject fruit) {
        return;
    }

    /**
     * Changes the color of the energy bar based on the current energy level.
     *
     * @param currentEnergy The current energy level of the avatar.
     */
    private void changeColor(double currentEnergy){
        if (currentEnergy <= Constants.VERY_LOW_ENERGY_LEVEL){
            this.energyBar.renderer().setRenderable(this.veryLowEnergy);
        }
        else if (currentEnergy <= Constants.LOW_ENERGY_LEVEL){
            this.energyBar.renderer().setRenderable(this.lowEnergy);
        }
        else{
            this.energyBar.renderer().setRenderable(this.highEnergy);
        }
    }

    /**
     * Returns the TextRenderable object for the energy text display.
     *
     * @return The TextRenderable displaying the energy level.
     */
    public TextRenderable getEnergyText() {
        return energyText;
    }

    /**
     * Returns the GameObject representing the energy bar.
     *
     * @return The GameObject for the energy bar.
     */
    public GameObject getEnergyBar(){
        return this.energyBar;
    }
}
