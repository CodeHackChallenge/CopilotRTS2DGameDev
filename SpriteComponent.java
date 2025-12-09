class SpriteComponent {
    private final String texturePath; // e.g. "resources/textures/unit.png"

    public SpriteComponent(String texturePath) {
        this.texturePath = texturePath;
    }

    public String getTexturePath() { return texturePath; }
}
