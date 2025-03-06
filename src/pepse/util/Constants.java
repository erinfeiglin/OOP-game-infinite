package pepse.util;

import danogl.util.Vector2;
import pepse.world.AvatarJumpCallback;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * This class contains constant values used across the game.
 * These constants are grouped by their functionality (e.g., Night, Sun, Flora, etc.)
 */
public class Constants {

    // General Constants
    /**
     * The size of a single block in the game.
     */
    public static final int BLOCK_SIZE = 30;
    /**
     * A utility constant representing half (2).
     */
    public static final int HALF = 2;
    /**
     * Utility constant for powers of two.
     */
    public static final int POWER_OF_TWO = 2;

    //Night
    /**
     * The opacity of the night during midnight.
     */
    public static final float MIDNIGHT_OPACITY = 0.5f;
    /**
     * The initial value for transitions.
     */
    public static final float INITIAL_TRANSITION_VALUE = 0f;

    // Halo
    /**
     * Multiplier for the size of the halo.
     */
    public static final float HALO_MULTIPLICATION_FACTOR = 1.5f;
    /**
     * The color of the halo.
     */
    public static final Color HALO_COLOR = new Color(255, 255, 0, 20);

    // Sun
    /**
     * The initial offset of the sun.
     */
    public static final int SUN_INIT_OFFSET = 200;
    /**
     * The initial angle of the sun.
     */
    public static final float SUN_INITIAL_ANGLE = 0f;
    /**
     * The final angle of the sun's movement.
     */
    public static final float SUN_FINAL_ANGLE = 360f;
    /**
     * The color of the sun.
     */
    public static final Color SUN_COLOR = Color.YELLOW;
    /**
     * The size of the sun.
     */
    public static final int SUN_SIZE = 60;
    /**
     * The factor by which the sun's position offsets the screen height.
     */
    public static final float SUN_OFFSET_FACTOR_SCREEN = 2.0f / 3.0f;
    /**
     * Duration of the sun's cycle in seconds.
     */
    public static final float SUN_CYCLE_LENGTH = 50f;

    // Flora
    /**
     * Amplitude offset for sine wave used in flora generation.
     */
    public static final double SIN_AMPLITUDE_OFFSET = 0.5;
    /**
     * Noise factor for sine wave in flora generation.
     */
    public static final double SIN_NOISE_FACTOR = 1.0;
    /**
     * The number of samples used for smoothing terrain noise.
     */
    public static final int SAMPLES_FOR_SMOOTHING = 5;
    /**
     * Periodicity of the sine wave for flora generation.
     */
    public static final double FLORA_SIN_PERIODICITY = 0.1;
    /**
     * Amplitude of the sine wave for flora generation.
     */
    public static final int FLORA_SINE_AMPLITUDE = 3;
    /**
     * Threshold for tree spawning in the flora generation process.
     */
    public static final double TREE_SPAWN_THRESHOLD = 0.5f;
    /**
     * Noise factor for flora generation.
     */
    public static final double FLORA_NOISE_FACTOR = 2f;


    // Tree Generator
    /**
     * Probability of leaf creation during tree generation.
     */
    public static final double LEAF_SUCCESS_RATE = 0.6;
    /**
     * Multiplier for tree canopy size.
     */
    public static final int CANOPY_MULT_FACTOR = 20;
    /**
     * The color of the tree trunk.
     */
    public static final Color TREE_TRUNK_COLOR = new Color(101, 67, 33);
    /**
     * The size of each element in the tree trunk.
     */
    public static final int TRUNK_ELEMENT_SIZE = 30;
    /**
     * Minimum height of a tree trunk.
     */
    public static final int MIN_TRUNK_SIZE = 4;
    /**
     * Maximum height of a tree trunk.
     */
    public static final int MAX_TRUNK_SIZE = 7;

    // Leaf
    /**
     * Dimensions of a leaf.
     */
    public static final int LEAF_DIMENSION = 10;
    /**
     * Initial angle for leaf animation.
     */
    public static final float INITIAL_LEAF_ANGLE = -25f;
    /**
     * Final angle for leaf animation.
     */
    public static final float FINAL_LEAF_ANGLE = 25f;
    /**
     * Duration of leaf animation transition.
     */
    public static final float TRANSITION_TIME = 2f;
    /**
     * Additional dimension added to a leaf's size.
     */
    public static final int ADDED_DIMENSION_LEAF = 10;

