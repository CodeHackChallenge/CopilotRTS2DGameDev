import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Refactored RenderSystem:
 * - Checks for SoldierAnimation first
 * - Falls back to static Sprite if no animation
 * - Uses Position + offset for correct screen placement
 */
public class RenderSystem {

    public void render(Graphics2D g, List<Entity> entities, int offsetX, int offsetY) {
        for (Entity e : entities) {
            Position pos = e.getComponent(Position.class);
            if (pos == null) continue;

            int screenX = pos.x - offsetX;
            int screenY = pos.y - offsetY;

            // Refactored: soldier animation rendering
            SoldierAnimation soldierAnim = e.getComponent(SoldierAnimation.class);
            if (soldierAnim != null) {
                BufferedImage frame = soldierAnim.getCurrentAnimation().getCurrentFrame();
                g.drawImage(frame, screenX, screenY, null);
                continue; // skip static sprite if animation exists
            }

            // Fallback: static sprite rendering
            Sprite sprite = e.getComponent(Sprite.class);
            if (sprite != null && sprite.image != null) {
                g.drawImage(sprite.image, screenX, screenY, sprite.width, sprite.height, null);
            }
        }
    }
}


