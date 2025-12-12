import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * TileMapLoader reads a text file from /resources/maps/
 * Each line contains space-separated tile IDs (e.g. "0 1 2 3").
 * Builds a TileMapComponent with those IDs.
 */
public class TileMapLoader {

    public static TileMapComponent loadFromFile(String resourcePath, MapConfig config) throws IOException {
        TileMapComponent map = new TileMapComponent(config);

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(TileMapLoader.class.getResourceAsStream(resourcePath)))) {

            String line;
            int y = 0;
            while ((line = reader.readLine()) != null && y < config.heightTiles()) {
                StringTokenizer st = new StringTokenizer(line);
                int x = 0;
                while (st.hasMoreTokens() && x < config.widthTiles()) {
                    String token = st.nextToken();
                    int id;
                    try {
                        id = Integer.parseInt(token);
                    } catch (NumberFormatException e) {
                        id = 0; // default to grass if invalid
                    }
                    map.setTile(x, y, id);
                    x++;
                }
                y++;
            }
        }
        return map;
    }
}