    /**
     * The different possible colors a leaf is able to be generated as
     */
    private static final Color LEAF_COLOR_1 = new Color(50, 200, 30);
    private static final Color LEAF_COLOR_2 = new Color(11, 168, 54);
    private static final Color LEAF_COLOR_3 = new Color(9, 119, 40);
    private static final Color LEAF_COLOR_4 = new Color(9, 101, 32);
    /**
     * The array of different possible colors a leaf is able to be generated as
     */
    public static final Color[] LEAF_COLORS = {LEAF_COLOR_1, LEAF_COLOR_2, LEAF_COLOR_3, LEAF_COLOR_4};

    // Fruit
    /**
     * Dimensions of a fruit.
     */
    public static final int FRUIT_DIMENSION = 8;
    /**
     * Probability of fruit generation.
     */
    public static final double FRUIT_PROBABILITY = 0.05;

    /**
     * Value given to a cell to mark it as a leaf
     */
    public static final int LEAFCELL = 1;
    /**
     * Value given to a cell to mark it as a fruit
     */
    public static final int FRUITCELL = 2;
    /**
     * Value given to a cell to mark it as a leaf and a fruit
     */
    public static final int FRUITANDLEAFCELL = 3;
    /**
     * Offset of the y value in the position transition
     */
    public static final int POSITION_TRANSITION_Y_OFFSET = 2;

    // Cloud
    /**
     * Radius for dense areas of the cloud.
     */
    public static final float CLOUD_DENSE_RADIUS = 0.5f;
    /**
     * Dimensions of the cloud grid.
     */
    public static final int CLOUD_GRID_SIZE = 6;
    /**
     * Base color of the clouds.
     */
    public static final Color BASE_CLOUD_COLOR = new Color(255, 255, 255);
    /**
     * Factor to determine cloud's Y-axis dimensions relative to the screen.
     */
    public static final float CLOUD_Y_DIMENSIONS_FACTOR = (float) (1.0 / 8.0);
    /**
     * Duration of the cloud animation in seconds.
     */
    public static final float CLOUD_ANIMATION_DURATION = 30f;

    // Raindrop
    /**
     * Duration of the transition for a raindrop animation.
     */
    public static final float RAINDROP_TRANSITION_TIME = 0.8f;
    /**
     * Probability of a raindrop appearing.
     */
    public static final float RAINDROP_SUCCESS_RATE = 0.2f;
    /**
     * Height of a raindrop.
     */
    public static final int RAINDROP_HEIGHT = 10;
    /**
     * Width of a raindrop.
     */
    public static final int RAINDROP_WIDTH = 5;
    /**
     * Dimensions of the raindrop grid.
     */
    public static final int RAINDROP_GRID_SIZE = 6;
    /**
     * Gravity applied to raindrops.
     */
    public static final int RAINDROP_GRAVITY = 300;
    /**
     * The initial opaqueness of the raindrops
     */
    public static final float RAINDROP_INIT_OPAQUENESS = 1f;
    /**
     * The final opaqueness of the raindrops
     */
    public static final float RAINDROP_FINAL_OPAQUENESS = 0f;
    /**
     * The color of the raindrops
     */
    public static final Color RAINDROP_COLOR = Color.CYAN;


    //Avatar
    /**
     * A normal to differentiate collisions with the top of objects from other collisions.
     */
    public static final float NORMAL_FOR_COLLISION = -0.5f;

    /**
     * Time interval between idle animation clips.
     */
    public static final float TIME_BETWEEN_IDLE_CLIPS = 0.3f;
    /**
     * Time interval between running animation clips.
     */
    public static final float TIME_BETWEEN_RUNNING_CLIPS = 0.2f;
    /**
     * Time interval between jumping animation clips.
     */
    public static final float TIME_BETWEEN_JUMPING_CLIPS = 0.5f;
    /**
     * Mass of the avatar.
     */
    public static final float AVATAR_MASS = 100f;
    /**
     * Minimum energy required for running.
     */
    public static final float MIN_REQUIRED_ENERGY_RUNNING = 0.5f;
    /**
     * Minimum energy required for jumping.
     */
    public static final float MIN_REQUIRED_ENERGY_JUMPING = 10f;
    /**
     * Maximum energy level of the avatar.
     */
    public static final int MAX_ENERGY = 100;
    /**
     * Movement speed of the avatar.
     */
    public static final float AVATAR_MOVEMENT_SPEED = 130f;
    /**
     * Jump speed of the avatar.
     */
    public static final float AVATAR_JUMP_SPEED = 250f;
    /**
     * Gravity affecting the avatar.
     */
    public static final float AVATAR_GRAVITY = 250f;

