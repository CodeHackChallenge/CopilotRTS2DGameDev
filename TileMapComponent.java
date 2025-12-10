public class TileMapComponent implements Component {
    private int[][] tiles;
    private MapConfig config;

    public TileMapComponent(MapConfig config) {
        this.config = config;
        this.tiles = new int[config.heightTiles()][config.widthTiles()];
    }

    public void setTile(int x, int y, int id) {
        tiles[y][x] = id;
    }

    public int getTile(int x, int y) {
        return tiles[y][x];
    }

    public MapConfig getConfig() { return config; }
}
