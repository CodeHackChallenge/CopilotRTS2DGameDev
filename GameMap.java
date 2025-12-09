import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private TileMapComponent tileMap;

    public GameMap(TileMapComponent tileMap) {
        this.tileMap = tileMap;
    }

    /**
     * Checks if a tile is walkable (e.g., not water or blocked).
     */
    public boolean isWalkable(Point p) {
        if (p.x < 0 || p.y < 0 ||
            p.x >= tileMap.config.widthTiles() ||
            p.y >= tileMap.config.heightTiles()) {
            return false;
        }

        int id = tileMap.getTile(p.x, p.y);
        // Example: 0=grass, 1=dirt, 2=water (not walkable)
        return id != 2;
    }

    /**
     * Returns neighboring tiles (4-directional).
     */
    public List<Point> getNeighbors(Point p) {
        List<Point> neighbors = new ArrayList<>();
        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
        for (int[] d : dirs) {
            Point n = new Point(p.x + d[0], p.y + d[1]);
            if (isWalkable(n)) {
                neighbors.add(n);
            }
        }
        return neighbors;
    }

    /**
     * Expose the underlying tile map if needed.
     */
    public TileMapComponent getTileMap() {
        return tileMap;
    }
}
