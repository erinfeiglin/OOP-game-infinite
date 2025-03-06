package pepse.world;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.Constants;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * A class that represents the main character (avatar) in the simulation, controlled by user input.
 * Includes movement, jumping, animations, energy management, and collision handling.
 */
public class Avatar extends GameObject {

    private AvatarJumpCallback jumpCallback;
    private EnergyHandler energyHandler;
    private static EnergyCallback energyCallback;

    private Vector2 topLeftCorner;
    private UserInputListener inputListener;


    private int objectsUnderFeetCount = 0;
    private boolean airborne = false;
    private final Set<GameObject> objectsBelowFeetSet = new HashSet<>();

    private final Renderable[] idleClips;
    private final Renderable[] runClips;
    private final Renderable[] jumpClips;

    private AnimationRenderable idleAnimation;
    private AnimationRenderable runAnimation;
    private AnimationRenderable jumpAnimation;

    private enum AnimationState { IDLE, RUNNING, JUMPING }
    private AnimationState currentAnimationState = AnimationState.IDLE;


    /**
     * constructor for the avatar calss
     * @param topLeftCorner top left corner for the gameObject
     * @param inputListener an input listener for the keyboard inputs
     * @param imageReader an image reader to load the images for the renderable
     */
    public Avatar(Vector2 topLeftCorner,
                  UserInputListener inputListener,
                  ImageReader imageReader){
        super(topLeftCorner, new Vector2(Constants.AVATAR_WIDTH, Constants.AVATAR_HEIGHT),
                imageReader.readImage(Constants.IMAGE_RENDERABLE_PATH_IDLE_0, true));
        this.inputListener = inputListener;

        this.idleClips = new danogl.gui.rendering.ImageRenderable[]{
                imageReader.readImage(Constants.IMAGE_RENDERABLE_PATH_IDLE_0, true),
                imageReader.readImage(Constants.IMAGE_RENDERABLE_PATH_IDLE_1, true),
                imageReader.readImage(Constants.IMAGE_RENDERABLE_PATH_IDLE_2, true),
                imageReader.readImage(Constants.IMAGE_RENDERABLE_PATH_IDLE_3, true),
                imageReader.readImage(Constants.IMAGE_RENDERABLE_PATH_IDLE_4, true),
                imageReader.readImage(Constants.IMAGE_RENDERABLE_PATH_IDLE_5, true),};

        this.jumpClips = new danogl.gui.rendering.ImageRenderable[]{
                imageReader.readImage(Constants.IMAGE_RENDERABLE_PATH_JUMP_0, true),
                imageReader.readImage(Constants.IMAGE_RENDERABLE_PATH_JUMP_1, true),
                imageReader.readImage(Constants.IMAGE_RENDERABLE_PATH_JUMP_2, true),
                imageReader.readImage(Constants.IMAGE_RENDERABLE_PATH_JUMP_3, true),
                imageReader.readImage(Constants.IMAGE_RENDERABLE_PATH_JUMP_4, true),
                imageReader.readImage(Constants.IMAGE_RENDERABLE_PATH_JUMP_5, true),
                imageReader.readImage(Constants.IMAGE_RENDERABLE_PATH_JUMP_6, true),
                imageReader.readImage(Constants.IMAGE_RENDERABLE_PATH_JUMP_7, true),};

        this.runClips = new danogl.gui.rendering.ImageRenderable[]{
                imageReader.readImage(Constants.IMAGE_RENDERABLE_PATH_RUN_0, true),
                imageReader.readImage(Constants.IMAGE_RENDERABLE_PATH_RUN_1, true),
                imageReader.readImage(Constants.IMAGE_RENDERABLE_PATH_RUN_2, true),
                imageReader.readImage(Constants.IMAGE_RENDERABLE_PATH_RUN_3, true),
                imageReader.readImage(Constants.IMAGE_RENDERABLE_PATH_RUN_4, true),
                imageReader.readImage(Constants.IMAGE_RENDERABLE_PATH_RUN_5, true),
                imageReader.readImage(Constants.IMAGE_RENDERABLE_PATH_RUN_6, true),
                imageReader.readImage(Constants.IMAGE_RENDERABLE_PATH_RUN_7, true)};

        this.idleAnimation = new AnimationRenderable(this.idleClips, Constants.TIME_BETWEEN_IDLE_CLIPS);
        this.runAnimation = new AnimationRenderable(this.runClips, Constants.TIME_BETWEEN_RUNNING_CLIPS);
        this.jumpAnimation = new AnimationRenderable(this.jumpClips, Constants.TIME_BETWEEN_JUMPING_CLIPS);

        this.renderer().setRenderable(this.idleAnimation);
        this.currentAnimationState = AnimationState.IDLE;

        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(Constants.AVATAR_MASS);
        this.energyHandler = new EnergyHandler();
    }

