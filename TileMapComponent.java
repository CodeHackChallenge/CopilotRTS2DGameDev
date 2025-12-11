public class TileMapComponent implements Component {
    private final int[][] tiles;
    private final MapConfig config;

    public TileMapComponent(MapConfig config) {
        this.config = config;
        this.tiles = new int[config.heightTiles()][config.widthTiles()];
    }

    public void setTile(int x, int y, int id) { tiles[y][x] = id; }
    public int getTile(int x, int y) { return tiles[y][x]; }
    public MapConfig getConfig() { return config; }

    public boolean isWalkable(int tx, int ty) {
        if (tx < 0 || ty < 0 || tx >= config.widthTiles() || ty >= config.heightTiles()) return false;
        int id = tiles[ty][tx];
        return id == 0 || id == 1; // grass and dirt are walkable
    }
}
