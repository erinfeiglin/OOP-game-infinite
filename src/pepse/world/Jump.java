package pepse.world;

import pepse.PepseGameManager;

/**
 * Callback class that triggers actions when the avatar jumps.
 * In this implementation, it generates raindrops in the game world.
 */
public class Jump implements AvatarJumpCallback{
    private final PepseGameManager gameManager;

    /**
     * Constructor for the Jump class.
     *
     * @param gameManager The PepseGameManager instance to interact with the game world.
     */
    public Jump(PepseGameManager gameManager){
        this.gameManager = gameManager;
    }

    /**
     * Called when the avatar jumps.
     * Generates raindrops in the game world.
     */
    @Override
    public void onAvatarJump() {
        gameManager.generateRaindrops();
    }
}
