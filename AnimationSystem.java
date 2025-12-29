package demo.main;
 
import java.util.List;

import demo.main.AttackState.AttackStateType; 

public class AnimationSystem {

    public void update(List<Entity> entities, float delta) {

        for (Entity e : entities) {

            EntityAnimation animComp = e.getComponent(EntityAnimation.class);
            if (animComp == null) continue;

            Animation anim = animComp.getAnimation();
            if (anim == null) continue;

            AttackState as = e.getComponent(AttackState.class);

            // ---------------------------------------------------------
            // Switch animation based on attack state
            // ---------------------------------------------------------
            if (as != null) {
                if (as.state == AttackStateType.WINDUP ||
                    as.state == AttackStateType.HIT ||
                    as.state == AttackStateType.RECOVERY) {

                    animComp.setAnimation("attack");

                } else {
                    animComp.setAnimation("idle");
                }
            }

            // ---------------------------------------------------------
            // Advance animation frames
            // ---------------------------------------------------------
            anim.timer += delta;

            if (anim.timer >= anim.frameDelay) {
                anim.timer -= anim.frameDelay;
                anim.currentFrame++;

                if (anim.currentFrame >= anim.totalFrames) {
                    anim.currentFrame = 0;
                }
            }
        }
    }
}