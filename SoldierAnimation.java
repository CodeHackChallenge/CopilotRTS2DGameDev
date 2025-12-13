import java.awt.image.BufferedImage;
import java.util.EnumMap;

/**
 * Refactored SoldierAnimation:
 * - Uses SpritesheetManager to load frames from /soldier_sheet.png
 * - Holds animations for all 8 directions
 * - Supports freezeStanding() and play() via Animation class
 */
public class SoldierAnimation implements Component {
    private EnumMap<Direction, Animation> animations;
    private Direction currentDirection;

    public SoldierAnimation() {
        animations = new EnumMap<>(Direction.class);

        // Build animations for all 8 directions from soldier spritesheet
        for (int dirIndex = 0; dirIndex < 8; dirIndex++) {
            BufferedImage[] frames = new BufferedImage[8]; // 8 frames per direction
            for (int i = 0; i < 8; i++) {
                frames[i] = SpritesheetManager.getInstance()
                        .getSubImage("/soldier_sheet.png", i * 64, dirIndex * 64, 64, 64);
            }
            animations.put(Direction.values()[dirIndex], new Animation(frames, 8));
        }

        // Default facing south
        currentDirection = Direction.SOUTH;
    }

    public void setDirection(Direction dir) {
        this.currentDirection = dir;
    }

    public Animation getCurrentAnimation() {
        return animations.get(currentDirection);
    }

    // Convenience methods to control animation state
    public void freezeStanding() {
        getCurrentAnimation().freezeStanding();
    }

    public void play() {
        getCurrentAnimation().play();
    }
}
