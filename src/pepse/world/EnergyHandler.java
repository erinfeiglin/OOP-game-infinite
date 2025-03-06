package pepse.world;

import pepse.util.Constants;

/**
 * Class that manages the energy level of the avatar, providing methods for energy changes in different modes.
 */
public class EnergyHandler {

    private double energy;
    /**
     * Constructor for the EnergyHandler class.
     * Initializes the energy level to the maximum value.
     */
    public EnergyHandler() {
        this.energy = Constants.MAX_ENERGY;
    }
    /**
     * Increases energy while in idle mode by a predefined amount.
     * Ensures energy does not exceed the maximum allowed value.
     *
     * @return The updated energy level.
     */
    public double idleMode() {
        this.energy += Constants.CREATED_ENERGY;
        if (this.energy > Constants.MAX_ENERGY) {
            this.energy = Constants.MAX_ENERGY;
        }
        return this.energy;
    }

    /**
     * Decreases energy when the avatar jumps.
     *
     * @return The updated energy level.
     */
    public double jumpMode() {
        this.energy -= Constants.LOST_ENERGY_JUMPING;
        return this.energy;
    }

    /**
     * Decreases energy when the avatar runs.
     *
     * @return The updated energy level.
     */
    public double runMode() {
        this.energy -= Constants.LOST_ENERGY_RUNNING;
        return this.energy;
    }
    /**
     * Returns the current energy level.
     *
     * @return The current energy level.
     */
    public double getEnergy() {
        return this.energy;
    }
    /**
     * Increases energy when a fruit is consumed.
     * Ensures energy does not exceed the maximum allowed value.
     *
     * @return The updated energy level.
     */
    public double ateFruit() {
        this.energy += Constants.ADDED_FRUIT_ENERGY;
        if (this.energy > Constants.MAX_ENERGY) {
            this.energy = Constants.MAX_ENERGY;
        }
        return this.energy;
    }
}