    /**
     * Width of the avatar.
     */
    public static final float AVATAR_WIDTH = 40f;
    /**
     * Height of the avatar.
     */
    public static final float AVATAR_HEIGHT = 53f;

    /**
     * Key to move the avatar left.
     */
    public static final int MOVE_KEY_LEFT = KeyEvent.VK_LEFT;
    /**
     * Key to move the avatar right.
     */
    public static final int MOVE_KEY_RIGHT = KeyEvent.VK_RIGHT;
    /**
     * Key to make the avatar jump.
     */
    public static final int MOVE_KEY_UP = KeyEvent.VK_SPACE;

    /**
     * Path to the first idle animation image for the avatar.
     */
    public static final String IMAGE_RENDERABLE_PATH_IDLE_0 = "assets/idle_0.png";
    /**
     * Path to the second idle animation image for the avatar.
     */
    public static final String IMAGE_RENDERABLE_PATH_IDLE_1 = "assets/idle_1.png";
    /**
     * Path to the third idle animation image for the avatar.
     */
    public static final String IMAGE_RENDERABLE_PATH_IDLE_2 = "assets/idle_2.png";
    /**
     * Path to the fourth idle animation image for the avatar.
     */
    public static final String IMAGE_RENDERABLE_PATH_IDLE_3 = "assets/idle_3.png";
    /**
     * Path to the fifth idle animation image for the avatar
     */
    public static final String IMAGE_RENDERABLE_PATH_IDLE_4 = "assets/idle_4.png";
    /**
     * Path to the sixth idle animation image for the avatar
     */
    public static final String IMAGE_RENDERABLE_PATH_IDLE_5 = "assets/idle_5.png";

    /**
     * Path to the first running animation image for the avatar.
     */
    public static final String IMAGE_RENDERABLE_PATH_RUN_0 = "assets/run_0.png";
    /**
     * Path to the second running animation image for the avatar.
     */
    public static final String IMAGE_RENDERABLE_PATH_RUN_1 = "assets/run_1.png";
    /**
     * Path to the third running animation image for the avatar.
     */
    public static final String IMAGE_RENDERABLE_PATH_RUN_2 = "assets/run_2.png";
    /**
     * Path to the fourth running animation image for the avatar.
     */
    public static final String IMAGE_RENDERABLE_PATH_RUN_3 = "assets/run_3.png";
    /**
     * Path to the fifth running animation image for the avatar.
     */
    public static final String IMAGE_RENDERABLE_PATH_RUN_4 = "assets/run_4.png";
    /**
     * Path to the sixth running animation image for the avatar.
     */
    public static final String IMAGE_RENDERABLE_PATH_RUN_5 = "assets/run_5.png";
    /**
     * Path to the seventh running animation image for the avatar.
     */
    public static final String IMAGE_RENDERABLE_PATH_RUN_6 = "assets/run_6.png";
    /**
     * Path to the eighth running animation image for the avatar.
     */
    public static final String IMAGE_RENDERABLE_PATH_RUN_7 = "assets/run_7.png";


    /**
     * Path to the first jumping animation image for the avatar.
     */
    public static final String IMAGE_RENDERABLE_PATH_JUMP_0 = "assets/jump_0.png";
    /**
     * Path to the second jumping animation image for the avatar.
     */
    public static final String IMAGE_RENDERABLE_PATH_JUMP_1 = "assets/jump_1.png";
    /**
     * Path to the third jumping animation image for the avatar.
     */
    public static final String IMAGE_RENDERABLE_PATH_JUMP_2 = "assets/jump_2.png";
    /**
     * Path to the fourth jumping animation image for the avatar.
     */
    public static final String IMAGE_RENDERABLE_PATH_JUMP_3 = "assets/jump_3.png";
    /**
     * Path to the fourth jumping animation image for the avatar.
     */
    public static final String IMAGE_RENDERABLE_PATH_JUMP_4 = "assets/jump_4.png";
    /**
     * Path to the sixth jumping animation image for the avatar.
     */
    public static final String IMAGE_RENDERABLE_PATH_JUMP_5 = "assets/jump_5.png";
    /**
     * Path to the seventh jumping animation image for the avatar.
     */
    public static final String IMAGE_RENDERABLE_PATH_JUMP_6 = "assets/jump_6.png";
    /**
     * Path to the eighth jumping animation image for the avatar.
     */
    public static final String IMAGE_RENDERABLE_PATH_JUMP_7 = "assets/jump_7.png";


