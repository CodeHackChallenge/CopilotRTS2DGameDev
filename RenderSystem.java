package demo.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

public class RenderSystem {
	 
	
    public void render(Graphics2D g, List<Entity> entities) {

        for (Entity e : entities) {

            Position pos = e.getComponent(Position.class);
            if (pos == null) continue;

            // =========================================================
            // 1. DRAW SPRITE / ANIMATION
            // =========================================================
            EntityAnimation animComp = e.getComponent(EntityAnimation.class);
            if (animComp != null) {

                Animation anim = animComp.getAnimation();
                if (anim != null && anim.frames != null) {

                    BufferedImage frame = anim.frames[anim.currentFrame];
                    if (frame != null) {
                        g.drawImage(frame, (int) pos.x, (int) pos.y, null);
                    }
                }
            }

            // =========================================================
            // 2. DRAW DAMAGE TEXT (always above sprite)
            // =========================================================
            DamageTextComponent dt = e.getComponent(DamageTextComponent.class);
            RenderTextComponent rtc = e.getComponent(RenderTextComponent.class);

            if (dt != null && rtc != null) {

                g.setColor(rtc.color);
                g.setFont(rtc.font);

                int drawX = (int) (pos.x + dt.offsetX);
                int drawY = (int) (pos.y + dt.offsetY);

                g.drawString(dt.text, drawX, drawY);
            }
            
            // =========================================================
            // 3. DEBUG: COLLISION BOX
            // =========================================================
            Collision col = e.getComponent(Collision.class);
            if (col != null) {
                g.setColor(new Color(0, 255, 0, 120)); // semi‑transparent green
                g.drawRect(
                    (int) (pos.x + col.offsetX),
                    (int) (pos.y + col.offsetY),
                    col.width,
                    col.height
                );
            }

            // =========================================================
            // 4. DEBUG: ATTACK HITBOX
            // =========================================================
            AttackHitbox hit = e.getComponent(AttackHitbox.class);
            if (hit != null && hit.width > 0 && hit.height > 0) {
                g.setColor(new Color(255, 0, 0, 120)); // semi‑transparent red
                g.drawRect(
                    (int) (pos.x + hit.offsetX),
                    (int) (pos.y + hit.offsetY),
                    hit.width,
                    hit.height
                );
            }
        }
    }
     
    
}