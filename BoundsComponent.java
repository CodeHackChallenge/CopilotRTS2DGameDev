import java.awt.Rectangle;

public class BoundsComponent {
    public Rectangle bounds;

    public BoundsComponent(int x, int y, int width, int height) {
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void update(Position pos, int width, int height) {
        bounds.setBounds(pos.x, pos.y, width, height);
    }
}
