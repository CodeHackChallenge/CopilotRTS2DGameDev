public class MovementSystem {
	
	public static final double entityDefaultSpeed = 0.4;
	private static double entityCurrentSpeed = entityDefaultSpeed;
	
    public void moveTowards(Entity entity, Position target) {
        Position pos = entity.getComponent(Position.class);
        BoundsComponent bc = entity.getComponent(BoundsComponent.class);
        //Sprite sprite = e.getComponent(Sprite.class);
        SoldierAnimation soldierAnim = entity.getComponent(SoldierAnimation.class);

        //if (pos == null || target == null || sprite == null) return;
 
//        if (pos.getX() < target.getX()) pos.setX(pos.getX() + entityCurrentSpeed);
//        if (pos.getX() > target.getX()) pos.setX(pos.getX() - entityCurrentSpeed);
//        if (pos.getY() < target.getY()) pos.setY(pos.getY() + entityCurrentSpeed);
//        if (pos.getY() > target.getY()) pos.setY(pos.getY() - entityCurrentSpeed); 
        
        if (pos == null || target == null) return;
        
        int dx = (int) (target.getX() - pos.getX());
        int dy = (int) (target.getY()- pos.getY());
        
        if (dx == 0 && dy == 0) {
            // NEW: soldier stopped → freeze animation at standing frame
            if (soldierAnim != null) {
                soldierAnim.getCurrentAnimation().freezeStanding();
            }
            return;
        }
        
        // soldier moving → update position
        double step = entityCurrentSpeed;
        if (dx != 0) pos.x += (dx > 0 ? step : -step); //TODO: watch this!
        if (dy != 0) pos.y  += (dy > 0 ? step : -step); 
        
        if (bc != null) {
            bc.bounds.setLocation((int)pos.getX(), (int)pos.getY());
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
    //my implementation
    public static void setSpeed(double speed) {
    	entityCurrentSpeed = speed;
    	
    }
  //my implementation
    public static double getSpeed() {return entityCurrentSpeed;}
}
