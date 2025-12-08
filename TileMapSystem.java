package ecs.systems;

import ecs.components.TileMapComponent;
import java.util.Random;

public final class TileMapSystem {
    private final Random rng = new Random(42);

    public void generate(TileMapComponent map) {
        for (int y = 0; y < map.config.heightTiles(); y++) {
            for (int x = 0; x < map.config.widthTiles(); x++) {
                // Example: 0=grass, 1=dirt, 2=water
                int id = rng.nextInt(10) == 0 ? 2 : (rng.nextInt(7) == 0 ? 1 : 0);
                map.setTile(x, y, id);
            }
        }
    }

    public void printSample(TileMapComponent map, int rows, int cols) {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                System.out.print(map.getTile(x, y) + " ");
            }
            System.out.println();
        }
    }
}
