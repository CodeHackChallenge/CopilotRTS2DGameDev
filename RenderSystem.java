import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * RenderSystem is responsible for drawing all entities on screen.
 * Each entity has a Position (world coordinates) and a Sprite (texture).
 * We now use BufferedImage for sprites instead of solid colors.
 */
public class RenderSystem {

    /**
     * Render all entities relative to the current viewport offset.
     * @param g Graphics2D context
     * @param entities list of entities to render
     * @param offsetX horizontal camera offset
     * @param offsetY vertical camera offset
     */
    public void render(Graphics2D g, List<Entity> entities, int offsetX, int offsetY) {
        for (Entity e : entities) {
            Position pos = e.getComponent(Position.class);
            Sprite sprite = e.getComponent(Sprite.class);

            // Skip entities without position or sprite
            if (pos == null || sprite == null || sprite.image == null) continue;

            // Convert world coordinates → screen coordinates
            int screenX = pos.x - offsetX;
            int screenY = pos.y - offsetY;

            // Cull entities outside the viewport (728x728 window)
            if (screenX + sprite.width < 0 || screenY + sprite.height < 0 ||
                screenX > 728 || screenY > 728) {
                continue;
            }

            // Draw the sprite texture at the correct position and size
            BufferedImage texture = sprite.image;
            g.drawImage(texture, screenX, screenY, sprite.width, sprite.height, null);
        }
    }
}

