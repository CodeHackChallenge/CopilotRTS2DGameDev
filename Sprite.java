import java.awt.image.BufferedImage;

/**
 * Sprite component represents an entity's visual appearance.
 * Instead of just a color, we now use a BufferedImage texture.
 * BufferedImage is chosen because:
 *   - It guarantees the image is fully loaded in memory.
 *   - It allows pixel-level access (getRGB/setRGB) for effects later.
 *   - It integrates seamlessly with Graphics2D.drawImage().
 */
public class Sprite implements Component {
    public BufferedImage image; // the texture for this sprite
    public int width, height;   // dimensions to render

    /**
     * Constructor loads the texture from the TextureManager cache.
     * @param resourcePath path to the image inside /resources/sprite/
     * @param width desired render width
     * @param height desired render height
     */
    public Sprite(String resourcePath, int width, int height) {
        this.image = TextureManager.getInstance().getTexture(resourcePath);
        this.width = width;
        this.height = height;
    }
}
