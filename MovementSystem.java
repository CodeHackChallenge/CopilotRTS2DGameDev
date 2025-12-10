import java.util.List;

public class MovementSystem {
    public void moveTowards(Entity e, Position target) {
        Position pos = e.getComponent(Position.class);
        BoundsComponent bc = e.getComponent(BoundsComponent.class);
        Sprite sprite = e.getComponent(Sprite.class);

        if (pos != null && target != null) {
            if (pos.x < target.x) pos.x++;
            if (pos.x > target.x) pos.x--;
            if (pos.y < target.y) pos.y++;
            if (pos.y > target.y) pos.y--;

            if (bc != null && sprite != null) {
                bc.update(pos, sprite.width, sprite.height);
            }
        }
    }
}
