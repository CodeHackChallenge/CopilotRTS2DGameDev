import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * TextureManager caches all textures (tiles, sprites, UI).
 * Ensures each image is loaded once and reused everywhere.
 * Uses BufferedImage for guaranteed pixel access and performance.
 */
public class TextureManager {
    private static TextureManager instance;
    private final Map<String, BufferedImage> cache = new HashMap<>();

    private TextureManager() { }

    public static TextureManager getInstance() {
        if (instance == null) {
            instance = new TextureManager();
        }
        return instance;
    }

    /**
     * Load or fetch a cached texture.
     * @param resourcePath path inside /resources (e.g. "/texture/grass.png")
     * @return BufferedImage texture
     */
    public BufferedImage getTexture(String resourcePath) {
        if (cache.containsKey(resourcePath)) {
            return cache.get(resourcePath);
        }
        try {
            BufferedImage img = ImageIO.read(getClass().getResource(resourcePath));
            cache.put(resourcePath, img);
            return img;
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }
}
