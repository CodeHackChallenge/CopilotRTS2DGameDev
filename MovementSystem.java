public class MovementSystem {
    public void moveTowards(Entity e, Position target, TileMapComponent map) {
        Position pos = e.getComponent(Position.class);
        BoundsComponent bc = e.getComponent(BoundsComponent.class);
        Sprite sprite = e.getComponent(Sprite.class);
        if (pos == null || target == null || sprite == null) return;

        int nextX = pos.x;
        int nextY = pos.y;

        if (pos.x < target.x) nextX++;
        if (pos.x > target.x) nextX--;
        if (pos.y < target.y) nextY++;
        if (pos.y > target.y) nextY--;

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
       
