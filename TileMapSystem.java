import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TileMapSystem {
    private final Map<Integer, Color> tileColors = new HashMap<>();

    public TileMapSystem() {
        tileColors.put(0, Color.GREEN);          // grass (walkable)
        tileColors.put(1, Color.ORANGE);         // dirt (walkable)
        tileColors.put(2, new Color(139,69,19)); // rock (brown, unwalkable)
        tileColors.put(3, Color.BLUE);           // water (unwalkable)
    }

    // Render only tiles visible in the 728x728 window based on offsets (in pixels)
    public void render(Graphics2D g, TileMapComponent map, int offsetX, int offsetY) {
        MapConfig cfg = map.getConfig();
        int ts = cfg.tileSize();

        int startTileX = Math.max(0, offsetX / ts);
        int startTileY = Math.max(0, offsetY / ts);

        int endTileX = Math.min(cfg.widthTiles() - 1, (offsetX + 728) / ts);
        int endTileY = Math.min(cfg.heightTiles() - 1, (offsetY + 728) / ts);

        for (int ty = startTileY; ty <= endTileY; ty++) {
            for (int tx = startTileX; tx <= endTileX; tx++) {
                int id = map.getTile(tx, ty);
                g.setColor(tileColors.getOrDefault(id, Color.GRAY));

                int screenX = tx * ts - offsetX;
                int screenY = ty * ts - offsetY;
                g.fillRect(screenX, screenY, ts, ts);
            }
        }
    }

    // Draw grid lines over the visible region (using drawLine, no drawRect)
    public void renderGrid(Graphics2D g, TileMapComponent map, int offsetX, int offsetY) {
        MapConfig cfg = map.getConfig();
        int ts = cfg.tileSize();

        int startTileX = Math.max(0, offsetX / ts);
        int startTileY = Math.max(0, offsetY / ts);
        int endTileX = Math.min(cfg.widthTiles(), (offsetX + 728) / ts + 1);
        int endTileY = Math.min(cfg.heightTiles(), (offsetY + 728) / ts + 1);

        g.setColor(new Color(255, 255, 255, 80));

        // Vertical lines
        for (int tx = startTileX; tx <= endTileX; tx++) {
            int x = tx * ts - offsetX;
            g.drawLine(x, 0, x, 728);
        }
        // Horizontal lines
        for (int ty = startTileY; ty <= endTileY; ty++) {
            int y = ty * ts - offsetY;
            g.drawLine(0, y, 728, y);
        }
    }
}
