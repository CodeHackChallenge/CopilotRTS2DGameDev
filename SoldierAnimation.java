import java.util.EnumMap;

/**
 * New: SoldierAnimation holds animations for all 8 directions.
 * Flexible: can be extended for other units with fewer/more directions.
 */
public class SoldierAnimation implements Component {
    private EnumMap<Direction, Animation> animations;
    private Direction currentDirection;

    public SoldierAnimation(EnumMap<Direction, Animation> animations) {
        this.animations = animations;
        this.currentDirection = Direction.SOUTH; // default facing south
    }

    public void setDirection(Direction dir) {
        this.currentDirection = dir;
    }

    public Animation getCurrentAnimation() {
        return animations.get(currentDirection);
    }
}
