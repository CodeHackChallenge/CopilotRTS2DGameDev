/**
 * Refactored: MovementSystem now freezes soldier animation when stopped,
 * and resumes animation when moving.
 */
public class MovementSystem {

    public void moveTowards(Entity entity, Position target, TileMapComponent map) {
        Position pos = entity.getComponent(Position.class);
        BoundsComponent bc = entity.getComponent(BoundsComponent.class);
        SoldierAnimation soldierAnim = entity.getComponent(SoldierAnimation.class);

        if (pos == null || target == null) return;

        int dx = target.x - pos.x;
        int dy = target.y - pos.y;

        if (dx == 0 && dy == 0) {
            // NEW: soldier stopped → freeze animation at standing frame
            if (soldierAnim != null) {
                soldierAnim.getCurrentAnimation().freezeStanding();
            }
            return;
        }

        // soldier moving → update position
        int step = 4;
        if (dx != 0) pos.x += (dx > 0 ? step : -step);
        if (dy != 0) pos.y += (dy > 0 ? step : -step);

        if (bc != null) {
            bc.bounds.setLocation(pos.x, pos.y);
        }

        // Refactored: update animation direction and resume animation
        if (soldierAnim != null) {
            Direction dir = getDirection(dx, dy);
            soldierAnim.setDirection(dir);
            soldierAnim.getCurrentAnimation().play(); // resume animation
        }
    }

    private Direction getDirection(int dx, int dy) {
        double angle = Math.atan2(dy, dx);
        double deg = Math.toDegrees(angle);
        if (deg < 0) deg += 360;

        if (deg >= 337.5 || deg < 22.5) return Direction.EAST;
        if (deg < 67.5) return Direction.NORTHEAST;
        if (deg < 112.5) return Direction.NORTH;
        if (deg < 157.5) return Direction.NORTHWEST;
        if (deg < 202.5) return Direction.WEST;
        if (deg < 247.5) return Direction.SOUTHWEST;
        if (deg < 292.5) return Direction.SOUTH;
        return Direction.SOUTHEAST;
    }
}
    
