public class AttackHitboxSystem {

    public void update(List<Entity> entities, float delta) {

        for (Entity e : entities) {

            AttackState as = e.getComponent(AttackState.class);
            AttackHitbox hitbox = e.getComponent(AttackHitbox.class);

            if (as == null || hitbox == null) continue;

            if (as.state == AttackStateType.HIT) {
                hitbox.offsetX = 40;
                hitbox.offsetY = 20;
                hitbox.width = 66;
                hitbox.height = 32;
            } else {
                hitbox.width = 0;
                hitbox.height = 0;
            }
        }
    }
}