    /**
     * Sets the jump callback for handling actions during a jump.
     *
     * @param jumpCallback The callback implementing AvatarJumpCallback.
     */
    public void setJumpCallback(AvatarJumpCallback jumpCallback){
        this.jumpCallback = jumpCallback;
    }

    /**
     * Returns the current energy level of the avatar.
     *
     * @return The current energy level.
     */
    public double getCurrentEnergy(){
        return this.energyHandler.getEnergy();
    }

    /**
     * Sets the energy callback for handling energy-related events.
     *
     * @param callback The callback implementing EnergyCallback.
     */
    public static void setEnergyCallback(EnergyCallback callback) {
        energyCallback = callback;
    }

    /**
     * Updates the avatar's state, handling movement, jumping, animations, and energy management.
     *
     * @param deltaTime The time elapsed, in seconds, since the last frame.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        float horizontalVelocity = 0f;
        boolean onGround = (objectsUnderFeetCount > 0);

        if (inputListener.isKeyPressed(Constants.MOVE_KEY_LEFT) &&
                getCurrentEnergy() >= Constants.MIN_REQUIRED_ENERGY_RUNNING) {
            horizontalVelocity -= Constants.AVATAR_MOVEMENT_SPEED;
            this.energyHandler.runMode();
            if(!airborne){setAnimationState(AnimationState.RUNNING);}
            this.renderer().setIsFlippedHorizontally(true);
        }
        if (inputListener.isKeyPressed(Constants.MOVE_KEY_RIGHT) &&
                getCurrentEnergy() >= Constants.MIN_REQUIRED_ENERGY_RUNNING) {
            horizontalVelocity += Constants.AVATAR_MOVEMENT_SPEED;
            this.energyHandler.runMode();
            if(!airborne){setAnimationState(AnimationState.RUNNING);}
            this.renderer().setIsFlippedHorizontally(false);
        }
        Vector2 currentVelocity = getVelocity();
        if(!onGround){
            currentVelocity = currentVelocity.add(new Vector2(0,Constants.AVATAR_GRAVITY * deltaTime));
        }
        if(inputListener.isKeyPressed(Constants.MOVE_KEY_UP) && !airborne &&
                this.getCurrentEnergy() >= Constants.MIN_REQUIRED_ENERGY_JUMPING){
            currentVelocity = new Vector2(currentVelocity.x(), - Constants.AVATAR_JUMP_SPEED);
            this.airborne = true;
            this.energyHandler.jumpMode();
            setAnimationState(AnimationState.JUMPING);
            if(jumpCallback != null){
                jumpCallback.onAvatarJump();
            }
        }
        else if(getVelocity().equals(Vector2.ZERO)){
            if (getCurrentEnergy() < Constants.MAX_ENERGY) {
                this.energyHandler.idleMode();
            }
            setAnimationState(AnimationState.IDLE);
        }
        if (energyCallback != null) {
            energyCallback.onEnergyChanged(getCurrentEnergy());
        }
        setVelocity(new Vector2(horizontalVelocity, currentVelocity.y()));
    }
    /**
     * Sets the animation state for the avatar.
     *
     * @param newState The new animation state.
     */
    private void setAnimationState(AnimationState newState) {
        if (currentAnimationState != newState) {
            currentAnimationState = newState;
            switch (newState) {
                case IDLE:
                    this.renderer().setRenderable(idleAnimation);
                    break;
                case RUNNING:
                    this.renderer().setRenderable(runAnimation);
                    break;
                case JUMPING:
                    this.renderer().setRenderable(jumpAnimation);
                    break;
            }
        }
    }

    /**
     * Handles collisions with other GameObjects.
     *
     * @param other     The GameObject involved in the collision.
     * @param collision Information about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, danogl.collisions.Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other.getTag().equals("fruit")){
            this.energyHandler.ateFruit();
            if (energyCallback != null) {
                energyCallback.onFruitConsumed(other); // Notify the callback
            }
            return;
        }
        // If we collided with an object from above (normal pointing up toward us)
        if (collision.getNormal().y() < Constants.NORMAL_FOR_COLLISION) {
            objectsUnderFeetCount++;
            objectsBelowFeetSet.add(other);
            airborne = false;
        }
    }

    /**
     * Handles when the avatar stops colliding with another GameObject.
     *
     * @param other The GameObject no longer colliding with the avatar.
     */
    @Override
    public void onCollisionExit(GameObject other) {
        super.onCollisionExit(other);
        if (objectsBelowFeetSet.remove(other)) {
            objectsUnderFeetCount--;
        }
    }
}
