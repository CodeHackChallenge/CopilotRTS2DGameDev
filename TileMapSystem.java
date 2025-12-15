import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * TileMapSystem is responsible for rendering the tile map.
 * Each tile ID (0=grass, 1=dirt, 2=rock, 3=water) maps to a BufferedImage texture.
 * Textures are cached in TextureManager so they are only loaded once.
 */
public class TileMapSystem {
   // private final Map<Integer, BufferedImage> tileTextures = new HashMap<>();

//    public TileMapSystem() {
//        TextureManager tm = TextureManager.getInstance();
//      
//        tileTextures.put(0, tm.getTexture("/texture/grass_32x32.png"));
//        tileTextures.put(1, tm.getTexture("/texture/dirt_32x32.png"));
//        tileTextures.put(2, tm.getTexture("/texture/rock_32x32.png"));
//        tileTextures.put(3, tm.getTexture("/texture/water_32x32.png"));
//        
//        
//    }

    /**
     * Render visible portion of the map.
     * @param g Graphics2D context
     * @param map TileMapComponent containing tile IDs
     * @param offsetX viewport offset in X
     * @param offsetY viewport offset in Y
     */
    public void render(Graphics2D g, TileMapComponent map, int offsetX, int offsetY) {
        MapConfig cfg = map.getConfig();
        int ts = cfg.tileSize();

        // Calculate visible tile range based on viewport
        int startTileX = Math.max(0, offsetX / ts);
        int startTileY = Math.max(0, offsetY / ts);
        int endTileX = Math.min(cfg.widthTiles() - 1, (offsetX + Engine.WIDTH) / ts);
        int endTileY = Math.min(cfg.heightTiles() - 1, (offsetY + Engine.HEIGHT) / ts);

        // Draw each visible tile
        for (int ty = startTileY; ty <= endTileY; ty++) {
            for (int tx = startTileX; tx <= endTileX; tx++) {
                int id = map.getTile(tx, ty);
                //BufferedImage texture = tileTextures.get(id);

             // NEW: get tile texture from tilesheet
                BufferedImage texture = SpritesheetManager.getInstance().getSubImage("/texture/tilesheet_64x64.png", id * ts, 0, ts, ts);
                
                int screenX = tx * ts - offsetX;
                int screenY = ty * ts - offsetY;

//                if (texture != null) {
//                    // Draw tile texture scaled to tile size
//                    g.drawImage(texture, screenX, screenY, ts, ts, null);
//                }
                
                g.drawImage(texture, screenX, screenY, ts, ts, null); 
            }
        }
    }

    /**
     * Render grid overlay for debug mode.
     * This is unchanged, but still useful for visualizing tile boundaries.
     */
    public void renderGrid(Graphics2D g, TileMapComponent map, int offsetX, int offsetY) {
        MapConfig cfg = map.getConfig();
        int ts = cfg.tileSize();

        int startTileX = Math.max(0, offsetX / ts);
        int startTileY = Math.max(0, offsetY / ts);
        int endTileX = Math.min(cfg.widthTiles(), (offsetX + Engine.WIDTH) / ts + 1);
        int endTileY = Math.min(cfg.heightTiles(), (offsetY + Engine.HEIGHT) / ts + 1);

        g.setColor(new Color(255, 255, 255, 80));

        for (int tx = startTileX; tx <= endTileX; tx++) {
            int x = tx * ts - offsetX;
            g.drawLine(x, 0, x, Engine.HEIGHT);
        }
        for (int ty = startTileY; ty <= endTileY; ty++) {
            int y = ty * ts - offsetY;
            g.drawLine(0, y, Engine.WIDTH, y);
        }
    }
}
