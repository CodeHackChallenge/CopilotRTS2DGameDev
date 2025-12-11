import java.awt.Graphics2D;
import java.util.List;

public class RenderSystem {
    public void render(Graphics2D g, List<Entity> entities, int offsetX, int offsetY) {
        for (Entity e : entities) {
            Position pos = e.getComponent(Position.class);
            Sprite sprite = e.getComponent(Sprite.class);
            if (pos == null || sprite == null) continue;

            int screenX = pos.x - offsetX;
            int screenY = pos.y - offsetY;

            if (screenX + sprite.width < 0 || screenY + sprite.height < 0 ||
                screenX > 728 || screenY > 728) continue;

            g.setColor(sprite.color);
            g.fillRect(screenX, screenY, sprite.width, sprite.height);
        }
    }
}
