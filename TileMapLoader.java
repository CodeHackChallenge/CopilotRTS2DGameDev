import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TileMapLoader {
    public static TileMapComponent loadFromFile(String path, MapConfig config) throws IOException {
        TileMapComponent map = new TileMapComponent(config);
        List<String> lines = Files.readAllLines(Paths.get(path));

        for (int y = 0; y < config.heightTiles() && y < lines.size(); y++) {
            String line = lines.get(y).trim();
            for (int x = 0; x < config.widthTiles() && x < line.length(); x++) {
                int id = Character.getNumericValue(line.charAt(x));
                if (id < 0 || id > 9) id = 0;
                map.setTile(x, y, id);
            }
        }
        return map;
    }
}
