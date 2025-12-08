package ecs.components;

import core.world.MapConfig;

public final class TileMapComponent implements Component {
    public final MapConfig config;
    public final int[][] tiles; // terrain type IDs

    public TileMapComponent(MapConfig config) {
        this.config = config;
        this.tiles = new int[config.heightTiles()][config.widthTiles()];
    }

    public int getTile(int tx, int ty) {
        return tiles[ty][tx];
    }

    public void setTile(int tx, int ty, int id) {
        tiles[ty][tx] = id;
    }
}
