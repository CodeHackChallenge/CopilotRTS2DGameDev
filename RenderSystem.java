/**
 * Refactored: RenderSystem now checks SoldierAnimation first.
 * If present, it draws the current frame for the soldier's direction.
 */
public class RenderSystem {
    public void render(Graphics2D g, List<Entity> entities, int offsetX, int offsetY) {
        for (Entity e : entities) {
            Position pos = e.getComponent(Position.class);
            SoldierAnimation soldierAnim = e.getComponent(SoldierAnimation.class);
            Sprite sprite = e.getComponent(Sprite.class);

            if (pos == null) continue;

            int screenX = pos.x - offsetX;
            int screenY = pos.y - offsetY;

            if (soldierAnim != null) {
                g.drawImage(soldierAnim.getCurrentAnimation().getCurrentFrame(),
                            screenX, screenY, null);
            } else if (sprite != null && sprite.image != null) {
                g.drawImage(sprite.image, screenX, screenY, sprite.width, sprite.height, null);
            }
        }
    }
}

