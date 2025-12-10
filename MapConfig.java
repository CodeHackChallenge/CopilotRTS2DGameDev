public class MapConfig {
    private int widthTiles;
    private int heightTiles;
    private int tileSize;

    public MapConfig(int widthTiles, int heightTiles, int tileSize) {
        this.widthTiles = widthTiles;
        this.heightTiles = heightTiles;
        this.tileSize = tileSize;
    }

    public int widthTiles() { return widthTiles; }
    public int heightTiles() { return heightTiles; }
    public int tileSize() { return tileSize; }

    public static MapConfig defaultConfig() {
        return new MapConfig(96, 96, 64); // 96x96 tiles, each 64px
    }
}
