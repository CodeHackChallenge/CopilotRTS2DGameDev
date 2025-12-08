class RenderSystem {
    private Camera camera;

    public RenderSystem(Camera camera) {
        this.camera = camera;
    }

    public void renderMap(TileMapComponent map, Graphics g) {
        int tileSize = map.config.tileSize();
        for (int y = camera.y; y < camera.y + camera.height; y++) {
            for (int x = camera.x; x < camera.x + camera.width; x++) {
                int id = map.getTile(x, y);
                Color color = switch (id) {
                    case 0 -> Color.GREEN;   // grass
                    case 1 -> Color.ORANGE;  // dirt
                    case 2 -> Color.BLUE;    // water
                    default -> Color.GRAY;
                };
                g.setColor(color);
                g.fillRect((x - camera.x) * tileSize, (y - camera.y) * tileSize, tileSize, tileSize);
            }
        }
    }

    public void renderEntities(List<Entity> entities, Graphics g) {
        for (Entity e : entities) {
            Position pos = e.getComponent(Position.class);
            Sprite sprite = e.getComponent(Sprite.class);
            if (pos != null && sprite != null && camera.isVisible(pos.x, pos.y)) {
                g.setColor(Color.RED);
                g.fillOval((pos.x - camera.x) * 64, (pos.y - camera.y) * 64, 64, 64);
            }
        }
    }
}
