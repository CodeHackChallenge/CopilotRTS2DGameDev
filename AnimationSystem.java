public class AnimationSystem {

    public void update(List<Entity> entities, float delta) {

        for (Entity e : entities) {

            EntityAnimation animComp = e.getComponent(EntityAnimation.class);
            if (animComp == null) continue;

            Animation anim = animComp.getAnimation();
            if (anim == null) continue;

            // Advance animation timer
            anim.timer += delta;

            if (anim.timer >= anim.frameDelay) {
                anim.timer -= anim.frameDelay;
                anim.currentFrame++;

                if (anim.currentFrame >= anim.totalFrames) {
                    anim.currentFrame = 0;
                }
            }

            // ---------------------------------------------------------
            // PER-FRAME HITBOX UPDATE (Sword hit frame logic)
            // ---------------------------------------------------------
            AttackHitbox hitbox = e.getComponent(AttackHitbox.class);
            if (hitbox != null) {

                // Example: sword hits on frame 3
                if (anim.currentFrame == 3) {
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
}
