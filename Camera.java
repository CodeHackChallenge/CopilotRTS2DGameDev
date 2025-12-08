class Camera {
    int x, y;          // top-left tile
    int width, height; // viewport size in tiles
    MapConfig config;

    Camera(MapConfig config, int width, int height) {
        this.config = config;
        this.width = width;
        this.height = height;
    }

    public void follow(Position pos) {
        this.x = pos.x - width / 2;
        this.y = pos.y - height / 2;
        clamp();
    }

    public void pan(int dx, int dy) {
        this.x += dx;
        this.y += dy;
        clamp();
    }

    private void clamp() {
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x + width > config.widthTiles()) x = config.widthTiles() - width;
        if (y + height > config.heightTiles()) y = config.heightTiles() - height;
    }

    public boolean isVisible(int tx, int ty) {
        return tx >= x && tx < x + width &&
               ty >= y && ty < y + height;
    }
}
