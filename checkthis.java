public class AttackHitbox implements Component {
    public int offsetX;
    public int offsetY;
    public int width;
    public int height;

    public AttackHitbox(int offsetX, int offsetY, int width, int height) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
    }

    public Rectangle getBounds(Position pos) {
        return new Rectangle(
            (int) pos.x + offsetX,
            (int) pos.y + offsetY,
            width,
            height
        );
    }
}
