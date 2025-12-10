import java.io.*;
import java.nio.file.*;
import java.util.List;

public class TileMapLoader {
    public static TileMapComponent loadFromFile(String path, MapConfig config) throws IOException {
        TileMapComponent map = new TileMapComponent(config);
        List<String> lines = Files.readAllLines(Paths.get(path));

        for (int y = 0; y < config.heightTiles() && y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < config.widthTiles() && x < line.length(); x++) {
                int id = Character.getNumericValue(line.charAt(x));
                map.setTile(x, y, id);
            }
        }
        return map;
    }
}
