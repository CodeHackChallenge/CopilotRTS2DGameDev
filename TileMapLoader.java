import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * TileMapLoader reads a text file from /resources/maps/
 * Each character in the file corresponds to a tile ID (0=grass, 1=dirt, 2=rock, 3=water).
 * It builds a TileMapComponent with those IDs.
 *
 * Rendering is handled separately by TileMapSystem, which maps IDs → textures.
 */
public class TileMapLoader {

    /**
     * Load a map file into a TileMapComponent.
     * @param resourcePath path inside /resources/maps (e.g. "/maps/map.txt")
     * @param config MapConfig describing width, height, tile size
     * @return TileMapComponent with tile IDs populated
     */
    public static TileMapComponent loadFromFile(String resourcePath, MapConfig config) throws IOException {
        TileMapComponent map = new TileMapComponent(config);

        // Read all lines from the map file
        List<String> lines = Files.readAllLines(Paths.get(TileMapLoader.class.getResource(resourcePath).toURI()));

        for (int y = 0; y < config.heightTiles() && y < lines.size(); y++) {
            String line = lines.get(y).trim();
            for (int x = 0; x < config.widthTiles() && x < line.length(); x++) {
                int id = Character.getNumericValue(line.charAt(x));
                if (id < 0 || id > 9) id = 0; // default to grass if invalid
                map.setTile(x, y, id);
            }
        }
        return map;
    }
}

