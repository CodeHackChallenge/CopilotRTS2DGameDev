public class MapConfig {
	public static final int MAP_COL = 96, MAP_ROW = 96, MAP_TILESIZE = 64;
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
        return new MapConfig(MAP_COL, MAP_ROW, MAP_TILESIZE); // 96x96 texture, each 64px
    }
}
