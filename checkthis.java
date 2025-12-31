import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

public class RenderSystem {
	 
	
	
    public void render(Graphics2D g, List<Entity> entities) {

        for (Entity e : entities) {
        	RenderPosition rp = e.getComponent(RenderPosition.class);
        	Position pos = e.getComponent(Position.class);
        	
            if (pos == null || rp == null) continue;
            
        	rp.x = (int)Math.round(pos.x);
        	rp.y = (int)Math.round(pos.y);
        	//System.out.println(rp.x + " "+ rp.y);
            //round position
           // int baseX = (int)Math.round(pos.x);
           // int baseY = (int)Math.round(pos.y);
            
            // =========================================================
            // 1. DRAW SPRITE / ANIMATION
            // =========================================================
            EntityAnimation animComp = e.getComponent(EntityAnimation.class);
            if (animComp != null) {

                Animation anim = animComp.getAnimation();
                if (anim != null && anim.frames != null) {
//                	//for smooth movement
//                	int drawX = (int)Math.round(pos.x);
//                	int drawY = (int)Math.round(pos.y);
                	
                    BufferedImage frame = anim.frames[anim.currentFrame];
                    if (frame != null) {
                       // g.drawImage(frame, rp.x, rp.y, null);
                        
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

                int drawX = rp.x + (int)Math.round(dt.offsetX);
                int drawY = rp.y + (int)Math.round(dt.offsetY);

                g.drawString(dt.text, drawX, drawY);
            }
            // =========================================================
            // Move marker
            // =========================================================
            MovementTarget mt = e.getComponent(MovementTarget.class);
            if(mt != null && mt.hasTarget) {
            	g.setColor(Color.YELLOW);
            	g.fillOval((int)mt.x - 4, (int)mt.y - 4, 8, 8);
            }
            
           
            // =========================================================
            // 3. DEBUG: COLLISION BOX
            // =========================================================
            Collision col = e.getComponent(Collision.class);
            if (col != null) {
//            	int colX = baseX + col.offsetX;
//            	int colY = baseY + col.offsetY; 
            	
                g.setColor(new Color(0, 255, 0, 120)); // semi‑transparent green
                g.drawRect(rp.x + col.offsetX, 
                		   rp.y + col.offsetY, 
                		   col.width, 
                		   col.height);
                
            }

            // =========================================================
            // 4. DEBUG: ATTACK HITBOX
            // =========================================================
            AttackHitbox hit = e.getComponent(AttackHitbox.class);
            if (hit != null && hit.width > 0 && hit.height > 0) {
//            	int hitX = baseX + hit.offsetX;
//            	int hitY = baseY + hit.offsetY; 
            	
                g.setColor(new Color(255, 0, 0, 120)); // semi‑transparent red
                g.drawRect(rp.x + hit.offsetX, 
                		   rp.y + hit.offsetY, 
                		   hit.width, 
                		   hit.height);
                
            }
             
        }
    }
     
    
