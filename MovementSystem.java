public class MovementSystem {
    // Move selected entity one pixel step toward target, but block on unwalkable tiles
    public void moveTowards(Entity e, Position target, TileMapComponent map) {
        Position pos = e.getComponent(Position.class);
        BoundsComponent bc = e.getComponent(BoundsComponent.class);
        Sprite sprite = e.getComponent(Sprite.class);
        if (pos == null || target == null || sprite == null) return;

        int nextX = pos.x, nextY = pos.y;
        if (pos.x < target.x) nextX++;
        if (pos.x > target.x) nextX--;
        if (pos.y < target.y) nextY++;
        if (pos.y > target.y) nextY--;

        // Convert the bounding box’s future top-left to tile space and check all corners
        int ts = map.getConfig().tileSize();
        int leftTile   = nextX / ts;
        int rightTile  = (nextX + sprite.width - 1) / ts;
        int topTile    = nextY / ts;
        int bottomTile = (nextY + sprite.height - 1) / ts;

        boolean walkable =
            map.isWalkable(leftTile, topTile) &&
            map.isWalkable(rightTile, topTile) &&
            map.isWalkable(leftTile, bottomTile) &&
            map.isWalkable(rightTile, bottomTile);

        if (walkable) {
            pos.x = nextX;
            pos.y = nextY;
            if (bc != null) bc.update(pos, sprite.width, sprite.height);
        } else {
            // Stop movement when next step would collide
            // Optional: try axis-separated movement for sliding
        }
    }
}