    /**
     * path to the background music file
     */
    public static final String BACKGROUND_MUSIC_PATH = "assets/background_music.wav";

    // Energy Bar
    /**
     * Very low energy level threshold.
     */
    public static final int VERY_LOW_ENERGY_LEVEL = 20;
    /**
     * Low energy level threshold.
     */
    public static final int LOW_ENERGY_LEVEL = 60;
    /**
     * Percentage of a full energy bar.
     */
    public static final int FULL_ENERGY_BAR_PERCENTAGE = 100;
    /**
     * Maximum width of the energy bar.
     */
    public static final float MAX_ENERGY_BAR_WIDTH = 200f;
    /**
     * Height of the energy bar.
     */
    public static final float ENERGY_BAR_HEIGHT = 20f;
    /**
     * Top-left position of the energy display on the screen.
     */
    public static final Vector2 ENERGY_DISPLAY_TOP_LEFT = new Vector2(50, 50);
    /**
     * Dimensions of the energy text display.
     */
    public static final Vector2 ENERGY_TEXT_DIMS = new Vector2(30, 30);
    /**
     * Initial energy text message (full energy)
     */
    public static final String INITIAL_ENERGY_TEXT = "Energy: 100";
    /**
     * Color of the energy bar with high energy
     */
    public static final Color HIGH_ENERGY_COLOR = Color.GREEN;
    /**
     * Color of the energy bar with mid level energy
     */
    public static final Color MID_ENERGY_COLOR = Color.YELLOW;
    /**
     * Color of the energy bar with low energy
     */
    public static final Color LOW_ENERGY_COLOR = Color.RED;

    // Energy Handler
    /**
     * Energy created and gained when idle.
     */
    public static final int CREATED_ENERGY = 1;
    /**
     * Energy lost when running.
     */
    public static final double LOST_ENERGY_RUNNING = 0.5;
    /**
     * Energy lost when jumping.
     */
    public static final double LOST_ENERGY_JUMPING = 10;
    /**
     * Energy gained from eating a fruit.
     */
    public static final double ADDED_FRUIT_ENERGY = 10;

    // Sky
    /**
     * Basic color of the sky.
     */
    public static final Color BASIC_SKY_COLOR = new Color(163, 201, 217);

    // Terrain
    /**
     * Depth of the terrain in blocks.
     */
    public static final int TERRAIN_DEPTH = 20;
    /**
     * Noise factor for generating the base terrain structure.
     */
    public static final int TERRAIN_BASE_NOISE_FACTOR = 8;
    /**
     * Multiplier applied to base terrain noise values.
     */
    public static final float TERRAIN_BASE_NOISE_MULTIPLIER = 0.5f;
    /**
     * Noise factor for generating fine terrain details.
     */
    public static final int TERRAIN_FINE_NOISE_FACTOR = 20;
    /**
     * Multiplier applied to fine terrain noise values.
     */
    public static final float TERRAIN_FINE_NOISE_MULTIPLIER = 0.2f;
    /**
     * Noise factor for X-axis terrain variations.
     */
    public static final int TERRAIN_X_NOISE_FACTOR = 2;

    /**
     * The different possible colors a block in the terrain is able to be generated as
     */
    private static final Color BASE_GROUND_COLOR_1 = new Color(242, 242, 242);
    private static final Color BASE_GROUND_COLOR_2 = new Color(255, 250, 250);
    private static final Color BASE_GROUND_COLOR_3 = new Color(193, 210, 217);
    private static final Color BASE_GROUND_COLOR_4 = new Color(114, 151, 166);
    private static final Color BASE_GROUND_COLOR_5 = new Color(92, 108, 115);
    /**
     * The array of the different possible colors a block in the terrain is able to be generated as
     */
    public static final Color[] GROUND_COLORS = {BASE_GROUND_COLOR_1,
            BASE_GROUND_COLOR_2, BASE_GROUND_COLOR_3, BASE_GROUND_COLOR_4, BASE_GROUND_COLOR_5};

    // Game Manager
    /**
     * Origin of the seed range for random generation.
     */
    public static final int SEED_ORIGIN = 0;
    /**
     * Upper bound of the seed range for random generation.
     */
    public static final int SEED_BOUND = 255;
    /**
     * Main layer of the game elements.
     */
    public static final int MAIN_LAYER = 50;
    /**
     * Target framerate for the game.
     */
    public static final int TARGET_FRAMERATE = 30;

    /**
     * the size of each chunk for the procedural generator.
     */
    public static final int CHUNK_WIDTH = 10;

}
