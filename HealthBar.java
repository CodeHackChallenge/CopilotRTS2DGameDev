public class HealthBar implements Component {
    public int width;       // custom width
    public int height;      // custom height
    public int offsetX;     // relative to hurtbox
    public int offsetY;     // relative to hurtbox

    public HealthBar(int width, int height, int offsetX, int offsetY) {
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }
}
