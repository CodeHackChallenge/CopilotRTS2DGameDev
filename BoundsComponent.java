import java.awt.Rectangle;

public class BoundsComponent implements Component {
    public Rectangle bounds;
    public BoundsComponent(int x, int y, int w, int h) {
        this.bounds = new Rectangle(x, y, w, h);
    }
    public void update(Position pos, int w, int h) {
        bounds.setBounds(pos.x, pos.y, w, h);
    }
}
