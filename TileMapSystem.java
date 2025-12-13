import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Refactored: TileMapSystem now uses SpritesheetManager
 * with a dedicated tilesheet (e.g. "/tilesheet.png").
 */
public class TileMapSystem {
    public void render(Graphics2D g, TileMapComponent map, int offsetX, int offsetY) {
        MapConfig cfg = map.getConfig();
        int ts = cfg.tileSize();

        int startTileX = Math.max(0, offsetX / ts);
        int startTileY = Math.max(0, offsetY / ts);
        int endTileX = Math.min(cfg.widthTiles() - 1, (offsetX + 1200) / ts);
        int endTileY = Math.min(cfg.heightTiles() - 1, (offsetY + 720) / ts);

        for (int ty = startTileY; ty <= endTileY; ty++) {
            for (int tx = startTileX; tx <= endTileX; tx++) {
                int id = map.getTile(tx, ty);

                // NEW: get tile texture from tilesheet
                BufferedImage texture = SpritesheetManager.getInstance()
                        .getSubImage("/tilesheet.png", id * ts, 0, ts, ts);

                int screenX = tx * ts - offsetX;
                int screenY = ty * ts - offsetY;

                g.drawImage(texture, screenX, screenY, ts, ts, null);
            }
        }
    }
}

