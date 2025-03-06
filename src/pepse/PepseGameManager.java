package pepse;

import danogl.GameManager;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.components.ScheduledTask;
import danogl.gui.*;
import danogl.GameObject;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;
import pepse.util.Constants;
import pepse.world.*;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Flora;
import pepse.world.weather.Cloud;
import pepse.world.weather.RaindropGenerator;

import java.util.List;
import java.util.Random;

/**
 * Main game manager for the game.
 * Handles initialization, game object management, and game updates.
 */
public class PepseGameManager extends GameManager implements GameObjectManager{

    /** Random number generator for procedural generation. */
    Random random = new Random();

    /** Seed for procedural generation, within predefined bounds. */
    private final int seed = random.nextInt(Constants.SEED_ORIGIN, Constants.SEED_BOUND);

    private ProceduralGenerationManager proceduralGenerationManager;
    private Vector2 windowDimensions;
    private UserInputListener inputListener;
    private ImageReader imageReader;
    private EnergyDisplay energyDisplay;
    private Terrain terrain;
    private Avatar avatar;
    private Cloud currentCloud;

    /** Creates the sky object and adds it to the background layer. */
    private void createSky(){
        GameObject sky = Sky.create(windowDimensions);
        gameObjects().addGameObject(sky, Layer.BACKGROUND);
    }

    /** Creates the night object and adds it to the foreground layer. */
    private void createNight(){
        gameObjects().addGameObject(Night.create(
                this.windowDimensions, Constants.SUN_CYCLE_LENGTH), Layer.UI);
    }

    /** Spawns a cloud, animates its movement, and handles cloud lifecycle. */
    private void spawnCloud(){
        this.currentCloud.create();
        List<Block> cloudBlocks = this.currentCloud.getBlocks();
        for (Block block : cloudBlocks) {
            gameObjects().addGameObject(block, Layer.BACKGROUND);
        }

        GameObject cloudOwner = new GameObject(Vector2.ZERO, Vector2.ZERO, null);
        gameObjects().addGameObject(cloudOwner, Layer.BACKGROUND);
        this.currentCloud.startTransition(cloudOwner, Constants.CLOUD_ANIMATION_DURATION,
                ()-> {
                    for(Block block : this.currentCloud.getBlocks()) {
                        removeGameObject(block, Layer.BACKGROUND);
                    }
                    removeGameObject(cloudOwner, Layer.BACKGROUND);
                    spawnCloud();
                }
                );
    }

    /** Creates the sun and sun halo objects and adds them to the background layer. */
    private void spawnSunWithHalo(){
        GameObject sun = Sun.create(this.windowDimensions, Constants.SUN_CYCLE_LENGTH);
        gameObjects().addGameObject(sun, Layer.BACKGROUND);
        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo, Layer.BACKGROUND);
    }

    /** Adds the energy display UI elements to the game. */
    private void addEnergyDisplay(){
        this.energyDisplay = new EnergyDisplay(Constants.ENERGY_DISPLAY_TOP_LEFT);
        Avatar.setEnergyCallback(energyDisplay);
        GameObject energyText = new GameObject(Vector2.ZERO, Constants.ENERGY_TEXT_DIMS,
                energyDisplay.getEnergyText());
        gameObjects().addGameObject(energyText, Layer.UI);
        energyText.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(energyDisplay.getEnergyBar(), Layer.UI);
    }

    /** Creates the avatar, sets up its position and camera, and adds it to the main layer. */
    private void createAvatar(){
        Vector2 avatarStartPos = new Vector2(0,(float) Math.floor(this.terrain.groundHeightAt(0)
                / Constants.BLOCK_SIZE) * Constants.BLOCK_SIZE - Constants.AVATAR_HEIGHT);
        Avatar avatar = new Avatar(avatarStartPos, this.inputListener, this.imageReader);
        this.avatar = avatar;
        setCamera(new Camera(avatar,
                Vector2.ZERO, //we chose to keep it as 0 because it looks better
                this.windowDimensions, this.windowDimensions));
        Jump jumpCallback = new Jump(this);
        avatar.setJumpCallback(jumpCallback);
        gameObjects().addGameObject(avatar, Constants.MAIN_LAYER);
        FruitRemoverCallback fruitRemover = new FruitRemoverCallback(this);
        Avatar.setEnergyCallback(fruitRemover);
    }

    /** Initializes the game, creates and configures all essential components. */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowDimensions = windowController.getWindowDimensions();
        this.inputListener = inputListener;
        this.imageReader = imageReader;
        windowController.setTargetFramerate(Constants.TARGET_FRAMERATE);

        Sound backgroundMusic = soundReader.readSound(Constants.BACKGROUND_MUSIC_PATH);
        backgroundMusic.playLooped();


        this.terrain = new Terrain(windowController.getWindowDimensions(), seed);
        Flora flora = new Flora(terrain, seed);

        createSky();
        createNight();
        spawnSunWithHalo();

        addEnergyDisplay();
        createAvatar();
        gameObjects().layers().shouldLayersCollide(Constants.MAIN_LAYER,
                Layer.STATIC_OBJECTS, true);
        Vector2 cameraPosition = avatar.getCenter().subtract(windowDimensions.mult(0.5f));
        this.currentCloud = new Cloud(this.windowDimensions, cameraPosition);
        spawnCloud();
        this.proceduralGenerationManager = new ProceduralGenerationManager(this.terrain, flora,
                this, this.avatar, windowController.getWindowDimensions());
    }

    /** Updates the game state, procedural generation, and energy display. */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        proceduralGenerationManager.update(deltaTime);

        double currentEnergy = avatar.getCurrentEnergy();
        energyDisplay.onEnergyChanged(currentEnergy);
    }

    /** Adds a game object to the specified layer. */
    @Override
    public void addGameObject(GameObject gameObject, int layer) {
        gameObjects().addGameObject(gameObject, layer);
    }

    /** Removes a game object from the specified layer. */
    @Override
    public void removeGameObject(GameObject gameObject, int layer) {
        gameObjects().removeGameObject(gameObject, layer);
    }

    /** Generates raindrops and schedules their removal after animation. */
    public void generateRaindrops() {
        RaindropGenerator raindropGenerator = new RaindropGenerator();
        List<GameObject> raindrops = raindropGenerator.generateGrid(this.currentCloud.getCenter());
        for (GameObject raindrop : raindrops) {
            new ScheduledTask(raindrop, Constants.RAINDROP_TRANSITION_TIME, false,
                    ()->gameObjects().removeGameObject(raindrop, Layer.BACKGROUND));
            gameObjects().addGameObject(raindrop, Layer.BACKGROUND);
        }
    }

    /** Main method to run the game. */
    public static void main(String[] args) {
        new PepseGameManager().run();
    }

}