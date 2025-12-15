import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Refactored: Unified manager for multiple spritesheets.
 * - Caches each spritesheet by resource path
 * - Provides subimages for tiles, soldiers, or any entity
 */
public class SpritesheetManager {
    private static SpritesheetManager instance;
    private final Map<String, BufferedImage> sheets = new HashMap<>();

    private SpritesheetManager() { }

    public static SpritesheetManager getInstance() {
        if (instance == null) {
            instance = new SpritesheetManager();
        }
        return instance;
    }

    /**
     * Load and cache a spritesheet if not already cached.
     * @param resourcePath path inside /resources (e.g. "/tilesheet.png")
     */
    private BufferedImage loadSheet(String resourcePath) {
        if (sheets.containsKey(resourcePath)) {
            return sheets.get(resourcePath);
        }
        try {
            BufferedImage sheet = ImageIO.read(getClass().getResource(resourcePath));
            sheets.put(resourcePath, sheet);
            return sheet;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get a subimage from a specific spritesheet.
     * @param resourcePath path to spritesheet
     * @param x pixel X
     * @param y pixel Y
     * @param w width
     * @param h height
     */
    public BufferedImage getSubImage(String resourcePath, int x, int y, int w, int h) {
        BufferedImage sheet = loadSheet(resourcePath);
        if (sheet == null) return null;
        return sheet.getSubimage(x, y, w, h);
    }
}
