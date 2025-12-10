import java.awt.Graphics2D;
import java.util.List;

public class RenderSystem {
    public void render(Graphics2D g, List<Entity> entities) {
        for (Entity e : entities) {
            Position pos = e.getComponent(Position.class);
            Sprite sprite = e.getComponent(Sprite.class);
            if (pos != null && sprite != null) {
                g.setColor(sprite.color);
                g.fillRect(pos.x, pos.y, sprite.width, sprite.height);

                BoundsComponent bc = e.getComponent(BoundsComponent.class);
                if (bc != null) {
                    g.setColor(Color.YELLOW);
                    g.draw(bc.bounds);
                }
            }
        }
    }
}
