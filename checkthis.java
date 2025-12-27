public class AnimationSystem {

    public void update(List<Entity> entities, float delta) {

        for (Entity e : entities) {

            EntityAnimation anim = e.getComponent(EntityAnimation.class);
            if (anim == null) continue;

            Animation a = anim.getAnimation();
            if (a == null) continue;

            // Advance animation timer
            a.timer += delta;

            // If enough time passed, move to next frame
            if (a.timer >= a.frameDelay) {
                a.timer -= a.frameDelay;
                a.currentFrame++;

                // Loop animation
                if (a.currentFrame >= a.totalFrames) {
                    a.currentFrame = 0;
                }
            }
        }
    }
}
