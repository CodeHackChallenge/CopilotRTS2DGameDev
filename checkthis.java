import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D; 
import java.util.List;
 
 
public class RenderSystem { 
	 
	
    public void render(Graphics2D graphics2D, List<Entity> entities) { 
    	
    	for(Entity e : entities) {
			Position pos = e.getComponent(Position.class);
			
			if(pos == null) continue;
			 
			EntityAnimation anim = e.getComponent(EntityAnimation.class);
			Sprite sprite = e.getComponent(Sprite.class); 
			
			//animation
			if(anim != null) {
				Animation a = anim.getAnimation(); 
				if(a != null) {
					graphics2D.drawImage(a.getCurrentFrame(), (int)pos.x, (int)pos.y, null);
				} 
				 
	         } else if(sprite != null) {
	        	 graphics2D.setColor(sprite.color);
	        	 graphics2D.fillRect((int) pos.x, (int) pos.y, sprite.width, sprite.height);
	         } 
			 		
			//-------------------------------------------------------------------
			// HP and MP
			//-------------------------------------------------------------------	 
			Health hp = e.getComponent(Health.class);
			if(hp != null) {
				
				float pct =  hp.getDisplayedPrecent();   
				
				int barWidth = 44;
				int barHeight = 6;
				 
				int screenX = (int) pos.x + 10; 
				int screenY = (int) pos.y - 6;
				
				//-------------------------------------------------------------------
				// lerp color
				//-------------------------------------------------------------------	 
				Color green = Color.GREEN;
				Color orange = Color.ORANGE;
				Color red = Color.RED;
				
				Color barColor;
				if(pct > 0.5f) {					
					//barColor = Color.GREEN;
					//green - orange
					float t = (pct - 0.5f) / 0.5f; // 1 - 0
					barColor = lerpColor(orange, green, t);
				} else if(pct > 0.25f) {
					//barColor = Color.ORANGE;
					//orange - red
					float t = (pct - 0.25f) / 0.25f;  
					barColor = lerpColor(red, orange, t);
				} else {
					barColor = Color.RED;
				}
				//prevent disappearing bar
				int filled = (int) (barWidth * pct);
				if(hp.current > 0) {
					filled = Math.max(1, filled);
				}
				
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

    private Color lerpColor(Color a, Color b, float t ) {
    	t = Math.max(0f, Math.min(1f, t));
    	int r = (int)(a.getRed() + (b.getRed() - a.getRed()) * t);
    	int g = (int)(a.getGreen() + (b.getGreen() - a.getGreen()) * t);
    	int bc = (int)(a.getBlue() + (b.getBlue() - a.getBlue()) * t);
    	
    	return new Color(r, g, bc);    	
    }
	
    
}
