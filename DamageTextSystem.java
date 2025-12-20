public class DamageTextSystem {

    public void update(List<Entity> entities, float delta) {
        List<Entity> toRemove = new ArrayList<>();

        for (Entity e : entities) {
            DamageTextComponent dt = e.getComponent(DamageTextComponent.class);
            Position pos = e.getComponent(Position.class);

            if (dt == null || pos == null) continue;

            // Move upward
            pos.y -= dt.upwardSpeed * delta;

            // Fade out
            dt.elapsed += delta;
            dt.alpha = 1f - (dt.elapsed / dt.lifetime);

            if (dt.alpha <= 0f) {
                toRemove.add(e);
            }
        }

        entities.removeAll(toRemove);
    }
}
