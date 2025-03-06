package pepse.world;

/**
 * Callback interface for responding to the avatar's jump action.
 * Classes implementing this interface can define custom behavior that occurs when the avatar jumps.
 */
public interface AvatarJumpCallback {
    /**
     * Method to be called when the avatar jumps.
     */
    void onAvatarJump();
}
