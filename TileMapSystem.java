import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TileMapSystem {
    private Map<Integer, Color> tileColors = new HashMap<>();

    public TileMapSystem() {
        tileColors.put(0, Color.GREEN);   // grass
        tileColors.put(1, Color.ORANGE);  // dirt
        tileColors.put(2, Color.BLUE);    // water
    }

    public void render(Graphics2D g, TileMapComponent map) {
        MapConfig config = map.getConfig();
        for (int y = 0; y < config.heightTiles(); y++) {
            for (int x = 0; x < config.widthTiles(); x++) {
                int id = map.getTile(x, y);
                Color c = tileColors.getOrDefault(id, Color.GRAY);
                g.setColor(c);
                g.fillRect(x * config.tileSize(), y * config.tileSize(),
                           config.tileSize(), config.tileSize());
            }
        }
    }
}
