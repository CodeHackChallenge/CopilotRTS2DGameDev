public class Entity {
    private int id;
    private Position position;
    private BoundsComponent bounds;
    private Sprite sprite;

    public Entity(int id, Position position, BoundsComponent bounds, Sprite sprite) {
        this.id = id;
        this.position = position;
        this.bounds = bounds;
        this.sprite = sprite;
    }

    public int getId() { return id; }
    public Position getPosition() { return position; }
    public BoundsComponent getBounds() { return bounds; }
    public Sprite getSprite() { return sprite; }
}
