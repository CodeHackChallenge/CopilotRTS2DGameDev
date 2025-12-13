import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Refactored: Instead of loading separate textures,
 * TextureManager now loads a single spritesheet and provides subimages.
 */
public class TextureManager {
    private static TextureManager instance;
    private BufferedImage spriteSheet;

    private TextureManager() {
        try {
            // Load one big spritesheet from /resources/spritesheet.png
            spriteSheet = ImageIO.read(getClass().getResource("/spritesheet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static TextureManager getInstance() {
        if (instance == null) {
            instance = new TextureManager();
        }
        return instance;
    }

    /**
     * Get a subimage from the spritesheet.
     * @param x column index in spritesheet
     * @param y row index in spritesheet
     * @param w width of frame
     * @param h height of frame
     */
    public BufferedImage getSubImage(int x, int y, int w, int h) {
        return spriteSheet.getSubimage(x, y, w, h);
    }
}

