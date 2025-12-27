public class Collision implements Component {

    // Local offset from the entity's position (top-left anchor)
    public int offsetX;
    public int offsetY;

    // Size of the collision box
    public int width;
    public int height;

    public Collision(int offsetX, int offsetY, int width, int height) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
    }

    public java.awt.Rectangle getBounds(Position pos) {
        return new java.awt.Rectangle(
                (int) pos.x + offsetX,
                (int) pos.y + offsetY,
                width,
                height
        );
    }
}
