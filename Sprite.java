import java.awt.Color;

public class Sprite implements Component {
    public Color color;
    public int width, height;
    public Sprite(Color color, int width, int height) {
        this.color = color; this.width = width; this.height = height;
    }
}
