import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * TextureManager caches all textures (tiles, sprites, UI).
 * Uses BufferedImage for guaranteed pixel access and performance.
 * Supports preloading all known textures at startup.
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

    /**
     * Preload all textures you know you’ll need.
     * Call this once at game startup.
     */
    public void preloadAll() {
        // Tiles
        getTexture("/texture/grass.png");
        getTexture("/texture/dirt.png");
        getTexture("/texture/rock.png");
        getTexture("/texture/water.png");

        // Sprites
        getTexture("/sprite/soldier.png");
        getTexture("/sprite/building.png");

        // UI (optional)
        // getTexture("/ui/button.png");
        // getTexture("/ui/icon.png");
    }
}

