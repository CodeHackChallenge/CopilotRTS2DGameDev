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

                // Example: sword only hits on frame 2
                if (anim.currentFrame == 2) {
                    hitbox.offsetX = 32;  // adjust to your sprite
                    hitbox.offsetY = 0;
                    hitbox.width = 66;    // sword reach
                    hitbox.height = 32;
                } else {
                    // Disable hitbox outside hit frame
                    hitbox.width = 0;
                    hitbox.height = 0;
                }
            }
        }
    }
}
