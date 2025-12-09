class RenderSystem {
    private final AssetManager assets;

    public RenderSystem(GameContext context) {
        this.assets = context.getResource(AssetManager.class);
    }

    public void render(Entity entity) throws IOException {
        SpriteComponent sprite = entity.get(SpriteComponent.class);
        if (sprite != null) {
            Texture texture = assets.load(sprite.getTexturePath(), Texture::fromStream);
            texture.draw(entity.get(PositionComponent.class));
        }
    }
}
