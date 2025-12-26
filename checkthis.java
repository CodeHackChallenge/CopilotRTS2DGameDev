import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
 
 
public class RenderSystem { 
    public void render(Graphics2D graphics2D, List<Entity> entities) { 
    	
    	for(Entity e : entities) {
			Position pos = e.getComponent(Position.class);
			
			if(pos == null) continue;
			
			//Sprite image = e.getComponent(Sprite.class); 
			EntityAnimation anim = e.getComponent(EntityAnimation.class);
		 
			//animation
			if(anim != null) {
				 BufferedImage frame = anim.getAnimation().getCurrentFrame();
				 graphics2D.drawImage(frame, (int)pos.x, (int)pos.y, null);
				 
	         }  
			 		
			//-------------------------------------------------------------------
			// HP and MP
			//-------------------------------------------------------------------	 
			Health hp = e.getComponent(Health.class);
			if(hp != null) {
				
				float pct =  hp.getPercent();  
				System.out.println(pct);
				int barWidth = 44;
				int barHeight = 6;
				 
				int screenX = (int) pos.x + 10; 
				int screenY = (int) pos.y - 6;
				
				Color barColor;
				if(pct > 0.05f) {
					barColor = Color.GREEN;
				} else if(pct > 0.25f) {
					barColor = Color.ORANGE;
				} else {
					barColor = Color.RED;
				}
				
				int filled = (int) (barWidth * pct);
				
				graphics2D.setColor(barColor);
				graphics2D.fillRect(screenX, screenY, filled, barHeight);
				
			} 
			
			//-------------------------------------------------------------------
			// damage Event
			//-------------------------------------------------------------------	 
			RenderTextComponent rtc = e.getComponent(RenderTextComponent.class);
			DamageTextComponent dt = e.getComponent(DamageTextComponent.class); 
			if(rtc != null && dt != null && pos != null) {  
				
				
				int baseX = (int) (pos.x + dt.offsetX);
				int baseY = (int) (pos.y + dt.offsetY); 
				
				int alpha = (int)(dt.alpha * 255);
				alpha = Math.max(0, Math.min(255, alpha));
				//shadow
				Color shawdowColor = new Color(0, 0, 0, alpha);
				//main color
				Color mainColor = new Color(rtc.color.getRed(),
										rtc.color.getGreen(),
										rtc.color.getBlue(),
										alpha						
				);
				//prepare scaled drawing
				Graphics2D gText = (Graphics2D) graphics2D.create();
				//apply scaling for crit 
				gText.translate(baseX, baseY);
				gText.scale(dt.scale, dt.scale);
				
				if(!dt.isCrit) {
					//shadow
					gText.setColor(shawdowColor); 
					gText.drawString(dt.text, 
							              rtc.shadowOffsetX, 
							              rtc.shadowOffsetY 
					);
				}  
				
				gText.setColor(mainColor);
				gText.setFont(new Font("Arial", Font.BOLD, rtc.size));		 
				gText.drawString(dt.text, 0, 0);
				
				gText.dispose();
			} 
			 
				
		} 
    	 
    }
    
    
}

