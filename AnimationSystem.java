package demo.main;

import java.util.List;

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
            /*
            if (anim.currentFrame >= anim.totalFrames) {
            	anim.currentFrame = 0;
            	AttackCycle cycle = e.getComponent(AttackCycle.class);
            	
            	if(cycle != null) {
            		cycle.hasHit = false;
                  
            	}
            }
            */

            // ---------------------------------------------------------
            // PER-FRAME HITBOX UPDATE (Sword hit frame logic)
            // ---------------------------------------------------------
            AttackHitbox hitbox = e.getComponent(AttackHitbox.class);
            if (hitbox != null) { 
            	//40, 20, 66, 32
                // Example: sword only hits on frame 2
                if (anim.currentFrame == 0) {  
                    hitbox.offsetX = 40;  // adjust to your sprite
                    hitbox.offsetY = 20;
                    hitbox.width = 66;    // sword reach
                    hitbox.height = 32;
                	 // System.out.println("anim: "+anim.currentFrame + " " + hitbox.width);
                   /*
                    AttackCoolDown acd = e.getComponent(AttackCoolDown.class);  
                    if(acd != null) {
                    	    acd.cooldown = 0f;
                    }
                    */
                } else {
                    // Disable hitbox outside hit frame
                    hitbox.width = 0;
                    hitbox.height = 0;
                }
            }
        }
    }
}