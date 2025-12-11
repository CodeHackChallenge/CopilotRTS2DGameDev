import java.awt.*;
import java.util.List;

public class RenderSystem {
    // Draw entities in window with current viewport offsets
    public void render(Graphics2D g, List<Entity> entities, int offsetX, int offsetY) {
        for (Entity e : entities) {
            Position pos = e.getComponent(Position.class);
            Sprite sprite = e.getComponent(Sprite.class);
            if (pos == null || sprite == null) continue;

            int screenX = pos.x - offsetX;
            int screenY = pos.y - offsetY;

            // Cull entities outside the window (728x728)
            if (screenX + sprite.width < 0 || screenY + sprite.height < 0 ||
                screenX > 728 || screenY > 728) continue;

            g.setColor(sprite.color);
            g.fillRect(screenX, screenY, sprite.width, sprite.height);
        }
    }
}
