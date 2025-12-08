package core.world;

public record MapConfig(int tileSize, int widthTiles, int heightTiles) {
    public int worldWidthPx() { return tileSize * widthTiles; }
    public int worldHeightPx() { return tileSize * heightTiles; }

    public static MapConfig defaultRts() {
        return new MapConfig(64, 96, 96);
    }
}
