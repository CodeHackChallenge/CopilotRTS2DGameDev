import java.awt.image.BufferedImage;

/**
 * Refactored: Sprite now stores a single frame from the spritesheet.
 */
public class Sprite implements Component {
    public BufferedImage image;
    public int width, height;

    public Sprite(int sheetX, int sheetY, int width, int height) {
        this.image = TextureManager.getInstance().getSubImage(sheetX, sheetY, width, height);
        this.width = width;
        this.height = height;
    }
}
